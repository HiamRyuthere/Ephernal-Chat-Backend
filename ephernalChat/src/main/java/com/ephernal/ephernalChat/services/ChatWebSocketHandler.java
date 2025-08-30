package com.ephernal.ephernalChat.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ConcurrentHashMap;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static class User {
        private String username;
        private int age;
        private String gender;

        public User(String username, int age, String gender) { this.username = username; this.age = age; this.gender = gender; }
        public String getUsername() { return username; }
        public int getAge() { return age; }
        public String getGender() { return gender; }
    }

    //Using CopyOnWriteArraySet because many users will interact in same time and we dont want trouble's.
    private final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final Map<String, User> usersBySessionId = new ConcurrentHashMap<>();
    private final Map<String, WebSocketSession> sessionsByUsername = new ConcurrentHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("New connection established: " + session.getId());
    }



    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        Map<String, Object> data = mapper.readValue(payload, Map.class);
        String type = (String) data.get("type");


        //if request from frontend json include type : join
        if ("join".equals(type)) {
            String username = (String) data.get("username");

            // ## THE BOUNCER LOGIC STARTS HERE ##
            // Check kar, kya is naam ka banda pehle se hi party mein hai?
            if (sessionsByUsername.containsKey(username)) {
                System.out.println("Join rejected for " + username + ". Already exists.");

                // User ko politely bata de ki "Entry nahi hai, bro"
                Map<String, String> errorMsg = new HashMap<>();
                errorMsg.put("type", "join_error");
                errorMsg.put("message", "Username '" + username + "' is already taken. Try another.");
                session.sendMessage(new TextMessage(mapper.writeValueAsString(errorMsg)));

                // Aur connection band kar de
                session.close();
                return; // Aage ka code execute mat kar
            }
            // ## BOUNCER LOGIC ENDS HERE ##

            Integer age = (Integer) data.get("age");
            String gender = (String) data.get("gender");

            if (username != null && age != null && gender != null) {
                User newUser = new User(username, age, gender);
                usersBySessionId.put(session.getId(), newUser);
                sessionsByUsername.put(username, session);
                System.out.println("User joined: " + username);
                broadcastUserList();
            }
        }

        //if request from frontend json include type : chat
        else if ("chat".equals(type)) {
            broadcastMessage(data);
        }

        //if request from frontend json include type : private_chat( we have to give sender and reciver as well with this)
        else if ("private_chat".equals(type)) {
            sendPrivateMessage(data);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        User user = usersBySessionId.remove(session.getId());
        if (user != null) {
            sessionsByUsername.remove(user.getUsername());
            System.out.println("User left: " + user.getUsername());
            broadcastUserList();
        }
    }

    private void broadcastMessage(Map<String, Object> data) throws IOException {
        String json = mapper.writeValueAsString(data);
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) s.sendMessage(new TextMessage(json));
        }
    }

    private void sendPrivateMessage(Map<String, Object> data) throws IOException {
        String toUser = (String) data.get("to");
        String fromUser = (String) data.get("from");
        WebSocketSession recipientSession = sessionsByUsername.get(toUser);
        WebSocketSession senderSession = sessionsByUsername.get(fromUser);

        String json = mapper.writeValueAsString(data);

        if (recipientSession != null && recipientSession.isOpen()) {
            recipientSession.sendMessage(new TextMessage(json));
        }
        if (senderSession != null && senderSession.isOpen()) {
            senderSession.sendMessage(new TextMessage(json));
        }
    }

    private void broadcastUserList() {
        try {
            Map<String, Object> userListMsg = new HashMap<>();
            userListMsg.put("type", "userlist");
            userListMsg.put("users", new ArrayList<>(usersBySessionId.values()));
            String json = mapper.writeValueAsString(userListMsg);
            for (WebSocketSession s : sessions) {
                if (s.isOpen()) s.sendMessage(new TextMessage(json));
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}

