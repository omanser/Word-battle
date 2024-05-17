package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.pojo.NewWord;
import com.example.springboot.pojo.Result;
import com.example.springboot.pojo.WordListItem;
import com.example.springboot.pojo.WrongWord;
import com.example.springboot.service.NewWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/new_word")
public class NewWordController {
    @Autowired
    private NewWordService newWordService;
    @PostMapping("/insert")
    public ResponseEntity<?> insertData(@RequestParam("id") String id,
                                        @RequestParam("word") String word,
                                        @RequestParam("username") String username,
                                        @RequestParam("meanings") String meanings, // 直接接收JSON字符串
                                        @RequestParam("updateTime") String updateTime) {
        NewWord newWord =  new NewWord(
                Integer.parseInt(id),
                username,
                word,
                meanings,
                LocalDateTime.parse(updateTime)
        );
        newWordService.saveOrUpdate(newWord);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete")
    public Result deleteData(@RequestParam("id") String id,
                             @RequestParam("username") String username) {
        QueryWrapper<NewWord> queryWrapper = new QueryWrapper<NewWord>()
                .eq("id", id)
                .eq("username", username);
        newWordService.remove(queryWrapper);
        return Result.success("删除成功");
    }
    @GetMapping("/getAll")
    public Result getAll(@RequestParam(defaultValue = "1") int currentPage,
                         @RequestParam(defaultValue = "10") int pageSize,
                         @RequestParam String username) {
        Page<NewWord> page = new Page<>(currentPage, pageSize);
        QueryWrapper<NewWord> queryWrapper = new QueryWrapper<NewWord>()
                .eq("username", username);
        long totalRecords = newWordService.count(queryWrapper);
        long totalPages = (totalRecords + pageSize - 1) / pageSize;
        if(currentPage > totalPages) {
            return Result.error(1,"没有更多了");
        }
        IPage<NewWord> page1 = newWordService.findNewWordPage(page, queryWrapper);
        List<NewWord> list = page1.getRecords();
        List<WordListItem> wordListItems = toWordListItem(list);
        return Result.success(wordListItems);
    }
    public List<WordListItem> toWordListItem(List<NewWord> list) {
        return list.stream().map(item -> {
            WordListItem wordListItem = new WordListItem();
            wordListItem.setId(item.getId());
            wordListItem.setWord(item.getWord());
            wordListItem.setMeanings(item.getMeaning());
            return wordListItem;
        }).toList();
    }
}
