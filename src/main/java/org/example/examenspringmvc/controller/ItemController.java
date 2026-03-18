package org.example.examenspringmvc.controller;

import org.example.examenspringmvc.model.Item;
import org.example.examenspringmvc.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Vista de todos los items
    @GetMapping("/items")
    public String findAll(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        return "items";
    }

    // Vista detalle item
    @GetMapping("/items/{id}")
    public String buscarPorId(@PathVariable String id, Model model) {
        return itemService.findById(id)
                .map(item -> {
                    model.addAttribute("item", item);
                    return "detail-item";
                })
                .orElse("error");
    }

    // Vista de stats
    @GetMapping("/stats")
    public String stats(Model model) {
        List<Item> items = itemService.findAll();

        long totalItems = items.size();

        List<Item> lowCountItems = items.stream()
                .filter(item -> item.getCount() < 100)
                .toList();

        Set<String> manufacturers = items.stream()
                .map(Item::getManufacturer)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        model.addAttribute("totalItems", totalItems);
        model.addAttribute("lowCountItems", lowCountItems);
        model.addAttribute("manufacturers", manufacturers);

        return "stats";
    }

    // Formulario de edición
    @GetMapping("/items/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        return itemService.findById(id)
                .map(item -> {
                    model.addAttribute("item", item);
                    return "edit-item";
                })
                .orElse("error");
    }

    // Guardar cambios
    @PostMapping("/items/{id}/edit")
    public String editItem(@PathVariable String id, @ModelAttribute Item updatedItem, Model model) {
        return itemService.findById(id)
                .map(item -> {
                    item.setTitle(updatedItem.getTitle());
                    item.setCategory(updatedItem.getCategory());
                    item.setCount(updatedItem.getCount());
                    item.setManufacturer(updatedItem.getManufacturer());

                    itemService.crear(item);

                    model.addAttribute("item", item);
                    return "detail-item";
                })
                .orElse("error");
    }
}