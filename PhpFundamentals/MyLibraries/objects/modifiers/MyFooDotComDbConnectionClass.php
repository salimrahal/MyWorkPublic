<?php

class MyFooDotComDbConnectionClass extends MyDbConnectionClass {

    protected function createDbConnection() {
        return mysql_connect("foo.com");
    }

}

?>
