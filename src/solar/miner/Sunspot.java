package solar.miner;

import javafx.util.Pair;
import mitsai.miner.Item;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by junior on 3/2/16.
 */
public abstract class Sunspot implements Item {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final LocalDate date;
    private final Pair<Integer, Integer> location;
    private final String locationString;
    private final Integer number;

    Sunspot(String date, String location, Integer number) {
        this.date = LocalDate.parse(date,FORMATTER);
        this.number = number;

        locationString = location;

        String [] s = location.split(" ");
        s = s[1].split("\"");
        this.location = new Pair<>(Integer.valueOf(s[0].replace("(","")), Integer.valueOf(s[1].replace(",", "")));
    }

    @Override
    public LocalDate getTime() {
        return date;
    }

    @Override
    public Pair<Integer, Integer> getLocation() {
        return location;
    }

    @Override
    public Integer getId() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass())
            return false;

        Item that = (Item) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + locationString.hashCode();
        result = 31 * result + number.hashCode();
        return result;
    }
}
