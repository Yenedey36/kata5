package software.ulpgc.kata4.application;

import software.ulpgc.kata4.io.MovieLoader;
import software.ulpgc.kata4.model.Movie;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.zip.GZIPInputStream;

public class RemoteMovieLoader implements MovieLoader {
    private static final String REMOTE_URL = "https://datasets.imdbws.com/title.basics.tsv.gz";
    private static final int BUFFER_SIZE = 65536;
    private final Function<String, Movie> deserializer;

    public RemoteMovieLoader(Function<String, Movie> deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public List<Movie> loadAll() {
        try {
            return loadFrom(URI.create(REMOTE_URL).toURL());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Movie> loadFrom(URL url) throws IOException {
        return loadFrom(url.openConnection());
    }

    private List<Movie> loadFrom(URLConnection connection) throws IOException {
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new BufferedInputStream(connection.getInputStream(), BUFFER_SIZE))){
            return loadFrom(gzipInputStream);
        }
    }

    private List<Movie> loadFrom(InputStream inputStream) throws IOException {
        return loadFrom(new BufferedReader(new InputStreamReader(inputStream)));
    }

    private List<Movie> loadFrom(BufferedReader reader) throws IOException {
        ArrayList<Movie> movies = new ArrayList<>();
        reader.readLine();
        while (true){
            String line = reader.readLine();
            if(line==null) break;
            movies.add(deserializer.apply(line));
        }
        return movies;
    }
}
