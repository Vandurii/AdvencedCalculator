public class Power {
    Fraction base;
    int power;

    public Power(int base, int power){
        init(new Fraction(base), power);
    }

    public Power(Fraction base, int power){
        init(base, power);
    }

    public void init(Fraction base, int power){
        this.base = base;
        this.power = power;
        if(power < 0){
            this.power = Main.absolute(power);
            int temp = this.base.denominator;
            this.base.denominator = this.base.numerator;
            this.base.numerator = temp;
        }

        calcValue();
    }

    public  void calcValue(){
        if(power == 0){
            base.numerator = 1;
            base.denominator = 1;
        }

        int bn = base.numerator;
        int dn = base.denominator;
        while(--power > 0){
            base.numerator *= bn;
            base.denominator *= dn;
        }
    };

    public Power mul(Power power){
         if(this.base.equals(power.base)){
             int pow = this.power + power.power;
             return new Power(this.base, pow);
         }else if(this.power == power.power){
             Fraction f = this.base.multiply(power.base);
             return new Power(f, power.power);
         }else{
            throw new IllegalStateException("cant multiply this two powers");
         }
    }

    public Power div(Power power){
        if(this.base.equals(power.base)){
            int pow = this.power - power.power;
            return new Power(this.base, pow);
        }else if(this.power == power.power){
            Fraction f = this.base.divide(power.base);
            return new Power(f, power.power);
        }else{
            throw new IllegalStateException("cant divide this two powers");
        }
    }

    public Power power(int power){
        int pow = this.power * power;
        return new Power(this.base, pow);
    }

    @Override
    public String toString(){
        return base.toString();
    }
}
