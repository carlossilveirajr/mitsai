package solar.miner;

import mitsai.miner.Extractor;

/**
 * Created by junior on 2/4/17.
 */
class Main {
    private static double support;
    private static double confidence;
    private static int deltaSpace;
    private static int deltaTime;

    public static void main(String[] args) throws Exception {
        parseInput(args);

        long start = System.currentTimeMillis();

        Extractor extractor = new Extractor(new MongoItemToSunspot());
        extractor.run(support, confidence, deltaSpace, deltaTime);

        long end = System.currentTimeMillis();
        System.out.println("time = " + (end - start) / 1000.0 + " ms.");
    }

    private static void parseInput(String[] args) {
        try {
            support = Double.valueOf(args[0]);
            confidence = Double.valueOf(args[1]);
            deltaSpace = Integer.valueOf(args[2]);
            deltaTime = Integer.valueOf(args[3]);
        } catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("The system expects support, confidence, maximum space and time as double.");
            System.exit(-1);
        }
    }
}
