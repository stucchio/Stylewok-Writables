package stylewok.utils.writable;

import org.apache.hadoop.io.*;

public class TextArrayWritable extends ArrayWritable {
    public TextArrayWritable() { super(Text.class); }

    public Text[] getTextArray() {
	Writable[] data = get();
	Text[] result = new Text[data.length];
	for (int i=0;i<data.length;i++) {
	    result[i] = (Text)data[i];
	}
	return result;
    }
}
