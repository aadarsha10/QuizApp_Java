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
import javax.imageio.ImageIO;
import javax.swing.*;
public class Exam_Mgmt_AMS extends JFrame implements ActionListener{
    //For dashboard
    JLabel lbl_welcome;
    JButton btn_login_first,btn_Signup;
    Container c;
    public Exam_Mgmt_AMS(){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src\\exam_mgmt_ams\\IMG_2011.JPG"));
        } catch (IOException e) {
          
        }
        Image dimg = img.getScaledInstance(650, 650, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        setContentPane(new JLabel(imageIcon));
        setTitle("DashBoard");
        lbl_welcome = new JLabel(" Welcome to Softwarica College Exam Management System. ");
        lbl_welcome.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_welcome.setFont(new Font("Arial Black", Font.PLAIN, 16));
         lbl_welcome.setBounds(20, 20, 550, 40);
         add(lbl_welcome);
 
         //Buttons to choose the type of user login 
             btn_login_first = new JButton("Login");
             btn_login_first.setFont(new Font("Arial", Font.PLAIN, 14));
             btn_login_first.setBounds(150, 120, 80, 30);
             add(btn_login_first);
             btn_login_first.addActionListener(this);
            
             btn_Signup = new JButton("Signup");
             btn_Signup.setFont(new Font("Arial", Font.PLAIN, 14));
             btn_Signup.setBounds(240, 120, 80, 30);
             add(btn_Signup);
             btn_Signup.addActionListener(this);
            
             setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(650,650);
             setLayout(null);
         
         
    }
    
    public static void main(String[] args) {
        new Exam_Mgmt_AMS().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         
        if (ae.getSource().equals(btn_login_first)) {
            LoginPage lp = new LoginPage();
            this.hide();

        }
        if (ae.getSource().equals(btn_Signup)) {
            Registration reg = new Registration();
            this.hide();
        }
    }
    
}
