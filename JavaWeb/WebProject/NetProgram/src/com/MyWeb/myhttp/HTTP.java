package com.MyWeb.myhttp;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTP {
    public static void main(String[] args) throws IOException {
        //创建服务器
        ServerSocket server = new ServerSocket();
        //绑定端口
        server.bind(new InetSocketAddress("localhost",4888));
        //阻塞方式获取
        Socket accept = server.accept();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
//        String s = null;
        InputStream in = accept.getInputStream();
        int len;
        byte[] buf = new byte[512];
        while ((len=in.read(buf))!=-1) {
            System.out.println(new String(buf,0,len));
            if(len<512){
                accept.shutdownInput();
            }
        }
//        while ((s=reader.readLine())!=null){
//            System.out.println(s);
//            i++;
//            if(i>10)accept.shutdownInput();
//        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));
        writer.write("http1.1 200 ok\r\nContent-type:text/html\r\nContent-length:14\r\n\r\n<h1>hello</h1>");
        writer.flush();
        writer.close();
        accept.close();
    }
}
