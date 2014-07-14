package org.kamol.shopafter;

import android.content.Context;

import com.squareup.otto.Produce;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import org.kamol.shopafter.bus.BusProvider;
import org.kamol.shopafter.event.ActivityResultEvent;
import org.kamol.shopafter.event.FragmentEvent;
import org.kamol.shopafter.ui.activity.GenericActivity;
import org.kamol.shopafter.ui.activity.MainActivity;
import org.kamol.shopafter.ui.fragment.InsertAdContainerFragment;
import org.kamol.shopafter.ui.fragment.InsertAdFragment;
import org.kamol.shopafter.ui.fragment.ListingFragment;
import org.kamol.shopafter.ui.fragment.MainFragment;
import org.kamol.shopafter.ui.fragment.MyAdsFragment;
import org.kamol.shopafter.ui.fragment.ProfileContainerFragment;
import org.kamol.shopafter.ui.fragment.SignInFragment;
import org.kamol.shopafter.ui.fragment.SignupFragment;
import org.kamol.shopafter.ui.gallery.MyAdsView;

/**
 * This module represents objects which exist only for the scope of a single activity.
 * We can safely create singletons using the activity instance because the entire
 * object graph will only ever exist inside of that activity.
 */
@Module(
    injects = {
        MainActivity.class,
        MainFragment.class,
        ListingFragment.class,
        InsertAdFragment.class,
        InsertAdContainerFragment.class,
        ProfileContainerFragment.class,
        ActivityResultEvent.class,

        MyAdsView.class,
        MyAdsFragment.class,

        GenericActivity.class,
        SignupFragment.class,
        SignInFragment.class,
        FragmentEvent.class //XXX do we need it?
    },
    addsTo = AndroidModule.class,
    library = true
)
public class ActivityModule {
  private final BaseActivity activity;

  public ActivityModule(BaseActivity activity) {
    this.activity = activity;
  }

  /**
   * Allow the activity context to be injected but require that it be annotated with
   * {@link ForActivity @ForActivity} to explicitly differentiate it from application context.
   */
  @Provides @Singleton @ForActivity Context provideActivityContext() {
    return activity;
  }

  @Produce public FragmentEvent produceFragment() {
    return new FragmentEvent("SignupFragment");
  }
}
