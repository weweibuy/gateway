package com.weweibuy.gateway.lb.mq;

import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.weweibuy.framework.rocketmq.annotation.Payload;
import com.weweibuy.framework.rocketmq.annotation.RocketConsumerHandler;
import com.weweibuy.framework.rocketmq.annotation.RocketListener;
import com.weweibuy.gateway.lb.mq.message.ServerChangeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author durenhao
 * @date 2020/3/7 20:42
 **/
@Slf4j
@Component
@RocketListener(topic = "SERVER_CHANGE_TOPIC", group = "SERVER_CHANGE_GW_C_GROUP", messageModel = MessageModel.BROADCASTING, threadMax = 1, threadMin = 1)
public class RocketServerChangeListener {

    private final SpringClientFactory springClientFactory;

    public RocketServerChangeListener(SpringClientFactory springClientFactory) {
        this.springClientFactory = springClientFactory;
    }

    @RocketConsumerHandler
    public void update(@Payload ServerChangeMessage changeMessage) {
        String name = changeMessage.getName();
        if (StringUtils.isBlank(name)) {
            return;
        }
        log.info("接受到服务: {} 变更消息", name);
        ILoadBalancer loadBalancer = springClientFactory.getLoadBalancer(name);
        List<Server> allServers = loadBalancer.getAllServers();
        List<Server> reachableServers = loadBalancer.getReachableServers();
        if (loadBalancer instanceof DynamicServerListLoadBalancer) {
            DynamicServerListLoadBalancer dynamicServerListLoadBalancer = (DynamicServerListLoadBalancer) loadBalancer;
            dynamicServerListLoadBalancer.updateListOfServers();
            log.info("更新服务信息成功");
        }
    }

}
