package com.example.programkasir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity implements ListView.OnItemClickListener{
    //deklarasi variabel
    private ListView listView;
    private String JSON_STRING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //mengambil id dari activity_main2.xml untuk di pakai di variabel disebelah kiri
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
        //mengambil id dari activity_main2.xml untuk di pakai di variabel disebelah kiri
        Button btnKembali = findViewById(R.id.btnKembali);
        //mensetting button btnTampilData agar ketika diklik
        //dapat menampilkan MainActivity1
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kembali = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(kembali);
            }
        });
    }
    private void showBarang(){
        JSONObject jsonObject = null;
        //membuat arraylist bernama list
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            //Mengambil data dan menambahkannya ke arraylist list
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String kodeBarang = jo.getString(Konfigurasi.TAG_KODEBARANG);
                String namaBarang = jo.getString(Konfigurasi.TAG_NAMABARANG);
                String harga = jo.getString(Konfigurasi.TAG_HARGABARANG);
                String jumlah = jo.getString(Konfigurasi.TAG_JUMLAHBARANG);
                String diskon = jo.getString(Konfigurasi.TAG_DISKONBARANG);
                String tglBeli = jo.getString(Konfigurasi.TAG_TANGGALBELI);
                String namaKasir = jo.getString(Konfigurasi.TAG_NAMAKASIR);
                String totalBayar = jo.getString(Konfigurasi.TAG_TOTALBAYAR);

                HashMap<String,String> item = new HashMap<String,String>();
                item.put(Konfigurasi.TAG_KODEBARANG, kodeBarang);
                item.put(Konfigurasi.TAG_NAMABARANG,namaBarang);
                item.put(Konfigurasi.TAG_HARGABARANG, harga);
                item.put(Konfigurasi.TAG_JUMLAHBARANG,jumlah);
                item.put(Konfigurasi.TAG_DISKONBARANG,diskon);
                item.put(Konfigurasi.TAG_TANGGALBELI,tglBeli);
                item.put(Konfigurasi.TAG_NAMAKASIR,namaKasir);
                item.put(Konfigurasi.TAG_TOTALBAYAR,totalBayar);
                list.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //menampilkan data pada listview
        ListAdapter adapter = new SimpleAdapter(
                MainActivity2.this, list, R.layout.list_item,
                new String[]{
                        Konfigurasi.TAG_NAMABARANG,Konfigurasi.TAG_HARGABARANG,
                        Konfigurasi.TAG_JUMLAHBARANG,Konfigurasi.TAG_DISKONBARANG,Konfigurasi.TAG_TANGGALBELI,
                        Konfigurasi.TAG_NAMAKASIR,Konfigurasi.TAG_TOTALBAYAR
                },
                new int[]{
                        R.id.namaBarang, R.id.hargaBarang, R.id.jumlahBarang,
                        R.id.diskonBarang, R.id.tanggalBeli, R.id.namaKasir, R.id.totalBayar
                });
        listView.setAdapter(adapter);
    }
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        MainActivity2.this,
                        "Mengambil Data",
                        "Mohon Tunggu...",
                        false,
                        false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showBarang();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Konfigurasi.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent = new Intent(this, TampilBarang.class);
        HashMap<String,String> map
                =(HashMap)parent.getItemAtPosition(position);
        String kodeBar = map.get(Konfigurasi.TAG_KODEBARANG).toString();
        intent.putExtra(Konfigurasi.KODEBARANG,kodeBar);
        startActivity(intent);
    }
}