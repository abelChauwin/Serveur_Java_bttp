package serveur.service;


import serveur.LoadElement;

import java.net.Socket;

public abstract class Service implements Runnable {
	protected Socket socket;
	protected LoadElement element;
	public Service(Socket s, LoadElement element){
		socket=s;
		element=element;
	}
	public Service(Socket s){
		socket=s;
	}
	
	public abstract void run ( );
	

}
