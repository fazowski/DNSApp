
public class ServerTest {

	public static void main(String[] args) throws Exception {
    	Server server = new Server(4445);
        while (true) {
            server.readMessages();
        }
    }

}
