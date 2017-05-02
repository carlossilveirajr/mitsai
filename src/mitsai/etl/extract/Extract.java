package mitsai.etl.extract;

import mitsai.etl.commun.Item;
import mitsai.etl.extract.reader.Reader;
import mitsai.etl.extract.reader.ReaderFactory;

import java.util.List;
import java.util.Objects;

/**
 * Created by junior on 9/1/16.
 */
public class Extract {
    public static final String DEFAULT_EXTENSION = "json";
    private String path = "";
    private Reader reader = null;

    public void setPath(String inputPath, InputToItem converter)
            throws ReaderFactory.NotSupportedInputFileFormatException,
                   NoSetPathException {
        checkAndSetPath(inputPath);

        String extension = DEFAULT_EXTENSION;
        try {
            extension = inputPath.split("\\.")[1];
        } catch (ArrayIndexOutOfBoundsException ignored) { }

        reader = (new ReaderFactory()).getReader(extension, converter);
    }

    private void checkAndSetPath(String inputPath) throws NoSetPathException {
        if (inputPath == null || Objects.equals(inputPath, ""))
            throw new NoSetPathException();

        path = inputPath;
    }

    public void run() throws Exception {
        if (reader == null)
            throw new NoSetPathException();

        reader.invokeFor(path);
    }

    public List<Item> getItems() {
        return reader.getItems();
    }

    public class NoSetPathException extends RuntimeException {}
}
