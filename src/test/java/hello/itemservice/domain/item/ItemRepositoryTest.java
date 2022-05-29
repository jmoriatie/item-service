package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Item item = new Item("ItemA", 10000, 10);

        //when
        Item saveItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(saveItem.getId());
        assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void findAll(){
        //given
        Item itemA = new Item("ItemA", 10000, 10);
        Item itemB = new Item("ItemB", 20000, 33);

        //when
        itemRepository.save(itemA);
        itemRepository.save(itemB);

        List<Item> findItems = itemRepository.findAll();

        //then
        assertThat(findItems).hasSize(2);
        assertThat(findItems.size()).isEqualTo(2);
        assertThat(findItems).contains(itemA, itemB);
    }

    @Test
    void update(){
        //given
        Item itemA = new Item("ItemA", 10000, 10);
        Item savedItem = itemRepository.save(itemA);
        Long itemId = savedItem.getId();

        //when
        Item updateParam = new Item("ItemA", 55555, 50);
        itemRepository.update(itemId, updateParam);

        //then
        Item findItem = itemRepository.findById(itemId);
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }




}