import java.io.IOException;

public class ServerTest {

	public static void main(String[] args) throws IOException {
    	Server server = new Server(4444);
        while (true) {
            server.readMessages();
        }
    }

}
