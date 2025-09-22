// 代码生成时间: 2025-09-23 01:18:29
package com.example.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class InventoryManagementSystem {

    public static void main(String[] args) {
        SpringApplication.run(InventoryManagementSystem.class, args);
    }
}

// InventoryItem.java
package com.example.inventory.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int quantity;
}

// InventoryService.java
package com.example.inventory.service;

import com.example.inventory.model.InventoryItem;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public List<InventoryItem> getAllItems() {
        return repository.findAll();
    }

    public void addItem(InventoryItem item) {
        repository.save(item);
    }

    public InventoryItem getItemById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public void updateItem(Long id, InventoryItem item) {
        InventoryItem existingItem = getItemById(id);
        existingItem.setName(item.getName());
        existingItem.setQuantity(item.getQuantity());
        repository.save(existingItem);
    }

    public void deleteItem(Long id) {
        getItemById(id);
        repository.deleteById(id);
    }
}

// InventoryRepository.java
package com.example.inventory.repository;

import com.example.inventory.model.InventoryItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InventoryRepository extends CrudRepository<InventoryItem, Long> {
    // Custom repository methods can be added here
}
