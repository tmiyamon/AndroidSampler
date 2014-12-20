package com.tmiyamon.androidsampler.login;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity implements OnReloadListener {
    public static final String APP_NAME = "Login Parse";
    public static final String APP_DESCRIPTION = "A login sample using AccountManager for Parse REST-API";

    public static final String ACCOUNT_TYPE = "com.tmiyamon.parse";
    public static final String AUTH_TOKEN_TYPE = "general";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_container);

        getFragmentManager().beginTransaction()
                .add(R.id.container, new MainFragment())
                .commit();

    }

    @Override
    public void onReload() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final AccountManager accountManager = AccountManager.get(this);
        final Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
        if (accounts.length == 0) {
            accountManager.addAccount(ACCOUNT_TYPE, AUTH_TOKEN_TYPE, null, null, this, null, null);
        } else {
            accountManager.getAuthToken(accounts[0], AUTH_TOKEN_TYPE, null, this, null, null);
        }
    }

    /**
     * Created by tmiyamon on 12/16/14.
     */
    public static class MainFragment extends Fragment {
        private OnReloadListener listener;

        public MainFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_login_main, container, false);

            rootView.findViewById(R.id.btnLogut).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Account account = AccountManager.get(getActivity()).getAccountsByType(ACCOUNT_TYPE)[0];
                    AccountManager.get(getActivity()).removeAccount(account, new AccountManagerCallback<Boolean>() {
                        @Override
                        public void run(AccountManagerFuture<Boolean> future) {
                            listener.onReload();
                        }
                    }, null);
                }
            });

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            if (activity instanceof OnReloadListener) {
                this.listener = (OnReloadListener)activity;
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
            this.listener = null;
        }
    }
}

interface OnReloadListener {
    public void onReload();
}
