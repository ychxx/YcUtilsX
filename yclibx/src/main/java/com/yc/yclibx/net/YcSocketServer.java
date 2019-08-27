package com.yc.yclibx.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket服务器
 */
public class YcSocketServer {

    public void open(int port) throws IOException {
        ServerSocket ss = new ServerSocket(port);
        Socket client = null;
        while (true) {
            System.out.println("waiting for...");
            client = ss.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println(br.readLine());
        }
    }
}
