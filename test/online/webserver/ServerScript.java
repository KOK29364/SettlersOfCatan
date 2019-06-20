package online.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerScript {

	public final static int PORT_NUMBER = 32323;


	private MatchMaker matchmaker;


	public ServerScript() {
		matchmaker = new MatchMaker();
		matchmaker.start();

		try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
			System.out.println("Server is listening on port " + PORT_NUMBER);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("New client: " + clientSocket.getInetAddress());

				Client c = new Client(clientSocket);
				matchmaker.addClient(c);
				c.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}


	public static void main(String[] args) throws IOException {
		new ServerScript();
	}

}




class MatchMaker extends Thread {

	private ArrayList<Client> clients, newClients, removedClients;


	public MatchMaker() {
		clients = new ArrayList<>();
		newClients = new ArrayList<>();
		removedClients = new ArrayList<>();
	}


	@Override
	public void run() {
		while (true) {
			for (Client c : clients) {
				if (c.getSocket().isClosed()) {
					this.removeClient(c);
					System.out.println(c.getSocket().getInetAddress() + " disconnected");
					continue;
				}

				System.out.println(c.getSocket().getInetAddress() + " is " + c.getClientState());
			}

			clients.removeAll(removedClients);
			clients.addAll(newClients);
			removedClients.clear();
			newClients.clear();

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	public void addClient(Client c) {
		newClients.add(c);
	}

	public void removeClient(Client c) {
		removedClients.add(c);
	}

}
