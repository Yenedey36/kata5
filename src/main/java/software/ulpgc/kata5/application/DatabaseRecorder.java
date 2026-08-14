package software.ulpgc.kata5.application;

import software.ulpgc.kata5.io.Recorder;
import software.ulpgc.kata5.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Stream;

public class DatabaseRecorder implements Recorder {

    private final Connection connection;
    private final PreparedStatement statement;
    private int count = 0;

    public DatabaseRecorder(Connection connection) throws SQLException {
        this.connection = connection;
        createTableIfNotExists();
        this.statement = connection.prepareStatement("INSERT INTO movies (title, year, duration) VALUES (?, ?, ?)");
    }

    private void createTableIfNotExists() throws SQLException {
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS movies(title STRING, year INTEGER, duration INTEGER)");
    }

    @Override
    public void record(Stream<Movie> movies) throws SQLException {
        movies.forEach(movie -> this.record(movie));
    }

    private void record(Movie movie) {
        try{
            write(movie);
            flushIfNeeded();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void flushIfNeeded() throws SQLException {
        if(mustFlush()) flush();
    }

    private void flush() throws SQLException {
        statement.executeBatch();
    }

    private boolean mustFlush() {
        return ++count % 10000 == 0;
    }

    private void write(Movie movie) throws SQLException {
        statement.setString(1, movie.title());
        statement.setInt(2, movie.year());
        statement.setInt(3, movie.duration());
        statement.addBatch();
    }
}
