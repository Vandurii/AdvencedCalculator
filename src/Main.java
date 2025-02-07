import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println(generatePrimeNumber(30));
    }

    public static int nww(int a, int b){
        List<Integer> aPrimeFactors = getPrimeFactors(a);
        List<Integer> bPrimeFactors = getPrimeFactors(b);

        List<Integer> longerList;
        List<Integer> shorterList;

        if(aPrimeFactors.size() > bPrimeFactors.size()){
            longerList = aPrimeFactors;
            shorterList = bPrimeFactors;
        }else{
            longerList = bPrimeFactors;
            shorterList = aPrimeFactors;
        }

        for(Integer num: longerList){
            shorterList.remove(num);
        }

        return reduce(shorterList) * reduce(longerList);
    }

    public static int nwd(int a, int b){
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

        return reduce(duplicateNumber);
    }

    public static int reduce(List<Integer> list){
        int sum = 1;
        for(Integer num: list) sum *= num;
        return sum;
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
        List<Integer> primeNumbers = generatePrimeNumber(number);

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

    public static List<Integer> generatePrimeNumber(int range){
        List<Integer> list = new ArrayList<>();

        for(int i = 2; i <= range; i++){
            if(isPrime(i)) list.add(i);
        }

        return list;
    }
}

