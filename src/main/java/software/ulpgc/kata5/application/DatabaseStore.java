package software.ulpgc.kata5.application;

import software.ulpgc.kata5.io.Store;
import software.ulpgc.kata5.model.Movie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Stream;

public class DatabaseStore implements Store {
    private final Connection connection;

    @Override
    public Stream<Movie> movies() {
        try {
            return moviesIn(query());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<Movie> moviesIn(ResultSet query) {
        return Stream.generate(() -> movieIn(query)).takeWhile(Objects::nonNull);
    }

    private Movie movieIn(ResultSet rs) {
        try {
            return rs.next() ? readFrom(rs) : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Movie readFrom(ResultSet rs) throws SQLException {
        return new Movie(
                rs.getString("title"),
                rs.getInt("year"),
                rs.getInt("duration")
        );
    }

    private ResultSet query() throws SQLException {
        return connection.createStatement().executeQuery("SELECT * FROM movies");
    }

    public DatabaseStore(Connection connection) {
        this.connection = connection;
    }
}
