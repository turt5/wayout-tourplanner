package wayout.files.Dashboard;

import wayout.files.Dashboard.util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private ServerSocket serverSocket;
    private HashMap<String, NetworkUtil> clientMap;
    NetworkInformation networkInformation = new NetworkInformation();

    public Server() {
        clientMap = new HashMap<>();
        try {
            serverSocket = new ServerSocket(33331);
            System.out.println("Server has started...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server has accepted a connection...");
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        String clientName = (String) networkUtil.read();
        clientMap.put(clientName, networkUtil);  // set userName

        System.out.println(clientName + " has joined");


        // inner class ReadThreadServer to receive messages from client and to send the message to appropriate receiver client:

        class ReadThreadServer implements Runnable {

            @Override
            public void run() {
                try {
                    while (true) {
                        Message msg = (Message) networkUtil.read();
                        if (msg != null) {
                            String toClientName = msg.getTo();
                            String message = msg.getText();
                            String from = msg.getFrom();

                            if (toClientName != null) {
                                NetworkUtil toClient = clientMap.get(toClientName);

                                if (toClient != null) {

                                    // sending to appropriate client

                                    toClient.write(msg);
                                }
                            }
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {

                    // if class is not found, that means client left the chat (application terminated) so remove the client from the hashmap
                    clientMap.remove(clientName);
                    System.out.println(clientName+" has left the chat!");
                }
            }
        }

        new Thread(new ReadThreadServer()).start();

    }

    public static void main(String[] args) {
        Server server=new Server();
    }

}

