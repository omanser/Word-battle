package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.pojo.NewWord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NewWordMapper extends BaseMapper<NewWord> {
}
