package co.edu.unbosque.swii;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FabricaConexiones implements PooledObjectFactory<Connection> {

    private static final Boolean control = true;

    public PooledObject<Connection> makeObject() throws Exception {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://aretico.com:5432/software_2";
        Connection connection = null;
        connection = DriverManager.getConnection(url, "grupo6", "YckGwYC8r3");
        DefaultPooledObject defaultPooledObject = new DefaultPooledObject<Connection>(connection);
        return defaultPooledObject;
    }

    public void destroyObject(PooledObject<Connection> pooledObject) throws Exception {
        pooledObject.getObject().close();
    }

    public boolean validateObject(PooledObject<Connection> pooledObject) {

        try {
            Statement s = pooledObject.getObject().createStatement();

            return s.execute("Select 1");
        } catch (SQLException ex) {
            Logger.getLogger(FabricaConexiones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void activateObject(PooledObject<Connection> pooledObject) throws Exception {
    }

    public void passivateObject(PooledObject<Connection> pooledObject) throws Exception {
        pooledObject.getObject().rollback();
    }
}
