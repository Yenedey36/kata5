package software.ulpgc.kata4.application;

import software.ulpgc.kata4.model.Movie;
import software.ulpgc.kata4.viewmodel.Histogram;
import software.ulpgc.kata4.viewmodel.HistogramBuilder;

import java.io.IO;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Movie> movies = new RemoteMovieLoader(TsvMovieParser::fromTsv).loadAll();
        Histogram histogram = new HistogramBuilder(m -> (m.year() / 10) * 10).build(movies);
        for (int bin: histogram){
            IO.println(bin + ": "+ histogram.count(bin));
        }
    }
}
