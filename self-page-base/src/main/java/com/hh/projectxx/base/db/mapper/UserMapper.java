package com.hh.projectxx.base.db.mapper;


import com.hh.projectxx.base.db.entity.User;

public interface UserMapper {
    int delete(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);
    
    User selectByPassport(String passport);
    
    int updateByPrimaryKeySelective(User record);
    
    int countAll();
}