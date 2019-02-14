package com.nobitastudio.oss.model.entity;

import com.nobitastudio.oss.model.enumeration.Area;

import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 10:14
 * @description 科室实例
 */
public class Department implements Serializable {

    private static final long serialVersionUID = -3712970229472760165L;

    public interface DepartmentSimpleView {};
    public interface DepartmentDetailView extends DepartmentSimpleView {};

    public Department() {
        this(1,"name","address",1,1, Area.A,"introduction");
    }

    public Department(Integer id, String name, String address, Integer location, Integer floor, Area area, String introduction) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.location = location;
        this.floor = floor;
        this.area = area;
        this.introduction = introduction;
    }

    private Integer id;

    private String name;

    private String address;

    private Integer location;

    private Integer floor;

    private Area area;

    private String introduction;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "科室名称";
    }
}
