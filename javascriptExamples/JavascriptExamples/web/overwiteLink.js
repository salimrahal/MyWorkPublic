/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * http://tysonlloydcadenhead.com/blog/replacing-href-attributes-with-onclick-events-with-regex#.UnEx87OJR2M
 */


/*
 * Replacing href attributes with onClick events with Regex
 * It turn <a href="myLink.html">My Link</a>
 * into:
 * <a onClick="myFunction('myLink.html')">My Link</a>
 */
function overrideLink(functionName, str){

        // Remove links with blank href tags
        // For exampe: &lt;a href=""&gt;<a>This would break everything!&lt;/a&gt;</a>
        str = str.replace(/href=""/g, '');

        // Replaces links with onclick events
        return str.replace(/href=\"(.+?)\"/, 'onclick="' + functionName + '(\'$1\');"');
};

var myString = 'This is a string that contains &lt;a href="myLink.html"&gt;My Link&lt;/a&gt; inside of it.';

// This is how you call it
overrideLink('myFunctionName', myString);