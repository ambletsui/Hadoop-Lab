import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

/*
 * 1. No Reducer needed.
 * 2. XMLInputFormat class is the same as the one in XMLDriver.java
 */

public class MapperForXML extends Mapper<LongWritable, Text, NullWritable, Text> {

	@Override
	public void map(LongWritable key, Text values, Context context) throws IOException, InterruptedException {

  		String xmlString = values.toString();
  
  		SAXBuilder builder = new SAXBuilder();
  		Reader in = new StringReader(xmlString);
  		String value="";
  		try {
  
  			Document doc = builder.build(in);
  			Element root = doc.getRootElement();
  
  			String tag1 =root.getChild("tag").getChild("tag1").getTextTrim() ;
  
  			String tag2 =root.getChild("tag").getChild("tag1").getChild("tag2").getTextTrim();
  			value= tag1+ ","+tag2;
  			
  			context.write(NullWritable.get(), new Text(value));
			
		} catch (JDOMException ex) {
			  Logger.getLogger(MapperForXML.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			  Logger.getLogger(MapperForXML.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
