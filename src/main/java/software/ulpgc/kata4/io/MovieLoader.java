package software.ulpgc.kata4.io;

import software.ulpgc.kata4.model.Movie;

import java.util.List;
import java.util.stream.Stream;

public interface MovieLoader {
    Stream<Movie> movies();
}
