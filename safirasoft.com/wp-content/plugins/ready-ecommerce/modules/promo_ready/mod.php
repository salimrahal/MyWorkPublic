<?php
class toecpromo_ready extends module {
	public function init() {
		parent::init();
		dispatcher::addFilter('templatesListToAdminTab', array($this, 'addPromoTemplates'));
		dispatcher::addFilter('adminOptModulesList', array($this, 'addPromoPayments'));
	}
	public function addPromoPayments($modules) {
		if(isset($modules['payment']) && !empty($modules['payment'])) {
			$promoPayments = array(
				array(
					'code' => 'pm2checkout',
					'label' => '2Checkout',
					'description' => '2Checkout Payment Module',
					'href' => 'http://readyshoppingcart.com/product/2checkout-payment-solution-add-on/',
				),
				array(
					'code' => 'paypalpro',
					'label' => 'PayPal Pro',
					'description' => 'PayPal Pro Payment Module',
					'href' => 'http://readyshoppingcart.com/product/paypal-pro/',
				),
				array(
					'code' => 'paypalexpress',
					'label' => 'PayPal Express',
					'description' => 'PayPal Express Payment Module',
					'href' => 'http://readyshoppingcart.com/product/paypal-express-plugin/',
				),
				array(
					'code' => 'paypal_buynow',
					'label' => 'PayPal - Buy Now button',
					'description' => 'PayPal - Buy Now button for your products',
					'href' => 'http://readyshoppingcart.com/product/paypal-express-buy-now-button/',
				),
				array(
					'code' => 'authorizenet_aim',
					'label' => 'Authorize.net AIM',
					'description' => 'Authorize.net AIM Payment Module',
					'href' => 'http://readyshoppingcart.com/product/authorize-net-aim-sim-gateway-add-on/',
				),
				array(
					'code' => 'authorizenet_dpm',
					'label' => 'Authorize.net DPM',
					'description' => 'Authorize.net DPM Payment Module',
					'href' => 'http://readyshoppingcart.com/product/authorize-net-aim-sim-gateway-add-on/',
				),
				array(
					'code' => 'authorizenet_sim',
					'label' => 'Authorize.net SIM',
					'description' => 'Authorize.net SIM Payment Module',
					'href' => 'http://readyshoppingcart.com/product/authorize-net-aim-sim-gateway-add-on/',
				),
				array(
					'code' => 'eprocessingnetwork',
					'label' => 'eProcessing Network',
					'description' => 'eProcessing Network Payment Module',
					'href' => 'http://readyshoppingcart.com/product/eprocessing-network-add-on/',
				),
				array(
					'code' => 'cybersource',
					'label' => 'CyberSource',
					'description' => 'CyberSource Payment Module',
					'href' => 'http://readyshoppingcart.com/product/cybersource-payment-gateway/',
				),
				array(
					'code' => 'dwolla',
					'label' => 'Dwolla',
					'description' => 'Dwolla Payment Module',
					'href' => 'http://readyshoppingcart.com/product/dwolla-gateway/',
				),
				array(
					'code' => 'e4_payments',
					'label' => 'E4 Payments',
					'description' => 'E4 Payments Payment Module',
					'href' => 'http://readyshoppingcart.com/product/global-gateway-e4-extension/',
				),
				array(
					'code' => 'firstdata',
					'label' => 'First Data Global Gateway',
					'description' => 'First Data Global Gateway Payment Module',
					'href' => 'http://readyshoppingcart.com/product/global-gateway-e4-extension/',
				),
				array(
					'code' => 'googlecheckout',
					'label' => 'GoogleCheckout',
					'description' => 'GoogleCheckout Payment Module',
					'href' => 'http://readyshoppingcart.com/product/google-wallet-payment-gateway/',
				),
				array(
					'code' => 'international_checkout',
					'label' => 'International Checkout',
					'description' => 'International Checkout Payment Module',
					'href' => 'http://readyshoppingcart.com/product/international-checkout-gateway/',
				),
				array(
					'code' => 'intuit',
					'label' => 'Intuit Payments',
					'description' => 'Intuit Payments Module',
					'href' => 'http://readyshoppingcart.com/product/intuit-payment-gateway/',
				),
				array(
					'code' => 'moneybookers',
					'label' => 'Moneybookers',
					'description' => 'Moneybookers Payment Module',
					'href' => 'http://readyshoppingcart.com/product/moneybookers-payment-method-for-wordpress/',
				),
				array(
					'code' => 'paymentexpress',
					'label' => 'PaymentExpress',
					'description' => 'PaymentExpress Payment Module',
					'href' => 'http://readyshoppingcart.com/product/payment-express-eftpos-payment-gateway/',
				),
				array(
					'code' => 'paytrace',
					'label' => 'PayTrace',
					'description' => 'PayTrace Payment Module',
					'href' => 'http://readyshoppingcart.com/product/paytrace-gateway/',
				),
			);
			foreach($promoPayments as $pMod) {
				if(!frame::_()->moduleExists($pMod['code'])) {
					$modules['payment'][] = $this->_preparePromoPaymentMod($pMod);
				}
			}
		}
		return $modules;
	}
	private function _preparePromoPaymentMod($pMod) {
		$pMod['id'] = '<span style="color: #1D7317; font-weight: bold;">PRO</span>';
		$pMod['type'] = 'payment';
		$pMod['href'] = $this->_preparePromoLink( $pMod['href'] );
		$pMod['action'] = '<a target="_blank" href="'. $pMod['href']. '" class="button button-primary button-large toeGreenButton toeGetModButton">'. lang::_('Get It!'). '</a>';
		return $pMod;
	}
	public function addPromoTemplates($templates) {
		if(!empty($templates) && is_array($templates)) {
			$allInstaledThemes = wp_get_themes();
			$allInstalledThemesNames = array();
			foreach($allInstaledThemes as $t) {
				if(is_object($t)) {
					$allInstalledThemesNames[] = $t->get('Name');
				}
			}
			$promoTemlates = array(
				'startcommerce' => array(
					'name' => 'StartCommerce',
					'description' => 'New e-commerce WordPress theme with shopping cart feature for Your online store that will make it really cool!',
					'prevImg' => $this->getModPath(). 'img/startcommerce.jpg',
					'href' => 'http://readyshoppingcart.com/product/start-commerce/',
					'isPromo' => true,
					'buttVal' => lang::_('Get PRO template'),
				),
				'the_venus' => array(
					'name' => 'The Venus',
					'description' => 'Simplicity is the key to the e-commerce and we release it in this theme!',
					'prevImg' => $this->getModPath(). 'img/the_venus.png',
					'href' => 'http://readyshoppingcart.com/product/venus-stylish-e-commerce/',
					'isPromo' => true,
					'buttVal' => lang::_('Get PRO template'),
				),
				'EntireSpace' => array(
					'name' => 'Entire Space Shop',
					'description' => 'Simple design and powerful options rise level of your WordPress shop to the top of online ecommerce.',
					'prevImg' => $this->getModPath(). 'img/EntireSpace.png',
					'href' => 'http://readyshoppingcart.com/product/entire-responsive-e-commerce-wp-theme/',
					'isPromo' => true,
					'buttVal' => lang::_('Get PRO template'),
				),
				'perfectecommerce' => array(
					'name' => 'PerfectEcommerce',
					'description' => 'Personalize any part of your WordPress webshop with different types of slideshows, custom shopping cart and checkout.',
					'prevImg' => $this->getModPath(). 'img/perfectecommerce.jpg',
					'href' => 'http://readyshoppingcart.com/product/perfect-wordpress-ecommerce-template/',
					'isPromo' => true,
					'buttVal' => lang::_('Get PRO template'),
				),
				'interior' => array(
					'name' => 'Finilia Interior',
					'description' => 'Interior WordPress E-commerce Template is best suited for users who like to make use a lots of graphics to their online store.',
					'prevImg' => $this->getModPath(). 'img/interior.png',
					'href' => 'http://readyshoppingcart.com/product/interior-wordpress-e-commerce-template/',
					'isPromo' => true,
					'buttVal' => lang::_('Get PRO template'),
				),
				'jeans' => array(
					'name' => 'Jeans theme',
					'description' => 'The theme is easy to configure, and offers integration options with Ready! ecommerce plugin.',
					'prevImg' => $this->getModPath(). 'img/jeans.png',
					'href' => 'http://readyshoppingcart.com/product/jeans-free-ecommerce-theme/',
					'isPromo' => true,
					'buttVal' => lang::_('Get FREE template'),
				),
				'ready_ecommerce_theme' => array(
					'name' => 'Ready! to Be',
					'description' => 'This free WordPress thee is a great way to kickstart your online business quickly!',
					'prevImg' => $this->getModPath(). 'img/ready_ecommerce_theme.png',
					'href' => 'http://readyshoppingcart.com/product/free-wordpress-e-commerce-theme/',
					'isPromo' => true,
					'buttVal' => lang::_('Get FREE template'),
				),
				'albecocommerce' => array(
					'name' => 'AlbecoCommerce',
					'description' => 'It’s include all professional options, easy to use and to configure.',
					'prevImg' => $this->getModPath(). 'img/albecocommerce.jpg',
					'href' => 'http://readyshoppingcart.com/product/albeco-free-wp-ecommerce-theme/',
					'isPromo' => true,
					'buttVal' => lang::_('Get FREE template'),
				),
			);
			foreach($promoTemlates as $i => $pTpl) {
				$promoTemlates[$i]['href'] = $this->_preparePromoLink($promoTemlates[$i]['href']);
			}
			foreach($promoTemlates as $i => $pTpl) {
				//if(in_array($pTpl['name'], $allInstalledThemesNames)) continue;
				$templates[] = (object) $pTpl;
			}
		}
		return $templates;
	}
	private function _preparePromoLink($link) {
		$link .= '?ref=user';
		return $link;
	}
}
