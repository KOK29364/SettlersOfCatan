package online.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ServerThread extends Thread {

	private Socket clientSocket;


	public ServerThread(Socket socket) {
		this.clientSocket = socket;
	}


	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

			while (true) {
				String input = reader.readLine();
				if (input.equals("exit")) {
					writer.println("Goodbye.");
					break;
				}

				writer.println(input);
			}

			reader.close();
			writer.close();
			clientSocket.close();
			System.out.println(clientSocket.getInetAddress() + " disconnected");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
