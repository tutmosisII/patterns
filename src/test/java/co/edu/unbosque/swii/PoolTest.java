/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unbosque.swii;

import static co.edu.unbosque.swii.ObjectPoolTest.database;
import static co.edu.unbosque.swii.ObjectPoolTest.host;
import static co.edu.unbosque.swii.ObjectPoolTest.port;
import static co.edu.unbosque.swii.ObjectPoolTest.pwd;
import static co.edu.unbosque.swii.ObjectPoolTest.user;
import java.sql.Connection;
import java.util.NoSuchElementException;
import org.apache.commons.pool2.BaseObjectPool;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.testng.annotations.Test;

/**
 *
 * @author Alejandro
 */
public class PoolTest {

    public static final String pwd = "swii";
    public static final String host = "localhost";
    public static final int port = 5555;
    public static final String user = "swii";
    public static final String database = "swii";

    @Test(expectedExceptions = org.postgresql.util.PSQLException.class)
    public void soloDebeCrear5Conexiones() throws Exception {
        FabricaConexiones fc = new FabricaConexiones(host, port, database, user, pwd);

        ObjectPool<Connection> pool = new GenericObjectPool<Connection>(fc);
        for (int i = 0; i < 6; i++) {
            System.out.println(i);
            pool.borrowObject();
        }
    }
    
     @Test(timeOut = 3000,expectedExceptions = {NoSuchElementException.class})
    public void probandoTiempoDeEsperaCuandoNoHayRecursos() throws Exception {
        FabricaConexiones fc = new FabricaConexiones(host, port, database, user, pwd);
         GenericObjectPoolConfig config=new GenericObjectPoolConfig();
         config.setMaxTotal(4);
         //config.setMaxIdle(4);
      
         config.setMaxWaitMillis(2500);
         config.setBlockWhenExhausted(true);
      
        ObjectPool<Connection> pool = new GenericObjectPool<Connection>(fc,config);
        for (int i = 0; i < 6; i++) {
                        System.out.println(i);

            pool.borrowObject();
        }
    }

    @Test
    public void aprendiendoAControlarLasConexiones() throws Exception {
        FabricaConexiones fc = new FabricaConexiones(host, port, database, user, pwd);

        ObjectPool<Connection> pool = new GenericObjectPool<Connection>(fc);
        for (int i = 0; i < 6; i++) {
            Connection c = pool.borrowObject();
            pool.returnObject(c);
        }
    }

    @Test
    public void quePasaCuandoSeCierraUnaConexionAntesDeRetornarla() {

    }

    @Test
    public void quePasaCuandoSeRetornaUnaconexionContransaccionIniciada() {

    }

    @Test(threadPoolSize = 5, invocationCount = 5)
    public void midaTiemposParaInsertar1000RegistrosConSingleton() {

    }

    @Test(threadPoolSize = 5, invocationCount = 5)
    public void midaTiemposParaInsertar1000RegistrosConObjectPool() {

    }
}
