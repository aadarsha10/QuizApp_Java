/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exam_mgmt_ams;

import java.sql.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author HP
 */
public class DatabaseTest {
    
    public DatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getConnection method, of class Database.
     */
//    @Test
//    public void testGetConnection() {
//        System.out.println("getConnection");
//        Database instance = new Database();
//        Connection expResult = null;
//        Connection result = instance.getConnection();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }

    /**
     * Test of RegistrationSave method, of class Database.
     */
    @Test
    public void testRegistrationSave() {
        System.out.println("RegistrationSave");
        String Fname = "aadarsha";
        String Lname = "shrestha";
        String email = "ams@gmail";
        String Usrname = "ams";
        String Pwd = "stha";
        Database instance = new Database();
        int expResult = 1;
        int result = instance.RegistrationSave(Fname, Lname, email, Usrname, Pwd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class Database.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        String Fname = "aadarsha man";
        String Lname = "shrestha";
        String email = "ams123@gmail.com";
        String Usrname = "ams";
        Database instance = new Database();
        int expResult = 3;
        int result = instance.update(Fname, Lname, email, Usrname);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of tokenGen method, of class Database.
     */
    @Test
    public void testTokenGen() {
        //failing means the function is working because the token is supposed to be randomly generated
        System.out.println("tokenGen");
        Database instance = new Database();
        String expResult = "abc1";
        String result = instance.tokenGen();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
