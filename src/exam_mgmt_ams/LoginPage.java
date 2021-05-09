/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exam_mgmt_ams;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class LoginPage extends JFrame implements ActionListener {

    JLabel lbl_usr, lbl_pwd;
    JTextField txt_user;
    JPasswordField txt_pass;
    JButton btnLogin;
    PreparedStatement pstmt;
    ResultSet rs;
    Database db;
    Connection conn;
    Container c;
    public LoginPage() {
      BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src\\exam_mgmt_ams\\IMG_0825.JPG"));
        } catch (IOException e) {
           // e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        setContentPane(new JLabel(imageIcon));
        //username label and text field
        setTitle("Login");
        lbl_usr = new JLabel(" Username: ");
        lbl_usr.setFont(new Font("Arial Black", Font.PLAIN, 12));
        lbl_usr.setBounds(20, 30, 80, 30);
        add(lbl_usr);

        txt_user = new JTextField();
        txt_user.setBounds(110, 30, 150, 30);
        add(txt_user);

        //password label and password field
        lbl_pwd = new JLabel(" Password: ");
        lbl_pwd.setFont(new Font("Arial Black", Font.PLAIN, 12));
        lbl_pwd.setBounds(20, 70, 80, 30);
        add(lbl_pwd);

        txt_pass = new JPasswordField();
        txt_pass.setBounds(110, 70, 150, 30);
        txt_pass.setEchoChar('*');
        add(txt_pass);
        txt_pass.setColumns(10);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(130, 170, 100, 30);
        add(btnLogin);
        btnLogin.addActionListener(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(null);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (ae.getSource().equals(btnLogin)) {
            //LoginPage lp = new LoginPage();

            if ((txt_user.getText().equals("admin")) & (txt_pass.getText().equals("admin"))) {
                ExamForm ef = new ExamForm();
                ef.setVisible(true);
                
                this.hide();
            } else {

                db = new Database();
                conn = db.getConnection();
                String msg= db.tokenGen();
                try {

                    String sql = ("select * from student where username=? and password=?");
                    pstmt = conn.prepareStatement(sql);

                    pstmt.setString(1, txt_user.getText());
                    pstmt.setString(2, txt_pass.getText());
                    rs = pstmt.executeQuery();

                    if (rs.next()) {
                        String usr = rs.getString("username");
                        String pswd = rs.getString("password");
                        if (((txt_user.getText()).equals(usr)) & ((txt_pass.getText()).equals(pswd))) {
                           System.out.println("Logged in");
                           
                            JOptionPane.showMessageDialog(null, "Your login token is: "+msg);
                            tokenLogin tl=new tokenLogin(msg);
                            tl.setVisible(true);
                           this.hide();
                            
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Username/password is wrong...!");

                    }
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                    //JOptionPane.showMessageDialog(ex.);
                  
                }
            }
        }
    }

}
//
