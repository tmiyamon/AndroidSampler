package com.tmiyamon.androidsampler.login;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tmiyamon on 12/16/14.
 */
public class AuthFragment extends Fragment {
    protected AccountGeneral.OnAuthListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AccountGeneral.OnAuthListener) {
            this.listener = (AccountGeneral.OnAuthListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    public class AuthCallback implements Callback<Map<String, String>> {
        private String name;
        private String password;
        private String accountType;
        private String authTokenType;
        private boolean isNewAccount;

        public AuthCallback(String name, String password, String accountType, String authTokenType, boolean isNewAccount) {
            this.name = name;
            this.password = password;
            this.accountType = accountType;
            this.authTokenType = authTokenType;
            this.isNewAccount = isNewAccount;
        }

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
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    error.getBody().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
