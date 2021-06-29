package com.MyWeb.mysocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MyServer {
    public static void main(String[] args) throws IOException {
        //监听端口6666,没有写ip地址则监听的是本机端口
        ServerSocket ss = new ServerSocket(6666);
        System.out.println("server is running");
        while (true){
            //这里会不接受到消息会一直等待,进来一个客户则会返回一个socket实例
            Socket sock = ss.accept();
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            Thread t = new Handler(sock);
            t.start();
        }
    }
}
class Handler extends Thread{
    Socket sock;

    public Handler(Socket sock) {
        this.sock = sock;
    }

    @Override
    public void run() {
        //打印这一行表示成功接收到消息，可以处理此事socket对象的流
        System.out.println("connected from "+ sock.getRemoteSocketAddress());
        try( InputStream input = this.sock.getInputStream();) {
            try(OutputStream output = this.sock.getOutputStream();) {
                handle(input,output);
            }
        } catch (IOException e) {
            try {
                this.sock.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
            System.out.println("client 异常退出");
        }finally {
            try {
                this.sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("client disconnected");
        }
    }

    private void handle(InputStream input, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(new InputStreamReader(input,StandardCharsets.UTF_8));
        writer.write("hello\n");
        writer.flush();
        while (true){
            String s = reader.readLine();
            System.out.println("receive message: "+ s);
            if("bye".equals(s)){
                writer.write("bye");
                writer.flush();
                break;
            }
            writer.write("ok,server has received your message: " + s + "\n");
            writer.flush();
        }
    }
}
