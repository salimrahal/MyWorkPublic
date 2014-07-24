package myspeedserver.applet;

import java.applet.Applet;
import java.lang.reflect.Method;

public class JS
{
  public static Object js(Applet paramApplet, String paramString)
  {
    Class localClass = Class.forName("netscape.javascript.JSObject");
    Method localMethod1 = localClass.getMethod("getWindow", new Class[] { Applet.class });
    Method localMethod2 = localClass.getMethod("eval", new Class[] { String.class });
    Object localObject = localMethod1.invoke(null, new Object[] { paramApplet });
    return localMethod2.invoke(localObject, new Object[] { paramString });
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/myspeed_s.jar
 * Qualified Name:     myspeedserver.applet.JS
 * JD-Core Version:    0.6.2
 */