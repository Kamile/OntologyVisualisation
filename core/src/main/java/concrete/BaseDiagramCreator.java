package concrete;

import abstractDescription.*;
import icircles.abstractDescription.AbstractBasicRegion;
import icircles.abstractDescription.AbstractCurve;
import icircles.abstractDescription.AbstractDescription;
import icircles.concreteDiagram.CircleContour;
import icircles.concreteDiagram.ConcreteZone;
import icircles.concreteDiagram.DiagramCreator;
import icircles.util.CannotDrawException;

import java.awt.geom.Ellipse2D;
import java.util.*;

/***
 * Layout algorithms for well-formedness of concept and property diagrams.
 *
 * Here we re-write the DiagramCreator from iCircles to remove split curves and apply
 * all of the layout constraints as described in the success criteria.
 *
 * First use DiagramCreator to draw each spider diagram separately, space them out and draw arrows
 */
class BaseDiagramCreator {
    private Set<AbstractCOP> abstractCOPs;
    private Set<AbstractDT> abstractDTs;
    private Set<AbstractArrow> abstractArrows;

    BaseDiagramCreator(AbstractDiagram abstractDescription) {
        abstractCOPs = abstractDescription.getCOPs();
        abstractDTs = abstractDescription.getDTs();
        abstractArrows = abstractDescription.getArrowDescriptions();
    }

    ConcreteBaseDiagram createDiagram(int size) throws CannotDrawException {
        Set<ConcreteCOP> COPs = new HashSet<>();
        Set<ConcreteDT> DTs = new HashSet<>();
        Set<ConcreteArrow> concreteArrows = new HashSet<>();
        int id = 1;

        for (AbstractCOP abstractCOP: abstractCOPs) {
            AbstractDescription ad = abstractCOP.getPrimarySDDescription();
            Set<ConcreteArrow> concreteCOPArrows = new HashSet<>();
            Set<ConcreteEquality> concreteEqualities = new HashSet<>();
            Set<ConcreteZone> highlightedZones = new HashSet<>();


            for (AbstractArrow abstractArrow: abstractCOP.getArrows()) {
                ConcreteArrow concreteArrow = new ConcreteArrow(abstractArrow, id);
                concreteCOPArrows.add(concreteArrow);
            }

            for (AbstractEquality abstractEquality: abstractCOP.getEqualities()) {
                ConcreteEquality concreteEquality = new ConcreteEquality(abstractEquality, id);
                concreteEqualities.add(concreteEquality);
            }

            ConcreteCOP concreteCOP;
            if (ad!=null) {
                DiagramCreator dc = new DiagramCreator(ad);
                icircles.concreteDiagram.ConcreteDiagram cd = dc.createDiagram(size);

                ArrayList<CircleContour> circles = cd.getCircles();
                HashMap<AbstractCurve, CircleContour> map = getMap(circles);

                for (AbstractBasicRegion abr: abstractCOP.getHighlightedZones()) {
                    ConcreteZone highlightedZone =  makeConcreteZone(abr, circles, map);
                    highlightedZones.add(highlightedZone);
                }

                concreteCOP = new ConcreteCOP(id, cd, highlightedZones, concreteCOPArrows, concreteEqualities);
            } else { // no contours, just dots
                concreteCOP = new ConcreteCOP(id, concreteCOPArrows, abstractCOP.getDots(), concreteEqualities);
            }

            if (abstractCOP.id > 0) {
                concreteCOP.setId(abstractCOP.id*-1); // id was set in syntax
            }

            if (abstractCOP.containsInitialT()) {
                concreteCOP.containsInitialT = true;
            }

            if (abstractCOP.isSingleVariableT) {
                concreteCOP.isSingleVariableTInstance = true;
                concreteCOP.setId(0);
            }

            COPs.add(concreteCOP);
            id++;
        }

        for (AbstractDT abstractDT: abstractDTs) {
            AbstractDescription ad = abstractDT.getPrimarySDDescription();
            Set<ConcreteZone> highlightedZones = new HashSet<>();



            ConcreteDT ConcreteDT;
            if (ad!=null) {
                DiagramCreator dc = new DiagramCreator(ad);
                icircles.concreteDiagram.ConcreteDiagram cd = dc.createDiagram(size);
                ArrayList<CircleContour> circles = cd.getCircles();
                HashMap<AbstractCurve, CircleContour> map = getMap(circles);

                for (AbstractBasicRegion abr: abstractDT.getHighlightedZones()) {
                    ConcreteZone highlightedZone =  makeConcreteZone(abr, circles, map);
                    highlightedZones.add(highlightedZone);
                }
                ConcreteDT = new ConcreteDT(id, cd, highlightedZones);

            } else { // no contours, just dots
                ConcreteDT = new ConcreteDT(id, abstractDT.getDots());
            }
            DTs.add(ConcreteDT);
            id++;
        }

        for (AbstractArrow abstractArrow: abstractArrows) {
            ConcreteArrow concreteArrow = new ConcreteArrow(abstractArrow, 0); //outermost arrow set
            concreteArrows.add(concreteArrow);
        }
        return new ConcreteBaseDiagram(COPs, DTs, concreteArrows);
    }

    private ConcreteZone makeConcreteZone(AbstractBasicRegion z, ArrayList<CircleContour> circles, HashMap<AbstractCurve, CircleContour> map) {
        ArrayList<CircleContour> includingCircles = new ArrayList<CircleContour>();
        ArrayList<CircleContour> excludingCircles = new ArrayList<CircleContour>(circles);
        Iterator<AbstractCurve> acIt = z.getContourIterator();
        while (acIt.hasNext()) {
            AbstractCurve ac = acIt.next();
            CircleContour containingCC = map.get(ac);
            excludingCircles.remove(containingCC);
            includingCircles.add(containingCC);
        }
        ConcreteZone cz = new ConcreteZone(z, includingCircles, excludingCircles);
        return cz;
    }

    private HashMap<AbstractCurve, CircleContour> getMap(ArrayList<CircleContour> circles) {
        HashMap<AbstractCurve, CircleContour> map = new HashMap<>();
        for (CircleContour circle: circles) {
            map.put(circle.ac, circle);
        }
        return map;
    }
}
