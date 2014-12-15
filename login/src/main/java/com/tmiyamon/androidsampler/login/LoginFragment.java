package com.tmiyamon.androidsampler.login;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
* Created by tmiyamon on 12/16/14.
*/
public class LoginFragment extends Fragment {
    public static interface OnAuthFinishListener {
        public void onAuthFinished(Intent intent);
    }

    private TextView accountName;
    private TextView accountPassword;

    private String accountType;
    private String authTokenType;
    private boolean isNewAccount;

    private OnAuthFinishListener listener;

    public LoginFragment() { }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnAuthFinishListener) {
            this.listener = (OnAuthFinishListener)activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        String accountName = args.getString(AuthenticatorActivity.ARG_ACCOUNT_NAME);
        this.accountType = args.getString(AuthenticatorActivity.ARG_ACCOUNT_TYPE);
        this.authTokenType = args.getString(AuthenticatorActivity.ARG_AUTH_TOKEN_TYPE);
        this.isNewAccount = args.getBoolean(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, false);

        View rootView = inflater.inflate(R.layout.fragment_login_login, container, false);
        this.accountName = (TextView)rootView.findViewById(R.id.accountName);
        this.accountPassword = (TextView) rootView.findViewById(R.id.accountPassword);

        this.accountName.setText(accountName);
        rootView.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
//            rootView.findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Since there can only be one AuthenticatorActivity, we call the sign up activity, get his results,
//                    // and return them in setAccountAuthenticatorResult(). See finishLogin().
//                    Intent signup = new Intent(getBaseContext(), SignUpActivity.class);
//                    signup.putExtras(getIntent().getExtras());
//                    startActivityForResult(signup, REQ_SIGNUP);
//                }
//            });

        return rootView;
    }

    public void submit() {

        final String name = accountName.getText().toString();
        final String password = accountPassword.getText().toString();

        ParseComServerAuthenticate.parse.login(name, password, new Callback<Map<String, String>>() {
            @Override
            public void success(Map<String, String> map, Response response) {
                final String authToken = map.get("sessionToken");

                final Account account = new Account(name, accountType);
                final AccountManager accountManager = AccountManager.get(getActivity());

                if (isNewAccount) {
                    accountManager.addAccountExplicitly(account, password, null);
                    accountManager.setAuthToken(account, authTokenType, authToken);
                } else {
                    accountManager.setPassword(account, password);
                }

                Bundle data = new Bundle();
                data.putString(AccountManager.KEY_ACCOUNT_NAME, name);
                data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                data.putString(AccountManager.KEY_AUTHTOKEN, authToken);
                data.putString(AuthenticatorActivity.PARAM_USER_PASS, password);

                final Intent intent = new Intent();
                intent.putExtras(data);

                listener.onAuthFinished(intent);
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }
}
