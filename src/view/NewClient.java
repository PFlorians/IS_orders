package view;
import java.time.LocalDate;

import javafx.*;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.event.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.*;
import javafx.scene.input.*;

public class NewClient extends Application{
	private NewClient me;
	private Stage stage;
	private GridPane gl;
	private Scene s;
	
	public NewClient()
	{
		this.me=this;
	}
	
	public void init()//popup version of window initialize in parent first by setting stage
	{
		try
		{
			this.stage.setTitle("Register new Client");
			this.stage.setScene(createDialog());
			this.stage.initModality(Modality.APPLICATION_MODAL);
			this.stage.showAndWait();
		}
		catch(Exception e)
		{
			System.out.println("Singular menu exception " + e);
			e.printStackTrace();
		}
	}
	public void init(String args[])
	{
		try
		{
			launch(args);
		}
		catch(Exception e)
		{
			System.out.println("New Client exception " + e);
			e.printStackTrace();
		}
	}
	public void start(Stage stage)
	{
		this.stage=stage;
		System.out.println("super stage: " + this.stage);
		createDialog();
	}
	protected Scene createDialog()
	{
		Scene s1;
		GridPane gp=new GridPane();
		ColumnConstraints col0, col1;
		RowConstraints row0, row1, row2, row3, row4, row5, row6, row7, row8, row9;
		Label namel, ownerl, phonel, maill, cityl, addrl, statel, notesl;
		TextField name, owner, phone, mail, city, addr, state;
		TextArea notes;
		VBox actions;
		
		col0=new ColumnConstraints();
		col1=new ColumnConstraints();
		
		row0=new RowConstraints();
		row1=new RowConstraints();
		row2=new RowConstraints();
		row3=new RowConstraints();
		row4=new RowConstraints();
		row5=new RowConstraints();
		row6=new RowConstraints();
		row7=new RowConstraints();
		row8=new RowConstraints();
		row9=new RowConstraints();
		
		col0.setHalignment(HPos.CENTER);
		col1.setHalignment(HPos.CENTER);
		col0.setHgrow(Priority.ALWAYS);
		col1.setHgrow(Priority.ALWAYS);
		
		row0.setValignment(VPos.CENTER);
		row1.setValignment(VPos.CENTER);
		row2.setValignment(VPos.CENTER);
		row3.setValignment(VPos.CENTER);
		row4.setValignment(VPos.CENTER);
		row5.setValignment(VPos.CENTER);
		row6.setValignment(VPos.CENTER);
		row7.setValignment(VPos.CENTER);
		row8.setValignment(VPos.CENTER);
		row9.setValignment(VPos.CENTER);
		
		row0.setVgrow(Priority.ALWAYS);
		row1.setVgrow(Priority.ALWAYS);
		row2.setVgrow(Priority.ALWAYS);
		row3.setVgrow(Priority.ALWAYS);
		row4.setVgrow(Priority.ALWAYS);
		row5.setVgrow(Priority.ALWAYS);
		row6.setVgrow(Priority.ALWAYS);
		row7.setVgrow(Priority.ALWAYS);
		row8.setVgrow(Priority.ALWAYS);
		row9.setVgrow(Priority.ALWAYS);
		
		gp.getColumnConstraints().addAll(col0, col1);
		gp.getRowConstraints().addAll(row0, row1, row2, row3, row4, row5, row6, row7, row8, row9);
		gp.setHgap(15);
		gp.setVgap(10);
		gp.setAlignment(Pos.CENTER);
		gp.setPadding(new Insets(5, 5, 5, 5));
		gp.setPrefSize(600, 400);
		
		namel=new Label("Name");
		namel.autosize();
		namel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(namel, 0, 0);
		
		name=new TextField();
		name.autosize();
		name.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		name.setPrefSize(50, 30);
		name.setMinSize(50, 30);
		gp.add(name, 0, 1);
		
		ownerl=new Label("Owner");
		ownerl.autosize();
		ownerl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(ownerl, 1, 0);
		
		owner=new TextField();
		owner.autosize();
		owner.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		owner.setMinSize(50, 30);
		gp.add(owner, 1, 1);
				
		phonel=new Label("Phone");
		phonel.autosize();
		phonel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(phonel, 0, 2);
		
		phone=new TextField();
		phone.autosize();
		phone.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		phone.setMinSize(50, 30);
		gp.add(phone, 0, 3);
		
		maill=new Label("Mail");
		maill.autosize();
		maill.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(maill, 1, 2);
		
		mail=new TextField();
		mail.autosize();
		mail.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		mail.setMinSize(50, 30);
		gp.add(mail, 1, 3);
		
		cityl=new Label("City");
		cityl.autosize();
		cityl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(cityl, 0, 4);
		
		city=new TextField();
		city.autosize();
		city.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		city.setMinSize(50, 30);
		gp.add(city, 0, 5);
				
		addrl=new Label("Address");
		addrl.autosize();
		addrl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(addrl,  1, 4);
		
		addr=new TextField();
		addr.autosize();
		addr.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		addr.setMinSize(50, 30);
		gp.add(addr, 1, 5);
		
		statel=new Label("State/Province");
		statel.autosize();
		statel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(statel, 0, 6);
		
		state=new TextField();
		state.autosize();
		state.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		state.setMinSize(50, 30);
		gp.add(state, 0, 7);
		
		notesl=new Label("Notes");
		notesl.autosize();
		notesl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(notesl, 0, 8);
		
		notes=new TextArea();
		notes.autosize();
		notes.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		notes.setMinSize(50, 30);
		gp.add(notes, 0, 9);
		
		actions=createActions();
		actions.setAccessibleHelp("Choose action");
		gp.add(actions, 1, 9);
		
		s1=new Scene(gp, 600, 400);
		//gp.setGridLinesVisible(true);
		this.gl=gp;
		this.s=s1;
		return s1;
	}
	private VBox createActions()
	{
		VBox v=new VBox();
		Label actions=new Label("Actions:");
		Button submit=new Button("submit");
		Button cancel=new Button("cancel");
		
		actions.autosize();
		actions.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		submit.autosize();
		cancel.autosize();
		submit.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		cancel.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		submit.setOnAction(new closeClient());
		cancel.setOnAction(new closeClient());
		v.getChildren().addAll(actions, submit, cancel);
		return v;
	}
	public void initStage()
	{
		this.stage=new Stage();
	}
	public NewClient getInstance()
	{
		return this.me;
	}
	public Stage getStage()
	{
		return this.stage;
	}
	public void setStage(Stage stage)
	{
		this.stage=stage;
	}
	public Scene getScene()
	{
		return this.s;
	}
	public GridPane getPane()
	{
		return this.gl;
	}
	class closeClient implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			stage.close();
		}
	}
}
