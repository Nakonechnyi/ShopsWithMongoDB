package models;

import com.mongodb.DBObject;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @autor A_Nakonechnyi
 * @date 10.06.2016.
 */
public abstract class Shop {

    protected abstract String getId();

    protected abstract String getName();

    public List<Product> getProducts() throws UnknownHostException {
        return Product.getProducts("shopId", String.valueOf(getId()));
    }

    public List<Product> getProducts(String category) throws UnknownHostException {
        return Product.getProducts("category", category);
    }

    public Product addProduct(String title, double price, ProductStatus status, String category) throws Exception {
        return Product.addProduct(title, price, status, category, String.valueOf(getId()));

    }
    public Product updateProductStatus(Product product, ProductStatus status) throws UnknownHostException {
        return product.updateStatus(status);
    }

    public Product updateProductPrice(Product product, double price) throws UnknownHostException {
        return product.updatePrice(price);
    }

    public List<String> getCategories() throws UnknownHostException {
        return Product.getCategories(getId());
    }

    public List<Product> getAvailableProducts() throws UnknownHostException {
        return Product.getProducts("status", "AVAILABLE");
    }

    public String toString() {
        return "Shop: " + getName() + ", \n" +
                "id: " + getId() + "; \n";
    }

    public static Shop parseShop(DBObject object) throws UnknownHostException {
        String shopId = (String) object.get("_id");
        String shopName = (String) object.get("name");
        if (shopName.equals("Guitars")){
            return GuitarShop.getInstance(shopId);
        } else if (shopName.equals("Bikes")) {
            return BikesShop.getInstance(shopId);
        } else {
            return null;
        }
    }
}
