import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	ServerSocket server;
	Socket clientSocket;
	DataOutputStream out;
	ObjectInputStream in;	
	Protos protoBuf;
	ClientSerial cs = null;
	
    Server(int port) throws Exception{
    	System.out.println("Binding to port " + port + "...");
       // server = new ServerSocket(port);
        InetAddress adres = InetAddress.getLocalHost();
        server = new ServerSocket(port, 1 ,adres);
        System.out.println("Server started: " + server);
        System.out.println("Waiting for client...");
        clientSocket = server.accept();
        System.out.println("Client connected.");
        protoBuf = new Protos();
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    void readMessages() throws IOException, ClassNotFoundException {
    	System.out.println("Waiting for Client message...");
    	cs = (ClientSerial) in.readObject();
    	//String clientMessage = in.readLine();
    	System.out.println("Client: " + cs.IP + cs.address);
    	//String[] splitMessage = clientMessage.split(";");
    	//System.out.println(splitMessage[0] + ", " + splitMessage[1]);
    	//System.out.println(splitMessage[1]);
    	System.out.println(cs.IP + ", " + cs.address);
    	System.out.println(cs.address);
    	
    	String send = protoBuf.serverMessage(cs.address, cs.IP);
    	System.out.println("\nSending:" + send);
    	String[] splitSendMessage = send.split(";");
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