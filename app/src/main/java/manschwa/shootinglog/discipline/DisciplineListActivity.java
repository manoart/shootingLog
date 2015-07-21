package manschwa.shootinglog.discipline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.List;
import java.util.ArrayList;

import manschwa.shootinglog.R;

/**
 * Created by Manuel on 13.07.15.
 */
public class DisciplineListActivity extends AppCompatActivity implements DisciplineListAdapter.OnItemClickListener {

    private List<Discipline> disciplines = new ArrayList<Discipline>();

    private DrawerLayout drawerLayout;
    private View content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discipline_list);

        this.disciplines = getAllDisciplines();

        initRecyclerView();
        initFab();
        initToolbar();
        setupDrawerLayout();

        content = findViewById(R.id.discipline_list_coordinator_layout);

        //final ImageView avatar = (ImageView) findViewById(R.id.avatar);
        //Picasso.with(this).load(AVATAR_URL).transform(new CircleTransform()).into(avatar);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//
//    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.discipline_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DisciplineListAdapter disciplineListAdapter = new DisciplineListAdapter(disciplines);
        disciplineListAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(disciplineListAdapter);
    }

    private void initFab() {
        findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisciplineListActivity.this, DisciplineNewActivity.class);

                startActivity(intent);
                //Snackbar.make(content, "FAB Clicked", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private List<Discipline> getAllDisciplines() {

        DisciplineDatabaseHelper disciplineDatabaseHelper = new DisciplineDatabaseHelper(this);

        return disciplineDatabaseHelper.findAllDisciplines();
    }

    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(MenuItem menuItem) {
                Snackbar.make(content, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override public void onItemClick(View view, Discipline discipline) {
        Intent intent = new Intent(DisciplineListActivity.this, DisciplineNewActivity.class);
        intent.putExtra("Discipline", discipline);
        startActivity(intent);
//        Snackbar.make(content, discipline.getName() + " pressed", Snackbar.LENGTH_LONG).show();
//        DisciplineNewActivity.navigate(this, view.findViewById(R.id.image), viewModel);
    }
}
