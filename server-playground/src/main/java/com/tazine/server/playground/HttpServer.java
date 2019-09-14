package com.tazine.server.playground;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

/**
 * 简易的 HTTP 服务器
 *
 * @author frank
 * @date 2018/09/14
 */
public class HttpServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(80);
            while (true) {
                Socket socket = serverSocket.accept();

                InputStream is = socket.getInputStream();
                // toString 会导致错误
                // String req = IOUtils.toString(is, Charset.defaultCharset());

                // 也会报错
                System.out.println(Thread.currentThread().getName());
                List<String> reqs = IOUtils.readLines(is, Charset.defaultCharset());
                reqs.forEach(s -> {
                    System.out.println(s);
                });

//                BufferedReader br = new BufferedReader(new InputStreamReader(is));
//                while (br.ready()){
//                    System.out.println(br.readLine());
//                }

//                System.out.println("收到请求：" + reqs);

                OutputStream os = socket.getOutputStream();

                os.write("HTTP/1.1 200 OK\r\n".getBytes());
                os.write("Content-Type:text/html;charset=utf-8\r\n".getBytes());
                os.write("Content-Length:38\r\n".getBytes());
                os.write("Server:tazine\r\n".getBytes());
                os.write(("Date:" + new Date() + "\r\n").getBytes());
                os.write("\r\n".getBytes());
                os.write("<h1>hello!</h1>".getBytes());
                os.write("<h3>HTTP服务器!</h3>".getBytes("utf-8"));
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
