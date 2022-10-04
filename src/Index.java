import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


public class Index {
	
	private ArrayList<String> deleted = new ArrayList<String>();
	private HashMap<String,String> indeces = new HashMap<String,String>();
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
			if(entry.getValue() != null)
				printer.print(entry.getKey() + " : " + entry.getValue()+"\n");
			else if(deleted.contains(entry.getKey()))
				printer.print("*deleted* " + entry.getKey());
			else
				printer.print("*edited* " + entry.getKey());
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
			if(entry.getValue() != null)
				printer.print(entry.getKey() + " : " + entry.getValue()+"\n");
			else if(deleted.contains(entry.getKey()))
				printer.print("*deleted* " + entry.getKey());
			else
				printer.print("*edited* " + entry.getKey());
		}
		printer.close();
		
	}
	
	public void delete(String fileName) throws IOException
	{
		remove(fileName);
		// Scans the whole index file
		Scanner scan = new Scanner(index);
		String fileContents = "";
		
		while(scan.hasNextLine())
			fileContents += scan.nextLine() + "\n";
		scan.close();
		
		// Appends the *delete* to the end
		FileWriter fw = new FileWriter(index);
		fw.append(fileContents + "*deleted* " + fileName);
		fw.close();
		
		// Actually deletes the file and keeps track of its name
		File file = new File(fileName);
		file.delete();
		deleted.add(fileName);
		
		// Updates HashMap with "null", which can be later identified in Commit as a sign that the file has been deleted
		indeces.put(fileName, null);
	}
	
	public void edit(String fileName) throws IOException
	{
		remove(fileName);
		// Scans the whole index file
		Scanner scan = new Scanner(index);
		String fileContents = "";
		
		while(scan.hasNextLine())
			fileContents += scan.nextLine() + "\n";
		scan.close();
		
		// Appends the *edit* to the end
		FileWriter fw = new FileWriter(index);
		fw.append(fileContents + "*edited* " + fileName);
		fw.close();
		
		// Updates HashMap with "null", which can be later identified in Commit as a sign that the file has been deleted
		indeces.put(fileName, null);
	}
	
}
