package controller;
import java.sql.*;
import java.util.*;
import javafx.collections.*;
import model.*;
import view.*;
import javafx.beans.property.*;

public class EmployeeManager {
	private Connection c;
	private String name, passwd, dbase;
	public EmployeeManager()
	{
		
	}
	public EmployeeManager(Connection c)
	{
		this.c=c;
	}
	public ObservableList<Zamestnanec> getTasks()
	{
		Statement stmt=null;
	    ResultSet rs;
	    String sql="select id from assign;";
	    //ret value
		List<Zamestnanec> a=new ArrayList<Zamestnanec>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	         stmt=c.createStatement();
	         rs=stmt.executeQuery(sql);
	         while(rs.next())
	         {
	        	 Zamestnanec z=new Zamestnanec();
	        	 z.setTask(new SimpleIntegerProperty(rs.getInt("id")));
	        	 a.add(z);
	         }
		}
		catch(Exception e)
		{
			System.out.println("Client exception: " + e);
			e.printStackTrace();
		}
		ObservableList<Zamestnanec> o=FXCollections.observableArrayList(a);
		return o;
	}
	public ObservableList<Integer> parseTasks(ObservableList<Zamestnanec> o)
	{
		List<Integer> x=new ArrayList<Integer>();
		String s;
		int i=0;
		while(i<o.size())
		{
			x.add(o.get(i).getTask());
			i++;
		}
		ObservableList<Integer> y=FXCollections.observableArrayList(x);
		return y;
	}
	public ArrayList<Zamestnanec> getAllInArray()
	{
		Statement stmt=null;
	    ResultSet rs;
	    String sql="select e.id, e.name, e.age, e.gender, e.position, e.task_id, e.salary, b.activity from employees as e inner join"
	    		+ " emp_act as b on e.id=b.emp_id;";
	    //ret value
		ArrayList<Zamestnanec> o=new ArrayList<Zamestnanec>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	         stmt=c.createStatement();
	         rs=stmt.executeQuery(sql);
	         while(rs.next())
	         {
	        	 o.add(new Zamestnanec(new SimpleIntegerProperty(rs.getInt("id")),
	        			 new SimpleStringProperty(rs.getString("name")),
	        			 new SimpleIntegerProperty(rs.getInt("age")),
	        			 new SimpleStringProperty(rs.getString("gender")),
	        			 new SimpleIntegerProperty(rs.getInt("position")),
	        			 new SimpleIntegerProperty(rs.getInt("task_id")),
	        			 new SimpleIntegerProperty(rs.getInt("salary")),
	        			 new SimpleIntegerProperty(rs.getInt("activity"))));
	        	 
	         }
		}
		catch(Exception e)
		{
			System.out.println("Employee all in array: " + e);
			e.printStackTrace();
		}
		//
		return o;
	}
	public Zamestnanec updateEmployee(Zamestnanec z)
	{
		Zamestnanec emp=null;
		Statement stmt=null;
	    ResultSet rs;
	    String sql=
	    		 "update employees"
	    		+ " set name = '"+z.getName()+"'" 
	    		+ " , age = "+z.getAge()+""
	    		+ " , gender ='"+z.getGender()+"'"
	    		+ " , position="+z.getPosition()+""
	    		+ " , task_id = "+z.getTask()+""
	    		+ " , salary = "+z.getSalary()+""
	    		+ " where id = "+z.getId()+";";
	    String sql2="update objednavka set status=1 where id=(select cons_id from assign where id="+z.getTask()+");";
	    String sql3="update emp_act set activity="+z.getActivity()+" where emp_id="+z.getId()+";";
	    String updated="select e.id, e.name, e.age, e.gender, e.position, e.task_id, e.salary, b.activity from employees as e inner join"
	    		+ " emp_act as b on e.id=b.emp_id where e.id="+z.getId()+";";
	    System.out.println(sql);
	    //ret value
	    try
		{
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
	         stmt=c.createStatement();
	         c.setAutoCommit(false);
	         stmt.executeUpdate(sql);
	         c.commit();    
	         stmt.executeUpdate(sql2);
	         c.commit();    
	         stmt.executeUpdate(sql3);
	         c.commit();  
		}
	    catch(Exception e)
	    {
	    	System.out.println("Update exception commit query1: " + e);
	    	e.printStackTrace();
	    	try
	    	{
	    		c.rollback();
	    	}
	    	catch(Exception ex)
	    	{
	    		System.out.println("Update exception rollback query1: " + e);
		    	e.printStackTrace();
	    	}
	    }
	    try
	    {
	    	rs=stmt.executeQuery(updated);
	         while(rs.next())
	         {
	        	 emp=(new Zamestnanec(new SimpleIntegerProperty(rs.getInt("id")),
	        			 new SimpleStringProperty(rs.getString("name")),
	        			 new SimpleIntegerProperty(rs.getInt("age")),
	        			 new SimpleStringProperty(rs.getString("gender")),
	        			 new SimpleIntegerProperty(rs.getInt("position")),
	        			 new SimpleIntegerProperty(rs.getInt("task_id")),
	        			 new SimpleIntegerProperty(rs.getInt("salary")),
	        			 new SimpleIntegerProperty(rs.getInt("activity"))));
	         }
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Second update statement " + e);
	    	e.printStackTrace();
	    }
	   
	    return emp;
	}
	public Zamestnanec addEmployee(Zamestnanec z)
	{
		Zamestnanec emp=null;
		ResultSet rs;
		Statement stmt=null;
	    String sql=
	    		 "insert into employees"
	    		+ "(name, age, gender, position, "
	    		+ "task_id, salary)"
	    		+ " values('"+z.getName()+"'" 
	    		+ " , "+z.getAge()+""
	    		+ " , '"+z.getGender()+"'"
	    		+ " , "+z.getPosition()+""
	    		+ " , "+((z.getTask()==0)?"NULL":z.getTask())
	    		+ " , "+z.getSalary()+" );";
	    String inserted="select e.id, e.name, e.age, e.gender, e.position, e.task_id, e.salary, b.activity from employees as e inner join"
	    		+ " emp_act as b on e.id=b.emp_id where e.id="+z.getId()+";";
	    //System.out.println(sql);
	    //System.exit(1);
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
	    	System.out.println("Insert exception commit: " + e);
	    	e.printStackTrace();
	    	try
	    	{
	    		c.rollback();
	    	}
	    	catch(Exception ex)
	    	{
	    		System.out.println("insert exception rollback: " + e);
		    	e.printStackTrace();
	    	}
	    }
	    try
	    {
	    	rs=stmt.executeQuery(inserted);
	         while(rs.next())
	         {
	        	 emp=(new Zamestnanec(new SimpleIntegerProperty(rs.getInt("id")),
	        			 new SimpleStringProperty(rs.getString("name")),
	        			 new SimpleIntegerProperty(rs.getInt("age")),
	        			 new SimpleStringProperty(rs.getString("gender")),
	        			 new SimpleIntegerProperty(rs.getInt("position")),
	        			 new SimpleIntegerProperty(rs.getInt("task_id")),
	        			 new SimpleIntegerProperty(rs.getInt("salary")),
	        			 new SimpleIntegerProperty(rs.getInt("activity"))));
	         }
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Second insert statement " + e);
	    	e.printStackTrace();
	    }
	   
	    return emp;
	}
}
