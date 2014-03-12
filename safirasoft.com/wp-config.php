<?php
/**
 * The base configurations of the WordPress.
 *
 * This file has the following configurations: MySQL settings, Table Prefix,
 * Secret Keys, WordPress Language, and ABSPATH. You can find more information
 * by visiting {@link http://codex.wordpress.org/Editing_wp-config.php Editing
 * wp-config.php} Codex page. You can get the MySQL settings from your web host.
 *
 * This file is used by the wp-config.php creation script during the
 * installation. You don't have to use the web site, you can just copy this file
 * to "wp-config.php" and fill in the values.
 *
 * @package WordPress
 */

// ** MySQL settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define('DB_NAME', 'safirasoft');

/** MySQL database username */
define('DB_USER', 'salimr_ei');

/** MySQL database password */
define('DB_PASSWORD', '1rynpfkj');
/*Cpanel: DB name: salim_safirasoftDB ; Added user “salim_salim” with password “salim00”. */ 
/** MySQL hostname */
define('DB_HOST', 'localhost');

/** Database Charset to use in creating database tables. */
define('DB_CHARSET', 'utf8');

/** The Database Collate type. Don't change this if in doubt. */
define('DB_COLLATE', '');

/**#@+
 * Authentication Unique Keys and Salts.
 *
 * Change these to different unique phrases!
 * You can generate these using the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}
 * You can change these at any point in time to invalidate all existing cookies. This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define('AUTH_KEY',         '}lg![pd2MX=Le)UuoKF]Kd/78g-y;J[5N2+u-^C[+_|ujS/Ba7:jx%-6M{5I.Sn4');
define('SECURE_AUTH_KEY',  'k+tz[N3S-$[]Qs7:e|Q/$|qR75^~CCNY`QV[OWs#qlJ(JOijUHud f%Z!GHr9TK8');
define('LOGGED_IN_KEY',    'aH+>j?fqM@,U%^HK[G`Gjls&VU~p+Y I*Ggflh&9lId-Y>8sbm)-|<,YizK[Axj_');
define('NONCE_KEY',        '}[@p>E{i3SUl8<-t9:?=AX%gTb2KET$W0xI|SG,m2jCPB16;2PR<A`g+-qv:TkkU');
define('AUTH_SALT',        '#&rQmb?z#5+-Mkk!Hj{+Y2UHh6hRB8}3>9w@VmB1=VT]%4p-jIMmeetw~|4=+><P');
define('SECURE_AUTH_SALT', 'b&U;GfjwvE^`R*f+#@|HOkZ}N^=Ke=<03e:i][mm)c)j6qP)Tv0WSA0ZvR:?]@HK');
define('LOGGED_IN_SALT',   '@Y2<%u!uI H)UaS:a6CF;l9!w~:_U_@m>?kqRYUx `[x%/0xhI0WDs;TzhljLmn4');
define('NONCE_SALT',       'ty|O VZ$Y-=Uj*Be-?khG&@2084$kMvkQU*}|T]_vbP9Yg9YA4yj|4 1nU_G<I?n');

/**#@-*/

/**
 * WordPress Database Table prefix.
 *
 * You can have multiple installations in one database if you give each a unique
 * prefix. Only numbers, letters, and underscores please!
 */
$table_prefix  = 'wp_';

/**
 * WordPress Localized Language, defaults to English.
 *
 * Change this to localize WordPress. A corresponding MO file for the chosen
 * language must be installed to wp-content/languages. For example, install
 * de_DE.mo to wp-content/languages and set WPLANG to 'de_DE' to enable German
 * language support.
 */
define('WPLANG', '');

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 */
define('WP_DEBUG', false);

/* That's all, stop editing! Happy blogging. */

/** Absolute path to the WordPress directory. */
if ( !defined('ABSPATH') )
	define('ABSPATH', dirname(__FILE__) . '/');

/** Sets up WordPress vars and included files. */
require_once(ABSPATH . 'wp-settings.php');
