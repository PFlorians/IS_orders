package controller;
import java.sql.*;

public class Validator {
	private String name, passwd, dbase;
	private boolean nonExistentDBflag=false;//je nepravda ze db neexistuje
	public Validator(String name, String passwd, String dbase)
	{
		this.name=name;
		this.passwd=passwd;
		this.dbase=dbase;
	}
	public Connection test(){
	      Connection c = null;
	      
	      try 
	      {
	    	  Class.forName("com.mysql.jdbc.Driver").newInstance();
	    	  c = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+this.dbase,
			            this.name, this.passwd);
	      }
	      catch(Exception e)
	      {
	    	  System.out.println(e);
	    	  if(e.toString().contains("database does not exist"));
	    	  {
	    		  System.out.println("Attempt to create a new DB?");
	    	  }
	    	  e.printStackTrace();
	      }
	      if(c==null)
	      {
	    	  System.out.println("Error while logging in.");
	      }
	      return c;
	}
	public boolean testDb()
	{
		try 
	      {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	    	DriverManager.getConnection("jdbc:mysql://localhost:3306/"+this.dbase,
			            this.name, this.passwd);
	      }
	      catch(Exception e)
	      {
	    	  System.out.println(e);
	    	  e.printStackTrace();
	    	  if(e.toString().contains("database does not exist"));
	    	  {
	    		  this.nonExistentDBflag=true;//je pravda ze db neexistuje
	    	  }
	    	  return false;
	      }
		return true;
	}
	public boolean queryTest(Connection c)//testuje ci su tabluky pritomne v db
	{
		  try
	  	  {
	  		  Statement  stmt=c.createStatement();
	  		  stmt.executeQuery("select * from clients.companies;");
	  	  }
	  	  catch(Exception e)
	  	  {
	  		  if(e.toString().contains("does not exist"))
	  		  {
	  			 	return false;
	  		  }
	  	  }
		  
		return true;
	}
	public boolean getDBExistentFlag()
	{
		return this.nonExistentDBflag;
	}
}
