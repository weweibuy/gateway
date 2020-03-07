package com.weweibuy.gateway.mq;

import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.weweibuy.framework.rocketmq.annotation.RocketConsumerHandler;
import com.weweibuy.framework.rocketmq.annotation.RocketListener;
import com.weweibuy.gateway.mq.message.ServerChangeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author durenhao
 * @date 2020/3/7 20:42
 **/
@Slf4j
@Component
@RocketListener(topic = "SERVER_CHANGE_TOPIC", group = "SERVER_CHANGE_GW_C_GROUP", messageModel = MessageModel.BROADCASTING, threadMax = 1, threadMin = 1)
public class RocketServerChangeListener {

    @Autowired
    private DynamicServerListLoadBalancer dynamicServerListLoadBalancer;

    @RocketConsumerHandler
    public void update(ServerChangeMessage changeMessage) {
        log.info("接受到服务变更消息");
        dynamicServerListLoadBalancer.updateListOfServers();
    }

}
