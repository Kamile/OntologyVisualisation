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
import speedith.ui.DiagramVisualisation;

import java.util.*;

public class AbstractDescriptionTranslator {
    private static final int DEFAULT_DIAGRAM_SIZE = 500;
    private static Set<AbstractCurve> contours;
    private static Set<String> spiders;
    private static Set<String> spiderMaps;
    private static Map<String, Region> spiderHabitats;

    private AbstractDescriptionTranslator() {
    }

    public static AbstractConceptDiagramDescription getAbstractDescription(BasicConceptDiagram cd) throws CannotDrawException {
        Set<AbstractDescription> abstractSDDescriptions = new TreeSet<>();
        contours = new TreeSet<>();
        spiders = new TreeSet<>();
        spiderMaps = new TreeSet<>();
        spiderHabitats = new TreeMap<>();

        AbstractCurve c = new AbstractCurve();

        List<SpiderDiagram> spiderDiagrams = cd.getSpiderDiagrams();
        for (SpiderDiagram sd: spiderDiagrams) {
            AbstractDescription ad = DiagramVisualisation.getAbstractDescription((PrimarySpiderDiagram) sd);
            abstractSDDescriptions.add(ad);
            contours.addAll(ad.getCopyOfContours());
            spiderHabitats.putAll(getSpiderMaps((PrimarySpiderDiagram) sd));
            spiders.addAll(((PrimarySpiderDiagram) sd).getSpiders());
        }

        List<Arrow> arrows = cd.getArrows();
        if (arrows != null) {
            for (Arrow a: arrows) {
                boolean isSourceContour = isContour(a.getSource());
                boolean isTargetContour = isContour(a.getTarget());
//                public AbstractArrow(String label, boolean isAnon, AbstractBasicRegion source, AbstractBasicRegion target)
            }

        } else {
            return new AbstractConceptDiagramDescription(abstractSDDescriptions, null);
        }
        return null;
    }

    private static List<String> getContourLabels() {
        List<String> contourLabels = new ArrayList<>();
        for(AbstractCurve ac: contours) {
            contourLabels.add(ac.getLabel());
        }
        return contourLabels;
    }

    private static boolean isContour(String label) {
        if (getContourLabels().contains(label)){
            return true;
        }
        return false;
    }

    private static HashMap<String, Region> getSpiderMaps(PrimarySpiderDiagram sd) {
        HashMap<String, Region> regions = new HashMap<>();
        for (String spider: sd.getSpiders()) {
            regions.put(spider, sd.getSpiderHabitat(spider));
        }
        return regions;
    }

}
