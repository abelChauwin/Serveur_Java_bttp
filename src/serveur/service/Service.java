package serveur.service;

import serveur.BaseDeDonne;
import java.net.Socket;

public abstract class Service implements Runnable {
	protected Socket socket;
	protected BaseDeDonne element;

	public Service(Socket s, BaseDeDonne element){
		this.socket = s;
		this.element = element;
	}

	public Service(Socket s){
		this.socket = s;
	}

	public abstract void run();
}
