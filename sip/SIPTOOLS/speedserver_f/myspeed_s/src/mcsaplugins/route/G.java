package mcsaplugins.route;

import java.util.StringTokenizer;

public class G
{
  public static route g_vrApplet;
  private static final String COPYRIGHTDATE = "2011";
  public static final String COPYRIGHTTEXT = "Copyright (c) 2011 Visualware, Inc.";
  public static long FADESTART = 0L;

  public static String getParameter(String paramString)
  {
    return g_vrApplet.getSetting(paramString, null);
  }

  public static boolean isProfessional()
  {
    return g_vrApplet.getSetting("pro", false);
  }

  public static boolean isWhoisPermitted()
  {
    return g_vrApplet.getSetting("whoispermitted", true);
  }

  public static boolean isDetachPermitted()
  {
    return g_vrApplet.getSetting("detachpermitted", true);
  }

  public static int getRGRouteGraphLabelThreshold()
  {
    return g_vrApplet.getSetting("rg-routegraphlabelthreshold", 0);
  }

  public static int getSummRouteGraphLabelThreshold()
  {
    return g_vrApplet.getSetting("summ-routegraphlabelthreshold", 0);
  }

  public static int getWarnMaxResponse()
  {
    return g_vrApplet.getSetting("numwarnmaxresponse", 180);
  }

  public static int getBadMaxResponse()
  {
    return g_vrApplet.getSetting("numbadmaxresponse", 300);
  }

  public static int getWarnAvgResponse()
  {
    return g_vrApplet.getSetting("numwarnavgresponse", 120);
  }

  public static int getBadAvgResponse()
  {
    return g_vrApplet.getSetting("numbadavgresponse", 200);
  }

  public static int getWarnNumHops()
  {
    return g_vrApplet.getSetting("numwarnhops", 15);
  }

  public static int getBadNumHops()
  {
    return g_vrApplet.getSetting("numbadhops", 25);
  }

  public static int getWarnMaxLoss()
  {
    return g_vrApplet.getSetting("numwarnmaxloss", 10);
  }

  public static int getBadMaxLoss()
  {
    return g_vrApplet.getSetting("numbadmaxloss", 50);
  }

  public static int getWarnDNSTime()
  {
    return g_vrApplet.getSetting("numwarndnstime", 100);
  }

  public static int getBadDNSTime()
  {
    return g_vrApplet.getSetting("numbaddnstime", 500);
  }

  public static int getErraticMS()
  {
    return g_vrApplet.getSetting("numerraticms", 50);
  }

  public static int getErraticPercent()
  {
    return g_vrApplet.getSetting("numerraticpercent", 50);
  }

  public static String getDefaultTraceIP()
  {
    return g_vrApplet.getSetting("ip", null);
  }

  public static String getTabs()
  {
    return "summary,graph,table,analysis,map,netnode";
  }

  public static boolean getShowTabSummary()
  {
    return g_vrApplet.getSetting("showtabsummary", true);
  }

  public static boolean getShowTabAnalysis()
  {
    return g_vrApplet.getSetting("showtabanalysis", true);
  }

  public static boolean getShowTabTable()
  {
    return g_vrApplet.getSetting("showtabtable", true);
  }

  public static boolean getShowTabNetNode()
  {
    return g_vrApplet.getSetting("showtabnetnode", true);
  }

  public static boolean getShowTabRouteGraph()
  {
    return g_vrApplet.getSetting("showtabgraph", true);
  }

  public static boolean getShowTabMap()
  {
    return g_vrApplet.getSetting("showtabmap", true);
  }

  public static String getMap()
  {
    return g_vrApplet.getSetting("mapname", "bpol");
  }

  public static int getMapLineColour()
  {
    String str = g_vrApplet.getSetting("maplinecolor", null);
    try
    {
      return Integer.parseInt(str.substring(1), 16);
    }
    catch (Exception localException)
    {
    }
    return 0;
  }

  public static int getMapMaxZoom()
  {
    return 4;
  }

  public static boolean isMapShowLabels()
  {
    return g_vrApplet.getSetting("mapshowlabels", true);
  }

  public static boolean isTraceTargetEditable()
  {
    return g_vrApplet.getSetting("allowtargetedit", true);
  }

  public static boolean isRTATraceAvailable()
  {
    return g_vrApplet.getSetting("allowntas", true);
  }

  public static String getRTAs()
  {
    return g_vrApplet.RC("mss.rtas", "#NO");
  }

  public static boolean isMyComputerTraceAvailable()
  {
    return g_vrApplet.getSetting("allowmycomputer", true);
  }

  public static boolean isLogonApplet()
  {
    return g_vrApplet.getSetting("login", false);
  }

  public static boolean isAppletDefaultPassword()
  {
    return g_vrApplet.getSetting("defpass", false);
  }

  public static String getParisDisabledText()
  {
    return g_vrApplet.getSetting("netnodedisabled", "The Omnipath tracing system is currently switched off.");
  }

  public static String getParisHelpText()
  {
    return g_vrApplet.getSetting("netnodehelp", "Mouse-over any network node for statistics. Click and drag to move.");
  }

  public static String getParisInitText()
  {
    return g_vrApplet.getSetting("netnodeinit", "Omnipath discovers all routes to the destination");
  }

  public static String getParisFirewallText()
  {
    return g_vrApplet.getSetting("netnodefirewall", "Firewall");
  }

  public static String getParisNoResponsesText()
  {
    return g_vrApplet.getSetting("netnodenoresp", "No ICMP/UDP responses (firewalled?)");
  }

  public static String getParisNoResponsesSectionText()
  {
    return g_vrApplet.getSetting("netnodenorespsection", "No responses for this section of the route");
  }

  public static String getParisOptionsShow()
  {
    return g_vrApplet.getSetting("netnodeshowopt", "Show Options");
  }

  public static String getParisOptionsHide()
  {
    return g_vrApplet.getSetting("netnodehideopt", "Hide Options");
  }

  public static boolean getParis()
  {
    return g_vrApplet.iniGetTestBoolean("doomnitrace");
  }

  public static boolean getAllowLocal()
  {
    return g_vrApplet.iniGetTestBoolean("allowlocal");
  }

  public static int getPingsPerHop()
  {
    return g_vrApplet.iniGetTestInt("pingsperhop", 7);
  }

  public static int getPingTimeout()
  {
    return g_vrApplet.iniGetTestInt("pingtimeout", 8000);
  }

  public static int getNullHop()
  {
    return g_vrApplet.iniGetTestInt("nullhop", 4);
  }

  public static int getNullHopsRow()
  {
    return g_vrApplet.iniGetTestInt("nullterminate", 4);
  }

  public static int getPingsPerNode()
  {
    return g_vrApplet.iniGetTestInt("pingspernode", 4);
  }

  public static int getNodeTimeout()
  {
    return g_vrApplet.iniGetTestInt("nodetimeout", 6000);
  }

  public static int getNodeMSLower()
  {
    return 0;
  }

  public static int getNodeMSUpper()
  {
    return 0;
  }

  public static int getNodePktLossLower()
  {
    return 0;
  }

  public static int getNodePktLossUpper()
  {
    return 0;
  }

  public static String getLimitReachedUrl()
  {
    return g_vrApplet.getSetting("limitreachedurl", null);
  }

  public static int[] getMsColourTable(String paramString)
  {
    // Byte code:
    //   0: getstatic 31	mcsaplugins/route/G:g_vrApplet	Lmcsaplugins/route/route;
    //   3: new 253	java/lang/StringBuilder
    //   6: dup
    //   7: aload_0
    //   8: invokestatic 255	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   11: invokespecial 259	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   14: ldc_w 262
    //   17: invokevirtual 264	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   20: invokevirtual 268	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   23: aconst_null
    //   24: invokevirtual 33	mcsaplugins/route/route:getSetting	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   27: astore_1
    //   28: new 271	java/util/StringTokenizer
    //   31: dup
    //   32: aload_1
    //   33: ldc_w 273
    //   36: invokespecial 275	java/util/StringTokenizer:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   39: astore_2
    //   40: aload_2
    //   41: invokevirtual 278	java/util/StringTokenizer:countTokens	()I
    //   44: iconst_2
    //   45: irem
    //   46: ifeq +11 -> 57
    //   49: new 281	java/lang/IllegalArgumentException
    //   52: dup
    //   53: invokespecial 283	java/lang/IllegalArgumentException:<init>	()V
    //   56: athrow
    //   57: aload_2
    //   58: invokevirtual 278	java/util/StringTokenizer:countTokens	()I
    //   61: newarray int
    //   63: astore_3
    //   64: iconst_0
    //   65: istore 4
    //   67: goto +62 -> 129
    //   70: aload_3
    //   71: iload 4
    //   73: aload_2
    //   74: invokevirtual 284	java/util/StringTokenizer:nextToken	()Ljava/lang/String;
    //   77: invokevirtual 287	java/lang/String:trim	()Ljava/lang/String;
    //   80: invokestatic 290	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   83: iastore
    //   84: aload_2
    //   85: invokevirtual 284	java/util/StringTokenizer:nextToken	()Ljava/lang/String;
    //   88: invokevirtual 287	java/lang/String:trim	()Ljava/lang/String;
    //   91: astore 5
    //   93: aload_3
    //   94: iload 4
    //   96: iconst_1
    //   97: iadd
    //   98: aload 5
    //   100: ldc_w 293
    //   103: invokevirtual 295	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   106: ifeq +12 -> 118
    //   109: aload 5
    //   111: iconst_1
    //   112: invokevirtual 132	java/lang/String:substring	(I)Ljava/lang/String;
    //   115: goto +5 -> 120
    //   118: aload 5
    //   120: bipush 16
    //   122: invokestatic 138	java/lang/Integer:parseInt	(Ljava/lang/String;I)I
    //   125: iastore
    //   126: iinc 4 2
    //   129: iload 4
    //   131: aload_3
    //   132: arraylength
    //   133: if_icmplt -63 -> 70
    //   136: aload_3
    //   137: areturn
    //   138: astore_2
    //   139: aload_2
    //   140: invokevirtual 298	java/lang/Exception:printStackTrace	()V
    //   143: ldc_w 301
    //   146: aload_0
    //   147: invokevirtual 303	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   150: ifeq +37 -> 187
    //   153: bipush 6
    //   155: newarray int
    //   157: dup
    //   158: iconst_1
    //   159: ldc_w 307
    //   162: iastore
    //   163: dup
    //   164: iconst_2
    //   165: bipush 100
    //   167: iastore
    //   168: dup
    //   169: iconst_3
    //   170: ldc_w 308
    //   173: iastore
    //   174: dup
    //   175: iconst_4
    //   176: sipush 200
    //   179: iastore
    //   180: dup
    //   181: iconst_5
    //   182: ldc_w 309
    //   185: iastore
    //   186: areturn
    //   187: bipush 10
    //   189: newarray int
    //   191: dup
    //   192: iconst_1
    //   193: ldc_w 310
    //   196: iastore
    //   197: dup
    //   198: iconst_2
    //   199: bipush 50
    //   201: iastore
    //   202: dup
    //   203: iconst_3
    //   204: ldc_w 311
    //   207: iastore
    //   208: dup
    //   209: iconst_4
    //   210: bipush 90
    //   212: iastore
    //   213: dup
    //   214: iconst_5
    //   215: ldc_w 312
    //   218: iastore
    //   219: dup
    //   220: bipush 6
    //   222: sipush 150
    //   225: iastore
    //   226: dup
    //   227: bipush 7
    //   229: ldc_w 313
    //   232: iastore
    //   233: dup
    //   234: bipush 8
    //   236: sipush 300
    //   239: iastore
    //   240: dup
    //   241: bipush 9
    //   243: ldc_w 314
    //   246: iastore
    //   247: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   28	137	138	java/lang/Exception
  }

  public static String getJavascript()
  {
    return g_vrApplet.getSetting("javascript", null);
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.G
 * JD-Core Version:    0.6.2
 */