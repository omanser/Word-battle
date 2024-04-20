package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.mapper.WordMapper;
import com.example.springboot.pojo.Words;
import com.example.springboot.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordServiceImpl extends ServiceImpl<WordMapper, Words> implements WordService {
    @Autowired
    private WordMapper wordMapper;
    @Override
    public Words findWordById(Integer id) {
        return wordMapper.findWordById(id);
    }

    @Override
    public Words findWordByWord(String word) {
        QueryWrapper<Words> queryWrapper = new QueryWrapper<Words>()
                .eq("word", word);
        return wordMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Words> findAllWordsByBookId(Integer bookId) {
        return wordMapper.findAllWordsByBookId(bookId);
    }
}
