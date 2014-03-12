<?php
class adminmenuView extends view {
    protected $_file = '';
    /**
     * Array for standart menu pages
     * @see initMenu method
     */
    protected $_options = array(
        
    );
    public function init() {
        $this->_file = __FILE__;
        $this->_options = array(
            array('title' => lang::_('Options'), 'capability' => 'manage_options', 'menu_slug' => 'toeoptions', 'function' => array(frame::_()->getModule('options')->getView(), 'getAdminPage')),
            array('title' => lang::_('Categories'), 'capability' => 'manage_options', 'menu_slug' => 'edit-tags.php?taxonomy=products_categories', 'function' => ''),
            array('title' => lang::_('Brands'), 'capability' => 'manage_options', 'menu_slug' => 'edit-tags.php?taxonomy=products_brands', 'function' => ''),
            array('title' => lang::_('Products'), 'capability' => 'manage_options', 'menu_slug' => 'edit.php?post_type='. S_PRODUCT, 'function' => ''),
            array('title' => lang::_('Orders'), 'capability' => 'manage_options', 'menu_slug' => 'orders', 'function' => array(frame::_()->getModule('order')->getView(), 'getAllOrders')),
        );
        if(frame::_()->getModule('log')) {
            $this->_options[] = array('title' => lang::_('Log'), 'capability' => 'manage_options', 'menu_slug' => 'toelog', 'function' => array(frame::_()->getModule('log')->getView(), 'getList'));
        }
		$this->_options = dispatcher::applyFilters('adminMenuOptions', $this->_options);
        add_action('admin_menu', array($this, 'initMenu'), 9);
        parent::init();
    }
    public function initMenu() {
        add_menu_page(lang::_('The One Ecommerce'), lang::_('Ready! Ecommerce'), 'manage_options', $this->_file, array($this, 'showMenuMainPage'));
        foreach($this->_options as $opt) {
            add_submenu_page($this->_file, lang::_($opt['title']), lang::_($opt['title']), $opt['capability'], $opt['menu_slug'], $opt['function']);
        }
		/**
		 * @deprecated as it's functionality was not done correctly
		 */
        /*if(frame::_()->inPlugin()) {
            frame::_()->addScript('openAdmMenu', S_JS_PATH. 'openAdmMenu.js', array('jquery'));
        }*/
    }
    public function showMenuMainPage() {
        $data = array();
        $data['system_info'] = array(
            'Operating System' => array('value' => PHP_OS),
            'PHP Version' => array('value' => PHP_VERSION),
            'Server Software' => array('value' => $_SERVER['SERVER_SOFTWARE']),
            'MySQL' => array('value' => mysql_get_server_info()),
            'PHP Safe Mode' => array('value' => ini_get('safe_mode') ? 'Yes' : 'No', 'error' => ini_get('safe_mode')),
            'PHP Allow URL Fopen' => array('value' => ini_get('allow_url_fopen') ? 'Yes' : 'No'),
            'PHP Memory Limit' => array('value' => ini_get('memory_limit')),
            'PHP Max Post Size' => array('value' => ini_get('post_max_size')),
            'PHP Max Upload Filesize' => array('value' => ini_get('upload_max_filesize')),
            'PHP Max Script Execute Time' => array('value' => ini_get('max_execution_time')),
            'PHP EXIF Support' => array('value' => extension_loaded('exif') ? 'Yes' : 'No'),
            'PHP EXIF Version' => array('value' => phpversion('exif')),
            'PHP XML Support' => array('value' => extension_loaded('libxml') ? 'Yes' : 'No', 'error' => !extension_loaded('libxml')),
            'PHP CURL Support' => array('value' => extension_loaded('curl') ? 'Yes' : 'No', 'error' => !extension_loaded('curl')),
        );
        $this->assign('version', frame::_()->getModule('options')->get('version'));
        $devQuestions = array(
            lang::_('adding products'),
            lang::_('plugin options'),
            lang::_('ecommerce template'),
            lang::_('report a bug'),
            lang::_('require new functionality'),
            lang::_('other'),
        );
        $temp = array(0 => lang::_('- Please choose -'));
        foreach($devQuestions as $q) 
            $temp[$q] = $q;
        $devQuestions = $temp;
        $this->assign('devQuestions', $devQuestions);
        $this->assign('data', $data);
        parent::display('mainpage');
    }
    /**
     * @deprecated
    public function showOptionsPage() {
        parent::display('options');
    }*/
    public function getFile() {
        return $this->_file;
    }
    public function getOptions() {
        return $this->_options;
    }
}

