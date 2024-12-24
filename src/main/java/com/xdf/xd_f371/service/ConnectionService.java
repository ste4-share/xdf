package com.xdf.xd_f371.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Service
public class ConnectionService {

    public boolean checkConnection(String ip, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 5000); // 5-second timeout
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
