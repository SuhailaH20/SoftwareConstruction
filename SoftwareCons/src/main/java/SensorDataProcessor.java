
import java.io.*;


public class SensorDataProcessor {
// Sensor data and limits.
    public double[][][] sensorData;
    public double[][] limit;

  /**
     * Constructor for the SensorDataProcessor class.
     *
     * @param sensorData  3D array representing sensor data.
     * @param limit       2D array representing limits for the sensor data.
     */
    public SensorDataProcessor(double[][][] sensorData, double[][] limit) {
        this.sensorData = sensorData;
        this.limit = limit;
    }

    private double calculateAverage(double[] array) {
   
        double val = 0;
        for (int i = 0; i < array.length; i++) {
            val += array[i];
        }
        return val / array.length;
    }

    public void calculate(double d) {
       
        double[][][] calculatedData = new double[sensorData.length][sensorData[0].length][sensorData[0][0].length];
      
// Write racing stats data into a file
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("RacingStatsData.txt"));
            for (int i = 0; i < sensorData.length; i++) {
                for (int j = 0; j < sensorData[0].length; j++) {
                    for (int k = 0; k < sensorData[0][0].length; k++) {
                        calculatedData[i][j][k] = sensorData[i][j][k] / d
                                - Math.pow(limit[i][j], 2.0);
                        if (average(calculatedData[i][j]) > 10 && average(calculatedData[i][j])
                                < 50) {
                            break;
                        } else if (Math.max(sensorData[i][j][k], calculatedData[i][j][k])
                                > sensorData[i][j][k]) {
                            break;
                        } else if (Math.pow(Math.abs(sensorData[i][j][k]), 3)
                                < Math.pow(Math.abs(calculatedData[i][j][k]), 3)
                                && calculateAverage(sensorData[i][j]) < calculatedData[i][j][k] && (i + 1)
                                * (j + 1) > 0) {
                            calculatedData[i][j][k] *= 2;
                        } else {
                            continue;
                        }
                    }
                }
            }
            for (i = 0; i < calculatedData.length; i++) {
                for (j = 0; j < calculatedData[0].length; j++) {
                    out.write(calculatedData[i][j] + "\t");
                }
            }
            out.close();
        } catch (Exception e) {
            System.out.println("Error= " + e.getMessage());
        }
    }
}
