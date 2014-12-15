package com.tmiyamon.androidsampler.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
* Created by tmiyamon on 12/16/14.
*/
public class LoginFragment extends AuthFragment {

    private TextView accountName;
    private TextView accountPassword;

    private String accountType;
    private String authTokenType;
    private boolean isNewAccount;

    public LoginFragment() { }

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
        rootView.findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSwitch(SignUpFragment.class, getArguments());
            }
        });

        return rootView;
    }

    public void submit() {

        final String name = accountName.getText().toString();
        final String password = accountPassword.getText().toString();

        ParseComServerAuthenticate.parse.login(
                name, password,
                new AuthCallback(name, password, accountType, authTokenType, isNewAccount));
    }
}
