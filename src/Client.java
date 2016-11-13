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
                break;
        }
    }

    void sendMessage(String address) throws IOException{
        //Send the message to the server       
        out.writeBytes(address + "\n");
        out.flush();
        System.out.println("Send to Server: " + address);
        
        System.out.println("Waiting for server response...");
        String serverResponse = in.readLine();
        System.out.println("Server: " + serverResponse );        
    }
}