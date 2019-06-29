package com.weweibuy.gateway.manager.client.model.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RouterPredicateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RouterPredicateExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andRouterIdIsNull() {
            addCriterion("router_id is null");
            return (Criteria) this;
        }

        public Criteria andRouterIdIsNotNull() {
            addCriterion("router_id is not null");
            return (Criteria) this;
        }

        public Criteria andRouterIdEqualTo(String value) {
            addCriterion("router_id =", value, "routerId");
            return (Criteria) this;
        }

        public Criteria andRouterIdNotEqualTo(String value) {
            addCriterion("router_id <>", value, "routerId");
            return (Criteria) this;
        }

        public Criteria andRouterIdGreaterThan(String value) {
            addCriterion("router_id >", value, "routerId");
            return (Criteria) this;
        }

        public Criteria andRouterIdGreaterThanOrEqualTo(String value) {
            addCriterion("router_id >=", value, "routerId");
            return (Criteria) this;
        }

        public Criteria andRouterIdLessThan(String value) {
            addCriterion("router_id <", value, "routerId");
            return (Criteria) this;
        }

        public Criteria andRouterIdLessThanOrEqualTo(String value) {
            addCriterion("router_id <=", value, "routerId");
            return (Criteria) this;
        }

        public Criteria andRouterIdLike(String value) {
            addCriterion("router_id like", value, "routerId");
            return (Criteria) this;
        }

        public Criteria andRouterIdNotLike(String value) {
            addCriterion("router_id not like", value, "routerId");
            return (Criteria) this;
        }

        public Criteria andRouterIdIn(List<String> values) {
            addCriterion("router_id in", values, "routerId");
            return (Criteria) this;
        }

        public Criteria andRouterIdNotIn(List<String> values) {
            addCriterion("router_id not in", values, "routerId");
            return (Criteria) this;
        }

        public Criteria andRouterIdBetween(String value1, String value2) {
            addCriterion("router_id between", value1, value2, "routerId");
            return (Criteria) this;
        }

        public Criteria andRouterIdNotBetween(String value1, String value2) {
            addCriterion("router_id not between", value1, value2, "routerId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdIsNull() {
            addCriterion("predicate_id is null");
            return (Criteria) this;
        }

        public Criteria andPredicateIdIsNotNull() {
            addCriterion("predicate_id is not null");
            return (Criteria) this;
        }

        public Criteria andPredicateIdEqualTo(String value) {
            addCriterion("predicate_id =", value, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdNotEqualTo(String value) {
            addCriterion("predicate_id <>", value, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdGreaterThan(String value) {
            addCriterion("predicate_id >", value, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdGreaterThanOrEqualTo(String value) {
            addCriterion("predicate_id >=", value, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdLessThan(String value) {
            addCriterion("predicate_id <", value, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdLessThanOrEqualTo(String value) {
            addCriterion("predicate_id <=", value, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdLike(String value) {
            addCriterion("predicate_id like", value, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdNotLike(String value) {
            addCriterion("predicate_id not like", value, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdIn(List<String> values) {
            addCriterion("predicate_id in", values, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdNotIn(List<String> values) {
            addCriterion("predicate_id not in", values, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdBetween(String value1, String value2) {
            addCriterion("predicate_id between", value1, value2, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdNotBetween(String value1, String value2) {
            addCriterion("predicate_id not between", value1, value2, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateNameIsNull() {
            addCriterion("predicate_name is null");
            return (Criteria) this;
        }

        public Criteria andPredicateNameIsNotNull() {
            addCriterion("predicate_name is not null");
            return (Criteria) this;
        }

        public Criteria andPredicateNameEqualTo(String value) {
            addCriterion("predicate_name =", value, "predicateName");
            return (Criteria) this;
        }

        public Criteria andPredicateNameNotEqualTo(String value) {
            addCriterion("predicate_name <>", value, "predicateName");
            return (Criteria) this;
        }

        public Criteria andPredicateNameGreaterThan(String value) {
            addCriterion("predicate_name >", value, "predicateName");
            return (Criteria) this;
        }

        public Criteria andPredicateNameGreaterThanOrEqualTo(String value) {
            addCriterion("predicate_name >=", value, "predicateName");
            return (Criteria) this;
        }

        public Criteria andPredicateNameLessThan(String value) {
            addCriterion("predicate_name <", value, "predicateName");
            return (Criteria) this;
        }

        public Criteria andPredicateNameLessThanOrEqualTo(String value) {
            addCriterion("predicate_name <=", value, "predicateName");
            return (Criteria) this;
        }

        public Criteria andPredicateNameLike(String value) {
            addCriterion("predicate_name like", value, "predicateName");
            return (Criteria) this;
        }

        public Criteria andPredicateNameNotLike(String value) {
            addCriterion("predicate_name not like", value, "predicateName");
            return (Criteria) this;
        }

        public Criteria andPredicateNameIn(List<String> values) {
            addCriterion("predicate_name in", values, "predicateName");
            return (Criteria) this;
        }

        public Criteria andPredicateNameNotIn(List<String> values) {
            addCriterion("predicate_name not in", values, "predicateName");
            return (Criteria) this;
        }

        public Criteria andPredicateNameBetween(String value1, String value2) {
            addCriterion("predicate_name between", value1, value2, "predicateName");
            return (Criteria) this;
        }

        public Criteria andPredicateNameNotBetween(String value1, String value2) {
            addCriterion("predicate_name not between", value1, value2, "predicateName");
            return (Criteria) this;
        }

        public Criteria andDictIdIsNull() {
            addCriterion("dict_id is null");
            return (Criteria) this;
        }

        public Criteria andDictIdIsNotNull() {
            addCriterion("dict_id is not null");
            return (Criteria) this;
        }

        public Criteria andDictIdEqualTo(Long value) {
            addCriterion("dict_id =", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdNotEqualTo(Long value) {
            addCriterion("dict_id <>", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdGreaterThan(Long value) {
            addCriterion("dict_id >", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdGreaterThanOrEqualTo(Long value) {
            addCriterion("dict_id >=", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdLessThan(Long value) {
            addCriterion("dict_id <", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdLessThanOrEqualTo(Long value) {
            addCriterion("dict_id <=", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdIn(List<Long> values) {
            addCriterion("dict_id in", values, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdNotIn(List<Long> values) {
            addCriterion("dict_id not in", values, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdBetween(Long value1, Long value2) {
            addCriterion("dict_id between", value1, value2, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdNotBetween(Long value1, Long value2) {
            addCriterion("dict_id not between", value1, value2, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictTypeIsNull() {
            addCriterion("dict_type is null");
            return (Criteria) this;
        }

        public Criteria andDictTypeIsNotNull() {
            addCriterion("dict_type is not null");
            return (Criteria) this;
        }

        public Criteria andDictTypeEqualTo(String value) {
            addCriterion("dict_type =", value, "dictType");
            return (Criteria) this;
        }

        public Criteria andDictTypeNotEqualTo(String value) {
            addCriterion("dict_type <>", value, "dictType");
            return (Criteria) this;
        }

        public Criteria andDictTypeGreaterThan(String value) {
            addCriterion("dict_type >", value, "dictType");
            return (Criteria) this;
        }

        public Criteria andDictTypeGreaterThanOrEqualTo(String value) {
            addCriterion("dict_type >=", value, "dictType");
            return (Criteria) this;
        }

        public Criteria andDictTypeLessThan(String value) {
            addCriterion("dict_type <", value, "dictType");
            return (Criteria) this;
        }

        public Criteria andDictTypeLessThanOrEqualTo(String value) {
            addCriterion("dict_type <=", value, "dictType");
            return (Criteria) this;
        }

        public Criteria andDictTypeLike(String value) {
            addCriterion("dict_type like", value, "dictType");
            return (Criteria) this;
        }

        public Criteria andDictTypeNotLike(String value) {
            addCriterion("dict_type not like", value, "dictType");
            return (Criteria) this;
        }

        public Criteria andDictTypeIn(List<String> values) {
            addCriterion("dict_type in", values, "dictType");
            return (Criteria) this;
        }

        public Criteria andDictTypeNotIn(List<String> values) {
            addCriterion("dict_type not in", values, "dictType");
            return (Criteria) this;
        }

        public Criteria andDictTypeBetween(String value1, String value2) {
            addCriterion("dict_type between", value1, value2, "dictType");
            return (Criteria) this;
        }

        public Criteria andDictTypeNotBetween(String value1, String value2) {
            addCriterion("dict_type not between", value1, value2, "dictType");
            return (Criteria) this;
        }

        public Criteria andPredicateDescIsNull() {
            addCriterion("predicate_desc is null");
            return (Criteria) this;
        }

        public Criteria andPredicateDescIsNotNull() {
            addCriterion("predicate_desc is not null");
            return (Criteria) this;
        }

        public Criteria andPredicateDescEqualTo(String value) {
            addCriterion("predicate_desc =", value, "predicateDesc");
            return (Criteria) this;
        }

        public Criteria andPredicateDescNotEqualTo(String value) {
            addCriterion("predicate_desc <>", value, "predicateDesc");
            return (Criteria) this;
        }

        public Criteria andPredicateDescGreaterThan(String value) {
            addCriterion("predicate_desc >", value, "predicateDesc");
            return (Criteria) this;
        }

        public Criteria andPredicateDescGreaterThanOrEqualTo(String value) {
            addCriterion("predicate_desc >=", value, "predicateDesc");
            return (Criteria) this;
        }

        public Criteria andPredicateDescLessThan(String value) {
            addCriterion("predicate_desc <", value, "predicateDesc");
            return (Criteria) this;
        }

        public Criteria andPredicateDescLessThanOrEqualTo(String value) {
            addCriterion("predicate_desc <=", value, "predicateDesc");
            return (Criteria) this;
        }

        public Criteria andPredicateDescLike(String value) {
            addCriterion("predicate_desc like", value, "predicateDesc");
            return (Criteria) this;
        }

        public Criteria andPredicateDescNotLike(String value) {
            addCriterion("predicate_desc not like", value, "predicateDesc");
            return (Criteria) this;
        }

        public Criteria andPredicateDescIn(List<String> values) {
            addCriterion("predicate_desc in", values, "predicateDesc");
            return (Criteria) this;
        }

        public Criteria andPredicateDescNotIn(List<String> values) {
            addCriterion("predicate_desc not in", values, "predicateDesc");
            return (Criteria) this;
        }

        public Criteria andPredicateDescBetween(String value1, String value2) {
            addCriterion("predicate_desc between", value1, value2, "predicateDesc");
            return (Criteria) this;
        }

        public Criteria andPredicateDescNotBetween(String value1, String value2) {
            addCriterion("predicate_desc not between", value1, value2, "predicateDesc");
            return (Criteria) this;
        }

        public Criteria andIsUseIsNull() {
            addCriterion("is_use is null");
            return (Criteria) this;
        }

        public Criteria andIsUseIsNotNull() {
            addCriterion("is_use is not null");
            return (Criteria) this;
        }

        public Criteria andIsUseEqualTo(Boolean value) {
            addCriterion("is_use =", value, "isUse");
            return (Criteria) this;
        }

        public Criteria andIsUseNotEqualTo(Boolean value) {
            addCriterion("is_use <>", value, "isUse");
            return (Criteria) this;
        }

        public Criteria andIsUseGreaterThan(Boolean value) {
            addCriterion("is_use >", value, "isUse");
            return (Criteria) this;
        }

        public Criteria andIsUseGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_use >=", value, "isUse");
            return (Criteria) this;
        }

        public Criteria andIsUseLessThan(Boolean value) {
            addCriterion("is_use <", value, "isUse");
            return (Criteria) this;
        }

        public Criteria andIsUseLessThanOrEqualTo(Boolean value) {
            addCriterion("is_use <=", value, "isUse");
            return (Criteria) this;
        }

        public Criteria andIsUseIn(List<Boolean> values) {
            addCriterion("is_use in", values, "isUse");
            return (Criteria) this;
        }

        public Criteria andIsUseNotIn(List<Boolean> values) {
            addCriterion("is_use not in", values, "isUse");
            return (Criteria) this;
        }

        public Criteria andIsUseBetween(Boolean value1, Boolean value2) {
            addCriterion("is_use between", value1, value2, "isUse");
            return (Criteria) this;
        }

        public Criteria andIsUseNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_use not between", value1, value2, "isUse");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}