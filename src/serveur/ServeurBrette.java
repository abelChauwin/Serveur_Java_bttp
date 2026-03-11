package serveur;

import serveur.service.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurBrette implements Runnable {
	private ServerSocket listen_socket;
	private Class<? extends Service> service;
	private LoadElement element;
	
	public ServeurBrette(Class<? extends Service> clas , int port, LoadElement element) throws IOException {
			listen_socket = new ServerSocket(port);
			service=clas;
			this.element=element;
	}

	public void run() {
		try {
			while(true) {
				System.out.println("J'attends un client...");
				Socket client_socket = listen_socket.accept(); // Appel bloquant !
				System.out.println("Ca y est ! J'ai un client.");
				
				System.out.print("Adresse IP locale : "+client_socket.getLocalAddress());
				System.out.println(" Port local : "+client_socket.getLocalPort());
				System.out.print("Adresse IP distante (client) : "+client_socket.getInetAddress());
				System.out.println(" Port distant : "+client_socket.getPort());
				
				try {
					new Thread(service.getConstructor(Socket.class).newInstance(client_socket,element) ).start();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		catch (IOException e) { 
			try {this.listen_socket.close();} catch (IOException e1) {}
			System.err.println("Pb sur le port d'écoute :"+e);
		}
	}

	 // restituer les ressources --> finalize
	protected void finalize() throws Throwable {
		try {this.listen_socket.close();} catch (IOException e1) {}
	}
}
