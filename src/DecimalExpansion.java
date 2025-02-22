import enums.Expansion;

import java.util.ArrayList;
import java.util.*;

class DecimalExpansion{
    String wholePart;
    String decimal;
    Expansion expansion;

    public DecimalExpansion(Fraction fraction){
        this.wholePart = String.valueOf(fraction.numerator / fraction.denominator);
        int whole = Integer.parseInt(wholePart);
        if(whole > 0) fraction.numerator = fraction.numerator - (whole * fraction.denominator);

        init(fraction);
    }

    public DecimalExpansion(String wholePart, String decimal){
        this.wholePart = wholePart;
        this.decimal = decimal;
        this.expansion = Expansion.finite;
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
            decimal = val.substring(start, end);
        }else if(len == 1){
            decimal = val.substring(start, start + 1);
            expansion = Expansion.infinite;
        }else{
            decimal = val.substring(start, start + len);
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

    public int pow(int pow){
        int n = 1;
        for(int i = 0; i < pow; i++){
            n *= 10;
        }

        return n;
    }

    public Fraction translateToFraction(){
        if (this.expansion == Expansion.finite) {
            int len = decimal.length();
            int den = pow(len);

            int whole = Integer.parseInt(wholePart);
            Fraction f = new Fraction(whole, Integer.parseInt(decimal), den);
            f.reduceFraction();

            return f;
        }else{
            // todo make it work
//            int len = decimal.length();
//            int den = pow(len);
//
//            float x = Float.parseFloat("0." + decimal);
//            int y = (int)((x * den));
//
//            Fraction f = new Fraction(y, den - 1);
//            return f;
            return null;
        }
    }

    public String toString(){
        return switch (expansion) {
            case Expansion.finite -> String.format("%s.%s", wholePart, decimal);
            case Expansion.infinite, Expansion.infinitePeriodic -> String.format("%s.(%s)", wholePart, decimal);
            default -> null;
        };
    }
}