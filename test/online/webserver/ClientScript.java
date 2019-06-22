package online.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class ClientScript {
	public static void main(String[] args) throws IOException {

		try (Socket serverSocket = new Socket(Server.HOST_NAME, Server.PORT_NUMBER)) {
			System.out.println("Connection established to " + Server.HOST_NAME + ".");

			BufferedReader reader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			PrintWriter writer = new PrintWriter(serverSocket.getOutputStream(), true);

			Scanner scanner = new Scanner(System.in);

			loop: while (true) {
				String input = scanner.nextLine();

				switch (input) {
					case "exit":
						writer.println("exit");
						break loop;
					case "lobby":
						System.out.println("Enter ID:");
						final int lobbyID = scanner.nextInt();
						System.out.println("Looking for a game with ID " + lobbyID + "...");
						writer.println("lobby:" + lobbyID);
						break;
					case "random":
						System.out.println("Looking for a random game...");
						writer.println("random");
						break;

					default:
						writer.println(input);
						break;
				}

				System.out.println("echo: " + reader.readLine());
			}

			scanner.close();
			reader.close();
			writer.close();
			serverSocket.close();
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + Server.HOST_NAME);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + Server.HOST_NAME);
			System.exit(1);
		}
	}

}
