import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static Socket socket;
    String address;
    int port;

    Client (String address, int port) {
        this.address = address;
        this.port = port;
        this.createConnection();
    }

    void createConnection() {
        try {

            socket = new Socket(this.address, this.port);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
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
                socket.close();
                break;
        }
    }

    void sendMessage(String message) throws IOException{
        //Send the message to the server
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        String sendMessage = message + "\n";
        bw.write(sendMessage);
        bw.flush();
        System.out.println("String send to server : " + sendMessage);

        //Get the return message from the server
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String getMessage = br.readLine();
        System.out.println("String received : " + getMessage);
    }

    public static void main(String args[]) throws IOException {
        Client newClient = new Client("localhost", 9999);
        while (true) {
            newClient.menu();
        }
    }
}