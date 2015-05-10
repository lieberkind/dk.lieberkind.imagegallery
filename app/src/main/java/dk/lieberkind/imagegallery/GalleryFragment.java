package dk.lieberkind.imagegallery;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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

    private ArrayList<Bitmap> images;

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            images = savedInstanceState.getParcelableArrayList("images");
        } else {
            images = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        mPager = (ViewPager) view.findViewById(R.id.slides);

        setImages(images);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("images", images);

        super.onSaveInstanceState(outState);
    }

    public void setImages(ArrayList<Bitmap> images) {
        this.images = images;
        mPagerAdapter = new ImagePagerAdapter(getActivity().getSupportFragmentManager(), images);
        mPager.setAdapter(mPagerAdapter);
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
