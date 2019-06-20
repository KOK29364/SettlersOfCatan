package online.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class EchoServer {

	public final static int PORT_NUMBER = 32323;


	public EchoServer() {
		try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
			System.out.println("Server is listening on port " + PORT_NUMBER);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("New client: " + clientSocket.getInetAddress());

				new ServerThread(clientSocket).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args) throws IOException {
		new EchoServer();
	}

}
