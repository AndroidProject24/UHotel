package com.acuteksolutions.uhotel.injector.component;

import com.acuteksolutions.uhotel.injector.module.ActivityModule;
import com.acuteksolutions.uhotel.injector.qualifier.PerActivity;
import com.acuteksolutions.uhotel.ui.activity.MainActivity;
import com.acuteksolutions.uhotel.ui.fragment.food.FoodFragment;
import com.acuteksolutions.uhotel.ui.fragment.landing.LandingFragment;
import com.acuteksolutions.uhotel.ui.fragment.liveTV.LiveTVFragment;
import com.acuteksolutions.uhotel.ui.fragment.login.LoginFragment;
import com.acuteksolutions.uhotel.ui.fragment.movies.ListMoviesFragment;
import com.acuteksolutions.uhotel.ui.fragment.movies.MoreMoviesFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

  void inject(MainActivity mainActivity);

  void inject(LandingFragment landingFragment);

  /*LOGIN*/
  void inject(LoginFragment loginFragment);

  /*void inject(RegisterFragment registerFragment);*/


  /*LIVETV*/
  void inject(LiveTVFragment liveTVFragment);

  /*MOVIES*/
  void inject(ListMoviesFragment listMoviesFragment);

  void inject(MoreMoviesFragment moreMoviesFragment);

  /*FOOD*/
  void inject(FoodFragment foodFragment);
}