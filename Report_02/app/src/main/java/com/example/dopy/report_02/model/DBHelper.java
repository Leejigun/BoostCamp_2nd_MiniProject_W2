package com.example.dopy.report_02.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dopy.report_02.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Dopy on 2017-07-13.
 * http://dunkinpender.tistory.com/4
 */
/*TODO: version reset
        테스트때마다 버젼을 올리는것을 좋지만,
        최종 PR시에는 처음 릴리즈 되었다는 것을 생각하여 버젼을 1로 초기화해서 진행해도 좋습니다.
        (마켓에 기 릴리즈된 앱을 수정하는 것이 아니기때문입니다.)*/
public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "restaurant.db";
    private static final int DATABASE_VERSION = 1;
    Context context;

    private Dao<Item, Integer> mRestaurant;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    /*헬퍼 인스턴스가 생성되고 테이블을 생성한다.
    SQLite에서는 쿼리를 직접 입력해야 했지만 이 경우 사전에 정의한 클래스에 마크를 보고 테이블을 생성*/
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.d("DB","테이블생성");
            TableUtils.createTable(connectionSource, Item.class);  //Table 생성
        } catch (SQLException e) {
            Log.e(OrmLiteSqliteOpenHelper.class.getName(), "Unable to create datbases", e);
        }
        DataBaseManager.getInstance().setUpData();
    }
    //    버전이 업데이트 되었을 때 DB의 행동을 정의하게 된다.
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.d("DBhelper","UpgragdeDatabase");
            TableUtils.dropTable(connectionSource, Item.class, true);    //업데이트시 테이블 삭제
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(OrmLiteSqliteOpenHelper.class.getName(),
                    "Unable to upgrade database from version " + oldVersion + " to new " + newVersion, e);
        }
    }
    public Dao<Item, Integer> getStandardInfosDao() throws SQLException {
        if (mRestaurant == null) {
            mRestaurant = getDao(Item.class);
        }
        return mRestaurant;
    }
}
