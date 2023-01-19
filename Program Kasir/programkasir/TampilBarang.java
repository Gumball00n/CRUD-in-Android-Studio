package com.example.programkasir;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TampilBarang extends AppCompatActivity  implements View.OnClickListener{
    private EditText editTextKodeBarang;
    private Spinner editTextNamaBarang;
    private EditText editTextHargaBarang;
    private Spinner editTextJumlahBarang;
    private EditText editTextDiskonBarang;
    private EditText editTextTanggalBeli;
    private Spinner editTextNamaKasir;
    private Button buttonUpdate;
    private Button buttonDelete;
    private String kodeBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_barang);
        Intent intent = getIntent();
        kodeBarang = intent.getStringExtra(Konfigurasi.KODEBARANG);
        editTextKodeBarang = (EditText) findViewById(R.id.editTextKodeBarang);
        editTextNamaBarang = (Spinner) findViewById(R.id.editTextNamaBarang);
        editTextHargaBarang = (EditText) findViewById(R.id.editTextHargaBarang);
        editTextJumlahBarang = (Spinner) findViewById(R.id.editTextJumlahBarang);
        editTextDiskonBarang = (EditText) findViewById(R.id.editTextDiskonBarang);
        editTextTanggalBeli = (EditText) findViewById(R.id.editTextTanggalBeli);
        editTextNamaKasir = (Spinner) findViewById(R.id.editTextNamaKasir);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        editTextKodeBarang.setText(kodeBarang);
        getBarang();
    }
    private void getBarang(){
        class GetBarang extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        TampilBarang.this,
                        "Fetching...",
                        "Wait...",
                        false,
                        false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showBarang(s);
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_GET_BAR,kodeBarang);
                return s;
            }
        }
        GetBarang gb = new GetBarang();
        gb.execute();
    }
    private void showBarang(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String namaBarang = c.getString(Konfigurasi.TAG_NAMABARANG);
            String harga = c.getString(Konfigurasi.TAG_HARGABARANG);
            String jumlah = c.getString(Konfigurasi.TAG_JUMLAHBARANG);
            String diskon = c.getString(Konfigurasi.TAG_DISKONBARANG);
            String tglBeli = c.getString(Konfigurasi.TAG_TANGGALBELI);
            String namaKasir = c.getString(Konfigurasi.TAG_NAMAKASIR);

            //editTextNamaBarang.setText(namaBarang);
            editTextHargaBarang.setText(harga);
            //editTextJumlahBarang.setText(jumlah);
            editTextDiskonBarang.setText(diskon + "%");
            editTextTanggalBeli.setText(tglBeli);
            //editTextNamaKasir.setText(namaKasir);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateBarang(){
        final String namaBarang = editTextNamaBarang.getSelectedItem().toString().trim();
        final String harga = editTextHargaBarang.getText().toString().trim();
        final String jumlah = editTextJumlahBarang.getSelectedItem().toString().trim();
        final String diskon = editTextDiskonBarang.getText().toString().trim();
        final String tglBeli = editTextTanggalBeli.getText().toString().trim();
        final String namaKasir = editTextNamaKasir.getSelectedItem().toString().trim();

        class UpdateBarang extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading =
                        ProgressDialog.show(TampilBarang.this,"Updating...","Wait...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilBarang.this,s,Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Konfigurasi.KEY_KODEBARANG,kodeBarang);
                hashMap.put(Konfigurasi.KEY_NAMABARANG,namaBarang);
                hashMap.put(Konfigurasi.KEY_HARGABARANG,harga);
                hashMap.put(Konfigurasi.KEY_JUMLAHBARANG,jumlah);
                hashMap.put(Konfigurasi.KEY_DISKONBARANG,diskon);
                hashMap.put(Konfigurasi.KEY_TANGGALBELI,tglBeli);
                hashMap.put(Konfigurasi.KEY_NAMAKASIR,namaKasir);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Konfigurasi.URL_UPDATE_BAR,hashMap);
                return s;
            }
        }
        UpdateBarang ub = new UpdateBarang();
        ub.execute();
    }
    private void deleteBarang(){
        class DeleteBarang extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilBarang.this, "Updating...",
                        "Tunggu...", false, false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilBarang.this, s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_DELETE_BAR, kodeBarang);
                return s;
            }
        }
        DeleteBarang de = new DeleteBarang();
        de.execute();
    }
    private void confirmDeleteBarang(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Data ini?");
                alertDialogBuilder.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteBarang();
                                startActivity(new Intent(TampilBarang.this,MainActivity2.class));
                            }
                        });
        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void onClick(View v) {
        if(v == buttonUpdate){
            updateBarang();
        }
        if(v == buttonDelete){
            confirmDeleteBarang();
        }
    } }