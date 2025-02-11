import java.util.Arrays;

public class Fraction {

    int numerator;
    int denominator;

    public Fraction(int numerator, int denominator){
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public void add(Fraction ...args){
        reduceToCommonDenominator(args);
        infixToFunction(Operator.add, args);
    }

    public void subtract(Fraction ...args){
        reduceToCommonDenominator(args);
        infixToFunction(Operator.subtract, args);
    }

    public void multiply(Fraction ...args){
        infixToFunction(Operator.multiply, args);
    }

    public void divide(Fraction ...args){
        infixToFunction(Operator.divide, args);
    }

    public void infixToFunction(Operator operator, Fraction ...args){
        for(Fraction f: args){
            switch (operator){
                case add:
                    this.numerator += f.numerator;
                    break;
                case subtract:
                    this.numerator -= f.numerator;
                    break;
                case multiply:
                    this.numerator *= f.numerator;
                    this.denominator *= f.denominator;
                    break;
                case divide:
                    this.numerator *= f.denominator;
                    this.denominator *= f.numerator;
                    break;
                default:
                    throw new IllegalStateException("false operator");
            }
        }
    }

    public int findCommonDenominator(Fraction ...args){
        int[] array = new int[args.length + 1];

        array[0] = this.denominator;
        for(int i = 0; i < args.length; i++){
            array[i+1] = args[i].denominator;
        }

        return Main.lowestCommonMultiple(array);
    }

    public void reduceToCommonDenominator(Fraction ...other){
        int commonDenominator = findCommonDenominator(other);
        int scale = commonDenominator / denominator;
        updateValue(scale);

        for(Fraction f: other){
            scale = commonDenominator / f.denominator;
            f.updateValue(scale);
        }
    }

    public void updateValue(int scale){
        this.numerator = this.numerator * scale;
        this.denominator = this.denominator * scale;
    }

    @Override
    public String toString(){
        return String.format("%s / %s", numerator, denominator);
    }
}
