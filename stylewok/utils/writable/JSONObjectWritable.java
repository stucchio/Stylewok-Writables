package stylewok.utils.writable;

import org.apache.hadoop.io.*;
import stylewok.utils.writable.simplejson.*;
import stylewok.utils.writable.simplejson.parser.*;

import java.io.*;

public class JSONObjectWritable extends JSONObject implements Writable {
    private static ThreadLocal JSONParser = new ThreadLocal() {
	    protected synchronized Object initialValue() {
		return new JSONParser() ;
	    }
	};

    @Override
    public void write(DataOutput out) throws IOException {
	StringWriter stringWriter = new StringWriter();
	JSONObject.writeJSONString(this, stringWriter);
	out.writeBytes(stringWriter.toString());
	out.writeByte(0);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	StringWriter writer = new StringWriter();
	try {
	    byte b = in.readByte();
	    while (true && (b != 0)) {
		writer.append((char)b);
		b = in.readByte();
	    }
	} catch (EOFException e) {
	}
	clear();
	String toParse = writer.toString();
	try {
	    JSONObject obj = (JSONObject) ((JSONParser)JSONParser.get()).parse(toParse);
	    for (Object k : obj.keySet()) {
		put(k, obj.get(k));
	    }
	} catch (ParseException e) {
	    throw new IOException("JSON parsing error: " + e.toString() + "\nString being parsed: " + toParse);
	}
    }
}
