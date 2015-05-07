package cz.inspire.clubspire_02;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by michal on 5/3/15.
 */
public class HelpAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public HelpAdapter(FragmentManager fm, List<Fragment> fragments) {

        super(fm);

        this.fragments = fragments;

    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position position of the
     */
    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {

        return this.fragments.size();
    }

    /**
     * Created by michal on 5/6/15.
     */

}
