package myProject_LSP;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Integer restaurantId;
    private Integer restaurantMenuId;
    private Integer customerId;
    private Integer qty;
    private Long modifiedDate;
    private String status;


    //추가부분
    @PrePersist
    public void onPrePersist(){
        System.out.println("Pre!!!!!!!!!!!!!!!!11");
        //추가부분

        if(!"QTY_COOK_CANCELLED".equals(this.getStatus())){
            System.out.println("xxxxxxxxxxxxxxxx30");
            this.setStatus("ORDERED");
        }
        this.setModifiedDate(System.currentTimeMillis());
    }

    @PostPersist
    public void onPostPersist(){

        if(!"QTY_COOK_CANCELLED".equals(this.getStatus())){
            System.out.println("xxxxxxxxxxxxxxxxx40");
            Ordered ordered = new Ordered();
            BeanUtils.copyProperties(this, ordered);
            System.out.println("26!!!!!!!!!!!!!!11");
            ordered.publishAfterCommit();
        }
    }

    @PreRemove
    public void onPreRemove(){
        OrderCancelled orderCancelled = new OrderCancelled();
        BeanUtils.copyProperties(this, orderCancelled);
        orderCancelled.setStatus("ORDER_CANCELED");
        orderCancelled.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        myProject_LSP.external.Cancellation cancellation = new myProject_LSP.external.Cancellation();
        // mappings goes here

        //추가부분
        cancellation.setOrderId(this.getId());
        cancellation.setCustomerId(this.getCustomerId());
        cancellation.setStatus("ORDER_CANCELED");

        OrderApplication.applicationContext.getBean(myProject_LSP.external.CancellationService.class)
                .cancel(cancellation);


    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
    public Integer getRestaurantMenuId() {
        return restaurantMenuId;
    }

    public void setRestaurantMenuId(Integer restaurantMenuId) {
        this.restaurantMenuId = restaurantMenuId;
    }
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public Long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
