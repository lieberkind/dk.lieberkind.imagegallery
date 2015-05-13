package dk.lieberkind.imagegallery;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements ThumbnailFragment.OnThumbnailClickedListener {

    private ImageCache imageCache = ImageCache.getInstance();

    private ArrayList<Image> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.add(R.id.frame, new ThumbnailFragment(), "thumbnails");
            transaction.commit();
        }

        if(!imageCache.empty()) {
            images = imageCache.all();
        } else {
            new DownloadImagesTask().execute();
        }
    }

    @Override
    public void onThumbnailClicked(int position) {
        System.out.println("I was pressed");

        GalleryFragment gf = GalleryFragment.create(images, position);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frame, gf);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private class DownloadImagesTask extends AsyncTask<Void, Void, ArrayList<Image>> {

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
        protected ArrayList<Image> doInBackground(Void... arguments) {
            return imageFetcher.fetch();
        }

        /**
         * Run when images are fetched
         *
         * @param images The fetched images
         */
        @Override
        protected void onPostExecute(ArrayList<Image> images) {

            System.out.println("AsyncTask: onPostExecute");

            if(images != null) {
                MainActivity.this.images = images;

                for(Image image : images) {
                    imageCache.addImage(image);
                }

                ThumbnailFragment thumbnails = (ThumbnailFragment) getSupportFragmentManager().findFragmentByTag("thumbnails");
                thumbnails.setImages(images);
            }
        }
    }
}
