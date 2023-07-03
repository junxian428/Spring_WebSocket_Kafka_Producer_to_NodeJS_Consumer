package com.example.demo.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.demo.config.SocketTextHandler;

@Service
public class Consumer {
  

	    private final SocketTextHandler webSocketHandler;

    @Autowired
    public Consumer (SocketTextHandler  webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }



	@KafkaListener(topics="mytopic3", groupId="mygroup")
	public void consumeFromTopic(String message) throws IOException {
		System.out.println("Consummed message "+message);
		//File log = new File("log.txt");
		       System.out.println(webSocketHandler.getSessionIds());
    	String payload = message;
		JSONObject jsonObject = new JSONObject(payload);
        System.out.println(payload);
		//session.sendMessage(new TextMessage("Hi " + jsonObject.get("user") + " how may we help you?"));
       for (WebSocketSession session : webSocketHandler.getSessionsFromIds(webSocketHandler.getSessionIds())) {
            session.sendMessage(new TextMessage(message));
        }
	
	}
}
