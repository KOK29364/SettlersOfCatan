package online.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

	public final static String HOST_NAME = "127.0.0.1";
	public final static int PORT_NUMBER = 32323;


	private MatchMaker matchmaker;


	public Server() {
		matchmaker = new MatchMaker();
		matchmaker.start();

		try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
			System.out.println("Server is listening on port " + PORT_NUMBER);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				ClientHandler c = new ClientHandler(clientSocket);
				matchmaker.addClient(c);
				c.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
