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

   
