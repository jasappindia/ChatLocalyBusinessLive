package com.chatlocalybusiness.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.fragments.UnapprovedBusinesDetailFragment;

public class Unapproved_Edit_BusinessInfoActivity extends AppCompatActivity {

    public static final String BUSINESS_FRAG="Business";
    public static final String CATEGORY_FRAG="Category" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unapproved__edit__business_info);
        UnapprovedBusinesDetailFragment unapprovedBusinesDetailFragment=new UnapprovedBusinesDetailFragment(this);
        replaceFragment(unapprovedBusinesDetailFragment,BUSINESS_FRAG);

    }

    public void replaceFragment(Fragment fragment, String tag)
    {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right)
                .replace(R.id.container_businessSetup, fragment,tag)
                .commit();
/*
                 getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.rotate_in,R.anim.rotate_out)
                .replace(R.id.container_businessSetup, fragment,tag)
                .commit();
*/

    }

    @Override
    public void onBackPressed() {
        Fragment fragment;
        if((fragment=getSupportFragmentManager().findFragmentByTag(BUSINESS_FRAG))!=null){
//        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.rotate_in, R.anim.rotate_out).remove(fragment).commit();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).remove(fragment).commit();

        }


        else super.onBackPressed();

    }


}
