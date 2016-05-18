package com.weather.yogurt.weatherapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.weather.yogurt.weatherapp.R;
import com.weather.yogurt.weatherapp.database.WeatherDB;
import com.weather.yogurt.weatherapp.model.City;
import com.weather.yogurt.weatherapp.model.Country;
import com.weather.yogurt.weatherapp.model.Province;
import com.weather.yogurt.weatherapp.util.HttpCallbackListener;
import com.weather.yogurt.weatherapp.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yogurt on 5/17/16.
 */
public class ChooseAreaActivity extends Activity {
    public static final int LEVEL_PROVINCE=0;
    public static final int LEVEL_CITY=1;
    public static final int LEVEL_COUNTRY=2;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private WeatherDB weatherDB;
    private List<String> dataList=new ArrayList<>();

    //省列表
    List<Province> provinceList;
    //市列表
    List<City> cityList;
    //县列表
    List<Country> countryList;

    private Province selectedProvince;//选中的省份
    private City selectedCity;//选中的市
    private int currentLevel;//当前选中级别

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        listView= (ListView) findViewById(R.id.list_view);
        titleText = (TextView) findViewById(R.id.title_text);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);

        weatherDB=WeatherDB.getInstance(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if (currentLevel==LEVEL_PROVINCE){
                   if ("上海".equals(provinceList.get(position).getProvinceName())||"北京".equals(provinceList.get(position).getProvinceName())
                   ||"重庆".equals(provinceList.get(position).getProvinceName())||"天津".equals(provinceList.get(position).getProvinceName())){
                       selectedCity=weatherDB.loadCities(provinceList.get(position).getProvinceName()).get(0);
                        queryCountries();
                   }else {
                       selectedProvince=provinceList.get(position);
                       queryCities();
                   }

               }else if (currentLevel==LEVEL_CITY){
                   selectedCity=cityList.get(position);
                   queryCountries();
               }
            }
        });

        queryProvinces();//加载省级数据
    }

    /*
        查询全国所有的省份,优先从数据库查询,没有查询到则去服务器查询
     */
    private void queryProvinces(){
        provinceList=weatherDB.loadProvinces();
        if (provinceList.size()>0){
            dataList.clear();
            for (Province province:provinceList){
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();//????????????
            listView.setSelection(0);
            titleText.setText("中国");
            currentLevel=LEVEL_PROVINCE;
        }else {
            queryFromServer();
        }
    }

    private void queryCities(){
        cityList=weatherDB.loadCities(selectedProvince.getProvinceName());
        if (cityList.size()>0){
            dataList.clear();
            for (City city:cityList){
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedProvince.getProvinceName());
            currentLevel=LEVEL_CITY;
        }else {
            queryFromServer();
        }
    }

    private void queryCountries(){
        countryList=weatherDB.loadCountries(selectedCity.getCityName());
        if (countryList.size()>0){
            dataList.clear();
            for (Country country:countryList){
                dataList.add(country.getCountryNameChinese());
            }
            adapter.notifyDataSetChanged();
            titleText.setText(selectedCity.getCityName());
            listView.setSelection(0);
            currentLevel=LEVEL_COUNTRY;
        }else {
            queryFromServer();
        }
    }

    private void queryFromServer() {
        boolean result=false;
        HttpCallbackListener listener=new HttpCallbackListener() {
            @Override
            public void onFinish(String result) {

            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showProgressDialog();
            }
        });
        HttpUtil.sendHttpRequest("http://10.13.4.198/allchina.xml",listener,weatherDB);
        //Log.d("AAA1",response.substring(0,100));
        Log.d("AAA1", String.valueOf(result));
        if (result){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    closeProgressDialog();
                }
            });
        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    closeProgressDialog();
                    Toast.makeText(ChooseAreaActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    /*
        显示进度对话框
     */
    private void showProgressDialog() {

        if (progressDialog==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if (currentLevel==LEVEL_COUNTRY){
            if ("上海".equals(selectedCity.getCityName())||"北京".equals(selectedCity.getCityName())
                    ||"重庆".equals(selectedCity.getCityName())||"天津".equals(selectedCity.getCityName())){
                queryProvinces();
            }else {
                queryCities();
            }

        }else if (currentLevel==LEVEL_CITY){
            queryProvinces();
        }else if (currentLevel==LEVEL_COUNTRY){
            finish();
        }
    }
}
