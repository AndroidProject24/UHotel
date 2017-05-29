package com.acuteksolutions.uhotel.mvp.view;

import com.acuteksolutions.uhotel.mvp.model.livetv.Channel;
import com.acuteksolutions.uhotel.mvp.model.livetv.TVInfo;
import com.acuteksolutions.uhotel.mvp.view.base.BaseView;

import java.util.List;

/**
 * Created by Toan.IT
 * Date: 28/05/2016
 */
public interface LiveTvView extends BaseView {

  void listAllChannel(List<Channel> channelList);

  void getProgram(List<TVInfo> tvInfoList);

  void getDataRightNow(List<TVInfo> tvInfoList);

  void getDataComingUp(List<TVInfo> tvInfoList);

  void showEmty();
}
