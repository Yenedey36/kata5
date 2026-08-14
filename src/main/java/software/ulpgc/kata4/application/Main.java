package software.ulpgc.kata4.application;

import software.ulpgc.kata4.model.Movie;
import software.ulpgc.kata4.viewmodel.Histogram;
import software.ulpgc.kata4.viewmodel.HistogramBuilder;

import java.io.IO;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Desktop.create().display(histogram()).setVisible(true);
    }

    private static Histogram histogram() {
        return HistogramBuilder
                .with(movies())
                .title("Movies per year")
                .x("Year")
                .y("Count")
                .legend("Movies")
                .use(Movie::year);
    }

    private static Stream<Movie> movies() {
        return new RemoteStore(MovieDeserializer::fromTsv)
                .movies()
                .limit(1000);
    }
}
