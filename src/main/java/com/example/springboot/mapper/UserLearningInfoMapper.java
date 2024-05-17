package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.pojo.UserLearningInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLearningInfoMapper extends BaseMapper<UserLearningInfo> {
}
