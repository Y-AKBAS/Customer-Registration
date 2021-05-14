
import java.sql.*;
import java.time.LocalDate;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class Databank 
{
	private final String url = "jdbc:postgresql://localhost/customerregistration";
    private final String user = "postgres";
    private final String password = "<Password>";
    private Connection con=null;
	
	public Connection connect()
	{
		try
		{
			con=DriverManager.getConnection(url,user,password);
			
		}catch (SQLException e) { System.out.println(e.getMessage());}
		
		return con;
	}
	
	public void insertion(TextField fname,TextField lname,TextField city,TextField pnumber,TextField email,DatePicker dp) throws SQLException
	{
	   String in="INSERT INTO CustomerInfo (FirstName,LastName,City,PhoneNumber,Email,DateOfReg) VALUES (?,?,?,?,?,?)";
	   PreparedStatement pstm=con.prepareStatement(in);
	   	     
	     String FirstName=fname.getText().trim();
	     String LastName=lname.getText().trim();
	     String City=city.getText().trim();
	     String PhoneNumber=pnumber.getText().trim();
	     String Email=email.getText().trim();
	     
	     LocalDate ld=dp.getValue();
	     Date d=Date.valueOf(ld);
	     
	     pstm.setString(1, FirstName);
	     pstm.setString(2, LastName);
	     pstm.setString(3, City);
	     pstm.setString(4, PhoneNumber);
	     pstm.setString(5, Email);
	     pstm.setDate(6,d);
	     
	     pstm.executeUpdate();
	}
	
	public void delete(TextField tf) throws Exception
	{
		String de="DELETE FROM CustomerInfo where customerid=?";
		
		PreparedStatement pstm=con.prepareStatement(de);
		pstm.setInt(1,Integer.parseInt(tf.getText()));
		
		pstm.execute();
	}
	
	public void search(TextField cid,TextField fname,TextField lname,TextField city,TextField pnumber,TextField email,DatePicker dp) throws Exception
	{
		String se="SELECT * FROM CustomerInfo where customerid=?";
		
		PreparedStatement pstm=con.prepareStatement(se);
		pstm.setInt(1, Integer.parseInt(cid.getText()));
		
		ResultSet rs=pstm.executeQuery();
		
		LocalDate ld;
		
		while(rs.next())
		{
			 fname.setText(rs.getString("firstname").trim());
		     lname.setText(rs.getString("lastname").trim());
		     city.setText(rs.getString("city").trim());
		     pnumber.setText(rs.getString("phonenumber").trim());
		     email.setText(rs.getString("email").trim());
		     ld=rs.getDate("dateofreg").toLocalDate();
			 dp=new DatePicker(ld);
		}
	}
	
	public void update(TextField cid,TextField fname,TextField lname,TextField city,TextField pnumber,TextField email,DatePicker dp) throws Exception
	{
		PreparedStatement pstm;
		String str;
		
		str="UPDATE CustomerInfo SET firstname=?,lastname=?,city=?,phonenumber=?,email=?,dateofreg=?"
				    + " where customerid="+Integer.valueOf(cid.getText());
		pstm=con.prepareStatement(str);
		pstm.setString(1,fname.getText());
		pstm.setString(2, lname.getText());
		pstm.setString(3, city.getText());
		pstm.setString(4, pnumber.getText());
		pstm.setString(5, email.getText());
		
		LocalDate ld=dp.getValue();
		Date d=Date.valueOf(ld);
		pstm.setDate(6,d);
		
		pstm.executeQuery();
	}	
	
	public static void main(String args[])
	{
		Databank db=new Databank();
		Connection conn=db.connect();
		
		try 
		{
			conn.close();	
		}
		catch(SQLException e) {}
		
	}
}
