package online.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;


public class ClientHandler extends Thread {

	private Client client;


	public ClientHandler(Socket socket) {
		this.client = new Client(socket);
	}


	@Override
	public void run() {
		System.out.println("New client: " + client.getSocket().getInetAddress());

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
			PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream(), true);

			loop: while (true) {
				final String message = reader.readLine();
				if (message == null) break loop;

				String command;
				String[] args;
				if (message.contains(":")) {
					final String[] split = message.split(":");
					command = split[0];
					args = Arrays.copyOfRange(split, 1, split.length);
				} else {
					command = message;
					args = null;
				}

				switch (command) {
					case "exit":
						writer.println("Goodbye.");
						break loop;
					case "lobby":
						client.setState(ClientState.LOBBY);
						System.out.println(client.getSocket().getInetAddress() + " requested game " + args[0] + ".");
						break;
					case "random":
						client.setState(ClientState.RANDOM_GAME);
						break;

					default:
						writer.println("Unrecognized command.");
						break;
				}
			}

			reader.close();
			writer.close();
			client.getSocket().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public Client getClient() {
		return client;
	}

}
