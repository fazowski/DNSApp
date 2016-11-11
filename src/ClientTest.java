import java.io.IOException;

public class ClientTest {

	public static void main(String[] args) throws IOException {
		Client newClient = new Client("localhost", 9999);
		
		while(true) {
			newClient.menu();
		}

	}

}
