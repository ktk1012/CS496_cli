package cs496.prj_2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;

/**
 * Created by q on 2016-07-05.
 */
public class ImageActivity extends Activity{
    private Bitmap mPlaceHolderBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image);
        mPlaceHolderBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.waiting);

        ImageView image = (ImageView)findViewById(R.id.image);

        Point pSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(pSize);

        Intent intent = getIntent();
        String img_url = intent.getStringExtra("selected");

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        int width = options.outWidth;
        int height = options.outHeight;
        if ((height/(double)width)*pSize.x>pSize.y) {
            Ion.with(this).load(img_url).withBitmap().resize((int)(pSize.y*(width/(double)height)), pSize.y)
                    .fitCenter().intoImageView(image);
        } else {
            Ion.with(this).load(img_url).withBitmap().resize(pSize.x, (int)(pSize.x * (height/(double)width)))
                    .fitCenter().intoImageView(image);
        }


    }
}
