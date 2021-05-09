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
import javax.imageio.ImageIO;

public class tokenLogin extends JFrame implements ActionListener {

    Database db;
    JLabel lbl_main, lbl_tkn;
    JTextField txt_token;
    JButton btnOK;
    String tk;
    

    public tokenLogin(String tkn) {
            BufferedImage img = null;
        try {
            img = ImageIO.read(new File("D:\\Java projects\\Exam_Mgmt_AMS\\src\\exam_mgmt_ams\\IMG_2295.JPG"));
        } catch (IOException e) {
           // e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(650, 650, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        setContentPane(new JLabel(imageIcon));
        
        setTitle("Token Authentication");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(null);
        setVisible(true);

        //Main label 
        lbl_main = new JLabel("Token Authentication");
        lbl_main.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_main.setFont(new Font("Arial Black", Font.PLAIN, 20));
        lbl_main.setBounds(110, 5, 254, 30);
        add(lbl_main);

        lbl_tkn = new JLabel(" Enter token: ");
        lbl_tkn.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl_tkn.setBounds(20, 30, 80, 30);
        add(lbl_tkn);

        txt_token = new JTextField(tkn);
        txt_token.setBounds(110, 30, 150, 30);
        add(txt_token);
        txt_token.setEditable(false);
        
        tk= tkn;
        
        btnOK = new JButton("Ok");
        btnOK.setFont(new Font("Arial", Font.PLAIN, 12));
        btnOK.setBounds(130, 170, 100, 30);
        add(btnOK);
        btnOK.addActionListener(this);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       
        if (ae.getSource().equals(btnOK)) {
            if (((txt_token.getText()).equals(tk))) {
                StudentHome sh = new StudentHome();
                sh.setVisible(true);
                this.hide();
                System.out.println("token verified");
            }
            else{
                JOptionPane.showMessageDialog(null, "Invalid Token..Retry!");
            }
        }

    }
}
