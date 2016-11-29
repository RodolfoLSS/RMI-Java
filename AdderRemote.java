import java.rmi.*;  
import java.rmi.server.*;  
import java.sql.*;  
import java.util.*;  


public class AdderRemote extends UnicastRemoteObject implements Adder{

	AdderRemote()throws RemoteException{
		super();
	}
	
	public int add(int x,int y){
		return x+y;
	}
	
	public int sub(int x,int y){
		return x-y;
	}
	
	public int Unsubscribe (String nome, String topico) throws RemoteException{
		try{
    			Class.forName("org.postgresql.Driver");  
			Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/SistemasOperacionais","postgres","senha");   
			if(topico == "#sod"){
				PreparedStatement ps=con.prepareStatement("UPDATE topico SET sod=0 WHERE username=(?)");
				ps.setString(1, nome); // seta 0 no campo do topico o qual o usuario parou de seguir
			}
			else if(topico == "#cc"){
				PreparedStatement ps=con.prepareStatement("UPDATE topico SET cc=0 WHERE username=(?)");
				ps.setString(1, nome); // seta 0 no campo do topico o qual o usuario parou de seguir
			}
			else if(topico == "#cd"){
				PreparedStatement ps=con.prepareStatement("UPDATE topico SET cd=0 WHERE username=(?)");
				ps.setString(1, nome); // seta 0 no campo do topico o qual o usuario parou de seguir
			}
    			ResultSet rs=ps.executeQuery();  
      			con.close();
      		return 1;
      	
    		}catch(Exception e){
    			System.out.println(e);
    			return 0;	
    		} 
	}
	
	// funçao que ira inserir no banco de posts
	public int InserePost(String nome, String topico, timestamp horaPost, String texto) throws RemoteException{
		try{
    			Class.forName("org.postgresql.Driver");  
			Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/SistemasOperacionais","postgres","senha");   
			if (topico == "#cc" or topico == "#cd" or topico == "#sod"){
				PreparedStatement ps=con.prepareStatement("INSERT INTO post (timestamp,nome,topico,texto) VALUES (?,?,?,?)");
				ps.setTimestamp(1, horaPost);
				ps.setString(2, nome);
				ps.setString(3, topico);
				ps.setString(4, texto);
			}
    			ResultSet rs=ps.executeQuery();  
      			con.close();
      			return 1;
    		}catch(Exception e){
    			System.out.println(e);
    			return 0;	
    		} 
	}

	public List<String> RetrieveTime(String nome, timestamp tempo) throws RemoteException{
		try{
    			Class.forName("org.postgresql.Driver");  
			Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/SistemasOperacionais","postgres","senha");   
			List<String> posts = new ArrayList<String>;
			PreparedStatement ps=con.prepareStatement("SELECT * FROM topico WHERE username=(?)");
			ps.setString(1, nome);
			ResultSet rs=ps.executeQuery(); 

			if (rs.next()){
				PreparedStatement ps1=con.prepareStatement("SELECT username,topico,texto FROM post WHERE time>=(?) AND topico='#sod'",(tempo,)) // procura todos os posts de um determinado usuario a partir do tempo especificado

			results = cursor.fetchall()
			for row in results:
				posts += 'Username:' + row[0] + ' topico:' + row[1] + ' postou: ' + row[2] + '\n'
		
		cursor.execute("SELECT * FROM topico WHERE username=(?) AND cc=1", (nome,))
		x =  cursor.fetchone()
			}  
      			con.close();
      			return 1;
    		}catch(Exception e){
    			posts.append("Erro: USERNAME inexistente ou DATA escrita de forma errada");
    		}
		return posts; // retorna lista de posts encontrados
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
