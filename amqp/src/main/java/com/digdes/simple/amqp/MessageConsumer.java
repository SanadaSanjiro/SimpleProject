package com.digdes.simple.amqp;

import com.alibaba.fastjson2.JSONObject;
import com.digdes.simple.mail.MailMessageModel;
import com.digdes.simple.mail.TestMailSender;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConsumer {
    private final RabbitTemplate rabbitTemplate;

    private final TestMailSender testMailSender;

    public MessageConsumer(RabbitTemplate rabbitTemplate,TestMailSender testMailSender) {
        this.rabbitTemplate = rabbitTemplate;
        this.testMailSender = testMailSender;
    }

    @RabbitListener(queues = "${rabbitmq.queue}")
    public  void recieveMessage(Message message) throws Exception {
        try {
            byte[] bytes = message.getBody();
            String toSend = new String(bytes);
            testMailSender.send(toSend);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
