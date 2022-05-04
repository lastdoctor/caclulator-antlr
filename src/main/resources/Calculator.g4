grammar Calculator;

start: expr EOF;

// expression
expr: expr TimesOrObelus expr # multiplyOrDivide
| expr PlusOrMinus expr # addOrSubtract
| Int # integer
| Dec # decimal
| '(' expr ')' # subexpression;

TimesOrObelus: '*'|'/';
PlusOrMinus: '+'|'-';
Int: [0-9]+;
Dec: [0-9]+'.'[0-9]+;
Ws: [ \t] -> skip;
