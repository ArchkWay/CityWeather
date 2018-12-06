package com.example.archek.weathercities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsFragment extends Fragment {

    EditText etNameCity;
    private SQLiteDatabase database;
    private ContentValues contentValues = new ContentValues();

    EditText etDec;
    EditText etJan;
    EditText etFeb;
    EditText etMar;
    EditText etApr;
    EditText etMay;
    EditText etJun;
    EditText etJul;
    EditText etAug;
    EditText etSep;
    EditText etOct;
    EditText etNov;

    Button btnBig;
    Button btnMiddle;
    Button btnLittle;
    Button btnAddCity;
    Button btnClear;

    String nameCity;
    String sizeCity;
    String stDec;
    String stJan;
    String stFeb;
    String stMar;
    String stApr;
    String stMay;
    String stJun;
    String stJul;
    String stAug;
    String stSep;
    String stOct;
    String stNov;

    public static boolean choosen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        DBHelper dbHelper = new DBHelper(getContext());
        database = dbHelper.getWritableDatabase();
        setIdViews(view);
        setSizeCity();
        btnAddFunc();
        setBtnClear();
    }

    public static void setChoosen(boolean choosen) {
        SettingsFragment.choosen = choosen;
    }

    public void setIdViews(View view){
        etNameCity = view.findViewById(R.id.etCityName);
        etDec = view.findViewById(R.id.etDec);
        etJan = view.findViewById(R.id.etJan);
        etFeb = view.findViewById(R.id.etFeb);
        etMar = view.findViewById(R.id.etMar);
        etApr = view.findViewById(R.id.etApr);
        etMay = view.findViewById(R.id.etMay);
        etJun = view.findViewById(R.id.etJun);
        etJul = view.findViewById(R.id.etJul);
        etAug = view.findViewById(R.id.etAug);
        etSep = view.findViewById(R.id.etSep);
        etOct = view.findViewById(R.id.etOct);
        etNov = view.findViewById(R.id.etNov);
        btnBig = view.findViewById(R.id.btnBig);
        btnMiddle = view.findViewById(R.id.btnMiddle);
        btnLittle = view.findViewById(R.id.btnLittle);
        btnAddCity = view.findViewById(R.id.btnAddCity);
        btnClear = view.findViewById(R.id.btnClear);
    }

    public void setSizeCity(){
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                switch (id) {
                    case R.id.btnBig:
                        if(!choosen) {
                            sizeCity = "Большой";
                            btnMiddle.setVisibility(View.INVISIBLE);
                            btnLittle.setVisibility(View.INVISIBLE);
                            setChoosen(true);
                        }
                        else if(choosen){
                            btnMiddle.setVisibility(View.VISIBLE);
                            btnLittle.setVisibility(View.VISIBLE);
                            setChoosen(false);
                        }
                        break;
                    case R.id.btnMiddle:
                        if(!choosen) {
                            sizeCity = "Средний";
                            btnBig.setVisibility(View.INVISIBLE);
                            btnLittle.setVisibility(View.INVISIBLE);
                            setChoosen(true);
                        }
                        else if(choosen){
                            btnBig.setVisibility(View.VISIBLE);
                            btnLittle.setVisibility(View.VISIBLE);
                            setChoosen(false);
                        }
                        break;
                    case R.id.btnLittle:
                        if(!choosen) {
                            sizeCity = "Малый";
                            btnMiddle.setVisibility(View.INVISIBLE);
                            btnBig.setVisibility(View.INVISIBLE);
                            setChoosen(true);
                        }
                        else if(choosen){
                            btnMiddle.setVisibility(View.VISIBLE);
                            btnBig.setVisibility(View.VISIBLE);
                            setChoosen(false);
                        }
                        break;
                }
            }
        };
        btnBig.setOnClickListener(listener);
        btnMiddle.setOnClickListener(listener);
        btnLittle.setOnClickListener(listener);
    }

    public void btnAddFunc(){
        btnAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameCity = etNameCity.getText().toString();
                stDec = etDec.getText().toString();
                stJan = etJan.getText().toString();
                stFeb = etFeb.getText().toString();
                stMar = etMar.getText().toString();
                stApr = etApr.getText().toString();
                stMay = etMay.getText().toString();
                stJun = etJun.getText().toString();
                stJul = etJul.getText().toString();
                stAug = etAug.getText().toString();
                stSep = etSep.getText().toString();
                stOct = etOct.getText().toString();
                stNov = etNov.getText().toString();
                try {
                    putInDB();
                }
                catch (Exception ignored){
                    Toast.makeText(getContext(),R.string.error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void putInDB() {
        double winter = (Math.round((Double.parseDouble(stDec) + Double.parseDouble(stJan) + Double.parseDouble(stFeb))/3*100));
        winter = winter/100;
        double spring = (Math.round((Double.parseDouble(stMar) + Double.parseDouble(stApr) + Double.parseDouble(stMay))/3*100));
        spring = spring/100;
        double summer = (Math.round((Double.parseDouble(stJun) + Double.parseDouble(stJul) + Double.parseDouble(stAug))/3*100));
        summer = summer/100;
        double autumn = (Math.round((Double.parseDouble(stSep) + Double.parseDouble(stOct) + Double.parseDouble(stNov))/3*100));
        autumn = autumn/100;
        contentValues.put(DBHelper.CITY_NAME, nameCity);
        contentValues.put(DBHelper.CITY_SIZE, sizeCity);
        contentValues.put(DBHelper.TEMPRETURE_AVERAGE_WINTER, winter);
        contentValues.put(DBHelper.TEMPRETURE_AVERAGE_SPRING, spring);
        contentValues.put(DBHelper.TEMPRETURE_AVERAGE_SUMMER, summer);
        contentValues.put(DBHelper.TEMPRETURE_AVERAGE_AUTUMN, autumn);
        database.insert(DBHelper.TABLE_CW, null, contentValues);
    }

    public void setBtnClear(){
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.delete(DBHelper.TABLE_CW, null, null);
            }
        });
    }
}