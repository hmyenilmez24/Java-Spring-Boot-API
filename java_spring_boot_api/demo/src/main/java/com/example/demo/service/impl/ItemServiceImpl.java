package com.example.demo.service.impl;

import com.example.demo.Repository.ItemRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.Item;
import com.example.demo.entity.User;
import com.example.demo.service.ItemService;
import com.example.demo.smtp.EmailDetails;
import com.example.demo.smtp.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    @Autowired
    private final EmailService emailService;

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    @Override
    public Item newItem(Item item) {
        if (item.getItemName().equals("") || item.getItemPrice() == null) {
            throw new NullPointerException();
        }
        try {
            return itemRepository.save(item);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        } catch (NullPointerException e) {
            throw new NullPointerException();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> getItemsBetweenMinAndMaxPrices(Long min, Long max) {
        List<Item> resultItem = itemRepository.findAll();
        if (min > max) {
            throw new IllegalArgumentException();
        }
        try {
            return (resultItem.stream().filter(c -> c.getItemPrice() > min && c.getItemPrice() < max).collect(Collectors.toList()));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    @Override
    public Item getItem(Long id) {
        Optional<Item> item = itemRepository.findById(id);

        if (item.isPresent()) {
            return item.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Item updateItem(Long id, Item item) {
        Optional<Item> resultItem = itemRepository.findById(id);

        if (resultItem.isPresent()) {
            resultItem.get().setItemName(item.getItemName());
            resultItem.get().setItemDescription(item.getItemDescription());
            resultItem.get().setItemPrice(item.getItemPrice());
            return itemRepository.save(resultItem.get());
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Boolean deleteItem(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            itemRepository.deleteById(id);
            return true;
        } else {
            throw new EntityNotFoundException();
        }
    }

    private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy-hh:mm:ss");

    @Override
    public List<Item> newDiscount(Map<String, String> json) {

        List<Item> resultItems = itemRepository.findAll().stream().filter(c -> c.getCategory().equals(json.get("item_category"))).collect(Collectors.toList());

        resultItems.forEach(a -> {
            a.setDiscountRate(Integer.valueOf(json.get("discount_rate")));
            a.setOldPrice(a.getItemPrice());
            a.setPlannedDiscountedPrice(a.getItemPrice() - a.getItemPrice() * a.getDiscountRate() / 100);
            a.setSalePlanned(true);

            try {
                Date startDate = new Date(DATE_TIME_FORMAT.parse(json.get("discount_start")).getTime());
                Date endDate = new Date(DATE_TIME_FORMAT.parse(json.get("discount_end")).getTime());
                if (startDate.before(endDate) & a.getDiscountRate()>0){
                    a.setDiscountStart(startDate);
                    a.setDiscountEnd(endDate);
                    log.info("dates are given");
                }else {
                    log.info("dates are not given");
                    throw new IllegalArgumentException();
                }

            } catch (ParseException e) {
                log.info("dates are not given");
                throw new IllegalArgumentException(e);
            }

            itemRepository.save(a);

        });

        return resultItems;
    }

    @Scheduled(fixedRate = 5000, initialDelay = 1000)
    @Async
    public void scheduledDiscount() {

        LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

        Date currentTime = Date.from(instant);

        List<Item> resultItems = itemRepository.findAll().stream().filter(c -> c.getSalePlanned().equals(true)).toList();
        List<User> resultUser = userRepository.findAll().stream().filter(c -> !c.getEmail().equals("demomerttest@gmail.com")).toList();

        System.out.println("system time: " + currentTime);

        try {
            resultItems.forEach(a -> {

                if (a.getDiscountStart().after(currentTime)) {
                    a.setIsOnSale(false);
                    itemRepository.save(a);
                    log.info("setIsOnSale false " + a.getItemName());
                }

                if (a.getDiscountStart().before(currentTime) && a.getDiscountEnd().after(currentTime)) {
                    a.setIsOnSale(true);
                    a.setItemPrice(a.getPlannedDiscountedPrice());
                    itemRepository.save(a);
                    log.info("setIsOnSale true " + a.getItemName());
                    log.info("added to email body " + a.getItemName());
                }

                if (a.getDiscountEnd().before(currentTime)) {
                    a.setItemPrice(a.getOldPrice());
                    a.setDiscountStart(null);
                    a.setDiscountEnd(null);
                    a.setDiscountRate(null);
                    a.setOldPrice(null);
                    a.setPlannedDiscountedPrice(null);
                    a.setSalePlanned(false);
                    a.setIsOnSale(false);
                    itemRepository.save(a);
                    log.info("setIsOnSale & setSalePlanned null " + a.getItemName());
                }
            });
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            log.info("No discounted items yet.");
        }

        int randomUserIndex = 0;

        if (resultItems.isEmpty()) {
            log.info("No discounted items.");
            return;
        }
        Random random = new Random();
        randomUserIndex = random.nextInt(resultUser.size());

        StringBuilder msgBody = new StringBuilder(String.format("Hi %s \n ",
                resultUser.get(randomUserIndex).getFirstName()));

        String line1 = "Item name";
        String line2 = "New Price";
        String line3 = "Old Price";
        for (Item resultItem : resultItems) {
            if (!Objects.equals(resultItem.getOldPrice(), resultItem.getItemPrice()) & resultItem.getOldPrice()!=null) {
                msgBody.append(String.format(line1 + ": %s \n ",
                        resultItem.getItemName())).append(String.format(line2 + ": %s \n ",
                        resultItem.getItemPrice())).append(String.format(line3 + ": %s \n\n ",
                        resultItem.getOldPrice()));
            }
        }
        if (msgBody.toString().length()>(line1.length()+line2.length()+line3.length())) {
            try {
                emailService.sendSimpleMail(new EmailDetails(resultUser.get(randomUserIndex).getEmail(),
                        msgBody.toString(),
                        "Discount Started"));
            } catch (MailException e) {
                e.getStackTrace();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException();
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }
}

