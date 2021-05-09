
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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.w3c.dom.events.EventListener;

public class StudentHome extends JFrame implements ActionListener {

    private final JTextField stdNameText;
    JButton startBtn, viewResultbtn, btnNext, btnPrev;

    Database db;
    Connection cn;
    PreparedStatement pstmt;
    Statement st;
    String Qry, examName, ans;
    ResultSet rs;
    JComboBox<String> examCombo;
    int timecounter = 19, ss = 60, current = -1, currentQno = 1;
    long idg, eidg, qidg;
    JLabel timeLabel, questionLabel, Tcounter;
    Random random;
    JRadioButton radio_ans[] = new JRadioButton[5];
    long score = 0, questionAttempted = 0, correctAttempts = 0, wrongAttempts = 0, totalMarks = 0, achievedMarks = 0, correctAns = 0;
    long totalQuestions, negtiveMarks, marksForEach;

    ButtonGroup Rdgroup;
    Timer timer;

    boolean chk = false, rchk = false;

    public StudentHome() {
            BufferedImage img = null;
        try {
            img = ImageIO.read(new File("D:\\Java projects\\Exam_Mgmt_AMS\\src\\exam_mgmt_ams\\IMG_2243.JPG"));
        } catch (IOException e) {
           // e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(1075, 777, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        setContentPane(new JLabel(imageIcon));
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1075, 777);
        setLayout(null);

        setTitle("Softwarica Exam Management System. ");

        JLabel examLabel = new JLabel("Exam Name: ");
        examLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        examLabel.setBounds(10, 25, 162, 30);
        add(examLabel);

        examCombo = new JComboBox<>();
        examCombo.setBounds(210, 25, 238, 30);
        add(examCombo);

        JLabel stdNameLabel = new JLabel("UserName: ");
        stdNameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        stdNameLabel.setBounds(10, 65, 163, 30);
        add(stdNameLabel);
//
        stdNameText = new JTextField();
        stdNameText.setColumns(10);
        stdNameText.setBounds(214, 65, 238, 29);
        add(stdNameText);

        //
        startBtn = new JButton("Start Test");
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    current=0;
                    current++;
                    loadTest();
                    startTime();
            }
        });

        startBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        startBtn.setBounds(203, 120, 126, 40);
        add(startBtn);

        // display questions and answers radio buttons
        questionLabel = new JLabel("Question:");
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        questionLabel.setBounds(11, 165, 617, 30);
        add(questionLabel);

        //radio buttons for answers
        Rdgroup = new ButtonGroup();
        for (int i = 0; i < 5; i++) {
            radio_ans[i] = new JRadioButton();
            add(radio_ans[i]);
            Rdgroup.add(radio_ans[i]);
        }

        btnNext = new JButton("Next");
        btnNext.addActionListener(this);
        btnNext.setFont(new Font("Arial", Font.PLAIN, 12));
        btnNext.setBounds(230, 350, 120, 40);
        add(btnNext);

        btnPrev = new JButton("Prev");
        btnPrev.addActionListener(this);
        btnPrev.setFont(new Font("Arial", Font.PLAIN, 12));
        btnPrev.setBounds(70, 350, 120, 40);
        add(btnPrev);

        timeLabel = new JLabel("Time Remaining: " + "mm:ss");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        timeLabel.setBounds(650, 53, 190, 30);
        add(timeLabel);

        viewResultbtn = new JButton("View Result");
        viewResultbtn.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                     finalResults();
                    new ResultView().setVisible(true);
                    close();
                }
            }
        });
        viewResultbtn.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                 finalResults();
                new ResultView().setVisible(true);
                close();
            }
        });
        viewResultbtn.setFont(new Font("Arial", Font.PLAIN, 12));
        viewResultbtn.setEnabled(true);
        viewResultbtn.setBounds(400, 395, 112, 30);
        add(viewResultbtn);

        random = new Random();
        fload();
        
    }

    //form load for test 
    public final void fload() {
        //form load//
        score = questionAttempted = correctAttempts = wrongAttempts = totalMarks = achievedMarks = 0;
        correctAns = 0;
        totalQuestions = negtiveMarks = marksForEach = 0;
        currentQno = 1;
        ss = 60;
        chk = false;
        int mid=0;
        try {
            db = new Database();
            cn = db.getConnection();
            pstmt = cn.prepareStatement("select module_name from module order by module_name");
            rs = pstmt.executeQuery();
            examCombo.removeAllItems();
            examCombo.addItem("");
            
            while (rs.next()) {
                examCombo.addItem(rs.getString(1));
                 //mid= rs.getInt("m_id");

            }
             
        } catch (SQLException ex) {

            System.err.println(ex.getMessage());
        }
       //return mid;
       
    }

    void loadTest() //function to set next/previous question 
    {
        radio_ans[4].setSelected(true);

        try {
            Database d = new Database();
             ArrayList<String> arr = new ArrayList<>();
//            pstmt = d.conn.prepareStatement("select * from qanda where (moduleN=?) order by rand()");
//          pstmt.setString(1,((examCombo.getSelectedItem()).toString()) );
//            pstmt.executeQuery();
            if (current==0) {
                pstmt = d.conn.prepareStatement("select * from qanda where (moduleN=?) order by rand()");
                pstmt.setString(1, ((examCombo.getSelectedItem()).toString()));
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    arr.add(rs.getString("question"));
                    arr.add(rs.getString("opt_1"));
                    arr.add(rs.getString("opt_2"));
                    arr.add(rs.getString("opt_3"));
                    arr.add(rs.getString("opt_4"));
                    arr.add(rs.getString("ans_value"));
                       
                }
                 questionLabel.setText(currentQno + "." + arr.get(0));
                    radio_ans[0].setText(arr.get(1));
                    radio_ans[1].setText(arr.get(2));
                    radio_ans[2].setText(arr.get(3));
                    radio_ans[3].setText(arr.get(4));
             progress();
              //viewResultbtn.setVisible(true);  
            }
            if (current == 1) {
                pstmt = d.conn.prepareStatement("select * from qanda where (moduleN=?) order by rand()");
                pstmt.setString(1, ((examCombo.getSelectedItem()).toString()));
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    arr.add(rs.getString("question"));
                    arr.add(rs.getString("opt_1"));
                    arr.add(rs.getString("opt_2"));
                    arr.add(rs.getString("opt_3"));
                    arr.add(rs.getString("opt_4"));
                    arr.add(rs.getString("ans_value"));
                       
                }
                 questionLabel.setText(currentQno + "." + arr.get(0));
                    radio_ans[0].setText(arr.get(1));
                    radio_ans[1].setText(arr.get(2));
                    radio_ans[2].setText(arr.get(3));
                    radio_ans[3].setText(arr.get(4));
             progress();

            }
            if (current == 2) {
                pstmt = d.conn.prepareStatement("select * from qanda where (moduleN=?) order by rand()");
                //String sql = "select * from qanda where q_id=1";
                pstmt.setString(1, ((examCombo.getSelectedItem()).toString()));
                //pstmt = d.conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    arr.add(rs.getString("question"));
                    arr.add(rs.getString("opt_1"));
                    arr.add(rs.getString("opt_2"));
                    arr.add(rs.getString("opt_3"));
                    arr.add(rs.getString("opt_4"));
                    arr.add(rs.getString("ans_value"));
                }
                 questionLabel.setText(currentQno + "." + arr.get(0));
                    radio_ans[0].setText(arr.get(1));
                    radio_ans[1].setText(arr.get(2));
                    radio_ans[2].setText(arr.get(3));
                    radio_ans[3].setText(arr.get(4));
             progress();

            }
            if (current == 3) {
                pstmt = d.conn.prepareStatement("select * from qanda where (moduleN=?) order by rand()");
                //String sql = "select * from qanda where q_id=1";
                pstmt.setString(1, ((examCombo.getSelectedItem()).toString()));
                //pstmt = d.conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    arr.add(rs.getString("question"));
                    arr.add(rs.getString("opt_1"));
                    arr.add(rs.getString("opt_2"));
                    arr.add(rs.getString("opt_3"));
                    arr.add(rs.getString("opt_4"));
                    arr.add(rs.getString("ans_value"));
                }
                 questionLabel.setText(currentQno + "." + arr.get(0));
                    radio_ans[0].setText(arr.get(1));
                    radio_ans[1].setText(arr.get(2));
                    radio_ans[2].setText(arr.get(3));
                    radio_ans[3].setText(arr.get(4));
             progress();
            }
            if (current == 4) {
                pstmt = d.conn.prepareStatement("select * from qanda where (moduleN=?) order by rand()");
                //String sql = "select * from qanda where q_id=1";
                pstmt.setString(1, ((examCombo.getSelectedItem()).toString()));
                //pstmt = d.conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    arr.add(rs.getString("question"));
                    arr.add(rs.getString("opt_1"));
                    arr.add(rs.getString("opt_2"));
                    arr.add(rs.getString("opt_3"));
                    arr.add(rs.getString("opt_4"));
                    arr.add(rs.getString("ans_value"));
                       
                }
                 questionLabel.setText(currentQno + "." + arr.get(0));
                    radio_ans[0].setText(arr.get(1));
                    radio_ans[1].setText(arr.get(2));
                    radio_ans[2].setText(arr.get(3));
                    radio_ans[3].setText(arr.get(4));
                    
                    if (current>4)
                    {
                        finalResults();
                    }
                    
             progress();
             
                    btnNext.setEnabled(false);
                    viewResultbtn.setVisible(true);       
            }
           
            for (int i = 200, j = 0; i <= 310; i += 30, j++) {

                radio_ans[j].setBounds(20, i, 535, 25);
            }
          
        } catch (Exception e) {
            System.out.println("setnext\n" + e);
        }
    }

    //timer start and keep track 
    public void startTime() {
        timecounter = 19;
        ActionListener timerListener = (ActionEvent e) -> {
            if (timecounter > 0 || ss > 0) {
                if (ss > 0) {
                    ss--;
                } else {
                    ss = 60;
                    timecounter--;
                }
            } else {
                ss = 0;
                if (!chk) {
                    JOptionPane.showMessageDialog(null, "Time up!!");
                    finalResults();
                   
                } else {
                    finalResults();
                }
                timer.stop();
            }

            String s = Integer.toString(timecounter);
            String p = Integer.toString(ss);
            timeLabel.setText(s + ":" + p);
        };
        timer = new Timer(1000, timerListener); //timer from the util package to setup the timer
        timer.setInitialDelay(0);
        timer.start();
    }

    //calculating marks
    public void progress()//radio_Ans1, radio_Ans2, radio_Ans3, radio_Ans4;ans
    {
         negtiveMarks = 1;
        try {  
           String ansQry = "select ans_value from qanda where (moduleN='?') and q_id="+(current)+"";
           pstmt.setString(1,((examCombo.getSelectedItem()).toString()) );
            pstmt = db.conn.prepareStatement(ansQry);
            rs = pstmt.executeQuery(ansQry);
             
            if(rs.next()){
                correctAns=rs.getInt("ans_value");
            if (radio_ans[0].isSelected() && correctAns == 1) {
                score = score + 1;
                correctAttempts++;
                System.out.println(" ans 1");
            } else if (radio_ans[1].isSelected() && correctAns == 2) {
                score = score + 1;
                correctAttempts++;
                System.out.println(" ans 2");
            } else if (radio_ans[2].isSelected() && correctAns == 3) {
                score = score + 1;
                correctAttempts++;
                System.out.println(" ans 3");
            } else if (radio_ans[3].isSelected() && correctAns == 4) {
                score = score + 1;
                correctAttempts++;
                System.out.println(" ans 4");

            } else {
                wrongAttempts++;
                score = score - 1;
            }
            System.out.println(" Current score =" + score);
            questionAttempted++;
            }
            
        } catch (Exception ex) {

            System.err.println(ex.getMessage());
        }

    }
    //displaying Final results
    public void finalResults() {
        String msg = "Result!\n 1.score=" + score + "\n 2.questions attempted=" + questionAttempted + "\n 3.correctly attempted=" + correctAttempts + "\n 4.wrong attempts=" + wrongAttempts + "\nb5.total marks=" + totalMarks + "";
        JOptionPane.showMessageDialog(null, msg);
        System.out.print(msg);
//        try {
//            Qry = "update student SET(examName,questions_attempted,correctAttempts,wrong_attempts,total_marks ,final_marks ) "
//                    + "values(" + examCombo.getSelectedItem().toString().toLowerCase() + "'," + questionAttempted + "," + correctAttempts + "," + wrongAttempts + "," + totalMarks + "," + score + " "
//                    + "where username='" + stdNameText.getText().toLowerCase() + ")";
//            st.executeUpdate(Qry);
//
//        } catch (SQLException ex) {
////            System.err.print("Exception: ");
//            System.err.println(ex.getMessage());
//        }

    }

    public ArrayList getAns_value() {
        ArrayList<Integer> C_ans = new ArrayList<Integer>();
        try {
            Database db = new Database();
            String ansQry;
            ansQry = "select ans_value from qanda where moduleN='java' and q_id="+currentQno+"";
            pstmt = db.conn.prepareStatement(ansQry);
            rs = pstmt.executeQuery(ansQry);
            while (rs.next()) {
                C_ans.add(rs.getInt("ans_value"));
            }
        } catch (Exception e) {

        }
        return C_ans;
    }

    public void close() {
        this.dispose();
    }

    public static void main(String[] args) {
        new StudentHome().setVisible(true);
    }

   
    public void actionPerformed(ActionEvent ae) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (ae.getSource() == btnNext) {
                current++;
                currentQno += 1;
                loadTest();
                // progress();
        }
        if (ae.getSource() == btnPrev) {
            current--;
            currentQno -= 1;
            loadTest();

        }
    }
    }
//}
//*******//
//Test form loading
//    public void loadTest() {
//        try {
//            int totalRec = 0;
//            int randomNumber = 0;
//            if (firstIncheck) {
//                firstIncheck = false;
//                System.out.println("first call");
//            } else {
//                rchk = false;
//                if (radio_Ans1.isSelected()) {
//                    rchk = true;
//                }
//                if (radio_Ans2.isSelected()) {
//                    rchk = true;
//                }
//                if (radio_Ans3.isSelected()) {
//                    rchk = true;
//                }
//                if (radio_Ans4.isSelected()) {
//                    rchk = true;
//                }
//                if (!rchk) {
//                    JOptionPane.showMessageDialog(null, "Please select an answer...!");
//                    return;
//                }
//            }
