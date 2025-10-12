// 代码生成时间: 2025-10-13 03:49:32
package com.inventorymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryManagementSystem {

    public static void main(String[] args) {
        SpringApplication.run(InventoryManagementSystem.class, args);
    }
}

// 库存项实体类
package com.inventorymanagement.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class InventoryItem {
    @Id
    private Long id;
    private String name;
    private int quantity;

    // 省略构造函数、getter和setter方法

    // 省略toString方法
}

// 库存服务接口
package com.inventorymanagement.service;

import com.inventorymanagement.model.InventoryItem;
import java.util.List;

public interface InventoryService {
    InventoryItem addInventoryItem(InventoryItem inventoryItem);
    List<InventoryItem> listAllInventoryItems();
    InventoryItem updateInventoryItem(InventoryItem inventoryItem);
    void deleteInventoryItem(Long id);
}

// 库存服务实现类
package com.inventorymanagement.service.impl;

import com.inventorymanagement.model.InventoryItem;
import com.inventorymanagement.repository.InventoryRepository;
import com.inventorymanagement.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public InventoryItem addInventoryItem(InventoryItem inventoryItem) {
        return inventoryRepository.save(inventoryItem);
    }

    @Override
    public List<InventoryItem> listAllInventoryItems() {
        return inventoryRepository.findAll();
    }

    @Override
    public InventoryItem updateInventoryItem(InventoryItem inventoryItem) {
        return inventoryRepository.save(inventoryItem);
    }

    @Override
    public void deleteInventoryItem(Long id) {
        inventoryRepository.deleteById(id);
    }
}

// 库存仓库接口
package com.inventorymanagement.repository;

import com.inventorymanagement.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    // 可以根据需要添加自定义查询方法
}

// 库存控制器
package com.inventorymanagement.controller;

import com.inventorymanagement.model.InventoryItem;
import com.inventorymanagement.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/item")
    public InventoryItem addInventoryItem(@RequestBody InventoryItem inventoryItem) {
        return inventoryService.addInventoryItem(inventoryItem);
    }

    @GetMapping("/items")
    public List<InventoryItem> listAllInventoryItems() {
        return inventoryService.listAllInventoryItems();
    }

    @PutMapping("/item")
    public InventoryItem updateInventoryItem(@RequestBody InventoryItem inventoryItem) {
        return inventoryService.updateInventoryItem(inventoryItem);
    }

    @DeleteMapping("/item/{id}")
    public void deleteInventoryItem(@PathVariable Long id) {
        inventoryService.deleteInventoryItem(id);
    }
}
