    package com.example.dopy.report_02.model;

import android.content.Context;
import android.util.Log;

import com.example.dopy.report_02.R;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Dopy on 2017-07-14.
 * https://github.com/justadreamer/WishListManager/blob/master/src/com/test/db/DatabaseManager.java
 *
 * Database의 초기 데이터값을 넣을 위치를 찾던 중 Helper의 OnCreate가 처음 DB를 만들 떄 혹은 DB 버전을 업데이트 해서 다시 DB를 생성할 때만
 * 동작한다는 것을 알게되었고 ORMLite 예시에서 Manager로 따로 만들어 사용하는 방법을 보고 참조( 링크는 상위 참조 )
 */


public class DataBaseManager {
    static private DataBaseManager instance;
    private RestaurantDao mRestaurantDao;
    static public void init(Context ctx) {
        if (null==instance) {
            instance = new DataBaseManager(ctx);
        }
    }

    static public DataBaseManager getInstance() {
        return instance;
    }

    public RestaurantDao getmRestaurantDao() {
        return mRestaurantDao;
    }

    private DBHelper helper;
    private DataBaseManager(Context ctx) {
        helper = new DBHelper(ctx);
        mRestaurantDao=new RestaurantDao();
    }
    private DBHelper getHelper() {return helper;}

    //컨트롤
    public ArrayList<Item> select(String order) {
        /*ArrayList<Item> itemArrayList = null;
        try {
            QueryBuilder<Item, Integer> qb = getHelper().getStandardInfosDao().queryBuilder().orderBy(order, false);
            itemArrayList = (ArrayList<Item>) getHelper().getStandardInfosDao().query(qb.prepare());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemArrayList;*/
        return  mRestaurantDao.select(order);
    }
    public void insert(Item item) {
       /* try {
            getHelper().getStandardInfosDao().createOrUpdate(item);
        } catch (SQLException e) {
            Log.e("DBHelper",e.getMessage());
            e.printStackTrace();
        }*/
       mRestaurantDao.insert(item);
    }
    public void updateData(Item item){
        /*try {
            getHelper().getStandardInfosDao().update(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        mRestaurantDao.updateData(item);
    }

    /*TODO:RestaurantDAO Class
    DBHelper에서 바로 StandardInfosDao()를 부르기 보다는
    RestaurantDAO Class를 만들어서 Restaurant과 관련된 CRUD를 수행 할 수 있게 나누는 방법이 좋습니다.*/
    class RestaurantDao{
        private Dao<Item, Integer> mRestaurant;

        public RestaurantDao() {
            try {
                this.mRestaurant = getHelper().getStandardInfosDao();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public ArrayList<Item> select(String order) {
            ArrayList<Item> itemArrayList = null;
            try {
                QueryBuilder<Item, Integer> qb = mRestaurant.queryBuilder().orderBy(order, false);
                itemArrayList = (ArrayList<Item>) mRestaurant.query(qb.prepare());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return itemArrayList;
        }
        public void insert(Item item) {
            try {
                getHelper().getStandardInfosDao().createOrUpdate(item);
            } catch (SQLException e) {
                Log.e("DBHelper",e.getMessage());
                e.printStackTrace();
            }
        }
        public void updateData(Item item){
            try {
                getHelper().getStandardInfosDao().update(item);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    //초기화
    public void setUpData(){
        Log.d("DBhleper","SetupData");
        mRestaurantDao.insert(new Item("rufXXX",
                "커피든 맥주든 혹은 와인이든 당신의 시간을 즐기는 데에는 오직 한 잔이면 충분하다. 극단 루프엑스가 운영하는 분위기 깡패 바&카페!",
                R.drawable.rufxxx,1,0,1));
        mRestaurantDao.insert(new Item("이탈리",
                "근방 이탈리안 레스토랑의 판도를 뒤집었다. 이탈리아의 프리미엄 식자재 브랜드가 차린 곳이니 맛만큼은 그 어떤 곳보다 현지급.",
                R.drawable.italy,2,9,3));
        mRestaurantDao.insert(new Item("매그놀리아",
                "혀가 녹아내릴 달콤함과 세포들이 순식간에 팽창해버릴 것만 같은 칼로리지만 결코 포기할 수 없다. 단 한 입으로 드라마 속 캐리가 된 기분!",
                R.drawable.megnolia,3,8,2));
        mRestaurantDao.insert(new Item("능라",
                "메밀 향 짙은 면발을 툭툭 끊어내고 이 시리게 육수를 퍽퍽 퍼먹자. 만두에 제육, 거기에 막걸리까지- 평냉은 역시 겨울이 갑이다.",
                R.drawable.nungra,4,7,6));
        mRestaurantDao.insert(new Item("알레그리아",
                "서울에서 굳이 마시기 위해 찾아와야 하는 최고의 라떼. 라떼와 아포가또 모두를 품은 카페 곤엘라또는 마셔보지 않은 자는 그 맛을 모른다.",
                R.drawable.algia,5,6,5));
        mRestaurantDao.insert(new Item("로네펠트 티 카페",
                "끝없이 펼쳐진 차의 향연! 문을 열자마자 폐부를 깊이 파고드는 단아한 향은 차 덕후의 정신을 아득히 멀리 날려버린다. 세계 3대 홍차 브랜드의 위엄!",
                R.drawable.ronepelt,6,5,4));
        mRestaurantDao.insert(new Item("블루밍가든",
                "\"비싼 음식이 맛있는 게 아니다. 맛있는 음식이 비싼 거다.\" 지갑은 얇아지고 월급은 마치 사이버 머니와 같지만 그래도 역시 여기다.",
                R.drawable.bluming,7,4,7));
        mRestaurantDao.insert(new Item("아임홈",
                "입맛 까다로운 우리 엄마도 만족시킬 새하얀 설원 같은 밀크빙수! 내가 널 가장 처음 무너뜨리고 말겠다는 굳은 의지",
                R.drawable.imhome,8,3,9));
        mRestaurantDao.insert(new Item("혜선이떡볶이",
                "홍대 유명 즉떡을 먹기 위해 이제 서울까지 가지 말자. 그 떡볶이의 기술을 그대로 이어받았으니. 심지어 더 맛있다는 평이 있을 정도!",
                R.drawable.heseng,9,2,8));
        mRestaurantDao.insert(new Item("홀드미",
                "참 먹을 곳 많은 판교에서 굳이 이곳까지 오는 이유는 아주 간단하다. 그저 '맛있다'로 끝나지 않는 라떼와 캠벨포도쥬스가 있으니까.",
                R.drawable.holdme,0,1,0));
    }
}
