package solar.etl;

import mitsai.etl.Process;

/**
 * Created by junior on 9/7/16.
 */
class SolarProcess {
    private Process etl;

    public SolarProcess() throws Exception {
        etl = new Process();
    }

    public void run(String input) throws Exception {
        etl.flow(input, new JsonToSolarItem());
    }
}
