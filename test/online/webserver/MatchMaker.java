package online.webserver;

import java.util.ArrayList;


public class MatchMaker extends Thread {

	private ArrayList<ClientHandler> clients, newClients, removedClients;


	public MatchMaker() {
		clients = new ArrayList<>();
		newClients = new ArrayList<>();
		removedClients = new ArrayList<>();
	}


	@Override
	public void run() {
		while (true) {
			for (ClientHandler ch : clients) {
				if (ch.getClient().getSocket().isClosed()) {
					this.removeClient(ch);
					System.out.println(ch.getClient().getSocket().getInetAddress() + " disconnected");
					continue;
				}

				System.out.println(ch.getClient().getSocket().getInetAddress() + " is " + ch.getClient().getState());
				switch (ch.getClient().getState()) {
					case IDLE:
						// TO-DO: Check for failed connections (timeout).
						break;
					case LOBBY:
						// TO-DO: Pair correct people up.
						break;
					case RANDOM_GAME:
						// TO-DO: Connect 4 random clients.
						break;
				}
			}

			clients.removeAll(removedClients);
			clients.addAll(newClients);
			removedClients.clear();
			newClients.clear();

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	public void addClient(ClientHandler c) {
		newClients.add(c);
	}

	public void removeClient(ClientHandler c) {
		removedClients.add(c);
	}

}
