import java.util.Random;
import java.util.ArrayList;

/*
challenge implemented (commented out):
Make it work for ellipses too
 */

/*
class Circle extends Ellipse {
    public Circle(double x, double y, double r){
        super(x, y, r, r);
    }
}

class Ellipse {
    double centerX;
    double centerY;
    double xAxis;
    double yAxis;
    public Ellipse(double x, double y, double r1, double r2) {
        this.centerX = x;
        this.centerY = y;
        this.xAxis = r1;
        this.yAxis = r2;
    }
}

 */

class Circle  {
    double centerX;
    double centerY;
    double radius;

    public Circle(double x, double y, double r) {
        this.centerX = x;
        this.centerY = y;
        this.radius = r;
    }
}

public class MonteCarlo{
    static Random rand = new Random();
    public static void main(String[] args){
        ArrayList<Circle> circs = new ArrayList<Circle>();
        circs.add(new Circle(1, 1, 1));
        circs.add(new Circle(1, 2, 1));
        System.out.println("Estimated area:");
        System.out.println(estimateArea(circs, 100));
    }
    private static double sample(double min, double max){
        //This method returns a random number between min and max
        return min + (max - min) * rand.nextDouble();
    }
    private static boolean isIn(double x, double y, Circle c){
        //This method returns true if the point (x, y) is within the circle c, and false otherwise
        double dist = Math.sqrt(Math.pow(x - c.centerX, 2) + Math.pow(y - c.centerY, 2));
        return dist <= c.radius;
        // return dist <= (Math.sqrt(Math.pow((x-this.centerX), 2) + Math.pow((y-this.centerY), 2)))

    }
    //helper method
    public static double[] getMinMax(ArrayList<Circle> c) {
        double[] minMax = new double[4];
        /*
        double minX = c.get(0).centerX-c.get(0).xAxis;
        double maxX = c.get(0).centerX+c.get(0).xAxis;
        double minY = c.get(0).centerY-c.get(0).yAxis;
        double maxY = c.get(0).centerY+c.get(0).yAxis;
        for (int i = 1; i < c.size(); i++) {
            double currMinX = c.get(i).centerX-c.get(i).xAxis;
            double currMaxX = c.get(i).centerX+c.get(i).xAxis;
            double currMinY = c.get(i).centerY-c.get(i).yAxis;
            double currMaxY = c.get(i).centerY+c.get(i).yAxis;

         */
        double minX = c.get(0).centerX-c.get(0).radius;
        double maxX = c.get(0).centerX+c.get(0).radius;
        double minY = c.get(0).centerY-c.get(0).radius;
        double maxY = c.get(0).centerY+c.get(0).radius;
        for (int i = 1; i < c.size(); i++) {
            double currMinX = c.get(i).centerX-c.get(i).radius;
            double currMaxX = c.get(i).centerX+c.get(i).radius;
            double currMinY = c.get(i).centerY-c.get(i).radius;
            double currMaxY = c.get(i).centerY+c.get(i).radius;
            if (currMinX < minX) {
                minX = currMinX;
            }
            if (currMaxX > maxX) {
                maxX = currMaxX;
            }
            if (currMinY < minY) {
                minY = currMinY;
            }
            if (currMaxY > maxY) {
                maxY = currMaxY;
            }

        }
        minMax[0] = minX;
        minMax[1] = maxX;
        minMax[2] = minY;
        minMax[3] = maxY;

        return minMax;
    }
    public static double estimateArea(ArrayList<Circle> circles, int numSamples){
        //YOUR CODE HERE
        int count = 0;
        double[] vals = getMinMax(circles);
        for (int i = 0;  i < numSamples; i++) {
            double x = sample(vals[0], vals[1]);
            double y = sample(vals[2], vals[3]);

            for (int j = 0; j < circles.size(); j++) {
                if (isIn(x, y, circles.get(j))) {
                    count++;
                }
            }
        }

        double estimate = (1.0 * count / numSamples)*((vals[1]-vals[0]) * (vals[3] - vals[2]));

        return estimate;
    }

}