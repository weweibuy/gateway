
/*==============================================================*/
/* Table: gateway_route                                         */
/*==============================================================*/
create table gateway_route
(
   id                   bigint unsigned not null auto_increment comment 'id',
   route_id             varchar(30) not null comment '路由id',
   system_id            varchar(30) not null default '' comment '路由系统id',
   system_name          varchar(50) not null default '' comment '路由系统名称',
   route_uri            varchar(50) not null default '' comment 'uri',
   route_priority       int not null default 0 comment '优先级',
   is_delete            tinyint(1) unsigned not null default 0 comment '是否删除',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id),
   unique key uk_route_id (route_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='路由表';



/*==============================================================*/
/* Table: route_predicate                                       */
/*==============================================================*/
create table route_predicate
(
   id                   bigint unsigned not null auto_increment comment 'id',
   route_id             varchar(30) not null default '' comment 'route_id 关联路由表',
   predicate_id         varchar(30) not null comment '断言id',
   predicate_name       varchar(50) not null default '' comment '断言名称',
   predicate_priority   int not null default 0 comment '断言优先级',
   is_delete            tinyint(1) unsigned not null default 0 comment '是否删除',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id),
   unique key uk_predicate_id (predicate_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='路由断言表';



/*==============================================================*/
/* Table: route_predicate_args                                  */
/*==============================================================*/
create table route_predicate_args
(
   id                   bigint unsigned not null auto_increment comment 'id',
   predicate_id         varchar(30) not null default '' comment '关联断言表 predicate_id',
   predicate_arg_id     varchar(30) not null comment '断言参数id',
   args_name            varchar(100) not null comment '参数名',
   args_value           varchar(255) not null comment '参数值',
   is_delete            tinyint(1) unsigned not null default 0 comment '是否删除',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id),
   unique key uk_predicate_arg_id (predicate_arg_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='断言参数表';



/*==============================================================*/
/* Table: route_filter                                          */
/*==============================================================*/
create table route_filter
(
   id                   bigint unsigned not null auto_increment comment 'id',
   route_id             varchar(30) not null default '' comment '关联路由表 router_id',
   filter_id            varchar(30) not null comment '过滤器id',
   filter_name          varchar(100) not null default '' comment '过滤器名称',
   filter_priority      int not null default 0 comment '过滤器优先级',
   is_delete            tinyint(1) unsigned not null default 0 comment '是否删除',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id),
   unique key uk_filter_id (filter_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='路由过滤器表';




/*==============================================================*/
/* Table: route_filter_args                                     */
/*==============================================================*/
create table route_filter_args
(
   id                   bigint unsigned not null auto_increment comment 'id',
   filter_id            varchar(30) not null default '' comment '关联过滤器表 filter_id',
   filter_args_id       varchar(30) not null comment '过滤器参数id',
   args_name            varchar(100) not null comment '参数名',
   args_value           varchar(255) not null comment '参数值',
   is_delete            tinyint(1) unsigned not null default 0 comment '是否删除',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id),
   unique key uk_filter_args_id (filter_args_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='路由过滤器参数表';






