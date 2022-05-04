import app.antlr.AntlrUtil;
import app.antlr.MathExpressionVisitor;
import app.antlr.generated.CalculatorLexer;
import app.antlr.generated.CalculatorParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

public class MathExpressionVisitorTest {
    private MathExpressionVisitor visitor;

    @BeforeEach
    void createVisitor() {
        visitor = new MathExpressionVisitor(MathContext.UNLIMITED);
    }

    @Test
    void calculateWithFloatAndIntegerTest() {
        ParseTree tree = AntlrUtil.parserFromString("10 * 10.1 + 3.1415926", CalculatorLexer::new, CalculatorParser::new).start();
        Assertions.assertEquals(new BigDecimal("104.1415926"), visitor.visit(tree));
    }

    @Test
    void calculateWithDivisionByZeroTest() {
        ParseTree treeWithError = AntlrUtil.parserFromString("10 - 5 / (5 - 5.0)", CalculatorLexer::new, CalculatorParser::new).start();
        Assertions.assertThrows(ArithmeticException.class, () ->  visitor.visit(treeWithError));

        ParseTree treeWithoutError = AntlrUtil.parserFromString("10 - 5 / (5 - 5.00000000001)", CalculatorLexer::new, CalculatorParser::new).start();
        Assertions.assertEquals(new BigDecimal("500000000010"), visitor.visit(treeWithoutError));
    }
}
