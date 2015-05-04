package com.haodaibao.fund.trade.dao;

import com.haodaibao.fund.trade.domain.CustomerSign;

public interface CustomerSignMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CustomerSign record);

    int insertSelective(CustomerSign record);

    CustomerSign selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerSign record);

    int updateByPrimaryKey(CustomerSign record);
}