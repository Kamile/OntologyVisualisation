package gui;

import concrete.ConcreteSubDiagram;
import icircles.concreteDiagram.*;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.StringWriter;
import java.util.*;
import java.util.List;

public class COPDiagramsDrawer extends JPanel {
    private static final BasicStroke DEFAULT_CONTOUR_STROKE = new BasicStroke(2.0F);
    private static final BasicStroke HIGHLIGHT_STROKE = new BasicStroke(3.5F);
    private static final Color HIGHLIGHT_LEG_COLOUR;
    private static final Color HIGHLIGHTED_FOOT_COLOUR;
    private static final Color HIGHLIGHT_STROKE_COLOUR;
    private static final Color HIGHLIGHT_ZONE_COLOUR;
    private static final int DOT_OFFSET; // position for dot source
    private static final int LABEL_OFFSET; // position to draw string
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
    private HashMap<Integer, HashMap<String, Ellipse2D.Double>> circleMap2;

    COPDiagramsDrawer(ConcreteSubDiagram diagram, HashMap<String, Ellipse2D.Double> circleMap) {
        this.domImpl = GenericDOMImplementation.getDOMImplementation();
        this.svgNS = "http://www.w3.org/2000/svg";
        this.document = this.domImpl.createDocument(this.svgNS, "svg", (DocumentType)null);
        this.svgGenerator = new SVGGraphics2D(this.document);
        this.scaleFactor = 1.0D;
        this.trans = new AffineTransform();
        this.highlightedContour = null;
        this.highlightedZone = null;
        this.highlightedFoot = null;
        this.initComponents();
        this.resetDiagram(diagram);
        this.resizeContents();
        this.circleMap = circleMap;
    }

    COPDiagramsDrawer(ConcreteSubDiagram diagram) {
        this.domImpl = GenericDOMImplementation.getDOMImplementation();
        this.svgNS = "http://www.w3.org/2000/svg";
        this.document = this.domImpl.createDocument(this.svgNS, "svg", (DocumentType)null);
        this.svgGenerator = new SVGGraphics2D(this.document);
        this.scaleFactor = 1.0D;
        this.trans = new AffineTransform();
        this.highlightedContour = null;
        this.highlightedZone = null;
        this.highlightedFoot = null;
        this.initComponents();
        this.resetDiagram(diagram);
        this.resizeContents();
        this.circleMap = new HashMap<>();
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

            ArrayList<ConcreteZone> shadedZones = this.diagram.getShadedZones();
            if (shadedZones != null) {
                Iterator var3 = this.diagram.getShadedZones().iterator();

                while(var3.hasNext()) {
                    ConcreteZone z = (ConcreteZone)var3.next();
                    if (z.getColor() != null) {
                        g.setColor(z.getColor());
                    } else {
                        g.setColor(Color.lightGray);
                    }
                    Area a = z.getShape(this.diagram.getBox());
                    g2d.fill(a.createTransformedArea(this.trans));
                }
            }

            if (this.getHighlightedZone() != null) {
                Color oldColour = g2d.getColor();
                g2d.setColor(HIGHLIGHT_ZONE_COLOUR);
                g2d.fill(this.getHighlightedZone().getShape(this.diagram.getBox()).createTransformedArea(this.trans));
                g2d.setColor(oldColour);
            }

            g2d.setStroke(DEFAULT_CONTOUR_STROKE);
            ArrayList<CircleContour> circles = this.diagram.getCircles();
            Ellipse2D.Double tmpCircle = new Ellipse2D.Double();
            if (circles != null) {
                Iterator var16 = circles.iterator();

                while (var16.hasNext()) {
                    CircleContour cc = (CircleContour) var16.next();
                    Color col = cc.color();
                    if (col == null) {
                        col = Color.black;
                    }

                    g.setColor(col);
                    transformCircle(this.scaleFactor, cc.getCircle(), tmpCircle);
                    circleMap.put(cc.ac.getLabel(), new Ellipse2D.Double(tmpCircle.getCenterX() + getX() + getCenteringTranslationX(), tmpCircle.getCenterY() + getY() + getCenteringTranslationY(), tmpCircle.getHeight(), tmpCircle.getWidth()));
                    g2d.draw(tmpCircle);

                    if (cc.ac.getLabel() != null) {
                        g.setColor(col);
                        if (cc.stroke() != null) {
                            g2d.setStroke(cc.stroke());
                        } else {
                            g2d.setStroke(DEFAULT_CONTOUR_STROKE);
                        }

                        Font f = this.diagram.getFont();
                        if (f != null) {
                            g2d.setFont(f);
                        }
                        if (!cc.ac.getLabel().startsWith("_")) {
                            g2d.drawString(cc.ac.getLabel(), (int) tmpCircle.getCenterX(), (int) (tmpCircle.getCenterY() - tmpCircle.getHeight()/2 - 8));
                        }
                    }
                }
            }

            ConcreteSpider highlightedSpider = this.getHighlightedFoot() == null ? null : this.getHighlightedFoot().getSpider();
            g.setColor(Color.black);

            ArrayList<ConcreteSpider> spiders = this.diagram.getSpiders();
            if (spiders != null) {
                Iterator var18 = this.diagram.getSpiders().iterator();

                while (var18.hasNext()) {
                    ConcreteSpider s = (ConcreteSpider) var18.next();
                    Color oldColor = null;
                    Stroke oldStroke = null;
                    if (highlightedSpider == s) {
                        oldColor = g2d.getColor();
                        g2d.setColor(HIGHLIGHT_LEG_COLOUR);
                        oldStroke = g2d.getStroke();
                        g2d.setStroke(HIGHLIGHT_STROKE);
                    }

                    Iterator var10 = s.legs.iterator();

                    while (var10.hasNext()) {
                        ConcreteSpiderLeg leg = (ConcreteSpiderLeg) var10.next();
                        g2d.drawLine((int) (leg.from.getX() * this.scaleFactor), (int) (leg.from.getY() * this.scaleFactor), (int) (leg.to.getX() * this.scaleFactor), (int) (leg.to.getY() * this.scaleFactor));
                    }

                    var10 = s.feet.iterator();

                    while (var10.hasNext()) {
                        ConcreteSpiderFoot foot = (ConcreteSpiderFoot) var10.next();
                        foot.getBlob(tmpCircle);
                        Color oldColor2 = g2d.getColor();
                        translateCircleCentre(this.scaleFactor, tmpCircle, tmpCircle);
                        if (this.getHighlightedFoot() == foot) {
                            oldColor2 = g2d.getColor();
                            g2d.setColor(HIGHLIGHTED_FOOT_COLOUR);
                            scaleCircleCentrally(tmpCircle, 1.4D);
                        }

                        if (this.diagram.containsInitialT && s.as.getName().equals("t")) {
                            circleMap.put(foot.getSpider().as.getName(), new Ellipse2D.Double(tmpCircle.getCenterX() + getX() + getCenteringTranslationX(), tmpCircle.getCenterY() + getY() + getCenteringTranslationY() + LABEL_SOURCE_OFFSET, tmpCircle.getHeight(), tmpCircle.getWidth()));
                        } else {
                            g2d.fill(tmpCircle);
                            circleMap.put(foot.getSpider().as.getName(), new Ellipse2D.Double(tmpCircle.getCenterX() + getX() + getCenteringTranslationX(), tmpCircle.getCenterY() + getY() + getCenteringTranslationY(), tmpCircle.getHeight(), tmpCircle.getWidth()));
                        }

                        if (this.getHighlightedFoot() == foot) {
                            g2d.setColor(oldColor2);
                        }
                    }

                    if (s.as.getName() != null) {
                        if (!s.as.getName().startsWith("_")) {
                            g2d.drawString(s.as.getName(), (int) (((ConcreteSpiderFoot) s.feet.get(0)).getX() * this.trans.getScaleX()) - 5, (int) (((ConcreteSpiderFoot) s.feet.get(0)).getY() * this.trans.getScaleY()) - 10);
                        }
                        if (highlightedSpider == s) {
                            g2d.setColor(oldColor);
                            g2d.setStroke(oldStroke);
                        }
                    }
                }
            }

            if (this.getHighlightedContour() != null) {
                g2d.setColor(HIGHLIGHT_STROKE_COLOUR);
                g2d.setStroke(HIGHLIGHT_STROKE);
                transformCircle(this.scaleFactor, this.getHighlightedContour().getCircle(), tmpCircle);
                g2d.draw(tmpCircle);
            }

            if (this.diagram.dots != null && this.diagram.dots.size() > 0) {
                List<String> dots = new ArrayList<>(this.diagram.dots);
                int numDots = dots.size();
                int currentXPos = this.getWidth()/2 - (19*(numDots-1)) - getCenteringTranslationX();
                double y = getHeight()/2 - getCenteringTranslationY();
                for (String dot : dots) {
                    Ellipse2D.Double dotCircle = new Ellipse2D.Double(currentXPos, y, 8, 8);
                    if (this.diagram.containsInitialT && dot.equals("t")) {
                        circleMap.put(dot, new Ellipse2D.Double(currentXPos + getX() + getCenteringTranslationX(), y + getY() + getCenteringTranslationY() + LABEL_SOURCE_OFFSET, 8, 8));
                    } else {
                        circleMap.put(dot, new Ellipse2D.Double(currentXPos + getX() + getCenteringTranslationX(), y + getY() + getCenteringTranslationY(), 8, 8));
                        g2d.fill(dotCircle);
                    }
                    g2d.drawString(dot, currentXPos, (int) y - 10);
                    currentXPos += 40;
                }
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

    private void initComponents() {
        this.setBackground(new Color(255, 255, 255));
        this.setLayout(null);
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
            if (size > 0) {
                this.setScaleFactor((double)Math.min((float)this.getWidth() / (float)size, (float)(this.getHeight()-50) / (float)size));
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
        return (this.getWidth() - (int)Math.round((double)this.diagram.getSize() * this.scaleFactor)) / 2;
    }

    private int getCenteringTranslationY() {
        return (this.getHeight() - (int)Math.round((double)this.diagram.getSize() * this.scaleFactor)) / 2;
    }

    protected Graphics getComponentGraphics(Graphics g) {
        return this.svgGenerator;
    }

    static {
        HIGHLIGHT_LEG_COLOUR = Color.BLUE;
        HIGHLIGHTED_FOOT_COLOUR = Color.RED;
        HIGHLIGHT_STROKE_COLOUR = Color.RED;
        HIGHLIGHT_ZONE_COLOUR = new Color(1895759872, true);
        DOT_OFFSET = 0;
        LABEL_OFFSET = 0;
        LABEL_SOURCE_OFFSET = -15;
    }
}
