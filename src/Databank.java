import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBHandler {
    private static final String DB_CONNECTION_URL = "jdbc:postgresql://localhost/customerregistration";
    private static final String DB_USER_NAME = "postgres";
    private static final String DB_PASSWORD = "...";

    private static final String INSERTION = "INSERT INTO CustomerInfo (FirstName,LastName,City,PhoneNumber,Email,DateOfReg)" +
            " VALUES (?,?,?,?,?,?)";
    private static final String DELETION = "DELETE FROM CustomerInfo where customerid=?";
    private static final String SEARCH = "SELECT * FROM CustomerInfo where customerid=?";
    private static final String UPDATE = "UPDATE CustomerInfo SET firstname=?,lastname=?,city=?,phonenumber=?,email=?,dateofreg=?"
            + " where customerid=";

    private static Connection DB_CONNECTION;

    static {
        try {
            DB_CONNECTION = DriverManager.getConnection(DB_CONNECTION_URL, DB_USER_NAME, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() throws SQLException {
        if (Objects.isNull(DB_CONNECTION)) {
            DB_CONNECTION = DriverManager.getConnection(DB_CONNECTION_URL, DB_USER_NAME, DB_PASSWORD);
        }
        return DB_CONNECTION;
    }

    public void insertion(TextField fname, TextField lname, TextField city, TextField pnumber, TextField email, DatePicker dp) throws SQLException {

        try (PreparedStatement pstm = getConnection().prepareStatement(INSERTION)) {

            String FirstName = fname.getText().trim();
            String LastName = lname.getText().trim();
            String City = city.getText().trim();
            String PhoneNumber = pnumber.getText().trim();
            String Email = email.getText().trim();

            LocalDate ld = dp.getValue();
            Date d = Date.valueOf(ld);

            pstm.setString(1, FirstName);
            pstm.setString(2, LastName);
            pstm.setString(3, City);
            pstm.setString(4, PhoneNumber);
            pstm.setString(5, Email);
            pstm.setDate(6, d);

            pstm.executeUpdate();
        }
    }

    public void delete(TextField tf) throws Exception {
        String input = tf.getText();

        String regex = "[+-]?[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find() && matcher.group().equals(input)) {
            try (PreparedStatement pstm = getConnection().prepareStatement(DELETION)) {
                pstm.setInt(1, Integer.parseInt(tf.getText()));
                pstm.execute();
            }
        }

    }

    public void search(TextField cid, TextField fname, TextField lname, TextField city, TextField pnumber,
                       TextField email) throws Exception {

        String input = cid.getText();
        String regex = "[+-]?[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find() && matcher.group().equals(input)) {

            ResultSet rs;
            try (PreparedStatement pstm = getConnection().prepareStatement(SEARCH)) {
                pstm.setInt(1, Integer.parseInt(cid.getText()));
                rs = pstm.executeQuery();
            }

            LocalDate ld;
            if (Objects.isNull(rs)) {
                return;
            }

            while (rs.next()) {
                fname.setText(rs.getString("firstname").trim());
                lname.setText(rs.getString("lastname").trim());
                city.setText(rs.getString("city").trim());
                pnumber.setText(rs.getString("phonenumber").trim());
                email.setText(rs.getString("email").trim());
                ld = rs.getDate("dateofreg").toLocalDate();
                DatePicker dp = new DatePicker(ld);
            }
        }
    }

    public void update(TextField cid, TextField fname, TextField lname, TextField city, TextField pnumber,
                       TextField email, DatePicker dp) throws Exception {

        String input = cid.getText();
        String regex = "[+-]?[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find() && matcher.group().equals(input)) {
            String query = UPDATE + Integer.valueOf(cid.getText());
            try (PreparedStatement pstm = getConnection().prepareStatement(query)) {
                pstm.setString(1, fname.getText());
                pstm.setString(2, lname.getText());
                pstm.setString(3, city.getText());
                pstm.setString(4, pnumber.getText());
                pstm.setString(5, email.getText());

                LocalDate ld = dp.getValue();
                Date d = Date.valueOf(ld);
                pstm.setDate(6, d);

                pstm.executeQuery();
            }
        }
    }
}
