package cs496.prj_2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.graphics.Rect;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by q on 2016-07-05.
 */
public class BitmapHelper {

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        int optWidth = options.outWidth;
        int optHeight = options.outHeight;

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;

        try{
            InputStream in = res.openRawResource(resId);
            BitmapRegionDecoder regionDecoder = BitmapRegionDecoder.newInstance(in, false);
            if (reqHeight/(double)reqWidth > optHeight/(double)optWidth) {
                int tWidth = (int) (optHeight * (reqWidth/(double)reqHeight));
                return regionDecoder.decodeRegion(new Rect((optWidth-tWidth)/2, 0 ,(optWidth+tWidth)/2, optHeight), options);
            }else{
                int tHeight = (int)(optWidth * (reqHeight/(double)reqWidth));
                return regionDecoder.decodeRegion(new Rect(0, (optHeight - tHeight)/2, optWidth, (optHeight + tHeight)/2), options);
            }
        }catch(Exception e){}

        return null;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;

        if(height > reqHeight || width > reqWidth){
            final int halfHeight = height /2;
            final int halfWidth = width /2;
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.i("LogCat", Integer.toString(inSampleSize));

        return inSampleSize;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static void loadBitmap(int resId, ImageView imageView, int xpx, int ypx, Bitmap placeHolder, Resources res){
        if (cancelPotentialWork(resId, imageView)){
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView, res);
            final AsyncDrawable asyncDrawable = new AsyncDrawable(res, placeHolder, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(resId, xpx, ypx);
        }
    }

    public static boolean cancelPotentialWork(int data, ImageView imageView){
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask!=null){
            final int bitmapData = bitmapWorkerTask.data;
            if(bitmapData != data){
                bitmapWorkerTask.cancel(true);
            }else{
                return false;
            }
        }
        return true;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    static class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private int data = 0;
        private Resources resources;

        public BitmapWorkerTask(ImageView imageView, Resources res) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
            resources=res;
        }


        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            int data = (Integer)params[0];
            int x = (Integer)params[1];
            int y = (Integer)params[2];

            Bitmap bitmap = BitmapHelper.decodeSampledBitmapFromResource(resources,data,x,y);
            return bitmap;
        }


        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

}
