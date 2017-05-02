package mitsai.etl;

import mitsai.etl.extract.Extract;
import mitsai.etl.extract.InputToItem;
import mitsai.etl.load.Load;
import mitsai.etl.transform.Transform;

/**
 * Created by junior on 7/23/16.
 */
public class Process {

    private final Extract extractor;
    private final Transform transformer;
    private final Load load;

    public Process() throws Exception {
        extractor = new Extract();
        transformer = new Transform();
        load = new Load();
    }

    public void flow(String inputPath, InputToItem converter) throws Exception {
        extractor.setPath(inputPath, converter);
        extractor.run();

        transformer.setRecords(extractor.getItems());
        transformer.run();

        load.setItems(transformer.getRecords());
        load.run();
    }
}
