package com.haodaibao.fund.trade.dao;

import com.haodaibao.fund.trade.domain.SignOrder;

public interface SignOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SignOrder record);

    int insertSelective(SignOrder record);

    SignOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SignOrder record);

    int updateByPrimaryKey(SignOrder record);
}