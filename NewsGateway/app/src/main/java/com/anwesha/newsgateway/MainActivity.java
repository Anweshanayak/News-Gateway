package com.anwesha.newsgateway;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.anwesha.newsgateway.databinding.ActivityMainBinding;
import com.anwesha.newsgateway.LayoutManager;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final ArrayList<Source> sourceList = new ArrayList<>();
    private final HashMap<String, ArrayList<Source>> sourceData = new HashMap<>();
    private Menu optionsMenu;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter<String> arrayAdapter;
    // maps new source names to source objects
    private HashMap<String, ArrayList<Source>> currentCategorySourcesData = new HashMap<>();
    private HashMap<String, String> id = new HashMap<>();
    // contains all new sources that cover a given category
    private ArrayList<String> sourcesDisplayed = new ArrayList<>();
    private ArrayList<String> currentCategories;
    private String currentSource;
    private List<Drawer> drawerList1;
    private Source obj;
    private static String currentTitle;
    private ArrayList<Article> a = new ArrayList<>();
    private Map<String, Integer> topicIntMap;
    // fragment vars
    private List<Fragment> newsFragments;
    //private CustomPageAdapter pageAdapter;
    private CategoryAdapter cAdapter;
    private ArrayList<Article> vp = new ArrayList<>();
    //private NewsReceiver newsReceiver;
    private ActivityMainBinding binding;
    private int[] topicColors;

    static final String ACTION_NEWS_STORY = "ACTION_NEWS_STORY";
    static final String ACTION_MSG_TO_SERVICE = "ACTION_MSG_TO_SERVICE";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        topicIntMap = new HashMap<>();
        topicColors = getResources().getIntArray(R.array.topicColors);
       // mDrawerLayout = findViewById(R.id.drawerlayout);
       // mDrawerList = findViewById(R.id.drawer_list);
        arrayAdapter = new ArrayAdapter<>(this, R.layout.drawer_item, sourcesDisplayed);
        binding.drawerList.setAdapter(arrayAdapter);
drawerList1=new ArrayList<>();
        cAdapter = new CategoryAdapter(this, vp);

        //viewPager = findViewById(R.id.viewpager1);
        binding.viewpager1.setAdapter(cAdapter);
        // Set up the drawer item click callback method
      binding.drawerList.setOnItemClickListener(
                (parent, view, position, id) -> {
                  selectItem(position);
                   // SourceVolley.performDownload(this,"");
                   binding.drawerlayout.closeDrawer(binding.drawerList);
                }
        );
        // Create the drawer toggle
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                binding.drawerlayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        );
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        // load data
        if (currentCategorySourcesData.isEmpty()) {
            SourceVolley.performDownload(this, "");
        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void selectItem(int pos) {
        // change bg from image to null so text is readable
        binding.viewpager1.setBackground(null);
      currentSource = sourcesDisplayed.get(pos);
        LayoutManager layoutRestore = new LayoutManager();
        layoutRestore.setSource(pos);
vp.clear();
        Log.d(TAG,vp.size()+"");
a=ArticleVolley.performDownload(this,id.get(currentSource));

        if (a == null) {
            Toast.makeText(this,
                    MessageFormat.format("No countries found for {0}", currentSource),
                    Toast.LENGTH_LONG).show();
            return;
        }
        vp.addAll(a);
        Log.d(TAG,vp+"");
       cAdapter.notifyDataSetChanged();
        binding.viewpager1.setCurrentItem(0);

        binding.drawerlayout.closeDrawer(binding.drawerList);

        setTitle(currentSource);

    }

    public static void resetTitle(String s) {
        currentTitle = s;
    }

    // Menu onClick (also opens drawer using drawerToggle)


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            Log.d(TAG, "onOptionsItemSelected: drawer_toggle " + item);
            return true;
        }
        // download news source data

        sourcesDisplayed.clear();
        ArrayList<Source>s=currentCategorySourcesData.get(item.getTitle().toString());
        if(s!=null) {
            for(Source s1:s)
            sourcesDisplayed.add(s1.getSourcename());
        }
        setTitle("News Gateway ("+sourcesDisplayed.size()+")");
//        SourceVolley.performDownload(this, item.getTitle().toString());
//        Log.d(TAG,item.getTitle().toString());
        arrayAdapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }



    // setup options menu reference so values can be dynamically loaded
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.optionsMenu = menu;
        return true;
    }



    public void updateData(ArrayList<Source> sourceArrayList, ArrayList<String> categoryArrayList) {
drawerList1.clear();

        if (sourceArrayList != null) {
            for (Source s : sourceArrayList) {
                if (!currentCategorySourcesData.containsKey(s.getSourcecategory())) {
                    currentCategorySourcesData.put(s.getSourcecategory(), new ArrayList<>());
                }
                ArrayList<Source> slist = currentCategorySourcesData.get(s.getSourcecategory());
                if (slist != null)
                    slist.add(s);
            }
        }
            currentCategorySourcesData.put("all", sourceArrayList);
            ArrayList<String> temp = new ArrayList<>(currentCategorySourcesData.keySet());
            Collections.sort(temp);
        int i=0;
            for (String s : temp) {
                SpannableString categoryString = new SpannableString(s);
                categoryString.setSpan(new ForegroundColorSpan(topicColors[i]), 0, categoryString.length(), 0);
                topicIntMap.put(s, topicColors[i++]);
                optionsMenu.add(categoryString);
            }

            i=0;
        for (Source s : sourceArrayList) {
            int color = topicIntMap.get(s.getSourcecategory());
            Log.d(TAG,color+"");
            SpannableString coloredString = new SpannableString(s.getSourcename());
            coloredString.setSpan(new ForegroundColorSpan(color), 0, s.getSourcename().length(), 0);
            SpannableStringBuilder sb=new SpannableStringBuilder(coloredString);
            sourcesDisplayed.add(sb.toString());
            id.put(s.getSourcename(), s.getSourceid());
        }
        for (Source source : sourceArrayList) {
            Drawer drawerContent = new Drawer();
            drawerContent.setItemName(source.getColoredName());
            drawerList1.add(drawerContent);
        }
            Log.d(TAG,sourcesDisplayed+"");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.drawer_item, sourcesDisplayed);
            binding.drawerList.setAdapter(arrayAdapter);

            arrayAdapter.notifyDataSetChanged();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
            }
            setTitle("News Gateway ("+sourcesDisplayed.size()+")");
       binding.progressBar.setVisibility(View.GONE);
        LayoutManager layoutRestore = new LayoutManager();
        layoutRestore.setCategories(categoryArrayList);
        layoutRestore.setSources(sourceArrayList);
    }

    public void updateDataA(ArrayList<Article> articleArrayList) {
        for (int i = 0; i < cAdapter.getItemCount(); i++)
            cAdapter.notifyItemChanged(i);

        vp.clear();
        LayoutManager layoutRestore = new LayoutManager();

        for (int article = 0; article < articleArrayList.size(); article++) {
            layoutRestore.setArticle(article);  vp.add(articleArrayList.get(article));
        }
        cAdapter.notifyDataSetChanged();
        binding.viewpager1.setCurrentItem(0);

//for(int i=0;i<10&&i<articleArrayList.size();i++)
//        vp.add(articleArrayList.get(i));
//        Log.d(TAG,vp.size()+"");
//        cAdapter.notifyDataSetChanged();
//        binding.viewpager1.setCurrentItem(0);
//        setTitle(currentSource);
        binding.drawerlayout.closeDrawer(binding.drawerList);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        LayoutManager layoutRestore = new LayoutManager();
        layoutRestore.setArticles(vp);
        outState.putSerializable("state", layoutRestore);
        super.onSaveInstanceState(outState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

    }
}