grammar ConceptDiagrams;

options {
    output=AST;
    ASTLabelType=CommonTree;
}

/* Tokens used as tags for head nodes in generated AST tree */
tokens {
    DICT            =           '{';
    PAIR            =           '=';
    LIST            =           '[';
    SLIST           =           '(';
    CD              =           'ConceptDiagram';
    COP_PRIMARY     =           'COP';
    COP_EMPTY        =          'EmptyCOP';
}

@parser::header {
package reader;
import static speedith.core.i18n.Translations.i18n;
import speedith.core.lang.reader.ParseException;
}

@lexer::header {
package reader;
import static speedith.core.i18n.Translations.i18n;
import speedith.core.lang.reader.ParseException;
}

@rulecatch {
catch (RecognitionException e) {
    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
}
}

@lexer::rulecatch {
catch (RecognitionException e) {
    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
}
}

@members {
@Override
public void reportError(RecognitionException e) {
    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
}
}

@lexer::members {
@Override
public void reportError(RecognitionException e) {
    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
}
}

/*******************************************************************************
                                  Parser section
*******************************************************************************/


/* Entry. */
start
    :    conceptDiagram
    ;

conceptDiagram
    :    'ConceptDiagram'^ '{'! (keyValue (','! keyValue)*)? '}'!
    ;

classObjectPropertyDiagram
    :    'COP'^ '{'! (keyValue (','! keyValue)*)? '}'!
    |    'EmptyCOP'^ '{'! (keyValue (','! keyValue)*)? '}'!
    ;

keyValues
    :    '{'^ (keyValue (','! keyValue)*)? '}'!
    ;

list
    :    '['^ (languageElement (','! languageElement)*)? ']'!
    ;

sortedList
    :    '('^ (languageElement (','! languageElement)*)? ')'!
    ;

keyValue
    :    ID '='^ languageElement
    ;

languageElement
    :    STRING
    |    keyValues
    |    list
    |    sortedList
    |    classObjectPropertyDiagram
    |    conceptDiagram
    ;



/*******************************************************************************
                                  Lexer section
*******************************************************************************/

COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    |   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) {$channel=HIDDEN;}
    ;

STRING
    :    '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
    ;

fragment
HEX_DIGIT
    :    ('0'..'9'|'a'..'f'|'A'..'F')
    ;

fragment
ESC_SEQ
    :    '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |    UNICODE_ESC
    |    OCTAL_ESC
    ;

fragment
OCTAL_ESC
    :    '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |    '\\' ('0'..'7') ('0'..'7')
    |    '\\' ('0'..'7')
    ;

fragment
UNICODE_ESC
    :    '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;

ID
    :    IdentifierStart IdentifierPart*
    ;

fragment
IdentifierStart
    :    '\u0024'
    |    '\u0041'..'\u005a'
    |    '\u005f'
    |    '\u0061'..'\u007a'
    |    '\u00a2'..'\u00a5'
    |    '\u00aa'
    |    '\u00b5'
    |    '\u00ba'
    |    '\u00c0'..'\u00d6'
    |    '\u00d8'..'\udfff'
    ;

fragment
IdentifierPart
    :    '\u0000'..'\u0008'
    |    '\u000e'..'\u001b'
    |    '\u0024'
    |    '\u0030'..'\u0039'
    |    '\u0041'..'\u005a'
    |    '\u005f'
    |    '\u0061'..'\u007a'
    |    '\u007f'..'\u009f'
    |    '\u00a2'..'\u00a5'
    |    '\u00aa'
    |    '\u00ad'
    |    '\u00b5'
    |    '\u00ba'
    |    '\u00c0'..'\u00d6'
    |    '\u00d8'..'\udfff'
;

