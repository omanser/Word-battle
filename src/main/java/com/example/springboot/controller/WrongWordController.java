package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.pojo.*;
import com.example.springboot.service.WrongWordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/wrong_word")
public class WrongWordController {
    @Autowired
    private WrongWordService wrongWordService;
    @PostMapping("/insert")
    public ResponseEntity<?> insertData(@RequestParam("id") String id,
                                        @RequestParam("word") String word,
                                        @RequestParam("username") String username,
                                        @RequestParam("meanings") String meanings, // 直接接收JSON字符串
                                        @RequestParam("updateTime") String updateTime) {
        WrongWord wrongWord = new WrongWord(
                Integer.parseInt(id),
                username,
                word,
                meanings,
                LocalDateTime.parse(updateTime)
        );
        wrongWordService.saveOrUpdate(wrongWord);
        return ResponseEntity.ok().build();
    }
    // 删除某单词
    @DeleteMapping("/delete")
    public Result deleteData(@RequestParam("id") String id,
                             @RequestParam("username") String username) {
        QueryWrapper<WrongWord> queryWrapper = new QueryWrapper<WrongWord>()
                .eq("id", id)
                .eq("username", username);
        wrongWordService.remove(queryWrapper);
        return Result.success("删除成功");
    }
    @GetMapping("/getAll")
    public Result getAll(@RequestParam(defaultValue = "1") int currentPage,
                         @RequestParam(defaultValue = "10") int pageSize,
                         @RequestParam String username) {
        Page<WrongWord> page = new Page<>(currentPage, pageSize);
        QueryWrapper<WrongWord> queryWrapper = new QueryWrapper<WrongWord>()
                .eq("username", username);
        long totalRecords = wrongWordService.count(queryWrapper);
        long totalPages = (totalRecords + pageSize - 1) / pageSize;
        if(currentPage > totalPages) {
            return Result.error(1,"没有更多了");
        }
        IPage<WrongWord> page1 = wrongWordService.findWrongWordPage(page, queryWrapper);
        List<WrongWord> list = page1.getRecords();
        List<WordListItem> wordListItems = toWordListItem(list);
        return Result.success(wordListItems);
    }
    public List<WordListItem> toWordListItem(List<WrongWord> list) {
        return list.stream().map(item -> {
            WordListItem wordListItem = new WordListItem();
            wordListItem.setId(item.getId());
            wordListItem.setWord(item.getWord());
            wordListItem.setMeanings(item.getMeaning());
            return wordListItem;
        }).toList();
    }
}
