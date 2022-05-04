package app.antlr;

import app.antlr.generated.CalculatorBaseVisitor;
import app.antlr.generated.CalculatorParser;
import app.antlr.generated.CalculatorVisitor;

import java.math.BigDecimal;
import java.math.MathContext;

public class MathExpressionVisitor extends CalculatorBaseVisitor<BigDecimal> implements CalculatorVisitor<BigDecimal> {

    private final MathContext mathContext;

    public MathExpressionVisitor(MathContext mathContext) {
        this.mathContext = mathContext;
    }

    @Override
    public BigDecimal visitStart(CalculatorParser.StartContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public BigDecimal visitInteger(CalculatorParser.IntegerContext ctx) {
        return new BigDecimal(ctx.Int().getText());
    }

    @Override
    public BigDecimal visitDecimal(CalculatorParser.DecimalContext ctx) {
        return new BigDecimal(ctx.Dec().getText());
    }

    @Override
    public BigDecimal visitSubexpression(CalculatorParser.SubexpressionContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public BigDecimal visitMultiplyOrDivide(CalculatorParser.MultiplyOrDivideContext ctx) {
        BigDecimal left = visit(ctx.expr(0));
        BigDecimal right = visit(ctx.expr(1));
        String operation = ctx.TimesOrObelus().getText();

        return operation.equals("/") ? left.divide(right, mathContext) : left.multiply(right, mathContext);
    }

    @Override
    public BigDecimal visitAddOrSubtract(CalculatorParser.AddOrSubtractContext ctx) {
        BigDecimal left = visit(ctx.expr(0));
        BigDecimal right = visit(ctx.expr(1));
        String operation = ctx.PlusOrMinus().getText();

        return operation.equals("-") ? left.subtract(right, mathContext) : left.add(right, mathContext);
    }
}
