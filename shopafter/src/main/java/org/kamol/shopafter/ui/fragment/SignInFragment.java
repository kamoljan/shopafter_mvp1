package org.kamol.shopafter.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import org.kamol.shopafter.BaseFragment;
import org.kamol.shopafter.R;
import org.kamol.shopafter.event.FragmentEvent;
import org.kamol.shopafter.event.RefreshEvent;
import org.kamol.shopafter.ui.activity.GenericActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import timber.log.Timber;

public class SignInFragment extends BaseFragment {
  static final String TAG = "SignInFragment";
  public final static String EXTRA_FRAGMENT = "org.kamol.shopafter.FRAGMENT";
  @Inject Bus bus;
  @InjectView(R.id.b_facebook_login) Button btnLogin;
  @InjectView(R.id.b_signup) Button btnSignup;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_signin, container, false);
    ButterKnife.inject(this, view);
    return view;
  }

  @OnClick(R.id.b_facebook_login) public void onClickBtnLogin() {
    List<String> permissions = Arrays.asList("email");
    ParseFacebookUtils.logIn(permissions, getActivity(), new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException err) {
        if (user == null) {
          Timber.d("Uh oh. The user cancelled the Facebook login.");
        } else if (user.isNew()) {
          Timber.d("User signed up and logged in through Facebook!");
        } else {
          Timber.d("User logged in through Facebook!");
        }
      }
    });
  }

  @OnClick(R.id.b_signup) public void onClickBtnSignup() {
    bus.post(new FragmentEvent("SignupFragment"));
    Intent intent = new Intent(getActivity(), GenericActivity.class);
    //intent.putExtra(EXTRA_FRAGMENT, "SignupFragment");
    startActivity(intent);

//    produceTitle();
  }

  private void destroyFragment() {
    getChildFragmentManager().beginTransaction().hide(this).commit();
  }

  /*
  private void logInInBackground(String username, String password) {
      ParseUser.logInInBackground(username, password, new LogInCallback() {
          public void done(ParseUser user, ParseException e) {
              if (user != null) {
                  Timber.i("Hooray! The user is logged in.");
              } else {
                  Timber.i("Signup failed. Look at the ParseException to see what happened.");
              }
          }
      });
  }
  */

//  @Override public void onResume() {
//    super.onResume();
//    bus.register(this);
//  }
//
//  @Override public void onPause() {
//    super.onPause();
//    bus.unregister(this);
//  }
}
