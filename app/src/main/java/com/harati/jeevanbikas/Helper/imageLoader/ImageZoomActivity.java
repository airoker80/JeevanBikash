package com.harati.jeevanbikas.Helper.imageLoader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.harati.jeevanbikas.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;



public class ImageZoomActivity extends AppCompatActivity {

    ZoomableImageView zoom_notice_image;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        zoom_notice_image = (ZoomableImageView) findViewById(R.id.zoom_notice_image);
        back= (ImageView) findViewById(R.id.img_back);
        Intent intent = getIntent();
        if(intent!=null){
            try {
                String[] splitString = intent.getStringExtra("photo").split(",");
                String base64Photo = splitString[1];
                byte[] decodedString = Base64.decode(base64Photo, Base64.DEFAULT);
                Bitmap userPhoto = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                zoom_notice_image.setImageBitmap(userPhoto);
            }catch (Exception e){
                Toast.makeText(this, "No Image ", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
          //  Log.i("zoomImage", intent.getStringExtra("imageUrl")+"");
/*            Picasso.with(getApplicationContext())
                    .load(intent.getStringExtra("imageUrl"))
                    .placeholder(R.drawable.tender_notice_default_image)
                    .error(R.drawable.tender_notice_default_image)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            zoom_notice_image.setImageBitmap(bitmap);
                           // zoom_notice_image.setOnTouchListener(new ZoomImage());
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });*/
        }

        back.setOnClickListener(view -> onBackPressed());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
