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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.w3c.dom.events.EventListener;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class ExamForm extends JFrame implements EventListener {

    private final JTextField ans1Text, ans2Text, ans3Text, ans4Text, ModuleNM;
    PreparedStatement pstmt;
    JTextArea QuestionText;
    JScrollPane sp, listScroll;
    Database db;
    Connection conn;
    JRadioButton ans1Radio, ans2Radio, ans3Radio, ans4Radio, ansAllRadio, ansNoneRadio;
    ButtonGroup Fgroup, Sgroup;
    JButton submitBtn;
    JList<String> list;
    JList<Long> templist;
    String[] data;
    DefaultListModel<String> myList;
    DefaultListModel<Long> tmpid;
    private JLabel countLabel;
    private JButton Btn_logout;

    public static void main(String[] args) {
        new ExamForm().setVisible(true);
    }

    public ExamForm() {
            BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src\\exam_mgmt_ams\\IMG_1875.JPG"));
        } catch (IOException e) {
           // e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(1075, 777, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        setContentPane(new JLabel(imageIcon));
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1075, 777);
        setLayout(null);
        setVisible(true);

        JLabel mainLabel = new JLabel("Insert Questions");
        mainLabel.setBounds(529, 5, 196, 29);
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
        add(mainLabel);

        JLabel categLabel = new JLabel("Module: ");
        categLabel.setBounds(5, 66, 119, 32);
        categLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        add(categLabel);

        ModuleNM = new JTextField();
        ModuleNM.setBounds(146, 67, 208, 32);
        add(ModuleNM);

        JLabel questionLabel = new JLabel("Question");
        questionLabel.setBounds(5, 139, 119, 32);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        add(questionLabel);

        QuestionText = new JTextArea();
        QuestionText.setTabSize(5);
        QuestionText.setLineWrap(true);
        QuestionText.setWrapStyleWord(true);
        QuestionText.setRows(10);
        QuestionText.setColumns(10);
        QuestionText.setBounds(1, 1, 213, 184);
        add(QuestionText);
        sp = new JScrollPane(QuestionText);
        sp.setBounds(146, 143, 353, 78);
        add(sp);

        JLabel answerLabel = new JLabel("Answer");
        answerLabel.setBounds(48, 233, 213, 32);
        answerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        answerLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        add(answerLabel);

        JLabel lbl_Opt1 = new JLabel("Option 1");//Wahhh
        lbl_Opt1.setBounds(10, 290, 90, 32);
        lbl_Opt1.setFont(new Font("Arial", Font.PLAIN, 15));
        add(lbl_Opt1);

        JLabel lbl_Opt2 = new JLabel("Option 2");
        lbl_Opt2.setBounds(10, 341, 90, 32);
        lbl_Opt2.setFont(new Font("Arial", Font.PLAIN, 15));
        add(lbl_Opt2);

        JLabel lbl_Opt3 = new JLabel("Option 3");
        lbl_Opt3.setBounds(10, 393, 90, 32);
        lbl_Opt3.setFont(new Font("Arial", Font.PLAIN, 15));
        add(lbl_Opt3);

        JLabel lbl_Opt4 = new JLabel("Option 4");
        lbl_Opt4.setBounds(10, 449, 90, 32);
        lbl_Opt4.setFont(new Font("Arial", Font.PLAIN, 15));
        add(lbl_Opt4);

        //**TEXT FIELDS**//
        ans1Text = new JTextField();
        ans1Text.setBounds(146, 297, 238, 29);
        add(ans1Text);
        ans1Text.setColumns(10);

        ans2Text = new JTextField();
        ans2Text.setBounds(146, 348, 238, 29);
        ans2Text.setColumns(10);
        add(ans2Text);

        ans3Text = new JTextField();
        ans3Text.setBounds(146, 400, 238, 29);
        ans3Text.setColumns(10);
        add(ans3Text);

        ans4Text = new JTextField();
        ans4Text.setBounds(146, 456, 238, 29);
        ans4Text.setColumns(10);
        add(ans4Text);

        ///*******RADIO BUTTONS****//
        ans1Radio = new JRadioButton();
        ans1Radio.setBounds(390, 297, 21, 26);
        ans1Radio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!ans4Text.isEnabled()) {
                    ans4Text.setEnabled(true);
                }
            }
        });
        add(ans1Radio);

        ans2Radio = new JRadioButton();
        ans2Radio.setBounds(391, 348, 21, 26);
        ans2Radio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!ans4Text.isEnabled()) {
                    ans4Text.setEnabled(true);
                }
            }
        });
        add(ans2Radio);

        ans3Radio = new JRadioButton();
        ans3Radio.setBounds(390, 399, 21, 26);
        ans3Radio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (!ans4Text.isEnabled()) {
                    ans4Text.setEnabled(true);
                }
            }
        });
        add(ans3Radio);

        ans4Radio = new JRadioButton();
        ans4Radio.setBounds(390, 455, 21, 26);
        ans4Radio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Sgroup.clearSelection();
                ans4Text.setEnabled(true);
            }
        });
        add(ans4Radio);

//creating group of radio buttons
        Fgroup = new ButtonGroup();
        Sgroup = new ButtonGroup();
        //*****************************//
        //four simple ans group//
        Fgroup.add(ans1Radio);
        Fgroup.add(ans2Radio);
        Fgroup.add(ans3Radio);
        Fgroup.add(ans4Radio);
        

        submitBtn = new JButton("Submit");
        submitBtn.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (insertQuestion()) {
                            clear();
                        }
                }
            }
        });
        submitBtn.setBounds(194, 572, 126, 41);
        submitBtn.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                    if (insertQuestion()) {
                        
                        clear();
                    }
            }
        });
        submitBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        add(submitBtn);

        myList = new DefaultListModel<>();
        tmpid = new DefaultListModel<>();

        templist = new JList<>();
        templist.setVisible(false);

        countLabel = new JLabel("");
        countLabel.setHorizontalAlignment(SwingConstants.CENTER);
        countLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        countLabel.setBounds(512, 107, 308, 32);
        add(countLabel);

//        list = new JList<>();
//        list.addListSelectionListener((ListSelectionEvent arg0) -> {
//            if (arg0.getValueIsAdjusting()==false) {
//                displayQuestion();
//                System.out.println("******");
//            }
//        });
//        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        list.setFont(new Font("Arial Black", Font.PLAIN, 12));
//        list.setBounds(509, 139, 271, 438);
//        add(list);
//        listScroll = new JScrollPane(list);
//        listScroll.setBounds(509, 139, 271, 438);
//        add(listScroll);
        //****************************//
//---------*-*-------------//
        //Home Page//
        Btn_logout = new JButton("Logout");
        Btn_logout.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    new Exam_Mgmt_AMS().setVisible(true);
                    close();
                }
            }
        });
        Btn_logout.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                new Exam_Mgmt_AMS().setVisible(true);
                close();
            }
        });
        Btn_logout.setFont(new Font("Arial", Font.PLAIN, 12));
        Btn_logout.setEnabled(true);
        Btn_logout.setBounds(276, 6, 112, 33);
        add(Btn_logout);

    }

    public boolean insertQuestion() {
        String sql;
        ResultSet rs;
        Statement st;
        long MId = 0, id;
        if (!onSubmitValiation()) {
            try {
                db = new Database();
                conn = db.getConnection();
                
                 pstmt=conn.prepareStatement("INSERT INTO `module`(`module_name`) VALUES (?)");
                 pstmt.setString(1,(ModuleNM.getText().toLowerCase()));
                 System.out.println("module entered");
                 pstmt.executeUpdate();
                    
                
                st = conn.createStatement();
                rs = st.executeQuery("select m_id from module where module_name='" + (ModuleNM.getText().toLowerCase()) + "'");
                if (rs.next()) {
                    MId = rs.getLong(1);
                }
                rs = st.executeQuery("select q_id from qanda order by q_id DESC limit 1 ");
                if (rs.next()) {
                    id = rs.getLong(1) + 1;
                } else {
                    id = 1;
                }
                //////answer ////////////////////
                int ans = 0;
                if (ans1Radio.isSelected()) {
                    ans = 1;
                } else if (ans2Radio.isSelected()) {
                    ans = 2;
                } else if (ans3Radio.isSelected()) {
                    ans = 3;
                } else if (ans4Radio.isSelected()) {
                    ans = 4;
                }

                String query = "INSERT INTO `qanda`(`m_id`, `question`, `opt_1`, `opt_2`, `opt_3`, `opt_4`, `ans_value`,`moduleN`) VALUES (?,?,?,?,?,?,?,?)";
                pstmt = conn.prepareStatement(query);
                pstmt.setLong(1, MId);
                pstmt.setString(2, (QuestionText.getText().toLowerCase()));
                pstmt.setString(3, (ans1Text.getText().toLowerCase()));
                pstmt.setString(4, (ans2Text.getText().toLowerCase()));
                pstmt.setString(5, (ans3Text.getText().toLowerCase()));
                pstmt.setString(6, (ans4Text.getText().toLowerCase()));
                pstmt.setInt(7, ans);
                pstmt.setString(8, (ModuleNM.getText().toLowerCase()));

                System.out.println(query);
                pstmt.executeUpdate();

            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
            return true;
        }
        return false;
    }


    public boolean onSubmitValiation() {
        String Blank = "", msg = "";
        boolean chk = false;
        if ((ModuleNM.getText().toLowerCase()).equals(Blank)) {
            msg = "Please Enter a Module...!";
            chk = true;
            ModuleNM.requestFocusInWindow();
        } else if (QuestionText.getText().equals(Blank)) {
            msg = "Please Enter a Question...!";
            QuestionText.requestFocusInWindow();
            chk = true;
        } else if (ans1Text.getText().equals(Blank)) {
            msg = "Please Enter Option 1...! ";
            ans1Text.requestFocusInWindow();
            chk = true;
        } else if (ans2Text.getText().equals(Blank)) {
            msg = "Please Enter Option 2...!";
            ans2Text.requestFocusInWindow();
            chk = true;
        } else if (ans3Text.getText().equals(Blank)) {
            msg = "Please Enter Option 3...!";
            ans3Text.requestFocusInWindow();
            chk = true;
        } else if (ans4Text.getText().equals(Blank)) {
            msg = "Please Enter Option 4...!";
            ans4Text.requestFocusInWindow();
            chk = true;
        } else if (Fgroup.getSelection() == null) {
            msg = "Please select an answer ...!";
            chk = true;
            
        }
        if (chk) {
            JOptionPane.showMessageDialog(null, msg);
        }
        return chk;
    }

    public void clear() {
        QuestionText.setText("");
        ans1Text.setText("");
        ans2Text.setText("");
        ans3Text.setText("");
        ans4Text.setText("");
        Sgroup.clearSelection();
        Fgroup.clearSelection();
        ans4Text.setEnabled(true);
    }

    public void close() {
        this.dispose();
    }

    @Override
    public void handleEvent(org.w3c.dom.events.Event event) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
