package com.MyWeb.myhttp;

import sun.net.InetAddressCachePolicy;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket sock = new Socket();
        sock.connect(new InetSocketAddress(InetAddress.getByName("127.0.0.1"),4000));
        OutputStream output = sock.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in);
        writer.write(scanner.nextLine());
        writer.flush();
        writer.close();
        sock.close();


    }
}
