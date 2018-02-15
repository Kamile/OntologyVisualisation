package gui;

import abstractDescription.AbstractArrow;
import abstractDescription.AbstractCOPDescription;
import abstractDescription.AbstractConceptDiagramDescription;
import icircles.abstractDescription.AbstractBasicRegion;
import icircles.abstractDescription.AbstractCurve;
import icircles.abstractDescription.AbstractDescription;
import icircles.util.CannotDrawException;
import lang.Arrow;
import lang.ConceptDiagram;
import lang.ClassObjectPropertyDiagram;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.Zone;
import speedith.ui.DiagramVisualisation;

import java.util.*;

/* Modified version of DiagramVisualisation from Speedith */
public class AbstractDescriptionTranslator {
    private static Set<AbstractCurve> contours;
    private static Set<String> spiders;
    private static Map<String, AbstractBasicRegion> spiderMap;
    private static HashMap<String, AbstractCurve> contourMap;
    private static HashMap<ClassObjectPropertyDiagram, AbstractCOPDescription> COPDescriptionMap;

    private AbstractDescriptionTranslator() {
    }

    static AbstractCOPDescription getAbstractDescription(ClassObjectPropertyDiagram COPDiagram) throws CannotDrawException {
        PrimarySpiderDiagram sd = (PrimarySpiderDiagram) COPDiagram.getSpiderDiagram();

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

        Set<AbstractArrow> abstractArrows = new HashSet<>();
        List<Arrow> arrows = COPDiagram.getArrows();
        if (arrows != null) {
            for (Arrow a : arrows) {
                String label = a.getLabel();
                boolean isAnon = a.isDashed();
                boolean isSourceContour = isContour(a.getSource());
                boolean isTargetContour = isContour(a.getTarget());

                if (isSourceContour && isTargetContour) {
                    abstractArrows.add(new AbstractArrow(label, isAnon, contourMap.get(a.getSource()), contourMap.get(a.getTarget())));
                } else if (isSourceContour) {
                    abstractArrows.add(new AbstractArrow(label, isAnon, contourMap.get(a.getSource()), spiderMap.get(a.getTarget())));
                } else if (isTargetContour) {
                    abstractArrows.add(new AbstractArrow(label, isAnon, spiderMap.get(a.getSource()), contourMap.get(a.getTarget())));
                } else {
                    abstractArrows.add(new AbstractArrow(label, isAnon, spiderMap.get(a.getSource()), spiderMap.get(a.getTarget())));
                }
            }
        }

        List<String> dots = COPDiagram.getDots();
        if (dots != null) {
            return new AbstractCOPDescription(ad, abstractArrows, new TreeSet<>(dots));
        }
        return new AbstractCOPDescription(ad, abstractArrows);
    }

    public static AbstractConceptDiagramDescription getAbstractDescription(ConceptDiagram cd) throws CannotDrawException {
        Set<AbstractArrow> abstractArrows = new HashSet<>();
        contours = new HashSet<>();
        COPDescriptionMap = new HashMap<>();
        spiders = new HashSet<>();
        spiderMap = new TreeMap<>();
        contourMap = new HashMap<>();

        List<ClassObjectPropertyDiagram> classObjectPropertyDiagrams = cd.getClassObjectPropertyDiagrams();
        for (ClassObjectPropertyDiagram cop : classObjectPropertyDiagrams) {
            COPDescriptionMap.put(cop, getAbstractDescription(cop));
        }

        List<Arrow> arrows = cd.getArrows(); // need contour map from
        if (arrows != null) {
            for (Arrow a : arrows) {
                String label = a.getLabel();
                boolean isAnon = a.isDashed();
                boolean isSourceContour = isContour(a.getSource());
                boolean isTargetContour = isContour(a.getTarget());

                if (isSourceContour && isTargetContour) {
                    abstractArrows.add(new AbstractArrow(label, isAnon, contourMap.get(a.getSource()), contourMap.get(a.getTarget())));
                } else if (isSourceContour) {
                    abstractArrows.add(new AbstractArrow(label, isAnon, contourMap.get(a.getSource()), spiderMap.get(a.getTarget())));
                } else if (isTargetContour) {
                    abstractArrows.add(new AbstractArrow(label, isAnon, spiderMap.get(a.getSource()), contourMap.get(a.getTarget())));
                } else {
                    abstractArrows.add(new AbstractArrow(label, isAnon, spiderMap.get(a.getSource()), spiderMap.get(a.getTarget())));
                }
            }
        }
        return new AbstractConceptDiagramDescription(COPDescriptionMap, abstractArrows);
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
}
