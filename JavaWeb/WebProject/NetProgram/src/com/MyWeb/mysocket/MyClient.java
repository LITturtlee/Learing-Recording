package com.MyWeb.mysocket;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MyClient {
    public static void main(String[] args) throws IOException {
        //如果连接成功会返回一个socket实例
        Socket sock = new Socket("localhost",6666);
        InputStream input = sock.getInputStream();
        OutputStream output = sock.getOutputStream();
        handle(input,output);
        sock.close();
        System.out.println("Socket closed");

    }

    private static void handle(InputStream input, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(new InputStreamReader(input,StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in);
        System.out.println("[server]"+reader.readLine());
        while (true){
            //提示输出
            System.out.print(">>>>");
            String s = scanner.nextLine();
            writer.write(s);
            writer.newLine();
            writer.flush();
            String resp = reader.readLine();
            System.out.println("<<<<"+resp);
            if("bye".equals(s)){
                break;
            }
        }
    }
}
