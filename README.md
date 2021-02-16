# gateway
  SpringCloud Gateway 的样例工程,使用响应式编程,包含功能:   
  - 鉴权  
  - 数据级权限控制  
  - 验签  
  - 动态路由  
  - 日志与APM  
  - 流控,降级  

### 先下载 https://github.com/weweibuy/weweibuy-framework  mvn install
 
### 1. 鉴权:
  支持功能:
  - 用户鉴权  
  - App鉴权  
  参考: [AuthenticationGatewayFilterFactory](gateway-filter/src/main/java/com/weweibuy/gateway/route/filter/authorization/AppAuthenticationGatewayFilterFactory.java)

### 2. 数据级权限控制:
  参考: [DataPermissionGatewayFilterFactory](gateway-filter/src/main/java/com/weweibuy/gateway/route/filter/authorization/DataPermissionGatewayFilterFactory.java)


### 3. 验签:
  对业务请求参数无侵入,无需特意改变请求/响应报文的验签     
  支持功能:
  - GET请求验签  
  - Json请求验签  
  - application/x-www-form-urlencoded 验签  
  - 文件上传请求验签

  参考: [SystemRequestParamGatewayFilterFactory](gateway-filter/src/main/java/com/weweibuy/gateway/route/filter/sign/SystemRequestParamGatewayFilterFactory.java)
        [VerifySignatureGatewayFilterFactory](gateway-filter/src/main/java/com/weweibuy/gateway/route/filter/sign/VerifySignatureGatewayFilterFactory.java)

### 4. 动态路由:
  基于JDBC + Mybatis 动态路由,路由/断言/过滤参数灵活配置:   
  数据库脚本: 
  [router.sql](script/router.sql)    
  功能实现:  
  参考: [JdbcRouteDefinitionLocator](gateway-router/src/main/java/com/weweibuy/gateway/router/dynamic/JdbcRouterDefinitionLocator.java)
        [JdbcRouterManger](gateway-router/src/main/java/com/weweibuy/gateway/router/dynamic/JdbcRouterManger.java)  
  查询与刷新路由服务端点:
      [JdbcRouterManger](gateway-router/src/main/java/com/weweibuy/gateway/router/endpoint/RouterManagerEndpoint.java)  

### 5. 日志与 APM
  基础的日志输出功能,类似AccessLog;   
  日志参考: [AccessLogFilter](gateway-filter/src/main/java/com/weweibuy/gateway/route/filter/log/AccessLogFilter.java)   
  APM参考:  [AddTraceHeaderGatewayFilterFactory](gateway-filter/src/main/java/com/weweibuy/gateway/route/filter/trace/AddTraceHeaderGatewayFilterFactory.java)   

### 6. 流控,降级
  参考: [Sentinel](https://github.com/alibaba/Sentinel/wiki/%E7%BD%91%E5%85%B3%E9%99%90%E6%B5%81)
  [SentinelGatewayFilterFactory](gateway-filter/src/main/java/com/weweibuy/gateway/route/filter/sentinel/SentinelGatewayFilterFactory.java)


### 7. 负载均衡
  ILoadBalancer对服务有自己的缓存,定时(默认30s)到注册中心更新缓存的服务信息. 当服务下线时,如果本地缓存不清除,将导致将请求转发到一个不可用的服务上,直到下次定时更新服务.
  可以通过注册中心监听服务健康状态(例如consul的watches),通知一个MQ服务,gateway监听MQ消息刷新本地缓存服务信息
  参考:  [RocketServerChangeListener](gateway-lb/src/main/java/com/weweibuy/gateway/lb/mq/RocketServerChangeListener.java)  

   
