package software.ulpgc.kata5.application;

import software.ulpgc.kata5.model.Movie;

public class MovieDeserializer {
    public static Movie fromTsv(String line) {
        return from(line.split("\t"));
    }

    private static Movie from(String[] split) {
        return new Movie(split[2], toInt(split[7]), toInt(split[5]));
    }

    private static int toInt(String s) {
        if (isValid(s)) return -1;
        return Integer.parseInt(s);
    }


    private static boolean isValid(String s) {
        return s.equals("\\N");
    }
}
