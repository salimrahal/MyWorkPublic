package mcsaplugins.route;

import java.awt.Point;

public class NetworkNodeCouplet
{
  private NetworkNode m_n1 = null;
  private NetworkNode m_n2 = null;
  private Point m_ptN1 = new Point(0, 0);
  private Point m_ptN2 = new Point(1, 0);
  boolean m_bUnifyPositions = false;
  private int m_layer = 0;

  public NetworkNodeCouplet(NetworkNode paramNetworkNode1, NetworkNode paramNetworkNode2)
  {
    this.m_n1 = paramNetworkNode1;
    this.m_n2 = paramNetworkNode2;
  }

  public boolean isUnify()
  {
    return this.m_bUnifyPositions;
  }

  public void unifyPositions(boolean paramBoolean)
  {
  }

  public Point getLeftmostNodePosition()
  {
    return this.m_ptN1.x < this.m_ptN2.x ? this.m_ptN1 : this.m_ptN2;
  }

  public NetworkNode getLeftmostNode()
  {
    return this.m_ptN1.x < this.m_ptN2.x ? this.m_n1 : this.m_n2;
  }

  public NetworkNode getRightmostNode()
  {
    return this.m_ptN1.x < this.m_ptN2.x ? this.m_n2 : this.m_n1;
  }

  public Point getRightmostNodePosition()
  {
    return this.m_ptN1.x < this.m_ptN2.x ? this.m_ptN2 : this.m_ptN1;
  }

  public void setLayer(int paramInt)
  {
    this.m_layer = paramInt;
    refreshLayer();
  }

  public int getLayer()
  {
    return this.m_layer;
  }

  public NetworkNode[] getNodes()
  {
    return new NetworkNode[] { getLeftmostNode(), getRightmostNode() };
  }

  public void reverse()
  {
    Point localPoint = this.m_ptN1;
    this.m_ptN1.setLocation(this.m_ptN2);
    this.m_ptN2.setLocation(localPoint);
  }

  private void refreshLayer()
  {
    this.m_ptN1.setLocation(this.m_ptN1.x, this.m_layer);
    this.m_ptN2.setLocation(this.m_ptN2.x, this.m_layer);
  }

  public Point getNodePosition(NetworkNode paramNetworkNode)
  {
    if (paramNetworkNode.equals(this.m_n1))
      return this.m_ptN1;
    return this.m_ptN2;
  }

  public void moveNode(NetworkNode paramNetworkNode, int paramInt)
  {
    setPosition(paramNetworkNode, getNodePosition(paramNetworkNode).x + paramInt);
  }

  public void setPosition(NetworkNode paramNetworkNode, int paramInt)
  {
    setPosition(paramNetworkNode, paramInt, this.m_layer);
  }

  private void setPosition(NetworkNode paramNetworkNode, int paramInt1, int paramInt2)
  {
    if (this.m_bUnifyPositions)
    {
      this.m_ptN1.setLocation(paramInt1, paramInt2);
      this.m_ptN2.setLocation(paramInt1, paramInt2);
    }
    else if (paramNetworkNode.equals(this.m_n1))
    {
      this.m_ptN1.setLocation(paramInt1, paramInt2);
    }
    else
    {
      this.m_ptN2.setLocation(paramInt1, paramInt2);
    }
  }

  public boolean alignNode(NetworkNodeCouplet paramNetworkNodeCouplet, NetworkNode paramNetworkNode)
  {
    if (paramNetworkNode.equals(getLeftmostNode()))
    {
      int i = paramNetworkNodeCouplet.getNodePosition(paramNetworkNode).x;
      NetworkNode localNetworkNode1 = getLeftmostNode();
      NetworkNode localNetworkNode2 = getRightmostNode();
      if (localNetworkNode1.equals(localNetworkNode2))
      {
        setPosition(localNetworkNode1, i);
        setPosition(localNetworkNode2, i);
      }
      else if (!this.m_bUnifyPositions)
      {
        setPosition(localNetworkNode1, i);
        setPosition(localNetworkNode2, i + 1);
      }
      else
      {
        setPosition(localNetworkNode1, i);
      }
    }
    else if (paramNetworkNode.equals(getRightmostNode()))
    {
      setPosition(getRightmostNode(), paramNetworkNodeCouplet.getNodePosition(paramNetworkNode).x);
    }
    else
    {
      return false;
    }
    return true;
  }

  public void replace(NetworkNode paramNetworkNode1, NetworkNode paramNetworkNode2)
  {
    if (this.m_n1 == paramNetworkNode1)
      this.m_n1 = paramNetworkNode2;
    if (this.m_n2 == paramNetworkNode1)
      this.m_n2 = paramNetworkNode2;
  }

  public boolean contains(NetworkNode paramNetworkNode)
  {
    return (this.m_n1.equals(paramNetworkNode)) || (this.m_n2.equals(paramNetworkNode));
  }

  public boolean equals(NetworkNodeCouplet paramNetworkNodeCouplet)
  {
    return (contains(paramNetworkNodeCouplet.getLeftmostNode())) && (contains(paramNetworkNodeCouplet.getRightmostNode()));
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.NetworkNodeCouplet
 * JD-Core Version:    0.6.2
 */