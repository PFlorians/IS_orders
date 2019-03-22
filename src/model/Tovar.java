package model;
import view.*;
import javafx.beans.property.*;

public class Tovar {
	private SimpleIntegerProperty id;
	private SimpleStringProperty meno;
	private SimpleStringProperty sklad;
	private SimpleIntegerProperty quantity;
	
	public Tovar(int id, String name, String sklad, int quantity)
	{
		this.id=new SimpleIntegerProperty(id);
		this.meno=new SimpleStringProperty(name);
		this.sklad=new SimpleStringProperty(sklad);
		this.quantity=new SimpleIntegerProperty(quantity);
	}
	public int getId()
	{
		return this.id.get();
	}
	public void setId(int id)
	{
		this.id.set(id);
	}
	public String getMeno()
	{
		return this.meno.get();
	}
	public void setMeno(String meno)
	{
		this.meno.set(meno);
	}
	public String getSklad()
	{
		return this.sklad.get();
	}
	public void setSklad(String sklad)
	{
		this.sklad.set(sklad);
	}
	public int getQuantity()
	{
		return this.quantity.get();
	}
	public void setQuantity(int quantity)
	{
		this.quantity.set(quantity);
	}
}
