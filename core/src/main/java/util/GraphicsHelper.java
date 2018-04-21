package util;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.List;

public class GraphicsHelper {

    public static List<Point2D.Double> getClosestPoints(Ellipse2D source, Ellipse2D target) {
        Point2D.Double sourcePositive;
        Point2D.Double sourceNegative;
        Point2D.Double targetPositive;
        Point2D.Double targetNegative;
        // array holds source intersection [0] then target intersection [1]
        List<Point2D.Double> intersections = new ArrayList<>();

        // define line from center of source ellipse to target ellipse
        double x1 = source.getX();
        double y1 = source.getY();
        double x2 = target.getX();
        double y2 = target.getY();
        double gradient = getGradient(x1, y1, x2, y2);
        double c = getC(gradient, x1, y1);

        // circles defined by same centre points + radius
        double sourceRadius = source.getWidth() / 2.0;
        double targetRadius = target.getWidth() / 2.0;

        // points on src an target curves that minimise distance of resulting edge
        // positive/negative solution in quadratic equation
        // find intersection between source circle and line
        if (gradient == Double.MAX_VALUE) {
            sourcePositive = findPoint(sourceRadius, x1, y1, true);
            sourceNegative = findPoint(sourceRadius, x1, y1, false);
            targetPositive = findPoint(targetRadius, x2, y2, true);
            targetNegative = findPoint(targetRadius, x2, y2, false);
        } else {
            sourcePositive = findPoint(gradient, c, sourceRadius, x1, y1, true);
            sourceNegative = findPoint(gradient, c, sourceRadius, x1, y1, false);
            targetPositive = findPoint(gradient * -1, c, targetRadius, x2, y2, true);
            targetNegative = findPoint(gradient * -1, c, targetRadius, x2, y2, false);
        }

        Point2D.Double closestSource;
        Point2D.Double closestTarget;

        // if the source or target is a spider - these have radius 4
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
            // will be pos+neg or neg+pos so as to not cross contour
            if (sourcePositive.distance(targetNegative) < sourceNegative.distance(targetPositive)) {
                closestSource = sourcePositive;
                closestTarget = targetNegative;
            } else {
                closestSource = sourceNegative;
                closestTarget = targetPositive;
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

    public static double getGradient(double x1, double y1, double x2, double y2) {
        double deltaX = x2 - x1;
        double deltaY = -(y2 - y1); // y is 0 at top

        if (deltaX < 1 && deltaX > -1) {
            return Double.MAX_VALUE;
        } else if (deltaY < 1 && deltaY > -1) {
            return 0;
        }
        return deltaY / deltaX;
    }

    private static double solveForDiscriminant(double a, double b, double c, int sign) {
        if (b * b - 4 * a * c >= 0) {
            return (-b + sign * Math.sqrt((b * b) - (4 * a * c))) / (2 * a);
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
                y = solveForDiscriminant(a, b, c, 1);
            } else {
                y = solveForDiscriminant(a, b, c, -1);
            }
        } catch (NumberFormatException e) {
            // invalid point that wouldn't fit on screen
            return new Point2D.Double(Double.MAX_VALUE, Double.MAX_VALUE);
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
                x = solveForDiscriminant(a, b, c, 1);
            } else {
                x = solveForDiscriminant(a, b, c, -1);
            }
        } catch (NumberFormatException e) {
            // invalid point that wouldn't fit on screen
            return new Point2D.Double(Double.MAX_VALUE, Double.MAX_VALUE);
        }
        double y = gradient * x + intercept;
        return new Point2D.Double(x, y);
    }

    /**
     * No closed-form integral for Quadratic Bezier so compute approximation
     * @param curve
     * @return
     */
    public static double getLength(QuadCurve2D.Double curve) {
        double t;
        double length = 0.0D;
        int steps = 10; // 10 steps gives a decent approximation
        Point2D.Double currentPoint = new Point2D.Double();
        Point2D.Double previousPoint = new Point2D.Double();
        for (int i = 0; i <= 10; i++) {
            t = (i + 0.0D)/steps;
            currentPoint.setLocation(getBezierPoint(t, curve));

            if (i > 0) {
                double deltaX = currentPoint.getX() - previousPoint.getX();
                double deltaY = currentPoint.getY() - previousPoint.getY();
                length += Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            }
            previousPoint.setLocation(currentPoint);
        }
        return length;
    }

    private static Point2D.Double getBezierPoint(double t, QuadCurve2D.Double curve) {
        double p0_x = curve.getX1();
        double p0_y = curve.getY1();
        double p1_x = curve.getCtrlX();
        double p1_y = curve.getCtrlY();
        double p2_x = curve.getX2();
        double p2_y = curve.getY2();

        double x = Math.pow(1.0D-t,2)*p0_x + 2.0*(1.0D-t)*t*p1_x + Math.pow(t,2)*p2_x;
        double y = Math.pow(1.0D-t,2)*p0_y + 2.0*(1.0D-t)*t*p1_y + Math.pow(t,2)*p2_y;

        return new Point2D.Double(x,y);
    }

    public static boolean isSourceLeftOfTarget(double x1, double y1, double x2, double y2) {
        if (x1 == x2) {
            return (y1 > y2); // prefer arrows to go down rather than up
        } else {
            return (x1 < x2); // prefer arrows to go left to right
        }
    }

    private static boolean rootExists(double a, double b, double c) {
        try {
            solveForDiscriminant(a,b,c,1);
        } catch (NumberFormatException e1) {
            try {
                solveForDiscriminant(a,b,c,-1);
            } catch (NumberFormatException e2) {
                return false;
            }
        }
        return true;
    }

    /**
     * Find whether two quadcurves intersect
     * Quadcurve defined as P(t) = (1-t)^2.P_0 + 2(1-t)t.P_1 + t^2.P_2.
     * Intersect if we can find a root when curves are equated
     * @param c1
     * @param c2
     * @return
     */
    public static boolean curvesIntersect(QuadCurve2D c1, QuadCurve2D c2) {
        double x1_0 = c1.getX1();
        double x1_1 = c1.getCtrlX();
        double x1_2 = c1.getX2();
        double y1_0 = c1.getY1();
        double y1_1 = c1.getCtrlY();
        double y1_2 = c1.getY2();
        double x2_0 = c2.getX1();
        double x2_1 = c2.getCtrlX();
        double x2_2 = c2.getX2();
        double y2_0 = c2.getY1();
        double y2_1 = c2.getCtrlY();
        double y2_2 = c2.getY2();

        double aX = x1_0 + x1_2 - 2*x1_1 - (x2_0 + x2_2 - 2*x2_1);
        double bX = 2*x1_1 - 2*x1_0 - (2*x1_1 - 2*x1_0);
        double cX = x1_0 - x2_0;

        double aY = y1_0 + y1_2 - 2*y1_1 - (y2_0 + y2_2 - 2*y2_1);
        double bY = 2*y1_1 - 2*y1_0 - (2*y1_1 - 2*y1_0);
        double cY = y1_0 - y2_0;

        return rootExists(aX, bX, cX) && rootExists(aY, bY, cY);
    }
}
