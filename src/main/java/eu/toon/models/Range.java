package eu.toon.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Range {

    @Id
    @GeneratedValue
    int id;

    double min;
    double max;

    public Range(int id, double min, double max) {
        this.id = id;
        this.min = min;
        this.max = max;
    }

    public Range(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range range = (Range) o;
        return id == range.id &&
                min == range.min &&
                max == range.max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, min, max);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
