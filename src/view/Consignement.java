package view;
import java.time.LocalDate;

import javafx.*;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.*;
import javafx.scene.input.*;
import javafx.stage.Stage;

public class Consignement extends Application{
	private Consignement me;
	private Stage stage;
	private GridPane gl;
	private Scene s;
	public Consignement()
	{
		this.me=this;
	}
	public void init(String args[])
	{
		try
		{
			launch(args);
		}
		catch(Exception e)
		{
			System.out.println("Singular menu exception " + e);
			e.printStackTrace();
		}
	}
	public void start(Stage stage)
	{
		this.stage=stage;
		System.out.println("super stage: " + this.stage);
		createMenu();
	}
	private Scene createMenu()
	{
		//vytvori scenu, layout a prvky + akcie kazdy sam
		Scene s1;
		Text title, sender, date1, rcvr, date2, tot, price, priority;
		ChoiceBox<String> cb=new ChoiceBox<String>(FXCollections.observableArrayList("Vehicle", "Plane", "combined"));
		ChoiceBox<String> cb1=new ChoiceBox<String>(FXCollections.observableArrayList(
				"Low", "Normal", "High"));
		DatePicker dp1=new DatePicker();
		DatePicker dp2=new DatePicker();
		TextField tfTitle, seField, rcField, priceField;
		GridPane gl=new GridPane();
		gl.setHgap(10);
		gl.setVgap(10);
		
		//set title
		title=new Text("Title");
		title.setFont(Font.font("Console", FontWeight.NORMAL, 12));
		gl.add(title, 0, 0);
		
		tfTitle=new TextField();
		tfTitle.autosize();
		//set pref size here
		tfTitle.setAccessibleHelp("Name of consignement");
		tfTitle.setFocusTraversable(true);
		gl.add(tfTitle, 1, 0);
		//end setting title
		
		//set sender
		sender=new Text("Title");
		sender.setFont(Font.font("Console", FontWeight.NORMAL, 12));
		gl.add(title, 0, 1);	
		
		seField=new TextField();
		seField.autosize();
		seField.setAccessibleHelp("Ender name of sending comapny");
		seField.setFocusTraversable(true);
		gl.add(seField, 2, 1);
		
		date1=new Text("Date: ");
		date1.setFont(Font.font("Console", FontWeight.NORMAL, 12));
		gl.add(date1, 3, 1);
		
		dp1.setAccessibleHelp("Set earliest date of shipping");
		dp1.autosize();
		dp1.setValue(LocalDate.now());
		dp1.setShowWeekNumbers(true);
		dp1.setFocusTraversable(true);
		gl.add(dp1, 4, 1);
		//end setting sender
		//set rcvr
		rcvr=new Text("Recipient");
		rcvr.setFont(Font.font("Console", FontWeight.NORMAL, 12));
		gl.add(rcvr, 0, 2);
		
		rcField=new TextField();
		rcField.autosize();
		rcField.setAccessibleHelp("Enter name of recipient");
		rcField.setFocusTraversable(true);
		gl.add(rcField, 1, 2);
		
		date2=new Text("Date: ");
		date2.setFont(Font.font("Console", FontWeight.NORMAL, 12));
		gl.add(date2, 2, 2);
		
		dp2.setAccessibleHelp("Set latest date of delivery");
		dp2.autosize();
		dp2.setValue(LocalDate.now());
		dp2.setShowWeekNumbers(true);
		dp2.setFocusTraversable(true);
		gl.add(dp2, 3, 2);
		//end set rcvr
		//start price&transport
		price=new Text("Estimated price");
		price.setAccessibleHelp("Enter type of transportation first");
		price.autosize();
		price.setFont(Font.font("Console", FontWeight.NORMAL, 12));
		gl.add(price,  0, 3);
		
		tot=new Text("Type of transportation");
		tot.autosize();
		tot.setFont(Font.font("Console", FontWeight.NORMAL, 12));
		gl.add(tot, 1, 3);
		
		priority=new Text("Level of priority");
		priority.autosize();
		priority.setFont(Font.font("Console", FontWeight.NORMAL, 12));
		gl.add(priority, 2, 3);
		//end type of transport
		//pricing/details fields
		priceField=new TextField();
		priceField.autosize();
		priceField.setAccessibleHelp("Calculated from transport type*priority");
		priceField.setEditable(false);//automaticka akcia
		gl.add(priceField, 0, 4);
		
		cb.setTooltip(new Tooltip("Select means of transport"));
		cb.autosize();
		cb.setFocusTraversable(true);
		gl.add(cb1,  1, 4);
		
		cb1.setTooltip(new Tooltip("Select priority"));
		cb1.autosize();
		cb1.setFocusTraversable(true);
		gl.add(cb1,  2,  4);
		
		//end pricing details fields
		gl.setGridLinesVisible(true);//debug
		s1=new Scene(gl, 600, 400);//ondefault
		this.gl=gl;
		return s1;
	}
	public Consignement getInstance()
	{
		return this.me;
	}
	public Stage getStage()
	{
		return this.stage;
	}
	public Scene getScene()
	{
		return this.s;
	}
	public GridPane getPane()
	{
		return this.gl;
	}
}
