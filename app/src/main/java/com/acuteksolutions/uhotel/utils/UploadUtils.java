package com.acuteksolutions.uhotel.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Toan.IT
 * Created by vantoan on 3/1/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class UploadUtils {
  @NonNull
  public static RequestBody createPartFromString(String descriptionString) {
    return RequestBody.create(MultipartBody.FORM, descriptionString);
  }

  /*@NonNull
  public static MultipartBody.Part prepareFilePart(Context mContext, Item partName, Uri fileUri) {
    File file = FileUtils.getFile(mContext, fileUri);
    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
    return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
  }*/

  @NonNull
  public static File prepareFilePart(Context mContext, Uri fileUri) {
    return FileUtils.getFile(mContext, fileUri);
  }
}
