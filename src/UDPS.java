import java.io.*;
import java.net.*;

class MyUDPServer {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(8009);

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                final String[] message = {new String(receivePacket.getData(), 0, receivePacket.getLength())};
                System.out.println("Client: " + message[0]);

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // start a new thread for sending messages to the client
                new Thread(() -> {
                    while (true) {
                        System.out.print("Enter message to send: ");
                        try {
                            message[0] = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        byte[] sendData = message[0].getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                        try {
                            socket.send(sendPacket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
