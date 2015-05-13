package dk.lieberkind.imagegallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {

    private final String CURRENT_IMAGE_POSITION = "position";

    /**
     * The ImageCache from where already fetched images can be retrieved
     */
    private ImageCache mImageCache = ImageCache.getInstance();

    /**
     * The list of images to display in this gallery
     */
    private ArrayList<Image> mImages = new ArrayList<>();

    /**
     * The ViewPager in use
     */
    private ViewPager mPager;

    /**
     * The PagerAdapter in use
     */
    private PagerAdapter mPagerAdapter;

    /**
     * The position in the ViewPager
     */
    private int mPosition;

    /**
     *  Required empty public constructor
     */
    public GalleryFragment() {
    }

    /**
     * Static method for creating an instance of this fragment populated with relevant data
     *
     * @param images Images to show in the gallery
     * @param position Initial position of the gallery
     *
     * @return New instance of GalleryFragment
     */
    public static GalleryFragment create(ArrayList<Image> images, int position) {
        GalleryFragment instance = new GalleryFragment();
        instance.mImages = images;
        instance.mPosition = position;
        return instance;
    }

    /**
     * The onCreate lifecycle method, here we create as much of the fragments state as possible
     *
     * @param savedInstanceState Bundle containing the saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            mPosition = savedInstanceState.getInt(CURRENT_IMAGE_POSITION);

            if(!mImageCache.empty()) {
                mImages = mImageCache.all();
            }
        }

        mPagerAdapter = new ImagePagerAdapter(getChildFragmentManager(), mImages);
    }

    /**
     * The onCreateView lifecycle method, here we initialize everything that has to do with the
     * fragments view
     *
     * @param inflater Layout inflater
     * @param container Container for the fragment's view
     * @param savedInstanceState Bundle containing the saved instance state
     *
     * @return The fragments view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        mPager = (ViewPager) view.findViewById(R.id.slides);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(mPosition);

        return view;
    }

    /**
     * The onSaveInstanceState lifecycle method
     *
     * @param outState Bundle to contain state
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(CURRENT_IMAGE_POSITION, mPosition);
    }

    /**
     * Adapter that decides how single elements in the fragment's ViewPager should be created
     */
    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        /**
         * List of images
         */
        List<Image> mImages;

        /**
         * Public constructor
         *
         * @param fm FragmentManager needed by FragmentStatePagerAdapter
         * @param images List of images
         */
        public ImagePagerAdapter(FragmentManager fm, List<Image> images) {
            super(fm);
            mImages = images;
        }

        /**
         * Get the item at the specified position
         *
         * @param position The position of the item to get
         *
         * @return New ImageFragment containing the image at give position
         */
        @Override
        public Fragment getItem(int position) {
            Image image = mImages.get(position);

            return ImageFragment.create(image.getBitmap(), image.getTitle());
        }

        /**
         * Get count of images in this adapter
         *
         * @return integer
         */
        @Override
        public int getCount() {
            return mImages.size();
        }
    }
}
