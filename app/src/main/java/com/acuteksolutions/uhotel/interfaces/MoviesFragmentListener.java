package com.acuteksolutions.uhotel.interfaces;

import android.view.View;

import com.acuteksolutions.uhotel.mvp.model.data.VODInfo;
/**
 * Created by huynhvantoan on 11/23/16.
 * Email huynhvantoan.itc@gmail.com
 */

public interface MoviesFragmentListener {
	void getPositonFist();

	void getPositonLast();

	void getPositon();

	void scrollFist();

	void showInfo(int position);

	void keyUp();

	void onClick(VODInfo vodInfo);

	View getMainLayout();

	void focusFistItem();

	void pausePlayer(boolean isPause);
}
