<?php
/**
 * Class WHMCS_License
 * Cracked by SHAW 2013/3/23
 * QQ:71781  EMAIL:71781@QQ.COM
 */
class WHMCS_License
{
    protected $licensekey = '';
    protected $localkey = '';
    protected $keydata = array();
    protected $salt = '';
    protected $date = '';
    protected $localkeydecoded = false;
    protected $responsedata = '';
    protected $forceremote = false;
    protected $postmd5hash = '';
    protected $releasedate = '20130314';
    protected $localkeydays = '10';
    protected $allowcheckfaildays = '5';
    protected $debuglog = array();
    protected $version = '9eb7da5f081b3fc7ae1e460afdcb89ea8239eca1';

    function __construct() { }

    function init()
    {
        global $whmcs;

        $obj = new WHMCS_License();
        $obj->licensekey = $whmcs->get_license_key();
        $obj->localkey = $whmcs->get_config('License');
        $obj->salt = sha1('WHMCS' . $whmcs->get_config('Version') . 'TFB' . $whmcs->get_hash());
        $obj->date = date('Ymd');

        return $obj;
    }

    function getHosts()
    {
        return '';
    }

    function getLicenseKey()
    {
        return $this->licensekey;
    }

    function getHostIP()
    {
        return (isset($_SERVER['SERVER_ADDR']) ? $_SERVER['SERVER_ADDR'] : (isset($_SERVER['LOCAL_ADDR']) ? $_SERVER['LOCAL_ADDR'] : ''));
    }

    function getHostDomain()
    {
        return $_SERVER['SERVER_NAME'];
    }

    function getHostDir()
    {
        return ROOTDIR;
    }

    function getSalt()
    {
        return $this->salt;
    }

    function getDate()
    {
        return $this->date;
    }

    function checkLocalKeyExpiry()
    {
        return true;
    }

    function remoteCheck()
    {
        global $CONFIG;
        $this->setKeyData(array(
            'registeredname' => $CONFIG['CompanyName'],
            'productname' => 'Owned License No Branding',
            'status' => 'Active',
            'latestversion' => $CONFIG['Version'],
            'validips' => $this->getHostIP(),
            'validdomains' => "{$_SERVER["HTTP_HOST"]},www.{$_SERVER["HTTP_HOST"]}",
            'validdirs' => ROOTDIR,
            'nextduedate' => '过你妹',
            'requiresupdates' => false,
            'addons' => array(
                array('name' => 'Branding Removal', 'nextduedate' => '2050-12-30', 'status' => 'Active'),
                array('name' => 'Support and Updates', 'nextduedate' => '2050-12-30', 'status' => 'Active'),
                array('name' => 'Mobile Edition', 'nextduedate' => '2050-12-30', 'status' => 'Active'),
                array('name' => 'iPhone App', 'nextduedate' => '2050-12-30', 'status' => 'Active'),
                array('name' => 'Android App', 'nextduedate' => '2050-12-30', 'status' => 'Active'),
                array('name' => 'Configurable Package Addon', 'nextduedate' => '2050-12-30', 'status' => 'Active'),
            )
        ));

        $this->debug('Remote Check Done');
        return true;
    }

    function getLocalMaxExpiryDate()
    {
        return 0;
    }

    function buildQuery($postfields)
    {
        $query_string = '';
        foreach ($postfields as $k => $v) {
            $query_string .= '' . $k . '=' . urlencode($v) . '&';
        }

        return $query_string;
    }

    function callHome($postfields)
    {
    }

    function callHomeLoop($query_string, $timeout = 5)
    {
    }

    function makeCall($ip, $query_string, $timeout = 5)
    {
    }

    function processResponse($data)
    {
    }

    function updateLocalKey()
    {
    }

    function forceRemoteCheck()
    {
        $this->forceremote = true;
        $this->remoteCheck();
    }

    function setInvalid($reason = 'Invalid')
    {
    }

    function decodeLocal()
    {
        return true;
    }

    function decodeLocalOnce()
    {
        return true;
    }

    function isRunningInCLI()
    {
        return (php_sapi_name() == 'cli' && empty($_SERVER['REMOTE_ADDR']));
    }

    function validateLocalKey()
    {
        return true;
    }

    function isValidDomain($domain)
    {
        $validdomains = $this->getArrayKeyData('validdomains');
        return in_array($domain, $validdomains);
    }

    function isValidIP($ip)
    {
        $validips = $this->getArrayKeyData('validips');
        return in_array($ip, $validips);
    }

    function isValidDir($dir)
    {
        $validdirs = $this->getArrayKeyData('validdirs');
        return in_array($dir, $validdirs);
    }

    function revokeLocal()
    {
    }

    function getKeyData($var)
    {
        return (isset($this->keydata[$var]) ? $this->keydata[$var] : '');
    }

    function setKeyData($data)
    {
        $this->keydata = $data;
    }

    function getArrayKeyData($var)
    {
        $data = explode(',', $data);
        $data = $this->getKeyData($var);
        foreach ($data as $k => $v) {
            $data[$k] = trim($v);
        }

        return $data;
    }

    function getProductName()
    {
        return $this->getKeyData('productname');
    }

    function getStatus()
    {
        return $this->getKeyData('status');
    }

    function getSupportAccess()
    {
        return $this->getKeyData('supportaccess');
    }

    function getReleaseDate()
    {
        return str_replace('-', '', $this->releasedate);
    }

    function getActiveAddons()
    {
        $addons = array();
        foreach ($this->getKeyData('addons') as $addon) {

            if ($addon['status'] == 'Active') {
                $addons[] = $addon['name'];
                continue;
            }
        }

        return $addons;
    }

    function isActiveAddon($addon)
    {
        return (in_array($addon, $this->getActiveAddons()) ? true : false);
    }

    function getLatestVersion()
    {
        return $this->getKeyData('latestversion');
    }

    function getRequiresUpdates()
    {
        return ($this->getKeyData('requiresupdates') ? true : false);
    }

    function checkOwnedUpdates()
    {
        if (!$this->getRequiresUpdates()) {
            return true;
        }

        foreach ($this->getKeyData('addons') as $addon) {

            if ($addon['name'] == 'Support and Updates') {
                if ($this->getReleaseDate() < str_replace('-', '', $addon['nextduedate'])) {
                    return true;
                }

                continue;
            }
        }

        return false;
    }

    function getBrandingRemoval()
    {
        return true;
    }

    function getVersionHash()
    {
        return $this->version;
    }

    function debug($msg)
    {
        $this->debuglog[] = '' . $msg . '<br />';
    }
}

?>
