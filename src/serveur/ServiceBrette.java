package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ServiceBrette extends Service {

	private StringBuffer txt;

	public ServiceBrette(Socket s){
		super(s);
	}
	
	public void run ( ) {
		
		try {
			BufferedReader sin = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
			PrintWriter sout = new PrintWriter (socket.getOutputStream ( ), true);
			
			txt = new StringBuffer("Tapez une chaîne de caractères\npetit retour a la ligne pour le flex");
			int i=0;
			while(true) {
				if(i==1){//se serv le fait qu'une foit mais on peut juste suprr ce if pour une conv infini
					this.socket.close(); 
					System.out.println("On raccroche avec le client\n");
					break;
				}
			
				sout.println(txt.toString().replace("\n", "##"));
				StringBuffer line = new StringBuffer(sin.readLine( ));
				
				if (line.toString().isBlank()) { 
					this.socket.close(); 
					System.out.println("On raccroche avec le client\n");
					break;
				}

				txt = line.reverse();
				
				
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
