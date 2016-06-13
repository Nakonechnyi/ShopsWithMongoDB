package model;

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
public class GuitarShop extends Shop {
    private final String shopId;
    private final String name;
    private static GuitarShop instance;

    private GuitarShop(String shopId, String name){
        this.shopId = shopId;
        this.name = name;
    }

    public static GuitarShop getInstance(String shopId) throws UnknownHostException {
        if (instance == null) {
            DBCollection collection = AbstractDao.getInstance().getDB().getCollection("shops");
            BasicDBObject whereQuery = new BasicDBObject();

            whereQuery.put(ID, shopId);

            DBObject object = collection.findOne(whereQuery);
            instance = GuitarShop.parseShop(object);
        }
        return instance;
    }
    public static GuitarShop parseShop(DBObject object) {
        String shopId = (String) object.get(ID);
        String shopName = (String) object.get(NAME);
        return new GuitarShop(shopId, shopName);
    }

    public List<Product> getProducts() throws UnknownHostException {
        return Product.getProducts(SHOP_ID, String.valueOf(shopId));
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
