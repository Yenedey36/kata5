package software.ulpgc.kata5.io;

import software.ulpgc.kata5.model.Movie;

import java.sql.SQLException;
import java.util.stream.Stream;

public interface Recorder {
    void record(Stream<Movie> movies) throws SQLException;
}
