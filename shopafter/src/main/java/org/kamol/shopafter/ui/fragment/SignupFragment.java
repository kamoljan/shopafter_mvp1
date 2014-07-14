package org.kamol.shopafter.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.kamol.shopafter.BaseFragment;
import org.kamol.shopafter.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import timber.log.Timber;

public class SignupFragment extends BaseFragment {

  @InjectView(R.id.et_name) EditText etName;
  @InjectView(R.id.et_email) EditText etEmail;
  @InjectView(R.id.et_password) EditText etPassword;
  @InjectView(R.id.b_parse_signup) Button btnParseSignup;

  public static SignupFragment newInstance() {
    return new SignupFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_signup, container, false);
    ButterKnife.inject(this, view);
    getActivity().setTitle("Sing Up with Email");
    return view;
  }

  @OnClick(R.id.b_parse_signup) public void onClickBtnParseSignup() {
    boolean isOk = true;
    if (etName.getText() == null || etName.getText().length() == 0) {
      etName.setError("What is your name?");
      isOk = false;
    }
    if (etEmail.getText() == null || etEmail.getText().length() == 0) {
      etEmail.setError("What is your email?");
      isOk = false;
    }
    if (etPassword.getText() == null || etPassword.getText().length() == 0) {
      etPassword.setError("Please enter your password");
      isOk = false;
    }
    if (isOk) {
      final ParseUser user = new ParseUser();
      user.setUsername(etName.getText().toString());
      user.setEmail(etEmail.getText().toString());
      user.setPassword(etPassword.getText().toString());
      user.signUpInBackground(new SignUpCallback() {
        public void done(ParseException e) {
          if (e == null) {
            Timber.i("A new account created :) ");
            Toast.makeText(getActivity(), "A new account is created :) ", Toast.LENGTH_LONG).show();
            getActivity().finish(); // close this activity
          } else {
            Timber.e(e, "Sign up didn't succeed. Look at ParseException for details");
            Toast.makeText(getActivity(), "Please, check your network connection", Toast.LENGTH_LONG).show();
            Toast.makeText(getActivity(), "The email is already in our system", Toast.LENGTH_LONG).show();
          }
        }
      });
    }
  }
}