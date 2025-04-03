import java.sql.*;

import javax.swing.*;
public class InsertTrade
{
    public static void main(String[] args)  throws Exception{
        
        // Class.forName("com.mysql.jdbc.Driver");
        //connection establishment
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/hostel management system","root","");
        //precomplied query
        PreparedStatement pi = con.prepareStatement("delete from trade where tradeid=? ");
        
        
        int id =9 ;
        //String nm = "AI";
      
            
            pi.setInt(1,id);
            //pi.setString(1, nm);
            pi.executeUpdate();
    
        con.close();
    }
}