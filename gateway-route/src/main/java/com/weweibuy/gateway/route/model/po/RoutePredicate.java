package com.weweibuy.gateway.route.model.po;

import java.time.LocalDateTime;

public class RoutePredicate {
    private Long id;

    private String routeId;

    private String predicateId;

    private String predicateName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId == null ? null : routeId.trim();
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