import java.rmi.*;  
import java.rmi.server.*;  
import java.sql.*;  
import java.util.*; 
import java.util.Date;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.sql.Timestamp;

public interface Adder extends Remote{

  	public int adicionaEleitor()throws RemoteException;	
  	
  	public int getLider()throws RemoteException;
  	
  	public void abdicar(int x)throws RemoteException;
  	
  	public int Eleicao()throws RemoteException;
  	
	public int add(int x,int y)throws RemoteException;
	
	public int sub(int x,int y)throws RemoteException;
	
	public int Follow (String nome, String topico)throws RemoteException;
	
	public int Unsubscribe(String nome, String topico) throws RemoteException;

	public int InserePost(String nome, String topico, Timestamp horaPost, String texto) throws RemoteException;
	
	public List<String> RetrieveTime(String nome, Timestamp tempo) throws RemoteException;
	
	public List<String> RetrieveTopic(String nome, Timestamp tempo, String topico) throws RemoteException;
	
	public int getCustomers()throws RemoteException;  
}
