package com.fintrex.fintrexfinance.Details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.fintrex.fintrexfinance.Fragment.InboxFragment;
import com.fintrex.fintrexfinance.Fragment.OutboxFragment;
import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.R;
import com.fintrex.fintrexfinance.databinding.ActivityMailScreenBinding;

import java.util.ArrayList;
import java.util.List;

public class MailScreen extends BaseActivity {

    private ActivityMailScreenBinding binding;
    ImageButton newmsg;
    ImageView mailScreenback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_mail_screen);

        setUpWithViewPager(binding.viewPager);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        //float btn for new msg screen
        newmsg = findViewById(R.id.newmsg_floatbtn);

        newmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewMailSend.class);
                startActivity(intent);
            }
        });

        //back to the home screen
        mailScreenback = findViewById(R.id.mailScreenback);
        mailScreenback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    //set adapter for take tab layout topics
    private void setUpWithViewPager(ViewPager viewPager){
        MailScreen.SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new InboxFragment(),"Inbox");
        adapter.addFragment(new OutboxFragment(),"Outbox");

        viewPager.setAdapter(adapter);
    }

    //set fragments
    private static class SectionPagerAdapter extends FragmentPagerAdapter{

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionPagerAdapter(FragmentManager manager){ super(manager);}

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){return mFragmentTitleList.get(position);}
    }
}