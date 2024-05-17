package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.pojo.WrongWord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WrongWordMapper extends BaseMapper<WrongWord> {
}
