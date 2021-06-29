package com.MyWeb.myhttp;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(4000));
        System.out.println("服务器已启动，正在监听端口4000");
        for(;;){
            StringBuilder sb = new StringBuilder();
            Socket accept = server.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
            String s = null;
            //如果不关闭输入，这里会一只等待在这里
            while ((s=reader.readLine())!=null){
                System.out.println(s);
                sb.append(s+"\r\n");
                if("".equals(s)){accept.shutdownInput();}
            }
            String url = sb.toString().split("\r\n")[0].split(" ")[1].substring(1);
            if("".equals(url)||"favicon.ico".equals(url)){continue;}

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));
//            writer.write(getResponse("<h3>Test</h3>"));
            writer.write(getResponse(getPage(url)));
            writer.flush();
            writer.close();
            accept.close();
        }
    }
    public static String getResponse(String html){
        //这里存在一个bug，最好还是用字节流，我这里用字符流再写汉字的时候容易出问题
        //下面这个Content-length是字节的长度，utf-8中中文是三个字节
        return "http/1.1 200 ok\r\nContent-type:text/html\r\nContent-length:"+html.getBytes(StandardCharsets.UTF_8).length+"\r\n\r\n"+html;
    }
    //使用流获得字符串页面
    public static String getPage(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream resourceAsStream  = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        if (resourceAsStream==null){
            resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("404.html");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
        String s = null;
        while ((s=reader.readLine())!=null){
            sb.append(s);
            sb.append("\r\n");
        }
        String name = "LitTurtle";
        return sb.toString().replace("{{ name }}",name);
    }
}
