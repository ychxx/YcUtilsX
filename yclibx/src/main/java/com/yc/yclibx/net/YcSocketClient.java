package com.yc.yclibx.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * socket客户端
 */
public class YcSocketClient {
    String ip = "10.1.3.88";
    private Socket client = null;
    //监听的端口号
    private int port = 5417;

    /**
     * 获取一个实例
     */
    public static YcSocketClient newInstance() throws UnknownHostException, IOException {
        return new YcSocketClient();
    }

    private YcSocketClient() throws UnknownHostException, IOException {
        client = new Socket(ip, port);
    }

    /**
     * 发送一条消息
     *
     * @param msg 消息内容
     */
    public void sendMessage(String msg) throws IOException {
        PrintWriter printWriter = new PrintWriter(client.getOutputStream());
        printWriter.print(msg);
        printWriter.flush();
        printWriter.close();    //一定要关闭输出流，要不然消息无法送达到服务器
    }
}
