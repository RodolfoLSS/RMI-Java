import java.rmi.*;
import java.util.*;
import java.util.Date;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.sql.Timestamp;
import java.util.InputMismatchException;


public class MyClient{

	public static void main(String args[]){
		int lider=0;
		int id=0;
		try{

			 //fazendo a conexão com o servidor
			Adder stub=(Adder)Naming.lookup("rmi://192.168.0.86:5000/sonoo");
			
			id = stub.adicionaEleitor();
			System.out.println(id);
			stub.getLider();
			
			Scanner ler = new Scanner(System.in);
			Scanner ler2 = new Scanner(System.in);
			int opcao;
			String name;
			String topic; 
			String userName;
			String topico;
			String texto;
			String date;
			Timestamp timestamp;
			List<String> posts = new ArrayList<String>();
			while(true){
				stub.getLider();
				System.out.println("1 - follow\n2 - post\n3 - unsubscribe\n4 - retrievetime\n5 - retrievetopic\n6 - Mostrar Lider\n0 - Sair\nOpcao:");
				opcao = ler.nextInt();

				if(opcao == 0){
					stub.abdicar(id);
					break;
				}
				else if(opcao == 1){

					System.out.print("Digite o nome do usuário: ");
					name = ler.next();
					userName = ("@"+name);
					System.out.print("Digite o tópico em que quer Seguir: ");
					topic = ler.next();
					stub.Follow(userName,topic);
					
				}
				else if(opcao == 2){


					while(stub.possoLerEscrever() == 1){;}
					stub.naoPodeEscreverSet();
					System.out.print("Postado por: ");
					name = ler.next();
					userName = ("@"+name);
					System.out.print("Tópico em que fara o post: ");
					topic = ler.next();
					topico = ("#"+topic);
					System.out.print("Texto a ser postado: ");
					texto = ler2.nextLine();
					timestamp = new Timestamp(System.currentTimeMillis());
					//System.out.println(timestamp);
					stub.InserePost(userName,topico,timestamp,texto);
					stub.podeEscreverSet();
				}
				else if(opcao == 3){

					System.out.print("Digite o nome do usuário: ");
					name = ler.next();
					userName = ("@"+name);
					System.out.print("Digite o tópico em que deseja deixar de seguir: ");
					topic = ler.next();
					topico = ("#"+topic);
					stub.Unsubscribe(userName, topico);
				}
				else if(opcao == 4){

					while(stub.possoLerEscrever() == 1){;}
					stub.naoPodeEscreverSet();
					System.out.print("Digite o nome do usuário: ");
					name = ler.next();
					userName = ("@"+name);
					//lendo a data com o timestamp
					System.out.print("Digite a data: ");
					date = ler.next();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    					Date parsedDate = dateFormat.parse(date);
    					timestamp = new java.sql.Timestamp(parsedDate.getTime());
    					posts = new ArrayList<String>();
    					posts = stub.RetrieveTime(userName, timestamp);
    					for(int i = 0; i < posts.size(); i++) {
            				System.out.println(posts.get(i));
       				}
					stub.podeEscreverSet();
				}
				else if(opcao == 5){

					while(stub.possoLerEscrever() == 1){;}
					stub.naoPodeEscreverSet();
					System.out.print("Digite o nome do usuário: ");
					name = ler.next();
					userName = ("@"+name);
					System.out.print("Digite o tópico em que deseja deixar de seguir: ");
					topic = ler.next();
					topico = ("#"+topic);
					//lendo a data com o timestamp
					System.out.print("Digite a data: ");
					date = ler.next();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    					Date parsedDate = dateFormat.parse(date);
    					timestamp = new java.sql.Timestamp(parsedDate.getTime());
    					posts = new ArrayList<String>();
    					posts = stub.RetrieveTopic(userName, timestamp,topico);
    					for(int i = 0; i < posts.size(); i++) {
            				System.out.println(posts.get(i));
       				}
					stub.podeEscreverSet();
				}
				else if(opcao == 6){

					lider = stub.getLider();
					System.out.println("Lider : "+lider);
				}
			}

		}catch(Exception e){System.out.println(e);}
		
	}

}
