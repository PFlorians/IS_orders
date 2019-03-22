package controller;
import java.util.*;
import javafx.collections.*;
import model.*;
import view.*;
import javafx.beans.property.*;
import java.sql.*;

public class Clients {
	private Connection c;
	public Clients()
	{
		
	}
	public Clients(Connection c)
	{
		this.c=c;
	}
	public ObservableList<Client> getNames()
	{
		Statement stmt=null;
	    ResultSet rs;
	    String sql="select c.name from clients as c;";
	    //ret value
		List<Client> a=new ArrayList<Client>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	         stmt=c.createStatement();
	         rs=stmt.executeQuery(sql);
	         while(rs.next())
	         {
	        	 Client cl=new Client();
	        	 cl.setName(new SimpleStringProperty(rs.getString("name")));
	        	 a.add(cl);
	         }
		}
		catch(Exception e)
		{
			System.out.println("Client exception: " + e);
			e.printStackTrace();
		}
		ObservableList<Client> o=FXCollections.observableArrayList(a);
		return o;
	}
	public ObservableList<String> parseClientNames(ObservableList<Client> o)
	{
		List<String> x=new ArrayList<String>();
		int i=0;
		while(i<o.size())
		{
			x.add(o.get(i).getName());
			i++;
		}
		ObservableList<String> y=FXCollections.observableArrayList(x);
		return y;
	}
	public void setC(Connection c)
	{
		this.c=c;
	}
}
