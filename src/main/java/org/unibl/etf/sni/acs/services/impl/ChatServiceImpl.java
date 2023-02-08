package org.unibl.etf.sni.acs.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.unibl.etf.sni.acs.exceptions.HttpException;
import org.unibl.etf.sni.acs.models.dto.MessageRequestDTO;
import org.unibl.etf.sni.acs.models.dto.UserDTO;
import org.unibl.etf.sni.acs.repositories.UserRepository;
import org.unibl.etf.sni.acs.services.ChatService;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    private final SimpMessagingTemplate template;
    private final List<UserDTO> users = new ArrayList<>();
    private final UserRepository userRepository;
    private final JmsTemplate jmsTemplate;
    @Value("${mq.queue.send}")
    private String queueName;

    @Autowired
    public ChatServiceImpl(SimpMessagingTemplate template,
                           UserRepository userRepository, JmsTemplate jmsTemplate) {
        this.template = template;
        this.userRepository = userRepository;
        this.jmsTemplate = jmsTemplate;
    }
    @Override
    public void addUser(UserDTO user) {
        if (users.stream().noneMatch(u -> u.getId().equals(user.getId()))) {
            users.add(user);
            template.convertAndSend("/topic/active-users", users);
        }
    }

    @Override
    public void removeUser(String username) {
        int index = -1;
        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getUsername().equals(username))
                index = i;
        if (index != -1)
            users.remove(index);
        template.convertAndSend("/topic/active-users", users);
    }

    /* @Override
    public void send(String messageRequest) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            MessageRequestDTO messageRequestDTO = mapper.readValue(messageRequest, MessageRequestDTO.class);
            template.convertAndSend("/queue/messages/" + messageRequestDTO.getReceiverUsername(), mapper.writeValueAsString(messageRequest));
        } catch (Exception e) {
            throw new HttpException(HttpStatus.BAD_REQUEST);
        }
    } */

    @Override
    public void send(String messageRequest) {
        try {
            jmsTemplate.convertAndSend(new ActiveMQQueue(queueName), messageRequest);
        } catch (Exception e) {
            throw new HttpException(HttpStatus.BAD_REQUEST);
        }
    }

    @JmsListener(destination = "${mq.queue.receive}")
    public void receiveMessage(TextMessage message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String messageRequest = message.getText();
            MessageRequestDTO messageRequestDTO = mapper.readValue(messageRequest, MessageRequestDTO.class);
            template.convertAndSend("/queue/messages/" + messageRequestDTO.getReceiverUsername(), mapper.writeValueAsString(messageRequest));
        } catch (Exception e) {
            throw new HttpException(HttpStatus.BAD_REQUEST);
        }
    }
}
