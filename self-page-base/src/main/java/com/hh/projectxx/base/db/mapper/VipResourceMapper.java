package com.hh.projectxx.base.db.mapper;


import com.hh.projectxx.base.db.entity.VipResource;
import com.hh.projectxx.base.db.entity.VipResourceWithBLOBs;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface VipResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VipResourceWithBLOBs record);

    int insertSelective(VipResourceWithBLOBs record);

    VipResourceWithBLOBs selectByPrimaryKey(Integer id);
    
    List<VipResource> selectByRowbouds(int type, RowBounds rowBounds);
    
    public int countAll(int type);

    int updateByPrimaryKeySelective(VipResourceWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(VipResourceWithBLOBs record);

    int updateByPrimaryKey(VipResource record);
}