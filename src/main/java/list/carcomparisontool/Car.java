/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package list.carcomparisontool;

import com.google.gson.annotations.SerializedName;

/**
 *The car class is designed to hold to objects relevent 
 * for a user who wishes to see the attributes of
 * a given car
 * @author nicholasshah
 */
public class Car {
    @SerializedName("makeName")
    private String makeName;
    @SerializedName("modelName")
    private String modelName;
    @SerializedName("bodyStyle")
    private String bodyStyle;
    @SerializedName("doors")
    private int doors;
    @SerializedName("passengerCapacity")
    private int passengerCapacity;
    @SerializedName("mpg")
    private int mpg;
    @SerializedName("cost")
    private double cost;

    @Override
    public String toString() {
        return "Car{" + "makeName=" + makeName + ", modelName=" + modelName + ", bodyStyle=" + bodyStyle + ", doors=" + doors + ", passengerCapacity=" + passengerCapacity + ", mpg=" + mpg + ", cost=" + cost + '}';
    }

   

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getBodyStyle() {
        return bodyStyle;
    }

    public void setBodyStyle(String bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public int getMpg() {
        return mpg;
    }

    public void setMpg(int mpg) {
        this.mpg = mpg;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    
}
