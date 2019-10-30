package fordtest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Grocery {

    public static class StockItem {

        String product;
        String unit;
        int costInPence;

        StockItem(String productIn, String unitIn, int costInPenceIn) {
            this.product = productIn;
            this.unit = unitIn;
            this.costInPence = costInPenceIn;
        }
    }

    public static class BasketEntry {
        StockItem stockItem;
        int quantity;

        BasketEntry(StockItem stockItemIn, int quantityIn) {
            this.stockItem = stockItemIn;
            this.quantity = quantityIn;
        }
    }

    private List<StockItem> stockItems = new ArrayList<>();

    //a list of functions that calculate a discount in pence based on a list of basket entries
    private List<Function<List<BasketEntry>, Integer>> discounts = new ArrayList<>();

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

        Function<List<BasketEntry>, Integer> buy2TinsOfSoupGetHalfPriceLoaf = (basket) -> {
            //TODO
            return 0;
        };

        Function<List<BasketEntry>, Integer> applel0PercentDiscount = (basket) -> {
            //TODO
            return 0;
        };


    }


    public void process() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in)));
        String line;
        for (; null != (line = br.readLine()); ) {
            System.out.println("read " + line);
        }
    }

}
