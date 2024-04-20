package com.example.springboot.service;

import com.example.springboot.pojo.Words;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class WordServiceTest {
    @Autowired
    private WordService wordService;
    @Test
    void findWordById() {
        System.out.println(wordService.getById(224));
    }

    @Test
    void testQuery() {
        List<Words> words =wordService.listByIds(List.of(221, 222, 223, 224));
        words.forEach(System.out::println);
    }
}