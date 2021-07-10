package dao;

import configuration.CustomerAPIConfiguration;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class DataBaseConnector {

    public static Handle openConnection() {
        return open().open();
    }

    public static Jdbi openSqlUpdateConnection() {
        return open();
    }

    private static Jdbi open() {
        CustomerAPIConfiguration conf = new CustomerAPIConfiguration();
        Jdbi jdbi = Jdbi.create(conf.getConnectionUrl(), conf.getUser(), conf.getPassword());
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

}
