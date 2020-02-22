package com.weweibuy.gateway.route.model.po;

import java.time.LocalDateTime;

public class RoutePredicateArgs {
    private Long id;

    private String predicateId;

    private String predicateArgId;

    private String argsName;

    private String argsValue;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}