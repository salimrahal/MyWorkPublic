<?php
class tablerateHelper extends helper { 
    /**
     * Uses for html::textFieldsDynamicTable() method $params['options'] value
     * @return array
     */
    public function getRateTableOptions() {
        return array(
            'weight' => array('label' => lang::_('Weight')),
            'price' => array('label' => lang::_('Price')),
        );
    }
}
?>