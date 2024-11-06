package com.trickynguci.civicmessagerbackend.listener;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.springframework.stereotype.Component;

@Component
public class SocketIOEventListener {

    private final SocketIOServer socketIOServer;

    public SocketIOEventListener(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    @OnConnect
    public void onConnect() {
        System.out.println("Client connected");
    }

    @OnDisconnect
    public void onDisconnect() {
        System.out.println("Client disconnected");
    }

    @OnEvent("message")
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
        socketIOServer.getBroadcastOperations().sendEvent("message", message);
    }
}