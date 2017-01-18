package com.example.bharti.materialdesign;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NavFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View containerId;
    public static final String FILE_NAME = "testpref";
    public static final String Key_User_learned = "user_learned_drawer";

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBar;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    private OnFragmentInteractionListener mListener;

    public NavFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NavFragment newInstance(String param1, String param2) {
        NavFragment fragment = new NavFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(returnFromPreferences(getActivity(),Key_User_learned,"false"));
        if(savedInstanceState!=null){
            mFromSavedInstanceState=true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/
    public void setUp(int fragmentId , DrawerLayout drawer_layout, Toolbar toolbar) {
        containerId = getActivity().findViewById(fragmentId);
        mDrawerLayout= drawer_layout;
        mActionBar = new ActionBarDrawerToggle(getActivity(),drawer_layout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){
            @Override
            /*
            it will check if the user has viewed the navigation drawer before or nt.
            */
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    savePreferences(getActivity(),Key_User_learned, mUserLearnedDrawer+"");

                }
                getActivity().invalidateOptionsMenu();
            }
            public void onDrawerClosed(View drawerView){

                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        if(!mUserLearnedDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(containerId);
        }
        mDrawerLayout.setDrawerListener(mActionBar);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBar.syncState();
            }
        });

    }

    public static void savePreferences(Context context, String preferenceName, String preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();

    }

    public static String returnFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
