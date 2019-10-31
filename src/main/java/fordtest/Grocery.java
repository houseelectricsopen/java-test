package fordtest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Assumptions:
 * Its assumed that "command prompt" means System.in and System.out
 *
 * Basket items are added with "add <productName>"
 * Baskets are priced with  "price <pricingDate>"
 * All prices are in pence
 *
 * TODO: process quantities of BasketEntry, currently only quantity==1 is used
 * 
 */
public class Grocery {

    public Grocery() {
        defaultStockAndDiscounts();
    }

    public void priceFromSystemIn() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in)));
        String line;
        List<BasketEntry> currentBasket = new ArrayList<>();
        for (; null != (line = br.readLine()); ) {
            line = line.trim();
            if (line.startsWith("add")) {
                String productName = line.replace("add", "").trim();
                if (!productName2stockItem.containsKey(productName)) {
                    System.out.println("Error: unknown product " + productName);
                    return;
                }
                // basket entries with quentity >1 not dealt with
                currentBasket.add(new BasketEntry(productName2stockItem.get(productName), 1));
            } else if (line.startsWith("price")) {
                String strLocalDate = line.replace("price", "").trim();
                LocalDate pricingDate = LocalDate.parse(strLocalDate);
                int price = price(currentBasket, pricingDate);
                System.out.println(price);
                currentBasket = new ArrayList<>();

            }
        }
    }



    private Map<String, StockItem> productName2stockItem = new HashMap<>();

    //a list of functions that calculate a discount in pence based on a list of basket entries
    private List<BiFunction<List<BasketEntry>, LocalDate, Integer>> discounts = new ArrayList<>();

    private void defaultStockAndDiscounts() {
        productName2stockItem.put("soup", new StockItem("soup", "tin", 65));
        productName2stockItem.put("bread", new StockItem("bread", "loaf", 80));
        productName2stockItem.put("milk", new StockItem("milk", "bottle", 130));
        productName2stockItem.put("apple", new StockItem("apple", "single", 10));

        final LocalDate today = LocalDate.now();
        final LocalDate yesterday = today.minusDays(1);
        final LocalDate threeDaysHence = today.plusDays(3);
        final LocalDate endOfFollowingMonthAfterThreeDaysHence = threeDaysHence.plusMonths(2).withDayOfMonth(1).minusDays(1);

        BiFunction<List<BasketEntry>, LocalDate, Integer> buy2TinsOfSoupGetHalfPriceLoaf = (basket, pricingDate) -> {
            if (pricingDate.isBefore(yesterday)) {
                return 0;
            }
            long soupTinCount = basket.stream().filter(entry -> entry.stockItem.product.equals("soup")).map(entry -> new Integer(entry.quantity))
                    .mapToInt(i -> i).sum();
            Optional<BasketEntry> loafItem = basket.stream().filter(entry -> entry.stockItem.product.equals("bread")).findFirst();
            if (soupTinCount >= 2 && loafItem.isPresent()) {
                return loafItem.get().stockItem.costInPence / 2;
            } else {
                return 0;
            }
        };

        BiFunction<List<BasketEntry>, LocalDate, Integer> applel0PercentDiscount = (basket, pricingDate) -> {
            if (pricingDate.isBefore(threeDaysHence) || pricingDate.isAfter(endOfFollowingMonthAfterThreeDaysHence)) {
                return 0;
            }
            List<BasketEntry> appleEntries = basket.stream().filter(entry -> entry.stockItem.product.equals("apple")).collect(Collectors.toList());
            if (appleEntries.size() > 0) {
                long appleCount = appleEntries.stream().map(entry -> entry.quantity).mapToInt(i -> i).sum();
                return (int) ((appleCount * appleEntries.get(0).stockItem.costInPence) * 0.1);
            } else {
                return 0;
            }
        };
        discounts.add(buy2TinsOfSoupGetHalfPriceLoaf);
        discounts.add(applel0PercentDiscount);

    }

    private int price(List<BasketEntry> currentBasket, LocalDate pricingDate) {
        long rawPrice = currentBasket.stream().map(entry -> entry.quantity * entry.stockItem.costInPence).mapToLong(i -> i).sum();
        long discount = discounts.stream().map(d -> d.apply(currentBasket, pricingDate)).mapToLong(i -> i).sum();
        return (int) (rawPrice - discount);
    }

}
