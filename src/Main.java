import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
       Fraction a = new Fraction(-1, 6);
       Fraction b = new Fraction(3, 4);
       Fraction c = new Fraction(1, 9);

       a.multiply(b);
        System.out.println(a.toString());
    }

    public static int  lowestCommonMultiple(int ...args){
        int max = max(args);
        List<Integer> primeFactors = generatePrimeNumbersMax(max);
        List<Integer> foundedFactors = new ArrayList<>();

        boolean found = true;
        while(found){

            found = false;
            for (int divider : primeFactors) {
                boolean add = false;
                for (int i = 0; i < args.length; i++) {
                    int number = args[i];
                    if (number > 1) {
                        if (number % divider == 0) {
                            args[i] = number / divider;
                            found = true;
                            add = true;
                        }
                    }
                }

                if (add) foundedFactors.add(divider);
            }
        }

        return reduceMul(foundedFactors);
    }

    public static int greatestCommonDivisor(int a, int b){
        List<Integer> aPrimeFactors = getPrimeFactors(a);
        List<Integer> bPrimeFactors = getPrimeFactors(b);

        // todo stream
        List<Integer> duplicateNumber = new ArrayList<>();

        for(Integer num: aPrimeFactors){
            if(bPrimeFactors.contains(num)){
                duplicateNumber.add(num);
                bPrimeFactors.remove(num);
            }
        }

        return reduceMul(duplicateNumber);
    }

    public static int reduceMul(List<Integer> list){
        int sum = 1;
        for(Integer num: list) sum *= num;
        return sum;
    }

    public static int getScale(int before, int after){
        return  after / before;
    }

    public static int max(List<Integer> list){
        int highest = Integer.MIN_VALUE;

        for(int i : list){
            if(i > highest) highest = i;
        }

        return highest;
    }

    public static int max(int[] args){
        int highest = Integer.MIN_VALUE;

        for(int i : args){
            if(i > highest) highest = i;
        }

        return highest;
    }

    public static boolean isPrime(int number){
        int divider = 2;
        int count = 1;

        while(divider <= number){
            if(number % divider == 0) count++;
            divider++;
        }

        return count == 2;
    }

    public static List<Integer> getPrimeFactors(int number){
        List<Integer> primeFactors = new ArrayList<>();
        List<Integer> primeNumbers = generatePrimeNumbersMax(number);

        while(number != 1){
            for(Integer num: primeNumbers){
                if(number % num == 0){
                    primeFactors.add(num);
                    number /= num;
                }
            }
        }

        return primeFactors;
    }

    public static List<Integer> generatePrimeNumbersMax(int max){
        List<Integer> list = new ArrayList<>();

        for(int i = 2; i <= max; i++){
            if(isPrime(i)) list.add(i);
        }

        return list;
    }

    public static List<Integer> generatePrimeNumbersRange(int range){
        List<Integer> list = new ArrayList<>();

        int i = 2;
        while(list.size() < range){
            if(isPrime(i)) list.add(i);
            i++;
        }

        return list;
    }
}

