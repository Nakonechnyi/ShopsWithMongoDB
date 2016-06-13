package model;

import com.mongodb.DBObject;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @autor A_Nakonechnyi
 * @date 10.06.2016.
 */
public abstract class Shop {

    public final static String CATEGORY = "category";
    public final static String SHOP_ID = "shopId";
    public final static String STATUS = "status";
    public final static String NAME = "name";
    public final static String ID = "_id";

    protected abstract String getId();

    protected abstract String getName();

    public List<Product> getProducts() throws UnknownHostException {
        return Product.getProducts(SHOP_ID, String.valueOf(getId()));
    }

    public List<Product> getProducts(String category) throws UnknownHostException {
        return Product.getProducts(CATEGORY, category);
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
        return Product.getProducts(STATUS, ProductStatus.AVAILABLE.toString());
    }

    public String toString() {
        return "Shop: " + getName() + ", \n" +
                "id: " + getId() + "; \n";
    }

    public static Shop parseShop(DBObject object) throws UnknownHostException {
        String shopId = (String) object.get(ID);
        String shopName = (String) object.get(NAME);
        if (shopName.equals("Guitars")){
            return GuitarShop.getInstance(shopId);
        } else if (shopName.equals("Bikes")) {
            return BikesShop.getInstance(shopId);
        } else {
            return null;
        }
    }
}
