package solar.etl;

class Main {

    public static void main(String[] args) throws Exception {
        long tStart = System.currentTimeMillis();
        SolarProcess solarETL = new SolarProcess();
        solarETL.run(args[0]);
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        double elapsedSeconds = tDelta / 1000.0;
        System.out.println("time = " + elapsedSeconds);
    }
}
