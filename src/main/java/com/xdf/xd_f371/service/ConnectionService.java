package com.xdf.xd_f371.service;

import com.xdf.xd_f371.controller.ConnectLan;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class ConnectionService {
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;

    public boolean checkConnection(String ip, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 30000); // 5-second timeout
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void maintainConnection(){
        try {
            socket = new Socket(ConnectLan.ip_pre, Integer.parseInt(ConnectLan.port_pre));
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            startHeartbeat();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void startHeartbeat() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (socket.isConnected()) {
                    out.println("heartbeat");
                    System.out.println("Heartbeat sent to server.");
                } else {
                    System.out.println("Connection lost. Attempting to reconnect...");
                    reconnect();
                }
            }
        }, 0, 7000);
    }

    private void reconnect() {
        try {
            socket.close();
            socket = new Socket(ConnectLan.ip_pre, Integer.parseInt(ConnectLan.port_pre));
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Reconnected to server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
