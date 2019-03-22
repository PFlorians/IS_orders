package model;
import view.*;
import javafx.beans.property.*;

public class Objednavka {
	//hlavne data consignements
	private SimpleIntegerProperty id;
	private SimpleStringProperty title;
	private SimpleStringProperty sender;
	private SimpleStringProperty send_date;
	private SimpleStringProperty recipient;
	private SimpleStringProperty rec_date;
	private SimpleFloatProperty price;
	private SimpleStringProperty transport;
	private SimpleStringProperty priority;
	private SimpleIntegerProperty quantity;
	private SimpleIntegerProperty status;
	//ostatne, doplnkove data a rozne kombinacie
	 
	public Objednavka()
	{
		
	}
	public Objednavka(SimpleIntegerProperty id, SimpleStringProperty title, 
					 SimpleStringProperty sender, SimpleStringProperty send_date, 
					 SimpleStringProperty recipient, SimpleStringProperty rec_date,
					 SimpleFloatProperty price, SimpleStringProperty transport,
					 SimpleStringProperty priority, SimpleIntegerProperty quantity,
					 SimpleIntegerProperty status)
	{
		this.id=id;
		this.title=title;
		this.sender=sender;
		this.send_date=send_date;
		this.recipient=recipient;
		this.rec_date=rec_date;
		this.price=price;
		this.transport=transport;
		this.priority=priority;
		this.quantity=quantity;
		this.status=status;
	}

	public int getId() {
		return this.id.get();
	}

	public void setId(SimpleIntegerProperty id) {
		this.id = id;
	}

	public String getTitle() {
		return title.get();
	}

	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}

	public String getSender() {
		return sender.get();
	}

	public void setSender(SimpleStringProperty sender) {
		this.sender = sender;
	}

	public String getSend_date() {
		return send_date.get();
	}

	public void setSend_date(SimpleStringProperty send_date) {
		this.send_date = send_date;
	}

	public String getRecipient() {
		return recipient.get();
	}

	public void setRecipient(SimpleStringProperty recipient) {
		this.recipient = recipient;
	}

	public String getRec_date() {
		return rec_date.get();
	}

	public void setRec_date(SimpleStringProperty rec_date) {
		this.rec_date = rec_date;
	}

	public float getPrice() {
		return price.get();
	}

	public void setPrice(SimpleFloatProperty price) {
		this.price = price;
	}

	public String getTransport() {
		return transport.get();
	}

	public void setTransport(SimpleStringProperty transport) {
		this.transport = transport;
	}

	public String getPriority() {
		return priority.get();
	}

	public void setPriority(SimpleStringProperty priority) {
		this.priority = priority;
	}

	public int getQuantity() {
		return quantity.get();
	}

	public void setQuantity(SimpleIntegerProperty quantity) {
		this.quantity = quantity;
	}

	public int getStatus() {
		return status.get();
	}

	public void setStatus(SimpleIntegerProperty status) {
		this.status = status;
	}
	
}
