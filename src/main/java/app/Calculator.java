package app;

public interface Calculator <Input, Output> {
    Output calculate(Input input) throws ArithmeticException;
}
