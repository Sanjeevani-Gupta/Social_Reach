package com.example.socialmediaintegration;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.util.Arrays.*;

public class FacebookLogin extends AppCompatActivity {

    private TextView name, email;
    private CircleImageView profile;
    private LoginButton login;
    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);

        name = (TextView) findViewById(R.id.name_textView);
        email = (TextView) findViewById(R.id.email_textView);
        profile = (CircleImageView) findViewById(R.id.profile_image);
        login = (LoginButton) findViewById(R.id.facebook_login_button);
        checkLoginStatus();



        callbackManager = CallbackManager.Factory.create();
        login.setReadPermissions(Arrays.asList("email","public_profile"));


        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }


        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                name.setText("");
                email.setText("");
                profile.setImageResource(0);
                Toast.makeText(FacebookLogin.this, "User Logged Out", Toast.LENGTH_LONG).show();
            } else
                LoaduserProfile(currentAccessToken);
        }
    };

    private void LoaduserProfile(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {

                    String First_name = object.getString("first_name");
                    String Last_name = object.getString("last_name");
                    String emailId = object.getString("email");

                    String User_id = object.getString("id");

                    String imageUrl ="https://graph.facebook.com/"+ User_id+"/picture?type=large";
                    //String profileImageUrl = ImageRequest.getProfilePictureUri(object.optString("id"), 500, 500).toString();

                    name.setText(First_name + " " + Last_name);
                    email.setText(emailId);

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();


                    //  Picasso.get().load(imageUrl).into(profile);


                    //Glide.with(Dashboard.this).load(imageUrl).bitmapTransform(new BlurTransformation(context))
                    //.into((ImageView) findViewById(R.id.image));

                    Glide.with(FacebookLogin.this).load(imageUrl).into(profile);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");

        request.setParameters(parameters);
        request.executeAsync();
    }

    private void checkLoginStatus()
    {
        if(AccessToken.getCurrentAccessToken()!=null)
        {
            LoaduserProfile(AccessToken.getCurrentAccessToken());
        }
    }
}











