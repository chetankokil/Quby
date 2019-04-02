package eu.toon.models;

import java.util.Objects;

public class RangeAdvice {
    private String advice;
    private Range range;

    public RangeAdvice() {
    }

    public RangeAdvice(String advice, Range range) {
        this.advice = advice;
        this.range = range;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RangeAdvice that = (RangeAdvice) o;
        return Objects.equals(advice, that.advice) &&
                Objects.equals(range, that.range);
    }

    @Override
    public int hashCode() {
        return Objects.hash(advice, range);
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }
}
