package models;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import dao.AbstractDao;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @autor A_Nakonechnyi
 * @date 12.06.2016.
 */
public class BikesShop extends Shop {
    private final String shopId;
    private final String name;
    private static BikesShop instance;

    private BikesShop(String shopId, String name){
        this.shopId = shopId;
        this.name = name;
    }

    public static BikesShop getInstance(String shopId) throws UnknownHostException {
        if (instance == null) {
            DBCollection collection = AbstractDao.getInstance().getDB().getCollection("shops");
            BasicDBObject whereQuery = new BasicDBObject();

            whereQuery.put("_id", shopId);

            DBObject object = collection.findOne(whereQuery);
            instance = BikesShop.parseShop(object);
        }
        return instance;
    }
    public static BikesShop parseShop(DBObject object) {
        String shopId = (String) object.get("_id");
        String shopName = (String) object.get("name");
        return new BikesShop(shopId, shopName);
    }

    public List<Product> getProducts() throws UnknownHostException {
        return Product.getProducts("shopId", String.valueOf(shopId));
    }

    @Override
    protected String getId() {
        return shopId;
    }

    @Override
    protected String getName() {
        return name;
    }
}
