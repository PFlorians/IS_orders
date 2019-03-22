package controller;
import java.sql.*;
import java.util.*;
import javafx.collections.*;
import model.*;
import view.*;
import javafx.beans.property.*;

public class Objednavky {
	private Connection c;
	private String name, passwd, dbase;
	public Objednavky()
	{
		
	}
	public Objednavky(Connection c)
	{
		this.c=c;
	}
	public Objednavky(Connection c, String name, String passwd, String dbase)
	{
		this.c=c;
		this.name=name;
		this.passwd=passwd;
		this.dbase=dbase;
	}
	public ObservableList<Objednavka> getBasic()
	{
		Statement stmt=null;
	    ResultSet rs;
	    String sql="select cons.title, cons.sender, c.name, cons.price from objednavka as cons"
                  + "inner join client_cons as b on cons.id = b.cons_id"
                  + "inner join clients as c on c.id=b.company_id;";
	    //ret value
	    Objednavka tmp;
		List<Objednavka> list=new ArrayList<Objednavka>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+this.dbase,
		            this.name, this.passwd);
	         stmt=c.createStatement();
	         rs=stmt.executeQuery(sql);
	         while(rs.next())
	         {
	        	 tmp=new Objednavka();
	        	 tmp.setTitle(new SimpleStringProperty(rs.getString("title")));
	        	 tmp.setSender(new SimpleStringProperty(rs.getString("sender")));
	        	 tmp.setRecipient(new SimpleStringProperty(rs.getString("name")));
	        	 tmp.setPrice(new SimpleFloatProperty(rs.getFloat("price")));
	         }
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		ObservableList<Objednavka> o=FXCollections.observableList(list);
		return o;
	}
	public ObservableList<Objednavka> getAll()//vrati select vsetkych objednavok
	{
		//db vars
		Statement stmt=null;
	    ResultSet rs;
	    String sql="select * from objednavka;";
	    //ret value
		List<Objednavka> list=new ArrayList<Objednavka>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_test",
		            this.name, this.passwd);
	         stmt=c.createStatement();
	         rs=stmt.executeQuery(sql);
	         while(rs.next())
	         {
	        	 list.add(new Objednavka(new SimpleIntegerProperty(rs.getInt("id")),
	        			 new SimpleStringProperty(rs.getString("title")), 
	        			 new SimpleStringProperty(rs.getString("sender")),
	        			 new SimpleStringProperty(rs.getString("send_date")),
	        			 new SimpleStringProperty(rs.getString("recipient")),
	        			 new SimpleStringProperty(rs.getString("rec_date")),
	        			 new SimpleFloatProperty(rs.getInt("price")),
	        			 new SimpleStringProperty(rs.getString("transport")),
	        			 new SimpleStringProperty(rs.getString("priority")),
	        			 new SimpleIntegerProperty(rs.getInt("quantity")),
	        			 new SimpleIntegerProperty(rs.getInt("status"))));
	         }
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		ObservableList<Objednavka> o=FXCollections.observableList(list);
		return o;
	}
	public ArrayList<Objednavka> getAllInArray()//vrati select vsetkych objednavok
	{
		//db vars
		Statement stmt=null;
	    ResultSet rs;
	    String sql="select cons.id, cons.title, cons.sender, cons.send_date, c.name, "
	    		+ "cons.rec_date, cons.price, cons.transport, cons.priority, cons.quantity, cons.status "
	    		+ "from objednavka as cons inner join client_cons as b on "
	    		+ "b.cons_id=cons.id inner join clients as c on c.id = b.company_id;";
	    //ret value
		ArrayList<Objednavka> o=new ArrayList<Objednavka>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	         stmt=c.createStatement();
	         rs=stmt.executeQuery(sql);
	         while(rs.next())
	         {
	        	 o.add(new Objednavka(new SimpleIntegerProperty(rs.getInt("id")),
	        			 new SimpleStringProperty(rs.getString("title")), 
	        			 new SimpleStringProperty(rs.getString("sender")),
	        			 new SimpleStringProperty(rs.getString("send_date")),
	        			 new SimpleStringProperty(rs.getString("name")),
	        			 new SimpleStringProperty(rs.getString("rec_date")),
	        			 new SimpleFloatProperty(rs.getInt("price")),
	        			 new SimpleStringProperty(rs.getString("transport")),
	        			 new SimpleStringProperty(rs.getString("priority")),
	        			 new SimpleIntegerProperty(rs.getInt("quantity")),
	        			 new SimpleIntegerProperty(rs.getInt("status"))));
	         }
		}
		catch(Exception e)
		{
			System.out.println("Error catched: " + e);
			e.printStackTrace();
		}
		return o;
	}
	public Objednavka updateCons(Objednavka o)
	{
		Objednavka obj=null;
		Statement stmt=null;
	    ResultSet rs;
	    String sql=
	    		 "update objednavka "
	    		+ " set title = '"+o.getTitle()+"'" 
	    		+ " , sender = '"+o.getSender()+"'"
	    		+ " , send_date ='"+o.getSend_date()+"'"
	    		+ " , recipient='"+o.getRecipient()+"'"
	    		+ " , rec_date = '"+o.getRec_date()+"'"
	    		+ " , price = "+o.getPrice()+""
	    		+ " , transport = '" + o.getTransport()+"'"
	    		+ " , priority = '"+o.getPriority()+"'"
	    		+ " , quantity = "+o.getQuantity()+""
	    		+ " , status = "+o.getStatus()+""
	    		+ " where id = "+o.getId()+";";
	    String sql1="update client_cons as b set company_id=(select c.id from clients as c where c.name = '"+o.getRecipient()+"') where " 
	    		+ "b.cons_id = (select cons.id from objednavka as cons where cons.id = "+o.getId()+");";
	    String updated="select * from objednavka where id="+o.getId()+";";
	   // System.out.println(sql);
	    //ret value
	    try
		{
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
	         stmt=c.createStatement();
	         c.setAutoCommit(false);
	         stmt.executeUpdate(sql);
	         c.commit();  
	         stmt.executeUpdate(sql1);
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
	         obj=new Objednavka(new SimpleIntegerProperty(rs.getInt("id")),
       			 new SimpleStringProperty(rs.getString("title")), 
       			 new SimpleStringProperty(rs.getString("sender")),
       			 new SimpleStringProperty(rs.getString("send_date")),
       			 new SimpleStringProperty(rs.getString("recipient")),
       			 new SimpleStringProperty(rs.getString("rec_date")),
       			 new SimpleFloatProperty(rs.getInt("price")),
       			 new SimpleStringProperty(rs.getString("transport")),
       			 new SimpleStringProperty(rs.getString("priority")),
       			 new SimpleIntegerProperty(rs.getInt("quantity")),
       			 new SimpleIntegerProperty(rs.getInt("status")));
	         }
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Second update statement " + e);
	    	e.printStackTrace();
	    }
	   
	    return obj;
	}
	public void deleteCons(Objednavka o)
	{
		//Objednavka obj=null;
		Statement stmt=null;
	    String sql=
	    		" delete from client_cons where cons_id="+o.getId()+";"
	    	    + " delete from assign where cons_id="+o.getId()+";"
	    		+ "delete from objednavka "
	    		+ " where id = "+o.getId()+";";
	    
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
	   
	    //return obj;
	}
	public void insertCons(Objednavka o)
	{
		Statement stmt=null;
	    String sql=
	    		 "insert into objednavka "
	    		+ "(title, sender, send_date, recipient, "
	    		+ "rec_date, price, transport, priority, quantity, status)"
	    		+ " values('"+o.getTitle()+"'" 
	    		+ " , '"+o.getSender()+"'"
	    		+ " , '"+o.getSend_date()+"'"
	    		+ " , '"+o.getRecipient()+"'"
	    		+ " , '"+o.getRec_date()+"'"
	    		+ " , "+o.getPrice()+""
	    		+ " , '" + o.getTransport()+"'"
	    		+ " , '"+o.getPriority()+"'"
	    		+ " , "+o.getQuantity()+""
	    		+ " , "+o.getStatus()+""
	    		+ " );";
	    String sql1="insert into client_cons(cons_id, company_id) select max(cons.id), c.id from objednavka as cons, "
	    		+ "clients as c where c.name = '"+o.getRecipient()+"'"
	    		+ " group by c.id;";
	    String sql2=" insert into assign(cons_id) select max(cons.id) from objednavka as cons;";
	    System.out.println(sql);
	    //System.exit(1);
	    //ret value
	    try
		{
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
	         stmt=c.createStatement();
	         c.setAutoCommit(false);
	         stmt.executeUpdate(sql);
	         c.commit();
	         stmt.executeUpdate(sql1);
	         c.commit();
	         stmt.executeUpdate(sql2);
	         c.commit();  
		}
	    catch(Exception e)
	    {
	    	System.out.println("Insert exception: " + e);
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
	}
	
	public ObservableList<String> getClientNames()
	{
		//db vars
		Statement stmt=null;
		ResultSet rs;
		String sql="select cons.recipient from objednavka as cons;";
		//ret value
		List<String> list=new ArrayList<String>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			stmt=c.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				list.add(rs.getString("recipient"));
			}
			list.add("nil");
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		ObservableList<String> o=FXCollections.observableList(list);
		return o;
	}
	public ObservableList<String> getProductNames()
	{
		//db vars
		Statement stmt=null;
		ResultSet rs;
		String sql="select cons.title from objednavka as cons;";
		//ret value
		List<String> list=new ArrayList<String>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			stmt=c.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				list.add(rs.getString("title"));
			}
			list.add("nil");
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		ObservableList<String> o=FXCollections.observableList(list);
		return o;
	}
	public ObservableList<Objednavka> getByPrice(String val)
	{
		float limit=Float.parseFloat(val);
		//db vars
		Statement stmt=null;
		ResultSet rs;
		String sql="select * from objednavka as cons where cons.price>="+limit+";";
		//ret value
		List<Objednavka> list=new ArrayList<Objednavka>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			stmt=c.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				list.add(new Objednavka(new SimpleIntegerProperty(rs.getInt("id")),
	        			 new SimpleStringProperty(rs.getString("title")), 
	        			 new SimpleStringProperty(rs.getString("sender")),
	        			 new SimpleStringProperty(rs.getString("send_date")),
	        			 new SimpleStringProperty(rs.getString("recipient")),
	        			 new SimpleStringProperty(rs.getString("rec_date")),
	        			 new SimpleFloatProperty(rs.getInt("price")),
	        			 new SimpleStringProperty(rs.getString("transport")),
	        			 new SimpleStringProperty(rs.getString("priority")),
	        			 new SimpleIntegerProperty(rs.getInt("quantity")),
	        			 new SimpleIntegerProperty(rs.getInt("status"))));
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		ObservableList<Objednavka> o=FXCollections.observableList(list);
		return o;	
	}
	public ObservableList<Objednavka> getByRecipient(String val)
	{
		
		//db vars
		Statement stmt=null;
		ResultSet rs;
		String sql="select * from objednavka as cons where cons.recipient='"+val+"';";
		//ret value
		List<Objednavka> list=new ArrayList<Objednavka>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			stmt=c.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				list.add(new Objednavka(new SimpleIntegerProperty(rs.getInt("id")),
	        			 new SimpleStringProperty(rs.getString("title")), 
	        			 new SimpleStringProperty(rs.getString("sender")),
	        			 new SimpleStringProperty(rs.getString("send_date")),
	        			 new SimpleStringProperty(rs.getString("recipient")),
	        			 new SimpleStringProperty(rs.getString("rec_date")),
	        			 new SimpleFloatProperty(rs.getInt("price")),
	        			 new SimpleStringProperty(rs.getString("transport")),
	        			 new SimpleStringProperty(rs.getString("priority")),
	        			 new SimpleIntegerProperty(rs.getInt("quantity")),
	        			 new SimpleIntegerProperty(rs.getInt("status"))));
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		ObservableList<Objednavka> o=FXCollections.observableList(list);
		return o;	
	}
	public ObservableList<Objednavka> getByProduct(String val)
	{
		//db vars
		Statement stmt=null;
		ResultSet rs;
		String sql="select * from objednavka as cons where cons.title='"+val+"';";
		//ret value
		List<Objednavka> list=new ArrayList<Objednavka>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			stmt=c.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				list.add(new Objednavka(new SimpleIntegerProperty(rs.getInt("id")),
	        			 new SimpleStringProperty(rs.getString("title")), 
	        			 new SimpleStringProperty(rs.getString("sender")),
	        			 new SimpleStringProperty(rs.getString("send_date")),
	        			 new SimpleStringProperty(rs.getString("recipient")),
	        			 new SimpleStringProperty(rs.getString("rec_date")),
	        			 new SimpleFloatProperty(rs.getInt("price")),
	        			 new SimpleStringProperty(rs.getString("transport")),
	        			 new SimpleStringProperty(rs.getString("priority")),
	        			 new SimpleIntegerProperty(rs.getInt("quantity")),
	        			 new SimpleIntegerProperty(rs.getInt("status"))));
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		ObservableList<Objednavka> o=FXCollections.observableList(list);
		return o;
		
	}
	public ObservableList<Objednavka> getByPriceAndRecipient(String val, String rec)
	{
		float limit=Float.parseFloat(val);
		//db vars
		Statement stmt=null;
		ResultSet rs;
		String sql="select * from objednavka as cons where cons.price>="+limit+" and cons.recipient='"+rec+"';";
		//ret value
		List<Objednavka> list=new ArrayList<Objednavka>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			stmt=c.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				list.add(new Objednavka(new SimpleIntegerProperty(rs.getInt("id")),
	        			 new SimpleStringProperty(rs.getString("title")), 
	        			 new SimpleStringProperty(rs.getString("sender")),
	        			 new SimpleStringProperty(rs.getString("send_date")),
	        			 new SimpleStringProperty(rs.getString("recipient")),
	        			 new SimpleStringProperty(rs.getString("rec_date")),
	        			 new SimpleFloatProperty(rs.getInt("price")),
	        			 new SimpleStringProperty(rs.getString("transport")),
	        			 new SimpleStringProperty(rs.getString("priority")),
	        			 new SimpleIntegerProperty(rs.getInt("quantity")),
	        			 new SimpleIntegerProperty(rs.getInt("status"))));
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		ObservableList<Objednavka> o=FXCollections.observableList(list);
		return o;
		
	}
	public ObservableList<Objednavka> getByPriceAndProduct(String val, String product)
	{
		float limit=Float.parseFloat(val);
		//db vars
		Statement stmt=null;
		ResultSet rs;
		String sql="select * from objednavka as cons where cons.price>="+limit+" and cons.title='"+product+"';";
		//ret value
		List<Objednavka> list=new ArrayList<Objednavka>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			stmt=c.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				list.add(new Objednavka(new SimpleIntegerProperty(rs.getInt("id")),
	        			 new SimpleStringProperty(rs.getString("title")), 
	        			 new SimpleStringProperty(rs.getString("sender")),
	        			 new SimpleStringProperty(rs.getString("send_date")),
	        			 new SimpleStringProperty(rs.getString("recipient")),
	        			 new SimpleStringProperty(rs.getString("rec_date")),
	        			 new SimpleFloatProperty(rs.getInt("price")),
	        			 new SimpleStringProperty(rs.getString("transport")),
	        			 new SimpleStringProperty(rs.getString("priority")),
	        			 new SimpleIntegerProperty(rs.getInt("quantity")),
	        			 new SimpleIntegerProperty(rs.getInt("status"))));
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		ObservableList<Objednavka> o=FXCollections.observableList(list);
		return o;
		
	}
	public ObservableList<Objednavka> getByRecipientAndProduct(String rec, String product)
	{
		
		//db vars
		Statement stmt=null;
		ResultSet rs;
		String sql="select * from objednavka as cons where cons.recipient='"+rec+"' and cons.title='"+product+"';";
		//ret value
		List<Objednavka> list=new ArrayList<Objednavka>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			stmt=c.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				list.add(new Objednavka(new SimpleIntegerProperty(rs.getInt("id")),
	        			 new SimpleStringProperty(rs.getString("title")), 
	        			 new SimpleStringProperty(rs.getString("sender")),
	        			 new SimpleStringProperty(rs.getString("send_date")),
	        			 new SimpleStringProperty(rs.getString("recipient")),
	        			 new SimpleStringProperty(rs.getString("rec_date")),
	        			 new SimpleFloatProperty(rs.getInt("price")),
	        			 new SimpleStringProperty(rs.getString("transport")),
	        			 new SimpleStringProperty(rs.getString("priority")),
	        			 new SimpleIntegerProperty(rs.getInt("quantity")),
	        			 new SimpleIntegerProperty(rs.getInt("status"))));
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		ObservableList<Objednavka> o=FXCollections.observableList(list);
		return o;
		
	}
	public ObservableList<Objednavka> getByAllMeans(String val, String rec, String product)
	{
		float limit=Float.parseFloat(val);
		//db vars
		Statement stmt=null;
		ResultSet rs;
		String sql="select * from objednavka as cons where cons.price>="+limit+" and cons.recipient='"+rec+"'"
				+ " and cons.title='"+product+"';";
		//ret value
		List<Objednavka> list=new ArrayList<Objednavka>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			stmt=c.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				list.add(new Objednavka(new SimpleIntegerProperty(rs.getInt("id")),
	        			 new SimpleStringProperty(rs.getString("title")), 
	        			 new SimpleStringProperty(rs.getString("sender")),
	        			 new SimpleStringProperty(rs.getString("send_date")),
	        			 new SimpleStringProperty(rs.getString("recipient")),
	        			 new SimpleStringProperty(rs.getString("rec_date")),
	        			 new SimpleFloatProperty(rs.getInt("price")),
	        			 new SimpleStringProperty(rs.getString("transport")),
	        			 new SimpleStringProperty(rs.getString("priority")),
	        			 new SimpleIntegerProperty(rs.getInt("quantity")),
	        			 new SimpleIntegerProperty(rs.getInt("status"))));
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		ObservableList<Objednavka> o=FXCollections.observableList(list);
		return o;
		
	}
	public void setC(Connection c)
	{
		this.c=c;
	}
}
