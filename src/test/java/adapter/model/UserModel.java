package adapter.model;

import org.asyou.sequoia.type.DecimalObject;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by steven on 17/7/14.
 */
public class UserModel {
    private ObjectId _id;
    private Long id;
    private String name;
    private Boolean state;
    private BigDecimal money;
    private DecimalObject decimal;
    private ArrayList arr;
    private Map map;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public ArrayList getArr() {
        return arr;
    }

    public void setArr(ArrayList arr) {
        this.arr = arr;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public DecimalObject getDecimal() {
        return decimal;
    }

    public void setDecimal(DecimalObject decimal) {
        this.decimal = decimal;
    }
}
