package gui;

import abstractDescription.AbstractArrow;
import abstractDescription.AbstractConceptDiagramDescription;
import icircles.abstractDescription.AbstractBasicRegion;
import icircles.abstractDescription.AbstractCurve;
import icircles.abstractDescription.AbstractDescription;
import icircles.util.CannotDrawException;
import lang.Arrow;
import lang.BasicConceptDiagram;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.Zone;
import speedith.ui.DiagramVisualisation;

import java.util.*;

public class AbstractDescriptionTranslator {
    private static final int DEFAULT_DIAGRAM_SIZE = 500;
    private static Set<AbstractCurve> contours;
    private static Set<String> spiders;
    private static Map<String, AbstractBasicRegion> spiderMap;
    private static HashMap<String, AbstractCurve> contourMap;

    private AbstractDescriptionTranslator() {
    }

    public static AbstractConceptDiagramDescription getAbstractDescription(BasicConceptDiagram cd) throws CannotDrawException {
        Set<AbstractDescription> abstractSDDescriptions = new HashSet<>();
        Set<AbstractArrow> abstractArrows = new HashSet<>();
        contours = new HashSet<>();
        spiders = new HashSet<>();
        spiderMap = new TreeMap<>();
        contourMap = new HashMap<>();

        List<SpiderDiagram> spiderDiagrams = cd.getSpiderDiagrams();
        for (SpiderDiagram sd : spiderDiagrams) {
            AbstractDescription ad = DiagramVisualisation.getAbstractDescription((PrimarySpiderDiagram) sd);
            abstractSDDescriptions.add(ad);
            contours.addAll(ad.getCopyOfContours());
            createContourMap();
            spiderMap.putAll(getSpiderMaps((PrimarySpiderDiagram) sd));
            spiders.addAll(((PrimarySpiderDiagram) sd).getSpiders());
        }

        List<Arrow> arrows = cd.getArrows();
        if (arrows != null) {
            for (Arrow a : arrows) {
                // TODO: Get whether arrow is dashed or not.
                String label = a.getLabel();
                boolean isSourceContour = isContour(a.getSource());
                boolean isTargetContour = isContour(a.getTarget());

                if (isSourceContour && isTargetContour) {
                    abstractArrows.add(new AbstractArrow(label, false, contourMap.get(a.getSource()), contourMap.get(a.getTarget())));
                } else if (isSourceContour) {
                    abstractArrows.add(new AbstractArrow(label, false, contourMap.get(a.getSource()), spiderMap.get(a.getTarget())));
                } else if (isTargetContour) {
                    abstractArrows.add(new AbstractArrow(label, false, spiderMap.get(a.getSource()), contourMap.get(a.getTarget())));
                } else {
                    abstractArrows.add(new AbstractArrow(label, false, spiderMap.get(a.getSource()), spiderMap.get(a.getTarget())));
                }
            }

        }
        return new AbstractConceptDiagramDescription(abstractSDDescriptions, abstractArrows);
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
