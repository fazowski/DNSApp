import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private  Socket clientSocket;
    ObjectInputStream in;
    ObjectOutputStream out;
    ClientSerial cs;
    ClientSerial receive;
    
    Client (int port) throws IOException {
    	clientSocket = new Socket("192.168.0.102", port);
    	out = new ObjectOutputStream(clientSocket.getOutputStream());
    	in = new ObjectInputStream(clientSocket.getInputStream());   	
    }

    void menu() throws IOException, ClassNotFoundException{
        System.out.println("==== MENU ====");
        System.out.println("1. Send String");
        System.out.println("2. Exit");
        Scanner input = new Scanner(System.in);
        int userInput = input.nextInt();

        switch(userInput) {
            case 1:
                System.out.println("Your string");
                Scanner addressInput = new Scanner(System.in);
                String userAddressInput = addressInput.nextLine();
                this.sendMessage(userAddressInput);
                break;
            case 2:
            	System.out.println("Closing program.");
                input.close();
                in.close();
                out.close();
                clientSocket.close();     
                System.exit(0);
                break;
        }
    }

    void sendMessage(String address) throws IOException, ClassNotFoundException {
        //Send the message to the server
    	cs = new ClientSerial();
    	cs.IP = "0.0.0.0";
    	cs.address = address;
        out.writeObject(cs);
        out.flush();
        System.out.println("Send to Server: " + address);
        
        System.out.println("Waiting for server response...");
        receive = null;
        receive = (ClientSerial) in.readObject();
        //String serverResponse = in.readLine();
        System.out.println("Server: " + receive.IP + " " + receive.address ); 
        //String[] splitServerResponse = serverResponse.split(";");
        if(receive.address.equals(address)) {
        	System.out.println("=================");
        	System.out.println(">> " + receive.IP + "  " + receive.address);
        	System.out.println("=================");
        }
        else {        	
        	System.out.println("\n" + receive.IP + "\t" + receive.address);
        	sendMessage(address, receive.IP);
        }
    }
    
    void sendMessage(String address, String ip) throws IOException, ClassNotFoundException {
    	cs = new ClientSerial();
    	cs.IP = ip;
    	cs.address = address;
    	out.writeObject(cs);
    	out.flush();
    	System.out.println("Send to Server: " + cs.address + ";" + cs.IP);
    	cs.IP = "";
    	cs.address ="";
    	System.out.println("Waiting for server repsonse...");
    	receive = null;
        receive = (ClientSerial) in.readObject();
        //String serverResponse = in.readLine();
        System.out.println("Server: " + receive.IP + " " + receive.address ); 
        //String[] splitServerResponse = serverResponse.split(";");
        if(receive.address.equals(address)) {
        	System.out.println("=================");
        	System.out.println(">> " + receive.IP + "  " + receive.address);
        	System.out.println("=================");
        }
        else {        	
        	System.out.println("\n" + receive.IP + "\t" + receive.address);
        	sendMessage(address, receive.IP);
        }
    	/*String serverResponse = in.readLine();
    	System.out.println("Server: " + serverResponse);
    	String[] splitServerResponse = serverResponse.split(";");
    	if(splitServerResponse[0].equals(address)) {
    		System.out.println("===================");
    		System.out.println(serverResponse);
    		System.out.println("===================");
    	}
    	else {
    		System.out.println("\n" + splitServerResponse[0] + "\t" + splitServerResponse[1]);
        	sendMessage(address, splitServerResponse[1]);
    	}*/
    	
    }
}