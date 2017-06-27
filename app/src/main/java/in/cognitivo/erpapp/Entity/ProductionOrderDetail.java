package in.cognitivo.erpapp.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by cognitivo on 20/05/17.
 */

public class ProductionOrderDetail implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id_production_order")
    @Expose
    private String id_production_order;

    @SerializedName("id_project_task")
    @Expose
    private String id_project_task;

    @SerializedName("quantity")
    @Expose
    private String quantity;

    @SerializedName("quantity_real")
    @Expose
    private String quantity_real;

    public ProductionOrderDetail(String id, String name, String id_production_order, String id_project_task, String quantity, String quantity_real) {
        this.id = id;
        this.name = name;
        this.id_production_order = id_production_order;
        this.id_project_task = id_project_task;
        this.quantity = quantity;
        this.quantity_real = quantity_real;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_production_order() {
        return id_production_order;
    }

    public void setId_production_order(String id_production_order) {
        this.id_production_order = id_production_order;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity_real() {
        return quantity_real;
    }

    public void setQuantity_real(String quantity_real) {
        this.quantity_real = quantity_real;
    }

    public String getId_project_task() {
        return id_project_task;
    }

    public void setId_project_task(String id_project_task) {
        this.id_project_task = id_project_task;
    }
}
