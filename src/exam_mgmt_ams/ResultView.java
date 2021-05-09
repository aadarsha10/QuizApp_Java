/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exam_mgmt_ams;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.imageio.ImageIO;

import javax.swing.*;
import org.w3c.dom.events.EventListener;

public class ResultView extends JFrame implements EventListener {

    private JTextField viewText;
    Database db;
    Connection con;
    Statement st;
    PreparedStatement pst;
    String Qry;
    ResultSet rs;
    JLabel lbl_main,lbluser, ename, std, emailLabel, questionAttemtedLabel, correctAttempts, wrongAttempts, totalMarks, achivedMarks;
    private JButton homeBtn;

    public static void main(String[] args) {

        ResultView rv = new ResultView();
        rv.setVisible(true);

    }

    public ResultView() {
              BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src\\exam_mgmt_ams\\IMG_2270.JPG")); //image location 
        } catch (IOException e) {
           // e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(800, 800, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        setContentPane(new JLabel(imageIcon));
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        
        lbl_main = new JLabel("Result View");
        lbl_main.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_main.setFont(new Font("Arial Black", Font.PLAIN, 20));
        lbl_main.setBounds(377, 0, 254, 29);
        add(lbl_main);
        
        lbluser = new JLabel("Username: ");
        lbluser.setHorizontalAlignment(SwingConstants.CENTER);
        lbluser.setFont(new Font("Arial Black", Font.PLAIN, 16));
        lbluser.setBounds(0, 52, 150, 30);
        add(lbluser);
        JButton viewBtn = new JButton("View");
        viewBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadResult();
            }
        });
        viewBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        viewBtn.setBounds(370, 54, 126, 31);
        add(viewBtn);

        viewText = new JTextField();
        viewText.setColumns(10);
        viewText.setBounds(125, 56, 238, 29);
        add(viewText);

        ename = new JLabel("Exam Name: ");
        ename.setFont(new Font("Arial", Font.PLAIN, 15));
        ename.setBounds(10, 109, 228, 32);
        add(ename);

        std = new JLabel("Student name: ");
        std.setFont(new Font("Arial", Font.PLAIN, 15));
        std.setBounds(247, 109, 244, 32);
        add(std);

        emailLabel = new JLabel("Email: ");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        emailLabel.setBounds(10, 165, 196, 32);
        add(emailLabel);

        questionAttemtedLabel = new JLabel("Question Attempted: ");
        questionAttemtedLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        questionAttemtedLabel.setBounds(247, 165, 228, 32);
        add(questionAttemtedLabel);

        correctAttempts = new JLabel("Correct attempts: ");
        correctAttempts.setFont(new Font("Arial", Font.PLAIN, 15));
        correctAttempts.setBounds(10, 215, 228, 32);
        add(correctAttempts);

        wrongAttempts = new JLabel("Wrong attempts: ");
        wrongAttempts.setFont(new Font("Arial", Font.PLAIN, 15));
        wrongAttempts.setBounds(245, 219, 206, 32);
        add(wrongAttempts);

        totalMarks = new JLabel("Total marks:");
        totalMarks.setFont(new Font("Arial", Font.PLAIN, 15));
        totalMarks.setBounds(10, 272, 225, 32);
        add(totalMarks);

        achivedMarks = new JLabel("Achieved Marks:");
        achivedMarks.setFont(new Font("Arial", Font.PLAIN, 15));
        achivedMarks.setBounds(245, 270, 228, 32);
        add(achivedMarks);

        homeBtn = new JButton("Logout");
        homeBtn.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    new Exam_Mgmt_AMS().setVisible(true);
                    close();

                }
            }
        });
        homeBtn.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                new Exam_Mgmt_AMS().setVisible(true);
                close();
            }
        });
        homeBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        homeBtn.setEnabled(true);
        homeBtn.setBounds(260, 0, 112, 33);
        add(homeBtn);
        //fload();
    }

    public void fload() {
        try {
            db = new Database();
            con = db.getConnection();
            st = con.createStatement();
        } catch (Exception ex) {
            System.err.print("Exception: ");
            System.err.println(ex.getMessage());
        }
    }

    public void loadResult() {
        db = new Database();
        con = db.getConnection();
       
        try {
            pst=con.prepareStatement("select * from student where username=?");
            pst.setString(1, (viewText.getText().toLowerCase()));
            rs = pst.executeQuery();
         
            if (rs.next()) {
                ename.setText("Exam Name: " + rs.getString(2));
                std.setText("Student Name: " + rs.getString(3) + " " + rs.getString(4));
                emailLabel.setText("Email:" + rs.getString(5));
               
            } else {
                JOptionPane.showMessageDialog(null, "Username not found...!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Username not found...!");
            System.err.print("Exception: ");
            System.err.println(ex.getMessage());
        }

    }

    public void close() {
        this.hide();
        this.dispose();
    }

    @Override
    public void handleEvent(org.w3c.dom.events.Event event) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
