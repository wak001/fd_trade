package com.haodaibao.fund.trade.domain;

import java.util.Date;

public class Product {
    private Long id;

    private String fdMhtNo;

    private String productCode;

    private String productName;

    private String productType;

    private Integer settle1Cycle;

    private Integer settle2Cycle;

    private String productStatus;

    private Date createdTime;

    private Date modifiedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFdMhtNo() {
        return fdMhtNo;
    }

    public void setFdMhtNo(String fdMhtNo) {
        this.fdMhtNo = fdMhtNo == null ? null : fdMhtNo.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public Integer getSettle1Cycle() {
        return settle1Cycle;
    }

    public void setSettle1Cycle(Integer settle1Cycle) {
        this.settle1Cycle = settle1Cycle;
    }

    public Integer getSettle2Cycle() {
        return settle2Cycle;
    }

    public void setSettle2Cycle(Integer settle2Cycle) {
        this.settle2Cycle = settle2Cycle;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus == null ? null : productStatus.trim();
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