 <?php
$servername = "HOST";
$username = "USER";
$password = "PASSWORD";
$dbname = "DBNAME";
$gebruikersid = $_SESSION["id"];

$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "select * from betausers WHERE id = '$gebruikersid'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        $gebruiker2 = $row["username"];
	$rang = $row["rank"];
	$ranglevel = $row["ranglevel"];
    }
} else {
	
}
$conn->close();
?> 