package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

class Application {
	


	public static void main(String[] args) {
		Socket socket = null;		
		try {
			
			int PORT;
			String HOST;
			Scanner sc = new Scanner(System.in);
			
			System.out.println("quelle url");
			
			while(true) {
			    String i = sc.nextLine();
			    String[] tab = i.split(":");
			    if (tab.length == 3 && tab[0].equals("BTTP")) {
			    	HOST = tab[1];
			    	try {
			    	PORT = Integer.parseInt(tab[2]);
			    	socket = new Socket(HOST, PORT);
			    	break;
			    	} catch( NumberFormatException | IOException  e){ 
			    		e.printStackTrace();
			    	}
			    }
			    System.out.println("re tente svp");
		    }
			
			System.out.println("Connecté au serveur " + socket.getInetAddress() + ":"+ socket.getPort());
			System.out.println("Port local : " + socket.getLocalPort());

			BufferedReader sin = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
			PrintWriter sout = new PrintWriter (socket.getOutputStream ( ), true);

			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
			
			while (true) {
				String recu = sin.readLine() ;
				if(recu==null) { 
					System.out.println("le serveur a racrocher\n");
					break;
				}
				recu = recu.replace("##", "\n");
				System.out.println(recu);
				
				String txt = clavier.readLine();
				sout.println(txt);
			
				if (txt.isBlank()) { 
					System.out.println("On raccroche avec le serveur\n");
					socket.close();
					break;
				}
			}
		}
		catch (IOException e) { System.err.println(e); }
		// Refermer dans tous les cas la socket
		try { if (socket != null) socket.close(); } 
		catch (IOException e2) { ; }		
	}
}
