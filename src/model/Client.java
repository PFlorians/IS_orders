package model;
import view.*;
import javafx.beans.property.*;

public class Client {
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleStringProperty owner;
	private SimpleStringProperty phone;
	private SimpleStringProperty mail;
	private SimpleStringProperty city;
	private SimpleStringProperty address;
	private SimpleStringProperty state;
	private SimpleStringProperty notes;
	
	public Client()
	{
		
	}

	public int getId() {
		return id.get();
	}

	public String getName() {
		return name.get();
	}

	public String getOwner() {
		return owner.get();
	}

	public String getPhone() {
		return phone.get();
	}

	public String getMail() {
		return mail.get();
	}

	public String getCity() {
		return city.get();
	}

	public String getAddress() {
		return address.get();
	}

	public String getState() {
		return state.get();
	}

	public String getNotes() {
		return notes.get();
	}

	public void setId(SimpleIntegerProperty id) {
		this.id = id;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public void setOwner(SimpleStringProperty owner) {
		this.owner = owner;
	}

	public void setPhone(SimpleStringProperty phone) {
		this.phone = phone;
	}

	public void setMail(SimpleStringProperty mail) {
		this.mail = mail;
	}

	public void setCity(SimpleStringProperty city) {
		this.city = city;
	}

	public void setAddress(SimpleStringProperty address) {
		this.address = address;
	}

	public void setState(SimpleStringProperty state) {
		this.state = state;
	}

	public void setNotes(SimpleStringProperty notes) {
		this.notes = notes;
	}
}
