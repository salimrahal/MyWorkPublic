package mcsaplugins.voip;

import java.applet.Applet;
import java.io.PrintStream;
import java.net.URL;
import java.util.Properties;
import myspeedserver.applet.AppletPlugin;
import myspeedserver.applet.AppletTest;
import myspeedserver.applet.ErrorCode;
import myspeedserver.applet.U;

public class voip extends AppletPlugin
{
  private VoIPTest m_voipTest;
  private VoIPTab m_voipTab;

  public voip(Applet paramApplet)
  {
    super(paramApplet, TX("voip"));
  }

  public void runTest()
  {
    doAppletDelaySelectTab(this.m_voipTab);
    int i = iniGetInteger(TX("voip.port"), -1);
    int j = iniGetInteger(TX("voip.uploadport"), -1);
    int k = iniGetInteger(TX("voip.sipport"), -1);
    int m = iniGetInteger(TX("voip_bytesperpacket"), TX("voipbytesperpacket"), 8);
    int n = Math.max(2, Math.min(120, iniGetInteger(TX("voip_simulationseconds"), TX("voipsimulationseconds"), 10)));
    int i1 = iniGetInteger(TX("voip_packetspersecond"), TX("voippacketspersecond"), 160) * iniGetInteger(TX("voip_lines"), TX("voiplines"), 1);
    int i2 = iniGetInteger(TX("voip_discardms"), TX("voipdiscardms"), 50);
    int i3 = iniGetInteger(TX("voip_omitpackets"), TX("voipomitpackets"), 0);
    int i4 = iniGetInteger(TX("voip_dscp"), TX("voipdscp"), 0);
    String str = iniGetString(TX("voip_siptests"));
    int i5 = iniGetInteger(TX("voip.rttnumtests"), 4);
    int i6 = iniGetInteger(TX("voip.rttport"), 0);
    int i7 = iniGetInteger(TX("voip.uplossdistributionwindow"), 20);
    int i8 = iniGetInteger(TX("voip.downlossdistributionwindow"), 20);
    if (i != -1)
    {
      this.m_voipTest = new VoIPTest(this.m_voipTab, this, base().getHost(), i, j, k, m, n, i1, i2, i3, i4, str, i5, i6, i7, i8);
      this.m_voipTest.runTest();
    }
    else
    {
      System.out.println(TX("VOIP Port=-1; cannot reach server"));
    }
  }

  public synchronized void doRepaint()
  {
    if (this.m_voipTab != null)
      this.m_voipTab.repaint();
  }

  public Properties getOverrideSettings()
  {
    return this.m_voipTab == null ? null : this.m_voipTab.getOverrideParameters();
  }

  public void reset()
  {
    this.m_voipTest = null;
  }

  public VoIPTest getVoIPTest()
  {
    return this.m_voipTest;
  }

  public AppletTest getTest()
  {
    return this.m_voipTest;
  }

  public void notifyTestBegin()
  {
    this.m_voipTab.stopAnimation();
    this.m_voipTab.setStartButtonVisible(false);
  }

  public void doFirstTimeInit()
  {
    this.m_voipTab = new VoIPTab(getApplet(), this);
    this.m_voipTab.doFirstTimeInit();
    addTabToApplet(this.m_voipTab, true);
  }

  public String doMySpeedVariableSubstitution(String paramString, boolean paramBoolean)
  {
    paramString = U.subst(paramString, TX("$VOIP.JITTER$"), this.m_voipTest == null ? -1.0D : this.m_voipTest.getUploadJitter());
    paramString = U.subst(paramString, TX("$VOIP.PACKETLOSS$"), this.m_voipTest == null ? -1.0D : this.m_voipTest.getUploadLoss());
    paramString = U.subst(paramString, TX("$VOIP.MOS$"), this.m_voipTest == null ? -1.0F : this.m_voipTest.getMOS());
    paramString = U.subst(paramString, TX("$VOIP.DJITTER$"), this.m_voipTest == null ? -1.0D : this.m_voipTest.getDownloadJitter());
    paramString = U.subst(paramString, TX("$VOIP.DPACKETLOSS$"), this.m_voipTest == null ? -1.0D : this.m_voipTest.getDownloadLoss());
    paramString = U.subst(paramString, TX("$VOIP.ORDER$"), this.m_voipTest == null ? -1.0D : this.m_voipTest.getUploadOrder());
    paramString = U.subst(paramString, TX("$VOIP.DORDER$"), this.m_voipTest == null ? -1.0D : this.m_voipTest.getDownloadOrder());
    paramString = U.subst(paramString, TX("$VOIP.DISCARDS$"), this.m_voipTest == null ? -1.0D : this.m_voipTest.getUploadDiscards());
    paramString = U.subst(paramString, TX("$VOIP.REGISTER$"), this.m_voipTest == null ? -1 : this.m_voipTest.getRegisterTime());
    paramString = U.subst(paramString, TX("$VOIP.INVITE$"), this.m_voipTest == null ? -1 : this.m_voipTest.getInviteTime());
    paramString = U.subst(paramString, TX("$VOIP.BYE$"), this.m_voipTest == null ? -1 : this.m_voipTest.getByeTime());
    paramString = U.subst(paramString, TX("$JITTER$"), this.m_voipTest == null ? -1.0D : this.m_voipTest.getUploadJitter());
    paramString = U.subst(paramString, TX("$PACKETLOSS$"), this.m_voipTest == null ? -1.0D : this.m_voipTest.getUploadLoss());
    paramString = U.subst(paramString, TX("$MOS$"), this.m_voipTest == null ? -1.0F : this.m_voipTest.getMOS());
    paramString = U.subst(paramString, TX("$DJITTER$"), this.m_voipTest == null ? -1.0D : this.m_voipTest.getDownloadJitter());
    paramString = U.subst(paramString, TX("$DPACKETLOSS$"), this.m_voipTest == null ? -1.0D : this.m_voipTest.getDownloadLoss());
    paramString = U.subst(paramString, TX("$ORDER$"), this.m_voipTest == null ? -1.0D : this.m_voipTest.getUploadOrder());
    paramString = U.subst(paramString, TX("$DISCARDS$"), this.m_voipTest == null ? -1.0D : this.m_voipTest.getUploadDiscards());
    return paramString;
  }

  public String getReportMSS()
  {
    float f = this.m_voipTest == null ? 0.0F : this.m_voipTest.getMOS();
    return (this.m_voipTest != null ? TX("&voip.jitter=") + (int)(this.m_voipTest.getUploadJitter() * 10.0D) / 10.0D : "") + (this.m_voipTest != null ? TX("&voip.loss=") + (int)(this.m_voipTest.getUploadLoss() * 10.0D) / 10.0D : "") + (this.m_voipTest != null ? TX("&voip.djitter=") + (int)(this.m_voipTest.getDownloadJitter() * 10.0D) / 10.0D : "") + (this.m_voipTest != null ? TX("&voip.dloss=") + (int)(this.m_voipTest.getDownloadLoss() * 10.0D) / 10.0D : "") + (this.m_voipTest != null ? TX("&voip.mos=") + (f == 0.0F ? -1.0F : f) : "") + (this.m_voipTest != null ? TX("&voip.order=") + (int)(this.m_voipTest.getUploadOrder() * 10.0D) / 10.0D : "") + (this.m_voipTest != null ? TX("&voip.dorder=") + (int)(this.m_voipTest.getDownloadOrder() * 10.0D) / 10.0D : "") + (this.m_voipTest != null ? TX("&voip.discards=") + (int)(this.m_voipTest.getUploadDiscards() * 10.0D) / 10.0D : "") + (this.m_voipTest != null ? TX("&voip.register=") + this.m_voipTest.getRegisterTime() : "") + (this.m_voipTest != null ? TX("&voip.invite=") + this.m_voipTest.getInviteTime() : "") + (this.m_voipTest != null ? TX("&voip.bye=") + this.m_voipTest.getByeTime() : "");
  }

  public String getDetailResults()
  {
    StringBuffer localStringBuffer = new StringBuffer(65536);
    if (this.m_voipTest != null)
    {
      String[] arrayOfString1 = toJitterLoss(this.m_voipTest.getUploadJitterArray(), this.m_voipTest.getUploadLossArray());
      if (arrayOfString1 != null)
      {
        localStringBuffer.append("&detail.jtr=").append(arrayOfString1[0]);
        localStringBuffer.append("&detail.loss=").append(arrayOfString1[1]);
      }
      String[] arrayOfString2 = toJitterLoss(this.m_voipTest.getDownloadJitterArray(), this.m_voipTest.getDownloadLossArray());
      if (arrayOfString2 != null)
      {
        localStringBuffer.append("&detail.djtr=").append(arrayOfString2[0]);
        localStringBuffer.append("&detail.dloss=").append(arrayOfString2[1]);
      }
    }
    return localStringBuffer.toString();
  }

  public ErrorCode getErrorCode()
  {
    return this.m_voipTest == null ? null : this.m_voipTest.getError();
  }

  public String[] toJitterLoss(float[] paramArrayOfFloat, boolean[] paramArrayOfBoolean)
  {
    if ((paramArrayOfFloat == null) || (paramArrayOfBoolean == null))
      return null;
    StringBuffer localStringBuffer1 = new StringBuffer();
    StringBuffer localStringBuffer2 = new StringBuffer();
    for (int i = 0; i < paramArrayOfFloat.length; i++)
    {
      if (localStringBuffer1.length() > 0)
      {
        localStringBuffer1.append(",");
        localStringBuffer2.append(",");
      }
      localStringBuffer1.append(paramArrayOfFloat[i]);
      localStringBuffer2.append(paramArrayOfBoolean[i] != 0 ? "y" : "n");
    }
    return new String[] { localStringBuffer1.toString(), localStringBuffer2.toString() };
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.voip.voip
 * JD-Core Version:    0.6.2
 */