package solar.miner;

/**
 * Created by junior on 2/8/17.
 */
public class TextualSunspot extends Sunspot {
    private final String value;

    public TextualSunspot(String date, String location, Integer number, String value) {
        super(date, location, number);
        this.value = value;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
