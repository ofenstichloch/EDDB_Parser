<?php

$system1 = $_POST['system1'];
$system2 = $_POST['system2'];

try {
 $conn = new PDO("mysql:host=localhost;dbname=admin_EDDN", "EDDN","");
  $stmt = $conn->prepare("SELECT x,y,z FROM Systems WHERE name=:system1 OR name=:system2");
    $stmt->bindParam(':system1', $system1);
    $stmt->bindParam(':system2', $system2);
    $stmt->execute();
    $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
    if(count($result) == 2){
    $distance = sqrt(
    	pow($result[1]['x']-$result[0]['x'], 2) +
    	pow($result[1]['y']-$result[0]['y'], 2) +
    	pow($result[1]['z']-$result[0]['z'], 2)
    	);


    echo $system1." at ".$result[0]['x'].",".$result[0]['y'].",".$result[0]['z']."\n";
    echo $system2." at ".$result[1]['x'].",".$result[1]['y'].",".$result[1]['z']."\n";
    echo "Distance: ".$distance." ly";
}else{
	echo "Error, DB did not return two Systems";
}
    }
catch(PDOException $e)
    {
    echo "Error: " . $e->getMessage();
    }
?>