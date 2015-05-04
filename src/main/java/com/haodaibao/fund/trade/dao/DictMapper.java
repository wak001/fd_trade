package com.haodaibao.fund.trade.dao;

import com.haodaibao.fund.trade.domain.Dict;

public interface DictMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);
}