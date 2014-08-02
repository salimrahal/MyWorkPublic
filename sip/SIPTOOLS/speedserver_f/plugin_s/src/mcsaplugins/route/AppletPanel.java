package mcsaplugins.route;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import myspeedserver.applet.ErrorCode;

public class AppletPanel extends JPanel
  implements Runnable, ActionListener
{
  private static final String ERR_LOCAL = "ERRLOC";
  private static final String ERR_SECURE = "ERRSEC";
  private static final String ERR_DNS = "ERRDNS";
  private static final String ERR_RANGE = "ERRRAN";
  private final String THIS_COMPUTER;
  private JComboBox m_from = new JComboBox();
  private JTextField m_to = new JTextField(25);
  private JButton m_start = new JButton();
  private ProgressBar m_progBar = new ProgressBar(U.text("progress"));
  private Hashtable m_agents;
  private route m_applet;
  private ViewSummaryPanel m_summaryView;
  private ViewTablePanel m_tableView;
  private ViewMapPanel m_mapView;
  private ViewAnalysisPanel m_analysisView;
  private ViewGraphPanel m_graphView;
  private ViewNetworkNodePanel m_netView;
  private AppletViewPanel m_tSummary;
  private AppletViewPanel m_tTable;
  private AppletViewPanel m_tMap;
  private AppletViewPanel m_tAnalysis;
  private AppletViewPanel m_tGraph;
  private AppletViewPanel m_tNet;
  private HeadingPanel m_heading;
  private TabPanel m_tabs;
  private TraceRoute m_tr = null;
  private int m_nDNSTime = -1;
  private String m_enteredName = null;
  private String m_error;
  private Thread m_thread;
  private boolean m_bRunning;
  private String m_host;
  private String m_prot;
  private int m_port;
  private boolean m_bRun;
  private String m_myCompInst = null;
  private String m_myCompLink = null;
  private Thread m_fade;
  private boolean m_bOverrideAllowLocal = false;

  public AppletPanel(route paramroute, RouteTab paramRouteTab, boolean paramBoolean)
  {
    this.m_applet = paramroute;
    this.THIS_COMPUTER = U.text("mycomputer");
    this.m_agents = new Hashtable();
    this.m_heading = new HeadingPanel(U.text("begintext"));
    this.m_tabs = new TabPanel(paramroute, this, (paramBoolean) || (!G.isDetachPermitted()));
    this.m_summaryView = new ViewSummaryPanel(this.m_applet, this);
    this.m_tableView = new ViewTablePanel(this);
    this.m_mapView = new ViewMapPanel(this.m_applet, this);
    this.m_analysisView = new ViewAnalysisPanel(this);
    this.m_graphView = new ViewGraphPanel(this);
    this.m_netView = new ViewNetworkNodePanel(this);
    this.m_tSummary = new AppletViewPanel(U.text("summary"), this.m_summaryView);
    this.m_tTable = new AppletViewPanel(U.text("table"), this.m_tableView);
    this.m_tMap = new AppletViewPanel(U.text("map"), this.m_mapView);
    this.m_tAnalysis = new AppletViewPanel(U.text("analysis"), this.m_analysisView);
    this.m_tGraph = new AppletViewPanel(U.text("graph"), this.m_graphView);
    this.m_tNet = new AppletViewPanel(U.text("netnode"), this.m_netView);
    Object localObject = null;
    if (G.getShowTabSummary())
    {
      localObject = localObject == null ? this.m_tSummary : localObject;
      this.m_tabs.addTab(this.m_tSummary);
    }
    if (G.getShowTabTable())
    {
      localObject = localObject == null ? this.m_tTable : localObject;
      this.m_tabs.addTab(this.m_tTable);
    }
    if (G.getShowTabMap())
    {
      localObject = localObject == null ? this.m_tMap : localObject;
      this.m_tabs.addTab(this.m_tMap);
    }
    if (G.getShowTabAnalysis())
    {
      localObject = localObject == null ? this.m_tAnalysis : localObject;
      this.m_tabs.addTab(this.m_tAnalysis);
    }
    if (G.getShowTabRouteGraph())
    {
      localObject = localObject == null ? this.m_tGraph : localObject;
      this.m_tabs.addTab(this.m_tGraph);
    }
    if ((G.getShowTabNetNode()) && (G.getParis()))
    {
      localObject = localObject == null ? this.m_tNet : localObject;
      this.m_tabs.addTab(this.m_tNet);
    }
    setBackground(new Color(12573695));
    setLayout(new GridBagLayout());
    String str = "";
    str = this.m_applet.getTraceList().length == 1 ? U.text(this.m_applet.getTraceFrom() == null ? "beginautosingular" : "beginautosingularmycomp", this.m_applet.getTraceList()[0]) : U.text("beginautomultiple", new String[] { this.m_applet.getTraceList()[0], this.m_applet.getTraceList().length });
    if (this.m_applet.isDatabaseViewMode())
    {
      this.m_heading.setText("This is a previous trace restored from the database");
      U.gbAdd(this, this.m_heading, 1, 2, "x1f1");
    }
    else if (this.m_applet.isUserDriven())
    {
      U.gbAdd(this, getControlsPanel(), 1, 1, "x1f1");
      U.gbAdd(this, this.m_heading, 1, 2, "x1f1");
    }
    else if (this.m_applet.isUserStart())
    {
      str = str + " " + U.text("promptstart");
      this.m_heading.setText(str);
      this.m_start.setText(U.text("start"));
      JPanel localJPanel = new JPanel();
      localJPanel.setLayout(new GridBagLayout());
      U.gbAdd(localJPanel, this.m_heading, 0, 0, "x1y1f3");
      U.gbAdd(localJPanel, this.m_start, 1, 0, "y1");
      U.gbAdd(this, localJPanel, 1, 2, "x1f1");
    }
    else
    {
      this.m_heading.setText(str);
      U.gbAdd(this, this.m_heading, 1, 2, "x1f1");
    }
    this.m_start.addActionListener(this);
    this.m_to.addActionListener(this);
    this.m_progBar.setVisible(false);
    U.gbAdd(this, this.m_progBar, 1, 3, "x1f1l5r5t2b2");
    U.gbAdd(this, this.m_tabs, 1, 4, "x1f1l5r5");
    U.gbAdd(this, this.m_tSummary, 1, 5, "x1y1f3l5b5r5");
    U.gbAdd(this, this.m_tTable, 1, 6, "x1y1f3l5b5r5");
    U.gbAdd(this, this.m_tMap, 1, 7, "x1y1f3l5b5r5");
    U.gbAdd(this, this.m_tAnalysis, 1, 8, "x1y1f3l5b5r5");
    U.gbAdd(this, this.m_tGraph, 1, 9, "x1y1f3l5b5r5");
    U.gbAdd(this, this.m_tNet, 1, 10, "x1y1f3l5b5r5");
    setView(localObject);
    G.FADESTART = System.currentTimeMillis();
    this.m_fade = new Thread(this);
    this.m_fade.start();
    this.m_to.setText(this.m_applet.getTraceList()[0]);
    if (this.m_applet.isDatabaseViewMode())
    {
      this.m_thread = new Thread(this);
      this.m_thread.start();
    }
  }

  public void reset()
  {
    if (this.m_applet.isUserDriven())
      return;
    clearData();
    if (this.m_applet.isUserDriven())
    {
      this.m_start.setEnabled(true);
    }
    else
    {
      this.m_start.setVisible(true);
      this.m_start.setEnabled(true);
    }
    String str = "";
    str = this.m_applet.getTraceList().length == 1 ? U.text(this.m_applet.getTraceFrom() == null ? "beginautosingular" : "beginautosingularmycomp", this.m_applet.getTraceList()[0]) : U.text("beginautomultiple", new String[] { this.m_applet.getTraceList()[0], this.m_applet.getTraceList().length });
    this.m_to.setText(this.m_applet.getTraceList()[0]);
    this.m_from.setSelectedItem(U.text("thisserver"));
    if (this.m_applet.isUserDriven())
    {
      this.m_heading.setText(U.text("begintext"));
    }
    else if (this.m_applet.isUserStart())
    {
      str = str + " " + U.text("promptstart");
      this.m_heading.setText(str);
    }
    else
    {
      this.m_heading.setText(str);
    }
    Object localObject = null;
    if (G.getShowTabSummary())
      localObject = localObject == null ? this.m_tSummary : localObject;
    if (G.getShowTabTable())
      localObject = localObject == null ? this.m_tTable : localObject;
    if (G.getShowTabMap())
      localObject = localObject == null ? this.m_tMap : localObject;
    if (G.getShowTabAnalysis())
      localObject = localObject == null ? this.m_tAnalysis : localObject;
    if (G.getShowTabRouteGraph())
      localObject = localObject == null ? this.m_tGraph : localObject;
    if ((G.getShowTabNetNode()) && (G.getParis()))
      localObject = localObject == null ? this.m_tNet : localObject;
    setView(localObject);
    validate();
  }

  public String doMySpeedVariableSubstitution(String paramString, boolean paramBoolean)
  {
    if (this.m_tr == null)
      return paramString;
    try
    {
      String str1 = paramBoolean ? "\"" : "";
      if (paramBoolean)
      {
        StringBuffer localStringBuffer1 = new StringBuffer("new Array(");
        StringBuffer localStringBuffer2 = new StringBuffer("new Array(");
        StringBuffer localStringBuffer3 = new StringBuffer("new Array(");
        StringBuffer localStringBuffer4 = new StringBuffer("new Array(");
        StringBuffer localStringBuffer5 = new StringBuffer("new Array(");
        StringBuffer localStringBuffer6 = new StringBuffer("new Array(");
        StringBuffer localStringBuffer7 = new StringBuffer("new Array(");
        StringBuffer localStringBuffer8 = new StringBuffer("new Array(");
        StringBuffer localStringBuffer9 = new StringBuffer("new Array(");
        StringBuffer localStringBuffer10 = new StringBuffer("new Array(");
        TraceRouteHop[] arrayOfTraceRouteHop = this.m_tr.hops;
        for (int i = 0; (arrayOfTraceRouteHop != null) && (i < arrayOfTraceRouteHop.length); i++)
        {
          if (i > 0)
          {
            localStringBuffer1.append(",");
            localStringBuffer2.append(",");
            localStringBuffer3.append(",");
            localStringBuffer4.append(",");
            localStringBuffer6.append(",");
            localStringBuffer5.append(",");
            localStringBuffer7.append(",");
            localStringBuffer8.append(",");
            localStringBuffer9.append(",");
            localStringBuffer10.append(",");
          }
          Place localPlace = U.getPlace(arrayOfTraceRouteHop[i].location);
          String str2 = localPlace.fullname + (arrayOfTraceRouteHop[i].bAuthoritativeLocation ? "" : "?");
          localStringBuffer1.append("'" + esc(arrayOfTraceRouteHop[i].hop) + "'");
          localStringBuffer2.append("'" + esc(arrayOfTraceRouteHop[i].ip) + "'");
          localStringBuffer3.append("'" + esc(arrayOfTraceRouteHop[i].getDisplayDNS()) + "'");
          localStringBuffer4.append("'" + arrayOfTraceRouteHop[i].ms + "'");
          localStringBuffer6.append("'" + arrayOfTraceRouteHop[i].minMs + "'");
          localStringBuffer5.append("'" + arrayOfTraceRouteHop[i].maxMs + "'");
          localStringBuffer7.append("'" + arrayOfTraceRouteHop[i].loss + "'");
          localStringBuffer8.append("'" + esc(str2) + "'");
          localStringBuffer9.append("'" + esc(arrayOfTraceRouteHop[i].network) + "'");
          localStringBuffer10.append("'" + (arrayOfTraceRouteHop[i].nAffinity == -1 ? 100 : arrayOfTraceRouteHop[i].nAffinity) + "'");
        }
        localStringBuffer1.append(")");
        localStringBuffer2.append(")");
        localStringBuffer3.append(")");
        localStringBuffer4.append(")");
        localStringBuffer6.append(")");
        localStringBuffer5.append(")");
        localStringBuffer7.append(")");
        localStringBuffer8.append(")");
        localStringBuffer9.append(")");
        localStringBuffer10.append(")");
        paramString = U.replace(paramString, "$ROUTE.HOPNUMARRAY$", localStringBuffer1.toString());
        paramString = U.replace(paramString, "$ROUTE.IPARRAY$", localStringBuffer2.toString());
        paramString = U.replace(paramString, "$ROUTE.DNSARRAY$", localStringBuffer3.toString());
        paramString = U.replace(paramString, "$ROUTE.MSARRAY$", localStringBuffer4.toString());
        paramString = U.replace(paramString, "$ROUTE.MSMAXARRAY$", localStringBuffer5.toString());
        paramString = U.replace(paramString, "$ROUTE.MSMINARRAY$", localStringBuffer6.toString());
        paramString = U.replace(paramString, "$ROUTE.PROBARRAY$", localStringBuffer10.toString());
        paramString = U.replace(paramString, "$ROUTE.LOSSARRAY$", localStringBuffer7.toString());
        paramString = U.replace(paramString, "$ROUTE.LOCATIONARRAY$", localStringBuffer8.toString());
        paramString = U.replace(paramString, "$ROUTE.NETWORKARRAY$", localStringBuffer9.toString());
        paramString = U.replace(paramString, "$HOPNUMARRAY", localStringBuffer1.toString());
        paramString = U.replace(paramString, "$IPARRAY", localStringBuffer2.toString());
        paramString = U.replace(paramString, "$DNSARRAY", localStringBuffer3.toString());
        paramString = U.replace(paramString, "$MSARRAY", localStringBuffer4.toString());
        paramString = U.replace(paramString, "$LOSSARRAY", localStringBuffer7.toString());
        paramString = U.replace(paramString, "$LOCATIONARRAY", localStringBuffer8.toString());
        paramString = U.replace(paramString, "$NETWORKARRAY", localStringBuffer9.toString());
      }
      else
      {
        paramString = U.replace(paramString, "$ROUTE.HOPNUMARRAY$", "");
        paramString = U.replace(paramString, "$ROUTE.IPARRAY$", "");
        paramString = U.replace(paramString, "$ROUTE.DNSARRAY$", "");
        paramString = U.replace(paramString, "$ROUTE.MSARRAY$", "");
        paramString = U.replace(paramString, "$ROUTE.MSMAXARRAY$", "");
        paramString = U.replace(paramString, "$ROUTE.MSMINARRAY$", "");
        paramString = U.replace(paramString, "$ROUTE.LOSSARRAY$", "");
        paramString = U.replace(paramString, "$ROUTE.LOCATIONARRAY$", "");
        paramString = U.replace(paramString, "$ROUTE.NETWORKARRAY$", "");
        paramString = U.replace(paramString, "$ROUTE.PROBARRAY$", "");
        paramString = U.replace(paramString, "$HOPNUMARRAY", "");
        paramString = U.replace(paramString, "$IPARRAY", "");
        paramString = U.replace(paramString, "$DNSARRAY", "");
        paramString = U.replace(paramString, "$MSARRAY", "");
        paramString = U.replace(paramString, "$LOSSARRAY", "");
        paramString = U.replace(paramString, "$LOCATIONARRAY", "");
        paramString = U.replace(paramString, "$NETWORKARRAY", "");
      }
      paramString = U.replace(paramString, "$ROUTE.ERROR$", str1 + (this.m_tr.errorResult != null ? esc(mapError(this.m_tr.error)) : "") + str1);
      paramString = U.replace(paramString, "$ROUTE.MAXMS$", getMaxMs(this.m_tr));
      paramString = U.replace(paramString, "$ROUTE.ENDLOSS$", getEndLoss(this.m_tr));
      paramString = U.replace(paramString, "$ROUTE.ENDMS$", getEndMs(this.m_tr));
      paramString = U.replace(paramString, "$ROUTE.TOIP$", str1 + getToIP(this.m_tr) + str1);
      paramString = U.replace(paramString, "$ROUTE.HOPCOUNT$", getHopCount(this.m_tr));
      paramString = U.replace(paramString, "$ROUTE.FROMIP$", str1 + getFromIP(this.m_tr) + str1);
      paramString = U.replace(paramString, "$ROUTE.TONAME$", str1 + getToName(this.m_tr) + str1);
      paramString = U.replace(paramString, "$TO", getToName(this.m_tr));
      paramString = U.replace(paramString, "$FROM", str1 + getFromIP(this.m_tr) + str1);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramString;
  }

  public String getDetailReport()
  {
    try
    {
      StringBuffer localStringBuffer = new StringBuffer();
      if (this.m_tr.errorResult != null)
      {
        localStringBuffer.append("&detail.error=");
        localStringBuffer.append(URLEncoder.encode(this.m_tr.error));
        System.out.println("DETAIL: " + localStringBuffer.toString());
        return localStringBuffer.toString();
      }
      TraceRouteHop[] arrayOfTraceRouteHop = this.m_tr.hops;
      int[] arrayOfInt1 = new int[arrayOfTraceRouteHop.length];
      int[] arrayOfInt2 = new int[arrayOfTraceRouteHop.length];
      int[] arrayOfInt3 = new int[arrayOfTraceRouteHop.length];
      int[] arrayOfInt4 = new int[arrayOfTraceRouteHop.length];
      int[] arrayOfInt5 = new int[arrayOfTraceRouteHop.length];
      String[] arrayOfString1 = new String[arrayOfTraceRouteHop.length];
      String[] arrayOfString2 = new String[arrayOfTraceRouteHop.length];
      String[] arrayOfString3 = new String[arrayOfTraceRouteHop.length];
      String[] arrayOfString4 = new String[arrayOfTraceRouteHop.length];
      String[] arrayOfString5 = new String[arrayOfTraceRouteHop.length];
      for (int i = 0; i < arrayOfTraceRouteHop.length; i++)
      {
        arrayOfInt1[i] = arrayOfTraceRouteHop[i].ms;
        arrayOfInt3[i] = arrayOfTraceRouteHop[i].maxMs;
        arrayOfInt4[i] = arrayOfTraceRouteHop[i].minMs;
        arrayOfInt5[i] = arrayOfTraceRouteHop[i].nAffinity;
        arrayOfInt2[i] = arrayOfTraceRouteHop[i].loss;
        arrayOfString1[i] = arrayOfTraceRouteHop[i].ip;
        arrayOfString2[i] = arrayOfTraceRouteHop[i].hop;
        arrayOfString3[i] = arrayOfTraceRouteHop[i].dns;
        Place localPlace = U.getPlace(arrayOfTraceRouteHop[i].location);
        if (localPlace == null)
          arrayOfString4[i] = "*";
        else if (arrayOfTraceRouteHop[i].bAuthoritativeLocation)
          arrayOfString4[i] = localPlace.toAppletString();
        else
          arrayOfString4[i] = ("?" + localPlace.toAppletString());
        arrayOfString5[i] = arrayOfTraceRouteHop[i].network;
      }
      localStringBuffer.append("&detail.nname=");
      localStringBuffer.append(U.urlEncode(U.stringArrayToString(arrayOfString3).toString()));
      localStringBuffer.append("&detail.affin=");
      localStringBuffer.append(U.urlEncode(U.intArrayToString(arrayOfInt5).toString()));
      localStringBuffer.append("&detail.maxms=");
      localStringBuffer.append(U.urlEncode(U.intArrayToString(arrayOfInt3).toString()));
      localStringBuffer.append("&detail.minms=");
      localStringBuffer.append(U.urlEncode(U.intArrayToString(arrayOfInt4).toString()));
      localStringBuffer.append("&detail.ms=");
      localStringBuffer.append(U.urlEncode(U.intArrayToString(arrayOfInt1).toString()));
      localStringBuffer.append("&detail.loss=");
      localStringBuffer.append(U.urlEncode(U.intArrayToString(arrayOfInt2).toString()));
      localStringBuffer.append("&detail.ips=");
      localStringBuffer.append(U.urlEncode(U.stringArrayToString(arrayOfString1).toString()));
      localStringBuffer.append("&detail.hops=");
      localStringBuffer.append(U.urlEncode(U.stringArrayToString(arrayOfString2).toString()));
      localStringBuffer.append("&detail.geo=");
      localStringBuffer.append(U.urlEncode(U.stringArrayToString(arrayOfString4, "|").toString()));
      localStringBuffer.append("&detail.nwho=");
      localStringBuffer.append(U.urlEncode(U.stringArrayToString(arrayOfString5).toString()));
      System.out.println("DETAIL: " + localStringBuffer.toString());
      return localStringBuffer.toString();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public ErrorCode getErrorCode()
  {
    if (this.m_tr == null)
      return null;
    return this.m_tr.errorResult;
  }

  public String getReportMSS()
  {
    if (this.m_tr == null)
      return null;
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("&route.ipto=");
    localStringBuffer.append(getToIP(this.m_tr));
    localStringBuffer.append("&route.hops=");
    localStringBuffer.append(getHopCount(this.m_tr));
    localStringBuffer.append("&route.endms=");
    localStringBuffer.append(getEndMs(this.m_tr));
    localStringBuffer.append("&route.maxms=");
    localStringBuffer.append(getMaxMs(this.m_tr));
    localStringBuffer.append("&route.endloss=");
    localStringBuffer.append(getEndLoss(this.m_tr));
    localStringBuffer.append("&route.maxloss=");
    localStringBuffer.append(getMaxLoss(this.m_tr));
    localStringBuffer.append("&route.ipfrom=");
    localStringBuffer.append(getFromIP(this.m_tr));
    localStringBuffer.append("&route.nameto=");
    localStringBuffer.append(getToName(this.m_tr));
    localStringBuffer.append("&route.dnstime=");
    localStringBuffer.append(getDNSTime());
    localStringBuffer.append("&route.enteredname=");
    localStringBuffer.append(getEnteredName());
    return localStringBuffer.toString();
  }

  public void setDetached(boolean paramBoolean)
  {
    this.m_tabs.setDetached(paramBoolean);
  }

  public void doFirstTimeInit()
  {
    System.out.println("G.isTraceTargetEditable()=" + G.isTraceTargetEditable());
    this.m_to.setEnabled(G.isTraceTargetEditable());
    if ((this.m_applet.isUserDriven()) && (this.m_applet.getGo() != null))
    {
      this.m_to.setText(this.m_applet.getGo());
      this.m_start.setEnabled(false);
      route.getRoute().getRouteTab().postStartEvent(this);
    }
  }

  public void runTest()
  {
    if (this.m_applet.isUserDriven())
    {
      this.m_start.setEnabled(false);
      start();
      if (this.m_thread != null)
        while (this.m_thread != null)
          try
          {
            Thread.sleep(500L);
          }
          catch (Exception localException1)
          {
          }
      this.m_start.setEnabled(true);
    }
    else
    {
      this.m_bOverrideAllowLocal = true;
      this.m_start.setVisible(false);
      String[] arrayOfString = this.m_applet.getTraceList();
      if (arrayOfString == null)
        return;
      for (int i = 0; i < arrayOfString.length; i++)
      {
        this.m_to.setText(arrayOfString[i]);
        start();
        if (this.m_thread != null)
          while (this.m_thread != null)
            try
            {
              Thread.sleep(500L);
            }
            catch (Exception localException2)
            {
            }
        if (i + 1 != arrayOfString.length)
          this.m_applet.doReportResult();
      }
    }
  }

  private JPanel getControlsPanel()
  {
    this.m_start.setText(U.text("start"));
    this.m_from.addItem(U.text("thisserver"));
    if (G.isMyComputerTraceAvailable())
      this.m_from.addItem(this.THIS_COMPUTER);
    if (G.isRTATraceAvailable())
    {
      localObject = G.getRTAs();
      StringTokenizer localStringTokenizer1 = new StringTokenizer((String)localObject, ",");
      while (localStringTokenizer1.hasMoreTokens())
        try
        {
          StringTokenizer localStringTokenizer2 = new StringTokenizer(localStringTokenizer1.nextToken(), ";");
          String str1 = localStringTokenizer2.nextToken();
          String str2 = U.base64Decode(localStringTokenizer2.nextToken());
          this.m_agents.put(str2, str1);
          this.m_from.addItem(str2);
        }
        catch (Exception localException)
        {
        }
    }
    this.m_from.setSelectedIndex(0);
    Object localObject = new JPanel(new GridBagLayout());
    ((JPanel)localObject).setBackground(new Color(14280697));
    int i = 0;
    if ((G.isRTATraceAvailable()) || (G.isMyComputerTraceAvailable()))
    {
      U.gbAdd((Container)localObject, new JLabel(U.text("reportfrom")), i++, 1, "l6r2t5b5");
      U.gbAdd((Container)localObject, this.m_from, i++, 1, "l2r5t5b5");
      U.gbAdd((Container)localObject, new JLabel(U.text("to")), i++, 1, "l2r2t5b5");
    }
    else
    {
      U.gbAdd((Container)localObject, new JLabel(U.text("reportto")), i++, 1, "l2r2t5b5");
    }
    U.gbAdd((Container)localObject, this.m_to, i++, 1, "x1f1l6r5t5b5");
    U.gbAdd((Container)localObject, this.m_start, i++, 1, "t5b5r5");
    return localObject;
  }

  public void snap()
  {
    if (this.m_host != null)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("--------------------------------------------------------------------------------------------\n");
      String str = new Date().toLocaleString();
      if ((this.m_tr != null) && (this.m_tr.time != 0L))
      {
        str = new Date(this.m_tr.time).toLocaleString();
        System.out.println("m_tr.time=" + this.m_tr.time);
      }
      else
      {
        System.out.println("m_tr=" + (this.m_tr == null ? "NULL" : new StringBuilder("OK, ").append(this.m_tr.time).toString()));
      }
      localStringBuffer.append(U.text("traceheading", new String[] { str, this.m_host })).append("\n");
      localStringBuffer.append("--------------------------------------------------------------------------------------------\n");
      localStringBuffer.append("\n");
      localStringBuffer.append(this.m_tableView.snap());
      new AppletWindow(localStringBuffer.toString());
    }
  }

  public void doWhois(String paramString, boolean paramBoolean)
  {
    if (G.isWhoisPermitted())
      try
      {
        new AppletWindow(new URL(this.m_applet.base(), "plugin/route/applet/" + (paramBoolean ? "d" : "n") + "whois?go=" + paramString));
      }
      catch (MalformedURLException localMalformedURLException)
      {
      }
    else
      new AppletWindow(U.text("whoisnotpermitted"));
  }

  public ViewSummaryPanel getSummaryViewPanel()
  {
    return this.m_summaryView;
  }

  public ViewNetworkNodePanel getNetViewPanel()
  {
    return this.m_netView;
  }

  public void setHighlight(TraceRoute paramTraceRoute)
  {
    this.m_summaryView.setHighlight(paramTraceRoute);
    this.m_tableView.setTraceRoute(paramTraceRoute);
    this.m_mapView.setTraceRoute(paramTraceRoute);
    this.m_analysisView.setTraceRoute(paramTraceRoute);
    this.m_graphView.setTraceRoute(paramTraceRoute);
  }

  private void setData(TraceRoute paramTraceRoute)
  {
    this.m_tr = paramTraceRoute;
    this.m_summaryView.setTraceRoute(paramTraceRoute);
    this.m_tableView.setTraceRoute(paramTraceRoute);
    this.m_mapView.setTraceRoute(paramTraceRoute);
    this.m_analysisView.setTraceRoute(paramTraceRoute);
    this.m_graphView.setTraceRoute(paramTraceRoute);
    this.m_netView.setTraceRoute(paramTraceRoute);
  }

  private void doEarlyReQuery(TraceRoute paramTraceRoute, boolean paramBoolean)
  {
    if (paramTraceRoute == null)
    {
      setData(paramTraceRoute);
      return;
    }
    if (paramTraceRoute.hops == null)
    {
      setData(paramTraceRoute);
      return;
    }
    if (paramTraceRoute.hops.length == 0)
    {
      setData(paramTraceRoute);
      return;
    }
    String str2;
    String str3;
    String str4;
    for (int i = 0; i < paramTraceRoute.hops.length; i++)
    {
      TraceRouteHop localTraceRouteHop1 = paramTraceRoute.hops[i];
      if (localTraceRouteHop1 != null)
        try
        {
          String str1 = HopServerQuery.getGeoString(localTraceRouteHop1.ip, localTraceRouteHop1.dns);
          str2 = HopServerQuery.getNetworkDesc(localTraceRouteHop1.ip, localTraceRouteHop1.dns);
          str3 = HopServerQuery.getLocation(localTraceRouteHop1.ip, localTraceRouteHop1.dns);
          str4 = HopServerQuery.getNameString(localTraceRouteHop1.ip, localTraceRouteHop1.dns);
          if ((str2 != null) && (str1 != null) && (str3 != null))
          {
            localTraceRouteHop1.network = str2;
            localTraceRouteHop1.locCode = str1;
            localTraceRouteHop1.location = str3;
            localTraceRouteHop1.bAuthoritativeLocation = (!str3.endsWith("?"));
            localTraceRouteHop1.location = str3;
          }
          if (str4 != null)
            localTraceRouteHop1.dns = str4;
        }
        catch (Exception localException2)
        {
          System.out.println("ERROR=");
          localException2.printStackTrace();
        }
    }
    if (paramBoolean)
    {
      i = 0;
      while (i == 0)
      {
        i = 1;
        for (int j = 0; j < paramTraceRoute.hops.length; j++)
        {
          TraceRouteHop localTraceRouteHop2 = paramTraceRoute.hops[j];
          if (localTraceRouteHop2 != null)
            try
            {
              str2 = HopServerQuery.getGeoString(localTraceRouteHop2.ip, localTraceRouteHop2.dns);
              str3 = HopServerQuery.getNetworkDesc(localTraceRouteHop2.ip, localTraceRouteHop2.dns);
              str4 = HopServerQuery.getLocation(localTraceRouteHop2.ip, localTraceRouteHop2.dns);
              String str5 = HopServerQuery.getNameString(localTraceRouteHop2.ip, localTraceRouteHop2.dns);
              if ((str3 == null) || (str4 == null))
              {
                i = 0;
              }
              else
              {
                localTraceRouteHop2.network = str3;
                localTraceRouteHop2.locCode = str2;
                localTraceRouteHop2.location = str4;
                localTraceRouteHop2.bAuthoritativeLocation = (!str4.endsWith("?"));
                localTraceRouteHop2.location = str4;
              }
              if ((str5 != null) && ("-".equals(localTraceRouteHop2.dns)))
                localTraceRouteHop2.dns = str5;
            }
            catch (Exception localException3)
            {
              System.out.println("ERROR=");
              localException3.printStackTrace();
            }
        }
        try
        {
          Thread.sleep(500L);
        }
        catch (Exception localException1)
        {
        }
      }
    }
    setData(paramTraceRoute);
  }

  public void updateSomePanels(TraceRoute paramTraceRoute)
  {
    if (G.getParis())
    {
      this.m_mapView.setTraceRoute(paramTraceRoute);
      this.m_analysisView.setTraceRoute(paramTraceRoute);
      this.m_graphView.setTraceRoute(paramTraceRoute);
    }
  }

  private void setData(String paramString)
  {
    Object localObject;
    if (paramString.startsWith("route="))
    {
      localObject = paramString.startsWith("route=") ? TraceRoute.fromAppletString(paramString.substring(6), this.m_host) : null;
      if (localObject != null)
      {
        this.m_tr = ((TraceRoute)localObject);
        this.m_summaryView.setTraceRoute((TraceRoute)localObject);
        this.m_tableView.setTraceRoute((TraceRoute)localObject);
        this.m_netView.setTraceRoute((TraceRoute)localObject);
        if (!G.getParis())
        {
          this.m_mapView.setTraceRoute((TraceRoute)localObject);
          this.m_analysisView.setTraceRoute((TraceRoute)localObject);
          this.m_graphView.setTraceRoute((TraceRoute)localObject);
        }
      }
      else
      {
        System.out.println("bad data: " + paramString);
      }
    }
    else if (paramString.startsWith("port="))
    {
      localObject = PortInfo.fromString(paramString.substring(5));
      if (localObject != null)
      {
        this.m_summaryView.setPortInfo((PortInfo)localObject);
        this.m_analysisView.setPortInfo((PortInfo)localObject);
      }
      else
      {
        System.out.println("bad data: " + paramString);
      }
    }
    else
    {
      int i;
      if (paramString.startsWith("percent="))
      {
        i = -1;
        try
        {
          i = Integer.parseInt(paramString.substring(8));
        }
        catch (Exception localException1)
        {
        }
        if (i == 100)
          setStatus(U.text("tracecomplete", this.m_host));
        else if (i >= 0)
          setStatus(U.text("tracing", new String[] { this.m_host, i }));
      }
      else if (paramString.startsWith("dnstime="))
      {
        i = -1;
        try
        {
          i = Integer.parseInt(paramString.substring(8));
        }
        catch (Exception localException2)
        {
        }
        if (i >= 0)
        {
          this.m_nDNSTime = i;
          this.m_summaryView.setDNSTime(i);
          this.m_analysisView.setDNSTime(i);
        }
      }
      else if (paramString.startsWith("locations="))
      {
        U.setPlaces(paramString.substring(10));
        updateData();
      }
      else if (paramString.startsWith("error="))
      {
        setError(paramString.substring(6));
      }
    }
  }

  private void setStatus(String paramString)
  {
    if (this.m_error == null)
      this.m_heading.setText(paramString);
  }

  private void setErrorTraceRoute(TraceRoute paramTraceRoute)
  {
    this.m_tr = paramTraceRoute;
    String str = mapError(paramTraceRoute.error) + " '" + this.m_host + "'";
    setError(str);
    if (paramTraceRoute.errorResult == null)
    {
      int i = TraceRoute.mapOldStringToNewInt(paramTraceRoute.error);
      ErrorCode localErrorCode = new ErrorCode(i & 0xFFFF | 0x30000, TraceRoute.mapErrorDesc(i, this.m_host));
      paramTraceRoute.errorResult = localErrorCode;
    }
  }

  private void setErrorTraceRoute(String paramString1, String paramString2)
  {
    int i = TraceRoute.mapOldStringToNewInt(paramString1);
    paramString1 = mapError(paramString1) + " '" + this.m_host + "'";
    ErrorCode localErrorCode = new ErrorCode(i & 0xFFFF | 0x30000, TraceRoute.mapErrorDesc(i, this.m_host));
    setError(paramString1);
    if ((paramString2 != null) && (paramString2.startsWith("route=")))
      this.m_tr = TraceRoute.fromAppletString(paramString2.substring(6), this.m_host);
    if (this.m_tr == null)
      this.m_tr = new TraceRoute();
    this.m_tr.error = paramString1;
    this.m_tr.errorResult = localErrorCode;
  }

  private String mapError(String paramString)
  {
    if ("ERRLOC".equals(paramString))
      return U.text("errlocal");
    if ("ERRSEC".equals(paramString))
      return U.text("errsecure");
    if ("ERRRAN".equals(paramString))
      return U.text("erriprange");
    if ("ERRDNS".equals(paramString))
      return U.text("errdns");
    return paramString;
  }

  private void setError(String paramString)
  {
    if ("userlimitreached".equals(paramString))
    {
      paramString = U.text("userlimitreached");
      String str = G.getLimitReachedUrl();
      if (str != null)
        try
        {
          this.m_applet.showDocument(new URL(str), "_blank");
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
    }
    this.m_error = paramString;
    this.m_heading.setText("Error: " + paramString);
  }

  private void clearData()
  {
    this.m_netView.reset();
    this.m_summaryView.reset();
    this.m_tableView.setTraceRoute(null);
    this.m_mapView.setTraceRoute(null);
    this.m_analysisView.setTraceRoute(null);
    this.m_graphView.setTraceRoute(null);
    this.m_netView.setTraceRoute(null);
  }

  private void updateData()
  {
    this.m_summaryView.updateData();
    this.m_tableView.updateData();
    this.m_mapView.updateData();
    this.m_analysisView.updateData();
    this.m_graphView.updateData();
    this.m_netView.updateData();
  }

  public void setView(AppletViewPanel paramAppletViewPanel)
  {
    this.m_tSummary.setVisible(paramAppletViewPanel == this.m_tSummary);
    this.m_tTable.setVisible(paramAppletViewPanel == this.m_tTable);
    this.m_tMap.setVisible(paramAppletViewPanel == this.m_tMap);
    this.m_tAnalysis.setVisible(paramAppletViewPanel == this.m_tAnalysis);
    this.m_tGraph.setVisible(paramAppletViewPanel == this.m_tGraph);
    this.m_tNet.setVisible(paramAppletViewPanel == this.m_tNet);
    validate();
    this.m_tabs.repaint();
  }

  public void setView(String paramString)
  {
    if ("map".equals(paramString))
      setView(this.m_tMap);
    else if ("table".equals(paramString))
      setView(this.m_tTable);
    else if ("summary".equals(paramString))
      setView(this.m_tSummary);
    else if ("analysis".equals(paramString))
      setView(this.m_tAnalysis);
    else if ("graph".equals(paramString))
      setView(this.m_tGraph);
    else if ("netnode".equals(paramString))
      setView(this.m_tNet);
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    Object localObject = paramActionEvent.getSource();
    if ((localObject == this.m_start) && (this.m_bRunning))
    {
      stop();
    }
    else if ((localObject == this.m_start) || (localObject == this.m_to))
    {
      if (!this.m_applet.isUserDriven())
        this.m_start.setVisible(false);
      this.m_start.setEnabled(false);
      route.getRoute().getRouteTab().postStartEvent(this);
    }
  }

  private String getLocalHostAddress()
  {
    String str = null;
    if (str == null)
      try
      {
        str = InetAddress.getLocalHost().getHostAddress();
      }
      catch (Exception localException)
      {
      }
    return str;
  }

  private String esc(String paramString)
  {
    if (paramString == null)
      return null;
    StringBuffer localStringBuffer = new StringBuffer();
    char[] arrayOfChar = paramString.toCharArray();
    for (int i = 0; i < arrayOfChar.length; i++)
    {
      if (arrayOfChar[i] == '\'')
        localStringBuffer.append("\\");
      localStringBuffer.append(arrayOfChar[i]);
    }
    return localStringBuffer.toString();
  }

  private void start()
  {
    stop();
    String str = this.m_to.getText().trim();
    str = str.equals("") ? getLocalHostAddress() : str;
    if (str != null)
    {
      int i = str.indexOf("://");
      this.m_prot = null;
      this.m_port = -1;
      if (i > 0)
      {
        this.m_prot = str.substring(0, i);
        str = str.substring(i + 3);
      }
      int j = str.indexOf(":");
      if (j > 0)
        try
        {
          this.m_port = Integer.parseInt(str.substring(j + 1));
          str = str.substring(0, j);
        }
        catch (Exception localException)
        {
        }
      int k = str.indexOf("/");
      if (k > 0)
        str = str.substring(0, k);
      this.m_host = str;
      this.m_to.setText((this.m_prot != null ? this.m_prot + "://" : "") + this.m_host + (this.m_port >= 0 ? ":" + this.m_port : ""));
      this.m_summaryView.setPortInfo(null);
      this.m_analysisView.setPortInfo(null);
      this.m_tr = null;
      this.m_nDNSTime = -1;
      this.m_enteredName = str;
      this.m_error = null;
      this.m_bRun = true;
      this.m_thread = new Thread(this);
      this.m_thread.start();
    }
  }

  private synchronized void stop()
  {
    this.m_bRun = false;
    this.m_progBar.setVisible(false);
    long l = System.currentTimeMillis() + 5000L;
    while ((this.m_thread != null) && (System.currentTimeMillis() < l))
      try
      {
        Thread.sleep(100L);
      }
      catch (Exception localException)
      {
      }
    if (this.m_thread != null)
    {
      setRunning(false);
      this.m_thread = null;
    }
    clearData();
  }

  private void setRunning(boolean paramBoolean)
  {
    this.m_bRunning = paramBoolean;
    validate();
  }

  private void showProgressBar()
  {
    this.m_progBar.setVisible(true);
    this.m_progBar.invalidate();
    validate();
    this.m_progBar.start();
  }

  public void run()
  {
    if (Thread.currentThread() == this.m_fade)
    {
      while (System.currentTimeMillis() < G.FADESTART + 2000L + 5000L)
      {
        updateData();
        try
        {
          Thread.sleep(100L);
        }
        catch (Exception localException1)
        {
        }
      }
      updateData();
    }
    else
    {
      if (this.m_applet.isDatabaseViewMode())
      {
        setDatabaseInfo();
        return;
      }
      setRunning(true);
      setStatus(U.text("tracing", new String[] { this.m_host, "0" }));
      InputStream localInputStream = null;
      label609: 
      try
      {
        long l = System.currentTimeMillis() / 1000L;
        String str1 = this.m_applet.getHost();
        String str2 = this.m_applet.getTraceFrom() == null ? (String)this.m_from.getSelectedItem() : this.THIS_COMPUTER;
        String str3 = (this.m_agents == null) || (str2 == null) ? "" : (String)this.m_agents.get(str2);
        int i = 1;
        if (this.m_from.getSelectedIndex() >= 0)
          i = this.m_from.getSelectedIndex() == 0 ? 1 : 0;
        i = this.m_applet.getTraceFrom() == null ? i : 0;
        if (i != 0)
        {
          localInputStream = performServerTrace(str3, str1, l);
        }
        else if (this.THIS_COMPUTER.equals(str2))
        {
          showProgressBar();
          String[] arrayOfString = null;
          if ((this.m_myCompInst != null) && (this.m_myCompLink != null))
            arrayOfString = new String[] { this.m_myCompInst, this.m_myCompLink };
          else
            arrayOfString = requestNewRTA(localInputStream);
          if (arrayOfString == null)
          {
            setError("Unable to download agent for testing");
            break label609;
          }
          String str4 = arrayOfString[0];
          String str5 = arrayOfString[1];
          this.m_myCompInst = str4;
          this.m_myCompLink = str5;
          try
          {
            str5 = new URL(this.m_applet.getAppletCodeBase(), str5 + "&ext=.exe").toString();
          }
          catch (Exception localException3)
          {
          }
          int j = 0;
          try
          {
            this.m_applet.js("document.getElementById('id_applet_custom_text').innerHTML='To download the agent to start the testing from your computer <a href=\"" + str5 + "\">click here</a>'");
          }
          catch (Exception localException4)
          {
            j = 1;
            setError("Unable to call javascript to allow download of agent, <a href=\"http://www.myconnectionserver.com/support/v9/config_route.html#js\">click here</a> for help with this error");
          }
          setStatus(U.text("tracingagent"));
          if (j == 0)
          {
            performRTATrace(str4);
            break label609;
          }
        }
        else
        {
          showProgressBar();
          setStatus(U.text("tracingagent"));
          performRTATrace(str3);
        }
      }
      catch (IOException localIOException)
      {
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
      finally
      {
        this.m_progBar.kill();
        this.m_progBar.setVisible(false);
        try
        {
          localInputStream.close();
        }
        catch (Exception localException7)
        {
        }
        setRunning(false);
        this.m_thread = null;
      }
    }
  }

  private void reQueryDatabaseInfo()
  {
    DataInputStream localDataInputStream = null;
    Object localObject1;
    try
    {
      if (this.m_tr == null);
      while ((this.m_tr.errorResult != null) || (this.m_tr.hops == null) || (this.m_tr.hops.length == 0))
        return;
      String str = getDetailReport();
      if (str == null)
        return;
      localObject1 = new URL(this.m_applet.getAppletCodeBase(), "plugin/route/applet/relookup?" + str);
      localDataInputStream = new DataInputStream(((URL)localObject1).openStream());
      Object localObject2 = null;
      while ((localObject2 = localDataInputStream.readLine()) != null)
      {
        if ("EOF".equals(localObject2))
          break;
        handleReQueryData((String)localObject2);
        setData(this.m_tr);
      }
    }
    catch (Exception localException1)
    {
      try
      {
        localDataInputStream.close();
      }
      catch (Exception localException4)
      {
      }
    }
    finally
    {
      try
      {
        localDataInputStream.close();
      }
      catch (Exception localException5)
      {
      }
    }
    for (int i = 0; i < this.m_tr.hops.length; i++)
    {
      localObject1 = this.m_tr.hops[i];
      if ("*".equals(((TraceRouteHop)localObject1).network))
        ((TraceRouteHop)localObject1).network = "-";
      else if ("*".equals(((TraceRouteHop)localObject1).location))
        ((TraceRouteHop)localObject1).location = "-";
    }
    setData(this.m_tr);
  }

  private void handleReQueryData(String paramString)
  {
    int i = paramString.indexOf(",");
    if (i == -1)
      return;
    String str = paramString.substring(0, i);
    paramString = paramString.substring(i + 1);
    if (paramString.startsWith("nwho="))
    {
      paramString = paramString.substring(5);
      setHopData(str, paramString, "nwho");
    }
    else if (paramString.startsWith("geo="))
    {
      paramString = paramString.substring(4);
      setHopData(str, paramString, "geo");
    }
    else if (paramString.startsWith("name="))
    {
      paramString = paramString.substring(5);
      setHopData(str, paramString, "name");
    }
  }

  private void setHopData(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 == null) || (paramString2 == null) || (paramString3 == null))
      return;
    if (this.m_tr == null)
      return;
    if (this.m_tr.hops == null)
      return;
    if (this.m_tr.hops.length == 0)
      return;
    for (int i = 0; i < this.m_tr.hops.length; i++)
      if (paramString1.equals(this.m_tr.hops[i].ip))
      {
        TraceRouteHop localTraceRouteHop = this.m_tr.hops[i];
        if ("nwho".equals(paramString3))
        {
          localTraceRouteHop.network = paramString2;
        }
        else if ("geo".equals(paramString3))
        {
          StringTokenizer localStringTokenizer = new StringTokenizer(paramString2, ";");
          String str = localStringTokenizer.nextToken();
          localTraceRouteHop.location = str;
          localTraceRouteHop.locCode = paramString2;
          localTraceRouteHop.bAuthoritativeLocation = (!str.endsWith("?"));
          localTraceRouteHop.location = str;
          if (!"-".equals(str))
            U.setPlaces(paramString2);
        }
        else if (("name".equals(paramString3)) && ("*".equals(localTraceRouteHop.dns)))
        {
          localTraceRouteHop.dns = paramString2;
        }
      }
  }

  private void setDatabaseInfo()
  {
    DataInputStream localDataInputStream = null;
    try
    {
      URL localURL = new URL(this.m_applet.getAppletCodeBase(), "plugin/route/applet/detailrequest?id=" + this.m_applet.getDatabaseID() + "&serverid=" + this.m_applet.getDatabaseServerID());
      localDataInputStream = new DataInputStream(localURL.openStream());
      StringBuffer localStringBuffer = new StringBuffer();
      Object localObject1 = null;
      while ((localObject1 = localDataInputStream.readLine()) != null)
        localStringBuffer.append(localObject1 + "\r\n");
      TraceRoute localTraceRoute = parseDetailData(localStringBuffer.toString());
      if (localTraceRoute.errorResult != null)
      {
        setError(mapError(localTraceRoute.error));
      }
      else
      {
        setData(localTraceRoute);
        this.m_host = localTraceRoute.ip;
        if ((localTraceRoute.hops != null) && (localTraceRoute.hops.length > 0) && (localTraceRoute.hops[0].locCode == null))
        {
          setStatus(U.text("waitagentextradata"));
          reQueryDatabaseInfo();
          setStatus(U.text("completeagentextradata"));
        }
      }
      try
      {
        localDataInputStream.close();
      }
      catch (Exception localException2)
      {
      }
      return;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    finally
    {
      try
      {
        localDataInputStream.close();
      }
      catch (Exception localException5)
      {
      }
    }
    setError("Problem aquiring database results, data may not be complete");
  }

  private String[] requestNewRTA(InputStream paramInputStream)
  {
    URL localURL = new URL(this.m_applet.getAppletCodeBase(), "rta/getnewrtainstance");
    DataInputStream localDataInputStream = new DataInputStream(localURL.openStream());
    try
    {
      String str1 = localDataInputStream.readLine();
      String str2 = str1.substring("status=".length());
      if (!"ok".equals(str2))
        return null;
      String str3 = null;
      String str4 = null;
      while ((str1 = localDataInputStream.readLine()) != null)
      {
        str1 = str1.trim();
        if (str1.startsWith("instanceid="))
          str3 = str1.substring("instanceid=".length());
        else if (str1.startsWith("link="))
          str4 = str1.substring("link=".length());
      }
      return new String[] { str3, str4 };
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private boolean performRTATrace(String paramString)
  {
    URL localURL = new URL(this.m_applet.getAppletCodeBase(), "rta/addapplettest?" + constructRTAArgs(paramString));
    DataInputStream localDataInputStream = new DataInputStream(localURL.openStream());
    try
    {
      String str1 = localDataInputStream.readLine();
      if ((str1 == null) || (str1.startsWith("error=")))
      {
        setErrorTraceRoute("Problem adding test to server, please try again later.", null);
        return false;
      }
      str1 = str1.substring("ticket=".length());
      if (!"unavailable".equals(str1))
      {
        int i = 0;
        int j = 0;
        int k = 0;
        String str2 = null;
        Object localObject1;
        while (((j == 0) || (i < 3)) && (str2 == null))
        {
          j = 0;
          localObject1 = requestTicketData(str1);
          if (((String)localObject1).startsWith("error"))
          {
            j = 1;
            i++;
          }
          else if ("wait".equals(localObject1))
          {
            i = 0;
            try
            {
              Thread.sleep(1000L);
            }
            catch (Exception localException2)
            {
            }
          }
          else if (((String)localObject1).startsWith("W"))
          {
            i = 0;
            k = 1;
            setStatus(U.text("gotagentintermediatedata"));
            TraceRoute localTraceRoute = parseDetailData(((String)localObject1).substring(1));
            doEarlyReQuery(localTraceRoute, false);
            try
            {
              Thread.sleep(1000L);
            }
            catch (Exception localException3)
            {
            }
          }
          else
          {
            i = 0;
            str2 = ((String)localObject1).substring(1);
          }
        }
        if (j == 0)
        {
          localObject1 = parseDetailData(str2);
          if (((TraceRoute)localObject1).errorResult == null)
          {
            setData((TraceRoute)localObject1);
            this.m_host = this.m_tr.ip;
            setStatus(U.text("waitagentextradata"));
            if (k == 0)
              reQueryDatabaseInfo();
            else
              doEarlyReQuery((TraceRoute)localObject1, true);
            setStatus(U.text("tracecomplete", this.m_host));
          }
          else
          {
            setErrorTraceRoute((TraceRoute)localObject1);
            this.m_host = this.m_tr.ip;
          }
        }
        else
        {
          setErrorTraceRoute("The connection to the server was unexpectedly terminated.", null);
        }
        return true;
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    finally
    {
      try
      {
        localDataInputStream.close();
      }
      catch (Exception localException7)
      {
      }
    }
    try
    {
      localDataInputStream.close();
    }
    catch (Exception localException8)
    {
    }
    return false;
  }

  private String requestTicketData(String paramString)
  {
    DataInputStream localDataInputStream = null;
    try
    {
      URL localURL = new URL(this.m_applet.getAppletCodeBase(), "rta/getappletresults?ticket=" + paramString);
      localDataInputStream = new DataInputStream(localURL.openStream());
      String str1 = localDataInputStream.readLine().trim();
      str1 = str1.substring("status=".length());
      if ((!"ok".equals(str1)) && (!"wait".equals(str1)))
      {
        localDataInputStream.close();
        localObject2 = str1;
        return localObject2;
      }
      String str2 = localDataInputStream.readLine();
      if (str2 == null)
      {
        localObject2 = str1;
        return localObject2;
      }
      str2 = str2.substring("result=".length());
      localDataInputStream.close();
      String str3 = U.base64Decode(str2);
      if ("wait".equals(str1))
        str3 = "W" + str3;
      else
        str3 = "O" + str3;
      Object localObject2 = str3;
      return localObject2;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    finally
    {
      try
      {
        localDataInputStream.close();
      }
      catch (Exception localException6)
      {
      }
    }
    System.out.println("Error quitting out of RTA request ticket data");
    return "error";
  }

  private String constructRTAArgs(String paramString)
  {
    Hashtable localHashtable = new Hashtable();
    localHashtable.put("rtatraceto", this.m_host);
    localHashtable.put("allowlocal", (G.getAllowLocal()) || (this.m_bOverrideAllowLocal) ? "yes" : "no");
    return "instanceid=" + paramString + "&tspec=" + this.m_applet.getTestSpecId() + "&pluginname=" + route.getRoute().getName() + "&args=" + U.base64Encode(U.hashTableToString(localHashtable, "#", "=", "+"));
  }

  public static void main(String[] paramArrayOfString)
  {
    try
    {
      URL localURL1 = new URL("http://69.73.191.250");
      String str1 = "65.32.115.48";
      String str2 = "69.73.191.250";
      int i = 7;
      int j = 8000;
      int k = 4;
      int m = 4;
      int n = 4;
      int i1 = 6000;
      URL localURL2 = new URL(localURL1, "myspeed/plugin/route/applet/trace?proto=2&url=" + str1 + "&t=" + System.currentTimeMillis() / 1000L + "&dh=" + str2 + "&pph=" + i + "&pt=" + j + "&nh=" + k + "&nhr=" + m + "&ppn=" + n + "&nt=" + i1 + "&al=false" + "&omni=true" + "&tspec=-3");
      InputStream localInputStream = localURL2.openStream();
      Vector localVector = new Vector();
      try
      {
        long[] arrayOfLong = { 1431655765L, 2004318077L, 858993503L };
        String str3;
        while ((str3 = U.readLineEnc(localInputStream, arrayOfLong)) != null)
        {
          System.out.println(str3);
          if (str3.startsWith("route="))
          {
            TraceRoute localTraceRoute = TraceRoute.fromAppletString(str3.substring(6), str1);
            if (localTraceRoute == null)
              System.out.println("TR==NULL");
            else
              localVector.add(localTraceRoute);
          }
        }
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
  }

  private InputStream performServerTrace(String paramString1, String paramString2, long paramLong)
  {
    int i = 0;
    URL localURL = new URL(this.m_applet.getAppletCodeBase(), "plugin/route/applet/trace?proto=2&url=" + this.m_host + (paramString1 != null ? "&instanceid=" + paramString1 : "") + (this.m_prot != null ? "&prot=" + this.m_prot : "") + (this.m_port != -1 ? "&port=" + this.m_port : "") + "&t=" + paramLong + "&dh=" + paramString2 + "&pph=" + G.getPingsPerHop() + "&pt=" + G.getPingTimeout() + "&nh=" + G.getNullHop() + "&nhr=" + G.getNullHopsRow() + "&ppn=" + G.getPingsPerNode() + "&nt=" + G.getNodeTimeout() + "&al=" + ((G.getAllowLocal()) || (this.m_bOverrideAllowLocal)) + "&omni=" + G.getParis() + "&tspec=" + this.m_applet.getTestSpecId());
    URLConnection localURLConnection = localURL.openConnection();
    int j = 0;
    InputStream localInputStream = localURLConnection.getInputStream();
    String str1 = localURLConnection.getContentType();
    Object localObject;
    String str2;
    String str3;
    if ((str1 != null) && (str1.trim().toLowerCase().startsWith("text")))
    {
      localObject = new DataInputStream(localInputStream);
      while ((this.m_bRun) && (i == 0) && ((str2 = ((DataInputStream)localObject).readLine()) != null))
        if (str2.equals("eof=OK"))
        {
          i = 1;
        }
        else
        {
          if (str2.startsWith("error="))
          {
            str3 = null;
            try
            {
              str3 = ((DataInputStream)localObject).readLine();
            }
            catch (Exception localException2)
            {
            }
            j = 1;
            String str4 = str2.substring("error=".length());
            setErrorTraceRoute(str4, str3);
            return localInputStream;
          }
          setData(str2);
        }
      setStatus(U.text("tracecomplete", this.m_host));
    }
    else
    {
      try
      {
        localObject = new long[] { 1431655765L, 2004318077L, 858993503L };
        do
        {
          if (str2.equals("eof=OK"))
          {
            i = 1;
          }
          else
          {
            if (str2.startsWith("error="))
            {
              str3 = null;
              try
              {
                str3 = U.readLineEnc(localInputStream, (long[])localObject);
              }
              catch (Exception localException3)
              {
              }
              j = 1;
              String str5 = str2.substring("error=".length());
              setErrorTraceRoute(str5, str3);
              return localInputStream;
            }
            setData(str2);
          }
          if (!this.m_bRun)
            break;
        }
        while ((str2 = U.readLineEnc(localInputStream, (long[])localObject)) != null);
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
    }
    if ((i != 0) || (j == 0))
      setStatus(U.text("tracecomplete", this.m_host));
    return localInputStream;
  }

  private static int getHopCount(TraceRoute paramTraceRoute)
  {
    if (paramTraceRoute == null)
      return -1;
    if (paramTraceRoute.hops == null)
      return -1;
    return paramTraceRoute.hops.length;
  }

  private static int getMaxLoss(TraceRoute paramTraceRoute)
  {
    if (paramTraceRoute == null)
      return -1;
    if ((paramTraceRoute.hops == null) || (paramTraceRoute.hops.length == 0))
      return -1;
    int i = -1;
    for (int j = 0; j < paramTraceRoute.hops.length; j++)
      if (i == -1)
        i = paramTraceRoute.hops[j].loss;
      else if (i < paramTraceRoute.hops[j].loss)
        i = paramTraceRoute.hops[j].loss;
    return i;
  }

  private static int getEndLoss(TraceRoute paramTraceRoute)
  {
    if (paramTraceRoute == null)
      return -1;
    if ((paramTraceRoute.hops == null) || (paramTraceRoute.hops.length == 0))
      return -1;
    return paramTraceRoute.hops[(paramTraceRoute.hops.length - 1)].loss;
  }

  private static int getEndMs(TraceRoute paramTraceRoute)
  {
    if (paramTraceRoute == null)
      return -1;
    if ((paramTraceRoute.hops == null) || (paramTraceRoute.hops.length == 0))
      return -1;
    return paramTraceRoute.hops[(paramTraceRoute.hops.length - 1)].ms;
  }

  private static String getToIP(TraceRoute paramTraceRoute)
  {
    if ((paramTraceRoute == null) || (paramTraceRoute.ip == null))
      return "?";
    if ("null".equals(paramTraceRoute.ip))
      return "?";
    return paramTraceRoute.ip;
  }

  private static int getMaxMs(TraceRoute paramTraceRoute)
  {
    if (paramTraceRoute == null)
      return -1;
    if ((paramTraceRoute.hops == null) || (paramTraceRoute.hops.length == 0))
      return -1;
    int i = -1;
    for (int j = 0; j < paramTraceRoute.hops.length; j++)
      if (i == -1)
        i = paramTraceRoute.hops[j].maxMs;
      else if (i < paramTraceRoute.hops[j].maxMs)
        i = paramTraceRoute.hops[j].maxMs;
    return i;
  }

  private static String getToName(TraceRoute paramTraceRoute)
  {
    if (paramTraceRoute == null)
      return "-";
    return paramTraceRoute.nname == null ? "-" : paramTraceRoute.nname;
  }

  private int getDNSTime()
  {
    return this.m_nDNSTime;
  }

  private String getEnteredName()
  {
    return this.m_enteredName == null ? "" : this.m_enteredName;
  }

  private static String getFromIP(TraceRoute paramTraceRoute)
  {
    if (paramTraceRoute == null)
      return "?";
    return paramTraceRoute.ipFrom == null ? "?" : paramTraceRoute.ipFrom;
  }

  private static TraceRoute parseDetailData(String paramString)
  {
    ErrorCode localErrorCode = new ErrorCode(0, null);
    StringTokenizer localStringTokenizer1 = new StringTokenizer(paramString, "\r\n");
    String str1 = null;
    Object localObject1 = null;
    Object localObject2 = null;
    while (localStringTokenizer1.hasMoreTokens())
    {
      localObject3 = localStringTokenizer1.nextToken();
      if (((String)localObject3).startsWith("detail."))
      {
        localErrorCode.detail = ((localErrorCode.detail == null ? "" : localErrorCode.detail) + (String)localObject3);
        if (((String)localObject3).startsWith("detail.ip="))
          str1 = ((String)localObject3).substring(10);
        else if (((String)localObject3).startsWith("detail.nname="))
          str1 = ((String)localObject3).substring(13);
        else if (((String)localObject3).startsWith("detail.ipfrom="))
          str1 = ((String)localObject3).substring(14);
      }
      else if (((String)localObject3).startsWith("error.code="))
      {
        try
        {
          localErrorCode.nCode = Integer.parseInt(((String)localObject3).substring(11));
        }
        catch (Exception localException1)
        {
        }
      }
      else if (((String)localObject3).startsWith("error.desc="))
      {
        localErrorCode.desc = ((String)localObject3).substring(11);
      }
    }
    if ((localErrorCode.nCode > 0) && (localErrorCode.desc != null))
    {
      localObject3 = new TraceRoute();
      ((TraceRoute)localObject3).errorResult = localErrorCode;
      if (196608 == (localErrorCode.nCode & 0xFFFF0000))
        ((TraceRoute)localObject3).error = TraceRoute.mapToOldStringError(localErrorCode.nCode & 0xFFFF);
      else
        ((TraceRoute)localObject3).error = "";
      ((TraceRoute)localObject3).ip = (str1 == null ? "?" : str1);
      ((TraceRoute)localObject3).nname = (localObject1 == null ? "?" : localObject1);
      ((TraceRoute)localObject3).ipFrom = (localObject2 == null ? "?" : localObject2);
      return localObject3;
    }
    Object localObject3 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    String str8 = null;
    String str9 = null;
    String str10 = null;
    String str11 = null;
    String str12 = null;
    String str13 = null;
    String str14 = "-";
    String str15 = "?";
    StringTokenizer localStringTokenizer2 = new StringTokenizer(paramString, "\r\n");
    System.out.println("data=" + paramString);
    while (localStringTokenizer2.hasMoreTokens())
    {
      localObject4 = localStringTokenizer2.nextToken().trim();
      if (((String)localObject4).startsWith("detail.ms="))
      {
        str3 = ((String)localObject4).substring("detail.ms=".length());
      }
      else if (((String)localObject4).startsWith("detail.time="))
      {
        str13 = ((String)localObject4).substring("detail.time=".length());
      }
      else if (((String)localObject4).startsWith("detail.loss="))
      {
        str2 = ((String)localObject4).substring("detail.loss=".length());
      }
      else if (((String)localObject4).startsWith("detail.ips="))
      {
        localObject3 = ((String)localObject4).substring("detail.ips=".length());
      }
      else if (((String)localObject4).startsWith("detail.hops="))
      {
        str4 = ((String)localObject4).substring("detail.hops=".length());
      }
      else if (((String)localObject4).startsWith("route.ipto="))
      {
        str5 = ((String)localObject4).substring("route.ipto=".length());
      }
      else if (((String)localObject4).startsWith("route.nameto="))
      {
        str14 = ((String)localObject4).substring("route.nameto=".length());
      }
      else if (((String)localObject4).startsWith("route.ipfrom="))
      {
        str15 = ((String)localObject4).substring("route.ipfrom=".length());
      }
      else if (((String)localObject4).startsWith("detail.maxms="))
      {
        str6 = ((String)localObject4).substring("detail.maxms=".length());
      }
      else if (((String)localObject4).startsWith("detail.minms="))
      {
        str7 = ((String)localObject4).substring("detail.minms=".length());
      }
      else if (((String)localObject4).startsWith("detail.affin="))
      {
        str9 = ((String)localObject4).substring("detail.affin=".length());
      }
      else if (((String)localObject4).startsWith("detail.nname="))
      {
        str8 = ((String)localObject4).substring("detail.nname=".length());
      }
      else if (((String)localObject4).startsWith("detail.error="))
      {
        str12 = ((String)localObject4).substring("detail.error=".length());
      }
      else if (((String)localObject4).startsWith("detail.nwho="))
      {
        str10 = ((String)localObject4).substring("detail.nwho=".length());
      }
      else if (((String)localObject4).startsWith("detail.geo="))
      {
        str11 = ((String)localObject4).substring("detail.geo=".length());
        System.out.println("GeoList: " + str11);
      }
    }
    if (str12 != null)
    {
      localObject4 = new TraceRoute();
      ((TraceRoute)localObject4).error = str12;
      ((TraceRoute)localObject4).ip = str5;
      ((TraceRoute)localObject4).nname = str14;
      ((TraceRoute)localObject4).ipFrom = str15;
      return localObject4;
    }
    Object localObject4 = U.stringToIntArray(str3, ",");
    int[] arrayOfInt1 = U.stringToIntArray(str2, ",");
    String[] arrayOfString1 = U.stringToStringArray((String)localObject3, ",");
    String[] arrayOfString2 = str8 == null ? null : U.stringToStringArray(str8, ",");
    String[] arrayOfString3 = str11 == null ? null : U.stringToStringArray(str11, "|");
    String[] arrayOfString4 = str10 == null ? null : U.stringToStringArray(str10, ",");
    int[] arrayOfInt2 = U.stringToIntArray(str4, ",");
    int[] arrayOfInt3 = str6 == null ? null : U.stringToIntArray(str6, ",");
    int[] arrayOfInt4 = str7 == null ? null : U.stringToIntArray(str7, ",");
    int[] arrayOfInt5 = str9 == null ? null : U.stringToIntArray(str9, ",");
    TraceRoute localTraceRoute = new TraceRoute();
    localTraceRoute.ip = str5;
    localTraceRoute.nname = str14;
    localTraceRoute.ipFrom = str15;
    localTraceRoute.uhl = "";
    try
    {
      localTraceRoute.time = Long.parseLong(str13);
    }
    catch (Exception localException2)
    {
    }
    TraceRouteHop[] arrayOfTraceRouteHop = new TraceRouteHop[arrayOfString1.length];
    for (int i = 0; i < arrayOfString1.length; i++)
    {
      arrayOfTraceRouteHop[i] = new TraceRouteHop();
      arrayOfTraceRouteHop[i].ip = arrayOfString1[i];
      if (arrayOfString1[i].equals(localTraceRoute.ip))
        arrayOfTraceRouteHop[i].bTerminal = true;
      arrayOfTraceRouteHop[i].hop = arrayOfInt2[i];
      arrayOfTraceRouteHop[i].sentTTL = arrayOfInt2[i];
      arrayOfTraceRouteHop[i].loss = arrayOfInt1[i];
      arrayOfTraceRouteHop[i].ms = localObject4[i];
      arrayOfTraceRouteHop[i].nAffinity = (arrayOfInt5 == null ? -1 : arrayOfInt5[i]);
      arrayOfTraceRouteHop[i].maxMs = (arrayOfInt3 == null ? localObject4[i] : arrayOfInt3[i]);
      arrayOfTraceRouteHop[i].minMs = (arrayOfInt4 == null ? localObject4[i] : arrayOfInt4[i]);
      arrayOfTraceRouteHop[i].dns = (arrayOfString2 == null ? "-" : arrayOfString2[i]);
      arrayOfTraceRouteHop[i].network = (arrayOfString4 == null ? "-" : arrayOfString4[i]);
      arrayOfTraceRouteHop[i].bAuthoritativeLocation = (arrayOfString3 != null);
      arrayOfTraceRouteHop[i].locCode = (arrayOfTraceRouteHop[i].bAuthoritativeLocation ? arrayOfString3[i] : arrayOfString3 == null ? null : arrayOfString3[i].substring(1));
      if ((arrayOfString3 != null) && (!"*".equals(arrayOfTraceRouteHop[i].locCode)))
      {
        localStringTokenizer2 = new StringTokenizer(arrayOfTraceRouteHop[i].locCode, ";");
        String str16 = localStringTokenizer2.nextToken();
        arrayOfTraceRouteHop[i].location = str16;
        U.setPlaces(arrayOfTraceRouteHop[i].locCode);
      }
      else
      {
        arrayOfTraceRouteHop[i].location = "-";
      }
    }
    localTraceRoute.hops = arrayOfTraceRouteHop;
    return localTraceRoute;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.AppletPanel
 * JD-Core Version:    0.6.2
 */