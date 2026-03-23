package serveur;

import serveur.service.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurBrette implements Runnable {

	private ServerSocket listen_socket;
	private Class<? extends Service> service;
	private BaseDeDonne element;

	public ServeurBrette(Class<? extends Service> clas, int port, BaseDeDonne element) throws IOException {
		listen_socket = new ServerSocket(port);
		service = clas;
		this.element = element;
	}

	public void run() {
		try {
			while (true) {

				Socket client_socket = listen_socket.accept();
				System.out.println("Connexion avec un client");

				try {
					new Thread(
							service
									.getConstructor(Socket.class, BaseDeDonne.class)
									.newInstance(client_socket, element)
					).start();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			try { listen_socket.close(); } catch (IOException ignored) {}
			System.err.println("Pb sur le port d'écoute :" + e);
		}
	}
}