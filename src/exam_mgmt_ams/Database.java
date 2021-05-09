/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exam_mgmt_ams;

import java.sql.*;
import java.util.*;
public class Database {

    public Connection conn;
    PreparedStatement pstmt;
    String sqlQ,sstar,Empty;
    ResultSet rs;

    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ams_examsystem", "root", "");

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    public Connection getConnection() {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ams_examsystem", "root", "");;
            System.out.println("Connection established.");

        } catch (Exception e) {
            System.out.println("Exception : " + e);
   
        }
        return conn;
        

    }
    
    
 public int RegistrationSave(String Fname,String Lname,String email,String Usrname,String Pwd)
    {
        int result = 0;
        try {
            pstmt=conn.prepareStatement("insert into student(F_name,L_name,email,username,password) values(?,?,?,?,?)");
            pstmt.setString(1, Fname);
            pstmt.setString(2, Lname);
           
            pstmt.setString(3, email);
            
            pstmt.setString(4, Usrname);
            pstmt.setString(5, Pwd);
     
            result=pstmt.executeUpdate();
           
        } catch (Exception e) {
            System.out.println("Error: "+e);
        }
         return result;   
    }
 
  public int update(String Fname,String Lname,String email,String Usrname)
    {
        int result=0;
        try {
            pstmt=conn.prepareStatement("update student set F_name=?,L_name=?,email=? where username=?");
            pstmt.setString(1, Fname);
            pstmt.setString(2, Lname);
           
            pstmt.setString(3, email);
        
            pstmt.setString(4, Usrname);
           

            result=pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: "+e);
        }
            return result;
        
    }
  public String tokenGen()
  {
      
      String logintkn="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
      String Gentoken="";
      for (int i=1;i<=5;i++)
      {
          Gentoken += logintkn.charAt(new Random().nextInt(logintkn.length()));
      }
      return (Gentoken);
  }

}
    




