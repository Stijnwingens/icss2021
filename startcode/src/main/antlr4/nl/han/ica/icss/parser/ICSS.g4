grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';


//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;


//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';




//--- PARSER: ---
stylesheet: (stylerule | variableAssignment)* EOF;
stylerule: selector body;

variableAssignment: variableReference ASSIGNMENT_OPERATOR expression SEMICOLON;
declaration: propertyName COLON expression SEMICOLON;

ifClause: IF BOX_BRACKET_OPEN conditionalExpression BOX_BRACKET_CLOSE body elseClause?;
elseClause: ELSE body;

body: OPEN_BRACE (declaration | ifClause | variableAssignment)+ CLOSE_BRACE;

literal:    COLOR #colorLiteral|
            PIXELSIZE #pixelLiteral|
            PERCENTAGE #percentageLiteral|
            SCALAR #scalarLiteral|
            bool #boolLiteral;

expression: literal #literalValue|
            variableReference #variableValue|
            expression MUL expression #multiplication|
            expression (PLUS | MIN) expression #subAdd;

bool: TRUE | FALSE;
conditionalExpression: bool | variableReference;

variableReference: CAPITAL_IDENT;
propertyName: LOWER_IDENT;

selector:    ID_IDENT #idSelector|
             CLASS_IDENT #classSelector|
             LOWER_IDENT #tagSelector;
