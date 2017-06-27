package in.cognitivo.erpapp.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by cognitivo on 19/05/17.
 */

public class ProductionOrder implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id_production_line")
    @Expose
    private String id_production_line;



    public ProductionOrder(String id, String name, String id_production_line) {
        this.id = id;
        this.name = name;
        this.id_production_line = id_production_line;
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

    public String getId_production_line() {
        return id_production_line;
    }

    public void setId_production_line(String id_production_line) {
        this.id_production_line = id_production_line;
    }
}
