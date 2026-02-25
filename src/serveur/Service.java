package serveur;


import java.net.Socket;

public abstract class Service implements Runnable {
	protected Socket socket;
	public Service(Socket s){
		socket=s;
	}
	
	public abstract void run ( );
	

}
