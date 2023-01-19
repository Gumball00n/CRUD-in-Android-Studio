kodebarang<?php 
//Import File Koneksi Database
require_once('koneksi.php');
//Membuat SQL Query
$sql = "SELECT * FROM tbbarang";
//Mendapatkan Hasil
$r = mysqli_query($con,$sql);
//Membuat Array Kosong 
$result = array();
while($row = mysqli_fetch_array($r)){
//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
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
}
//Menampilkan Array dalam Format JSON
echo json_encode(array('result'=>$result));
mysqli_close($con);
?>
