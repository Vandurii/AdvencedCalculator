import enums.Operator;

public class Fraction {

    int numerator;
    int denominator;

    public Fraction(int numerator, int denominator){
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction(int wholePart, int numerator, int denominator){
        this.numerator = Main.getSign(numerator, wholePart) + (wholePart * denominator);
        this.denominator = denominator;
    }

    public Fraction add(Fraction ...args){
        reduceToCommonDenominator(args);
        return infixToFunction(Operator.add, args);
    }

    public Fraction subtract(Fraction ...args){
        reduceToCommonDenominator(args);
        return infixToFunction(Operator.subtract, args);
    }

    public Fraction multiply(Fraction ...args){
        return infixToFunction(Operator.multiply, args);
    }

    public Fraction divide(Fraction ...args){
        return infixToFunction(Operator.divide, args);
    }

    public Fraction infixToFunction(Operator operator, Fraction ...args){

        Fraction fraction  = new Fraction(numerator, denominator);
        for(Fraction f: args){
            switch (operator){
                case add:
                    fraction.numerator += f.numerator;
                    break;
                case subtract:
                    fraction.numerator -= f.numerator;
                    break;
                case multiply:
                    fraction.numerator *= f.numerator;
                    fraction.denominator *= f.denominator;
                    break;
                case divide:
                    fraction.numerator *= f.denominator;
                    fraction.denominator *= f.numerator;
                    fraction.correctSign();
                    break;
                default:
                    throw new IllegalStateException("false operator");
            }
        }

        fraction.reduceFraction();

        return fraction;
    }

    public void reduceFraction(){
        int commonDivisor = Main.greatestCommonDivisor(numerator, denominator);
        if(commonDivisor > 1){
            numerator /= commonDivisor;
            denominator /= commonDivisor;
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

    public void correctSign(){
        if(denominator < 0){
            denominator *= -1;
            if(numerator > 0) numerator *= -1;
        }
    }

    @Override
    public String toString(){
        if(Main.absolute(numerator) > Main.absolute(denominator)){
            int wholePart = numerator / denominator;

            return String.format("%s: %s / %s", wholePart, Main.absolute(numerator - (wholePart * denominator)), denominator);

        }else {
            return String.format("%s / %s", numerator, denominator);
        }
    }
}
