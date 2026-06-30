package com.m42.custody.mapper;

import com.m42.custody.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
//    int insert(UserEntity user);
//    int insertBatch(List<UserEntity> userList);
//    int delete(UserEntity user);
//    int updateById(UserEntity user);
//    UserEntity selectById(Long id);

    int insertUser(UserEntity user);

    List<UserEntity> selectUserList(UserEntity user);

    int deleteBatch(@Param("idList") List<Long> idList);

    int updateUserSelective(UserEntity user);
}
