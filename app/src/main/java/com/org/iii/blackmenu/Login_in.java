package com.org.iii.blackmenu;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.roger.gifloadinglibrary.GifLoadingView;

public class Login_in extends AppCompatActivity {
    private LoginButton loginButton;
    LoginManager lmg;
    private CallbackManager callbackManager;
    AccessToken accessToken;
    private ProfileTracker profileTracker;
    private GifLoadingView mGifLoadingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        TextView textView = (TextView)findViewById(R.id.testView);
        Typeface typeface =Typeface.createFromAsset(getAssets(), "fonts/font1.ttf");
        textView.setTypeface(typeface);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_button);

        lmg.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // gif 畫面設定
                mGifLoadingView = new GifLoadingView();
                mGifLoadingView.setImageResource(R.drawable.load);
                mGifLoadingView.show(getFragmentManager(),"");


                //間隔時間5秒跳頁

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final Intent mainIntent = new Intent(Login_in.this, QR_Code.class);
                        startActivity(mainIntent);
                        finish();        // 當跳到另一 Activity 時，讓 MainActivity 結束。
                        // 這樣可以避免使用者按 back 後，又回到該 activity。
                    }
                }, 5000);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Log.v("ppking" , "oldProfile : "+ oldProfile );
                Log.v("ppking" , "currentProfile : "+ currentProfile );

            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void finish() {
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.logOut();
        super.finish();

    }
}
