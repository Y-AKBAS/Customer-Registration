
import java.sql.*;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.*;
import javafx.geometry.*;


public class MyFrame extends Application {

	Button add,delete,update,save,search;
	Label cid,fname,lname,city,pnumber,email,dateofreg;
	TextField tcid,tfname,tlname,tcity,tpnumber,temail,tdateofreg;
	DatePicker dp=new DatePicker(LocalDate.now());
	private Databank db=new Databank();
    private Connection connection=db.connect();
	
    public void setEmpty(TextField c,TextField fname,TextField lname,TextField city,TextField pnumber,TextField email,DatePicker d)
    {
    	c.setText("");
    	fname.setText("");
    	lname.setText("");
    	city.setText("");
    	pnumber.setText("");
    	email.setText("");
    	dp=new DatePicker(LocalDate.now());
    }
    
	public void start(Stage stage)
	{
		stage.setTitle("Customer Registration");
		
		Font f=Font.font("Arial",FontWeight.NORMAL,FontPosture.REGULAR,15);
		
		add=new Button("New");
		delete=new Button("Delete");
		update=new Button("Update");
		save=new Button("Save");
		search=new Button("Search");
		
		add.setPrefSize(80,30);    add.setFont(f);
		delete.setPrefSize(80,30); delete.setFont(f);
		update.setPrefSize(80,30); update.setFont(f);
		save.setPrefSize(80,30);   save.setFont(f);
		search.setPrefSize(70,30); search.setFont(f);
		
		add.setOnAction(a->
		{
			setEmpty(tcid,tfname,tlname,tcity,tpnumber,temail,dp);
		});
		
		save.setOnAction(a->
		{ 
			try
			{
				db.insertion(tfname,tlname,tcity,tpnumber,temail,dp);
			}
			catch(SQLException e) {}
			
			setEmpty(tcid,tfname,tlname,tcity,tpnumber,temail,dp);
		});
		
		delete.setOnAction(a->
		{
			try
			{
				db.delete(tcid);
			}
		   catch (Exception e) {e.printStackTrace();} 
			
			setEmpty(tcid,tfname,tlname,tcity,tpnumber,temail,dp);
		});
		
		search.setOnAction(a->
		{
			try
			{
				db.search(tcid,tfname,tlname,tcity,tpnumber,temail,dp);
			}
			catch(Exception e) {e.printStackTrace();}
			
		});
		
		update.setOnAction(a->
		{
			try
			{
				db.update(tcid,tfname,tlname,tcity,tpnumber,temail,dp);
			}
			catch(Exception e) {}
			
			setEmpty(tcid,tfname,tlname,tcity,tpnumber,temail,dp);
		});
		
		VBox vb1=new VBox();
		vb1.getChildren().addAll(add,delete,update,save);
		vb1.setPadding(new Insets(30,30,0,0));
		vb1.setSpacing(8);
		vb1.setAlignment(Pos.TOP_RIGHT);
		
		cid=new Label("Customer ID"); cid.setFont(f);
		tcid=new TextField();		  tcid.setFont(f);
		tcid.setPrefSize(220,30);
		
		HBox hb1=new HBox();
		hb1.getChildren().addAll(cid,tcid,search);
		hb1.setPadding(new Insets(30,0,20,60));
		hb1.setSpacing(10);
		hb1.setAlignment(Pos.TOP_LEFT);
		
		fname=new Label("First Name"); fname.setFont(f);
		tfname=new TextField(); 	   tfname.setFont(f);
		tfname.setPrefSize(300,30);
		
		HBox hb2=new HBox();
		hb2.getChildren().addAll(fname,tfname);
		hb2.setPadding(new Insets(0,0,20,70));
		hb2.setSpacing(10);
		hb2.setAlignment(Pos.TOP_LEFT);
		
		lname=new Label("Last Name"); lname.setFont(f);
		tlname=new TextField(); 	  tlname.setFont(f);
		tlname.setPrefSize(300,30);
		
		HBox hb3=new HBox();
		hb3.getChildren().addAll(lname,tlname);
		hb3.setPadding(new Insets(0,0,20,70));
		hb3.setSpacing(10);
		hb3.setAlignment(Pos.TOP_LEFT);
		
		city=new Label("City"); 		  city.setFont(f);
		tcity=new TextField();			  tcity.setFont(f);
		tcity.setPrefSize(300,30);
		
		HBox hb4=new HBox();
		hb4.getChildren().addAll(city,tcity);
		hb4.setPadding(new Insets(0,0,20,115));
		hb4.setSpacing(10);
		hb4.setAlignment(Pos.TOP_LEFT);
		
		pnumber=new Label("Phone Number");  pnumber.setFont(f);
		tpnumber=new TextField();			tpnumber.setFont(f);
		tpnumber.setPrefSize(300,30);
		
		HBox hb5=new HBox();
		hb5.getChildren().addAll(pnumber,tpnumber);
		hb5.setPadding(new Insets(0,0,20,40));
		hb5.setSpacing(10);
		hb5.setAlignment(Pos.TOP_LEFT);
		
		email=new Label("Email");		email.setFont(f);
		temail=new TextField();			temail.setFont(f);
		temail.setPrefSize(300,30);
		
		HBox hb6=new HBox();
		hb6.getChildren().addAll(email,temail);
		hb6.setPadding(new Insets(0,0,20,105));
		hb6.setSpacing(10);
		hb6.setAlignment(Pos.TOP_LEFT);
		
		dateofreg=new Label("Registration Date"); dateofreg.setFont(f);
		tdateofreg=new TextField();				  tdateofreg.setFont(f);
		
	    dp.getValue();
		dp.setOnAction(a->{ tdateofreg.setText(""+dp.getValue()) ;});
		
		HBox hb7=new HBox();
		hb7.getChildren().addAll(dateofreg,dp);
		hb7.setPadding(new Insets(0,0,20,25));
		hb7.setSpacing(10);
		hb7.setAlignment(Pos.TOP_LEFT);
		
		VBox dataPart=new VBox();
		dataPart.getChildren().addAll(hb1,hb2,hb3,hb4,hb5,hb6,hb7);
		dataPart.setAlignment(Pos.TOP_LEFT);
		
		HBox complete=new HBox();
		complete.getChildren().addAll(dataPart,vb1);
		complete.setSpacing(100);
		
		BorderPane bp=new BorderPane(complete);
		Scene sc=new Scene(bp,700,600);
		stage.setScene(sc);
		stage.show();
		
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}

}
