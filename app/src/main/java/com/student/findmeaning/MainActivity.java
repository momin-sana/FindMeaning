package com.student.findmeaning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.student.findmeaning.Adapters.BookmarkAdapter;
import com.student.findmeaning.Models.DictionaryApiResponse;
import com.student.findmeaning.Models.Meaning;
import com.student.findmeaning.Models.Phonetic;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnFetchDataListener {
    private DrawerLayout drawerLayout;
    private ImageButton btnNavMenu;
    private SearchView searchView;
    private TextView appnameTV;
    public Bundle bundle;
    private DefinitionFragment definitionFragment;
    String bookmarkQuery;
    String historyQuery;
    String notesQuery;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.nav_view);
        btnNavMenu = findViewById(R.id.navigation_button);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        appnameTV = findViewById(R.id.appNameTV);
        searchView = findViewById(R.id.search_view);

        navigationView.setNavigationItemSelectedListener(this);
        btnNavMenu.setOnClickListener(view -> {
            btnNavMenu.setBackgroundResource(R.drawable.button_background);
            drawerLayout.openDrawer(GravityCompat.START);
            searchView.setIconified(true);
        });

        MainFragment mainFragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mainFragment)
                .commit();


        // Retrieve the bookmarkQuery from the intent
        // navigation from list word to definition Fragment -- 4
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("bookmarkQuery")) {
            searchView.setIconified(true);
            appnameTV.setVisibility(View.VISIBLE);
            bookmarkQuery = extras.getString("bookmarkQuery");
            Log.d("Main Activity= ", "onCreate: BookmarQuery: "+ bookmarkQuery);
            setSearchBookmarkQueryListener();
        }
        if (bookmarkQuery != null && !bookmarkQuery.isEmpty()){
            searchView.setQuery(bookmarkQuery, true);
            searchView.clearFocus();
            setSreachFromBookmark();
        }
        if (extras != null && extras.containsKey("historyQuery")){
            searchView.setIconified(true);
            appnameTV.setVisibility(View.VISIBLE);
            historyQuery = extras.getString("historyQuery");
            Log.d("Main Activity= ", "onCreate: historyQuery: "+ historyQuery);
            setSearchHistoryQueryListener();
        }
        if (historyQuery != null && !historyQuery.isEmpty()){
            searchView.setQuery(historyQuery, true);
            setSearchFromHistory();
        }
        if (extras != null && extras.containsKey("notesQuery")){
            searchView.setIconified(true);
            appnameTV.setVisibility(View.VISIBLE);
            notesQuery = extras.getString("notesQuery");
            Log.d("Main Activity= ", "onCreate: notesQuery: "+ notesQuery);
            setSearchNotesQueryListener();
        }
        if (notesQuery != null && !notesQuery.isEmpty()){
            searchView.setQuery(notesQuery, true);
            setSearchFromNotes();
        }
        else{
            setSearchViewListener();
        }
    }

    private void setSearchViewListener(){
        searchView.setOnQueryTextFocusChangeListener((view, b) -> {
            if (view.hasFocus() || searchView.getQuery().length() > 0) {
                if (searchView.getVisibility() == View.VISIBLE) {
                    setSearchQueryListener();
                    appnameTV.setVisibility(View.INVISIBLE);
                }
            } else {
                appnameTV.setVisibility(View.VISIBLE);
                searchView.setIconified(true);
            }
        });
    }

    private void setSearchQueryListener(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String lowercaseQuery = query.toLowerCase();

                definitionFragment = DefinitionFragment.newInstance(lowercaseQuery);
                bundle = new Bundle();
                bundle.putString("query", lowercaseQuery);
                definitionFragment.setArguments(bundle);

                fragmentTransaction();

                definitionFragment.fetchWordData(lowercaseQuery);

                searchView.setIconified(true);
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setBackgroundResource(R.drawable.button_background);
    }

    private void setSearchFromHistory() {
        searchView.setOnQueryTextFocusChangeListener((view, hasFocus) -> {
            if (hasFocus || searchView.getQuery().length() > 0) {
                if (searchView.getVisibility() == View.VISIBLE) {
                    setSearchHistoryQueryListener();
                    appnameTV.setVisibility(View.INVISIBLE);
                }
            } else {
                appnameTV.setVisibility(View.VISIBLE);
                searchView.setIconified(true);
            }
        });
    }

    private void setSearchHistoryQueryListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String historyQuery) {
                definitionFragment = DefinitionFragment.newInstance(historyQuery);
                bundle= new Bundle();
                bundle.putString("historyQuery", historyQuery);
                definitionFragment.setArguments(bundle);
                fragmentTransaction();
                definitionFragment.fetchWordData(historyQuery);
                searchView.setIconified(true);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setSreachFromBookmark() {
        searchView.setOnQueryTextFocusChangeListener((view, hasFocus) -> {
            if (hasFocus || searchView.getQuery().length() > 0) {
                if (searchView.getVisibility() == View.VISIBLE) {
                    setSearchBookmarkQueryListener();
                    appnameTV.setVisibility(View.INVISIBLE);
                }
            } else {
                appnameTV.setVisibility(View.VISIBLE);
                searchView.setIconified(true);
            }
        });
    }

    private void setSearchBookmarkQueryListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String bookmarkQuery) {
                definitionFragment = DefinitionFragment.newInstance(bookmarkQuery);
                bundle = new Bundle();
                bundle.putString("bookmarkQuery", bookmarkQuery);
                definitionFragment.setArguments(bundle);
                fragmentTransaction();
                definitionFragment.fetchWordData(bookmarkQuery);

                searchView.setIconified(true);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setBackgroundResource(R.drawable.button_background);
    }

    private void setSearchFromNotes() {
        searchView.setOnQueryTextFocusChangeListener((view, hasFocus) -> {
            if (hasFocus || searchView.getQuery().length() > 0) {
                if (searchView.getVisibility() == View.VISIBLE) {
                    setSearchHistoryQueryListener();
                    appnameTV.setVisibility(View.INVISIBLE);
                }
            } else {
                appnameTV.setVisibility(View.VISIBLE);
                searchView.setIconified(true);
            }
        });
    }

    private void setSearchNotesQueryListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String notesQuery) {
                definitionFragment = DefinitionFragment.newInstance(notesQuery);
                bundle= new Bundle();
                bundle.putString("notesQuery", notesQuery);
                definitionFragment.setArguments(bundle);
                fragmentTransaction();
                definitionFragment.fetchWordData(notesQuery);
                searchView.setIconified(true);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.notes_menuItem) {
            Intent intent = new Intent(this, Notes.class);
            startActivity(intent);
        } else if (id == R.id.bookmark_menuItem) {
            Intent intent = new Intent(this, Bookmark.class);
            startActivity(intent);
        } else if (id == R.id.history_menuItem) {
            Intent intent = new Intent(this, History.class);
            startActivity(intent);
        } else if (id == R.id.exit_menuItem) {
            new AlertDialog.Builder(MainActivity.this);
            View customLayout = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_alert_dialog, null);
            Button positiveButton = customLayout.findViewById(R.id.positive_button);
            Button negativeButton = customLayout.findViewById(R.id.negative_button);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setView(customLayout);
            AlertDialog dialog = builder.create();
            dialog.show();

            positiveButton.setOnClickListener(v -> {
                finishAffinity();
                System.exit(0);
            });
            negativeButton.setOnClickListener(v -> dialog.dismiss());
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onFetchData(DictionaryApiResponse apiResponse, String message) {
        List<Phonetic> phoneticList = apiResponse.getPhonetics();
        List<Meaning> meaningList = apiResponse.getMeanings();

        definitionFragment.setData(phoneticList,meaningList);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @SuppressLint("CommitTransaction")
    @Override
    public void onBackPressed() {
        // If DefinitionFragment is currently visible, replace it with MainFragment
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment instanceof  DefinitionFragment){
//            ensure that the fragments are properly managed in the back stack and prevent overlapping when the activity is recreated.
            getSupportFragmentManager().popBackStack("MainFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,
                            MainFragment.newInstance())
                    .commit();
        }else{super.onBackPressed();}
    }

    public void fragmentTransaction(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, definitionFragment);
        fragmentTransaction.commit();
    }
}
