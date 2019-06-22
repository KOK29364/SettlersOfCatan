package online.webserver;

import java.net.Socket;


public class Client {

	private Socket socket;
	private ClientState state;


	public Client(Socket socket) {
		this.socket = socket;
		this.state = ClientState.IDLE;
	}


	public Socket getSocket() {
		return socket;
	}

	public ClientState getState() {
		return state;
	}

	public void setState(ClientState state) {
		this.state = state;
	}

}
