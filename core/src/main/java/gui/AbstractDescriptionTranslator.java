package gui;

import abstractDescription.AbstractConceptDiagramDescription;
import icircles.abstractDescription.AbstractCurve;
import icircles.abstractDescription.AbstractDescription;
import icircles.util.CannotDrawException;
import lang.Arrow;
import lang.BasicConceptDiagram;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.ui.DiagramVisualisation;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class AbstractDescriptionTranslator {
    private static final int DEFAULT_DIAGRAM_SIZE = 500;

    private AbstractDescriptionTranslator() {
    }

    public static AbstractConceptDiagramDescription getAbstractDescription(BasicConceptDiagram cd) throws CannotDrawException {
        Set<AbstractDescription> abstractSDDescriptions = new TreeSet<>();
        Set<AbstractCurve> contours = new TreeSet<>();

        AbstractCurve c = new AbstractCurve();

        List<SpiderDiagram> spiderDiagrams = cd.getSpiderDiagrams();
        for (SpiderDiagram sd: spiderDiagrams) {
            abstractSDDescriptions.add(DiagramVisualisation.getAbstractDescription((PrimarySpiderDiagram) sd));
            contours.addAll(DiagramVisualisation.getAbstractDescription((PrimarySpiderDiagram) sd).getCopyOfContours());
        }

        List<Arrow> arrows = cd.getArrows();
        if (arrows != null) {

        } else {
            return new AbstractConceptDiagramDescription(abstractSDDescriptions, null);
        }
        return null;
    }
}
