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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.*;
public class Registration extends JFrame implements ActionListener{
    JLabel lblFname,lblLname,lblemail,lblUsrName,lblpassword;
    JTextField txtFname,txtLname,txtemail,txtUsrname;
    JPasswordField txtpassword;
    JButton btnSignup,btnupdate,btnLogin;
    
    public Registration()
    {
            BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src\\exam_mgmt_ams\\IMG_2035.JPG"));
        } catch (IOException e) {
           // e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(800, 800, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        setContentPane(new JLabel(imageIcon));
        setTitle("Registration");
        lblFname= new JLabel("First Name: ");
        lblLname= new JLabel("Last Name: ");
      
        lblemail= new JLabel("Email: ");
       
        lblUsrName= new JLabel(" Username: ");
        lblpassword= new JLabel("Password: ");
        
        lblFname.setBounds(10, 10, 100, 30);
        lblLname.setBounds(10, 45, 100, 30);
        lblemail.setBounds(10, 85, 100, 30);
        lblUsrName.setBounds(10, 125, 100, 30);
        lblpassword.setBounds(10, 165, 100, 30);
      
        
        add(lblFname);add(lblLname);add(lblemail);add(lblUsrName);add(lblpassword);
        
        //txtboxes
        txtFname= new JTextField();
        txtLname= new JTextField();
        
        txtemail= new JTextField();
      
        txtUsrname= new JTextField();
        txtpassword= new JPasswordField();

        
        
        txtFname.setBounds(120, 10, 150, 30);
        txtLname.setBounds(120, 45, 150, 30);

        txtemail.setBounds(120, 85, 150, 30);
        txtUsrname.setBounds(120, 125, 150, 30);
        txtpassword.setBounds(120, 165, 150, 30);
        
        add(txtFname);add(txtLname);add(txtemail);add(txtUsrname);add(txtpassword);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
        btnSignup= new JButton("Signup");
        btnSignup.setBounds(200, 280, 90, 40);
        add(btnSignup);
        btnSignup.addActionListener(this);
        
        btnupdate= new JButton("Update");
        btnupdate.setBounds(300, 280, 90, 40);
        add(btnupdate);
        btnupdate.addActionListener(this);
        
        btnLogin= new JButton("Login");
        btnLogin.setBounds(400, 280, 90, 40);
        add(btnLogin);
        btnLogin.addActionListener(this);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setLayout(null);
        setVisible(true);
        
    }
     public boolean Validate()
    {
        boolean chk = false;
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(txtemail.getText());
        chk = m.matches();
        return chk;
    }
     public boolean checkempty()
     {
         boolean empty=false;
         if (txtFname.equals("")& txtLname.equals("")&txtUsrname.equals("")&txtemail.equals("")&txtpassword.equals("")){
             empty=true;
         }
         return empty;
     }
     public void clear() {
        txtFname.setText("");
        txtLname.setText("");
        txtUsrname.setText("");
        txtemail.setText("");
        txtpassword.setText("");
    }

   

    @Override
    public void actionPerformed(ActionEvent ae) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (ae.getSource() == btnSignup) {
            System.out.println("Connected");
            if ((Validate()) & (checkempty()==false)) {
                try {
                    Database db = new Database();
                    int result = db.RegistrationSave(txtFname.getText(), txtLname.getText(), txtemail.getText(), txtUsrname.getText(), txtpassword.getText());
                    if (result > 0) {
                        JOptionPane.showMessageDialog(null, "Saved!");
                        clear();

                    } else {
                        JOptionPane.showMessageDialog(null, " Save Unsuccessful!");
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, " Please enter a valid email.");
                txtemail.requestFocusInWindow();
            }
        }
   
    
        //Update
        if (ae.getSource() == btnupdate) {
            if ((Validate()) & (checkempty()==false)) {
                try {
                    Database db = new Database();
                    int result = db.update(txtFname.getText(), txtLname.getText(), txtemail.getText(), txtUsrname.getText());
                    if (result > 0) {
                        JOptionPane.showMessageDialog(null, "Updated!");
                        clear();
                    } else {
                        JOptionPane.showMessageDialog(null, "Update Unsuccessful!");
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } else{
                 JOptionPane.showMessageDialog(null, " Please fill in all the details.");
                 JOptionPane.showMessageDialog(null, " Please enter a valid email.");
                txtFname.requestFocusInWindow();
            }
        }
        //login page
        if (ae.getSource().equals(btnLogin)) {
            LoginPage lp = new LoginPage();
            this.hide();
        }
    }
    
}
