package mcsaplugins.firewall;

import java.applet.Applet;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.InputStream;
import myspeedserver.applet.AppletPlugin;
import myspeedserver.applet.AppletTest;
import myspeedserver.applet.ErrorCode;
import myspeedserver.applet.U;

public class firewall extends AppletPlugin
{
  private FirewallTab m_tab;
  private FirewallTest m_test;

  public firewall(Applet paramApplet)
  {
    super(paramApplet, TX("firewall"));
  }

  public void runTest()
  {
    doAppletDelaySelectTab(this.m_tab);
    this.m_test = new FirewallTest(this.m_tab, this);
    this.m_test.runTest();
  }

  public AppletTest getTest()
  {
    return this.m_test;
  }

  public ErrorCode getErrorCode()
  {
    return this.m_test == null ? null : this.m_test.getErrorCode();
  }

  public void updateGUI()
  {
    if (this.m_tab != null)
      this.m_tab.repaint();
  }

  public void updateCurrentTest()
  {
    if (this.m_tab != null)
      this.m_tab.updateCurrentTest();
  }

  public void doReportResults()
  {
    super.doReportResults();
  }

  public void reset()
  {
    this.m_test = null;
  }

  public void notifyTestBegin()
  {
    if (this.m_tab != null)
      this.m_tab.setStartButtonVisible(false);
  }

  public void doFirstTimeInit()
  {
    this.m_tab = new FirewallTab(getApplet(), this);
    this.m_tab.doFirstTimeInit();
    addTabToApplet(this.m_tab, true);
  }

  public Image getAppletImage(String paramString)
  {
    byte[] arrayOfByte = getAppletData(paramString);
    return arrayOfByte != null ? Toolkit.getDefaultToolkit().createImage(arrayOfByte) : null;
  }

  private byte[] getAppletData(String paramString)
  {
    DataInputStream localDataInputStream = null;
    Object localObject1 = new byte[65535];
    try
    {
      int i = 0;
      localDataInputStream = new DataInputStream(getClass().getResourceAsStream(paramString));
      while (localDataInputStream.available() > 0)
      {
        while (localObject1.length - i < localDataInputStream.available())
        {
          byte[] arrayOfByte = new byte[localObject1.length * 2];
          System.arraycopy(localObject1, 0, arrayOfByte, 0, localObject1.length);
          localObject1 = arrayOfByte;
        }
        int j = localDataInputStream.read((byte[])localObject1, i, localObject1.length - i);
        if (j == -1)
          break;
        i += j;
      }
      localObject1 = U.resize((byte[])localObject1, i);
      Object localObject3 = localObject1;
      return localObject3;
    }
    catch (Exception localException1)
    {
    }
    finally
    {
      try
      {
        localDataInputStream.close();
      }
      catch (Exception localException4)
      {
      }
    }
    return null;
  }

  public String doMySpeedVariableSubstitution(String paramString, boolean paramBoolean)
  {
    FirewallTestItem localFirewallTestItem = this.m_test == null ? null : this.m_test.getCurrentTest();
    int i = localFirewallTestItem == null ? -1 : localFirewallTestItem.getLowPort();
    int j = localFirewallTestItem == null ? -1 : localFirewallTestItem.getHighPort();
    String str = i + "-" + j;
    int[][] arrayOfInt = localFirewallTestItem == null ? null : localFirewallTestItem.getResponseTimes();
    StringBuffer localStringBuffer = new StringBuffer("new Array(");
    int k = 1;
    int m = 0;
    if ((arrayOfInt != null) && (arrayOfInt.length > 0))
    {
      m = arrayOfInt[0] != null ? arrayOfInt[0].length : 0;
      for (int n = 0; n < arrayOfInt.length; n++)
        for (int i1 = 0; i1 < m; i1++)
        {
          if (k != 0)
            k = 0;
          else
            localStringBuffer.append(',');
          if ((arrayOfInt.length == 1) && (m == 1))
            localStringBuffer.append("'" + arrayOfInt[0] + "'");
          else
            localStringBuffer.append(i1 < arrayOfInt[n].length ? arrayOfInt[n][i1] : -1);
        }
    }
    localStringBuffer.append(")");
    paramString = U.subst(paramString, TX("$FIREWALL.AVGRESPONSE$"), localFirewallTestItem == null ? -1.0F : localFirewallTestItem.getAvgResponseTime());
    paramString = U.subst(paramString, TX("$FIREWALL.MAXRESPONSE$"), localFirewallTestItem == null ? -1 : localFirewallTestItem.getMaxResponseTime());
    paramString = U.subst(paramString, TX("$FIREWALL.MINRESPONSE$"), localFirewallTestItem == null ? -1 : localFirewallTestItem.getMinResponseTime());
    paramString = U.subst(paramString, TX("$FIREWALL.PORT$"), "\"" + str + "\"");
    paramString = U.subst(paramString, TX("$FIREWALL.LOWPORT$"), i);
    paramString = U.subst(paramString, TX("$FIREWALL.HIGHPORT$"), j);
    paramString = U.subst(paramString, TX("$FIREWALL.TESTSPERPORT$"), m);
    paramString = U.subst(paramString, TX("$FIREWALL.PROTOCOL$"), "\"" + (localFirewallTestItem == null ? "" : localFirewallTestItem.getProtocol()) + "\"");
    paramString = U.subst(paramString, TX("$FIREWALL.RESPONSETIMES$"), localStringBuffer.toString());
    return paramString;
  }

  public String getReportMSS()
  {
    FirewallTestItem localFirewallTestItem = this.m_test == null ? null : this.m_test.getCurrentTest();
    int i = localFirewallTestItem == null ? -1 : localFirewallTestItem.getLowPort();
    int j = localFirewallTestItem == null ? -1 : localFirewallTestItem.getHighPort();
    String str = i + "-" + j;
    return (localFirewallTestItem != null ? TX("&firewall.avgresponse=") + (int)(localFirewallTestItem.getAvgResponseTime() * 10.0F) / 10.0D : "") + (localFirewallTestItem != null ? TX("&firewall.maxresponse=") + localFirewallTestItem.getMaxResponseTime() : "") + (localFirewallTestItem != null ? TX("&firewall.minresponse=") + localFirewallTestItem.getMinResponseTime() : "") + (localFirewallTestItem != null ? TX("&firewall.ports=") + str : "") + (localFirewallTestItem != null ? TX("&firewall.protocol=") + localFirewallTestItem.getProtocol() : "");
  }

  public String getDetailResults()
  {
    FirewallTestItem localFirewallTestItem = this.m_test == null ? null : this.m_test.getCurrentTest();
    if (localFirewallTestItem == null)
      return "";
    int[][] arrayOfInt = localFirewallTestItem.getResponseTimes();
    int i = localFirewallTestItem.getLowPort();
    StringBuffer localStringBuffer = new StringBuffer();
    for (int j = 0; (arrayOfInt != null) && (j < arrayOfInt.length); j++)
    {
      localStringBuffer.append((j == 0 ? "" : ",") + "p=" + (i + j) + localFirewallTestItem.getProtocol().substring(0, 1) + ",");
      localStringBuffer.append(toString(arrayOfInt[j]));
    }
    return "&detail.times=" + localStringBuffer.toString();
  }

  private static String toString(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null)
      return null;
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      if (localStringBuffer.length() > 0)
        localStringBuffer.append(',');
      localStringBuffer.append(paramArrayOfInt[i]);
    }
    return localStringBuffer.toString();
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.firewall.firewall
 * JD-Core Version:    0.6.2
 */