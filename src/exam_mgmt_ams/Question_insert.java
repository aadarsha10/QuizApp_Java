/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exam_mgmt_ams;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import org.w3c.dom.events.EventListener;
public class Question_insert extends JFrame implements EventListener {

    
    private JTextField ans1Text, ans2Text, ans3Text, ans4Text,ModuleNM;
  //  JComboBox<String> ModuleCombo;
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
    private JLabel countLabel,optionsLabel,aLabel,bLabel,cLabel,dLabel,totalLabel;
    private JButton BtnExam_setup,Btn_logout,viewResultbtn;

    public static void main(String[] args) {
       new Question_insert().setVisible(true);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void handleEvent(org.w3c.dom.events.Event event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
