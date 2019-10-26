package com.example.demo.repositories;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@DataJpaTest
@RunWith(SpringRunner.class)
public class ItemRepositoryTests {

    @Autowired
    ItemRepository itemRepository;

    @Before
    public void setUp() {
        itemRepository.save(getItems2().get(0));
    }

    @Test
    public void findByName() {
        assertNotNull(itemRepository.findByName("bottle1"));
    }

    private List<Item> getItems2() {
        Item bottle = new Item();
        bottle.setDescription("Glass bottle");
        bottle.setName("bottle1");
        bottle.setPrice(BigDecimal.valueOf(99.99));
        List<Item> list = new ArrayList<>(Arrays.asList(bottle));
        return list;
    }


}
