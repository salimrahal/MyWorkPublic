
/*
 http://javaknowledge.info/add-edit-delete-row-to-datatable-on-the-fly-using-primefaces/
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "item")
@SessionScoped
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    private String item;
    private Integer qty;
    private Double price;
    OrderBean order;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }
    private static final ArrayList<OrderBean> orderList = new ArrayList<OrderBean>();

    public ArrayList<OrderBean> getOrderList() {
        return orderList;
    }

    public Item prepareCreate(){
      System.out.println("prepareCreate..");
      Item selected = new Item();
        return selected;
    
    }
    /*
    Unused, I use the ajax open dialog
    */
    public void newItem() {
        System.out.println("newItem1");
        try {
            RequestContext.getCurrentInstance().openDialog("newItem");
        } catch (Exception e) {
            System.out.println("ex" + e.getLocalizedMessage());
        }
    }

    public void handleReturn(SelectEvent event) {
        Item itemObj = (Item) event.getObject();
        System.out.println("handleReturn..item=" + this);
        System.out.println("handleReturn..itemObj=" + itemObj.toString());
        OrderBean orderitem = new OrderBean(this.item, this.qty, this.price);
        orderList.add(orderitem);
        System.out.println("addAction..orderList=" + orderList.size());
        item = "";
        qty = 0;
        price = 0.0;
    }

    /*
    used
    */
    public String addAction() {
        System.out.println("addAction..item=" + this);
        OrderBean orderitem = new OrderBean(this.item, this.qty, this.price);
        orderList.add(orderitem);
        System.out.println("addAction..orderList=" + orderList.size());
        item = "";
        qty = 0;
        price = 0.0;
        //close dialog
        //RequestContext.getCurrentInstance().closeDialog("createDialog");
        return null;
    }

    public void onEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Item Edited", ((OrderBean) event.getObject()).getItem());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Item Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        orderList.remove((OrderBean) event.getObject());
    }

    @Override
    public String toString() {
        return "Item{" + "item=" + item + ", qty=" + qty + ", price=" + price + ", order=" + order + '}';
    }

}
