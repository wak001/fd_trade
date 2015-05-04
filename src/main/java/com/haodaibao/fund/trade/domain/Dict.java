package com.haodaibao.fund.trade.domain;

import java.util.Date;

public class Dict {
    private Long id;

    private String dictType;

    private String dictCode;

    private String dictValue1;

    private String dictValue2;

    private String dictValue3;

    private String remark;

    private Date createdTime;

    private Date modifiedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType == null ? null : dictType.trim();
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode == null ? null : dictCode.trim();
    }

    public String getDictValue1() {
        return dictValue1;
    }

    public void setDictValue1(String dictValue1) {
        this.dictValue1 = dictValue1 == null ? null : dictValue1.trim();
    }

    public String getDictValue2() {
        return dictValue2;
    }

    public void setDictValue2(String dictValue2) {
        this.dictValue2 = dictValue2 == null ? null : dictValue2.trim();
    }

    public String getDictValue3() {
        return dictValue3;
    }

    public void setDictValue3(String dictValue3) {
        this.dictValue3 = dictValue3 == null ? null : dictValue3.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}