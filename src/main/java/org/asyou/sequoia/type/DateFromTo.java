package org.asyou.sequoia.type;

import org.asyou.sequoia.common.Assertions;

import java.time.LocalDate;

/**
 * Created by steven on 17/9/22.
 */
public class DateFromTo {

    private String fieldName;
    private LocalDate from;
    private LocalDate to;

    public DateFromTo(String fieldName){
        Assertions.notNull("DateFromTo fieldName ", fieldName);
        this.fieldName = fieldName;
        from = LocalDate.now();
        to = LocalDate.now();
    }

    public DateFromTo(String fieldName, LocalDate fromDate, LocalDate toDate){
        this.fieldName = fieldName;
        from = fromDate;
        to = toDate;
    }

    public String toJson(){
        if (!fieldName.isEmpty()){
            return String.format("{%s:{$gt:%s,$lt:%s}}", fieldName, from, to);
        }else{
            return "";
        }
    }

    public DateFromTo field(String name){
        this.fieldName = name;
        return this;
    }

    public DateFromTo from(LocalDate from){
        this.from = from;
        return this;
    }

    public DateFromTo to(LocalDate to){
        this.to = to;
        return this;
    }

    public static DateFromTo fromField(String name){
        return new DateFromTo(name);
    }

    public String getFieldName(){
        return fieldName;
    }

    public void setFieldName(String value){
        this.fieldName = value;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}
