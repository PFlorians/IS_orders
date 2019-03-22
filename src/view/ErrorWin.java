package view;
import java.time.LocalDate;

import javafx.*;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.stage.*;
import javafx.util.Duration;
import view.Win1.editCons;
import javafx.event.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.*;
import javafx.scene.input.*;

public class ErrorWin extends Application{
	private ErrorWin me;
	private Stage stage;
	private GridPane gl;
	private Scene s;
	/*
	 * typ chybovej hlasky
	 */
	private String error;
	public ErrorWin(int type)
	{
		this.me=this;
		if(type==0)//chyba loginu
		{
			this.error="Name and password MUST be entered";
		}
		else if(type==1)
		{
			this.error="Databse does not exist";
		}
		else if(type==2)
		{
			this.error="Incorrect login information";
		}
		else if(type==3)
		{
			this.error="All fields HAVE to be filled in";
		}
	}
	
	public void init()//popup version of window initialize in parent first by setting stage
	{
		try
		{
			Alert a=new Alert(AlertType.ERROR);
			a.setTitle("Error");
			a.setHeaderText(this.error);
			//a.setOnCloseRequest(new closeError());
			a.showAndWait();
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
	private Scene createDialog()
	{
		Scene s1;
		BorderPane bp=new BorderPane();
		
		this.stage.setTitle("ERROR");
		
		bp.setCenter(initData());
		
		s1=new Scene(bp, 200, 100);
		return s1;
	}
	private VBox initData()
	{
		VBox v=new VBox();
		Alert a=new Alert(AlertType.ERROR);
		a.setTitle("Error");
		a.setContentText(this.error);
		return v;
	}
	public void initStage()
	{
		this.stage=new Stage();
	}
	public ErrorWin getInstance()
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
	class closeError implements EventHandler<DialogEvent>
	{
		public void handle(DialogEvent e)
		{
			System.exit(0);
		}
	}
	
}
