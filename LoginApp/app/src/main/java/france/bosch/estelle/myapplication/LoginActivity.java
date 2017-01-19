package france.bosch.estelle.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.concurrent.Callable;


public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
         CallbackManager mFacebookCallbackManager = CallbackManager.Factory.create();

        // This MUST be placed after the above two lines.
        setContentView(R.layout.activity_login);
        LoginButton mFacebookSignInButton = (LoginButton) findViewById(R.id.facebook_sign_in_button);
            mFacebookSignInButton.registerCallback(mFacebookCallbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(final LoginResult loginResult) {

                            handleSignInResult(new Callable<Void>() {
                                @Override
                                public Void call() throws Exception {
                                    LoginManager.getInstance().logOut();
                                    return null;
                                }
                            });
                        }

                        @Override
                        public void onCancel() {
                            handleSignInResult(null);
                        }

                        @Override
                        public void onError(FacebookException error) {
                            Log.d(LoginActivity.class.getCanonicalName(), error.getMessage());
                            handleSignInResult(null);
                        }
                    }
            );
        }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(Callable<Void> C) {

    }

}

