package stylewok.utils.writable;

import org.apache.hadoop.io.*;

import java.util.*;
import java.io.*;

public class UUIDPairWritable implements WritableComparable<UUIDPairWritable> {
    private UUIDWritable valueLeft;
    private UUIDWritable valueRight;

    public UUIDPairWritable(UUIDWritable left, UUIDWritable right) {
        if (left.compareTo(right) > 0) {
            valueLeft = left;
            valueRight = right;
        } else {
            valueLeft = right;
            valueRight = left;
	}
    }

    public UUIDPairWritable(){ }

    public UUIDWritable getLeft() {
        return valueLeft;
    }

    public UUIDWritable getRight() {
        return valueRight;
    }

    public String toString() {
        return valueLeft.toString() + ":" + valueRight.toString();
    }

    //Implementation of WritableComparable
    @Override
    public void write(DataOutput out) throws IOException {
        valueLeft.write(out);
        valueRight.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        valueLeft = UUIDWritable.read(in);
        valueRight = UUIDWritable.read(in);
    }

    public static UUIDPairWritable read(DataInput in) throws IOException {
        UUIDPairWritable result = new UUIDPairWritable();
        result.readFields(in);
        return result;
    }

    @Override
    public int compareTo(UUIDPairWritable other) {
        int leftComparison = valueLeft.compareTo(other.valueLeft);
        if (leftComparison != 0) {
            return leftComparison;
        }
        return valueRight.compareTo(other.valueRight);
    }
}