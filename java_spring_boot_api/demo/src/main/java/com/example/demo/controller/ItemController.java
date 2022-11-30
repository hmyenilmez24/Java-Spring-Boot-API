package com.example.demo.controller;
import com.example.demo.entity.Item;
import com.example.demo.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/new")
    public ResponseEntity<Item> newItem(@RequestBody Item item){
        Item resultItem = itemService.newItem(item);
        return ResponseEntity.ok(resultItem);
    }

    @GetMapping("")
    public ResponseEntity<List<Item>> getItems(){
        List<Item> items = itemService.getItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/min/{min}/max/{max}")
    public ResponseEntity<List<Item>> getItemsBetweenMinAndMaxPrices(@PathVariable("min") Long min,@PathVariable("max") Long max){
        List<Item> items = itemService.getItemsBetweenMinAndMaxPrices(min,max);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable("id") Long id){
        Item item = itemService.getItem(id);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable("id") Long id, @RequestBody Item item){
        Item resultItem = itemService.updateItem(id, item);
        return ResponseEntity.ok(resultItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteItem (@PathVariable("id") Long id){
        Boolean status = itemService.deleteItem(id);
        return ResponseEntity.ok((status));
    }

    @PostMapping("/discount")
    public ResponseEntity<List<Item>> newDiscount(@RequestBody Map<String, String> json){
        List<Item> items = itemService.newDiscount(json);
        return ResponseEntity.ok(items);
    }

}
