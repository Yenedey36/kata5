package software.ulpgc.kata5.viewmodel;

import software.ulpgc.kata5.model.Movie;

import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Stream;

public class HistogramBuilder {
    private final Stream<Movie> movies;
    private final HashMap<String, String> labels;

    public static HistogramBuilder with(Stream<Movie> movies){
        if(movies == null) throw new IllegalArgumentException("movies can´t be null");
        return new HistogramBuilder(movies);
    }

    private HistogramBuilder(Stream<Movie> movies){
        this.labels = new HashMap<>();
        this.movies = movies.filter(m -> m.year() >= 1900).filter(m -> m.year() <= 2025);
    }

    public HistogramBuilder title(String title){
        this.labels.put("title", title);
        return this;
    }

    public HistogramBuilder x(String x){
        this.labels.put("x", x);
        return this;
    }

    public HistogramBuilder y(String y){
        this.labels.put("y", y);
        return this;
    }

    public HistogramBuilder legend(String legend){
        this.labels.put("legend", legend);
        return this;
    }

    public Histogram use(Function<Movie, Integer> binarize){
        Histogram histogram = new Histogram(labels);
        movies.map(binarize).forEach(histogram::add);
        return histogram;
    }
}

