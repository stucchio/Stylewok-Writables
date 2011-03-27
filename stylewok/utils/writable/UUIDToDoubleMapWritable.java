package stylewok.utils.writable;

import org.apache.hadoop.io.*;

import java.util.*;
import java.io.*;

public class UUIDToDoubleMapWritable extends HashMap<UUIDWritable,Double> implements Writable {
    public UUIDToDoubleMapWritable() { }

    //Implementation of WritableComparable
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(size());
        for (UUIDWritable k : keySet()) {
            k.write(out);
            out.writeDouble(get(k));
        }
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        clear();
        int size = in.readInt();
        for (int i=0;i<size;i++) {
            UUIDWritable k = UUIDWritable.read(in);
            double v = in.readDouble();
            put(k,v);
        }
    }

    public static UUIDToDoubleMapWritable read(DataInput in) throws IOException {
        UUIDToDoubleMapWritable result = new UUIDToDoubleMapWritable();
        result.readFields(in);
        return result;
    }
}


