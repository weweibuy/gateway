package com.weweibuy.gateway.manager.model.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PredicateArgsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PredicateArgsExample() {
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

        public Criteria andPredicateIdIsNull() {
            addCriterion("predicate_id is null");
            return (Criteria) this;
        }

        public Criteria andPredicateIdIsNotNull() {
            addCriterion("predicate_id is not null");
            return (Criteria) this;
        }

        public Criteria andPredicateIdEqualTo(Long value) {
            addCriterion("predicate_id =", value, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdNotEqualTo(Long value) {
            addCriterion("predicate_id <>", value, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdGreaterThan(Long value) {
            addCriterion("predicate_id >", value, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdGreaterThanOrEqualTo(Long value) {
            addCriterion("predicate_id >=", value, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdLessThan(Long value) {
            addCriterion("predicate_id <", value, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdLessThanOrEqualTo(Long value) {
            addCriterion("predicate_id <=", value, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdIn(List<Long> values) {
            addCriterion("predicate_id in", values, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdNotIn(List<Long> values) {
            addCriterion("predicate_id not in", values, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdBetween(Long value1, Long value2) {
            addCriterion("predicate_id between", value1, value2, "predicateId");
            return (Criteria) this;
        }

        public Criteria andPredicateIdNotBetween(Long value1, Long value2) {
            addCriterion("predicate_id not between", value1, value2, "predicateId");
            return (Criteria) this;
        }

        public Criteria andArgsNameIsNull() {
            addCriterion("args_name is null");
            return (Criteria) this;
        }

        public Criteria andArgsNameIsNotNull() {
            addCriterion("args_name is not null");
            return (Criteria) this;
        }

        public Criteria andArgsNameEqualTo(String value) {
            addCriterion("args_name =", value, "argsName");
            return (Criteria) this;
        }

        public Criteria andArgsNameNotEqualTo(String value) {
            addCriterion("args_name <>", value, "argsName");
            return (Criteria) this;
        }

        public Criteria andArgsNameGreaterThan(String value) {
            addCriterion("args_name >", value, "argsName");
            return (Criteria) this;
        }

        public Criteria andArgsNameGreaterThanOrEqualTo(String value) {
            addCriterion("args_name >=", value, "argsName");
            return (Criteria) this;
        }

        public Criteria andArgsNameLessThan(String value) {
            addCriterion("args_name <", value, "argsName");
            return (Criteria) this;
        }

        public Criteria andArgsNameLessThanOrEqualTo(String value) {
            addCriterion("args_name <=", value, "argsName");
            return (Criteria) this;
        }

        public Criteria andArgsNameLike(String value) {
            addCriterion("args_name like", value, "argsName");
            return (Criteria) this;
        }

        public Criteria andArgsNameNotLike(String value) {
            addCriterion("args_name not like", value, "argsName");
            return (Criteria) this;
        }

        public Criteria andArgsNameIn(List<String> values) {
            addCriterion("args_name in", values, "argsName");
            return (Criteria) this;
        }

        public Criteria andArgsNameNotIn(List<String> values) {
            addCriterion("args_name not in", values, "argsName");
            return (Criteria) this;
        }

        public Criteria andArgsNameBetween(String value1, String value2) {
            addCriterion("args_name between", value1, value2, "argsName");
            return (Criteria) this;
        }

        public Criteria andArgsNameNotBetween(String value1, String value2) {
            addCriterion("args_name not between", value1, value2, "argsName");
            return (Criteria) this;
        }

        public Criteria andArgsValueIsNull() {
            addCriterion("args_value is null");
            return (Criteria) this;
        }

        public Criteria andArgsValueIsNotNull() {
            addCriterion("args_value is not null");
            return (Criteria) this;
        }

        public Criteria andArgsValueEqualTo(String value) {
            addCriterion("args_value =", value, "argsValue");
            return (Criteria) this;
        }

        public Criteria andArgsValueNotEqualTo(String value) {
            addCriterion("args_value <>", value, "argsValue");
            return (Criteria) this;
        }

        public Criteria andArgsValueGreaterThan(String value) {
            addCriterion("args_value >", value, "argsValue");
            return (Criteria) this;
        }

        public Criteria andArgsValueGreaterThanOrEqualTo(String value) {
            addCriterion("args_value >=", value, "argsValue");
            return (Criteria) this;
        }

        public Criteria andArgsValueLessThan(String value) {
            addCriterion("args_value <", value, "argsValue");
            return (Criteria) this;
        }

        public Criteria andArgsValueLessThanOrEqualTo(String value) {
            addCriterion("args_value <=", value, "argsValue");
            return (Criteria) this;
        }

        public Criteria andArgsValueLike(String value) {
            addCriterion("args_value like", value, "argsValue");
            return (Criteria) this;
        }

        public Criteria andArgsValueNotLike(String value) {
            addCriterion("args_value not like", value, "argsValue");
            return (Criteria) this;
        }

        public Criteria andArgsValueIn(List<String> values) {
            addCriterion("args_value in", values, "argsValue");
            return (Criteria) this;
        }

        public Criteria andArgsValueNotIn(List<String> values) {
            addCriterion("args_value not in", values, "argsValue");
            return (Criteria) this;
        }

        public Criteria andArgsValueBetween(String value1, String value2) {
            addCriterion("args_value between", value1, value2, "argsValue");
            return (Criteria) this;
        }

        public Criteria andArgsValueNotBetween(String value1, String value2) {
            addCriterion("args_value not between", value1, value2, "argsValue");
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

        public Criteria andArgsDescIsNull() {
            addCriterion("args_desc is null");
            return (Criteria) this;
        }

        public Criteria andArgsDescIsNotNull() {
            addCriterion("args_desc is not null");
            return (Criteria) this;
        }

        public Criteria andArgsDescEqualTo(String value) {
            addCriterion("args_desc =", value, "argsDesc");
            return (Criteria) this;
        }

        public Criteria andArgsDescNotEqualTo(String value) {
            addCriterion("args_desc <>", value, "argsDesc");
            return (Criteria) this;
        }

        public Criteria andArgsDescGreaterThan(String value) {
            addCriterion("args_desc >", value, "argsDesc");
            return (Criteria) this;
        }

        public Criteria andArgsDescGreaterThanOrEqualTo(String value) {
            addCriterion("args_desc >=", value, "argsDesc");
            return (Criteria) this;
        }

        public Criteria andArgsDescLessThan(String value) {
            addCriterion("args_desc <", value, "argsDesc");
            return (Criteria) this;
        }

        public Criteria andArgsDescLessThanOrEqualTo(String value) {
            addCriterion("args_desc <=", value, "argsDesc");
            return (Criteria) this;
        }

        public Criteria andArgsDescLike(String value) {
            addCriterion("args_desc like", value, "argsDesc");
            return (Criteria) this;
        }

        public Criteria andArgsDescNotLike(String value) {
            addCriterion("args_desc not like", value, "argsDesc");
            return (Criteria) this;
        }

        public Criteria andArgsDescIn(List<String> values) {
            addCriterion("args_desc in", values, "argsDesc");
            return (Criteria) this;
        }

        public Criteria andArgsDescNotIn(List<String> values) {
            addCriterion("args_desc not in", values, "argsDesc");
            return (Criteria) this;
        }

        public Criteria andArgsDescBetween(String value1, String value2) {
            addCriterion("args_desc between", value1, value2, "argsDesc");
            return (Criteria) this;
        }

        public Criteria andArgsDescNotBetween(String value1, String value2) {
            addCriterion("args_desc not between", value1, value2, "argsDesc");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
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