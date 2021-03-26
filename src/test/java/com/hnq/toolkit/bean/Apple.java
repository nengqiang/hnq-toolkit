package com.hnq.toolkit.bean;

/**
 * @author henengqiang
 * @date 2021/03/26
 */
public class Apple {
    private String name;
    private Double weight;

    @Override
    public String toString() {
        return "Apple{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
