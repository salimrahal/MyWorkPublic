package mcsaplugins.route;

import java.awt.Rectangle;

public class NodeHitRegion
{
  public static final int MOUSE_OUTSIDE_BOUNDS = 0;
  public static final int MOUSE_OVER = 1;
  public NetworkNode node = null;
  public Rectangle rect = null;
  public int order;
  public int type = 1;
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.NodeHitRegion
 * JD-Core Version:    0.6.2
 */