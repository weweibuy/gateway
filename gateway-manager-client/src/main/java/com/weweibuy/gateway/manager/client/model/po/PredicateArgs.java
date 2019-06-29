package com.weweibuy.gateway.manager.client.model.po;

import java.util.Date;

public class PredicateArgs {
    private Long id;

    private String predicateId;

    private String predicateArgId;

    private String argsName;

    private String argsValue;

    private Long dictId;

    private String dictType;

    private String argsDesc;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPredicateId() {
        return predicateId;
    }

    public void setPredicateId(String predicateId) {
        this.predicateId = predicateId == null ? null : predicateId.trim();
    }

    public String getPredicateArgId() {
        return predicateArgId;
    }

    public void setPredicateArgId(String predicateArgId) {
        this.predicateArgId = predicateArgId == null ? null : predicateArgId.trim();
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