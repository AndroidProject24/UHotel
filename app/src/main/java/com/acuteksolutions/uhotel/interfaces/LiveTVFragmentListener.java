package com.acuteksolutions.uhotel.interfaces;

/**
 * Created by huynhvantoan on 11/23/16.
 * Email huynhvantoan.itc@gmail.com
 */

public interface LiveTVFragmentListener {
    //void updateTitleBar(Program program, int cellPos);

    void onToRight(int index, int rowIndex);

    void onToLeft(int index, int rowIndex);

}
