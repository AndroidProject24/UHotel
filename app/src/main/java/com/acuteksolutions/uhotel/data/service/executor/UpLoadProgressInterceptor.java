package com.acuteksolutions.uhotel.data.service.executor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Toan.IT
 * Created by vantoan on 3/2/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class UpLoadProgressInterceptor implements Interceptor {
  private CountingRequestBody.Listener progressListener;

  public UpLoadProgressInterceptor(CountingRequestBody.Listener progressListener) {
    this.progressListener = progressListener;
  }

  @Override public Response intercept(Chain chain) throws IOException {
    Request originalRequest = chain.request();

    if (originalRequest.body() == null) {
      return chain.proceed(originalRequest);
    }

    Request progressRequest = originalRequest.newBuilder()
            .method(originalRequest.method(),
                    new CountingRequestBody(originalRequest.body(), progressListener))
            .build();

    return chain.proceed(progressRequest);
  }
}