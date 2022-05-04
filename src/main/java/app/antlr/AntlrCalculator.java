package app.antlr;

import app.Calculator;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

import java.util.function.Function;

public record AntlrCalculator<Input, Output>(Function<Input, ParseTree> createTree, ParseTreeVisitor<Output> visitor)
        implements Calculator<Input, Output> {

    @Override
    public Output calculate(Input input) throws ArithmeticException {
        return visitor.visit(createTree.apply(input));
    }
}
