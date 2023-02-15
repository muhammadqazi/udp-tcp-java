import java.io.*;
import java.net.*;

class MyUDPClient {
    public static void main(String[] args) {
        try {
            // create a new socket for connecting to the server
            DatagramSocket socket = new DatagramSocket();

            // start the thread for sending messages to the server
            new Thread(() -> {
                while (true) {
                    System.out.print("Enter message to send: ");
                    String message = null;
                    try {
                        message = new BufferedReader(new InputStreamReader(System.in)).readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    byte[] sendData = message.getBytes();
                    InetAddress serverAddress = null;
                    try {
                        serverAddress = InetAddress.getByName("localhost");
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 8009);
                    try {
                        socket.send(sendPacket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            // start the thread for receiving messages from the server
            new Thread(() -> {
                while (true) {
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    try {
                        socket.receive(receivePacket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("Server: " + message);
                }
            }).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
