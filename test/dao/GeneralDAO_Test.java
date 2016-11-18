/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.sun.media.jfxmedia.logging.Logger;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

/**
 *
 * @author orisserm
 */
public class GeneralDAO_Test {
    private static DataSource myDataSource;
    private static Connection myConnection ;

    public static DataSource getDataSource() throws SQLException {
        org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
        ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
        ds.setUser("sa");
        ds.setPassword("sa");
        return ds;
    }	

    @BeforeClass
    public static void setUpClass() throws IOException, SqlToolError, SQLException {
        // On crée la connection vers la base de test "in memory"
        myDataSource = getDataSource();
        myConnection = myDataSource.getConnection();
        // On initialise la base avec le contenu d'un fichier de test
        String sqlFilePath = GeneralDAO_Test.class.getResource("sample.sql").getFile();
        SqlFile sqlFile = new SqlFile(new File(sqlFilePath));

        sqlFile.setConnection(myConnection);
        sqlFile.execute();
        sqlFile.closeReader();
    }
    
    @AfterClass
    public static void tearDownClass() throws IOException, SqlToolError, SQLException {
        myConnection.close();
    }
    
    @Test
    public void checkUserPassword() {
        GeneralDAO g = new GeneralDAO(myDataSource);
        
        System.out.println(g.checkUserPassword("jumboeagle@example.com", "2"));
        
        Logger.logMsg(Logger.DEBUG, 
                g.checkUserPassword("jumboeagle@example.com", "2") == true ? "T" : "F");
        
        assertEquals(g.checkUserPassword("jumboeagle@example.com", "1"), true);
        //assertEquals(g.checkUserPassword("jumboeagle@example.com", "2"), false);
        //assertEquals(g.checkUserPassword("www.parts@ford.example.com", "1"), false);
        //assertEquals(g.checkUserPassword("www.parts@ford.example.com", "753"), true);
    }
}
