import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


public class Index {
	
	private HashMap<String,String> indeces=new HashMap<String,String>();
	private File index;
	
	public Index() {
		
	}
	
	public void init() throws IOException {
		File objects=new File("./tests/objects");
		objects.mkdir();
		index = new File("./tests/index");
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
	
	public void remove(String fileName) throws FileNotFoundException {
		//remove Blob file
		File removingThing = new File("./tests/objects/"+indeces.get(fileName));
		System.out.println(removingThing.exists());
		removingThing.delete();
		
		indeces.remove(fileName);
		PrintWriter printer=new PrintWriter(new File("./tests/index"));
		for (Map.Entry<String, String> entry : indeces.entrySet()) {
			printer.print(entry.getKey() + " : " + entry.getValue()+"\n");
		}
		printer.close();
		
	}
	
	public void delete(String fileName) throws IOException
	{
		FileWriter fw = new FileWriter(index);
		fw.append("*deleted* " + fileName);
		fw.close();
	}
	
}
