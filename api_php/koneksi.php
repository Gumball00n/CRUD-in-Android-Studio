<?php
//Mendefinisikan koneksi ke database
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','dbkasir');
//membuat koneksi dengan database
$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
?>