package gui;

import abstractDescription.*;
import icircles.abstractDescription.AbstractBasicRegion;
import icircles.abstractDescription.AbstractCurve;
import icircles.abstractDescription.AbstractDescription;
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
    private static Set<String> spiders;
    private static Map<String, AbstractBasicRegion> spiderMap;
    private static HashMap<String, AbstractCurve> contourMap;
    private static HashMap<ClassObjectPropertyDiagram, AbstractCOP> COPDescriptionMap;
    private static HashMap<DatatypeDiagram, AbstractDatatypeDiagram> DTDescriptionMap;

    private AbstractDescriptionTranslator() { }

    static AbstractCOP getAbstractDescription(ClassObjectPropertyDiagram COPDiagram) throws CannotDrawException {
        PrimarySpiderDiagram sd = (PrimarySpiderDiagram) COPDiagram.getSpiderDiagram();
        boolean containsInitialT = COPDiagram.containsInitialT();

        AbstractDescription ad;
        if (sd.isValid()) {
            ad = DiagramVisualisation.getAbstractDescription(sd);
            contours = new HashSet<>();
            spiders = new HashSet<>();
            spiderMap = new TreeMap<>();
            contourMap = new HashMap<>();
            contours.addAll(ad.getCopyOfContours());
            createContourMap();
            spiderMap.putAll(getSpiderMaps(sd));
            spiders.addAll((sd).getSpiders());
        } else {
            ad = null;
        }

        Set<AbstractArrow> abstractArrows = addArrows(COPDiagram.getArrows());
        List<Equality> equalities = COPDiagram.getEqualities();
        Set<AbstractEquality> abstractEqualities = new HashSet();

        if (equalities != null) {
            for (Equality equality: equalities) {
                boolean isKnown = equality.isKnown();
                abstractEqualities.add(new AbstractEquality(isKnown,
                        spiderMap.get(equality.getArg1()), spiderMap.get(equality.getArg2()),
                        equality.getArg1(), equality.getArg2()));
            }
        }

        Set<String> dots = new TreeSet<>(COPDiagram.getDots());

        return new AbstractCOP(ad, abstractArrows, abstractEqualities, dots, containsInitialT);
    }

    static AbstractDatatypeDiagram getAbstractDescription(DatatypeDiagram datatypeDiagram) throws CannotDrawException {
        PrimarySpiderDiagram sd = (PrimarySpiderDiagram) datatypeDiagram.getSpiderDiagram();

        AbstractDescription ad;
        if (sd.isValid()) {
            ad = DiagramVisualisation.getAbstractDescription(sd);
            contours = new HashSet<>();
            spiders = new HashSet<>();
            spiderMap = new TreeMap<>();
            contourMap = new HashMap<>();
            contours.addAll(ad.getCopyOfContours());
            createContourMap();
            spiderMap.putAll(getSpiderMaps(sd));
            spiders.addAll((sd).getSpiders());
        } else {
            ad = null;
        }
        Set<String> dots = new TreeSet<>(datatypeDiagram.getDots());
        return new AbstractDatatypeDiagram(ad, dots);
    }

    public static AbstractPropertyDiagram getAbstractDescription(PropertyDiagram pd) throws CannotDrawException {
        contours = new HashSet<>();
        COPDescriptionMap = new HashMap<>();
        DTDescriptionMap = new HashMap<>();
        spiders = new HashSet<>();
        spiderMap = new TreeMap<>();
        contourMap = new HashMap<>();

        List<ClassObjectPropertyDiagram> classObjectPropertyDiagrams = pd.getClassObjectPropertyDiagrams();
        if (classObjectPropertyDiagrams!= null){
            for (ClassObjectPropertyDiagram cop : classObjectPropertyDiagrams) {
                COPDescriptionMap.put(cop, getAbstractDescription(cop));
            }
        }

        List<DatatypeDiagram> datatypeDiagrams = pd.getDatatypeDiagrams();
        if (datatypeDiagrams != null) {
            for (DatatypeDiagram dt : datatypeDiagrams) {
                DTDescriptionMap.put(dt, getAbstractDescription(dt));
            }
        }

        Set<AbstractArrow> abstractArrows = addArrows(pd.getArrows());
        return new AbstractPropertyDiagram(COPDescriptionMap, DTDescriptionMap, abstractArrows);
    }

    public static AbstractConceptDiagram getAbstractDescription(ConceptDiagram cd) throws CannotDrawException {
        contours = new HashSet<>();
        COPDescriptionMap = new HashMap<>();
        DTDescriptionMap = new HashMap<>();
        spiders = new HashSet<>();
        spiderMap = new TreeMap<>();
        contourMap = new HashMap<>();

        List<ClassObjectPropertyDiagram> classObjectPropertyDiagrams = cd.getClassObjectPropertyDiagrams();
        if (classObjectPropertyDiagrams!= null){
            for (ClassObjectPropertyDiagram cop : classObjectPropertyDiagrams) {
                COPDescriptionMap.put(cop, getAbstractDescription(cop));
            }
        }

        List<DatatypeDiagram> datatypeDiagrams = cd.getDatatypeDiagrams();
        if (datatypeDiagrams != null) {
            for (DatatypeDiagram dt : datatypeDiagrams) {
                DTDescriptionMap.put(dt, getAbstractDescription(dt));
            }
        }
        Set<AbstractArrow> abstractArrows = addArrows(cd.getArrows());
        return new AbstractConceptDiagram(COPDescriptionMap, DTDescriptionMap, abstractArrows);
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
                    spiderMap.put(habitat.getKey(), constructABR(foot, contourMap));
                }
            }
        }
        return regions;
    }

    private static AbstractBasicRegion constructABR(Zone foot, HashMap<String, AbstractCurve> contourMap) {
        TreeSet<AbstractCurve> inContours = new TreeSet<>();
        if (foot.getInContoursCount() > 0) {
            for (String inContour : foot.getInContours()) {
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

                if (isSourceContour && isTargetContour) {
                    abstractArrows.add(new AbstractArrow(label, isAnon, contourMap.get(a.getSource()), contourMap.get(a.getTarget()), a.getSource(), a.getTarget()));
                } else if (isSourceContour) {
                    abstractArrows.add(new AbstractArrow(label, isAnon, contourMap.get(a.getSource()), spiderMap.get(a.getTarget()), a.getSource(), a.getTarget()));
                } else if (isTargetContour) {
                    abstractArrows.add(new AbstractArrow(label, isAnon, spiderMap.get(a.getSource()), contourMap.get(a.getTarget()), a.getSource(), a.getTarget()));
                } else {
                    abstractArrows.add(new AbstractArrow(label, isAnon, spiderMap.get(a.getSource()), spiderMap.get(a.getTarget()), a.getSource(), a.getTarget()));
                }
            }
        }
        return abstractArrows;
    }
}
