import java.io.*;
import java.net.*;

class EchoClient {
    public static void main(String[] args) {

        try {
            // create socket connection to server
            Socket socket = new Socket("localhost" , 8009);

            // create input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));


            // create a thread for sending messages to the server
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
                        out.println(message);
                        out.flush();
                    }
                }
            });

            // create a thread for receiving messages from the server
            Thread receiveThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String message = null;
                        try {
                            message = in.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Server: " + message);
                    }
                }
            });

            // start both threads
            sendThread.start();
            receiveThread.start();

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
