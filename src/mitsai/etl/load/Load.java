package mitsai.etl.load;

import mitsai.etl.load.database.TargetFactory;

/**
 * Created by junior on 9/7/16.
 */
public class Load extends LoadProcess {
    public Load() throws Exception {
        super(TargetFactory.getTargetInstance());
    }
}
