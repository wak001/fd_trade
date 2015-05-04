package com.haodaibao.fund.trade.dao;

import com.haodaibao.fund.trade.domain.UnSignOrder;

public interface UnSignOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UnSignOrder record);

    int insertSelective(UnSignOrder record);

    UnSignOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UnSignOrder record);

    int updateByPrimaryKey(UnSignOrder record);
}