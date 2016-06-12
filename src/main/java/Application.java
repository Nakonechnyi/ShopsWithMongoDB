import models.Shop;
import models.ShopsFactory;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @autor A_Nakonechnyi
 * @date 10.06.2016.
 */
public class Application {

    public static void main(String[] args) {
        try {
            List<Shop> shops = ShopsFactory.getShops();

            //Start Executing Shops and keep Threads
            Thread[] shopsThreads = new Thread[shops.size()];
            for (int i = 0; i < shops.size(); i++) {

                shopsThreads[i] = new Thread( new ShopThread(shops.get(i)));
                shopsThreads[i].start();

                if (i < shops.size()-1) {
                    Thread.sleep(10000);
                }
            }
            //Wait them
            for (int i = 0; i < shops.size(); i++) {
                shopsThreads[i].join();
            }

            System.out.println("Finish!");


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
