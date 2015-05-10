package dk.lieberkind.imagegallery;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity {

    private ImageCache imageCache;

    private ArrayList<Bitmap> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.add(R.id.frame, new GalleryFragment(), "gallery");
            transaction.commit();
        }

        imageCache = ImageCache.getInstance();

        if(!imageCache.empty()) {
            images = imageCache.all();
        } else {
            new DownloadImagesTask().execute();
        }

    }

    private class DownloadImagesTask extends AsyncTask<Void, Void, ArrayList<Bitmap>> {

        private ImageFetcher imageFetcher;

        /**
         * Create an instance of this task
         */
        public DownloadImagesTask() {
            imageFetcher = new SequentialImageFetcher();
        }

        /**
         * Fetch a list of images
         *
         * @param arguments The non-existing arguments
         * @return The fetched images
         */
        @Override
        protected ArrayList<Bitmap> doInBackground(Void... arguments) {
            return imageFetcher.fetch();
        }

        /**
         * Run when images are fetched
         *
         * @param images The fetched images
         */
        @Override
        protected void onPostExecute(ArrayList<Bitmap> images) {
            if(images != null) {

                for(Bitmap image : images) {
                    imageCache.addImage(image);
                }

                GalleryFragment gallery = (GalleryFragment) getSupportFragmentManager().findFragmentByTag("gallery");
                gallery.setImages(images);
            }
        }
    }
}
