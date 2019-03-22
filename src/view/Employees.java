package view;
import java.time.LocalDate;
import java.sql.*;
import javafx.*;
import javafx.application.*;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.stage.*;
import javafx.util.Duration;
import view.ConsViewer.closeViewer;
import view.ConsViewer.nxtAction;
import view.ConsViewer.prevAction;
import view.ConsViewer.saveChanges;
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
import controller.*;
import model.*;
import java.lang.*;
import java.util.*;

/*
 * activity stavy
 * 0 - no activity
 * 1 - ready to transport
 * 2 - transporting
 * 3 - delivered
 */
public class Employees extends Application{
	private Employees me;
	private Stage stage;
	private GridPane gl;
	private Scene s;
	private TextField name, age, salary, gender, position, tsk, activity;
	private ChoiceBox<String> pos=new ChoiceBox<String>(
			FXCollections.observableArrayList("driver", "accountant", "manager"));
	private ChoiceBox<String> act=new ChoiceBox<String>(
			FXCollections.observableArrayList("no activity", "ready to transport", "transporting", "delivered"));
	private ChoiceBox<Integer> task;
	private Button submit=new Button("save");
	private int currentListIndex=0;
	private ArrayList<Zamestnanec> data;//=new EmployeeManager().getAllInArray();
	private Connection c;
	
	public Employees()
	{
		this.me=this;
	}
	public Employees(Connection c)
	{
		this.c=c;
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
	private Scene createDialog()
	{
		Scene s1;
		BorderPane bp=new BorderPane();
		
		this.stage.setTitle("Employees");
		
		bp.setTop(initMenu(this.stage));
		bp.setCenter(initData());
		
		s1=new Scene(bp, 400, 300);
		return s1;
	}
	private MenuBar initMenu(Stage stage)
	{
		MenuBar menu1=new MenuBar();
		menu1.prefWidthProperty().bind(stage.widthProperty());
		
		Menu file=new Menu("File");
		MenuItem edit=new MenuItem("Edit");
		MenuItem newE=new MenuItem("New");
		edit.setOnAction(new enableEdit());
		newE.setOnAction(new newEmployee());
		file.getItems().add(edit);
		file.getItems().add(newE);
		
		//Menu find=new Menu("Find");
		//MenuItem searchEmp=new MenuItem("Search For employee");
		//MenuItem list=new MenuItem("List all employees");
		//find.getItems().addAll(searchEmp, list);
		
		menu1.getMenus().addAll(file);
		
		return menu1;
	}
	private GridPane initData()
	{
		GridPane gp=new GridPane();
		RowConstraints row0, row1, row2, row3, row4, row5, row6, row7, row8;
		ColumnConstraints col0, col1;
		
		Button cancel, next, prev;
		Label namel, idl, agel, genderl, positionl, assignementl, actl;
		
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
		
		row0.setVgrow(Priority.ALWAYS);
		row1.setVgrow(Priority.ALWAYS);
		row2.setVgrow(Priority.ALWAYS);
		row3.setVgrow(Priority.ALWAYS);
		row4.setVgrow(Priority.ALWAYS);
		row5.setVgrow(Priority.ALWAYS);
		row6.setVgrow(Priority.ALWAYS);
		row7.setVgrow(Priority.ALWAYS);
		row8.setVgrow(Priority.ALWAYS);
		
		gp.getColumnConstraints().addAll(col0, col1);
		gp.getRowConstraints().addAll(row0, row1, row2, row3, row4, row5, row6, row7, row8);
		gp.setHgap(10);
		gp.setVgap(8);
		gp.setAlignment(Pos.CENTER);
		gp.setPadding(new Insets(5, 5, 5, 5));
		gp.setPrefSize(400, 300);
		
		namel=new Label("Name");
		namel.autosize();
		namel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(namel, 0, 0);
		
		name=new TextField();
		name.autosize();
		name.setEditable(false);
		name.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(name, 0, 1);
		
		agel=new Label("Age");
		agel.autosize();
		agel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(agel, 1, 0);
		
		age=new TextField();
		age.autosize();
		age.setEditable(false);
		age.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(age, 1, 1);
		
		idl=new Label("Salary");
		idl.autosize();
		idl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(idl, 0, 2);
		
		salary=new TextField();
		salary.autosize();
		salary.setEditable(false);
		salary.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(salary, 0, 3);
		
		genderl=new Label("Gender");
		genderl.autosize();
		genderl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(genderl, 1, 2);
		
		gender=new TextField();
		gender.autosize();
		gender.setEditable(false);
		gender.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(gender, 1, 3);
		
		positionl=new Label("Position");
		positionl.autosize();
		positionl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(positionl, 0, 4);
		
		position=new TextField();
		position.autosize();
		position.setEditable(false);
		position.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(position, 0, 5);
		
		assignementl=new Label("Assignement");
		assignementl.autosize();
		assignementl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(assignementl, 1, 4);
		
		tsk=new TextField();
		tsk.autosize();
		tsk.setEditable(false);
		gp.add(tsk, 1, 5);
		
		actl=new Label("Activity");
		actl.autosize();
		actl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		gp.add(actl, 0, 6);
		
		activity=new TextField();
		activity.autosize();
		activity.setEditable(false);
		activity.setAccessibleHelp("Activity of employee");
		gp.add(activity, 1, 6);
		
		next=new Button("Next");
		next.autosize();
		next.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		next.setOnAction(new nxtAction());
		gp.add(next,  1, 7);
		
		prev=new Button("Previous");
		prev.autosize();
		prev.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		prev.setOnAction(new prevAction());
		gp.add(prev,  0, 7);
		
		submit.autosize();
		submit.setDisable(true);
		gp.add(submit, 0, 8);
		
		cancel=new Button("cancel");
		cancel.autosize();
		cancel.setOnAction(new closeEmployees());
		gp.add(cancel, 1, 8);
		this.data=new EmployeeManager(c).getAllInArray();
		update(this.currentListIndex);
		this.gl=gp;
		return gp;
	}
	/*
	 * position of employee by numbers
	 * 0 - driver
	 * 1 - accountant
	 * 2 - manager
	 */
	
	private void update(int index)
	{
		//System.out.println("updating employees");
		this.name.setText(this.data.get(index).getName());
		this.age.setText(String.valueOf(this.data.get(index).getAge()));
		this.salary.setText(String.valueOf(this.data.get(index).getSalary()));
		this.gender.setText(this.data.get(index).getGender());
		this.tsk.setText(String.valueOf(this.data.get(index).getTask()));
		
		if(this.data.get(index).getPosition()==0)
		{
			this.position.setText("driver");
		}
		else if(this.data.get(index).getPosition()==1)
		{
			this.position.setText("accountant");
		}
		else if(this.data.get(index).getPosition()==2)
		{
			this.position.setText("manager");
		}
		
		if(this.data.get(index).getActivity()==0)
		{
			this.activity.setText("no activity");
		}
		else if(this.data.get(index).getActivity()==1)
		{
			this.activity.setText("ready to transport");
		}
		else if(this.data.get(index).getActivity()==2)
		{
			this.activity.setText("transporting");
		}
		else if(this.data.get(index).getActivity()==3)
		{
			this.activity.setText("delivered");
		}
		
	}
	private boolean filledInFlag()
	{
		boolean ret=false;
		ret=(!this.name.getText().equals(""))?true:false;
		ret=(!this.age.getText().equals(""))?true:false;
		ret=(this.pos.getValue()!=null)?true:false;
		ret=(!this.gender.getText().equals(""))?true:false;
		ret=(!this.salary.getText().equals(""))?true:false;
		
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
	public Employees getInstance()
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
	class enableEdit implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			name.setEditable(true);
			age.setEditable(true);
			gender.setEditable(true);
			salary.setEditable(true);
			submit.setDisable(false);
			
			task=new ChoiceBox<Integer>(new EmployeeManager(c).parseTasks(new EmployeeManager(c).getTasks()));
			task.autosize();
			task.setDisable(false);
			
			gl.getChildren().remove(tsk);
			gl.add(task, 1, 5);
			gl.getChildren().remove(activity);
			gl.add(act, 1, 6);
			
			submit.setOnAction(new saveChanges());
		}
	}
	class newEmployee implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			name.setEditable(true);
			age.setEditable(true);
			gender.setEditable(true);
			salary.setEditable(true);
			submit.setDisable(false);
			
			name.setText("");
			age.setText("");
			gender.setText("");
			salary.setText("");
			
			task=new ChoiceBox<Integer>(new EmployeeManager(c).parseTasks(new EmployeeManager(c).getTasks()));
			task.autosize();
			task.setDisable(false);
			
			gl.getChildren().remove(tsk);
			gl.add(task, 1, 5);
			gl.getChildren().remove(position);
			gl.add(pos, 0, 5);
			
			gl.getChildren().remove(activity);
			gl.add(act, 1, 6);
			submit.setOnAction(new saveNewEmployee());
		}
	}
	class saveChanges implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			submit.setOnAction(null);
			try
			{
				data.get(currentListIndex).setName(new SimpleStringProperty(name.getText()));;
				data.get(currentListIndex).setAge(new SimpleIntegerProperty(Integer.parseInt(age.getText())));
				data.get(currentListIndex).setGender(new SimpleStringProperty(gender.getText()));
				
				if(position.getText().equals("driver"))
				{
					data.get(currentListIndex).setPosition(new SimpleIntegerProperty(0));
				}
				else if(position.getText().equals("accountant"))
				{
					data.get(currentListIndex).setPosition(new SimpleIntegerProperty(1));
				}
				else if(position.getText().equals("manager"))
				{
					data.get(currentListIndex).setPosition(new SimpleIntegerProperty(2));
				}
				
				if(act.getValue().equals("no activity"))
				{
					data.get(currentListIndex).setActivity(new SimpleIntegerProperty(0));
				}
				else if(act.getValue().equals("ready to transport"))
				{
					data.get(currentListIndex).setActivity(new SimpleIntegerProperty(1));
				}
				else if(act.getValue().equals("transporting"))
				{
					data.get(currentListIndex).setActivity(new SimpleIntegerProperty(2));
				}
				else if(act.getValue().equals("delivered"))
				{
					data.get(currentListIndex).setActivity(new SimpleIntegerProperty(3));
				}
				data.get(currentListIndex).setSalary(new SimpleIntegerProperty(Integer.parseInt(salary.getText())));
				data.get(currentListIndex).setTask(new SimpleIntegerProperty(task.getValue()));
				
				data.set(currentListIndex, new EmployeeManager(c).updateEmployee(data.get(currentListIndex)));
			}
			catch(Exception ex)
			{
				System.out.println("saving exception " + ex);
				ex.printStackTrace();
			}
			name.setEditable(false);
			age.setEditable(false);
			gender.setEditable(false);
			salary.setEditable(false);
			submit.setDisable(true);
			
			tsk.setEditable(false);
			tsk.setText(String.valueOf(task.getValue()));
			task.setDisable(true);
			
			gl.getChildren().remove(task);
			gl.add(tsk, 1, 5);
			gl.getChildren().remove(act);
			gl.add(activity, 1, 6);
		}
	}
	class saveNewEmployee implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			submit.setOnAction(null);
			if(checkInput())
			{
			try
			{
				data.get(currentListIndex).setName(new SimpleStringProperty(name.getText()));;
				data.get(currentListIndex).setAge(new SimpleIntegerProperty(Integer.parseInt(age.getText())));
				data.get(currentListIndex).setGender(new SimpleStringProperty(gender.getText()));
				if(pos.getValue().equals("driver"))
				{
					data.get(currentListIndex).setPosition(new SimpleIntegerProperty(0));
				}
				else if(pos.getValue().equals("accountant"))
				{
					data.get(currentListIndex).setPosition(new SimpleIntegerProperty(1));
				}
				else if(pos.getValue().equals("manager"))
				{
					data.get(currentListIndex).setPosition(new SimpleIntegerProperty(2));
				}
				data.get(currentListIndex).setSalary(new SimpleIntegerProperty(Integer.parseInt(salary.getText())));
				if(task.getValue()==null)
				{
					data.get(currentListIndex).setTask(new SimpleIntegerProperty(0));
				}
				else
				{
					data.get(currentListIndex).setTask(new SimpleIntegerProperty(task.getValue()));
				}

				if(act.getValue().equals("no activity"))
				{
					data.get(currentListIndex).setActivity(new SimpleIntegerProperty(0));
				}
				else if(act.getValue().equals("ready to transport"))
				{
					data.get(currentListIndex).setActivity(new SimpleIntegerProperty(1));
				}
				else if(act.getValue().equals("transporting"))
				{
					data.get(currentListIndex).setActivity(new SimpleIntegerProperty(2));
				}
				else if(act.getValue().equals("delivered"))
				{
					data.get(currentListIndex).setActivity(new SimpleIntegerProperty(3));
				}
				data.set(currentListIndex, new EmployeeManager(c).addEmployee(data.get(currentListIndex)));
			}
			catch(Exception ex)
			{
				System.out.println("saving exception " + ex);
				ex.printStackTrace();
			}
			name.setEditable(false);
			age.setEditable(false);
			gender.setEditable(false);
			salary.setEditable(false);
			submit.setDisable(true);
			
			tsk.setEditable(false);
			tsk.setText(String.valueOf(task.getValue()));
			task.setDisable(true);
			
			gl.getChildren().remove(task);
			gl.add(tsk, 1, 5);
			gl.getChildren().remove(pos);
			gl.add(position, 0, 5);
			gl.getChildren().remove(act);
			gl.add(activity, 1, 6);
			data=new EmployeeManager(c).getAllInArray();
			update(currentListIndex);
			}
		}
	}
	class closeEmployees implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			stage.close();
		}
	}
}
