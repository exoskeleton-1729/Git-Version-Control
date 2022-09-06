import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Index {
	HashMap<String,String> indeces=new HashMap<String,String>();
	
	public Index() {
		
	}
	
	public void init() throws IOException {
		File objects=new File("/Users/kensukeshimojo/eclipse-workspace/Prerequisites/tests/objects");
		objects.mkdir();
		File index=new File("/Users/kensukeshimojo/eclipse-workspace/Prerequisites/tests/index");
		index.getParentFile().mkdirs();
		index.createNewFile();
	}
	public void add(String fileName) throws IOException {
		Blob blobby=new Blob(fileName);
		indeces.put(fileName, blobby.getSha1Name());
		PrintWriter printer=new PrintWriter(new File("./tests/index"));
		for (Map.Entry<String, String> entry : indeces.entrySet()) {
			printer.print(entry.getKey() + " : " + entry.getValue()+"\n");
		}
		printer.close();
		
	}
	
}
