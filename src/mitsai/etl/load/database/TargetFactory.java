package mitsai.etl.load.database;

import mitsai.etl.commun.Configuration;
import mitsai.etl.load.database.cassandra.Cassandra;
import mitsai.etl.load.database.mariadb.MariaDB;
import mitsai.etl.load.database.mongo.Mongo;
import mitsai.etl.load.database.postgis.PostGIS;
import mitsai.etl.load.database.sqlite.SQLite;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by junior on 9/5/16.
 */
public class TargetFactory {

    public static Target getTargetInstance()
            throws TargetTypeNotSupported, IOException, SQLException, ClassNotFoundException {
        return new TargetFactory().getTarget();
    }

    @NotNull
    protected Target getTarget()
            throws IOException, SQLException, ClassNotFoundException {
        String target = getTargetFromConfiguration();

        switch (target.toUpperCase()) {
            case "MONGO":
                return new Mongo();
            case "SQLITE":
                return new SQLite();
            case "MARIADB":
                return new MariaDB();
            case "POSTGIS":
                return new PostGIS();
            case "CASSANDRA":
                return new Cassandra();
            default:
                throw new TargetTypeNotSupported();
        }
    }

    protected String getTargetFromConfiguration() throws IOException {
        return Configuration.getInstance().getConfiguration("target");
    }

    public static class TargetTypeNotSupported extends RuntimeException {}
}
