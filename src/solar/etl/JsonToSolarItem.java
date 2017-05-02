package solar.etl;

import mitsai.etl.commun.Item;
import mitsai.etl.extract.InputToItem;
import org.json.JSONArray;
import org.json.JSONObject;

/* {"date": "20101007",
 *  "area": ["/0000"],
 *  "number_of_spots": ["/01"],
 *  "hale_class": ["/\u03b1"],
 *  "mcintosh_class": ["/Axx"],
 *  "number": ["11111"],
 *  "image_urls": ["http://www.solarmonitor.org/data/2010/10/07/pngs/hxrt/hxrt_flter_fd_20101006_055344.png"],
  * "flares": [""],
  * "location": ["N22W45 (624\",277\")"],
  * "images": [
  *       {"url": "http://www.solarmonitor.org/data/2010/10/07/pngs/hxrt/hxrt_flter_fd_20101006_055344.png",
  *        "path": "/home/junior/solar/images/full/dfd007d514b57f25ab83cbae81ef9e98f379ccec.jpg",
  *        "checksum": "96aa7e95d15c9e95b8ab0c66cce35625"}],
  * "type": "XRT and NOAA Active Regions"}
 */
class JsonToSolarItem implements InputToItem {
    private static final SolarItem EMPTY_ITEM = new SolarItem();
    private JSONObject obj;

    @Override
    public Item convert(Object object) {
        obj = (JSONObject) object;
        return obj == null ? EMPTY_ITEM : makeConversion();
    }

    private Item makeConversion() {
        SolarItem item = new SolarItem();

        item.setDate(obj.getLong("date"));

        JSONArray imagesPath = obj.getJSONArray("images");
        if (imagesPath.length() != 0)
            item.setPath(imagesPath
                    .getJSONObject(0).getString("path"));

        final JSONArray areas = obj.getJSONArray("area");
        final int numberOfSunspots = areas.length();
        final JSONArray locations = obj.getJSONArray("location");
        final JSONArray haleClasses = obj.getJSONArray("hale_class");
        final JSONArray mcintoshClasses = obj.getJSONArray("mcintosh_class");
        final JSONArray numbers = obj.getJSONArray("number");
        for (int i = 0; i < numberOfSunspots; ++i) {
            item.addLocation(locations.getString(i));
            item.addHaleClass(haleClasses.getString(i));
            item.addMcintoshClass(mcintoshClasses.getString(i));
            item.addSunspotArea(areas.getString(i));
            item.addNumber(numbers.getInt(i));
        }
        item.setNumberOfSunspots(numberOfSunspots);

        return item;
    }
}
