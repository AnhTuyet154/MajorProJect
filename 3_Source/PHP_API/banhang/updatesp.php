<?php 
include "connect.php";
$tensp = $_POST['tensp'];
$gia = $_POST['gia'];
$hinhanh = $_POST['hinhanh'];
$mota = $_POST['mota'];
$loai = $_POST['loai'];
$id = $_POST['id'];
$sl = $_POST['slsp'];

$query = 'UPDATE `sanphammoi` SET `tensp`="'.$tensp.'" , `giasp`='.$gia.',`hinhanh`="'.$hinhanh.'",`mota`="'.$mota.'",`loai`='.$loai.' , `slsp`='.$sl.'  WHERE `id`='.$id;
$data = mysqli_query($conn, $query);

if($data == true){
    $arr = [
        'success' => true,
        'message' => "Thành công",
    ];
}else{
    $arr = [
        'success' => false,
        'message' => "Không thành công",
    ];
}
print_r(json_encode($arr));
?>