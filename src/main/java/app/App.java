package app;

import app.antlr.AntlrCalculator;
import app.antlr.AntlrUtil;
import app.antlr.MathExpressionVisitor;
import app.antlr.generated.CalculatorLexer;
import app.antlr.generated.CalculatorParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Properties;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Configurator.setLevel(PropsUtil.class, Level.INFO);
        createConsoleCalculator().run();
    }

    private static ParseTree createTree(String s) {
        return AntlrUtil.parserFromString(s, CalculatorLexer::new, CalculatorParser::new).start();
    }

    private static ParseTreeVisitor<BigDecimal> createVisitor(Properties props) {
        MathContext mathContext = new MathContext(PropsUtil.getPrecision(props), RoundingMode.HALF_UP);
        return new MathExpressionVisitor(mathContext);
    }

    private static ConsoleCalculator createConsoleCalculator() {
        Properties props = PropsUtil.props();
        Calculator<String, BigDecimal> calculator = new AntlrCalculator<>(App::createTree, createVisitor(props));
        return new ConsoleCalculator(calculator, new Scanner(System.in), PropsUtil.getStopWords(props));
    }
}
