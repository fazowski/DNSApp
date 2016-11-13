import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
	ServerSocket server;
	Socket clientSocket;
	DataOutputStream out;
	BufferedReader in;	

    Server(int port) throws IOException{
    	System.out.println("Binding to port " + port + "...");
        server = new ServerSocket(port);
        System.out.println("Server started: " + server);
        System.out.println("Waiting for client...");
        clientSocket = server.accept();
        System.out.println("Client connected.");
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    void readMessages() throws IOException {
    	System.out.println("Waiting for Client message...");
    	String clientMessage = in.readLine();
    	System.out.println("Client: " + clientMessage);
    	if (clientMessage == "www.wp.pl\n") {
    		String ip = "192.168.0.1";
    		System.out.println("www.wp.pl -> 192.168.0.1");
    		out.writeBytes(clientMessage + ip + "\n");
    		out.flush();
    		System.out.println("Send response to Client");
    	}
    	else {
    		System.out.println("ERROR. NOT FOUND!");
    		out.writeBytes("ERROR. NOT FOUND\n");
    		out.flush();
    	}	  	

    }
}