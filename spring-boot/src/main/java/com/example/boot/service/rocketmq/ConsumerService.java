package com.example.boot.service.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Service
public class ConsumerService {

    @Value("${spring.rocketmq.name-server}")
    private String nameServer;

    private DefaultMQPushConsumer consumer = null;

    @PostConstruct
    public void initMQConsumer() {
        consumer = new DefaultMQPushConsumer("bossGroup");
        consumer.setNamesrvAddr(nameServer);
        try {
            consumer.subscribe("boss", "*");
            consumer.registerMessageListener(new MessageListenerConcurrently() {

                @Override
                public ConsumeConcurrentlyStatus consumeMessage(
                        List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for (MessageExt msg : msgs) {
                        System.out.println("Tags=" + msg.getTags() + ", Length=" + msg.getBody().length);
                        switch (msg.getTags()) {
                            case "RESUME":
                                onResume(msg.getBody());
                                break;
                            case "PAY_SUCCESS":
                                onPaySuccess(msg.getBody());
                                break;
                            default:
                                break;
                        }

                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void shutDownConsumer() {
        if (consumer != null) {
            consumer.shutdown();
        }
    }

    private void onResume(byte[] body) {

    }

    private void onPaySuccess(byte[] body) {
    }
}
