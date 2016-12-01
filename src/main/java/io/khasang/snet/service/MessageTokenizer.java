package io.khasang.snet.service;


import io.khasang.snet.repository.AbstractCRUD;
import io.khasang.snet.repository.userauth.UserDAO;
import io.khasang.snet.entity.Chat;
import io.khasang.snet.entity.Message;
import io.khasang.snet.entity.userauth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.GregorianCalendar;

/* Function of this utility: parse object by JsonSerializer
* and work with database */
@Component
public class MessageTokenizer {
    private JsonSerializer<Message> messageSerializer;
    private JsonSerializer<Chat> chatJsonSerializer;
    private AbstractCRUD<Message> dataUtilMessages;
    private UserDAO userDAO;

    @Autowired
    public MessageTokenizer(JsonSerializer<Message> messageSerializer,
                            JsonSerializer<Chat> chatJsonSerializer,
                            AbstractCRUD<Message> dataUtilMessages,
                            UserDAO userDAO) {
        this.messageSerializer = messageSerializer;
        this.chatJsonSerializer = chatJsonSerializer;
        this.dataUtilMessages = dataUtilMessages;
        this.userDAO = userDAO;
    }

    public String getList(String request) {
        Message message = new Message();
        try {
            Chat chat = chatJsonSerializer.parseToEntity(request,Chat.class);
            message.setChat(chat);
            return messageSerializer.parseToJson(dataUtilMessages.getAll(message));
        } catch (Exception exc) {
            return String.format("Error occur while quering list of messages. %s",exc.getMessage());
        }
    }

    public String addNew(String raw, User sender) {
        try {
            Message message = messageSerializer.parseToEntity(raw, Message.class);
            sender = userDAO.getUserByName(sender.getLogin());
            message.setSender(sender);
            message.setStamp(new GregorianCalendar());
            dataUtilMessages.add(message);
            return messageSerializer.parseToJson(message);
        } catch (Exception exc) {
            return String.format("Error occur while saving message: %s", exc.getMessage());
        }
    }

    public String getOne(String raw) {
        try {
            Message message = messageSerializer.parseToEntity(raw, Message.class);
            message = dataUtilMessages.get(message);
            return messageSerializer.parseToJson(message);
        } catch (Exception exc) {
            return String.format("Error occur while getting existed message: %s", exc.getMessage());
        }
    }

    public String delete(String raw) {
        try {
            Message message = messageSerializer.parseToEntity(raw, Message.class);
            dataUtilMessages.delete(message);
        } catch (Exception exc) {
            return String.format("Error occur while deleting message: %s", exc.getMessage());
        }
        return "";
    }
}
