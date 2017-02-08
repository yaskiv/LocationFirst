package yaskiv.locationfirst;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.util.Arrays;
import java.util.List;

public class Share extends AppCompatActivity {



private Button button_facebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        button_facebook=(Button)findViewById(R.id.facebool_button);
        button_facebook.setOnClickListener(facebookClickShare);

    }
    public View.OnClickListener facebookClickShare = new View.OnClickListener() {
        public void onClick(View v) {
            startActivity(new Intent(Share.this,SharingActivity.class));
        }};


}
