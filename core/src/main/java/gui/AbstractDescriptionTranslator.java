package gui;

import abstractDescription.*;
import icircles.abstractDescription.AbstractBasicRegion;
import icircles.abstractDescription.AbstractCurve;
import icircles.abstractDescription.AbstractDescription;
import icircles.concreteDiagram.CircleContour;
import icircles.util.CannotDrawException;
import lang.*;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.Zone;
import speedith.ui.DiagramVisualisation;

import java.util.*;

/* Modified version of DiagramVisualisation from Speedith */
public class AbstractDescriptionTranslator {
    private static Set<AbstractCurve> contours;
    private static Map<String, AbstractBasicRegion> spiderMap;
    private static HashMap<String, AbstractCurve> contourMap;
    private static Set<AbstractCOP> COPs;
    private static Set<AbstractDT> DTs;

    private AbstractDescriptionTranslator() { }

    private static AbstractCOP getAbstractDescription(ClassObjectPropertyDiagram COPDiagram) throws CannotDrawException {
        PrimarySpiderDiagram sd = (PrimarySpiderDiagram) COPDiagram.getSpiderDiagram();
        boolean containsInitialT = COPDiagram.containsInitialT();
        Set<AbstractBasicRegion> highlightedZones = new HashSet<>();
        AbstractDescription ad;
        if (sd.isValid()) {
            ad = DiagramVisualisation.getAbstractDescription(sd);
            spiderMap = new TreeMap<>();
            contours.addAll(ad.getCopyOfContours());
            createContourMap();
            spiderMap.putAll(getSpiderMaps(sd));

            // ----- get highlighted zones from present zones
            for (Zone highlightedZone: COPDiagram.getHighlightedZones()) {
                highlightedZones.add(constructABR(highlightedZone));
            }
        } else {
            ad = null;
        }

        Set<AbstractArrow> abstractArrows = addArrows(COPDiagram.getArrows());
        List<Equality> equalities = COPDiagram.getEqualities();
        Set<AbstractEquality> abstractEqualities = new HashSet<>();

        if (equalities != null) {
            for (Equality equality: equalities) {
                boolean isKnown = equality.isKnown();
                abstractEqualities.add(new AbstractEquality(isKnown,
                        spiderMap.get(equality.getArg1()), spiderMap.get(equality.getArg2()),
                        equality.getArg1(), equality.getArg2()));
            }
        }

        Set<String> dots = new TreeSet<>(COPDiagram.getDots());

        AbstractCOP abstractCOP= new AbstractCOP(ad, highlightedZones, abstractArrows, abstractEqualities, dots, containsInitialT);
        if (COPDiagram.isSingleVariableT) {
            abstractCOP.isSingleVariableT = true;
        }
        if (COPDiagram.id > 0) {
            abstractCOP.id = COPDiagram.id;
        }
        return abstractCOP;
    }

    private static AbstractDT getAbstractDescription(DatatypeDiagram datatypeDiagram) throws CannotDrawException {
        PrimarySpiderDiagram sd = (PrimarySpiderDiagram) datatypeDiagram.getSpiderDiagram();
        Set<AbstractBasicRegion> highlightedZones = new HashSet<>();
        AbstractDescription ad;
        if (sd.isValid()) {
            ad = DiagramVisualisation.getAbstractDescription(sd);
            spiderMap = new TreeMap<>();
            contours.addAll(ad.getCopyOfContours());
            createContourMap();
            spiderMap.putAll(getSpiderMaps(sd));

            // highlighted zone also must be in present zones - get respective ABR
            for (Zone highlightedZone: datatypeDiagram.getHighlightedZones()) {
                highlightedZones.add(constructABR(highlightedZone));
            }
        } else {
            ad = null;
        }

        List<Equality> equalities = datatypeDiagram.getEqualities();
        Set<AbstractEquality> abstractEqualities = new HashSet<>();
        if (equalities != null) {
            for (Equality equality: equalities) {
                boolean isKnown = equality.isKnown();
                abstractEqualities.add(new AbstractEquality(isKnown,
                        spiderMap.get(equality.getArg1()), spiderMap.get(equality.getArg2()),
                        equality.getArg1(), equality.getArg2()));
            }
        }

        Set<String> dots = new TreeSet<>(datatypeDiagram.getDots());
        return new AbstractDT(ad, highlightedZones, dots, abstractEqualities);
    }

    static AbstractPropertyDiagram getAbstractDescription(PropertyDiagram pd) throws CannotDrawException {
        contours = new HashSet<>();
        COPs = new HashSet<>();
        DTs = new HashSet<>();
        spiderMap = new TreeMap<>();
        contourMap = new HashMap<>();

        List<ClassObjectPropertyDiagram> classObjectPropertyDiagrams = pd.getClassObjectPropertyDiagrams();
        if (classObjectPropertyDiagrams!= null){
            for (ClassObjectPropertyDiagram cop : classObjectPropertyDiagrams) {
                COPs.add(getAbstractDescription(cop));
            }
        }

        List<DatatypeDiagram> datatypeDiagrams = pd.getDatatypeDiagrams();
        if (datatypeDiagrams != null) {
            for (DatatypeDiagram dt : datatypeDiagrams) {
                DTs.add(getAbstractDescription(dt));
            }
        }

        Set<AbstractArrow> abstractArrows = addArrows(pd.getArrows());
        return new AbstractPropertyDiagram(COPs, DTs, abstractArrows);
    }

    public static AbstractConceptDiagram getAbstractDescription(ConceptDiagram cd) throws CannotDrawException {
        contours = new HashSet<>();
        COPs = new HashSet<>();
        DTs = new HashSet<>();
        spiderMap = new TreeMap<>();
        contourMap = new HashMap<>();

        List<ClassObjectPropertyDiagram> classObjectPropertyDiagrams = cd.getClassObjectPropertyDiagrams();
        if (classObjectPropertyDiagrams!= null){
            for (ClassObjectPropertyDiagram cop : classObjectPropertyDiagrams) {
                COPs.add(getAbstractDescription(cop));
            }
        }

        List<DatatypeDiagram> datatypeDiagrams = cd.getDatatypeDiagrams();
        if (datatypeDiagrams != null) {
            for (DatatypeDiagram dt : datatypeDiagrams) {
                DTs.add(getAbstractDescription(dt));
            }
        }
        Set<AbstractArrow> abstractArrows = addArrows(cd.getArrows());
        return new AbstractConceptDiagram(COPs, DTs, abstractArrows);
    }

    private static void createContourMap() {
        for (AbstractCurve ac : contours) {
            contourMap.put(ac.getLabel(), ac);
        }
    }

    private static boolean isContour(String label) {
        return contourMap.keySet().contains(label);
    }

    private static HashMap<String, AbstractBasicRegion> getSpiderMaps(PrimarySpiderDiagram sd) {
        HashMap<String, AbstractBasicRegion> regions = new HashMap<>();
        if (sd.getHabitatsCount() > 0) {
            SortedMap<String, Region> habitats = sd.getHabitats();
            for (Map.Entry<String, Region> habitat : habitats.entrySet()) {
                for (Zone foot : habitat.getValue().sortedZones()) {
                    spiderMap.put(habitat.getKey(), constructABR(foot));
                }
            }
        }
        return regions;
    }

    private static AbstractBasicRegion constructABR(Zone zone) {
        TreeSet<AbstractCurve> inContours = new TreeSet<>();
        if (zone.getInContoursCount() > 0) {
            for (String inContour : zone.getInContours()) {
                inContours.add(contourMap.get(inContour));
            }
        }
        return AbstractBasicRegion.get(inContours);
    }

    private static Set<AbstractArrow> addArrows(List<Arrow> arrows) {
        Set<AbstractArrow> abstractArrows = new HashSet<>();
        if (arrows != null) {
            for (Arrow a : arrows) {
                ArrowLabel label = new ArrowLabel(a.getLabel(), a.getCardinalityOperator(), a.getCardinalityArgument());
                boolean isAnon = a.isDashed();
                boolean isSourceContour = isContour(a.getSource());
                boolean isTargetContour = isContour(a.getTarget());
                AbstractArrow arrow;

                if (isSourceContour && isTargetContour) {
                    arrow = new AbstractArrow(label, isAnon, contourMap.get(a.getSource()), contourMap.get(a.getTarget()), a.getSource(), a.getTarget());
                } else if (isSourceContour) {
                    arrow = new AbstractArrow(label, isAnon, contourMap.get(a.getSource()), spiderMap.get(a.getTarget()), a.getSource(), a.getTarget());
                } else if (isTargetContour) {
                    arrow = new AbstractArrow(label, isAnon, spiderMap.get(a.getSource()), contourMap.get(a.getTarget()), a.getSource(), a.getTarget());
                } else {
                    arrow = new AbstractArrow(label, isAnon, spiderMap.get(a.getSource()), spiderMap.get(a.getTarget()), a.getSource(), a.getTarget());
                }

                if (a.getSourceId() > 0) {
                    arrow.setSourceId(a.getSourceId()*-1);
                }

                if (a.getTargetId() > 0) {
                    arrow.setTargetId(a.getTargetId()*-1);
                }
                abstractArrows.add(arrow);
            }
        }
        return abstractArrows;
    }
}
