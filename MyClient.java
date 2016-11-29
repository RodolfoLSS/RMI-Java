import java.rmi.*;  
import java.rmi.server.*;  
import java.sql.*;  
import java.util.*; 

public class MyClient{
	
	public static void main(String args[]){
		try{
			Adder stub=(Adder)Naming.lookup("rmi://localhost:5000/sonoo");
			System.out.println(stub.add(34,4));
			System.out.println(stub.sub(34,4));
			System.out.println(stub.getCustomers());  

		}catch(Exception e){System.out.println(e);}
	}

}
