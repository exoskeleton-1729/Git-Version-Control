import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Commit {
	private Tree tree;
	private String summary;
	private String date;
	private String author;
	private String pTree;
	private Commit parent;
	public Commit child;
	public static HashMap <String, String> trees = new HashMap<String, String>();
	
	public Commit(String inputSummary, String inputAuthor, Commit theParent) throws Exception {
		
		Index.clearToAdd();
		
		// Setting parent and child
		parent = theParent;
		if(theParent != null)
			parent.setChild(this);
		child = null;
		
		// Sets variables according to inputs
		summary = inputSummary;
		author = inputAuthor;
		Date dateObj = new Date();
		date = dateObj.toString();
		boolean wasModified = false;
		
		// Read the index file
		BufferedReader reader = new BufferedReader(new FileReader("./tests/index"));
		ArrayList <String> contents = new ArrayList<String>();
		ArrayList <String> modFileNames = new ArrayList<String>();
		String theOne = "";
		
		if(parent != null)
		{
			for(String str : trees.keySet())
			{
				if(trees.get(str) == null)
				{
					trees.put(str, parent.tree.fileName);
				}
			}
		}
		
		String read;
		while (reader.ready())
		{
			read = reader.readLine();
			if(read.contains(":") && !read.contains("*edited*") && !read.contains("*deleted*"))
			{
				String fileName = read.substring(0, read.indexOf(':') - 1);
				String SHA1 = read.substring(read.indexOf(':') + 2);
				contents.add("blob : " + SHA1 + " " + fileName);
			}
			else
			{
				wasModified = true;
				int index = read.indexOf('t');
				String str = read.substring(index + 5);
				modFileNames.add(str);
			}
		}
		
		if(parent != null)
		{
			contents.add("tree : " + parent.tree.fileName);
			pTree = parent.sha1TreeContent();
			parent.setChild(this);
			parent.printCommitInfo();
		}
//		else if(parent != null && wasModified)
//		{
//			for(String str : modFileNames)
//			{
//				theOne = str;
//			}
//			String correctTreeName = trees.get(theOne);
//			contents.add("tree : " + correctTreeName);
//			pTree = parent.sha1TreeContent();
//			parent.setChild(this);
//			parent.printCommitInfo();
//		}
		
		// Makes the tree
		reader.close();
		tree = new Tree(contents);
		
		
		// Clears index file
		File file = new File("./tests/index");
		PrintWriter pw = new PrintWriter(file);
		pw.write("");
		pw.close();

		PrintWriter hw = new PrintWriter("./tests/HEAD");
		hw.write(this.sha1PTreeAndSummary());
		hw.close();
	}
	
	
	public String sha1TreeContent() throws IOException {
		Path treePath = Paths.get(tree.fileName);
		String treeContent = Files.readString(treePath);
		return encryptThisString(treeContent);
	}
	
	public String sha1PTreeAndSummary() throws IOException {
		return encryptThisString(summary+date+author+parent);
	}
	
	public String getTree()
	{
		return pTree;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setChild(Commit c) {
		child = c;
	}
	
	public void printCommitInfo() throws IOException {
		File infoFile = new File("tests/objects/"+sha1PTreeAndSummary());
		infoFile.createNewFile();
		PrintWriter printer=new PrintWriter(infoFile);
		printer.println("objects/" + tree.fileName);
		
		if(this.parent==null) {
			printer.println();
		}else {
			printer.println("objects/" + this.toString());
		}
		
		if(this.child==null) {
			printer.println();
		}else {
			printer.println("objects/" + this.child);
		}
		printer.println(author);
		
		printer.println(date);
		
		printer.println(summary);
		
		printer.close();
	}
	
	public String toString()
	{
		return pTree;
	}
	
	
	private static String encryptThisString(String input)// from geeksforgeeks
	{
		try {
			// getInstance() method is called with algorithm SHA-1
			MessageDigest md = MessageDigest.getInstance("SHA-1");

			// digest() method is called
			// to calculate message digest of the input string
			// returned as array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);

			// Add preceding 0s to make it 32 bit
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			// return the HashText
			return hashtext;
		}
		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
}
