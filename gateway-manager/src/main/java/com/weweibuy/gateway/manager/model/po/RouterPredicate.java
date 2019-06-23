package com.weweibuy.gateway.manager.model.po;

import java.util.Date;

public class RouterPredicate {
    private Long id;

    private String routerId;

    private String predicateId;

    private String predicateName;

    private Long dictId;

    private String dictType;

    private String predicateDesc;

    private Boolean isUse;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRouterId() {
        return routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId == null ? null : routerId.trim();
    }

    public String getPredicateId() {
        return predicateId;
    }

    public void setPredicateId(String predicateId) {
        this.predicateId = predicateId == null ? null : predicateId.trim();
    }

    public String getPredicateName() {
        return predicateName;
    }

    public void setPredicateName(String predicateName) {
        this.predicateName = predicateName == null ? null : predicateName.trim();
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

    public String getPredicateDesc() {
        return predicateDesc;
    }

    public void setPredicateDesc(String predicateDesc) {
        this.predicateDesc = predicateDesc == null ? null : predicateDesc.trim();
    }

    public Boolean getIsUse() {
        return isUse;
    }

    public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
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