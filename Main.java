import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
public class Main {
    public static void main(String[] args) throws IOException {
        Map<String, Integer> romanToArabic = Map.of("I", 1, "II", 2, "III", 3, "IV", 4,
                "V", 5, "VI", 6, "VII", 7, "VIII", 8, "IX", 9, "X", 10);
        List<String> operations = List.of("+", "-", "*", "/");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        List<String> list = Arrays.asList(bufferedReader.readLine().split(" "));
        if (!operations.contains(list.get(1))) {
            throw new IllegalArgumentException("Ввели неправильную арифметическую операцию");
        }
        String operation = list.get(1);
        if (romanToArabic.containsKey(list.get(0)) && romanToArabic.containsKey(list.get(2))) {
            int left = romanToArabic.get(list.get(0));
            int right = romanToArabic.get(list.get(2));
            int result = calculate(left, right, operation);
            System.out.println(arabicToRoman(result));
        } else if (list.get(0).matches("[0-9]+") && list.get(2).matches("[0-9]+")) {
            int left = Integer.parseInt(list.get(0));
            int right = Integer.parseInt(list.get(2));
            if (left < 1 || left > 10 || right < 1 || right > 10) {
                throw new IllegalArgumentException("Ввели неправильное число");
            }
            System.out.println(calculate(left, right, operation));
        } else {
            throw new IllegalArgumentException("Ввели неправильные данные");
        }
    }
    private static int calculate(int left, int right, String operation) {
        return switch (operation) {
            case "+" -> left + right;
            case "-" -> left - right;
            case "*" -> left * right;
            default -> left / right;
        };
    }
    private static String arabicToRoman(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Результат операции с римскими цифрами меньше или равно 0");
        }
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (i < romanNumerals.size()) {
            RomanNumeral current = romanNumerals.get(i);
            if (current.getValue() <= number) {
                sb.append(current.name());
                number -= current.getValue();
            } else {
                i++;
            }
        }
        return sb.toString();
    }
    enum RomanNumeral {
        I(1),
        IV(4),
        V(5),
        IX(9),
        X(10),
        XL(40),
        L(50),
        XC(90),
        C(100);
        private int value;
        RomanNumeral(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing(RomanNumeral::getValue).reversed())
                    .collect(Collectors.toList());
        }
    }
}