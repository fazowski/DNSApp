import java.io.IOException;

public class ClientTest {

	public static void main(String[] args) throws IOException {
		Client newClient = new Client(4444);
		
		while(true) {
			newClient.menu();
		}

	}

}
