package mcsaplugins.video;

import java.applet.Applet;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import myspeedserver.applet.AppletPlugin;
import myspeedserver.applet.AppletTest;
import myspeedserver.applet.U;

public class video extends AppletPlugin
{
  private VideoTest m_test;
  private VideoTab m_tab;

  public video(Applet paramApplet)
  {
    super(paramApplet, TX("video"));
  }

  public void runTest()
  {
    doAppletDelaySelectTab(this.m_tab);
    int i = iniGetInteger(TX("video.port"), -1);
    int j = iniGetInteger(TX("video_videobpp"), TX("videobpp"), 160);
    int k = iniGetInteger(TX("video_videopps"), TX("videopps"), 20);
    int m = iniGetInteger(TX("video_audiobpp"), TX("audiobpp"), 32);
    int n = iniGetInteger(TX("video_audiopps"), TX("audiopps"), 20);
    int i1 = iniGetInteger(TX("video_numsecs"), TX("numsecs"), 10);
    int i2 = iniGetInteger(TX("video_audiocdt"), TX("audiocdt"), 100);
    int i3 = iniGetInteger(TX("video_videocdt"), TX("videocdt"), 100);
    int i4 = iniGetInteger(TX("video_audiosdt"), TX("audiosdt"), 500);
    int i5 = iniGetInteger(TX("video_videosdt"), TX("videosdt"), 500);
    int i6 = iniGetInteger(TX("video_omitms"), TX("omitms"), 500);
    boolean bool = (TX("yes").equals(iniGetString(TX("video.showrequesttimes")))) || (TX("yes").equals(iniGetString(TX("showrequesttimes"))));
    if (i != -1)
    {
      this.m_test = new VideoTest(this.m_tab, this, base().getHost(), i, j, k, i3, i5, m, n, i2, i4, i1 * 1000, i6, bool);
      this.m_test.runTest();
    }
    else
    {
      OUT(TX("Video Port=-1; cannot reach server"));
    }
  }

  public AppletTest getTest()
  {
    return this.m_test;
  }

  public void notifyTestBegin()
  {
    this.m_tab.setShowStartButton(false);
  }

  public void doFirstTimeInit()
  {
    this.m_tab = new VideoTab(getApplet(), this);
    this.m_tab.doFirstTimeInit();
    addTabToApplet(this.m_tab, true);
  }

  public void reset()
  {
    this.m_test = null;
  }

  public String doMySpeedVariableSubstitution(String paramString, boolean paramBoolean)
  {
    paramString = U.subst(paramString, TX("$VIDEO.VJITTER$"), this.m_test == null ? -1.0D : this.m_test.getClientVideoJitter());
    paramString = U.subst(paramString, TX("$VIDEO.VSJITTER$"), this.m_test == null ? -1.0D : this.m_test.getServerVideoJitter());
    paramString = U.subst(paramString, TX("$VIDEO.VPACKETLOSS$"), this.m_test == null ? -1 : this.m_test.getVideoPacketLoss());
    paramString = U.subst(paramString, TX("$VIDEO.AJITTER$"), this.m_test == null ? -1.0D : this.m_test.getClientAudioJitter());
    paramString = U.subst(paramString, TX("$VIDEO.ASJITTER$"), this.m_test == null ? -1.0D : this.m_test.getServerAudioJitter());
    paramString = U.subst(paramString, TX("$VIDEO.APACKETLOSS$"), this.m_test == null ? -1 : this.m_test.getAudioPacketLoss());
    paramString = U.subst(paramString, TX("$VIDEO.VDISCARDS$"), this.m_test == null ? -1.0D : this.m_test.getVideoDiscards());
    paramString = U.subst(paramString, TX("$VIDEO.ADISCARDS$"), this.m_test == null ? -1.0D : this.m_test.getAudioDiscards());
    paramString = U.subst(paramString, TX("$VIDEO.RTT$"), this.m_test == null ? -1 : this.m_test.getRTT());
    paramString = U.subst(paramString, TX("$VIDEO.MAXDELAY$"), this.m_test == null ? -1 : this.m_test.getMaxDelay());
    paramString = U.subst(paramString, TX("$VIDEO.SETUPTIME$"), this.m_test == null ? -1 : this.m_test.getSetupTime());
    paramString = U.subst(paramString, TX("$VIDEO.DESCRIBETIME$"), this.m_test == null ? -1 : this.m_test.getDescribeTime());
    paramString = U.subst(paramString, TX("$VIDEO.PLAYTIME$"), this.m_test == null ? -1 : this.m_test.getPlayTime());
    paramString = U.subst(paramString, TX("$VJITTER$"), this.m_test == null ? -1.0D : this.m_test.getClientVideoJitter());
    paramString = U.subst(paramString, TX("$VPACKETLOSS$"), this.m_test == null ? -1 : this.m_test.getVideoPacketLoss());
    paramString = U.subst(paramString, TX("$AJITTER$"), this.m_test == null ? -1.0D : this.m_test.getClientAudioJitter());
    paramString = U.subst(paramString, TX("$APACKETLOSS$"), this.m_test == null ? -1 : this.m_test.getAudioPacketLoss());
    paramString = U.subst(paramString, TX("$VDISCARDS$"), this.m_test == null ? -1.0D : this.m_test.getVideoDiscards());
    paramString = U.subst(paramString, TX("$ADISCARDS$"), this.m_test == null ? -1.0D : this.m_test.getAudioDiscards());
    return paramString;
  }

  public String getReportMSS()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Hashtable localHashtable = this.m_test.getAccessCTMetrics();
    int i = localHashtable != null ? 1 : 0;
    String str1 = TX("video");
    if (i != 0)
      str1 = TX("act");
    Enumeration localEnumeration = localHashtable == null ? null : localHashtable.keys();
    while ((localEnumeration != null) && (localEnumeration.hasMoreElements()))
    {
      String str2 = (String)localEnumeration.nextElement();
      if (!str2.startsWith("detail."))
        localStringBuffer.append(TX("&") + str1 + TX(".") + str2 + TX("=") + (String)localHashtable.get(str2));
    }
    return (this.m_test != null ? TX("&video.ajitter=") + (int)(this.m_test.getClientAudioJitter() * 10.0D) / 10.0D : "") + (this.m_test != null ? TX("&video.asjitter=") + (int)(this.m_test.getServerAudioJitter() * 10.0D) / 10.0D : "") + (this.m_test != null ? TX("&video.aloss=") + this.m_test.getAudioPacketLoss() * 10 / 10.0D : "") + (this.m_test != null ? TX("&video.vjitter=") + (int)(this.m_test.getClientVideoJitter() * 10.0D) / 10.0D : "") + (this.m_test != null ? TX("&video.vsjitter=") + (int)(this.m_test.getServerVideoJitter() * 10.0D) / 10.0D : "") + (this.m_test != null ? TX("&video.vloss=") + this.m_test.getVideoPacketLoss() * 10 / 10.0D : "") + (this.m_test != null ? TX("&video.vdiscards=") + (int)(this.m_test.getVideoDiscards() * 10.0D) / 10.0D : "") + (this.m_test != null ? TX("&video.adiscards=") + (int)(this.m_test.getAudioDiscards() * 10.0D) / 10.0D : "") + (this.m_test != null ? TX("&video.rtt=") + this.m_test.getRTT() : "") + (this.m_test != null ? TX("&video.maxdelay=") + this.m_test.getMaxDelay() : "") + (this.m_test != null ? TX("&video.setuptime=") + this.m_test.getSetupTime() : "") + (this.m_test != null ? TX("&video.describetime=") + this.m_test.getDescribeTime() : "") + (this.m_test != null ? TX("&video.playtime=") + this.m_test.getPlayTime() : "") + (i != 0 ? localStringBuffer.toString() : "");
  }

  public String getDetailResults()
  {
    StringBuffer localStringBuffer = new StringBuffer(65536);
    if (this.m_test != null)
    {
      String str1 = toString(this.m_test.getAudioDeltaArray());
      String str2 = toString(this.m_test.getAudioBytesArray());
      String str3 = toString(this.m_test.getAudioTimesArray());
      String str4 = toString(this.m_test.getVideoDeltaArray());
      String str5 = toString(this.m_test.getVideoBytesArray());
      String str6 = toString(this.m_test.getVideoTimesArray());
      if ((str1 != null) && (str2 != null) && (str3 != null))
      {
        localStringBuffer.append("&detail.adel=").append(str1);
        localStringBuffer.append("&detail.aby=").append(str2);
        localStringBuffer.append("&detail.atim=").append(str3);
      }
      if ((str4 != null) && (str5 != null) && (str6 != null))
      {
        localStringBuffer.append("&detail.vdel=").append(str4);
        localStringBuffer.append("&detail.vby=").append(str5);
        localStringBuffer.append("&detail.vtim=").append(str6);
      }
    }
    return localStringBuffer.toString();
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
 * Qualified Name:     mcsaplugins.video.video
 * JD-Core Version:    0.6.2
 */