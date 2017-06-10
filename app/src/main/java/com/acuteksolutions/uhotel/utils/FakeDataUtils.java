package com.acuteksolutions.uhotel.utils;

import android.content.Context;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.TabMainDef;
import com.acuteksolutions.uhotel.mvp.model.conciege.ListRoom;
import com.acuteksolutions.uhotel.mvp.model.conciege.Room;
import com.acuteksolutions.uhotel.mvp.model.login.HomeMenu;
import com.acuteksolutions.uhotel.mvp.model.setting.Account;
import com.acuteksolutions.uhotel.mvp.model.setting.Device;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Toan.IT on 4/12/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class FakeDataUtils {

  public static List<HomeMenu> homeMenus(Context context){
    List<HomeMenu> listMenu=new ArrayList<>();
    listMenu.add(new HomeMenu(R.drawable.menu_concierge,context.getResources().getString(TabMainDef.CONCIERGE)));
    listMenu.add(new HomeMenu(R.drawable.menu_livetv,context.getResources().getString(TabMainDef.LIVETV)));
    listMenu.add(new HomeMenu(R.drawable.menu_movies,context.getResources().getString(TabMainDef.MOVIES)));
    listMenu.add(new HomeMenu(R.drawable.menu_food_activities,context.getResources().getString(TabMainDef.FOOD)));
    listMenu.add(new HomeMenu(R.drawable.menu_room_control,context.getResources().getString(TabMainDef.ROOMCONTROL)));
    listMenu.add(new HomeMenu(R.drawable.menu_settings,context.getResources().getString(R.string.home_menu_setting)));
    return listMenu;
  }
  public static void initDataRoom(Context context){
    Realm.getDefaultInstance().executeTransactionAsync(realm -> {
      if(realm.where(ListRoom.class).count()==0) {
        String[] arrName = context.getResources().getStringArray(R.array.concierge_room_left_array);
        List<ListRoom> masterList = new ArrayList<>();
        for (int i = 0; i < arrName.length; i++) {
          RealmList<Room> detailInfoList = new RealmList<>();
          switch (i) {
            case 0:
              detailInfoList.add(new Room("Bath Towels"));
              detailInfoList.add(new Room("Wash Cloths"));
              detailInfoList.add(new Room("Bathwash"));
              detailInfoList.add(new Room("Shampoo"));
              detailInfoList.add(new Room("Conditioner"));
              break;
            case 1:
              detailInfoList.add(new Room("Hygiene Type 1"));
              detailInfoList.add(new Room("Hygiene Type 2"));
              detailInfoList.add(new Room("Hygiene Type 3"));
              break;
            case 2:
              detailInfoList.add(new Room("Office Type 1"));
              detailInfoList.add(new Room("Office Type 2"));
              detailInfoList.add(new Room("Office Type 3"));
              break;
            case 3:
              detailInfoList.add(new Room("Apps Gen 1"));
              detailInfoList.add(new Room("Apps Gen 2"));
              break;
            default:
              detailInfoList.add(new Room("Dental Type 1"));
              detailInfoList.add(new Room("Dental Type 2"));
              break;
          }
          masterList.add(new ListRoom(arrName[i], detailInfoList));
          realm.copyToRealmOrUpdate(masterList);
        }
      }
    });
  }

  public static List<Account> fakeDataAccount(){
    List<Account> list = new ArrayList<>();
    list.add(new Account("Name", "Michael Jackson", R.drawable.account_locked,Account.ACCOUNT));
    list.add(new Account("Room", "2005", R.drawable.account_locked,Account.ACCOUNT));
    list.add(new Account("Email", "user1@test.com", R.drawable.transparent,Account.ACCOUNT_NOTIFY));
    list.add(new Account("Password", "111111", R.drawable.transparent,Account.ACCOUNT_NOTIFY));
    list.add(new Account("Phone", "111-2222-3333", R.drawable.transparent,Account.ACCOUNT_NOTIFY));
    list.add(new Account("Visa", "xxx-xxx-x012", R.drawable.transparent,Account.ACCOUNT_NOTIFY));
    list.add(new Account("Notifications", "On", R.drawable.transparent,Account.ACCOUNT_NOTIFY));
    return list;
  }

  public static List<Device> fakeDataDevices(){
      List<Device> list = new ArrayList<>();
      Random random = new Random();
      Random active = new Random();
      list.add(new Device(random.nextInt(3) + 1, "device 1", active.nextBoolean()));
      list.add(new Device(random.nextInt(3) + 1, "device 2", active.nextBoolean()));
      list.add(new Device(random.nextInt(3) + 1, "device 3", active.nextBoolean()));
      list.add(new Device(random.nextInt(3) + 1, "device 4", active.nextBoolean()));
      return list;
  }
  public static JSONObject initFakeData() {
    JSONArray jsonArray = new JSONArray();
    JSONObject coffeeObject = new JSONObject();
    try {
      coffeeObject.put("viewName", "coffee");
      coffeeObject.put("category", "Coffee & Brunch");
      coffeeObject.put("hasRating", true);
      coffeeObject.put("img1Path", R.drawable.coffee_brunch_1);

      coffeeObject.put("img1Name", "Zazie");
      coffeeObject.put("img1Rating", "4");
      coffeeObject.put("img1Type", "$$ - French Restaurant");
      coffeeObject.put("img1Desc", "Petite French bistro & popular brunch spot with an outdoor patio & weekly Bring Your Dog dinners.");
      coffeeObject.put("img1Address", "941 Cole St, San Francisco, CA 94117. Open today &#183; 8AM-2PM, 5-9:30PM (415) 564-5332");

      coffeeObject.put("img2Path", R.drawable.coffee_brunch_2);
      coffeeObject.put("img2Name", "Starbelly");
      coffeeObject.put("img2Rating", "4");
      coffeeObject.put("img2Type", "$$ - Californian Restaurant");
      coffeeObject.put("img2Desc", "Californian comfort food & drinks are served at this cafe, which also features an outdoor patio.");
      coffeeObject.put("img2Address", "3583 16th St, San Francisco, CA 94114. Open today - 11:30AM-11PM (415) 252-7500");

      coffeeObject.put("img3Path", R.drawable.coffee_brunch_3);
      coffeeObject.put("img3Name", "Sambrosa");
      coffeeObject.put("img3Rating", "3");
      coffeeObject.put("img3Type", "$$$ - Mexican Restaurant");
      coffeeObject.put("img3Desc", "Upscale Mexican dishes & cocktails are presented in stylish room with banquettes and a U-shaped bar.");
      coffeeObject.put("img3Address", "3200 Fillmore St, San Francisco, CA 94123. Open today - 4PM-2AM (415) 638-6500");

      coffeeObject.put("img4Path", R.drawable.coffee_brunch_4);
      coffeeObject.put("img4Name", "Sweet Maple");
      coffeeObject.put("img4Rating", "5");
      coffeeObject.put("img4Type", "$$ - Breakfast Restaurant");
      coffeeObject.put("img4Desc", "Local spot for homemade morning/lunch fare like signature brown sugar-glazed \"millionaire's bacon.\"");
      coffeeObject.put("img4Address", "2101 Sutter St, San Francisco, CA 94115. Open today - 8AM-3PM (415) 655-9169");

      coffeeObject.put("img5Path", R.drawable.coffee_brunch_5);
      coffeeObject.put("img5Name", "Blue Bottle");
      coffeeObject.put("img5Rating", "0");
      coffeeObject.put("img5Type", "$ - Coffee Shop");
      coffeeObject.put("img5Desc", "Blue Bottle company is a US coffee roaster and retailer headquartered in Oakland, California, considered a major player in third wave coffee. The Company focuses on single-origin beans.");
      coffeeObject.put("img5Address", "66 Mint St, San Francisco, CA 94103. Open today - 7AM-7PM (510) 653-3394");

      coffeeObject.put("totalResources", 5);

    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    JSONObject restaurantObj = new JSONObject();
    try {
      restaurantObj.put("viewName", "restaurants");
      restaurantObj.put("category", "Best SF Restaurants");
      restaurantObj.put("hasRating", true);

      restaurantObj.put("img1Path", R.drawable.restaurants_1);
      restaurantObj.put("img1Name", "20th Century Cake");
      restaurantObj.put("img1Rating", "5");
      restaurantObj.put("img1Type", "$ - Cafe");
      restaurantObj.put("img1Desc", "Chic, retro corner bakery featuring sweet & savory specialties from Vienna, Budapest, & Prague.");
      restaurantObj.put("img1Address", "198 Gough St, San Francisco, CA 94102. Open today - 8AM-7PM (415) 621-2380");

      restaurantObj.put("img2Path", R.drawable.restaurants_2);
      restaurantObj.put("img2Name", "Namu Gaji");
      restaurantObj.put("img2Rating", "5");
      restaurantObj.put("img2Type", "$$ - Asian Restaurant");
      restaurantObj.put("img2Desc", "An open restaurent turns out innovative dishes by way of local produce & Korean culinary traditions.");
      restaurantObj.put("img2Address", "499 Dolores St, San Francisco, CA 94110. Open today - 5-10PM (415) 431-6268");

      restaurantObj.put("img3Path", R.drawable.restaurants_3);
      restaurantObj.put("img3Name", "Pauline's Pizza");
      restaurantObj.put("img3Rating", "4");
      restaurantObj.put("img3Type", "$$ - Pizza Restaurant");
      restaurantObj.put("img3Desc", "Homegrown ingredients go into the pie & salads at this family friendly pizzeria with a wine room.");
      restaurantObj.put("img3Address", "260 Valencia St, San Francisco, CA 94103. Open today - 5-10PM (415) 552-2050");

      restaurantObj.put("img4Path", R.drawable.restaurants_4);
      restaurantObj.put("img4Name", "Rich Table");
      restaurantObj.put("img4Rating", "5");
      restaurantObj.put("img4Type", "$$$ - Californian Restaurant");
      restaurantObj.put("img4Desc", "Californian fare from local ingredients, served in a salvaged-barn-wood space with an open kitchen.");
      restaurantObj.put("img4Address", "199 Gough St, San Francisco, CA 94102. Open today - 5:30-10PM (415) 355-9085");

      restaurantObj.put("img5Path", R.drawable.restaurants_5);
      restaurantObj.put("img5Name", "Eiji");
      restaurantObj.put("img5Rating", "5");
      restaurantObj.put("img5Type", "$$ - Japanese Restaurant");
      restaurantObj.put("img5Desc", "Housemade Tofu is the specialty at this cozy Japanese spot that also serves a varied sushi menu.");
      restaurantObj.put("img5Address", "317 Sanchez St, San Francisco, CA 94114. Open today - 11:30AM-10PM (415) 558-8149");

      restaurantObj.put("totalResources", 5);

    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    JSONObject attractionObj = new JSONObject();
    try {
      attractionObj.put("viewName", "attractions");
      attractionObj.put("category", "Top 5 Attractions");
      attractionObj.put("hasRating", false);

      attractionObj.put("img1Path", R.drawable.attractions_1);
      attractionObj.put("img1Name", "Golden Gate Bridge");
      attractionObj.put("img1Rating", "");
      attractionObj.put("img1Type", "");
      attractionObj.put("img1Desc", "The Golden Gate Bridge is a suspension bridge spanning the Golden Gate strait, the one-mile-wide, three-mile-long channel between San Francisco Bay and the Pacific Ocean.");
      attractionObj.put("img1Address", "");

      attractionObj.put("img2Path", R.drawable.attractions_2);
      attractionObj.put("img2Name", "Russian Hill");
      attractionObj.put("img2Rating", "");
      attractionObj.put("img2Type", "");
      attractionObj.put("img2Desc", "On Russian Hill, Polk Street is crowded with unusual boutiques, antique shops, trendy restaurants and night spots.");
      attractionObj.put("img2Address", "");

      attractionObj.put("img3Path", R.drawable.attractions_3);
      attractionObj.put("img3Name", "Alcatraz Island");
      attractionObj.put("img3Rating", "");
      attractionObj.put("img3Type", "");
      attractionObj.put("img3Desc", "Alcatraz Island offers a close-up look at the site of the first lighthouse and US built fort on the West Coast, the infamous federal penitentiary long off-limits of the public, and the 18 months occupation by the Indians of All tribes which saved the tribes. Rich in history, there is also a natural side to the Rock - gardens, tide pools, bird colonies, and bay views beyond compare.");
      attractionObj.put("img3Address", "");

      attractionObj.put("img4Path", R.drawable.attractions_4);
      attractionObj.put("img4Name", "Hyde St. Pier");
      attractionObj.put("img4Rating", "");
      attractionObj.put("img4Type", "");
      attractionObj.put("img4Desc", "Everybody looks good at this dimly lit, wood-paneled spot, which quickly turned from a neighborhood favorite into a dining destination.");
      attractionObj.put("img4Address", "");

      attractionObj.put("img5Path", R.drawable.attractions_5);
      attractionObj.put("img5Name", "Pier 39");
      attractionObj.put("img5Rating", "");
      attractionObj.put("img5Type", "");
      attractionObj.put("img5Desc", "PIER 39 is a must on your list of your things to do in San Francisco, with sea lions, waterfront dining, street performers, live music, shopping and more.");
      attractionObj.put("img5Address", "");

      attractionObj.put("totalResources", 5);

    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    JSONObject hotelObj = new JSONObject();
    try {
      hotelObj.put("viewName", "hotel");
      hotelObj.put("category", "Hotel Exclusives");
      hotelObj.put("hasRating", false);

      hotelObj.put("img1Path", R.drawable.hotelexclusive_1);
      hotelObj.put("img1Name", "In-RoomItem Spa Treatment");
      hotelObj.put("img1Rating", "");
      hotelObj.put("img1Type", "");
      hotelObj.put("img1Desc", "Step into an urban oasis of water basalt rock, glass tile and signature rituals inspired by the rugged natural beauty  of the Northwest in our hotel. Reserver today for 50% off.");
      hotelObj.put("img1Address", "");

      hotelObj.put("img2Path", R.drawable.hotelexclusive_2);
      hotelObj.put("img2Name", "Bar Luxe");
      hotelObj.put("img2Rating", "");
      hotelObj.put("img2Type", "");
      hotelObj.put("img2Desc", "Sip exotic martinis, famous champagnes and ports, indulgent coffees and cocktails of every description while enjoying savory hors d'oeuvres and other tempting bites. Thursday through Saturday features live piano music during cocktail hour starting at 7:00pm. Complimentary drink for Hotel guests");
      hotelObj.put("img2Address", "");

      hotelObj.put("img3Path", R.drawable.hotelexclusive_3);
      hotelObj.put("img3Name", "Couples Package");
      hotelObj.put("img3Rating", "");
      hotelObj.put("img3Type", "");
      hotelObj.put("img3Desc", "Share champagne on the balcony, spa treatments, or room service and a movie. Amenities vary by location. Call us for details.");
      hotelObj.put("img3Address", "");

      hotelObj.put("img4Path", R.drawable.hotelexclusive_4);
      hotelObj.put("img4Name", "Players Club");
      hotelObj.put("img4Rating", "");
      hotelObj.put("img4Type", "");
      hotelObj.put("img4Desc", "It's free and easy, join today! Simply present a valid photo identification at the Diamond Dividends Players Club booth and we will immediately enroll you into our player rewards program. Hotel Guests receive $100 credit.");
      hotelObj.put("img4Address", "");

      hotelObj.put("img5Path", R.drawable.hotelexclusive_5);
      hotelObj.put("img5Name", "Luggage Liason");
      hotelObj.put("img5Rating", "");
      hotelObj.put("img5Type", "");
      hotelObj.put("img5Desc", "Shipping luggage in advance to a destination is the ultimate luxury travel experience. Luggage Liason, a suit of travel services, also includes luggage storage and unpacking/packing services upon request.");
      hotelObj.put("img5Address", "");

      hotelObj.put("totalResources", 5);

      //put all act object to array

      jsonArray.put(0, coffeeObject);
      jsonArray.put(1, restaurantObj);
      jsonArray.put(2, attractionObj);
      jsonArray.put(3, hotelObj);
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    //return result JSON object
    JSONObject foodActivitiesObj = new JSONObject();
    try {
      foodActivitiesObj.put("FoodObject", jsonArray);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    String testObject = foodActivitiesObj.toString();
    return foodActivitiesObj;
  }
}
