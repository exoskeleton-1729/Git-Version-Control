
// Import the File class
import java.io.*;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
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
		while ((int) nextChar != -1 && (int) nextChar != 65535) {//store content in content string
			//writer.print(nextChar);
			content+=nextChar;
			nextChar = (char) reader.read();
		}
		//System.out.println(content);
		String zippedContent=compress(content);
		//System.out.println(zippedContent);
		
		//convert content to sha1
		sha1Name = encryptThisString(zippedContent);

		// creates new file in objects folder
		File newFile = new File("./tests/objects/" + sha1Name);
		newFile.getParentFile().mkdirs();
		newFile.createNewFile();
		
		//./ does relative file paths, but just owwrry about htat later, make them suffer >:)

		
		/**
		 * if (!newFile.exists()){ newFile.mkdirs(); }
		 */
		
		PrintWriter writer = new PrintWriter(newFile);
		writer.print(zippedContent);
		
		writer.close();
		reader.close();
	}
	
	public String getSha1Name() {
		return sha1Name;
	}
	
	public String compress(String str) throws UnsupportedEncodingException {
	// deflater object is created
	        Deflater def = new Deflater(); 
	        // get the string to be compressed 
	        String finalStr = ""; 
	        // This loop will create a final strig to be compressed by                          
	        for (int i = 0; i < 3; i++) {
            finalStr += str; 
		}	
		        // set the input for deflator by converting it into bytes 
		        def.setInput(finalStr.getBytes("UTF-8")); 
	        // finish.The finished() function in the Inflater class returns true when it reaches the end of compression data stream.
	       def.finish(); 
		        // output string data in bytes 
	       	byte compString[] = new byte[1024]; 
		        // compressed string data will be stored in compString, offset is set to 3 and maximum size of compressed string is 13. 
	        int compSize = def.deflate(compString, 3, 13, Deflater.FULL_FLUSH); 
		        // Final compressed String 
	       //System.out.println("Compressed String :" + new String(compString) + "\n Size :" + compSize); 
  
        // original String is printed for reference 
		        //System.out.println("Original String :" + finalStr + "\n Size :" + finalStr.length()); 
      // object end 
	        def.end();
			return new String(compString); 
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
