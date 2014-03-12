<!-- http://requirejs.org/docs/api.html#jsonp -->

<!DOCTYPE html><html xmlns:fb="http://ogp.me/ns/fb#" itemtype="http://schema.org">
    <head><meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=IE7">
        <meta name="fragment" content="!">
        <meta name="robots" content="NOODP">
        <meta charset="utf-8">
        <meta property="og:title" content="Coursera">
        <meta property="og:type" content="website">
        <meta property="og:image" content="http://s3.amazonaws.com/coursera/media/Coursera_Computer_Narrow.png">
        <meta property="og:url" content="https://www.coursera.org/">
        <meta property="og:site_name" content="Coursera">
        <meta property="og:locale" content="en_US">
        <meta property="og:description" content="Take free online classes from 80+ top universities and organizations. Coursera is a social entrepreneurship company partnering with Stanford University, Yale University, Princeton University and others around the world to offer courses online for anyone to take, for free. We believe in connecting people to a great education so that anyone around the world can learn without limits.">
        <meta property="fb:admins" content="727836538,4807654">
        <meta property="fb:app_id" content="274998519252278">
        <meta name="description" content="Take free online classes from 80+ top universities and organizations. Coursera is a social entrepreneurship company partnering with Stanford University, Yale University, Princeton University and others around the world to offer courses online for anyone to take, for free. We believe in connecting people to a great education so that anyone around the world can learn without limits."><meta name="image" content="http://s3.amazonaws.com/coursera/media/Coursera_Computer_Narrow.png">
        <script>window.onerror = function(message, url, lineNum) {

        // First check the URL and line number of the error
        url = url || window.location.href;
        // 99% of the time, errors without line numbers arent due to our code,
        // they are due to third party plugins and browser extensions
        if (lineNum === undefined || lineNum == null)
            return;

        // Now figure out the actual error message
        // If it's an event, as triggered in several browsers
        if (message.target && message.type) {
            message = message.type;
        }
        if (!message.indexOf) {
            message = 'Non-string, non-event error: ' + (typeof message);
        }

        var errorDescrip = {
            message: message,
            script: url,
            line: lineNum,
            url: document.URL
        }

        var err = {
            key: 'page.error.javascript',
            value: errorDescrip
        }

        window._204 = window._204 || [];
        window._204.push(err);

        window._gaq = window._gaq || [];
        window._gaq.push(err);
    }</script><title>Coursera.org</title>
        <link href="https://d2wvvaown1ul17.cloudfront.net/site-static/37068e8ae11aa6fb75cf5e885290263a82c6f633/css/home.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <div id="fb-root">           
        </div>
        <div id="origami">
        <div style="position:absolute;top:0px;left:0px;width:100%;height:100%;background:#f5f5f5;padding-top:5%;">
            <div id="coursera-loading-nojs" style="text-align:center; margin-bottom:10px;display:none;">
            <div>Please use a <a href="/browsers">modern browser </a> with JavaScript enabled to use Coursera.</div>
            <div id="get-browser-zh" style="display:none;">??????????<a href="http://windows.microsoft.com/zh-cn/internet-explorer/download-ie">IE10</a>?<a href="https://www.google.com/intl/zh-CN/chrome/browser/">Google Chrome</a>????Coursera?
            </div></div>
            <div><span id="coursera-loading-js" style="display: none; padding-left:45%"> loading &nbsp;&nbsp;<img src="https://d2wvvaown1ul17.cloudfront.net/site-static/images/icons/loading.gif"></span>
            </div><noscript>
            <div style="text-align:center; margin-bottom:10px;">Please use a <a href="/browsers">modern browser </a> with JavaScript enabled to use Coursera.
            </div></noscript></div></div><!--[if gte IE 9]><script>document.getElementById("coursera-loading-js").style.display = 'block';</script><![endif]-->
        <!--[if lte IE 8]><script>document.getElementById("coursera-loading-nojs").style.display = 'block';
        window._204 = window._204 || [];
        window._gaq = window._gaq || [];
        
        window._gaq.push(
            ['_setAccount', 'UA-28377374-1'],
            ['_setDomainName', window.location.hostname],
            ['_setAllowLinker', true],
            ['_trackPageview', window.location.pathname]);
            
        window._204.push(
          ['client', 'home'],
          {key:"pageview", value:window.location.pathname});
          </script><script src="https://eventing.coursera.org/204.min.js"></script><script src="https://ssl.google-analytics.com/ga.js"></script><![endif]-->
        <!--[if !IE]> -->
        <script>document.getElementById("coursera-loading-js").style.display = 'block';</script>
        <!-- <![endif]--><script type="text/javascript" src="https://d2wvvaown1ul17.cloudfront.net/site-static/37068e8ae11aa6fb75cf5e885290263a82c6f633/js/core/require.js"></script>
        
        <script type="text/javascript" data-baseurl="https://d2wvvaown1ul17.cloudfront.net/site-static/37068e8ae11aa6fb75cf5e885290263a82c6f633/" data-version="37068e8ae11aa6fb75cf5e885290263a82c6f633" data-timestamp='1382417304363' data-debug='0' data-locale="en-US,en;q=0.5" id="_require">(function(el) {
        var locale = (window.localStorage ? localStorage.getItem('locale') : '') || el.getAttribute('data-locale');
        if (/zh/i.test(locale)) {
            document.getElementById('get-browser-zh').style.display = 'block';
        }
        if (document.getElementById("coursera-loading-js").style.display == 'block') {
            // prevent throw
            require.onError = function(err) {
                window._204 = window._204 || [];
                window._204.push({key: 'requireErr', value: err});
            };

            require.config({
                enforceDefine: true,
                waitSeconds: 14,
                baseUrl: el.getAttribute("data-baseurl"),
                urlArgs: el.getAttribute("data-debug") == "1" ? "v=" + el.getAttribute("data-timestamp") : "",
                shim: {
                    "underscore": {
                        exports: '_'
                    },
                    "backbone": {
                        deps: ['underscore', 'jquery'],
                        exports: 'Backbone'
                    }
                },
                paths: {
                    "jquery": "js/core/jquery",
                    "underscore": "js/core/underscore",
                    "backbone": "js/core/backbone",
                    "i18n": "js/core/i18n._t",
                    "pages/home/models/user.json": "empty:"
                },
                callback: function() {
                    require(["pages/home/routes"]); // bootup coursera
                },
                config: {
                    i18n: {
                        locale: locale
                    }
                }
            });
        }
    })(document.getElementById("_require"));
        </script><script type="text/javascript">define("pages/home/models/user.json", [], function() {
                var user = "undefined";
                return user && user !== "undefined" ? JSON.parse(user) : null;
            });</script></body></html>