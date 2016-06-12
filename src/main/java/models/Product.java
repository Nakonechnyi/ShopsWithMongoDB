package models;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.util.JSON;
import dao.AbstractDao;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.*;

/**
 * @autor A_Nakonechnyi
 * @date 11.06.2016.
 */
public class Product {

    private ObjectId _id;
    private String title;
    private double price;
    private ProductStatus status;
    private String category;
    private String shopId;

    public Product( String title, double price, ProductStatus status, String category, String id) {
        this.title = title;
        this.price = price;
        this.status = status;
        this.category = category;
        this.shopId = id;
    }

    public static List<Product> getProducts(String criterionName, String criterionValue) throws UnknownHostException {
        List<Product> result = new ArrayList<Product>();
        DBCollection collection = AbstractDao.getInstance().getDB().getCollection("products");
        BasicDBObject query = new BasicDBObject();

        query.put(criterionName, criterionValue);
        DBCursor cursor = collection.find(query);

        while(cursor.hasNext()) {
            result.add(Product.parseProduct(cursor.next()));
        }
        return result;
    }

    private static Product parseProduct(DBObject next) {
        /*Product product = (new Gson().fromJson( next.toString(), Product.class)); incorrect parse _id */
        ObjectId _id = (ObjectId) next.get("_id");
        String title = (String) next.get("title");
        double price = (Double)next.get("price");
        ProductStatus status = ProductStatus.valueOf((String)next.get("status"));
        String category = (String) next.get("category");
        String id = (String) next.get("shopId");
        Product product = new Product(title, price, status, category, id);
        product._id = _id;
        return product;
    }

    public static Product addProduct(String title, double price, ProductStatus status, String category, String shopId) throws Exception {
        Product product = new Product( title, price, status,category,shopId);
        Gson gson = new Gson();
        BasicDBObject productDBObj = (BasicDBObject) JSON.parse(gson.toJson(product));
        AbstractDao.getInstance().saveToDB("products", productDBObj);
        product._id = (ObjectId) productDBObj.get("_id");
        return product;
    }

    public Product updateStatus(ProductStatus status) throws UnknownHostException {
        DBCollection collection = AbstractDao.getInstance().getDB().getCollection("products");

        BasicDBObject query = new BasicDBObject();
        query.append("_id", _id);

        BasicDBObject update = new BasicDBObject();
        update.append("status", status.toString());

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.append("$set", update);

        collection.update(query, updateObj);
        DBObject product = collection.findOne(query);
        return Product.parseProduct(product);

    }

    public Product updatePrice(double price) throws UnknownHostException {
        DBCollection collection = AbstractDao.getInstance().getDB().getCollection("products");

        BasicDBObject query = new BasicDBObject();
        query.append("_id", _id);

        BasicDBObject update = new BasicDBObject();
        update.append("price", price);

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.append("$set", update);

        collection.update(query, updateObj);
        DBObject product = collection.findOne(query);
        return Product.parseProduct(product);
    }

    public String toString() {
        return "\nProduct: " + title + "; \n" +
                "   Id:" + _id + "; \n" +
                "   Price:" + price + "; \n" +
                "   Status:" + status + "; \n" +
                "   Category:" + category + "; \n" +
                "   ShopId:" + shopId + "; \n";
    }

    public static List<String> getCategories(String shopId) throws UnknownHostException {
        Set<String> result = new HashSet<String>();
        DBCollection collection = AbstractDao.getInstance().getDB().getCollection("products");

        BasicDBObject query = new BasicDBObject();
        query.append("shopId", shopId);

        BasicDBObject fields = new BasicDBObject();
        fields.append("category", 1);

        DBCursor cursor = collection.find(query,fields);

        while(cursor.hasNext()) {
            result.add((String) cursor.next().get("category"));
        }
        return new ArrayList<String>(result);
    }

    public double getPrice() {
        return price;
    }

}
