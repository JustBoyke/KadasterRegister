<?php

session_start();
if(!isset($_SESSION["loggedin"]) || $_SESSION["loggedin"] !== true){
    header("location: https://portal.fire-mc.nl");
    exit;
}
?>

<?php
 require 'checkdata.php';
?>

<?php if ($ranglevel < "10"): ?>
<meta http-equiv='refresh' content='0; url=https://portal.fire-mc.nl/home.php' />
<?php endif ?>

<?php if ($ranglevel == "19"): ?>
<br>Toegang hiertoe is geblokkeerd omdat u nog geen toegang hebt gekregen van de PL / beheerder<br>
<br>Dit kan komen doordat u nog geen uitleg hebt gehad van het systeem!<br>
<meta http-equiv='refresh' content='10; url=https://portal.fire-mc.nl/home.php' />
<?php endif ?>

<?php if ($ranglevel > "19"): ?>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  <title>MT Info Console</title>
<p>
<ul class="nav nav-tabs">
  <li><a href="/home.php">< Portal</a></li>
  <li><a href="./index.php">Home</a></li>
    <li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Playerinfo
    <span class="caret"></span></a>
    <ul class="dropdown-menu">
      <li><a href="./playerinfo.php">Alle Spelers</a></li>
      <li><a href="./verified.php">Verified</a></li>
      <li><a href="./verzoeken.php">Verzoeken</a></li> 
    </ul>
  </li>
  <li><a href="./plotinfo.php">Plot Informatie</a></li>
</ul>
</p>
<?php endif ?>