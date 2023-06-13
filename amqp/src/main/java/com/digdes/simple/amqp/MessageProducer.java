package com.digdes.simple.amqp;

import com.alibaba.fastjson2.JSONObject;
import com.digdes.simple.mail.MailMessageModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    private final RabbitTemplate rabbitTemplate;

    public  MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(MailMessageModel mailMessageModel) {
        JSONObject jsonObject =JSONObject.from(mailMessageModel);
        String message = jsonObject.toJSONString();
        rabbitTemplate.convertAndSend(exchange, routingkey, mailMessageModel);
    }
}
