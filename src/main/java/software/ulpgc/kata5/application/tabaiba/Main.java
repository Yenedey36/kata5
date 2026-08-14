package software.ulpgc.kata5.application.tabaiba;

import software.ulpgc.kata5.application.*;
import software.ulpgc.kata5.model.Movie;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:movies.db")){
            con.setAutoCommit(false);
            importIfNeeded(con);
            Desktop
                    .create(new DatabaseStore(con))
                    .display()
                    .setVisible(true);
        }
    }

    private static void importIfNeeded(Connection con) throws SQLException {
        if(new File("movies.db").exists()) return;
        Stream<Movie> movies = new RemoteStore(MovieDeserializer::fromTsv).movies();
        new DatabaseRecorder(con).record(movies);
    }
} 

