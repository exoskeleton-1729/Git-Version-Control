
// Import the File class
import java.io.*;

import java.nio.Buffer;
import java.util.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Blob {
	String sha1Name="";
	public Blob(String fileName) throws IOException {
		File myFile = new File("./tests/"+fileName);
		//System.out.println(myFile.exists());
		BufferedReader reader = new BufferedReader(new FileReader(myFile));
		//System.out.println(myFile.getName());
		String content="";
		
		char nextChar = (char) reader.read();
		while ((int) nextChar != -1 && (int) nextChar != 65535) {
			//writer.print(nextChar);
			content+=nextChar;
			nextChar = (char) reader.read();
		}
		//System.out.println(content);
		
		//convert content to sha1
		sha1Name = encryptThisString(content);

		// creates new file in objects folder
		File newFile = new File("/Users/kensukeshimojo/eclipse-workspace/Prerequisites/tests/objects/" + sha1Name);
		newFile.getParentFile().mkdirs();
		newFile.createNewFile();
		
		//./ does relative file paths, but just owwrry about htat later, make them suffer >:)

		
		/**
		 * if (!newFile.exists()){ newFile.mkdirs(); }
		 */
		
		PrintWriter writer = new PrintWriter(newFile);
		writer.print(content);
		
		writer.close();
		reader.close();
	}
	public String getSha1Name() {
		return sha1Name;
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
