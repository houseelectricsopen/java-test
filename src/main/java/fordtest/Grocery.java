package fordtest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Grocery {

    private List<StockItem> stockItems = new ArrayList<>();

    //a list of functions that calculate a discount in pence based on a list of basket entries
    private List<BiFunction<List<BasketEntry>, LocalDate, Integer>> discounts = new ArrayList<>();

    private void defaultStockAndDiscounts() {
        stockItems.add(new StockItem("soup", "tin", 65));
        stockItems.add(new StockItem("bread", "loaf", 80));
        stockItems.add(new StockItem("milk", "bottle", 130));
        stockItems.add(new StockItem("apples", "single", 10));


        final LocalDate today = LocalDate.now();
        final LocalDate yesterday = today.minusDays(1);
        final LocalDate threeDaysHence = today.plusDays(3);
        final LocalDate endOfFollowingMonthAfterThreeDaysHence = threeDaysHence.plusMonths(2).withDayOfMonth(0).minusDays(1);
        System.out.println("yesterday = " + yesterday);

        BiFunction<List<BasketEntry>, LocalDate, Integer> buy2TinsOfSoupGetHalfPriceLoaf = (basket, pricingDate) -> {
            if (pricingDate.isBefore(yesterday)) {
                return 0;
            }
            long soupTinCount = basket.stream().filter(entry -> entry.stockItem.product.equals("soup")).map(entry -> new Integer(entry.quantity))
                    .mapToInt(i -> i).sum();
            Optional<BasketEntry> loafItem = basket.stream().filter(entry -> entry.stockItem.product.equals("loaf")).findFirst();
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

    public void price(LocalDate pricingDate) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in)));
        String line;
        for (; null != (line = br.readLine()); ) {
            System.out.println("read " + line);
        }
    }

}
