import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{

    private static Socket socket;
    ServerSocket serverSocket;
    int port;

    Server(int port) throws IOException{
        this.port = port;
        this.startServer();
    }

    void startServer() throws IOException {
        serverSocket = new ServerSocket(this.port);
        System.out.println("Server started at port " + this.port);
        socket = serverSocket.accept();
    }
    void readMessages() throws IOException {
        //Reading the message from the client
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String clientMessage = br.readLine();
        System.out.println("Message from Client " + clientMessage);

        String returnMessage = clientMessage + "BLABLABLA\n";


        //Sending the response back to the client.
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(returnMessage);
        bw.flush();
        System.out.println("Message sent to the client is " + returnMessage);

    }

    public static void main(String[] args) throws IOException {
    	Server server = new Server(9999);
        while (true) {
            server.readMessages();
        }
    }
}