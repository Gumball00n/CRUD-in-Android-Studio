package com.example.programkasir;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{
    //deklarasi variabel
    final Calendar myCalendar= Calendar.getInstance();
    Spinner txtNamaBarang, txtJumlahBarang, txtNamaKasir;
    EditText txtHargaBarang, txtDiskonBarang, txtTanggalBeli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mengambil id dari activity_main2.xml untuk di pakai di variabel disebelah kiri
        txtNamaBarang = (Spinner) findViewById(R.id.txtNamaBarang);
        txtHargaBarang = (EditText) findViewById(R.id.txtHargaBarang);
        txtJumlahBarang = (Spinner) findViewById(R.id.txtJumlahBarang);
        txtDiskonBarang = (EditText) findViewById(R.id.txtDiskonBarang);
        txtTanggalBeli = (EditText) findViewById(R.id.txtTanggalBeli);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        txtTanggalBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        txtNamaKasir = (Spinner) findViewById(R.id.txtNamaKasir);

        Button btnTampilData = findViewById(R.id.btnTampilData);
        //mensetting button btnTampilData agar ketika diklik
        //dapat menampilkan MainActivity2
        btnTampilData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent tampil = new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(tampil);
            }
        });
    }

    private void updateLabel(){
        String myFormat="dd MMMM yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ENGLISH);
        txtTanggalBeli.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void btnSimpan(View view) {
        //mengambil isi dari variabel yang dikanan untuk disimpan ke variabel yang dikiri
        String namabarang = txtNamaBarang.getSelectedItem().toString();
        String hargabarang = txtHargaBarang.getText().toString();
        String jumlahbarang = txtJumlahBarang.getSelectedItem().toString();
        String diskonbarang = txtDiskonBarang.getText().toString();
        String tanggalbeli = txtTanggalBeli.getText().toString();
        String namakasir = txtNamaKasir.getSelectedItem().toString();

        int intHarga, intJum, intDis, totalbayar;
        intHarga = Integer.parseInt(hargabarang);
        intJum = Integer.parseInt(jumlahbarang);
        intDis = Integer.parseInt(diskonbarang);

        totalbayar = (intHarga*intJum)-(intHarga*intJum*intDis/100);

        //mengeksekusi class object dengan parameternya
        simpanData simpen = new simpanData(this);
        simpen.execute(
                namabarang, hargabarang,
                jumlahbarang, diskonbarang,
                tanggalbeli, namakasir,
                String.valueOf(totalbayar)
        );
    }
}