package software.ulpgc.kata4.io;

import software.ulpgc.kata4.model.Movie;

import java.util.List;

public interface MovieLoader {
    List<Movie> loadAll();
}
