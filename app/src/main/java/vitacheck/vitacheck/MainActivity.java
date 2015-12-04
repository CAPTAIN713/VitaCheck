package vitacheck.vitacheck;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseUser;


import vitacheck.vitacheck.drawer.*;
import vitacheck.vitacheck.fragments.*;



public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks {
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Enable Local Datastore.
        //Parse.enableLocalDatastore(this);
        //Parse.initialize(this, "xJQq6UTUqt4IxtnQPmOTWAqtJAVmXHZtbVFhtDdb", "h56YvQcc77YsEmYj0RbfuJuDut8MUA5IXECwVqoP");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        ParseUser currentUser = ParseUser.getCurrentUser();
        mNavigationDrawerFragment.setUserData(currentUser.get("name").toString(), currentUser.getEmail(), BitmapFactory.decodeResource(getResources(), R.drawable.avatar));

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment fragment = null;

        switch(position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new DoctorsFragment();
                break;
            case 2:
                fragment = new MedicineFragment();
                break;
            case 3:
                fragment = new VitalsFragment();
                break;
            case 4:
                //fragment = new DietFragment();
                //fragment= new DietFoodFragment();
                //fragment= new DietAddFoodFragment();
                //fragment= new DietRecipeFragment();
                //fragment= new DietAddRecipeFragment();
                //fragment= new DietHealthArticleFragment();
                //fragment= new DietAddHealthArticleFragment();
                break;
            default:
                break;
        }

        if (fragment != null)
        {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else{
            /*pop stuff of the fragment stack
            http://stackoverflow.com/questions/28153397/adding-fragment-to-the-addtobackstack-when-you-have-a-single-activity-with-2-fra
            http://stackoverflow.com/questions/5448653/how-to-implement-onbackpressed-in-android-fragments
            docs: http://developer.android.com/reference/android/app/FragmentManager.html  */
            if(getFragmentManager().getBackStackEntryCount()>1)
            {
                //if at least one thing on fragment stack go back to that one
                getFragmentManager().popBackStack();
            }
            else{
                //if nothing else on stack exit app
                super.onBackPressed();
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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



}
