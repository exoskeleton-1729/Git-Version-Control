import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Commit {
	private String pTree;
	private String summary;
	private String date;
	private String author;
	
	private Commit parent;
	private Commit other;
	
	public Commit(String filename,String inputSummary,String inputAuthor,Commit theParent){
		pTree=filename;
		summary=inputSummary;
		//something with Date Object
		author=inputAuthor;
		parent=theParent;
	}
	
	

	public String sha1TreeContent() throws IOException {
		Path tree=Paths.get(pTree);
		String treeContent=Files.readString(tree);
		return encryptThisString(treeContent);
	}
	
	public String sha1PTreeAndSummary() throws IOException {
		return encryptThisString(pTree+"\n"+summary);
	}
	
	public String getDate() {
		return date;
	}
	
	public void printCommitInfo() throws IOException {
		File infoFile=new File("./objects/commit");
		infoFile.createNewFile();
		PrintWriter printer=new PrintWriter(infoFile);
		printer.println(pTree);
		
		printer.print(false);
		
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
