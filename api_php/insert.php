<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
//Mendapatkan Nilai Variable
$namabarang = $_POST['namabarang'];
$hargabarang = $_POST['hargabarang'];
$jumlahbarang = $_POST['jumlahbarang'];
$diskonbarang = $_POST['diskonbarang'];
$tanggalbeli = $_POST['tanggalbeli'];
$namakasir = $_POST['namakasir'];
$totalbayar = $_POST['totalbayar'];
//Pembuatan Syntax SQL
$sql = "INSERT INTO tbkasir (namabarang, hargabarang, jumlahbarang, diskonbarang, tanggalbeli, namakasir, totalbayar) VALUES ('$namabarang','$hargabarang','$jumlahbarang', '$diskonbarang', '$tanggalbeli', '$namakasir', '$totalbayar')";
//Import File Koneksi database
require_once('koneksi.php');
//pesan ksekusi Query database
if(mysqli_query($con,$sql)){
echo 'Berhasil Menambahkan Barang';
}else{
echo 'Gagal Menambahkan Barang';
}
mysqli_close($con);
}
?>