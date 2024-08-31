package com.currencyconverter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Type currency to convert from: ");
        String convertFrom = scanner.nextLine();
        System.out.print("Type currency to convert to: ");
        String convertTo = scanner.nextLine();
        System.out.print("Type quantity to convert: ");
        BigDecimal quantity = scanner.nextBigDecimal();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://openexchangerates.org/api/latest.json?app_id=f0f53c7b1ebd48bc9290e5334c99efc5&base=USD&prettyprint=true&show_alternative=true")
                .get()
                .addHeader("accept", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        String stringResponse = null;
        if (response.body() != null) {
            stringResponse = response.body().string();
        }
        JSONObject jsonObject = null;
        if (stringResponse != null) {
            jsonObject = new JSONObject(stringResponse);
        }
        JSONObject ratesObject = null;
        if (jsonObject != null) {
            ratesObject = jsonObject.getJSONObject("rates");
        }
        BigDecimal rate = ratesObject.getBigDecimal(convertTo.toUpperCase());

        BigDecimal result = rate.multiply(quantity);
        System.out.println(result);
//        System.out.println(stringResponse);

    }
}
