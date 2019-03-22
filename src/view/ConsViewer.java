package view;
import java.time.LocalDate;
import javafx.*;
import javafx.application.*;
import javafx.beans.property.*;
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
import java.sql.*;
import java.lang.*;
import java.util.*;
import controller.*;
import model.*;
import view.NewConsignement.viewCons;

/*
 * legenda stavov: 0, na sklade
 * 				 : 1, pripraveny na transport
 * 			     : 2, v transporte
 * 				 : 3, doruceny
 */
public class ConsViewer extends Application{
	private ConsViewer me;
	private Stage stage;
	private GridPane gl;
	private Scene s;
	private Connection c;
	private ArrayList<Objednavka> data;
	private int currentListIndex=0;
	private Button save;
	private TextField title, send, date1, date2, contents, price, status;
	private ChoiceBox<String> rec;
	private ChoiceBox<String> transport=new ChoiceBox<String>(FXCollections.observableArrayList("Vehicle", "Plane", "combined"));
	private ChoiceBox<String> priority=new ChoiceBox<String>(FXCollections.observableArrayList(
			"Low", "Normal", "High"));
	public ConsViewer()
	{
		this.me=this;
	}
	public ConsViewer(Connection c)
	{
		this.me=this;
		this.c=c;
	}
	public void init()//popup version of window initialize in parent first by setting stage
	{
		try
		{
			this.stage.setTitle("Consignement editor");
			this.stage.setScene(createWin());
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
	private Scene createWin()
	{
		Scene s;
		BorderPane bp=new BorderPane();
		
		bp.setTop(menuBar());
		bp.setCenter(createDialog());
		s=new Scene(bp, 600, 280);
		this.s=s;
		return s;
	}
	protected MenuBar menuBar()
	{
		MenuBar mb=new MenuBar();
		mb.prefWidthProperty().bind(this.stage.widthProperty());
		
		Menu file=new Menu("Actions");
		/*search klient v datab*/
		
		MenuItem filter=new MenuItem("Edit");
		filter.setOnAction(new enableEdit());
		file.getItems().add(filter);
		
		mb.getMenus().addAll(file);
		return mb;
	}
	private GridPane createDialog()
	{
		//Scene s1;
		GridPane gp=new GridPane();
		ColumnConstraints col0, col1, col2;
		Clients cl=new Clients(this.c);
		RowConstraints row0, row1, row2, row3, row4, row5, row6, row7, row8;
		Label titlel, recl, sendl, date1l, date2l, contentsl, pricel, transportl, priorityl, 
				statusl;
		
		Button next, prev, cancel, delete;
		col0=new ColumnConstraints();
		col1=new ColumnConstraints();
		col2=new ColumnConstraints();
		
		row0=new RowConstraints(20);
		row1=new RowConstraints(20);
		row2=new RowConstraints(20);
		row3=new RowConstraints(20);
		row4=new RowConstraints(20);
		row5=new RowConstraints(20);
		row6=new RowConstraints(20);
		row7=new RowConstraints(20);
		row8=new RowConstraints(20);
		
		col0.setHalignment(HPos.CENTER);
		col1.setHalignment(HPos.CENTER);
		col2.setHalignment(HPos.CENTER);
		col0.setHgrow(Priority.ALWAYS);
		col1.setHgrow(Priority.ALWAYS);
		col2.setHgrow(Priority.ALWAYS);
		
		row0.setValignment(VPos.CENTER);
		row1.setValignment(VPos.CENTER);
		row2.setValignment(VPos.CENTER);
		row3.setValignment(VPos.CENTER);
		row4.setValignment(VPos.CENTER);
		row5.setValignment(VPos.CENTER);
		row6.setValignment(VPos.CENTER);
		row7.setValignment(VPos.CENTER);
		row8.setValignment(VPos.CENTER);
		
		row0.setVgrow(Priority.ALWAYS);
		row1.setVgrow(Priority.ALWAYS);
		row2.setVgrow(Priority.ALWAYS);
		row3.setVgrow(Priority.ALWAYS);
		row4.setVgrow(Priority.ALWAYS);
		row5.setVgrow(Priority.ALWAYS);
		row6.setVgrow(Priority.ALWAYS);
		row7.setVgrow(Priority.ALWAYS);
		row8.setVgrow(Priority.ALWAYS);
		
		gp.getColumnConstraints().addAll(col0, col1, col2);
		gp.getRowConstraints().addAll(row0, row1, row2, row3, row4, row5, row6, row7);
		gp.setHgap(10);
		gp.setVgap(8);
		gp.setAlignment(Pos.CENTER);
		gp.setPadding(new Insets(5, 5, 5, 5));
		gp.setPrefSize(600, 250);
		gp.setMaxSize(600, 250);
		//stlpec, riadok
		
		sendl=new Label("sender");
		sendl.autosize();
		sendl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(sendl, 0, 0);
		
		send=new TextField();
		send.autosize();
		send.setFont(Font.font("Consolas", FontWeight.NORMAL, 14));
		send.setEditable(false);
		gp.add(send, 0, 1);
		
		contentsl=new Label("contents");
		contentsl.autosize();
		contentsl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(contentsl, 1, 0);
		
		contents=new TextField();
		contents.autosize();
		contents.setFont(Font.font("Consolas", FontWeight.NORMAL, 14));
		contents.setEditable(false);
		gp.add(contents, 1, 1);
		
		date1l=new Label("date of shipping");
		date1l.autosize();
		date1l.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(date1l, 2, 0);
		
		date1=new TextField();
		date1.autosize();
		date1.setFont(Font.font("Consolas", FontWeight.NORMAL, 14));
		date1.setEditable(false);
		gp.add(date1, 2, 1);
		
		recl=new Label("recipient");
		recl.autosize();
		recl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(recl, 0, 2);
		
		rec=new ChoiceBox<String>(cl.parseClientNames(cl.getNames()));
		rec.autosize();
		rec.setAccessibleHelp("Choose name of recipient");
		rec.setFocusTraversable(true);
		rec.setDisable(true);
		gp.add(rec, 0, 3);
		
		pricel=new Label("price");
		pricel.autosize();
		pricel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(pricel, 1, 2);
		
		price=new TextField();
		price.autosize();
		price.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		price.setEditable(false);
		gp.add(price, 1, 3);
		
		date2l=new Label("date of arrival");
		date2l.autosize();
		date2l.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(date2l, 2, 2);
		
		date2=new TextField();
		date2.autosize();
		date2.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		date2.setEditable(false);
		gp.add(date2, 2, 3);
		
		transportl=new Label("method of transport");
		transportl.autosize();
		transportl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(transportl, 0, 4);
		
		
		transport.autosize();
		transport.setDisable(true);
		gp.add(transport, 0, 5);
		
		priorityl=new Label("priority");
		priorityl.autosize();
		priorityl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(priorityl, 1, 4);
		
		priority.autosize();
		priority.setDisable(true);
		gp.add(priority, 1, 5);
		
		statusl=new Label("status");
		statusl.autosize();
		statusl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(statusl, 2, 4);
		
		status=new TextField();
		status.autosize();
		status.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		status.setEditable(false);
		gp.add(status, 2, 5);
		
		next=new Button("Next");
		next.autosize();
		next.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		next.setOnAction(new nxtAction());
		gp.add(next,  2, 6);
		
		cancel=new Button("Close");
		cancel.autosize();
		cancel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		cancel.setOnAction(new closeViewer());
		gp.add(cancel,  1, 6);
		
		prev=new Button("Previous");
		prev.autosize();
		prev.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		prev.setOnAction(new prevAction());
		gp.add(prev,  0, 6);
		
		delete=new Button("Delete");
		delete.autosize();
		delete.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		delete.setOnAction(new deleteCons());
		delete.setAccessibleHelp("Delete Consignement");
		gp.add(delete,  2, 7);
		
		save=new Button("Save");
		save.autosize();
		save.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(save, 1, 7);
		//s1=new Scene(gp, 600, 250);
		this.data=new Objednavky(c).getAllInArray();
		update(this.currentListIndex);
		//gp.setGridLinesVisible(true);
		this.gl=gp;
		//this.s=s1;
		return gp;
	}
	public void initStage()
	{
		this.stage=new Stage();
	}
	public ConsViewer getInstance()
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
	private void update(int index)
	{
		//this.title.setText(this.data.get(index).getTitle());
		this.send.setText(this.data.get(index).getSender());
		this.contents.setText(this.data.get(index).getTitle());
		this.date1.setText(this.data.get(index).getSend_date());
		this.rec.setValue(this.data.get(index).getRecipient());
		//this.rec.setText(this.data.get(index).getRecipient());
		this.price.setText(Float.toString(this.data.get(index).getPrice()));
		this.date2.setText(this.data.get(index).getRec_date());
		this.transport.setValue(this.data.get(index).getTransport());
		this.priority.setValue(this.data.get(index).getPriority());
		if(this.data.get(index).getStatus()==0)
		{
			this.status.setText("na sklade");
		}
		else if(this.data.get(index).getStatus()==1)
		{
			this.status.setText("pripraveny na transport");
		}
		else if(this.data.get(index).getStatus()==2)
		{
			this.status.setText("transport");
		}
		else if(this.data.get(index).getStatus()==3)
		{
			this.status.setText("dorucene");
		}
	}
	class nxtAction implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			currentListIndex++;
			if(currentListIndex <= data.size()-1)
			{
				update(currentListIndex);
			}
			else
			{
				currentListIndex=0;
				update(currentListIndex);
			}
		}
	}
	class prevAction implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			currentListIndex--;
			if(currentListIndex >= 0)
			{
				update(currentListIndex);
			}
			else
			{
				currentListIndex=data.size()-1;
				update(currentListIndex);
			}
		}
	}
	class closeViewer implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			stage.close();
		}
	}
	class deleteCons implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			new Objednavky(c).deleteCons(data.get(currentListIndex));
			data=new Objednavky(c).getAllInArray();
			currentListIndex=0;
			update(currentListIndex);
		}
	}
	class enableEdit implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			rec.setDisable(false);
			send.setEditable(true); 
			date1.setEditable(true);
			date2.setEditable(true);
			contents.setEditable(true); 
			price.setEditable(true); 
			transport.setDisable(false); 
			priority.setDisable(false); 
			status.setEditable(true);
			save.setOnAction(new saveChanges());
		}
	}
	class saveChanges implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			save.setOnAction(null);
			try
			{
			data.get(currentListIndex).setTitle(new SimpleStringProperty(contents.getText()));
			data.get(currentListIndex).setRecipient(new SimpleStringProperty(rec.getValue().toString()));
			data.get(currentListIndex).setSend_date(new SimpleStringProperty(date1.getText()));
			data.get(currentListIndex).setSender(new SimpleStringProperty(send.getText()));
			data.get(currentListIndex).setRec_date(new SimpleStringProperty(date2.getText()));
			data.get(currentListIndex).setPrice(new SimpleFloatProperty(Float.parseFloat(price.getText())));
			data.get(currentListIndex).setTransport(new SimpleStringProperty(transport.getValue().toString()));
			data.get(currentListIndex).setPriority(new SimpleStringProperty(priority.getValue()));
			data.get(currentListIndex).setStatus(new SimpleIntegerProperty(new StatusConverter().convert(status.getText())));
		
			data.set(currentListIndex, new Objednavky(c).updateCons(data.get(currentListIndex)));
			}
			catch(Exception ex)
			{
				System.out.println("saving exception " + ex);
				ex.printStackTrace();
			}
			rec.setDisable(true);
			send.setEditable(false); 
			date1.setEditable(false);
			date2.setEditable(false);
			contents.setEditable(false); 
			price.setEditable(false); 
			transport.setDisable(true); 
			priority.setDisable(true); 
			status.setEditable(false);
		}
	}
}
