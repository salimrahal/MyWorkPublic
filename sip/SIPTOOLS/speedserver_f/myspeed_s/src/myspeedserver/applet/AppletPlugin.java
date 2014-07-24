package myspeedserver.applet;

import java.applet.Applet;
import java.awt.Image;
import java.net.URL;
import java.util.Hashtable;
import java.util.Properties;

public abstract class AppletPlugin
{
  private myspeed LF;
  private String MG;
  private String OE;
  private StringBuffer KF = new StringBuffer();

  public AppletPlugin(Applet paramApplet, String paramString)
  {
    this.LF = ((myspeed)paramApplet);
    this.MG = paramString;
  }

  public double iniGetDouble(String paramString, double paramDouble)
  {
    return this.LF.iniGetDouble(paramString, paramDouble);
  }

  public int iniGetInteger(String paramString, int paramInt)
  {
    return this.LF.iniGetInteger(paramString, null, paramInt);
  }

  public int iniGetInteger(String paramString1, String paramString2, int paramInt)
  {
    return this.LF.iniGetInteger(paramString1, paramString2, paramInt);
  }

  public long iniGetLong(String paramString, long paramLong)
  {
    return this.LF.iniGetLong(paramString, null, paramLong);
  }

  public long iniGetLong(String paramString1, String paramString2, long paramLong)
  {
    return this.LF.iniGetLong(paramString1, paramString2, paramLong);
  }

  public String iniGetString(String paramString)
  {
    return this.LF.iniGetString(paramString, null);
  }

  public String iniGetString(String paramString1, String paramString2)
  {
    return this.LF.iniGetString(paramString1, paramString2);
  }

  public String iniGetProfessionalString(String paramString)
  {
    return this.LF.iniGetProfessionalString(paramString);
  }

  public void addCombinedGraphResults(String paramString, String[] paramArrayOfString)
  {
    this.LF.addCombinedGraphResults(paramString, paramArrayOfString);
  }

  public void addGraphResults(String paramString, long[] paramArrayOfLong, int[] paramArrayOfInt, float[] paramArrayOfFloat, boolean[] paramArrayOfBoolean)
  {
    this.LF.addGraphResults(paramString, paramArrayOfLong, paramArrayOfInt, paramArrayOfFloat, paramArrayOfBoolean);
  }

  public void addGraphResults(String paramString, long[] paramArrayOfLong, int[] paramArrayOfInt, boolean paramBoolean, int paramInt)
  {
    this.LF.addGraphResults(paramString, paramArrayOfLong, paramArrayOfInt, paramBoolean, paramInt);
  }

  public void addGraphResults(String paramString, long[][] paramArrayOfLong, int[][] paramArrayOfInt)
  {
    this.LF.addGraphResults(paramString, paramArrayOfLong, paramArrayOfInt);
  }

  public String RC(String paramString)
  {
    return this.LF.RC(paramString);
  }

  public String RC(String paramString1, String paramString2)
  {
    return this.LF.RC(paramString1, paramString2);
  }

  public String RC(String paramString, String[] paramArrayOfString)
  {
    return this.LF.RC(paramString, paramArrayOfString);
  }

  public URL base()
  {
    return this.LF.base();
  }

  public Image getImage(String paramString)
  {
    return this.LF.getImageFromJar(paramString);
  }

  public String getName()
  {
    return this.MG;
  }

  public void setMSSID(String paramString)
  {
    this.OE = paramString;
  }

  public String getMSSID()
  {
    return this.OE;
  }

  public AppletTest getTest()
  {
    return null;
  }

  public void setTest(AppletTest paramAppletTest)
  {
  }

  public String getLog()
  {
    return this.KF.toString();
  }

  public void resetLog()
  {
    this.KF.setLength(0);
  }

  public void OUT(String paramString)
  {
    this.KF.append(paramString + "\n");
    this.LF.OUT(paramString);
  }

  public void ERR(String paramString)
  {
    this.KF.append(paramString + "\n");
    this.LF.ERR(paramString);
  }

  public void logException(String paramString, Throwable paramThrowable)
  {
    this.LF.logException(paramString, paramThrowable);
  }

  public void addTabToApplet(AppletTab paramAppletTab, boolean paramBoolean)
  {
    this.LF.addTab(paramAppletTab, paramBoolean);
  }

  public AppletPlugin[] getPlugins()
  {
    return this.LF.getPlugins();
  }

  public int getTestSpecId()
  {
    return this.LF.getTestSpecId();
  }

  protected Applet getApplet()
  {
    return this.LF;
  }

  protected void doAppletDelaySelectTab(AppletTab paramAppletTab)
  {
    this.LF.delaySelectTab(paramAppletTab);
  }

  protected void doReportResults()
  {
    this.LF.doReportResults(this);
  }

  public abstract void doFirstTimeInit();

  public abstract String doMySpeedVariableSubstitution(String paramString, boolean paramBoolean);

  public abstract String getReportMSS();

  public ErrorCode getErrorCode()
  {
    return null;
  }

  public abstract String getDetailResults();

  public AppletTest createTest(Hashtable paramHashtable1, Hashtable paramHashtable2)
  {
    return null;
  }

  public Properties getOverrideSettings()
  {
    return null;
  }

  public abstract void runTest();

  public abstract void notifyTestBegin();
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/myspeed_s.jar
 * Qualified Name:     myspeedserver.applet.AppletPlugin
 * JD-Core Version:    0.6.2
 */