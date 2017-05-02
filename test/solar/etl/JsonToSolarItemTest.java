package solar.etl;

import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by junior on 9/7/16.
 */
public class JsonToSolarItemTest {
    private final JSONObject json = new JSONObject(
            "{\"date\": \"20101007\"," +
            " \"area\": [\"/0000\"]," +
            " \"number_of_spots\": [\"/01\"]," +
            " \"hale_class\": [\"/\\u03b1\"]," +
            " \"mcintosh_class\": [\"/Axx\"]," +
            " \"number\": [\"11111\"]," +
            " \"image_urls\": [\"http://www.solarmonitor.org/data/2010/10/07/pngs/" +
                    "hxrt/hxrt_flter_fd_20101006_055344.png\"]," +
            " \"flares\": [\"\"]," +
            " \"location\": [\"N22W45 (624\\\",277\\\")\"]," +
            " \"images\": [" +
                    "{\"url\": \"http://www.solarmonitor.org/data/2010/10/07/pngs/" +
                        "hxrt/hxrt_flter_fd_20101006_055344.png\"," +
                    " \"path\": \"full/dfd007d514b57f25ab83cbae81ef9e98f379ccec.jpg\"," +
                    " \"checksum\": \"96aa7e95d15c9e95b8ab0c66cce35625\"}]," +
            " \"type\": \"XRT and NOAA Active Regions\"" +
        "}");

    @Test public void
    assertInternalFields() throws Exception {
        JsonToSolarItem converter = new JsonToSolarItem();
        SolarItem item = (SolarItem) converter.convert(json);

        assertThat(item.getDate(), is(20101007L));
        assertThat(item.getImagePath(), is("full/dfd007d514b57f25ab83cbae81ef9e98f379ccec.jpg"));
        assertThat(item.size(), is(1));
        assertThat(item.hashFor(0).size(), is(7));
    }
}
