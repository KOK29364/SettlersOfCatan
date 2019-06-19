package online;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class MyClientSocket {
	private Socket socket;
	private Scanner scanner;

	private MyClientSocket(InetAddress serverAddress, int serverPort) throws Exception {
		this.socket = new Socket(serverAddress, serverPort);
		this.scanner = new Scanner(System.in);
	}

	private void start() throws IOException {
		String input;
		while (true) {
			input = scanner.nextLine();
			PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
			out.println(input);
			out.flush();
		}
	}

	public static void main(String[] args) throws Exception {
		String serverIP = "172.16.152.1";
		String port = "37175";
		MyClientSocket client = new MyClientSocket(InetAddress.getByName(serverIP), Integer.parseInt(port));

		System.out.println("\r\nConnected to Server: " + client.socket.getInetAddress());
		client.start();
	}
}
