package com.weweibuy.gateway.route.model.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RouteFilterArgsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RouteFilterArgsExample() {
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

        public Criteria andFilterIdIsNull() {
            addCriterion("filter_id is null");
            return (Criteria) this;
        }

        public Criteria andFilterIdIsNotNull() {
            addCriterion("filter_id is not null");
            return (Criteria) this;
        }

        public Criteria andFilterIdEqualTo(String value) {
            addCriterion("filter_id =", value, "filterId");
            return (Criteria) this;
        }

        public Criteria andFilterIdNotEqualTo(String value) {
            addCriterion("filter_id <>", value, "filterId");
            return (Criteria) this;
        }

        public Criteria andFilterIdGreaterThan(String value) {
            addCriterion("filter_id >", value, "filterId");
            return (Criteria) this;
        }

        public Criteria andFilterIdGreaterThanOrEqualTo(String value) {
            addCriterion("filter_id >=", value, "filterId");
            return (Criteria) this;
        }

        public Criteria andFilterIdLessThan(String value) {
            addCriterion("filter_id <", value, "filterId");
            return (Criteria) this;
        }

        public Criteria andFilterIdLessThanOrEqualTo(String value) {
            addCriterion("filter_id <=", value, "filterId");
            return (Criteria) this;
        }

        public Criteria andFilterIdLike(String value) {
            addCriterion("filter_id like", value, "filterId");
            return (Criteria) this;
        }

        public Criteria andFilterIdNotLike(String value) {
            addCriterion("filter_id not like", value, "filterId");
            return (Criteria) this;
        }

        public Criteria andFilterIdIn(List<String> values) {
            addCriterion("filter_id in", values, "filterId");
            return (Criteria) this;
        }

        public Criteria andFilterIdNotIn(List<String> values) {
            addCriterion("filter_id not in", values, "filterId");
            return (Criteria) this;
        }

        public Criteria andFilterIdBetween(String value1, String value2) {
            addCriterion("filter_id between", value1, value2, "filterId");
            return (Criteria) this;
        }

        public Criteria andFilterIdNotBetween(String value1, String value2) {
            addCriterion("filter_id not between", value1, value2, "filterId");
            return (Criteria) this;
        }

        public Criteria andFilterArgsIdIsNull() {
            addCriterion("filter_args_id is null");
            return (Criteria) this;
        }

        public Criteria andFilterArgsIdIsNotNull() {
            addCriterion("filter_args_id is not null");
            return (Criteria) this;
        }

        public Criteria andFilterArgsIdEqualTo(String value) {
            addCriterion("filter_args_id =", value, "filterArgsId");
            return (Criteria) this;
        }

        public Criteria andFilterArgsIdNotEqualTo(String value) {
            addCriterion("filter_args_id <>", value, "filterArgsId");
            return (Criteria) this;
        }

        public Criteria andFilterArgsIdGreaterThan(String value) {
            addCriterion("filter_args_id >", value, "filterArgsId");
            return (Criteria) this;
        }

        public Criteria andFilterArgsIdGreaterThanOrEqualTo(String value) {
            addCriterion("filter_args_id >=", value, "filterArgsId");
            return (Criteria) this;
        }

        public Criteria andFilterArgsIdLessThan(String value) {
            addCriterion("filter_args_id <", value, "filterArgsId");
            return (Criteria) this;
        }

        public Criteria andFilterArgsIdLessThanOrEqualTo(String value) {
            addCriterion("filter_args_id <=", value, "filterArgsId");
            return (Criteria) this;
        }

        public Criteria andFilterArgsIdLike(String value) {
            addCriterion("filter_args_id like", value, "filterArgsId");
            return (Criteria) this;
        }

        public Criteria andFilterArgsIdNotLike(String value) {
            addCriterion("filter_args_id not like", value, "filterArgsId");
            return (Criteria) this;
        }

        public Criteria andFilterArgsIdIn(List<String> values) {
            addCriterion("filter_args_id in", values, "filterArgsId");
            return (Criteria) this;
        }

        public Criteria andFilterArgsIdNotIn(List<String> values) {
            addCriterion("filter_args_id not in", values, "filterArgsId");
            return (Criteria) this;
        }

        public Criteria andFilterArgsIdBetween(String value1, String value2) {
            addCriterion("filter_args_id between", value1, value2, "filterArgsId");
            return (Criteria) this;
        }

        public Criteria andFilterArgsIdNotBetween(String value1, String value2) {
            addCriterion("filter_args_id not between", value1, value2, "filterArgsId");
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

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(LocalDateTime value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(LocalDateTime value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(LocalDateTime value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<LocalDateTime> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
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

        public Criteria andUpdateTimeEqualTo(LocalDateTime value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(LocalDateTime value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(LocalDateTime value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<LocalDateTime> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
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