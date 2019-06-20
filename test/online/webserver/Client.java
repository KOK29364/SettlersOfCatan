package online.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;


public class Client extends Thread {

	private Socket socket;
	private ClientState state;


	public Client(Socket socket) {
		this.socket = socket;
		state = ClientState.IDLE;
	}


	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

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
						state = ClientState.LOBBY;
						System.out.println(socket.getInetAddress() + " requested game " + args[0] + ".");
						break;
					case "random":
						state = ClientState.RANDOM_GAME;
						break;

					default:
						writer.println("Unrecognized command.");
						break;
				}
			}

			reader.close();
			writer.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public Socket getSocket() {
		return socket;
	}

	public ClientState getClientState() {
		return state;
	}

}
