package mitsai.etl.extract.reader;

import mitsai.etl.extract.InputToItem;

/**
 * Created by junior on 9/1/16.
 */
public class ReaderFactory {

    public Reader getReader(String extension, InputToItem converter)
            throws NotSupportedInputFileFormatException,
                   NullInputToItemConverter {
        if (converter == null)
            throw new NullInputToItemConverter();

        return selectReader(extension, converter);
    }

    private Reader selectReader(String extension, InputToItem converter) {
        switch (extension.toLowerCase()) {
            case "json":
                    return new JsonReader(converter);
                default:
                    throw new NotSupportedInputFileFormatException();
        }
    }

    public static class NotSupportedInputFileFormatException extends RuntimeException {}

    public class NullInputToItemConverter extends RuntimeException { }
}
