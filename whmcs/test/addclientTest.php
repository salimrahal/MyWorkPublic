 $command = "addclient";
 $adminuser = "admin";
 $values["firstname"] = "Test";
 $values["lastname"] = "User";
 $values["companyname"] = "WHMCS";
 $values["email"] = "demo@whmcs.com";
 $values["address1"] = "123 Demo Street";
 $values["city"] = "Demo";
 $values["state"] = "Florida";
 $values["postcode"] = "AB123";
 $values["country"] = "US";
 $values["phonenumber"] = "123456789";
 $values["password2"] = "demo";
 $values["customfields"] = base64_encode(serialize(array("1"=>"Google")));
 $values["currency"] = "1";
 
 $results = localAPI($command,$values,$adminuser);
