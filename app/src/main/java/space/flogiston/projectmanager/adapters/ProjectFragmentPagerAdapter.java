package space.flogiston.projectmanager.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import space.flogiston.projectmanager.R;
import space.flogiston.projectmanager.data.entities.ProjectsList;
import space.flogiston.projectmanager.fragments.ChiefFragment;
import space.flogiston.projectmanager.fragments.ColleaguesListFragment;
import space.flogiston.projectmanager.fragments.SubordinateFragment;
import space.flogiston.projectmanager.fragments.SubordinatesListFragment;

public class ProjectFragmentPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] pages;
    private ProjectsList project;
    private Context context;
    public ProjectFragmentPagerAdapter(Context context, FragmentManager fm, ProjectsList project) {
        super(fm);
        this.context = context;
        this.project = project;
        if (project.getSubordinated() != 1) {
            pages = new Fragment[4];
            pages[0] = new ChiefFragment();
            pages[1] = new SubordinatesListFragment();
            pages[2] = new SubordinateFragment();
            pages[3] = new ColleaguesListFragment();
        } else {
            pages = new Fragment[2];
            pages[0] = new ChiefFragment();
            pages[1] = new SubordinatesListFragment();
        }
    }

    @Override
    public Fragment getItem(int position) {
        return pages[position];
    }

    @Override
    public int getCount() {
        if (project.getSubordinated() == 1) {
            return 2;
        }
        return 4;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.leader);
        } else if (position == 1) {
            return context.getString(R.string.my_subordinates);
        } else if (position == 2) {
            return context.getString(R.string.subordinate);
        } else {
            return context.getString(R.string.my_colleagues);
        }
    }
}