package com.weweibuy.gateway.router.model.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public RouterPredicateExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public RouterPredicateExample orderBy(String ... orderByClauses) {
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
        RouterPredicateExample example = new RouterPredicateExample();
        return example.createCriteria();
    }

    public RouterPredicateExample when(boolean condition, IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }

    public RouterPredicateExample when(boolean condition, IExampleWhen then, IExampleWhen otherwise) {
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

        public Criteria andPredicatePriorityIsNull() {
            addCriterion("predicate_priority is null");
            return (Criteria) this;
        }

        public Criteria andPredicatePriorityIsNotNull() {
            addCriterion("predicate_priority is not null");
            return (Criteria) this;
        }

        public Criteria andPredicatePriorityEqualTo(Integer value) {
            addCriterion("predicate_priority =", value, "predicatePriority");
            return (Criteria) this;
        }

        public Criteria andPredicatePriorityNotEqualTo(Integer value) {
            addCriterion("predicate_priority <>", value, "predicatePriority");
            return (Criteria) this;
        }

        public Criteria andPredicatePriorityGreaterThan(Integer value) {
            addCriterion("predicate_priority >", value, "predicatePriority");
            return (Criteria) this;
        }

        public Criteria andPredicatePriorityGreaterThanOrEqualTo(Integer value) {
            addCriterion("predicate_priority >=", value, "predicatePriority");
            return (Criteria) this;
        }

        public Criteria andPredicatePriorityLessThan(Integer value) {
            addCriterion("predicate_priority <", value, "predicatePriority");
            return (Criteria) this;
        }

        public Criteria andPredicatePriorityLessThanOrEqualTo(Integer value) {
            addCriterion("predicate_priority <=", value, "predicatePriority");
            return (Criteria) this;
        }

        public Criteria andPredicatePriorityIn(List<Integer> values) {
            addCriterion("predicate_priority in", values, "predicatePriority");
            return (Criteria) this;
        }

        public Criteria andPredicatePriorityNotIn(List<Integer> values) {
            addCriterion("predicate_priority not in", values, "predicatePriority");
            return (Criteria) this;
        }

        public Criteria andPredicatePriorityBetween(Integer value1, Integer value2) {
            addCriterion("predicate_priority between", value1, value2, "predicatePriority");
            return (Criteria) this;
        }

        public Criteria andPredicatePriorityNotBetween(Integer value1, Integer value2) {
            addCriterion("predicate_priority not between", value1, value2, "predicatePriority");
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
        private RouterPredicateExample example;

        protected Criteria(RouterPredicateExample example) {
            super();
            this.example = example;
        }

        public RouterPredicateExample example() {
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
        void example(com.weweibuy.gateway.router.model.example.RouterPredicateExample example);
    }
}