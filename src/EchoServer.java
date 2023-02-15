import java.io.*;
import java.net.*;

class EchoServer {
    public static void main(String[] args) {

        try {
            ServerSocket s = new ServerSocket(8009);

            while(true) {

                Socket incoming = s.accept();

                // create input and output streams
                BufferedReader in = new BufferedReader( new InputStreamReader(incoming.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(incoming.getOutputStream()));

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
                            out.println(message);
                            out.flush();
                        }
                    }
                });

                // create a thread for receiving messages from the client
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
                            System.out.println("Client: " + message);
                        }
                    }
                });

                // start both threads
                sendThread.start();
                receiveThread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
