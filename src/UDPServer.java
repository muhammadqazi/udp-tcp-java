import java.io.*;
import java.net.*;

class UDPServer {
    public static void main(String[] args) {

        try {
            DatagramSocket s = new DatagramSocket(8009);

            while (true) {

                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                s.receive(receivePacket);
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Client: " + message);

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // create a thread for sending messages to the client
                Thread sendThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            System.out.print("Enter message to send: ");
                            String message = null;
                            try {
                                message = new BufferedReader(new InputStreamReader(System.in)).readLine();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            byte[] sendData = message.getBytes();
                            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                            try {
                                s.send(sendPacket);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                // start the thread
                sendThread.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}