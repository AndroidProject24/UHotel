package com.acuteksolutions.uhotel.injector.component;

import com.acuteksolutions.uhotel.injector.module.ActivityModule;
import com.acuteksolutions.uhotel.injector.qualifier.PerActivity;
import com.acuteksolutions.uhotel.ui.activity.LoginActivity;
import com.acuteksolutions.uhotel.ui.dialog.LockDialog;
import com.acuteksolutions.uhotel.ui.dialog.PinChangeDialog;
import com.acuteksolutions.uhotel.ui.dialog.PinVerifyDialog;
import com.acuteksolutions.uhotel.ui.fragment.concierge.ParentalFragment;
import com.acuteksolutions.uhotel.ui.fragment.concierge.RoomFragment;
import com.acuteksolutions.uhotel.ui.fragment.food.ListFoodFragment;
import com.acuteksolutions.uhotel.ui.fragment.landing.LandingFragment;
import com.acuteksolutions.uhotel.ui.fragment.liveTV.LiveTVFragment;
import com.acuteksolutions.uhotel.ui.fragment.movies.ListMoviesFragment;
import com.acuteksolutions.uhotel.ui.fragment.movies.MoreMoviesFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

  void inject(LoginActivity loginActivity);

  void inject(LandingFragment landingFragment);

  /*CONCIERGE*/
  void inject(RoomFragment roomFragment);

  void inject(ParentalFragment parentalFragment);

  /*LIVETV*/
  void inject(LiveTVFragment liveTVFragment);

  /*MOVIES*/
  void inject(ListMoviesFragment listMoviesFragment);

  void inject(MoreMoviesFragment moreMoviesFragment);

  /*FOOD*/
  void inject(ListFoodFragment listFoodFragment);

  /*DIALOG*/
  void inject(LockDialog lockDialog);

  void inject(PinVerifyDialog pinVerifyDialog);

  void inject(PinChangeDialog pinChangeDialog);
}