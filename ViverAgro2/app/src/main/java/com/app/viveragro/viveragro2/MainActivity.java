package com.app.viveragro.viveragro2;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.pixelcan.inkpageindicator.InkPageIndicator;

public class MainActivity extends AppCompatActivity {
    InkPageIndicator inkPageIndicator;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Firebase firebase;
    private Produtor produtor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        inkPageIndicator = (InkPageIndicator) findViewById(R.id.indicator);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        firebase = LibraryClass.getFirebase();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        produtor = new Produtor();
        inkPageIndicator.setViewPager(mViewPager);
       verifyUserLogged();
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


        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ScrollView relative = (ScrollView) rootView.findViewById(R.id.scroll1);
            ScrollView relative2 = (ScrollView) rootView.findViewById(R.id.scroll2);
            ScrollView relative3 = (ScrollView) rootView.findViewById(R.id.scroll3);
            ScrollView relative4 = (ScrollView) rootView.findViewById(R.id.scroll4);

            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                    relative.setVisibility(View.VISIBLE);
                    relative2.setVisibility(View.GONE);
                    break;
                case 2:
                    relative2.setVisibility(View.VISIBLE);
                    relative.setVisibility(View.GONE);
                    break;
                case 3:
                    relative3.setVisibility(View.VISIBLE);
                    relative2.setVisibility(View.GONE);
                    break;
                case 4:
                    relative4.setVisibility(View.VISIBLE);
                    relative3.setVisibility(View.GONE);
                    break;
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    public void comecar(View view){
        Intent intent = new Intent(this,Entrar.class);
        startActivity(intent);
    }
    private void verifyUserLogged(){
        try{
            if(firebase.getAuth() != null){
                Intent intent;
                produtor.retrieveIdSP(this);
                String tipo = produtor.retrieveTipoSP(this);
                switch (tipo){
                    case "Produtor":
                        intent = new Intent(getBaseContext(),ProdutorPerfil.class);
                        startActivity(intent);
                        break;
                    case "Consumidor":
                        intent = new Intent(getBaseContext(),ConsumidorPerfil.class);
                        startActivity(intent);
                        break;



                }
            }
        }catch (Exception de){

        }


    }

}
