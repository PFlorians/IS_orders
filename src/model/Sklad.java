package model;
import view.*;
import controller.*;
import javafx.beans.property.*;
public class Sklad {
	private SimpleStringProperty product;
	private SimpleIntegerProperty quantity;
	private SimpleIntegerProperty price_piece;
	private SimpleStringProperty oblast;
	public Sklad()
	{
		
	}
	public Sklad(SimpleStringProperty oblast)
	{
		this.oblast=oblast;
	}
	public Sklad(SimpleStringProperty product, SimpleIntegerProperty quantity,
			SimpleIntegerProperty price_piece)
	{
		this.product=product;
		this.quantity=quantity;
		this.price_piece=price_piece;
	}
	public Sklad(SimpleStringProperty product, SimpleIntegerProperty quantity,
			SimpleIntegerProperty price_piece, SimpleStringProperty oblast)
	{
		this.product=product;
		this.quantity=quantity;
		this.price_piece=price_piece;
		this.oblast=oblast;
	}
	public String getProduct() {
		return product.get();
	}
	public int getQuantity() {
		return quantity.get();
	}
	public int getPrice_piece() {
		return price_piece.get();
	}
	public String getOblast()
	{
		return this.oblast.get();
	}
	public void setProduct(SimpleStringProperty product) {
		this.product = product;
	}
	public void setQuantity(SimpleIntegerProperty quantity) {
		this.quantity = quantity;
	}
	public void setPrice_piece(SimpleIntegerProperty price_piece) {
		this.price_piece = price_piece;
	}
	public void setOblast (SimpleStringProperty oblast)
	{
		this.oblast=oblast;
	}
}
