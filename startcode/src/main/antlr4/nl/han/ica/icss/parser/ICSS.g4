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
stylesheet: (stylerule | varDeclaration)* EOF;
stylerule: tagSelector OPEN_BRACE (styleDeclaration | ifStatement)+ CLOSE_BRACE;

styleDeclaration: LOWER_IDENT COLON assignment SEMICOLON;
varDeclaration: variable ASSIGNMENT_OPERATOR assignment SEMICOLON;
ifStatement: IF BOX_BRACKET_OPEN condition BOX_BRACKET_CLOSE OPEN_BRACE (styleDeclaration | ifStatement)+ CLOSE_BRACE elseStatement*;
elseStatement: ELSE OPEN_BRACE (styleDeclaration | ifStatement)+ CLOSE_BRACE;

assignment:
            COLOR |
            PIXELSIZE |
            TRUE |
            FALSE |
            PERCENTAGE |
            SCALAR |
            variable |
            expression;

expression: expression MUL expression |
            expression PLUS expression |
            expression MIN expression |
            SCALAR |
            PIXELSIZE |
            PERCENTAGE |
            variable;

condition: TRUE | FALSE | variable;

variable: CAPITAL_IDENT;

tagSelector: ID_IDENT |
             CLASS_IDENT |
             LOWER_IDENT;



