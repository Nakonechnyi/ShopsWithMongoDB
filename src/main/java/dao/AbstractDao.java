package dao;

import com.mongodb.*;

import java.net.UnknownHostException;

public class AbstractDao {

    private Mongo connection = null;
    private DB db = null;

    private static AbstractDao mongoDao = null;

    private AbstractDao() throws UnknownHostException {
            connection= new Mongo("localhost" ,27017);
        db = connection.getDB("test");
    }

    public static AbstractDao getInstance() throws UnknownHostException{
        if(mongoDao==null){
            mongoDao = new AbstractDao();
        }
        return mongoDao;
    }

    public void saveToDB(String tableName, BasicDBObject dbObject)throws Exception{
        DBCollection dbCollection = db.getCollection(tableName);
        dbCollection.insert(dbObject);
    }

    public void showDB(String tableName)throws Exception{
        DBCollection dbCollection = db.getCollection(tableName);
        DBCursor cur = dbCollection.find();
        while(cur.hasNext()) {
            System.out.println(cur.next());
        }
    }

    public DB getDB() throws UnknownHostException {
        return AbstractDao.getInstance().db;
    }



}
