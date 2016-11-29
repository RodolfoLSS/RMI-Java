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
	
	public int Follow (String nome, String topico){
		try{
    			Class.forName("org.postgresql.Driver");  
			Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/SistemasOperacionais","postgres","senha");    
    			PreparedStatement ps=con.prepareStatement("INSERT INTO teste(username, post) VALUES ( 'vitor2','teste') ");  
    			ResultSet rs=ps.executeQuery();  
      		con.close();
      		return 1;
      	
    		}catch(Exception e){
    			System.out.println(e);
    			return 0;	
    		} 
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
