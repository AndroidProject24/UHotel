package com.acuteksolutions.uhotel.mvp.view.movies;

import com.acuteksolutions.uhotel.mvp.model.data.VODInfo;
import com.acuteksolutions.uhotel.mvp.view.base.BaseView;

import java.util.List;

/**
 * Created by Toan.IT
 * Date: 28/05/2016
 */
public interface MoviesView extends BaseView {

    void listMovies(List<VODInfo> moviesList);
}
