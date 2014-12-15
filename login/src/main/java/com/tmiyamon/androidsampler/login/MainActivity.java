package com.tmiyamon.androidsampler.login;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

public class MainActivity extends Activity implements OnReloadListener {
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

    /**
     * Created by tmiyamon on 12/16/14.
     */
    public static class MainFragment extends Fragment {
        private OnReloadListener listener;

        public MainFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_login_main, container, false);

            final AccountManager accountManager = AccountManager.get(getActivity());
            final Account[] accounts = accountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);
            if (accounts.length == 0) {
                accountManager.addAccount(AccountGeneral.ACCOUNT_TYPE, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, null, null, getActivity(), null, null);
            } else {
                accountManager.getAuthToken(accounts[0], AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, null, getActivity(), null, null);
            }

            rootView.findViewById(R.id.btnInvalidateAuthToken).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Account account = accountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
                    accountManager.getAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, null, getActivity(), new AccountManagerCallback<Bundle>() {
                        @Override
                        public void run(AccountManagerFuture<Bundle> future) {
                            try {
                                final String authToken = future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
                                accountManager.invalidateAuthToken(account.type, authToken);
                                accountManager.clearPassword(account);
                                listener.onReload();
                            } catch (OperationCanceledException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (AuthenticatorException e) {
                                e.printStackTrace();
                            }
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
