package dk.lieberkind.imagegallery;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity
        implements ThumbnailFragment.OnThumbnailClickedListener {

    private final String THUMBNAIL_FRAGMENT_TAG = "thumbnails";

    /**
     * Progress dialog to show while fetching images
     */
    private ProgressDialog progress;

    /**
     * The ImageCache from where already fetched images can be retrieved
     */
    private ImageCache mImageCache = ImageCache.getInstance();

    /**
     * The list of images that will be used by the GalleryFragment and ThumbnailFragment
     */
    private ArrayList<Image> mImages = new ArrayList<>();

    /**
     * The onCreate lifecycle method
     *
     * @param savedInstanceState Bundle containing the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity's view
        setContentView(R.layout.activity_main);

        // Add the ThumbnailFragment to the frame. We only want to do this the first time  onCreate
        // is called, as the fragment manager will re-add the last active fragment to the frame on
        // configuration changes. Failing to make this check results in several instances of the
        // fragment being placed on top of each other within the frame. We add the fragment with a
        // tag, so we can find it in the FragmentManager at a later time
        if(savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.add(R.id.frame, new ThumbnailFragment(), THUMBNAIL_FRAGMENT_TAG);
            transaction.commit();
        }

        // If the image cache has images, we use them in the activity. Only if the cache is empty
        // we will download them from the server
        if(!mImageCache.empty()) {
            mImages = mImageCache.all();
        } else {
            new DownloadImagesTask().execute();
        }
    }

    /**
     * The handler for when a thumbnail is clicked in the ThumbnailFragment as required by the
     * ThumbnailFragment.OnThumbnailClickedListener interface
     *
     * Here we replace the fragment which is currently in the frame with a new GalleryFragment
     *
     * @param position The position of the thumbnail clicked in the list of thumbnails
     */
    @Override
    public void onThumbnailClicked(int position) {
        GalleryFragment galleryFragment = GalleryFragment.create(mImages, position);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frame, galleryFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Asynchronous task for downloading images
     */
    private class DownloadImagesTask extends AsyncTask<Void, Void, ArrayList<Image>> {

        /**
         * The ImageFetcher in use
         */
        private ImageFetcher mImageFetcher;

        /**
         * Create an instance of this task
         */
        public DownloadImagesTask() {
            mImageFetcher = new SequentialImageFetcher();
        }

        /**
         * Run before we start fetching the images
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Show a progress dialog
            progress = ProgressDialog.show(MainActivity.this, "Fetching images", "Just a moment...");
        }

        /**
         * Fetch a list of images
         *
         * @param arguments The non-existing arguments
         * @return The fetched images
         */
        @Override
        protected ArrayList<Image> doInBackground(Void... arguments) {
            return mImageFetcher.fetch();
        }

        /**
         * Run when images are fetched
         *
         * @param images The fetched images
         */
        @Override
        protected void onPostExecute(ArrayList<Image> images) {

            // Hide the progress dialog
            progress.dismiss();

            if(images != null) {
                // Set the images in the activity
                MainActivity.this.mImages = images;

                // Add all images to the cache for later use
                for(Image image : images) {
                    mImageCache.addImage(image);
                }

                // Find the ThumbnailFragment and tell it to use the images as thumbnails
                ThumbnailFragment thumbnails =
                        (ThumbnailFragment) getSupportFragmentManager()
                            .findFragmentByTag(THUMBNAIL_FRAGMENT_TAG);

                thumbnails.setImages(images);
            }
        }
    }
}
