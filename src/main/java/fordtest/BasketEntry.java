package fordtest;

public class BasketEntry {
    StockItem stockItem;
    int quantity;

    BasketEntry(StockItem stockItemIn, int quantityIn) {
        this.stockItem = stockItemIn;
        this.quantity = quantityIn;
    }
}