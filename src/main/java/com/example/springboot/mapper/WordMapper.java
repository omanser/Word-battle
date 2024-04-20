package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.pojo.Words;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@Mapper
public interface WordMapper extends BaseMapper<Words> {
    Words findWordById(Integer id);

    //@Select("select * from words w, wordbook_words b where w.id = b.word_id and b.wordbook_id = #{bookId} limit 10")
    List<Words> findAllWordsByBookId(Integer bookId);
}
