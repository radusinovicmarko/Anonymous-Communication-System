package org.unibl.etf.sni.acs.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public abstract class ActiveMQConsumer implements MessageListener {
    /*@Override
    @JmsListener(destination = "${mq.queue.send}")
    public void onMessage(Message message) {
        try {
            System.out.println(message.getJMSMessageID());
        } catch (JMSException e) {
            System.out.println(e.getMessage());
        }
    }*/
}
