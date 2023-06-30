package com.example.demo.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public List<WebSocketSession> getSessions() {
        return sessions;
    }

    
    public List<String> getSessionIds() {
        List<String> sessionIds = new ArrayList<>();
        for (WebSocketSession session : sessions) {
            sessionIds.add(session.getId());
        }
        return sessionIds;
    }
    ///
        public List<WebSocketSession> getSessionsFromIds(List<String> sessionIds) {
        List<WebSocketSession> sessionList = new ArrayList<>();
        for (String sessionId : sessionIds) {
            for (WebSocketSession session : sessions) {
                if (session.getId().equals(sessionId)) {
                    sessionList.add(session);
                    break;
                }
            }
        }
        return sessionList;
    }



    ///

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {

		String payload = message.getPayload();
		JSONObject jsonObject = new JSONObject(payload);
        System.out.println(payload);
		//session.sendMessage(new TextMessage("Hi " + jsonObject.get("user") + " how may we help you?"));

        System.out.println(session);
        //

        System.out.println("Send Messages to all sessions....");
        for (WebSocketSession eachsession : sessions) {
            if (eachsession.isOpen() && !eachsession.equals(session)) {
                System.out.println(eachsession);
                eachsession.sendMessage(new TextMessage(jsonObject.get("message") + "\n"));
            }
        }
	}

    


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }


    
}