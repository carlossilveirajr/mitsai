package solar.miner;

/**
 * Created by junior on 2/8/17.
 */
public class FeatureSunspot extends Sunspot {
    private final String feature;
    private final String area;

    public FeatureSunspot(String date, String location, Integer number, String feature, String area) {
        super(date, location, number);
        this.feature = feature;
        this.area = area;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + feature.hashCode();
        result = 31 * result + area.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return '[' + feature + ", " + area + ']';
    }

    @Override
    public String getValue() {
        return feature;
    }
}
