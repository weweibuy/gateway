package com.weweibuy.gateway.router.model.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RouterFilterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RouterFilterExample() {
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

    public RouterFilterExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public RouterFilterExample orderBy(String ... orderByClauses) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < orderByClauses.length; i++) {
            sb.append(orderByClauses[i]);
            if (i < orderByClauses.length - 1) {
                sb.append(" , ");
            }
        }
        this.setOrderByClause(sb.toString());
        return this;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria(this);
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public static Criteria newAndCreateCriteria() {
        RouterFilterExample example = new RouterFilterExample();
        return example.createCriteria();
    }

    public RouterFilterExample when(boolean condition, IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }

    public RouterFilterExample when(boolean condition, IExampleWhen then, IExampleWhen otherwise) {
        if (condition) {
            then.example(this);
        } else {
            otherwise.example(this);
        }
        return this;
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

        public Criteria andFilterNameIsNull() {
            addCriterion("filter_name is null");
            return (Criteria) this;
        }

        public Criteria andFilterNameIsNotNull() {
            addCriterion("filter_name is not null");
            return (Criteria) this;
        }

        public Criteria andFilterNameEqualTo(String value) {
            addCriterion("filter_name =", value, "filterName");
            return (Criteria) this;
        }

        public Criteria andFilterNameNotEqualTo(String value) {
            addCriterion("filter_name <>", value, "filterName");
            return (Criteria) this;
        }

        public Criteria andFilterNameGreaterThan(String value) {
            addCriterion("filter_name >", value, "filterName");
            return (Criteria) this;
        }

        public Criteria andFilterNameGreaterThanOrEqualTo(String value) {
            addCriterion("filter_name >=", value, "filterName");
            return (Criteria) this;
        }

        public Criteria andFilterNameLessThan(String value) {
            addCriterion("filter_name <", value, "filterName");
            return (Criteria) this;
        }

        public Criteria andFilterNameLessThanOrEqualTo(String value) {
            addCriterion("filter_name <=", value, "filterName");
            return (Criteria) this;
        }

        public Criteria andFilterNameLike(String value) {
            addCriterion("filter_name like", value, "filterName");
            return (Criteria) this;
        }

        public Criteria andFilterNameNotLike(String value) {
            addCriterion("filter_name not like", value, "filterName");
            return (Criteria) this;
        }

        public Criteria andFilterNameIn(List<String> values) {
            addCriterion("filter_name in", values, "filterName");
            return (Criteria) this;
        }

        public Criteria andFilterNameNotIn(List<String> values) {
            addCriterion("filter_name not in", values, "filterName");
            return (Criteria) this;
        }

        public Criteria andFilterNameBetween(String value1, String value2) {
            addCriterion("filter_name between", value1, value2, "filterName");
            return (Criteria) this;
        }

        public Criteria andFilterNameNotBetween(String value1, String value2) {
            addCriterion("filter_name not between", value1, value2, "filterName");
            return (Criteria) this;
        }

        public Criteria andFilterPriorityIsNull() {
            addCriterion("filter_priority is null");
            return (Criteria) this;
        }

        public Criteria andFilterPriorityIsNotNull() {
            addCriterion("filter_priority is not null");
            return (Criteria) this;
        }

        public Criteria andFilterPriorityEqualTo(Integer value) {
            addCriterion("filter_priority =", value, "filterPriority");
            return (Criteria) this;
        }

        public Criteria andFilterPriorityNotEqualTo(Integer value) {
            addCriterion("filter_priority <>", value, "filterPriority");
            return (Criteria) this;
        }

        public Criteria andFilterPriorityGreaterThan(Integer value) {
            addCriterion("filter_priority >", value, "filterPriority");
            return (Criteria) this;
        }

        public Criteria andFilterPriorityGreaterThanOrEqualTo(Integer value) {
            addCriterion("filter_priority >=", value, "filterPriority");
            return (Criteria) this;
        }

        public Criteria andFilterPriorityLessThan(Integer value) {
            addCriterion("filter_priority <", value, "filterPriority");
            return (Criteria) this;
        }

        public Criteria andFilterPriorityLessThanOrEqualTo(Integer value) {
            addCriterion("filter_priority <=", value, "filterPriority");
            return (Criteria) this;
        }

        public Criteria andFilterPriorityIn(List<Integer> values) {
            addCriterion("filter_priority in", values, "filterPriority");
            return (Criteria) this;
        }

        public Criteria andFilterPriorityNotIn(List<Integer> values) {
            addCriterion("filter_priority not in", values, "filterPriority");
            return (Criteria) this;
        }

        public Criteria andFilterPriorityBetween(Integer value1, Integer value2) {
            addCriterion("filter_priority between", value1, value2, "filterPriority");
            return (Criteria) this;
        }

        public Criteria andFilterPriorityNotBetween(Integer value1, Integer value2) {
            addCriterion("filter_priority not between", value1, value2, "filterPriority");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNull() {
            addCriterion("is_delete is null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNotNull() {
            addCriterion("is_delete is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteEqualTo(Boolean value) {
            addCriterion("is_delete =", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotEqualTo(Boolean value) {
            addCriterion("is_delete <>", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThan(Boolean value) {
            addCriterion("is_delete >", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_delete >=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThan(Boolean value) {
            addCriterion("is_delete <", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThanOrEqualTo(Boolean value) {
            addCriterion("is_delete <=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIn(List<Boolean> values) {
            addCriterion("is_delete in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotIn(List<Boolean> values) {
            addCriterion("is_delete not in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delete between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delete not between", value1, value2, "isDelete");
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
        private RouterFilterExample example;

        protected Criteria(RouterFilterExample example) {
            super();
            this.example = example;
        }

        public RouterFilterExample example() {
            return this.example;
        }

        @Deprecated
        public Criteria andIf(boolean ifAdd, ICriteriaAdd add) {
            if (ifAdd) {
                add.add(this);
            }
            return this;
        }

        public Criteria when(boolean condition, ICriteriaWhen then) {
            if (condition) {
                then.criteria(this);
            }
            return this;
        }

        public Criteria when(boolean condition, ICriteriaWhen then, ICriteriaWhen otherwise) {
            if (condition) {
                then.criteria(this);
            } else {
                otherwise.criteria(this);
            }
            return this;
        }

        @Deprecated
        public interface ICriteriaAdd {
            Criteria add(Criteria add);
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

    public interface ICriteriaWhen {
        void criteria(Criteria criteria);
    }

    public interface IExampleWhen {
        void example(com.weweibuy.gateway.router.model.example.RouterFilterExample example);
    }
}