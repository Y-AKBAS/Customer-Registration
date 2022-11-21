import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;


public class MyFrame extends Application {

    private TextField tcid, tfname, tlname, tcity, tpnumber, temail, tdateofreg;
    private DatePicker datePicker = new DatePicker(LocalDate.now());
    private static final Font FONT = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 15);

    DBHandler dbHandler = new DBHandler();

    public void setEmpty(TextField c, TextField fname, TextField lname, TextField city, TextField pnumber,
                         TextField email) {
        c.setText("");
        fname.setText("");
        lname.setText("");
        city.setText("");
        pnumber.setText("");
        email.setText("");
        datePicker = new DatePicker(LocalDate.now());
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Customer Registration");

        Button add = new Button("New");
        Button delete = new Button("Delete");
        Button update = new Button("Update");
        Button save = new Button("Save");
        Button search = new Button("Search");

        setButtonBehaviour(add, delete, update, save, search);
        setFontAndPrefSize(add, delete, update, save, search);

        VBox dataPart = new VBox();
        dataPart.getChildren().addAll(
                getCustomerIdHBox(search),
                getFirstNameHBox(),
                getLastNameHBox(),
                getCityHBox(),
                getPhoneNumberHBox(),
                getEmailHBox(),
                getDateHBox()
        );

        dataPart.setAlignment(Pos.TOP_LEFT);

        HBox complete = new HBox();
        complete.getChildren().addAll(dataPart, getVBox(add, delete, update, save));
        complete.setSpacing(100);

        BorderPane bp = new BorderPane(complete);
        Scene sc = new Scene(bp, 700, 600);
        stage.setScene(sc);
        stage.show();
    }

    private void setButtonBehaviour(Button add, Button delete, Button update, Button save, Button search) {
        add.setOnAction(a ->
                setEmpty(tcid, tfname, tlname, tcity, tpnumber, temail));

        save.setOnAction(a ->
        {
            try {
                dbHandler.insertion(tfname, tlname, tcity, tpnumber, temail, datePicker);
            } catch (SQLException ignored) {
            }

            setEmpty(tcid, tfname, tlname, tcity, tpnumber, temail);
        });

        delete.setOnAction(a ->
        {
            try {
                dbHandler.delete(tcid);
            } catch (Exception e) {
                e.printStackTrace();
            }

            setEmpty(tcid, tfname, tlname, tcity, tpnumber, temail);
        });

        search.setOnAction(a ->
        {
            try {
                dbHandler.search(tcid, tfname, tlname, tcity, tpnumber, temail);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        update.setOnAction(a ->
        {
            try {
                dbHandler.update(tcid, tfname, tlname, tcity, tpnumber, temail, datePicker);
            } catch (Exception ignored) {
            }

            setEmpty(tcid, tfname, tlname, tcity, tpnumber, temail);
        });
    }

    private void setFontAndPrefSize(Button add, Button delete, Button update, Button save, Button search) {
        add.setPrefSize(80, 30);
        add.setFont(FONT);
        delete.setPrefSize(80, 30);
        delete.setFont(FONT);
        update.setPrefSize(80, 30);
        update.setFont(FONT);
        save.setPrefSize(80, 30);
        save.setFont(FONT);
        search.setPrefSize(70, 30);
        search.setFont(FONT);
    }

    private VBox getVBox(Button add, Button delete, Button update, Button save) {
        VBox vb1 = new VBox();
        vb1.getChildren().addAll(add, delete, update, save);
        vb1.setPadding(new Insets(30, 30, 0, 0));
        vb1.setSpacing(8);
        vb1.setAlignment(Pos.TOP_RIGHT);
        return vb1;
    }

    private HBox getCustomerIdHBox(Button search) {
        Label cid = new Label("Customer ID");
        cid.setFont(FONT);
        tcid = new TextField();
        tcid.setFont(FONT);
        tcid.setPrefSize(220, 30);

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(cid, tcid, search);
        hb1.setPadding(new Insets(30, 0, 20, 60));
        hb1.setSpacing(10);
        hb1.setAlignment(Pos.TOP_LEFT);
        return hb1;
    }

    private HBox getFirstNameHBox() {
        Label fname = new Label("First Name");
        fname.setFont(FONT);
        tfname = new TextField();
        tfname.setFont(FONT);
        tfname.setPrefSize(300, 30);

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(fname, tfname);
        hb2.setPadding(new Insets(0, 0, 20, 70));
        hb2.setSpacing(10);
        hb2.setAlignment(Pos.TOP_LEFT);
        return hb2;
    }

    private HBox getLastNameHBox() {
        Label lname = new Label("Last Name");
        lname.setFont(FONT);
        tlname = new TextField();
        tlname.setFont(FONT);
        tlname.setPrefSize(300, 30);

        HBox hb3 = new HBox();
        hb3.getChildren().addAll(lname, tlname);
        hb3.setPadding(new Insets(0, 0, 20, 70));
        hb3.setSpacing(10);
        hb3.setAlignment(Pos.TOP_LEFT);
        return hb3;
    }

    private HBox getCityHBox() {
        Label city = new Label("City");
        city.setFont(FONT);
        tcity = new TextField();
        tcity.setFont(FONT);
        tcity.setPrefSize(300, 30);

        HBox hb4 = new HBox();
        hb4.getChildren().addAll(city, tcity);
        hb4.setPadding(new Insets(0, 0, 20, 115));
        hb4.setSpacing(10);
        hb4.setAlignment(Pos.TOP_LEFT);
        return hb4;
    }

    private HBox getPhoneNumberHBox() {
        Label pnumber = new Label("Phone Number");
        pnumber.setFont(FONT);
        tpnumber = new TextField();
        tpnumber.setFont(FONT);
        tpnumber.setPrefSize(300, 30);

        HBox hb5 = new HBox();
        hb5.getChildren().addAll(pnumber, tpnumber);
        hb5.setPadding(new Insets(0, 0, 20, 40));
        hb5.setSpacing(10);
        hb5.setAlignment(Pos.TOP_LEFT);
        return hb5;
    }

    private HBox getEmailHBox() {
        Label email = new Label("Email");
        email.setFont(FONT);
        temail = new TextField();
        temail.setFont(FONT);
        temail.setPrefSize(300, 30);

        HBox hb6 = new HBox();
        hb6.getChildren().addAll(email, temail);
        hb6.setPadding(new Insets(0, 0, 20, 105));
        hb6.setSpacing(10);
        hb6.setAlignment(Pos.TOP_LEFT);
        return hb6;
    }

    private HBox getDateHBox() {
        Label dateofreg = new Label("Registration Date");
        dateofreg.setFont(FONT);
        tdateofreg = new TextField();
        tdateofreg.setFont(FONT);

        datePicker.getValue();
        datePicker.setOnAction(a -> {
            tdateofreg.setText("" + datePicker.getValue());
        });

        HBox hb7 = new HBox();
        hb7.getChildren().addAll(dateofreg, datePicker);
        hb7.setPadding(new Insets(0, 0, 20, 25));
        hb7.setSpacing(10);
        hb7.setAlignment(Pos.TOP_LEFT);
        return hb7;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
