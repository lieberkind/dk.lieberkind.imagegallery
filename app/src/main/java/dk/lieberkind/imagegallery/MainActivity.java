package dk.lieberkind.imagegallery;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("Creating activity");

        new DownloadImagesTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println("Destroying activity");
    }

    private class DownloadImagesTask extends AsyncTask<Void, Void, ArrayList<Bitmap>> {

        private ImageCache imageCache;
        private ImageFetcher imageFetcher;

        /**
         * Create an instance of this task
         */
        public DownloadImagesTask() {
            imageCache = ImageCache.getInstance();
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

            // If our cache isn't empty, we simple fetch images from there
            if(!imageCache.empty()) {
                System.out.println("Fetching images from cache...");
                return imageCache.all();
            }

            System.out.println("Fetching images from server...");

            // If our cache is empty, we fetch the images and make sure to
            // add them to the cache before returning them
            ArrayList<Bitmap> images = imageFetcher.fetch();

            for(int i = 0; i < images.size(); i++) {
                imageCache.addImage(images.get(i));
            }

            return images;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> images) {
            if(images != null) {
                System.out.println("Images fetched: " + images.size());

                mPager = (ViewPager) MainActivity.this.findViewById(R.id.slides);
                mPagerAdapter = new ImagePagerAdapter(MainActivity.this.getSupportFragmentManager(), images);
                mPager.setAdapter(mPagerAdapter);
            }
        }
    }

    private class ImagePagerAdapter extends FragmentPagerAdapter {

        List<Bitmap> images;

        public ImagePagerAdapter(FragmentManager fm, List<Bitmap> images) {
            super(fm);
            this.images = images;
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.create(images.get(position), "Cool Title, dawg!");
        }

        @Override
        public int getCount() {
            return images.size();
        }
    }
}
