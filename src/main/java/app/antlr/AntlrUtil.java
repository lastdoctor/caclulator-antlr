package app.antlr;

import org.antlr.v4.runtime.*;

import java.util.function.Function;

public class AntlrUtil {
    public static <P extends Parser> P parserFromString(String s, Function<CharStream, Lexer> createLexer, Function<TokenStream, P> createParser) {
        return createParser.apply(new CommonTokenStream(createLexer.apply(CharStreams.fromString(s))));
    }
}
