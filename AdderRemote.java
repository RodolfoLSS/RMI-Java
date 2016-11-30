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
			
			// procura todos os posts de um determinado usuario a partir do tempo especificado
			PreparedStatement ps=con.prepareStatement("SELECT * FROM topico WHERE username=(?) AND sod=1");
			ps.setString(1, nome);
			ResultSet rs=ps.executeQuery(); 

			if (rs.next()){
				PreparedStatement ps1=con.prepareStatement("SELECT username,topico,texto FROM post WHERE time>=(?) AND topico='#sod'")
				ps1.setString(1, tempo);
				ResultSet rs1=ps1.executeQuery();
				while(rs1.next()){
					posts.add("Username:" + rs1.getString("username") + " topico:" + rs1.getString("topico") + " postou: " + rs1.getString("texto")
				}
			}

			// procura todos os posts de um determinado usuario a partir do tempo especificado
			PreparedStatement ps2=con.prepareStatement("SELECT * FROM topico WHERE username=(?) AND cc=1");
			ps2.setString(1, nome);
			ResultSet rs2=ps2.executeQuery(); 

			if (rs2.next()){
				PreparedStatement ps3=con.prepareStatement("SELECT username,topico,texto FROM post WHERE time>=(?) AND topico='#cc'")
				ps3.setString(1, tempo);
				ResultSet rs3=ps3.executeQuery();
				while(rs3.next()){
					posts.add("Username:" + rs3.getString("username") + " topico:" + rs3.getString("topico") + " postou: " + rs3.getString("texto")
				}
			}

			// procura todos os posts de um determinado usuario a partir do tempo especificado
			PreparedStatement ps4=con.prepareStatement("SELECT * FROM topico WHERE username=(?) AND cd=1");
			ps4.setString(1, nome);
			ResultSet r4s=ps4.executeQuery(); 

			if (rs4.next()){
				PreparedStatement ps5=con.prepareStatement("SELECT username,topico,texto FROM post WHERE time>=(?) AND topico='#cd'")
				ps5.setString(1, tempo);
				ResultSet rs5=ps5.executeQuery();
				while(rs5.next()){
					posts.add("Username:" + rs5.getString("username") + " topico:" + rs5.getString("topico") + " postou: " + rs5.getString("texto")
				}
			}

      			con.close();
      			return 1;
    		}catch(Exception e){
    			posts.add("Erro: USERNAME inexistente ou DATA escrita de forma errada");
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
