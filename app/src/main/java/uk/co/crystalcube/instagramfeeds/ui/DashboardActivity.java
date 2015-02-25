package uk.co.crystalcube.instagramfeeds.ui;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsMenu;

import uk.co.crystalcube.instagramfeeds.InstagramApp;
import uk.co.crystalcube.instagramfeeds.R;
import uk.co.crystalcube.instagramfeeds.auth.AuthenticationManager;

@EActivity(R.layout.activity_dashboard)
@OptionsMenu(R.menu.menu_dashboard)
public class DashboardActivity extends ActionBarActivity implements ActionBar.TabListener {

    public static final int REQUEST_CODE = 0x10;

    /**
     * OAuth manager bean with singleton scope
     */
    @Bean
    protected AuthenticationManager auth;

    /**
     * Fragment pager adapter
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @AfterViews
    protected void setupViewPager() {

        if(((InstagramApp)getApplicationContext()).getAccessToken() == null) {
            authorize();
        }

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setIcon(mSectionsPagerAdapter.getIcon(i))
                            .setTabListener(this));
        }
    }

    private void authorize() {
        WebViewActivity_.intent(this).url(auth.getAuthorizationUrl()).startForResult(REQUEST_CODE);
    }

    @OnActivityResult(REQUEST_CODE)
    protected void onAuthorization(int resultCode, Intent data) {

        if(resultCode == Activity.RESULT_OK) {
            String token = data.getExtras().getString(WebViewActivity.KEY_EXTRA_ACCESS_TOKEN);
            ((InstagramApp) getApplicationContext()).setAccessToken(token);
        }
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
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public static final int MAX_PAGES = 2;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return DashboardListFragment_.builder().build();

                case 1:
                    return DashboardGridFragment_.builder().build();
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return MAX_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);

            }
            return null;
        }

        public int getIcon(int position) {

            switch (position) {
                case 0:
                    return R.drawable.ic_action_view_as_list;
                case 1:
                    return R.drawable.ic_action_view_as_grid;
            }

            return R.drawable.ic_launcher;
        }
    }
}
