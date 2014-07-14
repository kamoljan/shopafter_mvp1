package org.kamol.shopafter.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.kamol.shopafter.BaseActivity;
import org.kamol.shopafter.R;
import org.kamol.shopafter.event.FragmentEvent;
import org.kamol.shopafter.ui.fragment.SignupFragment;

import javax.inject.Inject;

public class GenericActivity extends BaseActivity {
  @Inject Bus bus;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_generic);

    bus.register(this);

    bus.post(new FragmentEvent("SignupFragment"));

//    // Check that the activity is using the layout version with
//    // the fragment_container FrameLayout
//    if (findViewById(R.id.fragment_container) != null) {
//
//      // However, if we're being restored from a previous state,
//      // then we don't need to do anything and should return or else
//      // we could end up with overlapping fragments.
//      if (savedInstanceState != null) {
//        return;
//      }
//
//      SignupFragment firstFragment = new SignupFragment();
//
//      // In case this activity was started with special instructions from an
//      // Intent, pass the Intent's extras to the fragment as arguments
//      firstFragment.setArguments(getIntent().getExtras());
//
//      getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
//    }
  }

  @Override public void onResume() {
    super.onResume();

  }

  @Override public void onPause() {
    super.onPause();
    bus.unregister(this);
  }

  @Subscribe public void onFragmentEvent(FragmentEvent event) {
    SignupFragment firstFragment = new SignupFragment();
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

    // Replace whatever is in the fragment_container view with this fragment,
    // and add the transaction to the back stack so the user can navigate back
    transaction.replace(R.id.fragment_container, firstFragment);
    transaction.addToBackStack(null);

    // Commit the transaction
    transaction.commit();
  }
}
