package com.weweibuy.gateway.manager.model.po;

import java.util.Date;

public class FilterArgs {
    private Long id;

    private Long filterId;

    private String argsName;

    private String argsValue;

    private Long dictId;

    private String dictType;

    private String argsDesc;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFilterId() {
        return filterId;
    }

    public void setFilterId(Long filterId) {
        this.filterId = filterId;
    }

    public String getArgsName() {
        return argsName;
    }

    public void setArgsName(String argsName) {
        this.argsName = argsName == null ? null : argsName.trim();
    }

    public String getArgsValue() {
        return argsValue;
    }

    public void setArgsValue(String argsValue) {
        this.argsValue = argsValue == null ? null : argsValue.trim();
    }

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType == null ? null : dictType.trim();
    }

    public String getArgsDesc() {
        return argsDesc;
    }

    public void setArgsDesc(String argsDesc) {
        this.argsDesc = argsDesc == null ? null : argsDesc.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}