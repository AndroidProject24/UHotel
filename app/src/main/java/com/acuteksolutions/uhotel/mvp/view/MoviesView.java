package com.acuteksolutions.uhotel.mvp.view;

import com.acuteksolutions.uhotel.mvp.model.movies.Category;
import com.acuteksolutions.uhotel.mvp.model.movies.VODInfo;
import com.acuteksolutions.uhotel.mvp.view.base.BaseView;

import java.util.List;

/**
 * Created by Toan.IT
 * Date: 28/05/2016
 */
public interface MoviesView extends BaseView {

    void listCategory(List<Category> categoryList);

    void listMovies(List<VODInfo> moviesList);

    void playStream(String title,String channel,String linkStream);

    void showEmty();

    void linkStreamError();
}
