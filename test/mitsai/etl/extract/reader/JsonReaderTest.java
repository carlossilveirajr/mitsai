package mitsai.etl.extract.reader;

import org.json.JSONObject;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by junior on 9/5/16.
 */
public class JsonReaderTest {
    private final String information = "{" +
            "    \"id\": 1," +
            "    \"name\": \"A green door\"," +
            "    \"price\": 12.50," +
            "    \"tags\": [\"home\", \"green\"]" +
            "}";

    @Test(expected = FileNotFoundException.class) public void
    notFindFile_throwsException() throws Exception {
        Reader json = new JsonReader(null);
        json.invokeFor("FileThatMustNotExist.withNoExtension");
    }

    @Test public void
    transformTheInformationInTheJSonFormat() throws Exception {
        JsonReader json = new TestableJsonReader();

        json.invokeFor("");

        JSONObject obj = new JSONObject(information);

        assertThat(json.getJsonObjects().get(0).toString(),
                is(obj.toString()));
    }

    class TestableJsonReader extends JsonReader {
        public TestableJsonReader() {
            super(null);
        }

        @Override
        protected FileReader getFileReader(String filePath) throws FileNotFoundException {
            return null;
        }

        @Override
        protected BufferedReader getBufferedReader(FileReader fileReader) {
            return new BufferedReader(new StringReader(information));
        }

        @Override
        protected void convertInputToItem(JSONObject obj) { }
    }
}
