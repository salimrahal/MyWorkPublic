<?php

class MyDbConnectionClass {

    public function connect() {
        $conn = $this->createDbConnection();
        $this->setDbConnection($conn);
        return $conn;
    }

    protected function createDbConnection() {
        return mysql_connect("localhost");
    }

    private function setDbConnection($conn) {
        $this->dbConnection = $conn;
    }

    private $dbConnection;

}

?>
