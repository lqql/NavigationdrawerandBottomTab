/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigationdrawerexample;

import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This example illustrates a common usage of the DrawerLayout widget
 * in the Android support library.
 * <p/>
 * <p>When a navigation (left) drawer is present, the host activity should detect presses of
 * the action bar's Up affordance as a signal to open and close the navigation drawer. The
 * ActionBarDrawerToggle facilitates this behavior.
 * Items within the drawer should fall into one of two categories:</p>
 * <p/>
 * <ul>
 * <li><strong>View switches</strong>. A view switch follows the same basic policies as
 * list or tab navigation in that a view switch does not create navigation history.
 * This pattern should only be used at the root activity of a task, leaving some form
 * of Up navigation active for activities further down the navigation hierarchy.</li>
 * <li><strong>Selective Up</strong>. The drawer allows the user to choose an alternate
 * parent for Up navigation. This allows a user to jump across an app's navigation
 * hierarchy at will. The application should treat this as it treats Up navigation from
 * a different task, replacing the current task stack using TaskStackBuilder or similar.
 * This is the only form of navigation drawer that should be used outside of the root
 * activity of a task.</li>
 * </ul>
 * <p/>
 * <p>Right side drawers should be used for actions, not navigation. This follows the pattern
 * established by the Action Bar that navigation should be to the left and actions to the right.
 * An action should be an operation performed on the current contents of the window,
 * for example enabling or disabling a data overlay on top of the current content.</p>
 */
public class MainActivity extends Activity implements OnClickListener{
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    private Fragment planetfragment;
    //……………………………………………………………………………………………………………………………………
    /** 
     * 用于展示消息的Fragment 
     */  
    private MessageFragment messageFragment;  
  
    /** 
     * 用于展示联系人的Fragment 
     */  
    private ContactsFragment contactsFragment;  
  
    /** 
     * 用于展示动态的Fragment 
     */  
    private NewsFragment newsFragment;  
  
    /** 
     * 用于展示设置的Fragment 
     */  
    private SettingFragment settingFragment;  
  
    /** 
     * 消息界面布局 
     */  
    private View messageLayout;  
  
    /** 
     * 联系人界面布局 
     */  
    private View contactsLayout;  
  
    /** 
     * 动态界面布局 
     */  
    private View newsLayout;  
  
    /** 
     * 设置界面布局 
     */  
    private View settingLayout;  
  
    /** 
     * 在Tab布局上显示消息图标的控件 
     */  
    private ImageView messageImage;  
  
    /** 
     * 在Tab布局上显示联系人图标的控件 
     */  
    private ImageView contactsImage;  
  
    /** 
     * 在Tab布局上显示动态图标的控件 
     */  
    private ImageView newsImage;  
  
    /** 
     * 在Tab布局上显示设置图标的控件 
     */  
    private ImageView settingImage;  
  
    /** 
     * 在Tab布局上显示消息标题的控件 
     */  
    private TextView messageText;  
  
    /** 
     * 在Tab布局上显示联系人标题的控件 
     */  
    private TextView contactsText;  
  
    /** 
     * 在Tab布局上显示动态标题的控件 
     */  
    private TextView newsText;  
  
    /** 
     * 在Tab布局上显示设置标题的控件 
     */  
    private TextView settingText;  
  
    /** 
     * 用于对Fragment进行管理 
     */  
    private FragmentManager fragmentManager;  

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     // 初始化布局元素  
        initViews();  
        fragmentManager = getFragmentManager();
        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); 
                
                  // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
        	setTabSelection(0);
        }
    }
    /** 
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。 
     */  
    private void initViews() {  
        messageLayout = findViewById(R.id.message_layout);  
        contactsLayout = findViewById(R.id.contacts_layout);  
        newsLayout = findViewById(R.id.news_layout);  
        settingLayout = findViewById(R.id.setting_layout);  
        messageImage = (ImageView) findViewById(R.id.message_image);  
        contactsImage = (ImageView) findViewById(R.id.contacts_image);  
        newsImage = (ImageView) findViewById(R.id.news_image);  
        settingImage = (ImageView) findViewById(R.id.setting_image);  
        messageText = (TextView) findViewById(R.id.message_text);  
        contactsText = (TextView) findViewById(R.id.contacts_text);  
        newsText = (TextView) findViewById(R.id.news_text);  
        settingText = (TextView) findViewById(R.id.setting_text);  
        messageLayout.setOnClickListener(this);  
        contactsLayout.setOnClickListener(this);  
        newsLayout.setOnClickListener(this);  
        settingLayout.setOnClickListener(this);  
    }  
  
    @Override  
    public void onClick(View v) {  
        switch (v.getId()) {  
        case R.id.message_layout:  
            // 当点击了消息tab时，选中第1个tab  
            setTabSelection(0);  
            break;  
        case R.id.contacts_layout:  
            // 当点击了联系人tab时，选中第2个tab  
            setTabSelection(1);  
            break;  
        case R.id.news_layout:  
            // 当点击了动态tab时，选中第3个tab  
            setTabSelection(2);  
            break;  
        case R.id.setting_layout:  
            // 当点击了设置tab时，选中第4个tab  
            setTabSelection(3);  
            break;  
        default:  
            break;  
        }  
    }  
  
    /** 
     * 根据传入的index参数来设置选中的tab页。 
     *  
     * @param index 
     *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。 
     */  
    private void setTabSelection(int index) {  
        // 每次选中之前先清楚掉上次的选中状态  
        clearSelection();  
        // 开启一个Fragment事务  
        FragmentTransaction transaction = fragmentManager.beginTransaction();  
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况  
        hideFragments(transaction);
        switch (index) {  
        case 0:  
            // 当点击了消息tab时，改变控件的图片和文字颜色  
            messageImage.setImageResource(R.drawable.message_selected);  
            messageText.setTextColor(Color.WHITE);  
            if (messageFragment == null) {  
                // 如果MessageFragment为空，则创建一个并添加到界面上  
                messageFragment = new MessageFragment();  
                transaction.add(R.id.content_frame, messageFragment);  
            } else {  
                // 如果MessageFragment不为空，则直接将它显示出来  
                transaction.show(messageFragment);  
            }  
            break;  
        case 1:  
            // 当点击了联系人tab时，改变控件的图片和文字颜色  
            contactsImage.setImageResource(R.drawable.contacts_selected);  
            contactsText.setTextColor(Color.WHITE);  
            if (contactsFragment == null) {  
                // 如果ContactsFragment为空，则创建一个并添加到界面上  
                contactsFragment = new ContactsFragment();  
                transaction.add(R.id.content_frame, contactsFragment);  
            } else {  
                // 如果ContactsFragment不为空，则直接将它显示出来  
                transaction.show(contactsFragment);  
            }  
            break;  
        case 2:  
            // 当点击了动态tab时，改变控件的图片和文字颜色  
            newsImage.setImageResource(R.drawable.news_selected);  
            newsText.setTextColor(Color.WHITE);  
            if (newsFragment == null) {  
                // 如果NewsFragment为空，则创建一个并添加到界面上  
                newsFragment = new NewsFragment();  
                transaction.add(R.id.content_frame, newsFragment);  
            } else {  
                // 如果NewsFragment不为空，则直接将它显示出来  
                transaction.show(newsFragment);  
            }  
            break;  
        case 3:  
        default:  
            // 当点击了设置tab时，改变控件的图片和文字颜色  
            settingImage.setImageResource(R.drawable.setting_selected);  
            settingText.setTextColor(Color.WHITE);  
            if (settingFragment == null) {  
                // 如果SettingFragment为空，则创建一个并添加到界面上  
                settingFragment = new SettingFragment();  
                transaction.add(R.id.content_frame, settingFragment);  
            } else {  
                // 如果SettingFragment不为空，则直接将它显示出来  
                transaction.show(settingFragment);  
            }  
            break;  
        }  
        transaction.commit();  
    }  
  
    /** 
     * 清除掉所有的选中状态。 
     */  
    private void clearSelection() {  
        messageImage.setImageResource(R.drawable.message_unselected);  
        messageText.setTextColor(Color.parseColor("#82858b"));  
        contactsImage.setImageResource(R.drawable.contacts_unselected);  
        contactsText.setTextColor(Color.parseColor("#82858b"));  
        newsImage.setImageResource(R.drawable.news_unselected);  
        newsText.setTextColor(Color.parseColor("#82858b"));  
        settingImage.setImageResource(R.drawable.setting_unselected);  
        settingText.setTextColor(Color.parseColor("#82858b"));  
    }  
  
    /** 
     * 将所有的Fragment都置为隐藏状态。 
     *  
     * @param transaction 
     *            用于对Fragment执行操作的事务 
     */  
    private void hideFragments(FragmentTransaction transaction) {  
        if (messageFragment != null) {  
            transaction.hide(messageFragment);  
        }  
        if (contactsFragment != null) {  
            transaction.hide(contactsFragment);  
        }  
        if (newsFragment != null) {  
            transaction.hide(newsFragment);  
        }  
        if (settingFragment != null) {  
            transaction.hide(settingFragment);
        }
    }  
    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_websearch:
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        mDrawerLayout.closeDrawer(mDrawerList);
    	Intent intent=new Intent();
    	intent.setClass(this, ChildActivity.class);
    	Bundle args = new Bundle();
        args.putInt("Planet numble", position);
    	intent.putExtras(args);
    	startActivity(intent);
        //planetfragment = new PlanetFragment();
        //Bundle args = new Bundle();
        //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        //planetfragment.setArguments(args);

        //FragmentManager fragmentManager = getFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.content_frame, planetfragment).commit();

        // update selected item and title, then close the drawer
        //mDrawerList.setItemChecked(position, true);
        //setTitle(mPlanetTitles[position]);
       // mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

   
}