package communication;

import controller.Controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {

    private ServerSocket serverSocket;
    private final int PORT = 7777;
    private Controller controller;

    public Server() {
    }

    public void start() {
        System.out.println("Starting Server...");
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Unable to connect with the given port");
            System.exit(1);
        }
        while (!serverSocket.isClosed()) {
            if (Thread.interrupted())
                break;

            System.out.println("Waiting for a client...");
            try {
                Socket client = serverSocket.accept();
                Thread clientThread = new Thread(new HandleClient(client));
                clientThread.start();
            } catch (IOException e) {
                System.out.println("Connection error " + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Could not close server");
            e.printStackTrace();
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private class HandleClient implements Runnable {

        private Socket client;

        HandleClient(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            String clientIP;
            String response, request;
            try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {

                clientIP = client.getInetAddress().getHostAddress();
                in.ready();
                request = in.readLine();
                System.out.println("Request from " + clientIP + ": " + request);

                response = controller.handleRequest(request);
                out.write(response + "\n");
                out.flush();
                System.out.println("Response to " + clientIP +": " + response);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
