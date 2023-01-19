<?php 
//Mendapatkan Nilai ID
$kodebarang = $_GET['kodebarang'];
//Import File Koneksi Database
require_once('koneksi.php');
//Membuat SQL Query
$sql = "DELETE FROM kodebarang WHERE kodebarang=$kodebarang;";
//Menghapus Nilai pada Database 
if(mysqli_query($con,$sql)){
echo 'Berhasil Menghapus Barang';
}else{
echo 'Gagal Menghapus Barang';
}
mysqli_close($con);
?>