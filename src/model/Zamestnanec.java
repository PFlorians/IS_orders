package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Zamestnanec {
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleIntegerProperty age;
	private SimpleStringProperty gender;
	private SimpleIntegerProperty position;
	private SimpleIntegerProperty task;
	private SimpleIntegerProperty salary;
	private SimpleIntegerProperty activity;
	public Zamestnanec()
	{
		
	}
	public Zamestnanec(SimpleIntegerProperty id, SimpleStringProperty name,SimpleIntegerProperty age,
			SimpleStringProperty gender, SimpleIntegerProperty position,
			SimpleIntegerProperty task, SimpleIntegerProperty salary, SimpleIntegerProperty activity)
	{
		this.id=id;
		this.name=name;
		this.age=age;
		this.gender=gender;
		this.position=position;
		this.task=task;
		this.salary=salary;
		this.activity=activity;
	}
	public int getActivity()
	{
		return activity.get();
	}
	public int getId() {
		return id.get();
	}
	public String getName() {
		return name.get();
	}
	public int getAge() {
		return age.get();
	}
	public String getGender() {
		return gender.get();
	}
	public int getPosition() {
		return position.get();
	}
	public int getTask() {
		return task.get();
	}
	public int getSalary() {
		return salary.get();
	}
	public void setActivity(SimpleIntegerProperty activity)
	{
		this.activity=activity;
	}
	public void setId(SimpleIntegerProperty id) {
		this.id = id;
	}
	public void setName(SimpleStringProperty name) {
		this.name = name;
	}
	public void setAge(SimpleIntegerProperty age) {
		this.age = age;
	}
	public void setGender(SimpleStringProperty gender) {
		this.gender = gender;
	}
	public void setPosition(SimpleIntegerProperty position) {
		this.position = position;
	}
	public void setTask(SimpleIntegerProperty task) {
		this.task = task;
	}
	public void setSalary(SimpleIntegerProperty salary) {
		this.salary = salary;
	}
	
}
