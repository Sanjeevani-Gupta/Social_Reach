package com.example.socialmediaintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



import com.facebook.login.widget.LoginButton;

public class Dashboard extends AppCompatActivity {
      private Button facebook_login;
      private Button Google_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        facebook_login=(Button)findViewById(R.id.fb_login_button);
        Google_login=(Button)findViewById(R.id.g_sign_in_button);

        facebook_login.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent fbIntent= new Intent(Dashboard.this,FacebookLogin.class);
                startActivity(fbIntent);
            }
        });

        Google_login.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent googleIntent=new Intent(Dashboard.this,GoogleLogin.class);
                startActivity(googleIntent);
            }
        });

    }
}