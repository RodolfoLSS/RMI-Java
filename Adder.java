import java.rmi.*;  
import java.rmi.server.*;  
import java.sql.*;  
import java.util.*;  

public interface Adder extends Remote{

	public int add(int x,int y)throws RemoteException;
	
	public int sub(int x,int y)throws RemoteException;
	
	public int getCustomers()throws RemoteException;  
}
