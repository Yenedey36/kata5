package software.ulpgc.kata4.viewmodel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Histogram implements Iterable<Integer> {
    private final Map<Integer, Integer> map;

    public Histogram() {
        this.map = new HashMap<>();
    }

    public void add(int bin){
        map.put(bin, count(bin)+1);
    }

    public int count(int bin) {
        return map.getOrDefault(bin, 0);
    }

    @Override
    public Iterator<Integer> iterator() {
        return map.keySet().iterator();
    }

    public int size(){
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }


}
