package com.haodaibao.fund.trade.dao;

import com.haodaibao.fund.trade.domain.RevokeOrder;

public interface RevokeOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RevokeOrder record);

    int insertSelective(RevokeOrder record);

    RevokeOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RevokeOrder record);

    int updateByPrimaryKey(RevokeOrder record);
}