package software.ulpgc.kata5.application;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import software.ulpgc.kata5.io.Store;
import software.ulpgc.kata5.viewmodel.Histogram;
import software.ulpgc.kata5.viewmodel.HistogramBuilder;
import software.ulpgc.kata5.model.Movie;

import javax.swing.*;

public class Desktop extends JFrame {

    private final Store store;

    private Desktop(Store store) {
        this.store = store;
        this.setTitle("Histogram");
        this.setResizable(false);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
    }

    public static Desktop create(Store store){
        return new Desktop(store);
    }

    public Desktop display(){
        this.getContentPane().add(chartPanelWith(histogram()));
        return this;
    }

    private Histogram histogram() {
        return HistogramBuilder.with(store.movies().limit(1000))
                .title("Movies per year")
                .x("Year")
                .y("Count")
                .legend("Movies")
                .use(Movie::year);
    }

    private ChartPanel chartPanelWith(Histogram histogram) {
        return new ChartPanel(chartWith(histogram));
    }

    private JFreeChart chartWith(Histogram histogram) {
        return ChartFactory.createHistogram(
                histogram.title(),
                histogram.x(),
                histogram.y(),
                datasetWith(histogram)
        );
    }

    private XYSeriesCollection datasetWith(Histogram histogram) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesIn(histogram));
        return dataset;
    }

    private XYSeries seriesIn(Histogram histogram) {
        XYSeries series = new XYSeries(histogram.legend());
        for (int bin: histogram){
            series.add(bin, histogram.count(bin));
        }
        return series;
    }
}
