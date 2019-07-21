<?php include "nav.php"; ?>
<center><form method = "post" action="#" class="form-inline active-cyan-3 active-cyan-4">
<div class="active-cyan-3 active-cyan-4 mb-4">
  <input class="form-control form-control-sm ml-3 w-75" name="kingdom" style="width:50%;" type="text" placeholder="Zoek Plot" aria-label="Search">
</div>
</form></center>
<div class="col-md-12" style="margin-bottom: 26px;">
<div class="table-responsive">
<?php

		
	echo "<table class='table table-hover'>";
    echo "<thead>";
      echo "<tr>";
        echo "<th>Plotnummer</th>";
        echo "<th>Bestemmingsplan</th>";
        echo "<th>Status / Prijs</th>";
      echo "</tr>";
    echo "</thead>";
    echo "<tbody>";
		
$servername = "HOST";
$username = "USER";
$password = "PASSWORD";
$dbname = "DATABASE";
$kingdom = $_POST['kingdom'];

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

if ($kingdom == null){
	$sql = "SELECT * FROM `KadasterRegister`";
}
else {
	$sql = "SELECT * FROM `KadasterRegister` WHERE plot = '$kingdom'";
}
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
		echo "<tr>";
        echo "<td> " . $row["plot"]. " </td>";
		echo "<td> " . $row["type"]. " </td>";
		echo "<td> " . $row["status"]. " </td>";
		echo "</tr>";
    }
} else {
    echo "<td> Geen Data </td>";
    echo "<td> Geen Data </td>";
    echo "<td> Geen Data </td>";
}
$conn->close();
