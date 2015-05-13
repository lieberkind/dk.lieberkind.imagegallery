package dk.lieberkind.imagegallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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

    private ImageCache imageCache = ImageCache.getInstance();

    private ArrayList<Image> mImages = new ArrayList<>();

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    private int mPosition;

    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment create(ArrayList<Image> images, int position) {
        GalleryFragment instance = new GalleryFragment();
        instance.mImages = images;
        instance.mPosition = position;
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("GalleryFragment: onCreate");

        if(savedInstanceState != null) {
            mPosition = savedInstanceState.getInt("position");
            System.out.println("Restored position:" + mPosition);

            if(!imageCache.empty()) {
                mImages = imageCache.all();

                System.out.println("Restored images: " + mImages.size());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println("GalleryFragment: onCreateView");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        mPagerAdapter = new ImagePagerAdapter(getChildFragmentManager(), mImages);
        mPager = (ViewPager) view.findViewById(R.id.slides);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(mPosition);

        // TODO: Delete the following line, if everything seems to work correctly...
        // mPagerAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("position", mPosition);
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        List<Image> images;

        public ImagePagerAdapter(FragmentManager fm, List<Image> images) {
            super(fm);
            this.images = images;
        }

        @Override
        public Fragment getItem(int position) {
            Image image = images.get(position);

            System.out.println("ImagePagerAdapter: getItem");

            return ImageFragment.create(image.getBitmap(), image.getTitle());
        }

        @Override
        public int getCount() {
            return images.size();
        }
    }
}
