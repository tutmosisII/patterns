package co.edu.unbosque.swii;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by alejandro on 4/09/15.
 */
public class SingletonTest {

    @Test(threadPoolSize = 3, invocationCount = 3)
    public void debeCrearUnaSolaInstanciaDeLaConexion() throws SQLException, ClassNotFoundException {
        System.out.println(Thread.currentThread().getId());
        Connection cxA=SingletonConnection.getConnection();
        Connection cxB=SingletonConnection.getConnection();
        Assert.assertEquals(cxB,cxA);
    }
}
