package com.weweibuy.gateway.mq;

import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.weweibuy.framework.rocketmq.annotation.Payload;
import com.weweibuy.framework.rocketmq.annotation.RocketConsumerHandler;
import com.weweibuy.framework.rocketmq.annotation.RocketListener;
import com.weweibuy.gateway.mq.message.ServerChangeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author durenhao
 * @date 2020/3/7 20:42
 **/
@Slf4j
//@Component
@RocketListener(topic = "SERVER_CHANGE_TOPIC", group = "SERVER_CHANGE_GW_C_GROUP", messageModel = MessageModel.BROADCASTING, threadMax = 1, threadMin = 1)
public class RocketServerChangeListener implements ApplicationContextAware {

    private ILoadBalancer loadBalancer;

    @RocketConsumerHandler
    public void update(@Payload ServerChangeMessage changeMessage) {
        log.info("接受到服务变更消息");
        if (loadBalancer instanceof DynamicServerListLoadBalancer) {
            DynamicServerListLoadBalancer dynamicServerListLoadBalancer = (DynamicServerListLoadBalancer) this.loadBalancer;
            dynamicServerListLoadBalancer.updateListOfServers();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ILoadBalancer bean = applicationContext.getBean(ILoadBalancer.class);
        this.loadBalancer = bean;
    }
}
