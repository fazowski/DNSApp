public class ClientTest {

	public static void main(String[] args) throws Exception {
		Client newClient = new Client(85);
		
		while(true) {
			newClient.menu();
		}

	}

}
