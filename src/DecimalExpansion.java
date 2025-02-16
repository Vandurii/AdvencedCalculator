import enums.Expansion;

import java.util.ArrayList;
import java.util.*;

class DecimalExpansion{
    int wholePart;
    int decimal;
    Expansion expansion;

    public DecimalExpansion(Fraction fraction){
        this.wholePart = fraction.numerator / fraction.denominator;
        if(wholePart > 0) fraction.numerator = fraction.numerator - (wholePart * fraction.denominator);

        init(fraction);
    }

    static String val = "";
    static int deep = 0;
    static int maxDeep = 20;
    static List<Integer> factors = new ArrayList<>();
    public void init(Fraction a){
        if(a.numerator == 0) return;
        if(deep > maxDeep) return;
        deep++;

        int n = a.numerator;
        int d = a.denominator;
        int result = n / d;

        val += result;
        if(result == 0){
            int next = n * 10;
            factors.add(next);
            init(new Fraction(next, d));
        }else{
            int next = (n - (result * d)) * 10;
            factors.add(next);
            init(new Fraction(next, d));
        }

        deep--;
        if(deep == 0) resolveExpansion();
    }

    public void resolveExpansion(){
        int start = 1;
        int maxLen = 5;
        int maxIntLen = 10;
        int len = periodicLength(factors);
        len = Main.min(maxIntLen, len);
        if(len == 0){
            expansion = Expansion.finite;
            int end = Main.min(maxLen, val.length());
            decimal = Integer.parseInt(val.substring(start, end));
        }else if(len == 1){
            decimal = Integer.parseInt(val.substring(start, start + 1));
            expansion = Expansion.infinite;
        }else{
            decimal = Integer.parseInt(val.substring(start, start + len));
            expansion = Expansion.infinitePeriodic;
        }

        reset();
    }

    public void reset(){
        val = "";
        factors.clear();
    }

    public int periodicLength(List<Integer> list){
        for(int i = 1; i < list.size(); i++){
            if(Objects.equals(list.getFirst(), list.get(i))) return i;
        }

        return  0;
    }


    public int getLen(){
        if (this.expansion == Expansion.finite) {
            int n = decimal;
            int len = 1;

            while((n /= 10) > 0) len++;

            return len;
        }else{
            throw new IllegalStateException("This decimal value is infinite");
        }
    }

    public void translateToFraction(){

    }

    public String toString(){
        return switch (expansion) {
            case Expansion.finite -> String.format("%s.%s", wholePart, decimal);
            case Expansion.infinite, Expansion.infinitePeriodic -> String.format("%s.(%s)", wholePart, decimal);
            default -> null;
        };
    }
}