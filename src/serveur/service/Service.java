package serveur.service;

import serveur.LoadElement;
import java.net.Socket;

public abstract class Service implements Runnable {
	protected Socket socket;
	protected LoadElement element;

	public Service(Socket s, LoadElement element){
		this.socket = s;
		this.element = element;
	}

	public Service(Socket s){
		this.socket = s;
	}

	public abstract void run();
}
