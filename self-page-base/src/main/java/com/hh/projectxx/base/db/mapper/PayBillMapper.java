package com.hh.projectxx.base.db.mapper;


import com.hh.projectxx.base.db.entity.PayBill;

public interface PayBillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PayBill record);

    int insertSelective(PayBill record);

    PayBill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayBill record);

    int updateByPrimaryKey(PayBill record);
}