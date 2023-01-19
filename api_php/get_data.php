<?php
//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
$kodebarang = $_GET['kodebarang'];
//Importing database
require_once('koneksi.php');
//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
$sql = "SELECT * FROM tbbarang WHERE kodebarang=$kodebarang";
//Mendapatkan Hasil 
$r = mysqli_query($con,$sql);
//Memasukkan Hasil Kedalam Array
$result = array();
$row = mysqli_fetch_array($r);
array_push($result,array(
"kodebarang"=>$row['kodebarang'],
"namabarang"=>$row['namabarang'],
"hargabarang"=>$row['hargabarang'],
"jumlahbarang"=>$row['jumlahbarang'],
"diskonbarang"=>$row['diskonbarang'],
"tanggalbeli"=>$row['tanggalbeli'],
"namakasir"=>$row['namakasir'],
"totalbayar"=>$row['totalbayar'],
));
//Menampilkan dalam format JSON
echo json_encode(array('result'=>$result));
mysqli_close($con);
?>
