package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.pojo.Result;
import com.example.springboot.pojo.Words;
import com.example.springboot.pojo.WrongWord;
import com.example.springboot.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/word")
public class WordController {
    @Autowired
    private WordService wordService;
    @RequestMapping("/findWordById")
    public Result findWordById(Integer id) {
        Words word = wordService.findWordById(id);
        if (word == null) {
            return Result.error(1,"未找到单词");
        } else {
            System.out.println(word.getTranslation());
            return Result.success(word);
        }
    }
    @RequestMapping("/findWordByWord")
    public Result findWordByWord(String word) {
        Words words = wordService.findWordByWord(word);
        if (words == null) {
            return Result.error(1,"未找到单词");
        } else {
            return Result.success(words);
        }
    }
    @RequestMapping("/findAllWordsByBookId")
    public Result findAllWordsByBookId(Integer wordbook_id) {
        List<Words> words = wordService.findAllWordsByBookId(wordbook_id);
        if (words == null) {
            return Result.error(1,"未找到单词");
        } else {
            return Result.success(words);
        }
    }
    @RequestMapping("/searchWords")
    public Result searchWord(String word) {
        QueryWrapper<Words> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("word", word);
        List<Words> words = wordService.list(queryWrapper);
        if (words == null) {
            return Result.error(1,"未找到单词");
        } else {
            return Result.success(words);
        }
    }
    @GetMapping("/getWords")
    public Result getWords(@RequestParam(defaultValue = "1") int currentPage,
                           @RequestParam(defaultValue = "10") int pageSize,
                           @RequestParam(defaultValue = "0") int bookId) {
        Page<Words> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Words> queryWrapper = new QueryWrapper<Words>()
                .exists("select 1 from wordbook_words where " +
                        "wordbook_words.word_id = id and wordbook_id = " + bookId);
        long totalRecords = wordService.selectCount(queryWrapper);
        long totalPages = (totalRecords + pageSize - 1) / pageSize;
        if(currentPage > totalPages) {
            return Result.error(1,"没有更多了");
        }
        IPage<Words> iPage = wordService.findWordPage(page, queryWrapper);
        if (iPage == null) {
            return Result.error(1,"未找到单词");
        } else {
            return Result.success(iPage.getRecords());
        }
    }
    @GetMapping("/getWordNumByBookId")
    public Result getWordNumByBookId(Integer bookId) {
        QueryWrapper<Words> queryWrapper = new QueryWrapper<Words>()
                .exists("select 1 from wordbook_words where " +
                        "wordbook_words.word_id = id and wordbook_id = " + bookId);
        long totalRecords = wordService.selectCount(queryWrapper);
        return Result.success(totalRecords);
    }
    @GetMapping("getLearningWords")
    public Result getLearningWords(String userId, Integer bookId, Integer process){
        QueryWrapper<Words> queryWrapper = new QueryWrapper<Words>();
        queryWrapper.exists("select 1 from wordbook_words where " +
                "wordbook_words.word_id = id and wordbook_id = " + bookId);
        queryWrapper.gt("id", process);
        List<Words> words = wordService.list(queryWrapper);
        // 只要前10个单词
        if (words.size() > 15) {
            words = words.subList(0, 15);
        }
        if (words == null) {
            return Result.error(1,"未找到单词");
        } else {
            return Result.success(words);
        }
    }
}
