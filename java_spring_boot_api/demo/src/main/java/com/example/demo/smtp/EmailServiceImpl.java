package com.example.demo.smtp;


import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    public Boolean sendSimpleMail(EmailDetails details)
    {
        if (details.getRecipient().equals("") || details.getMsgBody().equals("")) {
            throw new NullPointerException();
        }
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            javaMailSender.send(mailMessage);
            return true;
        }catch (MailException e) {
            e.getStackTrace();
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException();
        }catch (Exception e){
            throw new Error(e);
        }
        return false;
    }
}
