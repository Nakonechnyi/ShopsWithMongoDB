package model;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import dao.AbstractDao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor A_Nakonechnyi
 * @date 10.06.2016.
 */
public class ShopsFactory {

    public static Class<? extends Shop> getShop(String name) throws UnknownHostException {
        if (name.equals("Guitars")) {
            return GuitarShop.class;
        } else if (name.equals("Bikes")) {
            return BikesShop.class;
        } else {
            return null;
        }

    }


    public static List<Shop> getShops() throws UnknownHostException {
        List<Shop> result = new ArrayList<>();
        DBCollection collection = AbstractDao.getInstance().getDB().getCollection("shops");

        DBCursor cursor = collection.find();
        while(cursor.hasNext()) {
            result.add(Shop.parseShop(cursor.next()));
        }
        return result;
    }

}
