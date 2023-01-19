package com.example.programkasir;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class simpanData extends AsyncTask<String, Void, String>{

    AlertDialog dialog;
    Context context;
    public simpanData(Context context) {
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Status");
    }
    @Override
    protected void onPostExecute(String s) {
        dialog.setMessage(s);
        dialog.show();
    }
    @Override
    //mengambil nilai pada class object simpen di MainActivity sesuai urutan parameterny
    protected String doInBackground(String... voids) {
        String result = "";
        String namaBarang = voids[0];
        String harga = voids[1];
        String jumlah = voids[2];
        String diskon = voids[3];
        String tglBeli = voids[4];
        String namaKasir = voids[5];
        String total = voids[6];
        try {
            URL url = new URL(Konfigurasi.URL_ADD_DATA);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);
            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            String data = URLEncoder.encode("namabarang", "UTF-8") + "=" +
                    URLEncoder.encode(namaBarang, "UTF-8")
                    + "&&" + URLEncoder.encode("hargabarang", "UTF-8") + "=" +
                    URLEncoder.encode(harga, "UTF-8")
                    + "&&" + URLEncoder.encode("jumlahbarang", "UTF-8") + "=" +
                    URLEncoder.encode(jumlah, "UTF-8")
                    + "&&" + URLEncoder.encode("diskonbarang", "UTF-8") +
                    "=" + URLEncoder.encode(diskon, "UTF-8")
                    + "&&" + URLEncoder.encode("tanggalbeli", "UTF-8") + "=" +
                    URLEncoder.encode(tglBeli, "UTF-8")
                    + "&&" + URLEncoder.encode("namakasir", "UTF-8") + "=" +
                    URLEncoder.encode(namaKasir, "UTF-8")
                    + "&&" + URLEncoder.encode("totalbayar", "UTF-8") + "=" +
                    URLEncoder.encode(total, "UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();
            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
            String line = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            reader.close();
            ips.close();
            http.disconnect();
            return result;
        } catch (MalformedURLException e) {
            result = e.getMessage();
        } catch (IOException e) {
            result = e.getMessage();
        }
        return result;
    }
}
