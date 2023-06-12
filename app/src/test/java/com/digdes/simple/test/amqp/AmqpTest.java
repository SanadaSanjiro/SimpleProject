package com.digdes.simple.test.amqp;

import com.digdes.simple.amqp.MessageConsumer;
import com.digdes.simple.amqp.MessageProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AmqpTest {
    @Autowired
    MessageProducer messageProducer;

    @Autowired
    MessageConsumer messageConsumer;

    @Test
    public void sendMessageTest() {
        messageProducer.sendMessage();
    }
}
