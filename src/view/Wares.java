package view;
import java.time.LocalDate;
import java.util.*;
import javafx.*;
import javafx.application.*;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.*;
import javafx.util.Duration;
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
import model.*;
import view.ConsViewer.saveChanges;
import controller.*;
import java.sql.*;

public class Wares extends Application{
	private Wares me;
	private Stage stage;
	private GridPane gl;
	private Scene s;
	private TableView<Sklad> prehlad;
	private ObservableList<Sklad> data; 
	private Connection c;
	private ChoiceBox<String> product=new ChoiceBox<String>();
	private VBox vchoice=new VBox();
	private TextField quantity=new TextField();
	private TextField pp=new TextField();
	private TextField sum=new TextField();
	private Button save=new Button("save");
	private Slider quantsc=new Slider(0,100,50);
	private Label chosen=new Label();
	private Label chosenl=new Label("Amount: ");
	private TableColumn<Sklad, String> produkt=new TableColumn<Sklad, String>("Goods");
	private TableColumn<Sklad, Integer> mnozstvo=new TableColumn<Sklad, Integer>("Quantity");
	private TableColumn<Sklad, Integer> cena=new TableColumn<Sklad, Integer>("Price");
	private float finalQuantity=-1;
	private float finalSum=-1;
	private String finalProduct=null;
	private int chosenIndex;
	private boolean superuser=false;
	private ChoiceBox<String> place;
	public Wares()
	{
		this.me=this;
	}
	public Wares(Connection c)
	{
		this.c=c;
	}
	public Wares(Connection c, boolean superuser)
	{
		this.c=c;
		this.superuser=superuser;
	}
	public void init()//popup version of window initialize in parent first by setting stage
	{
		try
		{
			this.stage.setTitle("Available wares");
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
	private Scene createDialog()
	{
		Scene s1;
		BorderPane bp=new BorderPane();
		if(this.superuser==true)
		{
			bp.setTop(menuBar());
		}
		bp.setCenter(createOffer());
		s1=new Scene(bp, 600, 400);
		this.s=s1;
		return s1;
	}
	protected MenuBar menuBar()
	{
		MenuBar mb=new MenuBar();
		mb.prefWidthProperty().bind(this.stage.widthProperty());
		
		Menu file=new Menu("Actions");
		/*search klient v datab*/
		//MenuItem compare=new MenuItem("Compare");
		/*Filter podmienky apod.*/
		MenuItem edit=new MenuItem("Edit");
		edit.setOnAction(new enableEdit());
		//file.getItems().add(compare);
		file.getItems().add(edit);
		
		mb.getMenus().addAll(file);
		
		return mb;
	}
	protected GridPane createOffer()
	{
		GridPane gp=new GridPane();
		ColumnConstraints col0, col1, col2;
		RowConstraints row0, row1;
		
		col0=new ColumnConstraints();
		col1=new ColumnConstraints();
		col2=new ColumnConstraints();
		
		row0=new RowConstraints();
		row1=new RowConstraints();
		
		col0.setHalignment(HPos.CENTER);
		col1.setHalignment(HPos.CENTER);
		col2.setHalignment(HPos.CENTER);
		
		col0.setHgrow(Priority.ALWAYS);
		col1.setHgrow(Priority.ALWAYS);
		col2.setHgrow(Priority.ALWAYS);
		
		row0.setValignment(VPos.CENTER);
		row1.setValignment(VPos.CENTER);
		row0.setVgrow(Priority.ALWAYS);
		row1.setVgrow(Priority.ALWAYS);
		gp.getColumnConstraints().addAll(col0, col1, col2);
		gp.getRowConstraints().addAll(row0, row1);
		gp.setHgap(15);
		gp.setVgap(10);
		gp.setAlignment(Pos.CENTER);
		gp.setPadding(new Insets(5, 5, 5, 5));
		gp.setPrefSize(500, 300);
		gp.add(table(), 0, 0);
		gp.add(choiceMenu(), 1, 0);
		gp.add(counterMenu(), 2, 0);
		gp.add(bottomMenu(), 0, 1);
		return gp;
	}
	private VBox table()
	{
		VBox v=new VBox();
		Label prehladl;
		v.autosize();
		v.setSpacing(5);
		v.setPadding(new Insets(8, 8, 8, 8));
		
		prehladl=new Label("List of goods");
		prehladl.autosize();
		prehladl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		/*tabulka
		 * 
		 */
		this.prehlad=new TableView<Sklad>();
		this.prehlad.getColumns().addAll(produkt, mnozstvo, cena);
		//koniec definicie
		v.getChildren().addAll(prehladl, this.prehlad);
		return v;
	}
	private void updateTable()
	{
		this.prehlad.setItems(this.data);
	
		produkt.setCellValueFactory(new PropertyValueFactory<Sklad, String>("product"));
		mnozstvo.setCellValueFactory(new PropertyValueFactory<Sklad, Integer>("quantity"));
		cena.setCellValueFactory(new PropertyValueFactory<Sklad, Integer>("price_piece"));
		
		
		this.prehlad.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		//koniec definicie
	}
	private VBox choiceMenu()
	{
		
		Label vyberl=new Label("Choice of goods");
		Label placel=new Label("Place");
		Label productl=new Label("Product");
		
		//toto zmenene dynamicky
		place=new WaresManager(this.c).getAllPlaces();/*new ChoiceBox<String>(FXCollections.observableArrayList(
				"Leeds", "Jersey", "Hong_Kong", "Bruxelles", "Washington", "All"));*/
		
		vchoice.setSpacing(5);
		vchoice.setPadding(new Insets(8,8,8,8));
		vyberl.autosize();
		placel.autosize();
		placel.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		productl.autosize();
		productl.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
		place.autosize();
		
		place.getSelectionModel().selectedItemProperty().addListener(new locationChosen());
		sum.setEditable(false);
		quantity.setEditable(false);
		pp.setEditable(false);
		vchoice.getChildren().addAll(vyberl, placel, place);
		vchoice.getChildren().addAll(chosenl, chosen, productl);
		return vchoice;
	}
	private VBox counterMenu()
	{
		VBox v=new VBox();
		Label quantl=new Label("Available: ");
		Label pricel=new Label("Price per piece: ");
		Label suml=new Label("Sum total: ");
		Label sc=new Label("Quantity: ");
		
		
		quantsc.setMin(0);//podla vyberu a mnozstva
		//quantsc.setMax(100);
		quantsc.setOrientation(Orientation.HORIZONTAL);
		quantsc.setPrefSize(20, 20);
		save.autosize();
		save.setDisable(true);
		
		v.getChildren().addAll(quantl, quantity, pricel, pp, sc, quantsc, suml, sum, save);
		return v;
	}
	private HBox bottomMenu()
	{
		HBox h=new HBox();
		Button confirm=new Button("Confirm");
		Button cancel=new Button("Cancel");
		h.setSpacing(5);
		h.setPadding(new Insets(8,8,8,8));
		h.autosize();
		confirm.autosize();
		cancel.autosize();
		cancel.setOnAction(new closeWares());
		confirm.setOnAction(new confirmAction());
		h.getChildren().addAll(confirm, cancel);
		return h;
	}
	public void initStage()
	{
		this.stage=new Stage();
	}
	public Wares getInstance()
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
	public float getFinalQuantity()
	{
		return this.finalQuantity;
	}
	public float getFinalSum()
	{
		return this.finalSum;
	}
	public String getFinalProduct()
	{
		return this.finalProduct;
	}
	class locationChosen implements ChangeListener<String>
	{
		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
		{
			if(!newValue.equals("All"))
			{
				if(vchoice.getChildren().get(vchoice.getChildren().size()-1)==product)
				{
					vchoice.getChildren().remove(vchoice.getChildren().size()-1);
				}
				data=new WaresManager(c).getByPlace(newValue);
			    product=new ChoiceBox<String>(new WaresManager(c).parse(new WaresManager(c).getByPlace(newValue)));
			    product.getSelectionModel().selectedItemProperty().addListener(new dataLoad());
			    chosen.setText(String.valueOf(0));
			    vchoice.getChildren().addAll(product);
			    updateTable();//zmenit hodnoty po vybere
			}
			else//prehlad vsetkych tovarov vo vsetkych skladoch vo vsetkych lokalitach
			{	//cena je priemerom vsetkych cien na dany tovar
				if(vchoice.getChildren().get(vchoice.getChildren().size()-1)==product)
				{
					vchoice.getChildren().remove(vchoice.getChildren().size());
				}
				data=new WaresManager(c).getAllTogether();
				product=new ChoiceBox<String>(new WaresManager(c).parse(new WaresManager(c).getAllTogether()));
				product.getSelectionModel().selectedItemProperty().addListener(new dataLoad());
			    chosen.setText(String.valueOf(0));
			    vchoice.getChildren().addAll(product);
			    updateTable();
			}
		}
	}
	class dataLoad implements ChangeListener<String>
	{
		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
		{
			System.out.println("new " + newValue);
			int i=0;
			while(i<data.size())
			{
				if(!newValue.equals(data.get(i).getProduct()))
				{
					System.out.println("cyklujem");
					i++;
				}
				else
				{
					break;
				}
			}
			if(i<=data.size())//max prvok
			{
				quantity.setText(String.valueOf(data.get(i).getQuantity()));
				pp.setText(String.valueOf(data.get(i).getPrice_piece()));
				quantsc.setMax(data.get(i).getQuantity());
				quantsc.addEventHandler(MouseEvent.MOUSE_DRAGGED, new dragAction());
				finalProduct=newValue;
				chosenIndex=i;
			}
		}
	}
	class dragAction implements EventHandler<Event>
	{
		public void handle(Event e)
		{
			int suma;
			chosen.setText(String.valueOf(Utilitary.round(quantsc.getValue(), 0)));
			suma=(int)Utilitary.round(quantsc.getValue(), 0)*Integer.parseInt(pp.getText());
			sum.setText(String.valueOf(suma));
		}
	}
	class closeWares implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			stage.close();
		}
	}
	class confirmAction implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			if(chosen.getText()!=null && sum.getText()!=null)
			{
				finalQuantity=Float.parseFloat(chosen.getText());
				finalSum=Float.parseFloat(sum.getText());
				stage.close();
			}
			else
			{
				try
				{
					Stage secondaryStage=new Stage();
					ErrorWin error=new ErrorWin(3);
					error.setStage(secondaryStage);
					error.init();
				}
				catch(Exception ex)
				{
					System.out.println(ex);
					ex.printStackTrace();
				}
			}
		}
	}
	class enableEdit implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			pp.setEditable(true);
			save.setDisable(false);
			place.getItems().remove("All");
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
				data.get(chosenIndex).setPrice_piece(new SimpleIntegerProperty(Integer.parseInt(pp.getText())));
				
				data.set(chosenIndex, new WaresManager(c).updateWarehouse(data.get(chosenIndex), place.getValue()));
			}
			catch(Exception ex)
			{
				System.out.println("saving exception " + ex);
				ex.printStackTrace();
			}
			pp.setEditable(false);
			place.getItems().add("All");
			save.setDisable(true);
		}
	}
}
