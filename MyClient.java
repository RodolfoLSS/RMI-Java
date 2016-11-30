import java.rmi.*;
import java.util.Date;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.sql.Timestamp;

public class MyClient{

	public static void main(String args[]){

		try{

			 //fazendo a conexão com o servidor
			Adder stub=(Adder)Naming.lookup("rmi://localhost:5000/sonoo");

			Scanner ler = new Scanner(System.in);
			int opcao;
			String name;
			String topic; 
			String userName;
			String topico;
			String texto;
			String date;
			Timestamp timestamp;
			
			while(true){

				System.out.println("1 - post\n2 - follow\n3 - unsubscribe\n4 - retrievetime\n5 - retrievetopic\n0 - Sair\nOpcao:");
				opcao = ler.nextInt();

				if(opcao == 0)
					break;

				else if(opcao == 1){

					System.out.print("Digite o nome do usuário: ");
					name = ler.next();
					userName = ("@"+"name"+"\n");
					System.out.print("Digite o tópico em que quer participar: ");
					topic = ler.next();
					topico = ("#"+"topic");
				}
				else if(opcao == 2){

					System.out.print("Postado por: ");
					name = ler.next();
					userName = ("@"+"name"+"\n");
					System.out.print("Tópico em que fara o post: ");
					topic = ler.next();
					topico = ("#"+"topic");
					System.out.print("Texto a ser postado: ");
					texto = ler.next();
				}
				else if(opcao == 3){

					System.out.print("Digite o nome do usuário: ");
					name = ler.next();
					userName = ("@"+"name"+"\n");
					System.out.print("Digite o tópico em que deseja deixar de seguir: ");
					topic = ler.next();
					topico = ("#"+"topic");
				}
				else if(opcao == 4){

					System.out.print("Digite o nome do usuário: ");
					name = ler.next();
					userName = ("@"+"name"+"\n");
					//lendo a data com o timestamp
					timestamp = new Timestamp(System.currentTimeMillis());
					date = new SimpleDateFormat("dd/MM/yyyy").format(timestamp.getTime());
					System.out.println(date);
				}
				else if(opcao == 5){

					System.out.print("Digite o nome do usuário: ");
					name = ler.next();
					userName = ("@"+"name"+"\n");
					System.out.print("Digite o tópico em que deseja deixar de seguir: ");
					topic = ler.next();
					topico = ("#"+"topic");
					//lendo a data com o timestamp
					timestamp = new Timestamp(System.currentTimeMillis());
					date = new SimpleDateFormat("dd/MM/yyyy").format(timestamp.getTime());
					System.out.println(date);
				}
			}
			System.out.println(stub.add(34,4));

		}catch(Exception e){System.out.println(e);}
	}

}
