package util;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class GraphicsHelper {
    public static List<Point2D.Double> getClosestPoints(Ellipse2D source, Ellipse2D target) {
        // array holds source intersection then target intersection
        List<Point2D.Double> intersections = new ArrayList<>();

        // define line
        double x1 = source.getX();
        double x2 = target.getX();
        double y1 = source.getY();
        double y2 = target.getY();
        double gradient = getGradient(x1, y1, x2, y2);
        double c = getC(gradient, x1, y1);

        // circles defined by same centre points + radius
        double sourceRadius = source.getWidth() / 2.0;
        double targetRadius = target.getWidth() / 2.0;

        Point2D.Double sourcePositive;
        Point2D.Double sourceNegative;
        Point2D.Double targetPositive;
        Point2D.Double targetNegative;

        // find intersection between source circle and line
        if (gradient == Double.MAX_VALUE) {
            sourcePositive = findPoint(sourceRadius, x1, y1, true);
            sourceNegative = findPoint(sourceRadius, x1, y1, false);
            targetPositive = findPoint(targetRadius, x2, y2, true);
            targetNegative = findPoint(targetRadius, x2, y2, false);
        } else {
            sourcePositive = findPoint(gradient, c, sourceRadius, x1, y1, true);
            sourceNegative = findPoint(gradient, c, sourceRadius, x1, y1, false);
            targetPositive = findPoint(gradient*-1, c, targetRadius, x2, y2, true);
            targetNegative = findPoint(gradient*-1, c, targetRadius, x2, y2, false);
        }

        Point2D.Double closestSource;
        Point2D.Double closestTarget;

        // if the source or target is a spider
        if (sourceRadius < 5 && targetRadius < 5) {
            closestSource = new Point2D.Double(x1, y1);
            closestTarget = new Point2D.Double(x2, y2);
        } else if (sourceRadius < 5) {
            closestSource = new Point2D.Double(x1, y1);

            if (closestSource.distance(targetPositive) < closestSource.distance(targetNegative)) {
                closestTarget = targetPositive;
            } else {
                closestTarget = targetNegative;
            }
        } else if (targetRadius < 5) {
            closestTarget = new Point2D.Double(x2, y2);
            if (closestTarget.distance(sourcePositive) < closestTarget.distance(sourceNegative)) {
                closestSource = sourcePositive;
            } else {
                closestSource = sourceNegative;
            }

        } else {
            // return points that give the smallest distance between circles
            closestSource = sourcePositive;
            closestTarget = targetPositive;

            if (sourceNegative.distance(targetPositive) < closestSource.distance(closestTarget)) {
                closestSource = sourceNegative;
                closestTarget = targetPositive;
            }

            if (sourcePositive.distance(targetNegative) < closestSource.distance(closestTarget)) {
                closestSource = sourcePositive;
                closestTarget = targetNegative;
            }

            if (sourceNegative.distance(targetNegative) < closestSource.distance(closestTarget)) {
                closestSource = sourceNegative;
                closestTarget = targetNegative;
            }
        }
        intersections.add(closestSource);
        intersections.add(closestTarget);
        return intersections;
    }

    private static double getC(double gradient, double x, double y) {
        if (gradient < 1 && gradient > -1) {
            return y;
        }
        return y - gradient * x;
    }

    private static double getGradient(double x1, double y1, double x2, double y2) {
        double deltaX = x2 - x1;
        double deltaY = -(y2 - y1); // y is 0 at top

        if (deltaX < 1 && deltaX > -1) {
            return Double.MAX_VALUE;
        } else if (deltaY < 1 && deltaY > -1) {
            return 0;
        }
        return deltaY / deltaX;
    }

    private static double solveForPositiveDiscriminant(double a, double b, double c) {
        if (b * b - 4 * a * c >= 0) {
            return (-b + Math.sqrt((b * b) - (4 * a * c))) / (2 * a);
        } else {
            throw new NumberFormatException();
        }
    }

    private static double solveForNegativeDiscriminant(double a, double b, double c) {
        if (b * b - 4 * a * c >= 0) {
            return (-b - Math.sqrt((b * b) - (4 * a * c))) / (2 * a);
        } else {
            throw new NumberFormatException();
        }
    }

    /**
     * Solve for Y where gradient and intercept are INFINITY
     *
     * @param radius
     * @param x1
     * @param y1
     * @param positive
     * @return
     */
    private static Point2D.Double findPoint(double radius, double x1, double y1, boolean positive) {
        double a = 1;
        double b = -2 * y1;
        double c = Math.pow((y1), 2) - Math.pow(radius, 2);

        double y;

        try {
            if (positive) {
                y = solveForPositiveDiscriminant(a, b, c);
            } else {
                y = solveForNegativeDiscriminant(a, b, c);
            }
        } catch (NumberFormatException e) {
            return new Point2D.Double(0,0);
        }

        return new Point2D.Double(x1, y);
    }

    // Find coordinates of intersection where we take either positive or negative root in quadratic equation
    private static Point2D.Double findPoint(double gradient, double intercept, double radius, double x1, double y1, boolean positive) {
        double a = Math.pow(gradient, 2) + 1;
        double b = 2 * gradient * (intercept - y1) - 2 * x1;
        double c = Math.pow(x1, 2) + Math.pow((intercept - y1), 2) - Math.pow(radius, 2);

        double x;

        try {
            if (positive) {
                x = solveForPositiveDiscriminant(a, b, c);
            } else {
                x = solveForNegativeDiscriminant(a, b, c);
            }
        } catch (NumberFormatException e) {
            return new Point2D.Double(0,0);
        }

        double y = gradient * x + intercept;
        return new Point2D.Double(x, y);
    }

    public static double getLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
    }


    public static boolean isGradientPositive(double x1, double y1, double x2, double y2) {
        if (y1 == y2) {
            return (x1 < x2);
        } else if (x1 == x2) {
            return (y1 > y2); // prefer arrows to go down rather than up
        } else {
            return getGradient(x1, y1, x2, y2) > 0;
        }
    }
}
