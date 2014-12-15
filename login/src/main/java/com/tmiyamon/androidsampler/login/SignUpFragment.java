package com.tmiyamon.androidsampler.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tmiyamon on 12/16/14.
 */
public class SignUpFragment extends AuthFragment {
    private TextView accountName;
    private TextView accountPassword;

    private String accountType;
    private String authTokenType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle args = getArguments();
        accountType = args.getString(AuthenticatorActivity.ARG_ACCOUNT_TYPE);
        authTokenType = args.getString(AuthenticatorActivity.ARG_AUTH_TOKEN_TYPE);

        View rootView = inflater.inflate(R.layout.fragment_login_signup, container, false);

        accountName = ((TextView) rootView.findViewById(R.id.accountName));
        accountPassword = ((TextView) rootView.findViewById(R.id.accountPassword));

        rootView.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        rootView.findViewById(R.id.alreadyMember).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSwitch(LoginFragment.class, getArguments());
            }
        });

        return rootView;
    }

    public void submit() {

        final String name = accountName.getText().toString();
        final String password = accountPassword.getText().toString();

        Map<String, String> body = new HashMap<String, String>();
        body.put("username", name);
        body.put("password", password);

        ParseClient.instance.signup(
                body, new AuthCallback(name, password, accountType, authTokenType, false));
    }


}
