package com.example.archek.weathercities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    private final MainAdapter adapter = new MainAdapter(this);
    private List<City> list = new ArrayList <>();
    private SQLiteDatabase database;
    private ContentValues contentValues = new ContentValues();
    private TextView tvCitySize;
    private TextView tvCityTemp;
    private TextView tvSummer;
    private TextView tvWinter;
    private TextView tvSpring;
    private TextView tvAutumn;
    static String chooseSeason = "year";
    private boolean choosen;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        DBHelper dbHelper = new DBHelper(getContext());
        database = dbHelper.getWritableDatabase();
        final Cursor cursor = database.query(DBHelper.TABLE_CW, null, null, null, null, null, null);
        /**/
        /*BEGIN*/
        /**/
        /*Instal RecyclerView*/

        setupRecyclerView(view);
        /*set ability choose season*/
        flipSeasons(view);
        /*if the database(DB) empty, load a default list(and fill them DB)*/
        if(!cursor.moveToNext()) {
            createCityList();
            putInDB();
        }
        /*if DB have any cities(atleast 1) - load them*/
        getFromDB(cursor);
        adapter.addAll(list);
        /*in adapter realized ability choose one of cities*/
    }

    private void createCityList() {//prepared default citylist
        list.add(new City("Москва","Большой", -6.66,
                6.16,17.66, 5.16));
        list.add(new City("Ростов-на-Дону","Средний", -2.33,
                10.36,22.61, 9.93));
        list.add(new City("Екатеринбург","Средний", -12,
                4.02,17.63, 2.8));
        list.add(new City("Гуково","Малый", -2.83,
                10.16,22.66, 9.83));
    }
    public void putInDB() {//load into DB content from cityList
        for(int i = 0; i < list.size(); i++) {
            contentValues.put(DBHelper.CITY_NAME, list.get(i).getCityName());
            contentValues.put(DBHelper.CITY_SIZE, list.get(i).getCitySize());
            contentValues.put(DBHelper.TEMPRETURE_AVERAGE_WINTER, list.get(i).getWinter());
            contentValues.put(DBHelper.TEMPRETURE_AVERAGE_SPRING, list.get(i).getSpring());
            contentValues.put(DBHelper.TEMPRETURE_AVERAGE_SUMMER, list.get(i).getSummer());
            contentValues.put(DBHelper.TEMPRETURE_AVERAGE_AUTUMN, list.get(i).getAutumn());
            database.insert(DBHelper.TABLE_CW, null, contentValues);
        }
    }
    public void getFromDB(Cursor cursor){//get from DB into citylist
        list.clear();
        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(DBHelper.CITY_NAME);
            int sizeIndex = cursor.getColumnIndex(DBHelper.CITY_SIZE);
            int winterIndex = cursor.getColumnIndex(DBHelper.TEMPRETURE_AVERAGE_WINTER);
            int springIndex = cursor.getColumnIndex(DBHelper.TEMPRETURE_AVERAGE_SPRING);
            int summerIndex = cursor.getColumnIndex(DBHelper.TEMPRETURE_AVERAGE_SUMMER);
            int autumnIndex = cursor.getColumnIndex(DBHelper.TEMPRETURE_AVERAGE_AUTUMN);
            do {
                list.add(new City(cursor.getString(nameIndex), cursor.getString(sizeIndex), cursor.getDouble(winterIndex),
                        cursor.getDouble(springIndex), cursor.getDouble(summerIndex), cursor.getDouble(autumnIndex)));
            } while (cursor.moveToNext());
        } else cursor.close();

        cursor.close();
    }


        @SuppressLint("SetTextI18n")
        public void chooseCity(String citySize, String cityTemp){//choosing city(activating from recycler adapter(when user click on a city)
            tvCitySize.setText(citySize);
            if(cityTemp.substring(0,1).equals("-")){
                tvCityTemp.setText(cityTemp + "°");
            }
            else tvCityTemp.setText("+" + cityTemp + "°");

        }

        public void setupRecyclerView(View view){//instal RV
            RecyclerView rvCritics = view.findViewById(R.id.rvCities);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            rvCritics.setLayoutManager(linearLayoutManager);
            rvCritics.setAdapter(adapter);
        }

        public void flipSeasons(View view){//ability to change one of seasons and hide remaining
            tvCitySize = view.findViewById(R.id.tvCitySize);
            tvCityTemp = view.findViewById(R.id.tvCityTemp);
            tvSummer = view.findViewById(R.id.tvSummer);
            tvWinter = view.findViewById(R.id.tvWinter);
            tvSpring = view.findViewById(R.id.tvSpring);
            tvAutumn = view.findViewById(R.id.tvAutumn);
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    switch (id){
                        case R.id.tvWinter:
                            if(!choosen) {
                                setChooseSeason("winter");
                                tvSpring.setVisibility(View.INVISIBLE);
                                tvSummer.setVisibility(View.INVISIBLE);
                                tvAutumn.setVisibility(View.INVISIBLE);
                                setChoosen(true);
                            }
                            else if(choosen) {
                                tvSpring.setVisibility(View.VISIBLE);
                                tvSummer.setVisibility(View.VISIBLE);
                                tvAutumn.setVisibility(View.VISIBLE);
                                setChoosen(false);
                            }
                            break;
                        case R.id.tvSpring:
                            if(!choosen) {
                                setChooseSeason("spring");
                                tvWinter.setVisibility(View.INVISIBLE);
                                tvSummer.setVisibility(View.INVISIBLE);
                                tvAutumn.setVisibility(View.INVISIBLE);
                                setChoosen(true);
                            }
                            else if(choosen) {
                                tvWinter.setVisibility(View.VISIBLE);
                                tvSummer.setVisibility(View.VISIBLE);
                                tvAutumn.setVisibility(View.VISIBLE);
                                setChoosen(false);
                            }
                            break;
                        case R.id.tvSummer:
                            if(!choosen) {
                                setChooseSeason("summer");
                                tvSpring.setVisibility(View.INVISIBLE);
                                tvWinter.setVisibility(View.INVISIBLE);
                                tvAutumn.setVisibility(View.INVISIBLE);
                                setChoosen(true);
                            }
                            else if(choosen) {
                                tvSpring.setVisibility(View.VISIBLE);
                                tvWinter.setVisibility(View.VISIBLE);
                                tvAutumn.setVisibility(View.VISIBLE);
                                setChoosen(false);
                            }
                            break;
                        case R.id.tvAutumn:
                            if(!choosen) {
                                setChooseSeason("autumn");
                                tvSpring.setVisibility(View.INVISIBLE);
                                tvSummer.setVisibility(View.INVISIBLE);
                                tvWinter.setVisibility(View.INVISIBLE);
                                setChoosen(true);
                            }
                            else if(choosen){
                                tvSpring.setVisibility(View.VISIBLE);
                                tvSummer.setVisibility(View.VISIBLE);
                                tvWinter.setVisibility(View.VISIBLE);
                                setChoosen(false);
                            }
                            break;
                    }
                }
            };
            tvSummer.setOnClickListener(listener);
            tvWinter.setOnClickListener(listener);
            tvSpring.setOnClickListener(listener);
            tvAutumn.setOnClickListener(listener);
        }
    public void setChoosen(boolean choosen) {//seter for marking choose
        this.choosen = choosen;
    }

    public static void setChooseSeason(String chooseSeason) {//seter for change season
        MainFragment.chooseSeason = chooseSeason;
    }


}
