package software.ulpgc.kata5.application.pine;

import software.ulpgc.kata5.application.Desktop;
import software.ulpgc.kata5.application.MovieDeserializer;
import software.ulpgc.kata5.application.RemoteStore;

public class Main {
    public static void main(String[] args) {
        Desktop
                .create(new RemoteStore(MovieDeserializer::fromTsv))
                .display()
                .setVisible(true);
    }
}