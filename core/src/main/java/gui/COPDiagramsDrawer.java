package gui;

import concrete.ConcreteArrow;
import concrete.ConcreteCOP;
import concrete.ConcreteEquality;
import concrete.ConcreteSubDiagram;
import icircles.concreteDiagram.*;
import lang.Dot;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.StringWriter;
import java.util.*;
import java.util.List;

public class COPDiagramsDrawer extends JPanel {
    private static final BasicStroke DEFAULT_CONTOUR_STROKE = new BasicStroke(1.2F);
    private static final BasicStroke HIGHLIGHT_STROKE = new BasicStroke(3.5F);
    private static final Color HIGHLIGHT_LEG_COLOUR;
    private static final Color HIGHLIGHTED_FOOT_COLOUR;
    private static final Color HIGHLIGHT_STROKE_COLOUR;
    private static final Color HIGHLIGHT_ZONE_COLOUR;
    private static final int LABEL_OFFSET_X; // position to draw string
    private static final int LABEL_OFFSET_Y;
    private static final int LABEL_SOURCE_OFFSET; //position for initial t source
    private static final long serialVersionUID = 6593217652932473248L;
    private DOMImplementation domImpl;
    private String svgNS;
    private Document document;
    private SVGGraphics2D svgGenerator;
    private ConcreteSubDiagram diagram;
    private double scaleFactor;
    private AffineTransform trans;
    private CircleContour highlightedContour;
    private ConcreteZone highlightedZone;
    private ConcreteSpiderFoot highlightedFoot;
    private HashMap<String, Ellipse2D.Double> circleMap;
    private List<Component> ellipses;
    private List<Component> lines;
    private List<Component> areas;
    private List<Component> labels;
    private int width;
    private int height;
    private int offsetX = 0;

    COPDiagramsDrawer(ConcreteSubDiagram diagram, int width, int height, int offsetX) {
        this.domImpl = GenericDOMImplementation.getDOMImplementation();
        this.svgNS = "http://www.w3.org/2000/svg";
        this.document = this.domImpl.createDocument(this.svgNS, "svg", null);
        this.svgGenerator = new SVGGraphics2D(this.document);
        this.scaleFactor = 1.0D;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.trans = new AffineTransform();
        this.highlightedContour = null;
        this.highlightedZone = null;
        this.highlightedFoot = null;
        this.circleMap = new HashMap<>();
        this.init();
        this.resetDiagram(diagram);
        this.resizeContents();
        this.initComponents();
    }

    private void init() {
        this.setBackground(new Color(255, 255, 255));
        this.setLayout(null);
    }

    private int getAdjustedWidth() {
        if (this.getWidth() == 0) {
            return width;
        } else {
            return this.getWidth();
        }
    }
  
    private int getAdjustedHeight() {
        if (this.getWidth() == 0) {
            return height - 92;
        } else {
            return this.getHeight();
        }
    }

    private int getAdjustedOffsetX() {
        if (this.getWidth() == 0) {
            return offsetX;
        } else {
            return this.getCenteringTranslationX() + this.getX();
        }
    }

    private int getAdjustedOffsetY() {
        if (this.getHeight() == 0) {
            return 66;
        } else {
            return this.getCenteringTranslationY() + getY();
        }
    }

    private int getAdjustedCenteringOffsetX() {
        if (this.getWidth() == 0) {
            return 0;
        } else {
            return this.getCenteringTranslationX();
        }
    }

    private int getAdjustedCenteringOffsetY() {
        if (this.getWidth() == 0) {
            return 0;
        } else {
            return this.getCenteringTranslationY();
        }
    }
  
    private void initComponents() {
        this.lines = new ArrayList<>();
        this.ellipses = new ArrayList<>();
        this.areas = new ArrayList<>();
        this.labels = new ArrayList<>();

        if (this.diagram != null) {
            Font font = this.diagram.getFont();
            Color colour;
            Stroke stroke;
            ArrayList<ConcreteZone> shadedZones = this.diagram.getShadedZones();
            if (shadedZones != null) {
                for (ConcreteZone z : shadedZones) {
                    if (z.getColor() != null) {
                        colour = z.getColor();
                    } else {
                        colour = Color.lightGray;
                    }
                    Area a = z.getShape(this.diagram.getBox()).createTransformedArea(this.trans);
                    areas.add(new Component(a, colour));
                }
            }

            Set<ConcreteZone> highlightedZones = this.diagram.getHighlightedZones();
            if (highlightedZones != null) {
                for (ConcreteZone z : highlightedZones) {
                    Area a = z.getShape(this.diagram.getBox()).createTransformedArea(this.trans);
                    areas.add(new Component(a, HIGHLIGHT_ZONE_COLOUR));
                }
            }

            if (this.getHighlightedZone() != null) {
                areas.add(new Component(this.getHighlightedZone().getShape(this.diagram.getBox()).createTransformedArea(this.trans), HIGHLIGHT_ZONE_COLOUR));
            }

            stroke = DEFAULT_CONTOUR_STROKE;
            ArrayList<CircleContour> circles = this.diagram.getCircles();
            if (circles != null) {

                for (CircleContour cc : circles) {
                    Color col = cc.color();
                    if (col == null) {
                        col = Color.black;
                    }
                    Ellipse2D.Double tmpCircle = new Ellipse2D.Double();
                    transformCircle(this.scaleFactor, cc.getCircle(), tmpCircle);

                    circleMap.put(cc.ac.getLabel(), new Ellipse2D.Double(tmpCircle.getCenterX() + getAdjustedOffsetX(), tmpCircle.getCenterY() + getAdjustedOffsetY(), tmpCircle.getHeight(), tmpCircle.getWidth()));
                    ellipses.add(new Component(tmpCircle, stroke, col, false));

                    if (cc.ac.getLabel() != null) {
                        if (cc.stroke() != null) {
                            stroke = cc.stroke();
                        } else {
                            stroke = DEFAULT_CONTOUR_STROKE;
                        }

                        if (!cc.ac.getLabel().startsWith("_")) {
                            labels.add(new Component(tmpCircle.getCenterX() - LABEL_OFFSET_X, (int) (tmpCircle.getCenterY() - tmpCircle.getHeight() / 2 - LABEL_OFFSET_Y), cc.ac.getLabel(), font, stroke, col));
                        }
                    }
                }
            }

            ConcreteSpider highlightedSpider = this.getHighlightedFoot() == null ? null : this.getHighlightedFoot().getSpider();
            colour = Color.black;

            ArrayList<ConcreteSpider> spiders = this.diagram.getSpiders();
            if (spiders != null) {
                for (ConcreteSpider spider : this.diagram.getSpiders()) {
                    Color oldColor = null;
                    Stroke oldStroke = null;
                    if (highlightedSpider == spider) {
                        oldColor = colour;
                        colour = HIGHLIGHT_LEG_COLOUR;
                        oldStroke = stroke;
                        stroke = HIGHLIGHT_STROKE;
                    }

                    Iterator var10 = spider.legs.iterator();

                    while (var10.hasNext()) {
                        ConcreteSpiderLeg leg = (ConcreteSpiderLeg) var10.next();
                        Line2D.Double line = new Line2D.Double(leg.from.getX() * this.scaleFactor, (int) (leg.from.getY() * this.scaleFactor), (int) (leg.to.getX() * this.scaleFactor), (int) (leg.to.getY() * this.scaleFactor));
                        lines.add(new Component(line, stroke, colour));
                    }

                    var10 = spider.feet.iterator();

                    while (var10.hasNext()) {
                        ConcreteSpiderFoot foot = (ConcreteSpiderFoot) var10.next();
                        Ellipse2D.Double circle = new Ellipse2D.Double();
                        foot.getBlob(circle);
                        Color oldColor2 = colour;
                        translateCircleCentre(this.scaleFactor, circle, circle);
                        if (this.getHighlightedFoot() == foot) {
                            oldColor2 = colour;
                            colour = HIGHLIGHTED_FOOT_COLOUR;
                            scaleCircleCentrally(circle, 1.4D);
                        }

                        if (diagram instanceof ConcreteCOP && ((ConcreteCOP) this.diagram).containsInitialT && spider.as.getName().equals("t")) {
                            circleMap.put(foot.getSpider().as.getName(), new Ellipse2D.Double(circle.getCenterX() + getAdjustedOffsetX(), circle.getCenterY() + getAdjustedOffsetY() + LABEL_SOURCE_OFFSET, circle.getHeight(), circle.getWidth()));
                        } else {
                            ellipses.add(new Component(circle, stroke, colour, true));
                            circleMap.put(foot.getSpider().as.getName(), new Ellipse2D.Double(circle.getCenterX() + getAdjustedOffsetX(), circle.getCenterY() + getAdjustedOffsetY(), circle.getHeight(), circle.getWidth()));
                        }

                        if (this.getHighlightedFoot() == foot) {
                            colour = oldColor2;
                        }
                    }

                    if (spider.as.getName() != null) {
                        if (!spider.as.getName().startsWith("_")) {
                            ConcreteSpiderFoot foot = spider.feet.get(0);
                            labels.add(new Component((int) (foot.getX() * this.trans.getScaleX()) - 5, (int) (foot.getY() * this.trans.getScaleY()) - 10, spider.as.getName(), font, stroke, colour));
                        }
                        if (highlightedSpider == spider) {
                            colour = oldColor;
                            stroke = oldStroke;
                        }
                    }
                }
            }

            if (this.getHighlightedContour() != null) {
                Ellipse2D.Double tmpCircle = new Ellipse2D.Double();
                colour = HIGHLIGHT_STROKE_COLOUR;
                stroke = HIGHLIGHT_STROKE;
                transformCircle(this.scaleFactor, this.getHighlightedContour().getCircle(), tmpCircle);
                ellipses.add(new Component(tmpCircle, stroke, colour, false));
            }

            if (diagram.dots != null && diagram.dots.size() > 0) {
                List<String> dots = new ArrayList<>(this.diagram.dots);
                int numDots = dots.size();
                int currentXPos = getAdjustedWidth() / 2 - (19 * (numDots - 1)) - getAdjustedCenteringOffsetX();
                double y = getAdjustedHeight() / 2 - getAdjustedCenteringOffsetY();
                for (String dotLabel : dots) {
                    Ellipse2D.Double dotCircle = new Ellipse2D.Double(currentXPos, y, 8, 8);
                    if (diagram instanceof ConcreteCOP && ((ConcreteCOP) diagram).containsInitialT && dotLabel.equals("t")) {
                        circleMap.put(dotLabel, new Ellipse2D.Double(currentXPos + getAdjustedOffsetX() + LABEL_OFFSET_Y, y + getAdjustedOffsetY() + LABEL_SOURCE_OFFSET, 8, 8));
                    } else {
                        circleMap.put(dotLabel, new Ellipse2D.Double(currentXPos + getAdjustedOffsetX() + LABEL_OFFSET_Y, y + getAdjustedOffsetY(), 8, 8));
                        ellipses.add(new Component(dotCircle, stroke, colour, true));
                    }
                    labels.add(new Component(currentXPos, (int) y - 10, dotLabel, font, stroke, colour));
                    currentXPos += 40;
                }
            }
          
            for (ConcreteArrow arrow : diagram.arrows) {
                String source = arrow.getAbstractArrow().getSourceLabel();
                String target = arrow.getAbstractArrow().getTargetLabel();
                if (circleMap.containsKey(source)) {
                    arrow.setSource(circleMap.get(source));
                }
                if (circleMap.containsKey(target)) {
                    arrow.setTarget(circleMap.get(target));
                }
            }

            for (ConcreteEquality equality : diagram.equalities) {
                System.out.println("have equalities");
                String arg1 = equality.getAbstractEquality().getArg1();
                String arg2 = equality.getAbstractEquality().getArg2();
                if (circleMap.containsKey(arg1)) {
                    equality.setSource(circleMap.get(arg1));
                }
                if (circleMap.containsKey(arg2)) {
                    equality.setTarget(circleMap.get(arg2));
                }
            }
        }
    }

    HashMap<String, Ellipse2D.Double> getCircleMap() {
        return circleMap;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (this.diagram == null) {
            this.setBackground(Color.red);
            g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
            super.paint(g);
        } else {
            super.paint(g);
            g2d.setBackground(Color.white);
            g2d.drawRect(0,0,this.getWidth(), this.getHeight());
            g.translate(this.getCenteringTranslationX(), this.getCenteringTranslationY());
            initComponents();

            for (Component area: areas) {
                g2d.setColor(area.getColour());
                g2d.fill(area.getArea());
            }

            for (Component ellipse: ellipses) {
                g2d.setColor(ellipse.getColour());
                g2d.setStroke(ellipse.getStroke());
                if (ellipse.fill) {
                    g2d.fill(ellipse.getEllipse()); // spiders, dots
                } else {
                    g2d.draw(ellipse.getEllipse()); // contours
                }
            }

            for (Component label: labels) {
                g2d.setStroke(label.getStroke());
                g2d.setColor(label.getColour());
                Font font = label.getFont();
                if (font != null) {
                    g2d.setFont(font);
                }
                g2d.drawString(label.getLabel(), (int) label.getX(), (int) label.getY());
            }

            for (Component line: lines) {
                g2d.setColor(line.getColour());
                g2d.setStroke(line.getStroke());
                Line2D.Double line2 = line.getLine();
                g2d.drawLine((int) line2.x1, (int) line2.y1, (int) line2.x2, (int) line2.y2);
            }
        }
    }

    private void setScaleFactor(double newScaleFactor) {
        this.scaleFactor = newScaleFactor;
        this.recalculateTransform();
        this.repaint();
    }

    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        this.resizeContents();
    }

    public String toString() {
        this.paint(this.svgGenerator);
        StringWriter w = new StringWriter();

        try {
            this.svgGenerator.stream(w);
        } catch (SVGGraphics2DIOException var3) {
            return "<!-- SVG Generation Failed -->";
        }

        return w.toString();
    }

  private ConcreteZone getHighlightedZone() {
        return this.highlightedZone;
    }

    private void setHighlightedZone(ConcreteZone highlightedZone) {
        if (this.highlightedZone != highlightedZone) {
            this.setHighlightedContour(null);
            this.setHighlightedFoot(null);
            this.highlightedZone = highlightedZone;
            this.repaint();
        }
    }

    private CircleContour getHighlightedContour() {
        return this.highlightedContour;
    }

    private void setHighlightedContour(CircleContour highlightedContour) {
        if (this.highlightedContour != highlightedContour) {
            this.setHighlightedZone(null);
            this.setHighlightedFoot(null);
            this.highlightedContour = highlightedContour;
            this.repaint();
        }
    }

    private ConcreteSpiderFoot getHighlightedFoot() {
        return this.highlightedFoot;
    }

    private void setHighlightedFoot(ConcreteSpiderFoot foot) {
        if (this.highlightedFoot != foot) {
            this.setHighlightedZone(null);
            this.setHighlightedContour(null);
            this.highlightedFoot = foot;
            this.repaint();
        }
    }

    private void resetDiagram(ConcreteSubDiagram diagram) {
        this.diagram = diagram;
        if (diagram == null) {
            this.setPreferredSize(null);
        } else {
            this.setPreferredSize(new Dimension(diagram.getSize(), diagram.getSize()));
        }
        this.resizeContents();
    }

    private void resizeContents() {
        if (this.diagram != null) {
            int size = this.diagram.getSize();
            this.setScaleFactor(1.0D);
            if (size > 0 && getHeight() > 0 && getWidth() > 0) {
                this.setScaleFactor((double)Math.min((float)this.getWidth() / (float)size, (float)(this.getHeight() - 50) / (float)size));
            }
        }
    }

    private void recalculateTransform() {
        this.trans.setToScale(this.scaleFactor, this.scaleFactor);
    }

    private static void transformCircle(double scaleFactor, Ellipse2D.Double inCircle, Ellipse2D.Double outCircle) {
        translateCircle(scaleFactor, inCircle, outCircle);
        scaleCircle(scaleFactor, inCircle, outCircle);
    }

    private static void translateCircle(double scaleFactor, Ellipse2D.Double inCircle, Ellipse2D.Double outCircle) {
        outCircle.x = inCircle.x * scaleFactor;
        outCircle.y = inCircle.y * scaleFactor;
    }

    private static void translateCircleCentre(double scaleFactor, Ellipse2D.Double inCircle, Ellipse2D.Double outCircle) {
        double correctionFactor = (scaleFactor - 1.0D) / 2.0D;
        outCircle.x = inCircle.x * scaleFactor + inCircle.width * correctionFactor;
        outCircle.y = inCircle.y * scaleFactor + inCircle.height * correctionFactor;
    }

    private static void scaleCircle(double scaleFactor, Ellipse2D.Double inCircle, Ellipse2D.Double outCircle) {
        outCircle.width = inCircle.width * scaleFactor;
        outCircle.height = inCircle.height * scaleFactor;
    }

    private static void scaleCircleCentrally(Ellipse2D.Double circle, double scale) {
        circle.x -= circle.width * (scale - 1.0D) / 2.0D;
        circle.y -= circle.height * (scale - 1.0D) / 2.0D;
        circle.width *= scale;
        circle.height *= scale;
    }

    private int getCenteringTranslationX() {
        return Math.abs(this.getWidth() - (int)Math.round((double)this.diagram.getSize() * this.scaleFactor)) / 2;
    }

    private int getCenteringTranslationY() {
        return Math.abs(this.getHeight() - (int)Math.round((double)this.diagram.getSize() * this.scaleFactor)) / 2;
    }

    protected Graphics getComponentGraphics(Graphics g) {
        return this.svgGenerator;
    }

    static {
        HIGHLIGHT_LEG_COLOUR = Color.BLUE;
        HIGHLIGHTED_FOOT_COLOUR = Color.RED;
        HIGHLIGHT_STROKE_COLOUR = Color.RED;
        HIGHLIGHT_ZONE_COLOUR = new Color(0,255,0,77);
        LABEL_OFFSET_X = 8;
        LABEL_OFFSET_Y = 5;
        LABEL_SOURCE_OFFSET = -15;
    }
}
