<?php 
include "connect.php";
$query = "SELECT * FROM `sanphammoi` ORDER BY id DESC ";
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