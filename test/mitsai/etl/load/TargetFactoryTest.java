package mitsai.etl.load;

import mitsai.etl.load.database.TargetFactory;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by junior on 9/7/16.
 */
public class TargetFactoryTest extends TargetFactory {

    private static String wished_target;

    @Test(expected = TargetFactory.TargetTypeNotSupported.class)
    public void
    notSupportedTarget_throwsException() throws Exception {
        wished_target = "NonSupportedTarget";
        new TargetFactoryTest().getTarget();
    }

    @Override
    protected String getTargetFromConfiguration() throws IOException {
        return wished_target;
    }
}
