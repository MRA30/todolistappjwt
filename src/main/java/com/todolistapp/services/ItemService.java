package com.todolistapp.services;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolistapp.dto.response.ItemResponse;
import com.todolistapp.dto.response.TodoResponse;
import com.todolistapp.models.entity.Item;
import com.todolistapp.models.repos.ItemRepo;

@Service
@Transactional
public class ItemService {
    
    @Autowired
    private ItemRepo itemRepo;

    public Item save(Item item){
        return itemRepo.save(item);
    }


    public ItemResponse updateItem(long id, Map<String, Object> changes){
        ItemResponse itemResponse = findById(id);
        Item item = new Item(
            itemResponse.getId(),
            itemResponse.getItem(),
            itemResponse.isCek(),
            itemResponse.getTodoId(),
            itemResponse.getImage()
        );

        // if(itemRequest.getItem() != null){
        //     itemResponse.setItem(itemRequest.getItem());
        // }
        // if(itemRequest.toString() != null){
        //     itemResponse.setCek(itemRequest.isCek());
        // }
        // if(itemRequest.getImage() != null){
        //     itemResponse.setImage(itemRequest.getImage());
        // }
        changes.forEach(
                (change, value) -> {
                    switch (change){
                        case "item": item.setItem((String) value); break;
                        case "cek": item.setCek((boolean) value); break;
                    }
                }
        );
    
        itemRepo.save(item);
        ItemResponse response = new ItemResponse(
            item.getId(),
            item.getItem(),
            item.isCek(),
            item.getTodoId(),
            item.getImage()
        );

        return response;
    }

    public ItemResponse findById(Long id){
        Optional<Item> item = itemRepo.findById(id);
        return new ItemResponse(
            item.get().getId(),
            item.get().getItem(),
            item.get().isCek(),
            item.get().getTodoId(),
            item.get().getImage()
        );
    }

    public void delete(long id){
        itemRepo.deleteById(id);
    }

    public void deleteAll(TodoResponse todoResponse){
        itemRepo.deleteAll(todoResponse.getItems());
    }

}
