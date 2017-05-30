package com.acuteksolutions.uhotel.data.repository;

import com.acuteksolutions.uhotel.mvp.model.movies.Category;
import com.acuteksolutions.uhotel.mvp.model.movies.VODInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by vantoan on 2/22/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public interface DataSource {
    Observable<List<Category>> getCategory();

    Observable<List<VODInfo>> getListMovies(String idList,String categoryID);

    Observable<List<VODInfo>> getMoviesDetails(String categoryID);

/*
  Observable<List<RealmFeedItem>> getListHome(@NonNull Item url);

  Observable<RealmFeedItem> getObject(@NonNull Item FeedId);

  void saveListHome(@NonNull RealmChannel realmFeedItem);

  void completeTask(@NonNull Task task);

  void completeTask(@NonNull Item taskId);

  void activateTask(@NonNull Task task);

  void activateTask(@NonNull Item taskId);

  void clearCompletedTasks();

  void refreshTasks();

  void deleteAllTasks();

  void deleteTask(@NonNull Item taskId);*/
}
