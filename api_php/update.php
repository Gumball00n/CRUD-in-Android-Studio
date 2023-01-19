<?php 
if($_SERVER['REQUEST_METHOD']=='POST'){
//MEndapatkan Nilai Dari Variable
$kodebarang = $_POST['kodebarang']; 
$namabarang = $_POST['namabarang'];
$hargabarang = $_POST['hargabarang'];
$jumlahbarang = $_POST['jumlahbarang'];
$diskonbarang = $_POST['diskonbarang'];
$tanggalbeli = $_POST['tanggalbeli'];
$namakasir = $_POST['namakasir'];
$totalbayar = ($harga * $jumlah) - ($harga * $jumlah * $diskon/100);
//import file koneksi database 
require_once('koneksi.php');
//Membuat SQL Query
$sql = "UPDATE tbkasir SET namabarang = '$namabarang', hargabarang = '$hargabarang', jumlahbarang = '$jumlahbarang', diskonbarang = '$diskonbarang', tanggalbeli = '$tanggalbeli',
namakasir = '$namakasir', totalbayar = '$totalbayar' 
WHERE kodebarang = $kodebarang;";
//Meng-update Database 
if(mysqli_query($con,$sql)){
echo 'Berhasil Update Data Barang';
}else{
echo 'Gagal Update Data Barang';
}
mysqli_close($con);
}
?>
