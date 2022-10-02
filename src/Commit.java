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

public class Commit {
	private Tree tree;
	private String summary;
	private String date;
	private String author;
	
	private Commit parent;
	private Commit child;
	
	public Commit(String inputSummary, String inputAuthor, Commit theParent) throws Exception {
		// Sets variables according to inputs
		summary = inputSummary;
		Date dateObj = new Date();
		date = dateObj.toString();
		author = inputAuthor;
		parent = theParent;
		
		// If there is a parent already, makes this commit the child.
		if (parent != null)
			parent.child = this;
		
		// Read the index file
		BufferedReader reader = new BufferedReader(new FileReader("./tests/index"));
		ArrayList <String> contents = new ArrayList<String>();
		String read;
		while (reader.ready())
		{
			// Note to self will need to change this once on Honors Track Step 1
			read = reader.readLine();
			String fileName = read.substring(0, read.indexOf(':') - 1);
			String SHA1 = read.substring(read.indexOf(':') + 2);
			contents.add("blob : " + SHA1 + " " + fileName);
			
		}
		
		if(parent != null)
		{
			contents.add("tree : " + parent.tree.fileName);
		}
		
		reader.close();
		tree = new Tree(contents);
	}
	
	
	public String sha1TreeContent() throws IOException {
		Path treePath = Paths.get(tree.fileName);
		String treeContent = Files.readString(treePath);
		return encryptThisString(treeContent);
	}
	
	public String sha1PTreeAndSummary() throws IOException {
		return encryptThisString(summary+date+author+parent);
	}
	
	public Tree getTree()
	{
		return tree;
	}
	
	public String getDate() {
		return date;
	}
	
	public void printCommitInfo() throws IOException {
		File infoFile = new File("tests/objects/"+sha1PTreeAndSummary());
		infoFile.createNewFile();
		PrintWriter printer=new PrintWriter(infoFile);
		printer.println(tree.fileName);
		
		if(this.parent==null) {
			printer.println();
		}else {
			printer.println(this.parent);
		}
		
		if(this.child==null) {
			printer.println();
		}else {
			printer.println(this.child);
		}
		printer.println(author);
		
		printer.println(date);
		
		printer.println(summary);
		
		printer.close();
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
