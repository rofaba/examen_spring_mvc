package org.example.examenspringmvc.service;

import org.example.examenspringmvc.model.Item;
import org.example.examenspringmvc.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item crear(Item producto) {
        return itemRepository.save(producto);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> findById(String id) {
        return itemRepository.findById(id);
    }

    // Listado de items con stock (count < 10)
    public List<Item> findByCountLessThan(int count) {
        return itemRepository.findByCountLessThan(count);
    }

}