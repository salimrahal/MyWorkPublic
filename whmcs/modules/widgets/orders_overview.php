<?php

if (!defined("WHMCS"))
	die("This file cannot be accessed directly");

function widget_orders_overview($vars) {
    global $_ADMINLANG,$chart;

    $title = $_ADMINLANG['home']['ordersoverview'];

    $args = array();
    $args['colors'] = '#80D044,#CCCCCC';
    $args['legendpos'] = 'top';
    $args['xlabel'] = 'Day of the Month';
    $args['ylabel'] = 'Number of Orders';
    $args['chartarea'] = '80,40,85%,70%';

    $content = $chart->drawChart('Area','orders',$args,'300px');

    return array('title'=>$title,'content'=>$content);

}

function chartdata_orders() {
        $chartdata = array();
        $chartdata['cols'][] = array('label'=>'Year','type'=>'string');
        $chartdata['cols'][] = array('label'=>'Completed Orders','type'=>'number');
        $chartdata['cols'][] = array('label'=>'Total Orders','type'=>'number');
        for ($i = 14; $i >= 0; $i--) {
            $date = mktime(0,0,0,date("m"),date("d")-$i,date("Y"));
            $number = get_query_val("tblorders","COUNT(*)","date LIKE '".date("Y-m-d",$date)."%' AND status='Active'");
            $number2 = get_query_val("tblorders","COUNT(*)","date LIKE '".date("Y-m-d",$date)."%'");
            $chartdata['rows'][] = array('c'=>array(array('v'=>date("dS",$date)),array('v'=>(int)$number),array('v'=>(int)$number2)));
        }
        echo json_encode($chartdata);
        exit;
}

add_hook("AdminHomeWidgets",1,"widget_orders_overview");

?>