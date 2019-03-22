package controller;
import java.sql.*;
import java.util.*;
import javafx.collections.*;
import javafx.scene.control.ChoiceBox;
import model.*;
import view.*;
import javafx.beans.property.*;

/*
 * select product, quantity, count(*) from warehouses group by product having count(*)>1

 */
public class WaresManager {
	private Connection c;
	private String name, passwd, dbase;
	public WaresManager()
	{
		
	}
	public WaresManager(Connection c)
	{
		this.c=c;
	}
	public ChoiceBox<String> getAllPlaces()
	{
		Statement stmt=null;
	    ResultSet rs;
	    String sql="select oblast from locations;";
	    //ret value
		List<Sklad> list=new ArrayList<Sklad>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	         stmt=c.createStatement();
	         rs=stmt.executeQuery(sql);
	         while(rs.next())
	         {
	        	 list.add(new Sklad(new SimpleStringProperty(rs.getString("oblast"))));
	         }
		}
		catch(Exception e)
		{
			System.out.println("Get by place exception: " + e);
			e.printStackTrace();
		}
		ObservableList<String> o=parsePlace(FXCollections.observableArrayList(list));
		ChoiceBox<String> cb=new ChoiceBox<String>(o);
		return cb;
	}
	public ObservableList<Sklad> getByPlace(String place)
	{
		Statement stmt=null;
	    ResultSet rs;
	    String sql="select product, quantity, price_piece from warehouses "
	    		+ "where location=(select id from locations where oblast='"+place+"');";
	    //ret value
		List<Sklad> list=new ArrayList<Sklad>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	         stmt=c.createStatement();
	         rs=stmt.executeQuery(sql);
	         while(rs.next())
	         {
	        	 list.add(new Sklad(new SimpleStringProperty(rs.getString("product")),
	        			 new SimpleIntegerProperty(rs.getInt("quantity")),
	        			 new SimpleIntegerProperty(rs.getInt("price_piece"))));
	         }
		}
		catch(Exception e)
		{
			System.out.println("Get by place exception: " + e);
			e.printStackTrace();
		}
		ObservableList<Sklad> o=FXCollections.observableArrayList(list);
		return o;
	}
	public ObservableList<String> parsePlace(ObservableList<Sklad> o)
	{
		List<String> x=new ArrayList<String>();
		int i=0;
		while(i<o.size())
		{
			x.add(o.get(i).getOblast());
			i++;
		}
		ObservableList<String> y=FXCollections.observableArrayList(x);
		return y;
	}
	public ObservableList<String> parse(ObservableList<Sklad> o)
	{
		List<String> x=new ArrayList<String>();
		int i=0;
		while(i<o.size())
		{
			x.add(o.get(i).getProduct());
			i++;
		}
		ObservableList<String> y=FXCollections.observableArrayList(x);
		return y;
	}
	public int getAverageOfProduct(String productName)
	{
		int cena=-1;
		Statement stmt=null;
	    ResultSet rs;
	    String sql="select avg(price) as  price from("
	    	+ "select price_piece as price from warehouses where product like '" + productName +"') as x;";
	    try
	    {
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
	         stmt=c.createStatement();
	         rs=stmt.executeQuery(sql);
	         while(rs.next())
	         {
	        	 cena=rs.getInt("price");
	         }
	    }
	    catch(Exception e)
	    {
	    	System.out.println("cena: " + e);
			e.printStackTrace();
	    }
	    return cena;
	}
	public ObservableList<Sklad> getAllTogether()
	{
		int i=0;
		Statement stmt=null;
	    ResultSet rs;
	    String sql="select product, sum(quantity) as quantity from warehouses "
	    		+ "group by product order by product asc";
	    //ret value
		List<Sklad> list=new ArrayList<Sklad>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	         stmt=c.createStatement();
	         rs=stmt.executeQuery(sql);
	         while(rs.next())
	         {
	        	 list.add(new Sklad());
	        	 list.get(i).setProduct(new SimpleStringProperty(rs.getString("product")));
	        	 list.get(i).setQuantity(new SimpleIntegerProperty(rs.getInt("quantity")));
	        	 list.get(i).setPrice_piece(new SimpleIntegerProperty(getAverageOfProduct(rs.getString("product"))));
	        	 i++;
	         }
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		ObservableList<Sklad> o=FXCollections.observableList(list);
		return o;
	}
	public Sklad updateWarehouse(Sklad s, String place)
	{
		Sklad war=null;
		Statement stmt=null;
	    ResultSet rs;
	    String sql=
	    		 "update warehouses "
	    		+ " set product = '"+s.getProduct()+"'" 
	    		+ " , quantity = "+s.getQuantity()+""
	    		+ " , price_piece ="+s.getPrice_piece()+""
	    		+ " where product = '"+s.getProduct()+"';"
	    		;
	    String updated="select * from warehouses where product='"+s.getProduct()+"';";
	    System.out.println(sql);
	    //ret value
	    try
		{
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
	         stmt=c.createStatement();
	         c.setAutoCommit(false);
	         stmt.executeUpdate(sql);
	         c.commit();    
		}
	    catch(Exception e)
	    {
	    	System.out.println("Update exception: " + e);
	    	e.printStackTrace();
	    	try
	    	{
	    		c.rollback();
	    	}
	    	catch(Exception ex)
	    	{
	    		System.out.println("Update exception inner: " + e);
		    	e.printStackTrace();
	    	}
	    }
	    try
	    {
	    	rs=stmt.executeQuery(updated);
	         while(rs.next())
	         {
	         war=new Sklad(
       			 new SimpleStringProperty(rs.getString("product")), 
       			 new SimpleIntegerProperty(rs.getInt("quantity")),
       			 new SimpleIntegerProperty(rs.getInt("price_piece")));
	         }
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Second update statement " + e);
	    	e.printStackTrace();
	    }
	   
	    return war;
	}
	public void setC(Connection c)
	{
		this.c=c;
	}
}
