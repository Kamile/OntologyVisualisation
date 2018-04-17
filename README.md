# Ontology Visualiser
An extension of iCircles and Speedith allowing Concept and Property Diagrams to be visualised from an abstract textual representation.

[![Build Status](https://travis-ci.org/Kamile/OntologyVisualisation.svg?branch=master)](https://travis-ci.org/Kamile/OntologyVisualisation)

## Getting started
* Clone the repository
* Run `mvn install`
* Rebuild the project
* Run main in MainForm

## Abstract Representation Syntax

The root token for any diagram is either ConceptDiagram or PropertyDiagram.

The child tokens of these are `DTs`, `COPs` and `arrows`. These tokens each have a set of mandatory (+) and optional attributes (-):

`COPs` defines a list of class and object property diagrams, each having root token `COP` or `COPInitial` in the case of a property diagram that contains a COP with a single t instance.
* (+) spiders - Where individuals do not belong in any class, this can be a standalone attribute
* (-) habitats - Defines where spiders exist in a class 
* (-) present_zones
* (-) sh_zones  - Shaded/missing zones
* (-) highlighted_zones
* (-) equality - e.g. `("a1", "a2")`
* (-) unknown_equality
* (-) arrows - A list made of another object, `Arrow`.
* (-) id - optional id for disambiguating arrow source and targets

`DTs` defines a list of datatype diagrams, each having a root token `DT`.
* (+) spiders
* (-) habitats
* (-) present_zones
* (-) sh_zones  - Shaded/missing zones
* (-) highlighted_zones
* (-) equality
* (-) unknown_equality

`Arrow` defines binary relationships between combinations of curves, individuals, literals.
* (+) src - string for name of curve/literal/individual for source of arrow.
* (+) target - string for name of curve/literal/individual for target fo arrow.
* (+) property - arrow label
* (-) op - cardinality operator: `leq`, `geq` or `eq`
* (-) arg - cardinality argument
* (-) dashed - `"true"` or `"false"`. If `"false"` can be omitted.
* (-) source_id - id corresponding to some COP containing source/target.
* (-) target_id - id corresponding to some COP containing source/target.


* Anonymous classes (unlabelled curves) can be defined by prefixing an underscore to the name: `_`.
* Regions can be higlighted by adding an additional attribute to either a `DT` or `COP` named `highlighted_zones`. Note that these zones must be defined also in `present_zones`, also using the same syntax.

### Example Concept Diagram (Data Intersection)
```$xslt
ConceptDiagram {
    DTs = [
        DT {
            spiders = [],
            habitats = [],
            sh_zones = [],
            present_zones = [
                (["DR1"],["DR2"]),
                (["DR2"], ["DR1"]),
                (["DR1","DR2"], []),
                ([], ["DR1", "DR2"])],
            highlighted_zones = [
                (["DR1","DR2"], [])]
        }
    ]
}

```

### Example Property Diagram (Property Chain)
```
PropertyDiagram {
    COPs = [
        COP {
            spiders = [],
            habitats = [],
            sh_zones = [],
            present_zones = [([], ["_anon1"]), (["_anon1"],[])]},
        COP {
            spiders = [],
            habitats = [],
            sh_zones = [],
            present_zones = [([], ["_anon2"]), (["_anon2"],[])]},
        COP {
            spiders = [],
            habitats = [],
            sh_zones = [(["_anonB"], ["_anonA"])],
            present_zones = [([], ["_anonA", "_anonB"]), (["_anonA"],["_anonB"]), (["_anonA", "_anonB"],[])]}
    ],
    arrows = [
        Arrow {
            source = "t",
            target = "_anon1",
            property = "op",
            arg = "1"
        },
        Arrow {
            source = "_anon1",
            target = "_anon2",
            property = "op",
            arg = "2"
        },
        Arrow {
            source = "_anon2",
            target = "_anonB",
            property = "op",
            arg = "3"
        },
        Arrow {
            source = "t",
            target = "_anonA",
            property = "op"
        }
    ]
}
```


## Acknowledgements
This project extends on the work on iCircles by Gem Stapleton