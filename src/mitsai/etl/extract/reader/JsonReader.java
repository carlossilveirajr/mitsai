package mitsai.etl.extract.reader;

import mitsai.etl.commun.Item;
import mitsai.etl.extract.InputToItem;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by junior on 7/24/16.
 */
class JsonReader implements Reader {
    private final InputToItem converter;
    private final List<JSONObject> jsons = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();

    public JsonReader(InputToItem converter) {
        this.converter = converter;
    }

    @Override
    public void invokeFor(String filePath)
            throws IOException {
        FileReader fileReader = getFileReader(filePath);
        BufferedReader bufferedReader = getBufferedReader(fileReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            JSONObject obj = new JSONObject(line);
            jsons.add(obj);

            convertInputToItem(obj);
        }

        bufferedReader.close();
    }

    FileReader getFileReader(String filePath) throws FileNotFoundException {
        return new FileReader(filePath);
    }

    BufferedReader getBufferedReader(FileReader fileReader) {
        return new BufferedReader(fileReader);
    }

    void convertInputToItem(JSONObject obj) {
        items.add(converter.convert(obj));
    }

    public List<JSONObject> getJsonObjects() {
        return jsons;
    }

    public List<Item> getItems() {
        return items;
    }
}
