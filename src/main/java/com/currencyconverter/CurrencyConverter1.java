import java.util.*;
import java.util.regex.*;

public class CurrencyConverter1 {

    public static void main(String[] args) {
        // Входная строка
        String input = "3EUR + 5USD - 2USD + 35KZT - 1EUR";

        // Курсы валют (в реальности ты их подтягиваешь из БД)
        Map<String, Double> rates = new HashMap<>();
        rates.put("EUR", 200.0);
        rates.put("USD", 100.0);
        rates.put("KZT", 1.0);

        double total = convertToKZT(input, rates);

        // Вывод результата
        String outSum = (double ) total + "KZT";
        double  outNum = (double ) total;

        System.out.println("outSum = " + outSum);
        System.out.println("outNum = " + outNum);
    }

    private static double convertToKZT(String input, Map<String, Double> rates) {
        double total = 0.0;

        // Удалим пробелы
        input = input.replaceAll("\\s+", "");

        // Регулярка для парсинга + и - значений с валютой
        Pattern pattern = Pattern.compile("([-+]?\\d+(\\.\\d+)?)([A-Z]{3})");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String amountStr = matcher.group(1);
            String currency = matcher.group(3).toUpperCase();

            double amount = Double.parseDouble(amountStr);
            Double rate = rates.get(currency);

            if (rate == null) {
                throw new IllegalArgumentException("Неизвестная валюта: " + currency);
            }

            total += amount * rate;
        }

        return total;
    }
}
