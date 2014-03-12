 <?php
 
 $url = "http://www.yourdomain.com/whmcs/includes/api.php"; # URL to WHMCS API file
 $username = "Admin"; # Admin username goes here
 $password = "demo"; # Admin password goes here
 
 $postfields["username"] = $username;
 $postfields["password"] = md5($password);
 $postfields["action"] = "addinvoicepayment"; #action performed by the [[API:Functions]]
 $postfields["invoiceid"] = "1";
 $postfields["transid"] = "TEST";
 $postfields["gateway"] = "mailin";
 
 $ch = curl_init();
 curl_setopt($ch, CURLOPT_URL, $url);
 curl_setopt($ch, CURLOPT_POST, 1);
 curl_setopt($ch, CURLOPT_TIMEOUT, 100);
 curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
 curl_setopt($ch, CURLOPT_POSTFIELDS, $postfields);
 $data = curl_exec($ch);
 curl_close($ch);
 
 $data = explode(";",$data);
 foreach ($data AS $temp) {
   $temp = explode("=",$temp);
   $results[$temp[0]] = $temp[1];
 }
  
 if ($results["result"]=="success") {
   # Result was OK!
    echo "result is OK";
 } else {
   # An error occured
   echo "The following error occured: ".$results["message"];
 }
 
 ?>
