import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private  Socket clientSocket;
    BufferedReader in;
    DataOutputStream out;

    Client (int port) throws IOException {
    	clientSocket = new Socket("localhost", port);
    	out = new DataOutputStream(clientSocket.getOutputStream());
    	in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    	
    }

    void menu() throws IOException{
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

    void sendMessage(String address) throws IOException {
        //Send the message to the server
        out.writeBytes("0.0.0.0;" + address + "\n");
        out.flush();
        System.out.println("Send to Server: " + address);
        
        System.out.println("Waiting for server response...");
        String serverResponse = in.readLine();
        System.out.println("Server: " + serverResponse ); 
        String[] splitServerResponse = serverResponse.split(";");
        if(splitServerResponse[0].equals(address)) {
        	System.out.println("=================");
        	System.out.println(serverResponse);
        	System.out.println("=================");
        }
        else {        	
        	System.out.println("\n" + splitServerResponse[0] + "\t" + splitServerResponse[1]);
        	sendMessage(address, splitServerResponse[1]);
        }
    }
    
    void sendMessage(String address, String ip) throws IOException {
    	out.writeBytes(ip + ";" + address + "\n");
    	out.flush();
    	System.out.println("Send to Server: " + address + ";" + ip);
    	
    	System.out.println("Waiting for server repsonse...");
    	String serverResponse = in.readLine();
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
    	}
    	
    }
}