package com.haodaibao.fund.trade.dao;

import com.haodaibao.fund.trade.domain.PurchaseOrder;

public interface PurchaseOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PurchaseOrder record);

    int insertSelective(PurchaseOrder record);

    PurchaseOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseOrder record);

    int updateByPrimaryKey(PurchaseOrder record);
}