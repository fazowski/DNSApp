import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	ServerSocket server;
	Socket clientSocket;
	DataOutputStream out;
	BufferedReader in;	
	Protos protoBuf;
	
    Server(int port) throws Exception{
    	System.out.println("Binding to port " + port + "...");
        server = new ServerSocket(port);
        System.out.println("Server started: " + server);
        System.out.println("Waiting for client...");
        clientSocket = server.accept();
        System.out.println("Client connected.");
        protoBuf = new Protos();
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    void readMessages() throws IOException {
    	System.out.println("Waiting for Client message...");
    	String clientMessage = in.readLine();
    	System.out.println("Client: " + clientMessage);
    	String[] splitMessage = clientMessage.split(";");
    	System.out.println(splitMessage[0] + ", " + splitMessage[1]);
    	System.out.println(splitMessage[1]);
    	
    	String send = protoBuf.serverMessage(splitMessage[1]);
    	System.out.println("\n" + send);
    	out.writeBytes(send + "\n");
    	out.flush();
    	System.out.println("Send response to Client");
    	
    	/* Old version for tests.
    	 * 
    	 * if (splitMessage[1].equals("www.google.com")) {
		System.out.println("www.google.com -> 8.8.8.8");
		out.writeBytes("www.google.com -> 8.8.8.8\n");
		out.flush();
		System.out.println("Send response to Client");
		}
		else {
			System.out.println("ERROR. NOT FOUND!");
			out.writeBytes("ERROR. NOT FOUND\n");
			out.flush();
		}	*/

    }
}