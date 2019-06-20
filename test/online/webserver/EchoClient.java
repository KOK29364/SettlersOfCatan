package online.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class EchoClient {
	public static void main(String[] args) throws IOException {

//		final String hostName = "alpin.gencer.us";
		final String hostName = "127.0.0.1";
		final int portNumber = 32323;

		try (Socket serverSocket = new Socket(hostName, portNumber)) {
			System.out.println("Connection established to " + hostName + ".");

			BufferedReader reader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			PrintWriter writer = new PrintWriter(serverSocket.getOutputStream(), true);

			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				String input = stdIn.readLine();
				writer.println(input);

				System.out.println("echo: " + reader.readLine());
				if (input.equals("exit")) {
					break;
				}
			}

			stdIn.close();
			reader.close();
			writer.close();
			serverSocket.close();
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}

}
