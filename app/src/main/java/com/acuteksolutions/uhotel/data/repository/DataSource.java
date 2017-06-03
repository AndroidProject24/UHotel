package com.acuteksolutions.uhotel.data.repository;

import com.acuteksolutions.uhotel.mvp.model.movies.Category;
import com.acuteksolutions.uhotel.mvp.model.movies.VODInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by vantoan on 2/22/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public interface DataSource {
    Observable<List<Category>> getCategory();

    Observable<List<VODInfo>> getListMovies(String categoryID);

    Observable<List<VODInfo>> getMoviesFromCategory(String idList, String categoryID);

    void clear();
}
