package controller;
import java.sql.*;
public class Initializer {//nepouzivat 
	//vytvori tabluky, schemy a vstupy potrebne na beh programu
	public void create(Connection c, String name)
	{
		Statement stmt;
	    try
	    {
	    	stmt=c.createStatement();
	    	stmt.executeQuery("create schema if not exists clients authorization " + name + ";");
	    	stmt.executeQuery("create schema if not exists warehouses authorization " + name + ";");
	    	stmt.executeQuery("create schema if not exists employees authorization " + name + ";");
		    stmt.executeQuery("create schema if not exists consignements authorization " + name + ";");
		    stmt.executeQuery("create schema if not exists bindings authorization " + name + ";");
		    stmt.executeQuery("create table if not exists clients.companies("
		    		+ "id serial not null,"
		    		+ "name varchar(300) not null,"
		    		+ "owner varchar(300) not null,"
		    		+ "phone text not null,"
		    		+ "mail varchar(80) not null,"
		    		+ "city varchar(100) not null,"
		    		+ "address text not null,"
		    		+ "state varchar(150),"
		    		+ "notes text, "
		    		+ "primary key(id));");
		    
		    stmt.executeQuery("create table if not exists consignements.objednavky("
		    		+ "id serial not null,"
		    		+ "title text not null,"
		    		+ "sender text not null,"
		    		+ "send_date date not null,"
		    		+ "recipient  text null,"
		    		+ "rec_date date not null,"
		    		+ "price real check (price > 0),"
		    		+ "transport varchar(100) not null,"
		    		+ "priority varchar(100) not null, "
		    		+ "quantity int not null,"
		    		+ "status int not null,"
		    		+ "primary key(id));");
		    
		    stmt.executeQuery("create table if not exists employees.emp("
		    		+ "id serial not null,"
		    		+ "name varchar(250) not null,"
		    		+ "age int not null,"
		    		+ "gender varchar(2),"
		    		+ "position varchar(100) not null,"
		    		+ "taskt int,"
		    		+ " salary int, "
		    		+ "primary key(id));");
		    
		    stmt.executeQuery("create table if not exists warehouses.bruxelles("
		    		+ "id serial not null,"
		    		+ "product varchar(200) not null,"
		    		+ "quantity int not null,"
		    		+ "price_piece int not null,"
		    		+ "primary key(id));");
		    
		    stmt.executeQuery("create table if not exists warehouses.jersey("
		    		+ "id serial not null,"
		    		+ "product varchar(200) not null,"
		    		+ "quantity int not null,"
		    		+ "price_piece int not null,"
		    		+ "primary key(id));");
		    
		    stmt.executeQuery("create table if not exists warehouses.hong_kong("
		    		+ "id serial not null,"
		    		+ "product varchar(200) not null,"
		    		+ "quantity int not null,"
		    		+ "price_piece int not null,"
		    		+ "primary key(id));");
		    
		    stmt.executeQuery("create table if not exists warehouses.washington("
		    		+ "id serial not null,"
		    		+ "product varchar(200) not null,"
		    		+ "quantity int not null,"
		    		+ "price_piece int not null,"
		    		+ "primary key(id));");
		    stmt.executeQuery("insert into clients.companies(name, owner, phone, mail, city, address, state, notes)"
		    		+ "values('Lambda corp', 'Thomas Mortdecai', '09/126 521 684', 'lambda.corp@scam.com', 'Washington', 'Main st.', 'Washington', 'Medium sized company'),"
		    		+ "('Thievery corp.', 'Capitalist col.', '02/958 623 125', 'thieves@mail.com', 'New York', 'Wall street', 'State of New York', 'Small capital'),"
		    		+ "('Mortdecai corp.', 'Thomas Mortdecai', '02/568 745 124', 'mortdecai@scam.com', 'New York', 'Broadway st.', 'New York', 'One of the biggest client companies'),"
		    		+ "('Royal trading partners', 'British royal collective', '0512/128 456', 'royal.partners@britain.gov', 'London', 'Neville st.', 'Great Britain', 'One of the biggest british companies'),"
		    		+ "('Silk traders', 'British royal collective', '0512/785 654', 'traders@silk.com', 'Edinbourgh', 'Charleston st.', 'Great Britain', 'International trading company'),"
		    		+ "('Windsor merchants', 'Windsor family fund.', '0512/987 521', 'merchants@windsor.com', 'London', 'Royal st.', 'Great Britain', 'European merchants'),"
		    		+ "('Euro traders', 'European trading group', '0487 658 974', 'etraders@eu.gov', 'Bruxelles', 'Chamberlain st.', 'Belgium', 'Continental trading group'),"
					+ "('German Industry corp.', 'Deutsche bundes kolektive', '045 187 364', 'industry@deutsche.org', 'Berlin', 'Berlin st.', 'Germany', 'German industrial company'),"
					+ "('Les electrisiens', 'European trading group', '0481 564 713', 'electrisiens@euro.fr', 'Paris', 'Rue de Victorie', 'France', 'Continental trading group with medium sized capital'),"
					+ "('Prabang traders', 'South east asia syndicate', '98745 512 6547', 'prabang@lao.ls', 'Luong Prabang', 'Chiang na', 'Laos', 'Big east asian merchant group'),"
					+ "('Asian tiger', 'Peoples republic of China', '457 872 19854', 'tiger@china.cn', 'Beijing', 'Tien st.', 'China', 'Biggest trading company in the east');");
		    stmt.executeQuery("insert into warehouses.leeds(product, quantity, price_piece)"
		    		+ "values('tea', 55000, 35), ('coffee', 32045, 20), ('silk', 65000, 32),"
		    		+ "('chocolate', 12586, 32), ('atmega328p', 526, 8), ('raspberry_pi', 25620, 58),"
		    		+ "('siliconium', 56200, 20), ('wine', 2000, 258), ('jack_daniels', 25860, 65),"
		    		+ "('vodka', 2500, 95);");
		    stmt.executeQuery("insert into warehouses.jersey(product, quantity, price_piece)"
		    		+ "values('cheese', 45623, 20), ('intel_i686', 32400, 120), ('jack_daniels', 12345, 65),"
		    		+ "('chocolate', 20000, 150), ('siliconium', 56800, 25), ('silk', 65400, 30),"
		    		+ "('velvet_white', 25000, 65), ('coffee', 25000, 20);");
		    stmt.executeQuery("insert into warehouses.washington(product, quantity, price_piece)"
		    		+ "values('tobacco', 58900, 10), ('jack_daniels', 120000, 35), ('coffee', 987000, 20),"
		    		+ "('silk', 50020, 10), ('cotton', 78500, 15), ('wool', 987000, 20),"
		    		+ "('velvet_red', 95620, 35), ('velvet_white', 65200, 40), ('vodka', 2000, 50),"
		    		+ "('spare_parts', 50000, 10), ('nylon', 25078, 36), ('siliconium', 68542, 15);");
		    stmt.executeQuery("insert into warehouses.hong_kong(product, quantity, price_piece)"
		    		+ "values('atmega328', 65500, 8), ('atmega328p', 120000, 12), ('intel_i386', 20000, 80),"
		    		+ "('intel_i486', 25050, 95), ('intel_i686', 55000, 150), ('raspberry_pi', 65000, 50),"
		    		+ "('breadboard', 500000, 20), ('oscillators', 100000, 8), ('condenser', 80000, 12),"
		    		+ "('resistor', 56000, 12);");
		    stmt.executeQuery("insert into employees.zamestnanci(name, age, gender, position, assignement)"
		    		+ "values('James Pterson', 25, 'M', 'driver', 'delivery of consignements'), "
		    		+ "('Thomas Jefferson', 45, 'M', 'manager', 'look over the delivery schedule'), "
		    		+ "('Jessica Parker', 21, 'F', 'accountant', 'accounting'),"
		    		+ "('Ivan Voroshilov', 36, 'M', 'driver', 'delivery of consignements');");
	    }
	    catch(Exception e)
	    {
	    	System.out.println(e);
	    	e.printStackTrace();
	    }
	    
	}
}
