package com.tmiyamon.androidsampler.login;

import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

/**
 * The Authenticator activity.
 *
 * Called by the Authenticator and in charge of identifing the user.
 *
 * It sends back to the Authenticator the result.
 */
public class AuthenticatorActivity extends AccountAuthenticatorActivity
        implements AccountGeneral.OnAuthListener {

    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TOKEN_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";

    //public static final String KEY_ERROR_MESSAGE = "ERR_MSG";

    public final static String PARAM_USER_PASS = "USER_PASS";

    private final int REQ_SIGNUP = 1;

    private final String TAG = this.getClass().getSimpleName();

    private AccountManager mAccountManager;
    private String mAuthTokenType;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_login);
//        mAccountManager = AccountManager.get(getBaseContext());
//
//        mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TOKEN_TYPE);
//        if (mAuthTokenType == null)
//            mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;



        Fragment loginFragment = new LoginFragment();
        loginFragment.setArguments(getIntent().getExtras());

        getFragmentManager()
                .beginTransaction()
                .add(R.id.container, loginFragment)
                .commit();

//        String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
//        if (accountName != null) {
//            ((TextView)findViewById(R.id.accountName)).setText(accountName);
//        }
//
//        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                submit();
//            }
//        });
//        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Since there can only be one AuthenticatorActivity, we call the sign up activity, get his results,
//                // and return them in setAccountAuthenticatorResult(). See finishLogin().
//                Intent signup = new Intent(getBaseContext(), SignUpActivity.class);
//                signup.putExtras(getIntent().getExtras());
//                startActivityForResult(signup, REQ_SIGNUP);
//            }
//        });
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

    //
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        // The sign up activity returned that the user has successfully created an account
//        if (requestCode == REQ_SIGNUP && resultCode == RESULT_OK) {
//            finishLogin(data);
//        } else
//            super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    public void submit() {
//
//        final String userName = ((TextView) findViewById(R.id.accountName)).getText().toString();
//        final String userPass = ((TextView) findViewById(R.id.accountPassword)).getText().toString();
//
//        final String accountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);
//
//        new AsyncTask<String, Void, Intent>() {
//
//            @Override
//            protected Intent doInBackground(String... params) {
//                String authtoken = null;
//                Bundle data = new Bundle();
//                try {
//                    authtoken = sServerAuthenticate.userSignIn(userName, userPass, mAuthTokenType);
//
//                    data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
//                    data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
//                    data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
//                    data.putString(PARAM_USER_PASS, userPass);
//
//                } catch (Exception e) {
//                    data.putString(KEY_ERROR_MESSAGE, e.getMessage());
//                }
//
//                final Intent res = new Intent();
//                res.putExtras(data);
//                return res;
//            }
//
//            @Override
//            protected void onPostExecute(Intent intent) {
//                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
//                    Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
//                } else {
//                    finishLogin(intent);
//                }
//            }
//        }.execute();
//    }
//
//    private void finishLogin(Intent intent) {
//        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
//        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
//        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
//
//        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
//            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
//            String authtokenType = mAuthTokenType;
//
//            // Creating the account on the device and setting the auth token we got
//            // (Not setting the auth token will cause another call to the server to authenticate the user)
//            mAccountManager.addAccountExplicitly(account, accountPassword, null);
//            mAccountManager.setAuthToken(account, authtokenType, authtoken);
//        } else {
//            mAccountManager.setPassword(account, accountPassword);
//        }
//
//        setAccountAuthenticatorResult(intent.getExtras());
//        setResult(RESULT_OK, intent);
//        finish();
//    }

}
