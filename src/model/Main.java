package model;
import view.*;
import controller.*;
import java.sql.*;
/*
 * doprogramovat auto update vazobnych tabuliek client_cons a assign taktiez locations
 * zmenit cely wares manager
 * oblasti skladov by mohli byt podla tovarov odfiltrovat unikaty
 * nazov firmy by mohol byt modularny, volitelny na zaciatku
 */
public class Main {
	public static void main(String args[])
	{
		
		try
		{
			//new ConnectTes().test();
			Win1 w=new Win1();
			w.init(args);
			/*Class.forName("org.postgresql.Driver");
	        Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+"company",
	            "admin", "root");
			Objednavky o=new Objednavky(c);
			o.getAllInArray();*/
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		//NewConsignement nc=new NewConsignement();
		//nc.init(args);
		//NewClient n=new NewClient();
		//n.init(args);
	}
}
