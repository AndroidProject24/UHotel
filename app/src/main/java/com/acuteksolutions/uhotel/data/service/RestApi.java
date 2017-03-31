package com.acuteksolutions.uhotel.data.service;

import com.acuteksolutions.uhotel.mvp.model.JsonObject;
import com.acuteksolutions.uhotel.mvp.model.JsonString;
import com.acuteksolutions.uhotel.mvp.model.login.Login;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Toan.IT
 * Date: 28/05/2016
 */

public interface RestApi {
  String BASE_URL = "http://lv-api.acuteksolutions.com/cxf/ws/messagebus/rest/";

  //Service Path
  public static final String ACUCORE_SERVICE_PATH = "/cxf/ws/messagebus/rest/request/{mac}/Beesmart?path=";
  public static final String ACUCORE_AUTHORIZE_SERVICE_PATH = "/cxf/ws/messagebus/rest/authorize/Beesmart/AuthorizeService";
  public static final String ACUCORE_PIN_SERVICE_PATH = "check/Beesmart/PinCodeService";

  //Movies
  public static final String VOD_IMAGE_URL = "http://bsdev.acuteksolutions.com/restapi/rest/{regionId}/images/";
  public static final String VOD_INFO_PATH = "/vod/info/{id}";
  public static final String VOD_LIST_INFO_PATH = "/vod/info?purchase_item_list={id_list}";

  public static final String MOVIE_BY_CONTENT_ID_PATH = "/restapi/rest/{region_uid}/content/media?include_media_resources=true&content_info_id={cid}";

  public static final String VOD_CATEGORIES_PATH = "/restapi/rest/{region_uid}/store/categories";
  public static final String VOD_BY_CATEGORY_ID_PATH = "/restapi/rest/{region_uid}/store/products?purchase_category_id={catId}";

  //EPG
  public static final String ALL_CHANNELS_PATH = "/restapi/rest/{region_uid}/channels";
  public static final String ALL_CHANNELS_PROGRAM_PATH = "/restapi/rest/{region_uid}/tvprogram?date={date}";
  public static final String CHANNEL_PROGRAM_BY_ID_PATH = "/restapi/rest/{region_uid}/tvprogram?channel_id={channel_id}&date={date}&page_size=2000";
  public static final String MANUAL_LOGIN_PATH = "/{mac}/{pin}?region={region_name}&operator={operator_name}";

  //Parental Control
  public static final String AUTHENTICATE_PARENTAL_PIN_PATH = "/{mac}/{profileUid}/{action}?parentalPin={parental_pin}";
  public static final String UPDATE_PARENTAL_CONTROL_SETTINGS_PATH = "/{mac}/{profileId}/update?data={parental_settings}&parentalPin={parental_pin}";
  public static final String CHANGE_PARENTAL_CONTROL_PIN_PATH = "/{mac}/{profileId}/changepin?data={new_pin}&parentalPin={old_pin}";

  /*Login*/
  @POST("authorize/Beesmart/AuthorizeService/{mac}/{pin}")
  Observable<JsonObject<Login>> getLogin(@Path("mac") String mac, @Path("pin") String pin, @Query("region") String region_name, @Query("operator") String operator_name);

  /*Movies*/
  @GET("request/{mac}/Beesmart")
  Observable<JsonString<String>> getPathMovies(@Path("mac") String mac, @Query("path") String path);

}
