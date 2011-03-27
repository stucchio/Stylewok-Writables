package stylewok.utils.writable;

import org.apache.hadoop.io.*;

import java.util.*;
import java.io.*;

public class UUIDWritable implements WritableComparable<UUIDWritable> {
    private UUID value;

    public UUIDWritable(long mostSignificantBits, long leastSignificantBits) {
	value = new UUID(mostSignificantBits, leastSignificantBits);
    }

    public UUIDWritable(String stringRep) {
	value = UUID.fromString(stringRep);
    }

    public UUIDWritable(Text textRep) {
	value = UUID.fromString(textRep.toString());
    }

    public UUIDWritable() {
	value = UUID.randomUUID();
    }

    public String toString() {
	return value.toString();
    }

    public Text toText() {
	return new Text(toString());
    }

    public boolean equals(Object obj) {
	UUIDWritable other = (UUIDWritable)obj;
	return value.equals(other.value);
    }

    public int hashCode() {
	return value.hashCode();
    }

    //Implementation of WritableComparable
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(value.getMostSignificantBits());
        out.writeLong(value.getLeastSignificantBits());
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	long mostSignificantBits = in.readLong();
	long leastSignificantBits = in.readLong();
	value = new UUID(mostSignificantBits, leastSignificantBits);
    }

    public static UUIDWritable read(DataInput in) throws IOException {
	UUIDWritable result = new UUIDWritable();
	result.readFields(in);
	return result;
    }

    @Override
    public int compareTo(UUIDWritable w) {
	return value.compareTo(w.value);
    }

}
