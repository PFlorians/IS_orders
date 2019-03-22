package view;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.*;
import javafx.application.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.util.StringConverter;
import view.Win1.editCons;
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
import javafx.stage.Stage;
import java.sql.*;
import controller.*;
import model.*;

public class NewConsignement extends Application{
	private NewConsignement me;
	private Stage stage;
	private GridPane gl;
	private Scene s;
	private final String pattern = "yyyy-MM-dd";
	private DatePicker dp1=new DatePicker();
	private DatePicker dp2=new DatePicker();
	private TextField tfTitle, seField, priceField, quantField;
	private ChoiceBox<String> reciever, seBox;
	private Connection c;
	//transport
	private ChoiceBox<String> cb=new ChoiceBox<String>(FXCollections.observableArrayList("Vehicle", "Plane", "combined"));
	//priority
	private ChoiceBox<String> cb1=new ChoiceBox<String>(FXCollections.observableArrayList(
			"Low", "Normal", "High"));
	public NewConsignement()
	{
		this.me=this;
	}
	public NewConsignement(Connection c)
	{
		this.me=this;
		this.c=c;
	}
	public NewConsignement(boolean createNewStage)
	{
		this.me=this;
	}
	public void init()//popup version of window initialize in parent first by setting stage
	{
		try
		{
			this.stage.setTitle("Create New Consignement");
			this.stage.setScene(createFormular());
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
			System.out.println("Singular menu exception " + e);
			e.printStackTrace();
		}
	}
	public void start(Stage stage)
	{
		this.stage=stage;
		System.out.println("super stage: " + this.stage);
		try
		{
			this.stage.setTitle("Create New Consignement");
			this.stage.setScene(createFormular());
		}
		catch(Exception e)
		{
			System.out.println("exception: " +e);
			e.printStackTrace();
			System.exit(1);
		}
	}
	protected Scene createFormular()
	{
		Scene s1;
		BorderPane bp=new BorderPane();
		bp.setTop(menuBar());
		bp.setCenter(createMenu());
		s1=new Scene(bp, 600, 400);
		this.s=s1;
		return s1;
	}
	protected MenuBar menuBar()
	{
		MenuBar mb=new MenuBar();
		mb.prefWidthProperty().bind(this.stage.widthProperty());
		
		//Menu file=new Menu("Actions");
		/*search klient v datab*/
		//MenuItem search=new MenuItem("Search client");
		/*Filter podmienky apod.*/
		//MenuItem filter=new MenuItem("Filter search");
		
		//file.getItems().add(search);
	//	file.getItems().add(filter);
		
		Menu show=new Menu("Show");
		MenuItem showCons=new MenuItem("Show Consignements");
		showCons.setOnAction(new viewCons());
		
		show.getItems().add(showCons);
		
		mb.getMenus().addAll(show);
		
		return mb;
	}
	protected GridPane createMenu()
	{
		//vytvori scenu, layout a prvky + akcie kazdy sam
		//Scene s1;
		Text title, sender, date1, rcvr, date2, tot, price, priority, quantityl;
		Clients cl=new Clients(this.c);
		Button done=new Button("Submit");
		Button cancel=new Button("Cancel");
		Button tovar=new Button("Choose wares");
		GridPane gl=new GridPane();
		RowConstraints row1, row2, row3, row4, row5, row6;
		ColumnConstraints col1=new ColumnConstraints();
		ColumnConstraints col2, col3;
		col2=new ColumnConstraints();
		col3=new ColumnConstraints();
		//konverzia formatu datumu
		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = 
                DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        dp1.setConverter(converter);
        dp1.setPromptText(pattern.toLowerCase());
        dp1.requestFocus();
        
        dp2.setConverter(converter);
        dp2.setPromptText(pattern.toLowerCase());
        dp2.requestFocus();
        //koniec nastavovania konverzie
		col1.setHalignment(HPos.CENTER);
		col2.setHalignment(HPos.CENTER);
		col3.setHalignment(HPos.CENTER);
		col1.setHgrow(Priority.ALWAYS);
		col2.setHgrow(Priority.ALWAYS);
		col3.setHgrow(Priority.ALWAYS);
		
		row1=new RowConstraints();
		row2=new RowConstraints();
		row3=new RowConstraints();
		row4=new RowConstraints();
		row5=new RowConstraints();
		row6=new RowConstraints();
		row1.setVgrow(Priority.ALWAYS);
		row2.setVgrow(Priority.ALWAYS);
		row3.setVgrow(Priority.ALWAYS);
		row4.setVgrow(Priority.ALWAYS);
		row5.setVgrow(Priority.ALWAYS);
		row6.setVgrow(Priority.ALWAYS);
		
		row1.setValignment(VPos.CENTER);
		row2.setValignment(VPos.CENTER);
		row3.setValignment(VPos.CENTER);
		row4.setValignment(VPos.CENTER);
		row5.setValignment(VPos.CENTER);
		row6.setValignment(VPos.CENTER);
		gl.getColumnConstraints().addAll(col1, col2, col3);
		gl.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6);
		gl.setHgap(20);
		gl.setVgap(20);
		gl.setAlignment(Pos.CENTER);
		gl.setPadding(new Insets(10, 10, 10, 10));
		gl.setPrefSize(600, 400);
		
		//set title
		title=new Text("Product");
		title.setFont(Font.font("Console", FontWeight.NORMAL, 12));
		gl.add(title, 0, 0);
		
		tfTitle=new TextField();
		tfTitle.autosize();
		//set pref size here
		tfTitle.setAccessibleHelp("Type of product");
		tfTitle.setEditable(false);
		tfTitle.setFocusTraversable(true);
		gl.add(tfTitle, 1, 0);
		
		tovar.autosize();
		tovar.setAccessibleHelp("Choosing wares in warehouses");
		tovar.setFocusTraversable(true);
		tovar.setOnAction(new waresOpen());
		gl.add(tovar, 3, 0);
		//end setting title
		
		//set sender
		sender=new Text("Sender");
		sender.setFont(Font.font("Console", FontWeight.NORMAL, 12));
		gl.add(sender, 0, 1);	
		
		/*seField=new TextField();
		seField.autosize();
		seField.setAccessibleHelp("Name of sending comapny");
		seField.setFocusTraversable(true);
		seField.setText("Evil corp.");
		seField.setEditable(false);//vyplni sa samo*/
		seBox=new ChoiceBox<String>(cl.parseClientNames(cl.getNames()));
		seBox.autosize();
		seBox.setAccessibleHelp("Choose name of sender");
		seBox.setFocusTraversable(true);
		gl.add(seBox, 1, 1);
		
		date1=new Text("Date: ");
		date1.setFont(Font.font("Console", FontWeight.NORMAL, 12));
		gl.add(date1, 2, 1);
		
		dp1.setAccessibleHelp("Set earliest date of shipping");
		dp1.autosize();
		dp1.setValue(LocalDate.now());
		dp1.setShowWeekNumbers(true);
		dp1.setFocusTraversable(true);
		gl.add(dp1, 3, 1);
		//end setting sender
		//set rcvr
		rcvr=new Text("Recipient");
		rcvr.setFont(Font.font("Console", FontWeight.NORMAL, 12));
		gl.add(rcvr, 0, 2);
		
		reciever=new ChoiceBox<String>(cl.parseClientNames(cl.getNames()));
		reciever.autosize();
		reciever.setAccessibleHelp("Choose name of recipient");
		reciever.setFocusTraversable(true);
		gl.add(reciever, 1, 2);
		
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
		
		quantityl=new Text("Quantity");
		quantityl.setAccessibleHelp("Chosen quantity of goods");
		quantityl.autosize();
		quantityl.setFont(Font.font("Console", FontWeight.NORMAL, 12));
		gl.add(quantityl, 1, 3);
		
		tot=new Text("Type of transportation");
		tot.autosize();
		tot.setFont(Font.font("Console", FontWeight.NORMAL, 12));
		gl.add(tot, 2, 3);
		
		priority=new Text("Level of priority");
		priority.autosize();
		priority.setFont(Font.font("Console", FontWeight.NORMAL, 12));
		gl.add(priority, 3, 3);
		//end type of transport
		//pricing/details fields
		priceField=new TextField();
		priceField.autosize();
		priceField.setAccessibleHelp("Calculated from sum of goods");
		priceField.setEditable(false);//automaticka akcia
		gl.add(priceField, 0, 4);
		
		quantField=new TextField();
		quantField.autosize();
		quantField.setAccessibleHelp("Quantity");
		quantField.setEditable(false);//automaticka akcia
		gl.add(quantField, 1, 4);
		
		cb.setTooltip(new Tooltip("Select means of transport"));
		cb.autosize();
		cb.setFocusTraversable(true);
		gl.add(cb,  2, 4);
		
		cb1.setTooltip(new Tooltip("Select priority"));
		cb1.autosize();
		cb1.setFocusTraversable(true);
		gl.add(cb1,  3,  4);
		
		//end pricing details fields
		//start confirm buttons
		done.autosize();
		done.setAccessibleHelp("Confirm consignement");
		done.setFont(Font.font("Console", FontWeight.NORMAL, 12));
		done.setPrefSize(80, 35);
		done.setOnAction(new submitAction());
		gl.add(done,  0, 5);
		
		cancel.autosize();
		cancel.setAccessibleHelp("Cancel consignement");
		cancel.setFont(Font.font("Console", FontWeight.NORMAL, 12));
		cancel.setPrefSize(80, 35);
		cancel.setOnAction(new closeCons());
		gl.add(cancel, 1, 5);
		
		//end confirm buttons
		//gl.setGridLinesVisible(true);//debug
		//s1=new Scene(gl, 600, 400);//ondefault
		this.gl=gl;
		//this.s=s1;
		//this.stage.setScene(s1);
		//this.stage.show();
		return gl;
	}
	private boolean filledInFlag()
	{
		boolean ret=false;
		ret=(this.cb.getValue()!=null)?true:false;
		ret=(this.cb1.getValue()!=null)?true:false;
		ret=(this.dp1.getValue()!=null)?true:false;
		ret=(this.dp2.getValue()!=null)?true:false;
		ret=(!this.tfTitle.getText().equals(""))?true:false;
		ret=(this.seBox.getValue()!=null)?true:false;
		ret=(this.reciever.getValue()!=null)?true:false;
		ret=(!this.priceField.getText().equals(""))?true:false;
	
		return ret;
	}
	private boolean checkInput()
	{
		if(!filledInFlag())
		{
			try
			{
				ErrorWin error=new ErrorWin(3);
				error.init();
			}
			catch(Exception ex)
			{
				System.out.println(ex);
				ex.printStackTrace();
			}
			return false;
		}
		return true;
	}
	public void initStage()
	{
		this.stage=new Stage();
	}
	public NewConsignement getInstance()
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
	class closeCons implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			stage.close();
		}
	}
	class waresOpen implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			try
			{
				Stage tertiaryStage=new Stage();
				Wares w=new Wares(c);
				w.setStage(tertiaryStage);
				w.init();
				if(w.getFinalSum()!=-1 && w.getFinalQuantity()!=-1)
				{
					priceField.setText(String.valueOf(w.getFinalSum()));
					tfTitle.setText(w.getFinalProduct());
					quantField.setText(String.valueOf(w.getFinalQuantity()));
				}
			}
			catch(Exception ex)
			{
				System.out.println(ex);
				ex.printStackTrace();
			}
		}
	}
	class submitAction implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			if(checkInput())
			{
				Objednavka o=new Objednavka();
				o.setTitle(new SimpleStringProperty(tfTitle.getText()));
				o.setSender(new SimpleStringProperty(String.valueOf(seBox.getValue())));
				o.setSend_date(new SimpleStringProperty(String.valueOf(dp1.getValue())));
				o.setRecipient(new SimpleStringProperty(String.valueOf(reciever.getValue())));
				o.setRec_date(new SimpleStringProperty(String.valueOf(dp2.getValue())));
				o.setPrice(new SimpleFloatProperty(Float.parseFloat(priceField.getText())));
				o.setQuantity(new SimpleIntegerProperty((int)Float.parseFloat(quantField.getText())));
				o.setTransport(new SimpleStringProperty(cb.getValue()));
				o.setStatus(new SimpleIntegerProperty(0));
				o.setPriority(new SimpleStringProperty(cb1.getValue()));
				new Objednavky(c).insertCons(o);
				stage.close();
			}
			
		}
	}
	class viewCons implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			try
			{
				Stage tertiaryStage=new Stage();
				ConsViewer w=new ConsViewer(c);
				w.setStage(tertiaryStage);
				w.init();
			}
			catch(Exception ex)
			{
				System.out.println(ex);
				ex.printStackTrace();
			}
		}
	}
}
