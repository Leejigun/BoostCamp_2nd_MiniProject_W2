package com.example.dopy.report_02;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dopy.report_02.model.DBHelper;
import com.example.dopy.report_02.model.DataBaseManager;
import com.example.dopy.report_02.model.Item;

import java.util.ArrayList;

import static android.R.color.holo_green_light;

/*TODO ORMlite를 사용한 이유
* 처음에는 SQL lite로 코딩을 시작했는데, 저번주 튜터링에서 ORM에 대해서 언급하신 것을 생각해서 수정했습니다.
* 또한 ORM에 대해서 검색하던 중 http://iw90.tistory.com/114에서 다양한 ORM들을 분석한 글을 읽게 되었고
* 가장 commit이 활발하고 잘 정리되어 있어서 여러 블로그를 따라서 작성하게 되었습니다.
* */


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerView;
    ImageView imgCangeLayout;
    Button btnDistance;
    Button btnpopulrity;
    Button btnrecent;
    ArrayList<Item> arrayList;

    private boolean layout_linear=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//데이터 베이스 생성
        DataBaseManager.init(this);

//       리사이클러뷰 생성
        recyclerView=(RecyclerView)findViewById(R.id.rcvItems);
        recyclerView.setHasFixedSize(true); //아이템이 추가되도 사이즈 고정

        //처음에는 Linear로 생성
        arrayList=DataBaseManager.getInstance().select("distance");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ItemViewAdapter(this,arrayList));

//       메뉴바 버튼 액션 리스너 추가
        btnrecent=(Button) findViewById(R.id.btnRecent);
        imgCangeLayout=(ImageView) findViewById(R.id.imgChangeLayout);
        btnDistance=(Button)findViewById(R.id.btnDistance);
        btnpopulrity=(Button)findViewById(R.id.btnPopulrity);
        setUpButtonListener();

    }
    /*TODO:remove test code
    최종 Commit 또는 PR하기전에는 테스트 코드는 제외 해주길 바랍니다.*/


    /*TODO: selector
    클릭될때마다 boolean 체크로 resource를 코드로 변경하지 않고,
    Check/UnCheck 또는 Select/UnSelect의 값을 가지고 Resource에서 Selector를 만들어서 변경을 할 수 있습니다.*/
    public void setUpButtonListener(){
        //레이아웃 변경 액션 리스너
        imgCangeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layout_linear){
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,1));
                    layout_linear=!layout_linear;
                }else{
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    layout_linear=!layout_linear;
                }
            }
        });

        //거리순 정렬
        btnDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList=DataBaseManager.getInstance().select("distance");
                recyclerView.setAdapter(new ItemViewAdapter(getApplicationContext(),arrayList));
                if(!layout_linear){
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,1));
                }else{
                   recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }
                setAllBtnBlack();
                btnDistance.setTextColor(getResources().getColor(R.color.colorclicked));
            }
        });
        //추천순 정렬
        btnpopulrity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList=DataBaseManager.getInstance().select("popularity");
                recyclerView.setAdapter(new ItemViewAdapter(getApplicationContext(),arrayList));
                if(!layout_linear){
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,1));
                }else{
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
                setAllBtnBlack();
                btnpopulrity.setTextColor(getResources().getColor(R.color.colorclicked));
            }
        });
        //최신순 정렬
        btnrecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList=DataBaseManager.getInstance().select("recent");
                recyclerView.setAdapter(new ItemViewAdapter(getApplicationContext(),arrayList));
                if(!layout_linear){
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,1));
                }else{
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
                setAllBtnBlack();
                btnrecent.setTextColor(getResources().getColor(R.color.colorclicked));
            }
        });
    }

    public void setAllBtnBlack(){
        btnDistance.setTextColor(getResources().getColor(android.R.color.black));
        btnrecent.setTextColor(getResources().getColor(android.R.color.black));
        btnpopulrity.setTextColor(getResources().getColor(android.R.color.black));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_distance) {
            btnDistance.callOnClick();
        } else if (id == R.id.nav_popularity) {
            btnpopulrity.callOnClick();
        } else if (id == R.id.nav_recent) {
            btnrecent.callOnClick();
        } else if (id == R.id.nav_Linear) {
            if(!layout_linear) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                layout_linear = !layout_linear;
            }
        } else if (id == R.id.nav_stagg) {
            if(layout_linear){
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,1));
                layout_linear=!layout_linear;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
