package com.example.demo.service;

import com.example.demo.entity.Item;
import com.example.demo.entity.Item;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ItemService {

    Item newItem(Item item);
    List<Item> getItems();
    List<Item> getItemsBetweenMinAndMaxPrices(Long min, Long max);
    Item getItem(Long id);
    Item updateItem(Long id, Item item);
    Boolean deleteItem(Long id);
    List<Item> newDiscount(Map<String, String> json);
}