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
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class RemoteStore implements MovieLoader {
    private static final String REMOTE_URL = "https://datasets.imdbws.com/title.basics.tsv.gz";
    private static final int BUFFER_SIZE = 65536;
    private final Function<String, Movie> deserializer;

    public RemoteStore(Function<String, Movie> deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public Stream<Movie> movies() {
        try {
            return loadAllFrom(URI.create(REMOTE_URL).toURL());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<Movie> loadAllFrom(URL url) throws IOException {
        return loadAllFrom(url.openConnection());
    }

    private Stream<Movie> loadAllFrom(URLConnection connection) throws IOException {
        return loadAllFrom(unzip(connection.getInputStream()));
    }

    private Stream<Movie> loadAllFrom(InputStream inputStream) throws IOException {
        return loadFrom(new BufferedReader(new InputStreamReader(inputStream)));
    }

    private GZIPInputStream unzip(InputStream inputStream) throws IOException {
        return new GZIPInputStream(new BufferedInputStream(inputStream, BUFFER_SIZE));
    }

    private Stream<Movie> loadFrom(BufferedReader reader) throws IOException {
        return reader.lines().skip(1).map(deserializer);
    }
}
