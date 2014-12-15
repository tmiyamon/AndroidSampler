package com.tmiyamon.androidsampler.login;

import android.accounts.AccountAuthenticatorActivity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

public class AuthenticatorActivity extends AccountAuthenticatorActivity
        implements AccountGeneral.OnAuthListener {

    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TOKEN_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";

    public final static String PARAM_USER_PASS = "USER_PASS";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_login);

        Fragment loginFragment = new LoginFragment();
        loginFragment.setArguments(getIntent().getExtras());

        getFragmentManager()
                .beginTransaction()
                .add(R.id.container, loginFragment)
                .commit();
    }

    @Override
    public void onAuthFinished(Intent intent) {
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onSwitch(Class<? extends AuthFragment> fragment, Bundle args) {
        try {

            Fragment newFragment = fragment.newInstance();
            newFragment.setArguments(args);

            getFragmentManager().beginTransaction()
                    .replace(R.id.container, newFragment)
                    .commit();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
