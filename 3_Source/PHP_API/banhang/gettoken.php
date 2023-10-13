<?php 
include "connect.php";
$status = $_POST['status'];

$query = 'SELECT * FROM `user` WHERE `status`='.$status;
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)){
    $result[] = ($row);
    //code...
}

if(!empty($result)){
    $arr = [
        'success' => true,
        'message' => "Thanh cong",
        'result' => $result
    ];
}else{
    $arr = [
        'success' => false,
        'message' => "Khong hanh cong",
        'result' => $result
    ];
}
print_r(json_encode($arr));
?>