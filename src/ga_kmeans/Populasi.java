package ga_kmeans;

import java.util.ArrayList;
import java.util.List;

public class Populasi {
    List<Kromosom> populasi = new ArrayList<>();

    public Populasi() {
    }
    public Populasi(List<Kromosom> populasi) {
        this.populasi = populasi;
    }

    @Override
    public String toString() {
        return "Populasi " + populasi;
    }

    public List<Kromosom> getPopulasi() {
        return populasi;
    }

    public void setPopulasi(List<Kromosom> populasi) {
        this.populasi = populasi;
    }
}