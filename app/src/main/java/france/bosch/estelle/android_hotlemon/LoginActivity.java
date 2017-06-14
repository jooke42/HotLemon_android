package france.bosch.estelle.android_hotlemon;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import france.bosch.estelle.android_hotlemon.App.AppController;
import france.bosch.estelle.android_hotlemon.Class.Utilisateur;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final String LOGIN_URL = "http://82.232.20.224/rest-auth/login/";
    private static final int REQUEST_SIGNUP = 0;

    private String key = "";

    @InjectView(R.id.input_username) EditText _usernameText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup Activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.layout.loading_screen_layout);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // Scrap user short credentials
        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        // // Set new Utilisateur to be converted as JSON Object
        Utilisateur user = new Utilisateur();
        user.setUsername(username);
        user.setPassword(password);

        HashMap<String, String> params = new HashMap<>();
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());

        // TODO : Implement my own authentication logic here
        // (HTTP Request with Django & REST API)
        // Try to send a POST HTTP Request with previous JSONObject in parameter
        JsonObjectRequest req = new JsonObjectRequest(LOGIN_URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            key = response.getString("key");
                            AppController.getInstance().setKeyToken(key);
                            VolleyLog.v("Response:%n %s", response.toString(4));
                            Log.v("Response:%n %s", response.toString(4));
                            Log.v("Key: %s", AppController.getInstance().getKeyToken());
                            onLoginSuccess();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Log.e("Error: ", error.getMessage());
                onLoginFailed();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        // add the request object to the queue to be executed
        AppController.getInstance().addToRequestQueue(req);
        progressDialog.dismiss();

        // WUT ?
        /*new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // On complete call either onLoginSuccess or onLoginFailed
                onLoginSuccess();
                // onLoginFailed();
                progressDialog.dismiss();
            }
        }, 3000);*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP && resultCode == RESULT_OK) {

            // TODO : Implement successful signup logic here
            // By default we just finish the Activity and log them in automatically
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty() /*|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()*/) {
            _usernameText.setError("Enter a valid username.");
            valid = false;
        }
        else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 10) {
            _passwordText.setError("Your password must be between 6 and 10 alphanumeric characters.");
            valid = false;
        }
        else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
