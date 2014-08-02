package mcsaplugins.firewall;

import java.net.InetAddress;
import java.net.URL;
import java.util.StringTokenizer;
import myspeedserver.applet.AppletPlugin;
import myspeedserver.applet.AppletTest;
import myspeedserver.applet.ErrorCode;

public class FirewallTest extends AppletTest
{
  private firewall m_firewall;
  private FirewallTestItem[] m_tests;
  private int m_nCurrentTest;
  private String m_testSpec;
  private int m_nPercentDone;
  private static String m_threadtimeout;
  private boolean m_bRun;

  public FirewallTest(FirewallTab paramFirewallTab, firewall paramfirewall)
  {
    super(paramfirewall, paramFirewallTab);
    this.m_firewall = paramfirewall;
    this.m_testSpec = paramfirewall.iniGetString(TX("firewall_testports"));
    m_threadtimeout = paramfirewall.iniGetString(TX("firewall_threadtimeout"), null);
  }

  public FirewallTestItem getCurrentTest()
  {
    return (this.m_tests == null) || (this.m_tests.length == 0) ? null : this.m_tests[java.lang.Math.min(this.m_nCurrentTest, this.m_tests.length - 1)];
  }

  public ErrorCode getErrorCode()
  {
    return (this.m_tests == null) || (this.m_nCurrentTest >= this.m_tests.length) || (this.m_nCurrentTest < 0) ? null : this.m_tests[this.m_nCurrentTest].getErrorCode();
  }

  public FirewallTestItem[] getTests()
  {
    return this.m_tests;
  }

  public int getCurrentTestNum()
  {
    return this.m_nCurrentTest;
  }

  public static FirewallTestItem[] parseTestSpec(String paramString1, FirewallTest paramFirewallTest, String paramString2)
  {
    StringTokenizer localStringTokenizer1 = new StringTokenizer(paramString1 == null ? "" : paramString1, "+");
    FirewallTestItem[] arrayOfFirewallTestItem = new FirewallTestItem[localStringTokenizer1.countTokens()];
    int i = 0;
    while (localStringTokenizer1.hasMoreTokens())
    {
      localObject = localStringTokenizer1.nextToken();
      try
      {
        StringTokenizer localStringTokenizer2 = new StringTokenizer((String)localObject, "/");
        int j = Integer.parseInt(localStringTokenizer2.nextToken());
        int k = Integer.parseInt(localStringTokenizer2.nextToken());
        boolean bool = "tcp".equals(localStringTokenizer2.nextToken().toLowerCase());
        int m = Integer.parseInt(localStringTokenizer2.nextToken());
        String str = localStringTokenizer2.nextToken();
        long l = (m_threadtimeout == null) || (m_threadtimeout.length() < 2) ? -1L : Long.parseLong(m_threadtimeout);
        if ((j > 0) && (j < 65536) && (k > 0) && (k < 65536))
          arrayOfFirewallTestItem[(i++)] = new FirewallTestItem(paramFirewallTest, paramString2, j, k, bool, m, str, l);
      }
      catch (Exception localException)
      {
        if (paramFirewallTest != null)
          paramFirewallTest.OUT(TX("Error loading firewall test spec: ") + (String)localObject + ": " + localException);
      }
    }
    Object localObject = new FirewallTestItem[i];
    System.arraycopy(arrayOfFirewallTestItem, 0, localObject, 0, i);
    return localObject;
  }

  public void runTest()
  {
    this.m_bRun = true;
    this.m_nPercentDone = 0;
    String str = null;
    try
    {
      str = InetAddress.getByName(getBase().getHost()).getHostAddress();
    }
    catch (Exception localException)
    {
    }
    this.m_tests = parseTestSpec(this.m_testSpec, this, str);
    updateGUI();
    for (int i = 0; (this.m_bRun) && (i < this.m_tests.length); i++)
    {
      this.m_nCurrentTest = i;
      updateCurrentTest();
      this.m_tests[i].runTest();
      if (i + 1 < this.m_tests.length)
        doReportResults();
      this.m_nPercentDone = ((i + 1) * 100 / this.m_tests.length);
    }
    this.m_nPercentDone = 100;
    this.m_bRun = false;
    updateGUI();
  }

  public boolean isRunning()
  {
    return this.m_bRun;
  }

  public int getPercentComplete()
  {
    return this.m_nPercentDone;
  }

  public void updateGUI()
  {
    this.m_firewall.updateGUI();
  }

  public void updateCurrentTest()
  {
    this.m_firewall.updateCurrentTest();
  }

  public URL getBase()
  {
    return this.m_plugin.base();
  }

  public void OUT(String paramString)
  {
    this.m_firewall.OUT(paramString);
  }

  private void doReportResults()
  {
    this.m_firewall.doReportResults();
  }

  public Object[] getSummaryItem(String paramString)
  {
    return null;
  }

  public String[] getAdvancedDataItem(String paramString)
  {
    return null;
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.firewall.FirewallTest
 * JD-Core Version:    0.6.2
 */