
// Import the File class
import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.nio.Buffer;
import java.util.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Blob {
	String sha1Name="";
	public Blob(String fileName) throws IOException {
		File myFile = new File(".\\tests\\"+fileName);
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
	
	public static String compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        return out.toString("ISO-8859-1");
    }
	
	public static String decompress(String str) throws Exception {
	    if (str == null || str.length() == 0) {
	        return str;
	    }
	    //System.out.println("Input String length : " + str.length());
	    GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(str.getBytes("UTF-8")));
	    BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
	    String outStr = "";
	    String line;
	    while ((line=bf.readLine())!=null) {
	        outStr += line;
	    }
	    //System.out.println("Output String lenght : " + outStr.length());
	    return outStr;
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
