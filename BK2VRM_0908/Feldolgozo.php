<!DOCUMENT HTML >

<html>
	<head>
		<meta http-equiv="content-type" content="text/html"; charset="UTF-8">
	</head>
	<body style="background-color:blue;">
	<div align="center">
		<fieldset style="background-color:cyan; width:500px" align="center">
		<legend> Felvett adatok:</legend>
		<table>
		<?php     
			echo ("Név:" . $_POST['nev']. "<br><br>");
			echo ("Jelszó:" . $_POST['jelszo']. "<br><br>");
			echo ("Kor:" . $_POST['kor']. "<br><br>");
			echo ("Kisujjméret:" . $_POST['magassag']. "<br><br>");
			echo ("Alvázszám:" . $_POST['telefonszam']. "<br><br>");
			echo ("Szorgalom:" . $_POST['egyid']. "<br><br>");
		?>	
		</table>
		</fieldset>
		</div>
	</body>
</html>
