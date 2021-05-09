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
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;
import org.w3c.dom.events.EventListener;

public class Admin_panel extends JFrame implements EventListener{

    JLabel status1;//temp status
     JTextField txt_ExName;
    JComboBox<String> module_combo, ExamName_combo;//module
    JLabel lblmain,lblExam,lblcateg,lbltime,lblNoQ,lblMarksPerQ,lblNegativeMarking;//main label
    JButton insertFormBtn;
    JSpinner spinNoQ, spinTime, spinMarksforEach, spinNegative;//number of question spinner
    String ModuleName, ExamName, MyName;//module name,exam name
    long time, NoQ, MarksForEach, NegativeMarks;//declaration
    JButton sbmt_btn;
    Database db;
    Connection conn;
    int i = 1;
    JMenuBar mbar;
    JMenu Add, Update, Delete;
    JMenuItem addexam, updateexam, deleteexam,deleteCateg;

    public Admin_panel() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 900);
        setLayout(null);
        setVisible(true);
        setTitle("Admin Exam Setup");
        //adding menu items
        mbar = new JMenuBar();
        Add = new JMenu("Add");
        Update = new JMenu("Update");
        Delete = new JMenu("Delete");
        //menu items:
        addexam = new JMenuItem("Add Exam ");
        
        updateexam = new JMenuItem("Update Exam");
        updateexam.setEnabled(true);
        updateexam.addMouseListener(new MouseAdapter() {
         
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == KeyEvent.VK_ENTER) {
                   // update();
                   fload();
                }
            }
            
        });
        //fload();
        add(updateexam);
        Update.add(updateexam);
         
//        //----------//
        
        //adding it to the Frame;
        mbar.add(Add);mbar.add(Update);mbar.add(Delete);
        Add.add(addexam);
        add(mbar);setJMenuBar(mbar);

        //********************************//
        //main label//
         lblmain = new JLabel("Exam Setup");
        lblmain.setHorizontalAlignment(SwingConstants.CENTER);
        lblmain.setFont(new Font("Arial Black", Font.PLAIN, 20));
        lblmain.setBounds(408, -3, 228, 42);
        add(lblmain);
        //---------//

        //exam label//
         lblExam = new JLabel("Exam Name");
        lblExam.setFont(new Font("Arial", Font.PLAIN, 15));
        lblExam.setBounds(16, 115, 162, 32);
        add(lblExam);
        //-----//

        //status label//
         status1 = new JLabel("");
        status1.setBounds(45, 446, 78, 14);
        add(status1);
        //-----//

        //module label->
         lblcateg = new JLabel("Module: ");
        lblcateg.setFont(new Font("Arial", Font.PLAIN, 15));
        lblcateg.setBounds(16, 171, 162, 32);
        add(lblcateg);
       //-----//

        //time duration label->
         lbltime = new JLabel("Time Duration(mins): ");
        lbltime.setFont(new Font("Arial", Font.PLAIN, 15));
        lbltime.setBounds(16, 221, 162, 32);
        add(lbltime);
        //-----//

        //number of questions label->
         lblNoQ = new JLabel("Number of Questions: ");
        lblNoQ.setFont(new Font("Arial", Font.PLAIN, 15));
        lblNoQ.setBounds(13, 275, 165, 32);
        add(lblNoQ);
        //-----//

        //marks for each question label->
         lblMarksPerQ = new JLabel("Marks for each Question: ");
        lblMarksPerQ.setFont(new Font("Arial", Font.PLAIN, 15));
        lblMarksPerQ.setBounds(13, 327, 178, 32);
        add(lblMarksPerQ);
        //-----//

        //Negative marks label//
         lblNegativeMarking = new JLabel("Negtive Mark For Each: ");
        lblNegativeMarking.setFont(new Font("Arial", Font.PLAIN, 15));
        lblNegativeMarking.setBounds(13, 384, 178, 32);
        add(lblNegativeMarking);
        //-----//
        //*********************//
        //** spinners**// spinNoQ, spinTime, spinMarksforEach, spinNegative
        //number of question spinner
        spinNoQ = new JSpinner();
        spinNoQ.setModel(new SpinnerNumberModel(10, 1, 100, 1));
        spinNoQ.setBounds(237, 271, 208, 32);
        add(spinNoQ);
        //-------//

        //time duration
        spinTime = new JSpinner();
        spinTime.setModel(new SpinnerNumberModel(new Integer(60), new Integer(10), null, new Integer(5)));
        spinTime.setBounds(237, 221, 208, 32);
        add(spinTime);
        //-------//

        //marks for each question
        spinMarksforEach = new JSpinner();
        spinMarksforEach.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
        spinMarksforEach.setBounds(237, 327, 208, 32);
        add(spinMarksforEach);
        //-------//

        //Negative marks for each
        spinNegative = new JSpinner();
        spinNegative.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
        spinNegative.setBounds(237, 384, 208, 32);
        add(spinNegative);
        //-------//
        //*****************//
        
        //comboboxes module_combo, ExamName_combo
        //module combo//
        module_combo = new JComboBox<>();
        module_combo.addItemListener(new ItemListener() {
            
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    if (!ExamName_combo.getSelectedItem().equals("")) {
//                        deleteCateg.setEnabled(true);
                            insert();
                    }
                }
            }
        });
        module_combo.setEditable(true);
        module_combo.setBounds(237, 171, 208, 32);
        add(module_combo);
        //-------------//

       //exam name combo//
        ExamName_combo = new JComboBox<>();
        ExamName_combo.addItemListener((ItemEvent ie) -> {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                if (!ExamName_combo.getSelectedItem().equals("")) {
                   insert();
                   //display();
                }
            }
        });
        ExamName_combo.setEditable(true);
        ExamName_combo.setBounds(237, 116, 208, 32);
        add(ExamName_combo);
        //------------//
        
        //*********************// 
        //Submit button->
        sbmt_btn = new JButton("Submit");
        sbmt_btn.addKeyListener(new KeyAdapter() {
            
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (insert()) {
                        fload();
                    }

                }
            }
        });
        sbmt_btn.addMouseListener(new MouseAdapter() { //mousebutton click event 
            
            public void mouseClicked(MouseEvent arg0) {
                 MyName=ExamName_combo.getSelectedItem().toString();
                if (insert()) {
                    fload();
                }
            }
        });//end of event
        sbmt_btn.setFont(new Font("Arial", Font.PLAIN, 12));
        sbmt_btn.setBounds(228, 474, 126, 41);
        add(sbmt_btn);
        
        //****************//
        //Insert question forms 
        insertFormBtn = new JButton("Insert Questions");
        insertFormBtn.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    new ExamForm().setVisible(true);
                    close();
                }
            }
        });
        insertFormBtn.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent arg0) {
                new ExamForm().setVisible(true);
                close();
            }
        });
//        insertFormBtn.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                 new InsertForm().setVisible(true);
//                close();
//            }
//        });
        insertFormBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        insertFormBtn.setEnabled(true);
        insertFormBtn.setBounds(3, 5, 126, 32);
        add(insertFormBtn); 
        fload();
    }
    //form load//
    public final void fload() {
        

        try {
            db = new Database();
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement("Select module_name from  module order by module_name where module_name=?");
             pstmt.setString(1,ModuleName);
            ResultSet rs = pstmt.executeQuery();
            module_combo.removeAllItems();
            ExamName_combo.removeAllItems();
            ExamName_combo.addItem("");
            module_combo.addItem("");
           // updateexam.setEnabled(false);
            sbmt_btn.setEnabled(true);
            while (rs.next()) {
                module_combo.addItem(rs.getString(1));
            }
             pstmt = conn.prepareStatement("Select exam_name from  exam_setup order by exam_name where exam_name=?");
             pstmt.setString(1,ExamName);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ExamName_combo.addItem(rs.getString(1));
            }
        } catch (Exception ex) {
            System.err.print("Exception: ");
            System.err.println(ex.getMessage());
        }
    }
    
    //Insert Data
    public boolean insert() {
        if (validation()) {
            return false;
        }
        if (!validation()) {
            String me = spinTime.getValue().toString();
            time = Long.parseLong(me);
            me = spinMarksforEach.getValue().toString();
            MarksForEach = Long.parseLong(me);
            me = spinNoQ.getValue().toString();
            NoQ = Long.parseLong(me);
            me = spinNegative.getValue().toString();
            NegativeMarks = Long.parseLong(me);
            ////////////////////////
   
            ModuleName = (String) module_combo.getSelectedItem();
            ModuleName = ModuleName.toUpperCase();
            ExamName = (String) ExamName_combo.getSelectedItem();
            ExamName = ExamName.toUpperCase();

            long m_id , id = 0;
            boolean flag = true, flag2 = true;
            Statement st;
            String sql;
            ResultSet rs;
            try {
                //working with exam combo checking existing //
                //{  
                sql = "select examID,exam_name from exam_setup order by exam_name";
                st = conn.createStatement();
                rs = st.executeQuery(sql);
                if (rs.next()) {
                    id = rs.getLong(1);
                }
                rs = st.executeQuery(sql);
                if (!rs.wasNull()) {
                    
                    while (rs.next()) {
                        // System.out.println(ename.equals(rs.getString(2)));
                        if ((ExamName.equals(rs.getString(2))) == true) {
                            System.out.println("Exam Already Exists...!");
                            flag2 = false;
                            return flag2;
                        }
                    }
                }
                //working with module table;
                //{
                sql = "Select m_id from  module order by m_id DESC limit 1";
                // System.out.println("f1");
                rs = st.executeQuery(sql);
                if (rs.next()) {
                    m_id = rs.getLong(1);
                } else {
                    m_id = 1;
                }
                sql = "select m_id,module_name from module";

                rs = st.executeQuery(sql);
                if (!rs.wasNull()) {
                   
                    while (rs.next()) {
                        // System.out.println(catname.equals(rs.getString(2)));
                        if ((ModuleName.equals(rs.getString(2))) == true) {
                            System.out.println("module found inserting id ...!");
                            flag = false;
                        }
                    }
                    if (flag == true) {
                        m_id++;

                        sql = "insert into module(m_id,module_name) values (" + m_id + ",'" + ModuleName + "')";
                        st.executeUpdate(sql);
                        System.out.println("New module inserted successfully !");
                    }
                }
                //}end of this
                if (flag2) {
                    sql = "Select examID from  exam_setup order by id DESC limit 1";
                    rs = st.executeQuery(sql);
                    if (rs.next()) {
                        id = rs.getLong(1) + 1;
                    } else {
                        id = 1;
                    }
//                    if (mfeq < nmfe)// negative mark validation
//                    {
//                        JOptionPane.showMessageDialog(null, "Negtive Marks Should be less than question mark...!");
//                        return false;
               /* */
//                    }
                    sql = "INSERT INTO exam_setup (m_id,exam_name,marks_each,negtive_marks,no_Question,time,) "
                            + "VALUES (" + m_id + ", " + ExamName + "," + MarksForEach + "," + NegativeMarks + "," + NoQ + "," + time + ",)";
                    st = conn.createStatement();
                    st.executeUpdate(sql);
                    System.out.println("Exam inserted!");
                    return true;
                }
            } catch (Exception ex) {
                
                System.err.println(ex.getMessage());
            }
        }
        return true;

    }//end of insert
    
    ///*************************//
    
    public void display()//when existing exam is selected will be updated;
    {
        //sbmt_btn.setEnabled(false);
        Statement st;
        String sql, temp = "";
        ResultSet rs;
        long id = 0;
        try {

            //Retrieving existing data
            sql = "select * from exam_setup where exam_name= " + ExamName_combo.getSelectedItem() + "limit 1";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            updateexam.setEnabled(false);
            sbmt_btn.setEnabled(true);
            // rs= db.selectCols("", "exam_setup","");
            if (rs.next()) {
                id = rs.getLong(1);
                System.out.println(id);
                rs = st.executeQuery(sql);
                //displaying data into fields
                if (rs.next()) {
                    updateexam.setEnabled(true);
                    sbmt_btn.setEnabled(false);
                    deleteexam.setEnabled(true);
                    spinTime.setValue(rs.getLong(3));
                    spinMarksforEach.setValue(rs.getLong(4));
                    spinNegative.setValue(rs.getLong(5));
                    spinNoQ.setValue(rs.getLong(6));
                }
                module_combo.removeAllItems();//clearing module combo
                //Retrieving current module 
                rs = st.executeQuery("Select module_name from  module where m_id=" + rs.getLong(2) + " limit 1");
                if (rs.next()) {
                    temp = rs.getString(1);
                }
                module_combo.addItem(temp);
                //Retrieving rest of modules 
                rs = st.executeQuery("Select module_name from  module order by module_name");
                while (rs.next()) {
                    if (!rs.getString(1).equals(temp)) {
                        module_combo.addItem(rs.getString(1));
                        //System.out.println(rs.getString(1));
                    }
                }
                ModuleName = (String) module_combo.getSelectedItem();
                ModuleName = ModuleName.toUpperCase();
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }//end 

    public void delExam() {
        long ex_id;
        String s = "";
        if (!((ExamName_combo.getSelectedItem().toString()).toUpperCase()).equals(s)) {
            try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select examID from exam_setup where exam_name='" + ExamName_combo.getSelectedItem().toString().toUpperCase() + "' limit 1");
                if (rs.next()) {
                    ex_id = rs.getLong(1);
                } else {
                    JOptionPane.showMessageDialog(null, "Exam does not exist!");
                    return;
                }
                System.out.println(ex_id);
                st.executeUpdate("delete * from exam_setup where examID =" + ex_id + "");
            } catch (Exception ex) {
                System.err.print("Exception: ");
                System.err.println(ex.getMessage());
            }
        } else {
            System.out.println("Exit");
        }
    }

    public void deletemodule()//deleting module will delete all exams consist of current module,questions and current module;
    {
        long moduleId = 0;
        String s = "";
        int dialogButton = 0;
        int dialogResult = JOptionPane.showConfirmDialog(null, "This will delete everything releted to current selected module", "Delete", dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION) {
            if (!((module_combo.getSelectedItem().toString()).toUpperCase()).equals(s)) {
                try {
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("select m_id from module where module_name='" + module_combo.getSelectedItem().toString().toUpperCase() + "' limit 1");
                    if (rs.next()) {
                        moduleId = rs.getLong(1);
                    } else {
                        JOptionPane.showMessageDialog(null, "Module does not exist!");
                        return;
                    }
                    System.out.println(moduleId);
                    st.executeUpdate("delete * from exam_setup where m_id =" + moduleId + "");
                    st.executeUpdate("delete * from exam_questions where m_id =" + moduleId + "");
                    st.executeUpdate("delete * from module where m_id=" + moduleId + "");
                } catch (Exception ex) {
                    System.err.print("Exception: ");
                    System.err.println(ex.getMessage());
                }
            } else {
                System.out.println("Exit");
            }
        }
    }//
    //**************************//
    
    
    //VALIDATION
    public boolean validation() {
        String msg = "";
        boolean check = false;
        System.out.println("******* \n");
        if (ExamName_combo.getSelectedItem().toString().equals("")) {
            msg = "Please select an exam name!";
            check = true;
            ExamName_combo.requestFocusInWindow();
        } else if (module_combo.getSelectedItem().toString().equals("")) {
            msg = "Please select a module!";
            check = true;
            module_combo.requestFocusInWindow();
        }
        if (check==true) {
            JOptionPane.showMessageDialog(null, msg);
        }
        return check;
    }
//clear the comboboxes
    public void clear() {
        module_combo.removeAllItems();
        ExamName_combo.removeAllItems();
    }
    
    public void close() {
        this.dispose();
        this.hide();
    }

    @Override
    public void handleEvent(org.w3c.dom.events.Event event) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
    /* module_combo, ExamName_combo;//module
     lblmain,lblExam,lblcateg,lbltime,lblNoQ,lblMarksPerQ,lblNegativeMarking;//main top label
     insertFormBtn, viewResultBtn, homeBtn;
     spinNoQ, spinTime, spinMarksforEach, spinNegative;//number of question spinner
     ModuleName, ExamName, MyName;//module name,exam name
     time, NoQ, MarksForEach, NegativeMarks;//declaration
    addexam, updateexam, deleteexam,deleteCateg
    */

         /*
        timed=Integer.parseInt();
        mfeq=(int) mfeq_spin.getValue();
        nmfe=(int) nmfe_spin.getValue();
        nofq = (int) noq_spin.getValue();*/

           // deleteexam.setEnabled(false);
           // deleteCateg.setEnabled(false);
            /////update_btn.setEnabled(false);
            ///// update_btn.setEnabled(false);//;("")