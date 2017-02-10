package yaskiv.locationfirst;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Share extends AppCompatActivity {
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private  Activity activity;


private Button button_facebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        button_facebook=(Button)findViewById(R.id.facebool_button);
        button_facebook.setOnClickListener(facebookClickShare);
        activity=this;
        ImageView imageView=(ImageView)findViewById(R.id.screenOpen);
        Bitmap myBitmap = BitmapFactory.decodeFile(MapsActivity.file1.getAbsolutePath());
        imageView.setImageBitmap(myBitmap);

    }
    public View.OnClickListener facebookClickShare = new View.OnClickListener() {
        public void onClick(View v) {


            FacebookSdk.sdkInitialize(getApplicationContext());

            callbackManager = CallbackManager.Factory.create();

            List<String> permissionNeeds = Arrays.asList("publish_actions");

            //this loginManager helps you eliminate adding a LoginButton to your UI
            loginManager = LoginManager.getInstance();

            loginManager.logInWithPublishPermissions(activity, permissionNeeds);


            loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
            {
                @Override
                public void onSuccess(LoginResult loginResult)
                {
                    sharePhotoToFacebook();
                }

                @Override
                public void onCancel()
                {
                    System.out.println("onCancel");
                }

                @Override
                public void onError(FacebookException error) {

                }

            });


            //startActivity(new Intent(Share.this,SharingActivity.class));
        }};
    private void sharePhotoToFacebook(){
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_ndm);

        image=MapsActivity.mbitmap;

       /* SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("My way")
                .build();*/
        SharePhoto photo = new SharePhoto.Builder()
                .setImageUrl(Uri.fromFile(MapsActivity.file1))
                .setCaption("My way")
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareApi.share(content, null);

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data)
    {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }

}
