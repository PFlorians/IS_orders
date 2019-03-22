package view;
import javafx.*;
import javafx.application.*;
import javafx.stage.*;
import javafx.util.Duration;
import model.Tovar;
import javafx.event.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.*;
import javafx.scene.input.*;
import controller.*;
import java.sql.*;
import model.*;
import javafx.collections.*;
import java.io.*;

public class Win1 extends Application{//pustit prve
	private Stage stage;
	private Win1 me;
	private ScrollPane sp;
	private Scene secondaryScene;
	private Connection c=null;
	private TextField name=null, dbase=null;
	private PasswordField passwd=null;
	private Objednavky objedn=null;
	private TableView<Objednavka> cons;
	private ObservableList<Objednavka> data;
	private VBox v=new VBox();
	/*
	 * filter fields
	 */
	private TextField priceFilter;
	private ChoiceBox<String> productFilter, recipientFilter;
	public Win1()
	{
		this.me=this;
		System.out.println(getClass());
		System.out.println(System.getProperty("user.dir").trim());
		//try{System.out.println(new File(".").getCanonicalPath());}catch(Exception e){System.out.println(e);e.printStackTrace();}
		//System.exit(0);
	}
	public void init(String args[])
	{
		try
		{
			launch(args);
		}
		catch(Exception e)
		{
			System.out.println("konstruktor: " + e);
			e.printStackTrace();
		}
	}
	public void start(Stage stage)
	{
		this.stage=stage;
		this.stage.setScene(createMainWin());
		this.stage.show();
		
	}
	private Scene createMainWin()
	{
		Scene login;
		BorderPane log=new BorderPane();//login window
		
		this.stage.setTitle("Main");
		log.setCenter(login());
		
		login=new Scene(log, 300, 200);
		return login;
	}
	private void createSecondary()
	{
		Scene s1;
		BorderPane bp=new BorderPane();
		
		bp.setTop(initMenu(this.stage));
		bp.setCenter(initMainOutput());
		bp.setRight(initUserMenu());
		s1=new Scene(bp, 600, 400);
		this.secondaryScene=s1;
	}
	private MenuBar initMenu(Stage stage)
	{
		MenuBar menu1=new MenuBar();
		menu1.prefWidthProperty().bind(stage.widthProperty());
		
		Menu file=new Menu("Consignements");
		Menu company=new Menu("Company");
		
		/*uprava zasielok popup window*/
		MenuItem consignement=new MenuItem("New Consignement");
		MenuItem showcons=new MenuItem("Show consignements");
		//consignement.setOnAction(new editCons());
		consignement.setOnAction(new editCons());
		showcons.setOnAction(new viewCons());
		file.getItems().add(consignement);
		file.getItems().add(showcons);
		
		/*
		 * miscellanous options
		 */
		MenuItem manageemp=new MenuItem("Manage employees");
		MenuItem wares=new MenuItem("Goods and prices");
		manageemp.setOnAction(new manageEmployees());
		wares.setOnAction(new waresOpen());
		company.getItems().addAll( manageemp, wares);
		menu1.getMenus().addAll(file, company);
		
		return menu1;
	}
	private VBox initUserMenu()
	{
		VBox v=new VBox();
		Label l=new Label("Menu: ");
		Label filterl=new Label("Filter by price: ");
		Label filterr=new Label("Filter by recipient: ");
		Label filterp=new Label("Filter by product: ");
		Separator s1=new Separator(Orientation.HORIZONTAL);
		Button search=new Button("Search");
		Button newCons=new Button("New Consignement");
		//Button newClient=new Button("New Client");
		Button checkWares=new Button("Check goods");
		Button showCons=new Button("Show Consignements");
		Button manageEmployees=new Button("Manage Employees");
		//Button objednavka=new Button("Nova Objednavka");
		
		//objednavka.autosize();
		//objednavka.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		manageEmployees.autosize();
		manageEmployees.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		showCons.autosize();
		showCons.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		checkWares.autosize();
		checkWares.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		newCons.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		newCons.autosize();
		//newClient.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		//newClient.autosize();
		l.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		l.autosize();
		
		filterl.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		filterl.autosize();
		
		filterr.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		filterr.autosize();
		
		filterp.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		filterp.autosize();
		
		recipientFilter=new ChoiceBox<String>(FXCollections.observableArrayList(new Objednavky(c).getClientNames()));
		productFilter=new ChoiceBox<String>(FXCollections.observableArrayList(new Objednavky(c).getProductNames()));
		priceFilter=new TextField();
		priceFilter.autosize();
		search.setOnAction(new searchFilter());
		newCons.setOnAction(new editCons());
		//newClient.setOnAction(new newClient());
		checkWares.setOnAction(new waresOpen());
		showCons.setOnAction(new viewCons());
		manageEmployees.setOnAction(new manageEmployees());
		//objednavka.setOnAction(new novaObjednavka());
		s1.autosize();
		
		v.setPadding(new Insets(10));
		v.setSpacing(10);
		v.getChildren().addAll(l, newCons, checkWares, showCons, manageEmployees, s1, filterl
				,priceFilter, filterr, recipientFilter, filterp, productFilter, search);
		return v;
	}
	
	private VBox initMainOutput()
	{
		
		Label l1=new Label("Consignements: ");
		
		updateTable();
		
		v.setPadding(new Insets(10));
		v.setSpacing(10);
		v.getChildren().addAll(l1, this.cons);
		
		return v;
	}
	private GridPane login()
	{
		RowConstraints row0, row1, row2, row3;
		ColumnConstraints col0, col1;
		Button submit, cancel;
		Label namel, passl, dbasel;
		GridPane gp=new GridPane();
		
		col0=new ColumnConstraints();
		col1=new ColumnConstraints();
		
		row0=new RowConstraints();
		row1=new RowConstraints();
		row2=new RowConstraints();
		row3=new RowConstraints();
		
		col0.setHalignment(HPos.CENTER);
		col1.setHalignment(HPos.CENTER);
		col0.setHgrow(Priority.ALWAYS);
		col1.setHgrow(Priority.ALWAYS);
		
		row0.setValignment(VPos.CENTER);
		row1.setValignment(VPos.CENTER);
		row2.setValignment(VPos.CENTER);
		row3.setValignment(VPos.CENTER);
		
		row0.setVgrow(Priority.ALWAYS);
		row1.setVgrow(Priority.ALWAYS);
		row2.setVgrow(Priority.ALWAYS);
		row3.setVgrow(Priority.ALWAYS);

		gp.getColumnConstraints().addAll(col0, col1);
		gp.getRowConstraints().addAll(row0, row1, row2, row3);
		gp.setHgap(15);
		gp.setVgap(10);
		gp.setAlignment(Pos.CENTER);
		gp.setPadding(new Insets(5, 5, 5, 5));
		gp.setPrefSize(300, 200);
		
		namel=new Label("Name");
		namel.autosize();
		namel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(namel, 0, 0);
		
		this.name=new TextField();
		this.name.autosize();
		this.name.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		this.name.setAccessibleHelp("Enter user name here");
		gp.add(this.name, 1, 0);
		
		passl=new Label("Password");
		passl.autosize();
		passl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(passl, 0,1);
		
		this.passwd=new PasswordField();
		this.passwd.autosize();
		this.passwd.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		this.passwd.setAccessibleHelp("Enter your database password here.");
		gp.add(this.passwd, 1, 1);
		
		dbasel=new Label("Database");
		dbasel.autosize();
		dbasel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(dbasel, 0, 2);
		
		this.dbase=new TextField();
		this.dbase.autosize();
		this.dbase.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		this.dbase.setAccessibleHelp("If left blank chooses postgres(default).");
		this.dbase.setPromptText("postgres(default).");
		gp.add(this.dbase, 1, 2);
		
		submit=new Button("Submit");
		submit.autosize();
		submit.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		submit.setOnAction(new checkLogin());
		gp.add(submit, 0, 3);
		
		cancel=new Button("Cancel");
		cancel.autosize();
		cancel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		cancel.setOnAction(new terminate());
		gp.add(cancel, 1, 3);
		
		return gp;
	}
	public Win1 getWin()
	{
		return this.me;
	}
	private void updateTable()//vzdy novy request, updatne tabulku
	{
		System.out.println("updating");
		if(this.v.getChildren().size()>0)
		{
			this.v.getChildren().remove(this.cons);
		}
		this.objedn=new Objednavky(this.c, this.name.getText(), this.passwd.getText(), this.dbase.getText());
		/*tabulka
		 * 
		 */
		this.cons=new TableView<Objednavka>();
		this.data=this.objedn.getAll();
		this.cons.setItems(data);
		
		//mala tabulka
		TableColumn<Objednavka, String> title=new TableColumn<Objednavka, String>("Product");
		TableColumn<Objednavka, String> sender=new TableColumn<Objednavka, String>("Sender");
		TableColumn<Objednavka, String> recipient=new TableColumn<Objednavka, String>("Recipient");
		TableColumn<Objednavka, Float> price=new TableColumn<Objednavka, Float>("Price");
		
		title.setCellValueFactory(new PropertyValueFactory<Objednavka, String>("title"));
		sender.setCellValueFactory(new PropertyValueFactory<Objednavka, String>("sender"));
		recipient.setCellValueFactory(new PropertyValueFactory<Objednavka, String>("recipient"));
		price.setCellValueFactory(new PropertyValueFactory<Objednavka, Float>("price"));
		
		this.cons.getColumns().addAll(title, sender, recipient, price);
		if(this.v.getChildren().size()>0)
		{
			v.getChildren().addAll(this.cons);
		}
		this.cons.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			try
			{
				Stage secondaryStage=new Stage();
				ConsViewer nc=new ConsViewer(c);
				nc.setStage(secondaryStage);
				nc.init();
			}
			catch(Exception ex)
			{
				System.out.println(ex);
				ex.printStackTrace();
			}
		});
		/* velka verzia
		TableColumn<Objednavka, Integer> id=new TableColumn<Objednavka, Integer>("ID");
		TableColumn<Objednavka, String> title=new TableColumn<Objednavka, String>("Title");
		TableColumn<Objednavka, String> sender=new TableColumn<Objednavka, String>("Sender");
		TableColumn<Objednavka, String> sdate=new TableColumn<Objednavka, String>("Send date");
		TableColumn<Objednavka, String> recipient=new TableColumn<Objednavka, String>("Recipient");
		TableColumn<Objednavka, String> recdate=new TableColumn<Objednavka, String>("Recieve date");
		TableColumn<Objednavka, Integer> price=new TableColumn<Objednavka, Integer>("Price");
		TableColumn<Objednavka, String> transport=new TableColumn<Objednavka, String>("Transport");
		TableColumn<Objednavka, String> priority=new TableColumn<Objednavka, String>("Priority");
		TableColumn<Objednavka, Integer> quantity=new TableColumn<Objednavka, Integer>("Quantity");
		TableColumn<Objednavka, Integer> status=new TableColumn<Objednavka, Integer>("Status");
		
		id.setCellValueFactory(new PropertyValueFactory<Objednavka, Integer>("id"));
		title.setCellValueFactory(new PropertyValueFactory<Objednavka, String>("title"));
		sender.setCellValueFactory(new PropertyValueFactory<Objednavka, String>("sender"));
		sdate.setCellValueFactory(new PropertyValueFactory<Objednavka, String>("send_date"));
		recipient.setCellValueFactory(new PropertyValueFactory<Objednavka, String>("recipient"));
		recdate.setCellValueFactory(new PropertyValueFactory<Objednavka, String>("rec_date"));
		price.setCellValueFactory(new PropertyValueFactory<Objednavka, Integer>("price"));
		transport.setCellValueFactory(new PropertyValueFactory<Objednavka, String>("transport"));
		priority.setCellValueFactory(new PropertyValueFactory<Objednavka, String>("priority"));
		quantity.setCellValueFactory(new PropertyValueFactory<Objednavka, Integer>("quantity"));
		status.setCellValueFactory(new PropertyValueFactory<Objednavka, Integer>("status"));
		
		//koniec tabulky
		this.cons.getColumns().addAll(id, title, sender, recipient, recdate, price, transport,
										priority, quantity, status);
		*/
		this.cons.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	private void updateTableByData(ObservableList<Objednavka> newData)
	{
		System.out.println("updating");
		if(this.v.getChildren().size()>0)
		{
			this.v.getChildren().remove(this.cons);
		}
		
		/*tabulka
		 * 
		 */
		this.cons=new TableView<Objednavka>();
		this.data=newData;
		this.cons.setItems(data);
		
		//mala tabulka
		TableColumn<Objednavka, String> title=new TableColumn<Objednavka, String>("Product");
		TableColumn<Objednavka, String> sender=new TableColumn<Objednavka, String>("Sender");
		TableColumn<Objednavka, String> recipient=new TableColumn<Objednavka, String>("Recipient");
		TableColumn<Objednavka, Float> price=new TableColumn<Objednavka, Float>("Price");
		
		title.setCellValueFactory(new PropertyValueFactory<Objednavka, String>("title"));
		sender.setCellValueFactory(new PropertyValueFactory<Objednavka, String>("sender"));
		recipient.setCellValueFactory(new PropertyValueFactory<Objednavka, String>("recipient"));
		price.setCellValueFactory(new PropertyValueFactory<Objednavka, Float>("price"));
		
		this.cons.getColumns().addAll(title, sender, recipient, price);
		if(this.v.getChildren().size()>0)
		{
			v.getChildren().addAll(this.cons);
		}
		this.cons.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			try
			{
				Stage secondaryStage=new Stage();
				ConsViewer nc=new ConsViewer(c);
				nc.setStage(secondaryStage);
				nc.init();
			}
			catch(Exception ex)
			{
				System.out.println(ex);
				ex.printStackTrace();
			}
		});
		
		this.cons.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	class checkLogin implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			passwd.setText((passwd.getText()==null)?"":passwd.getText());
			if(name.getText()==null || //passwd.getText()==null || 
					name.getText().trim().isEmpty() /*|| passwd.getText().trim().isEmpty()*/)//hlasenie chyby, osetrenie vynimky
			{
				System.out.println("empty");
				try
				{
					Stage secondaryStage=new Stage();
					ErrorWin error=new ErrorWin(0);
					error.setStage(secondaryStage);
					error.init();
				}
				catch(Exception ex)
				{
					System.out.println(ex);
					ex.printStackTrace();
				}
			}
			else
			{
				//pouzitie defaultnej db
				if(dbase.getText()==null || dbase.getText().trim().isEmpty())
				{
					String n=new String("java_test");//default pre mysql
					Validator v= new Validator(name.getText(), passwd.getText(), n);
					if(v.testDb()==true)
					{
						c=v.test();
					}
					else
					{
						if(v.getDBExistentFlag()==true)
						{
							//warning okno neexistujucej db
							try
							{
								ErrorWin error=new ErrorWin(1);
								error.init();
							}
							catch(Exception ex)
							{
								System.out.println(ex);
								ex.printStackTrace();
							}
						}
						else
						{
							//error nespravny login
							try
							{
								ErrorWin error=new ErrorWin(2);
								error.init();
							}
							catch(Exception ex)
							{
								System.out.println(ex);
								ex.printStackTrace();
							}
						}
					}
					
					//nastavenie po logine
					createSecondary();
					stage.setScene(secondaryScene);
				}
				else
				{
					Validator v= new Validator(name.getText(), passwd.getText(), dbase.getText());
					//System.out.println("db: " + dbase);
					if(v.testDb()==true)
					{
						c=v.test();
					}
					else
					{
						if(v.getDBExistentFlag()==true)
						{
							//warning okno neexistujucej dbtry
							try
							{
								ErrorWin error=new ErrorWin(1);
								error.init();
							}
							catch(Exception ex)
							{
								System.out.println(ex);
								ex.printStackTrace();
							}
						}
						else
						{
							//error nespravny login
							try
							{
								ErrorWin error=new ErrorWin(2);
								error.init();
							}
							catch(Exception ex)
							{
								System.out.println(ex);
								ex.printStackTrace();
							}
						}
					}					
					//nastavenie po logine
					createSecondary();
					stage.setScene(secondaryScene);
				}
			}
		}
	}
	class terminate implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			System.exit(0);
		}
	}
	
	class editCons implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			try
			{
				Stage secondaryStage=new Stage();
				NewConsignement nc=new NewConsignement(c);
				nc.setStage(secondaryStage);
				nc.init();
				updateTable();
			}
			catch(Exception ex)
			{
				System.out.println(ex);
				ex.printStackTrace();
			}
		}
	}
	class newClient implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			try
			{
				Stage secondaryStage=new Stage();
				NewClient n=new NewClient();
				n.setStage(secondaryStage);
				n.init();
				
			}
			catch(Exception ex)
			{
				System.out.println(ex);
				ex.printStackTrace();
			}
		}
	}
	class waresOpen implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			try
			{
				Stage secondaryStage=new Stage();
				Wares w=new Wares(c, true);
				w.setStage(secondaryStage);
				w.init();
			}
			catch(Exception ex)
			{
				System.out.println(ex);
				ex.printStackTrace();
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
			updateTable();
		}
	}
	class manageEmployees implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			try
			{
				Stage tertiaryStage=new Stage();
				Employees w=new Employees(c);
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
	class searchFilter implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			if((!priceFilter.getText().trim().equals("")) || 
					(recipientFilter.getValue()!=null || (!recipientFilter.getValue().trim().equals("nil")))
					|| (productFilter.getValue()!=null || (!productFilter.getValue().trim().equals("nil"))))
			{
				if((!priceFilter.getText().trim().equals("")) && 
						(recipientFilter.getValue()==null || recipientFilter.getValue().trim().equals("nil"))&& 
					(productFilter.getValue()==null || productFilter.getValue().trim().equals("nil")))
				{
					//by price only
					updateTableByData(new Objednavky(c).getByPrice(priceFilter.getText()));
				}
				else if((priceFilter.getText().trim().equals("")) && 
						(recipientFilter.getValue()!=null || (!recipientFilter.getValue().trim().equals("nil")))&& 
						(productFilter.getValue()==null || productFilter.getValue().trim().equals("nil")))
				{
					//by recipient
					updateTableByData(new Objednavky(c).getByRecipient(recipientFilter.getValue()));
				}
				else if((priceFilter.getText().trim().equals("")) && 
						(recipientFilter.getValue()==null || recipientFilter.getValue().trim().equals("nil"))&& 
						(productFilter.getValue()!=null || (!productFilter.getValue().trim().equals("nil"))))
				{
					//by product
					updateTableByData(new Objednavky(c).getByProduct(productFilter.getValue()));
				}
				else if((!priceFilter.getText().trim().equals("")) && 
						(recipientFilter.getValue()!=null || (!recipientFilter.getValue().trim().equals("nil")))&& 
						(productFilter.getValue()==null|| (!productFilter.getValue().trim().equals("nil"))))
				{
					//by price and recipient
					updateTableByData(new Objednavky(c).getByPriceAndRecipient(priceFilter.getText(), recipientFilter.getValue()));
				}
				else if((!priceFilter.getText().trim().equals("")) && 
						(recipientFilter.getValue()==null || recipientFilter.getValue().trim().equals("nil"))&& 
						(productFilter.getValue()!=null || (!productFilter.getValue().trim().equals("nil"))))
				{
					//by price and product
					updateTableByData(new Objednavky(c).getByPriceAndProduct(priceFilter.getText(), productFilter.getValue()));
				}
				else if((priceFilter.getText().trim().equals("")) && 
						(recipientFilter.getValue()!=null || (!recipientFilter.getValue().trim().equals("nil")))&& 
						(productFilter.getValue()!=null || (!productFilter.getValue().trim().equals("nil"))))
				{
					//by recipient and product
					updateTableByData(new Objednavky(c).getByRecipientAndProduct(recipientFilter.getValue(), productFilter.getValue()));
				}
				else if((!priceFilter.getText().trim().equals("")) && 
						(recipientFilter.getValue()!=null || (!recipientFilter.getValue().trim().equals("nil")))&& 
						(productFilter.getValue()!=null || (!productFilter.getValue().trim().equals("nil"))))
				{
					//by all means
					updateTableByData(new Objednavky(c).getByAllMeans(priceFilter.getText(), recipientFilter.getValue(), productFilter.getValue()));
				}
			}
			else
			{
				System.out.println("Nothing to filter");
			}
		}
	}
	
}
