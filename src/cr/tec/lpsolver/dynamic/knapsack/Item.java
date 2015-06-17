package cr.tec.lpsolver.dynamic.knapsack;

import java.util.Objects;

public class Item {

    private String name;
    private double weight;
    private double value;
    private int bounding;
    private int inKnapsack;

    public Item() {
        setName("");
        setWeight(0);
        setValue(0);
        setBounding(1);
    }

    public Item(Item item) {
        this();
        setName(item.name);
        setWeight(item.weight);
        setValue(item.value);
        setBounding(item.bounding);
    }

    public Item(double _weight, double _value) {
        this();
        setWeight(_weight);
        setValue(_value);
    }

    public Item(double _weight, double _value, int _bounding) {
        this();
        setWeight(_weight);
        setValue(_value);
        setBounding(_bounding);
    }

    public Item(String _name, double _weight, double _value) {
        this();
        setName(_name);
        setWeight(_weight);
        setValue(_value);
    }

    public Item(String _name, double _weight, double _value, int _bounding) {
        this();
        setName(_name);
        setWeight(_weight);
        setValue(_value);
        setBounding(_bounding);
    }

    public void setName(String _name) {
        name = _name;
    }

    public void setWeight(double _weight) {
        weight = Math.max(_weight, 0);
    }

    public void setValue(double _value) {
        value = Math.max(_value, 0);
    }

    public void setInKnapsack(int _inKnapsack) {
        inKnapsack = Math.min(getBounding(), Math.max(_inKnapsack, 0));
    }

    public void setBounding(int _bounding) {
        bounding = Math.max(_bounding, 0);
        if (bounding == 0) {
            inKnapsack = 0;
        }
    }

    public void checkMembers() {
        setWeight(weight);
        setValue(value);
        setBounding(bounding);
        setInKnapsack(inKnapsack);
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getValue() {
        return value;
    }

    public int getInKnapsack() {
        return inKnapsack;
    }

    public int getBounding() {
        return bounding;
    }

    @Override
    public String toString() {
        return name + " amount=" + inKnapsack + " weight=" + weight + " value=" + value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.weight) ^ (Double.doubleToLongBits(this.weight) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.value) ^ (Double.doubleToLongBits(this.value) >>> 32));
        hash = 71 * hash + this.bounding;
        hash = 71 * hash + this.inKnapsack;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.weight != other.weight) {
            return false;
        }
        if (this.value != other.value) {
            return false;
        }
        if (this.bounding != other.bounding) {
            return false;
        }
        return this.inKnapsack == other.inKnapsack;
    }
    
    
}
