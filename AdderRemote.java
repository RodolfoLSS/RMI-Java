import java.rmi.*;  
import java.rmi.server.*;  
import java.sql.*;  
import java.util.*;  
import java.util.ArrayList;
import java.util.List;


public class AdderRemote extends UnicastRemoteObject implements Adder{
	
	int Lider = 0;
	int numEleitor = 1;
	ListaCircular lista = new ListaCircular();
	
	public int adicionaEleitor(){
		//adiciona um eleitor
		No no = new No();
		no.setId(numEleitor);
		lista.add(no);
		numEleitor++;
		return (numEleitor - 1); 
	}
	
	public int getLider(){
		if (Lider==0){
			Lider = Eleicao();	
		}
		return Lider;
	}
	
	public void abdicar(int x){
		No no = new No();
		no.setId(x);
		lista.delete(no);
		if (Lider == x){
			Lider = 0;
			int aux = getLider();
			
		}
	}
	
	public int Eleicao(){
		No no3;
		int menor = 100000;
		
		while(true){
           	no3 = lista.getNo();
            	//System.out.println(no3.getId());
            	if(no3.getId()<menor && no3.getId()>=0){
            		menor=no3.getId();
            	}if(no3.getId()==menor){
            		break;
            	}
            	lista.proximoNo();
        	}
        	return menor;		
	}
	
	
	AdderRemote()throws RemoteException{
		super();
	}
	
	public int add(int x,int y){
		return x+y;
	}
	
	public int sub(int x,int y){
		return x-y;
	}
	
	public int Follow (String nome, String topico){
	     
		try{
    			Class.forName("org.postgresql.Driver");  
			Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/SistemasOperacionais","postgres","senha");    
    			try{
    				PreparedStatement ps=con.prepareStatement("INSERT INTO topico(username, "+topico+") VALUES(?, 1)");
    				ps.setString(1,nome); 
    				ResultSet rs=ps.executeQuery();
    				System.out.println("TESTE:"+rs);
    			
      			return 1;
      	
    			}catch(Exception e){
    				if (e.getMessage().contains("duplicate key")){
    					//System.out.println("ENTROU");
    					PreparedStatement ps=con.prepareStatement("UPDATE topico SET "+topico+"=1 WHERE username=?");
    					
    					ps.setString(1,nome);
    					ResultSet rs=ps.executeQuery();
    					return 2; 
    				}
    				else{
    					System.out.println(e);
    					return 3;
    				}	
    			}
    		}catch(Exception e){
    			System.out.println(e);
    			return 0;
    		}
	}
	
	public int Unsubscribe (String nome, String topico) throws RemoteException{
		String novaInstancia = new String(topico);
		try{
			Class.forName("org.postgresql.Driver");  
			Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/SistemasOperacionais","postgres","senha");   
			
			if(novaInstancia.intern() == "#sod"){
				PreparedStatement ps=con.prepareStatement("UPDATE topico SET sod=0 WHERE username=?");
				ps.setString(1, nome); // seta 0 no campo do topico o qual o usuario parou de seguir
				ResultSet rs=ps.executeQuery(); 
			}
			else if(novaInstancia.intern() == "#cc"){
				PreparedStatement ps=con.prepareStatement("UPDATE topico SET cc=0 WHERE username=?");
				ps.setString(1, nome); // seta 0 no campo do topico o qual o usuario parou de seguir
				ResultSet rs=ps.executeQuery(); 
			}
			else if(novaInstancia.intern() == "#cd"){
				PreparedStatement ps=con.prepareStatement("UPDATE topico SET cd=0 WHERE username=?");
				ps.setString(1, nome); // seta 0 no campo do topico o qual o usuario parou de seguir
				ResultSet rs=ps.executeQuery(); 
			} 
      		con.close();
      		return 1;
      	
    		}catch(Exception e){
    			System.out.println(e);
    			return 0;	
    		} 
	}
	
	// funçao que ira inserir no banco de posts
	public int InserePost(String nome, String topico, Timestamp horaPost, String texto) throws RemoteException{
		String novaInstancia = new String(topico);
		try{
    			Class.forName("org.postgresql.Driver");  
			Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/SistemasOperacionais","postgres","senha");   
			if (novaInstancia.intern() == "#cc" || novaInstancia.intern() == "#cd" || novaInstancia.intern() == "#sod"){
				System.out.println(topico);
				PreparedStatement ps=con.prepareStatement("INSERT INTO post (time,username,topico,texto) VALUES (?,?,?,?)");
				ps.setTimestamp(1, horaPost);
				ps.setString(2, nome);
				ps.setString(3, novaInstancia.intern());
				ps.setString(4, texto);
				ResultSet rs=ps.executeQuery(); 
			}
    			 
      			con.close();
      			return 1;
    		}catch(Exception e){
    			System.out.println(e);
    			return 0;	
    		} 
	}
	
	public List<String> RetrieveTime(String nome, Timestamp tempo) throws RemoteException{
		List<String> posts = new ArrayList<String>();
		try{
    			Class.forName("org.postgresql.Driver");  
			Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/SistemasOperacionais","postgres","senha");   
			
			// procura todos os posts de um determinado usuario a partir do tempo especificado
			PreparedStatement ps=con.prepareStatement("SELECT * FROM topico WHERE username=(?) AND sod=1");
			ps.setString(1, nome);
			ResultSet rs=ps.executeQuery(); 

			if (rs.next()){
				PreparedStatement ps1=con.prepareStatement("SELECT username,topico,texto FROM post WHERE time>=(?) AND topico='#sod'");
				ps1.setTimestamp(1, tempo);
				ResultSet rs1=ps1.executeQuery();
				while(rs1.next()){
					posts.add("Username:" + rs1.getString("username") + " topico:" + rs1.getString("topico") + " postou: " + rs1.getString("texto"));
				}
			}

			// procura todos os posts de um determinado usuario a partir do tempo especificado
			PreparedStatement ps2=con.prepareStatement("SELECT * FROM topico WHERE username=(?) AND cc=1");
			ps2.setString(1, nome);
			ResultSet rs2=ps2.executeQuery(); 

			if (rs2.next()){
				PreparedStatement ps3=con.prepareStatement("SELECT username,topico,texto FROM post WHERE time>=(?) AND topico='#cc'");
				ps3.setTimestamp(1, tempo);
				ResultSet rs3=ps3.executeQuery();
				while(rs3.next()){
					posts.add("Username:" + rs3.getString("username") + " topico:" + rs3.getString("topico") + " postou: " + rs3.getString("texto"));
				}
			}

			// procura todos os posts de um determinado usuario a partir do tempo especificado
			PreparedStatement ps4=con.prepareStatement("SELECT * FROM topico WHERE username=(?) AND cd=1");
			ps4.setString(1, nome);
			ResultSet rs4=ps4.executeQuery(); 

			if (rs4.next()){
				PreparedStatement ps5=con.prepareStatement("SELECT username,topico,texto FROM post WHERE time>=(?) AND topico='#cd'");
				ps5.setTimestamp(1, tempo);
				ResultSet rs5=ps5.executeQuery();
				while(rs5.next()){
					posts.add("Username:" + rs5.getString("username") + " topico:" + rs5.getString("topico") + " postou: " + rs5.getString("texto"));
				}
			}

      			con.close();
    		}catch(Exception e){
    			posts.add("Erro: USERNAME inexistente ou DATA escrita de forma errada");
    		}
		return posts; // retorna lista de posts encontrados
	}
	
	public List<String> RetrieveTopic(String nome, Timestamp tempo, String topico) throws RemoteException{
		List<String> posts = new ArrayList<String>();
		String novaInstancia = new String(topico);
		String str2 = topico.substring(1);
		System.out.println(str2);
		 
		try{
			Class.forName("org.postgresql.Driver");  
			Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/SistemasOperacionais","postgres","senha"); 
			
			PreparedStatement ps=con.prepareStatement("SELECT * FROM topico WHERE username=(?) AND "+str2+"=1");
			ps.setString(1, nome);
			ResultSet rs=ps.executeQuery(); 
			if(rs.next()){
				System.out.println("Entrou");
				PreparedStatement ps2=con.prepareStatement("SELECT username,topico,texto FROM post WHERE time>=(?) AND topico=?");
				ps2.setTimestamp(1, tempo);
				ps2.setString(2, topico);
				ResultSet rs5=ps2.executeQuery();
				while(rs5.next()){
					posts.add("Username:" + rs5.getString("username") + " topico:" + rs5.getString("topico") + " postou: " + rs5.getString("texto"));
				}
			}
			 
		}catch(Exception e){
			System.out.println(e);
		}
		return posts;
	}

	public int getCustomers(){   
    		try{
    			Class.forName("org.postgresql.Driver");  
			Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/SistemasOperacionais","postgres","senha");  
    			System.out.println("Conectado com sucesso!!!!");  
    			PreparedStatement ps=con.prepareStatement("INSERT INTO teste(username, post) VALUES ( 'vitor2','teste') ");  
    			ps.executeQuery();  
      		con.close();
      		return 1;
      	
    		}catch(Exception e){
    			System.out.println(e);
    			return 0;	
    		} 
    }//end of getCustomers()  
}
