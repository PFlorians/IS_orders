package controller;
import java.sql.*;

public class ConnectTes {//testovanie na databazu a driver
	public void test(){
	      Connection c = null;
	      Statement stmt=null;
	      ResultSet rs;
	      String sql="select product, quantity, price_piece, oblast from warehouses as w, locations as l where w.location = l.id;";
	      //result data
	      int quant, price;
	      String oblast, product;
	      try 
	      {
	         Class.forName("com.mysql.jdbc.Driver").newInstance();
	         c = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_test",
	            "root", "");
	         stmt=c.createStatement();
	         rs=stmt.executeQuery(sql);
	         while(rs.next())
	         {
	        	 product=rs.getString("product");
	        	 quant=rs.getInt("quantity");
	        	 price=rs.getInt("price_piece");
	        	 oblast=rs.getString("oblast");
	        	 System.out.println("prod: " + product);
	        	 System.out.println("quant: " + quant);
	        	 System.out.println("price: " + price);
	        	 System.out.println("oblast: " + oblast);
	         }
	         c.close();
	         stmt.close();
	         rs.close();
	      }
	      catch (Exception e) 
	      {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	   }
}
