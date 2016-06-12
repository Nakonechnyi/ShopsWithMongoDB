import models.Product;
import models.ProductStatus;
import models.Shop;

import java.util.List;

/**
 * @autor A_Nakonechnyi
 * @date 12.06.2016.
 */
public class ShopThread implements Runnable {

    private Shop shop;

    public ShopThread(Shop s) {
        shop = s;
    }

    public void run() {
        try {
            System.out.println("Start: "+ shop.toString());
            List<String> categories = shop.getCategories();
            // #1 Add products
            shop.addProduct("Gift1", 1000.1, ProductStatus.AVAILABLE, categories.get(0));
            shop.addProduct("Gift2", 2000.1, ProductStatus.AVAILABLE, categories.get(0));
            shop.addProduct("Gift3", 3000.1, ProductStatus.AVAILABLE, categories.get(1));
            shop.addProduct("Gift4", 4000.1, ProductStatus.AVAILABLE, categories.get(2));
            // #2 Change status on "Absent" in one category
            List<Product> products = shop.getProducts(categories.get(0));
            for (Product product : products){
                shop.updateProductStatus(product, ProductStatus.ABSENT);
            }
            // #3 Change half of other products on "Expected" status
            List<Product> expectedProducts = shop.getProducts(categories.get(1));
            expectedProducts.addAll(shop.getProducts(categories.get(2)));
            for (int counter = 0; counter < expectedProducts.size()-1; counter=counter+2 ){ //each 2nd
                expectedProducts.get(counter).updateStatus(ProductStatus.EXPECTED);
            }
            // #4 Increase price on 20% for available products
            List<Product> availableProducts = shop.getAvailableProducts();
            for (Product product : availableProducts ){
                shop.updateProductPrice(product,((product.getPrice())* 1.2));
            }
            System.out.println("End of " + shop.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
