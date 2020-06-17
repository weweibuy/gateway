# gateway
  springCloud gateway 的样例工程,包含功能:   
 授权  
 验签  
 动态路由  
 日志  
 流控,降级  

### 先下载 https://github.com/weweibuy/weweibuy-framework  mvn install
 
### 1. 授权:
  参考: [AuthenticationGatewayFilterFactory](gateway-filter/src/main/java/com/weweibuy/gateway/route/filter/authorization/AuthenticationGatewayFilterFactory.java)

 
### 2. 验签:
  参考: [SystemRequestParamGatewayFilterFactory](gateway-filter/src/main/java/com/weweibuy/gateway/route/filter/sign/SystemRequestParamGatewayFilterFactory.java)
        [VerifySignatureGatewayFilterFactory](gateway-filter/src/main/java/com/weweibuy/gateway/route/filter/sign/VerifySignatureGatewayFilterFactory.java)

### 3. 动态路由:
  基于JDBC + mybatis 实现:
  参考: [JdbcRouteDefinitionLocator](gateway-route/src/main/java/com/weweibuy/gateway/route/dynamic/JdbcRouteDefinitionLocator.java)  
        [JdbcRouterManger](gateway-route/src/main/java/com/weweibuy/gateway/route/dynamic/JdbcRouterManger.java)  
  查询与刷新路由服务端点:
      [JdbcRouterManger](gateway-route/src/main/java/com/weweibuy/gateway/route/endpoint/RouteManagerEndpoint.java)  

### 4. 日志
  参考: [AccessLogFilter](gateway-filter/src/main/java/com/weweibuy/gateway/route/filter/record/AccessLogFilter.java)

### 5. 流控,降级
  参考: [Sentinel](https://github.com/alibaba/Sentinel/wiki/%E7%BD%91%E5%85%B3%E9%99%90%E6%B5%81)

### 6. 负载均衡
  ILoadBalancer对服务有自己的缓存,定时(默认30s)到注册中心更新缓存的服务信息. 当服务下线时,如果本地缓存不清除,将导致将请求转发到一个不可用的服务上,直到下次定时更新服务.
  可以通过注册中心监听服务健康状态(例如consul的watches),通知一个MQ服务,gateway监听MQ消息刷新本地缓存服务信息
  参考:  [RocketServerChangeListener](gateway-lb/src/main/java/com/weweibuy/gateway/lb/mq/RocketServerChangeListener.java)  

   
