package cr.ac.tec.meteleungolalmachismo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.ByteArrayOutputStream;

public class ListItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        Button FBbutton = (Button) findViewById(R.id.button2);
        FBbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FBshare();
            }
        });

        Button TWbutton = (Button) findViewById(R.id.button3);
        TWbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TWshare();
            }
        });
    }

    public void FBshare(){ //Share screen to Facebook https://developers.facebook.com/docs/sharing/android
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted
            Toast.makeText(ListItemActivity.this, "OKOKOK", Toast.LENGTH_LONG).show();
            Bitmap bitmap = getScreenBitmap(); // Get the bitmap
            SharePhoto photo = new SharePhoto.Builder().setBitmap(bitmap).build();
            SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
            ShareDialog.show(ListItemActivity.this,content);
        }
        else
        {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    public void TWshare(){ //Share screen to Twitter https://github.com/twitter/twitter-kit-android/wiki/Compose-Tweets
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted
            TweetComposer.Builder builder = new TweetComposer.Builder(this).text("just setting up my Twitter Kit.").image(getImageUri(this,getScreenBitmap()));
            builder.show();
        }
        else
        {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    public Bitmap getScreenBitmap() {
        //Tomar una imagen de la pantalla (getScreenBitmap)
        View v= findViewById(android.R.id.content).getRootView();
        v.setDrawingCacheEnabled(true);
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false); // clear drawing cache
        return b;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
