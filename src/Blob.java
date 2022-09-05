
// Import the File class
import java.io.*;

import java.nio.Buffer;
import java.util.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Blob {
	public Blob(String fileName) throws IOException {
		File myFile = new File(fileName);
		System.out.println(myFile.exists());
		BufferedReader reader = new BufferedReader(new FileReader(myFile));
		System.out.println(myFile.getName());
		//String content = null;
		
		
		
		String sha1Name = encryptThisString(myFile.getName());

		// creates new file in objects folder
		File newFile = new File("/Users/kensukeshimojo/eclipse-workspace/Prerequisites/tests/objects/" + sha1Name);
		newFile.getParentFile().mkdirs();
		newFile.createNewFile();
		
		
		
		
		
		/**
		 * if (!newFile.exists()){ newFile.mkdirs(); }
		 */
		PrintWriter writer = new PrintWriter(newFile);
char nextChar = (char) reader.read();
		while ((int) nextChar != -1 && (int) nextChar != 65535) {
			writer.print(nextChar);
			nextChar = (char) reader.read();
		}
		
		writer.close();
		reader.close();
	}
	
	
	// from http://www.java2s.com/example/java/security/get-sha1-hash-for-file.html
	public static String getSHA1(File file) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            FileInputStream fis = new FileInputStream(file);
            byte[] dataBytes = new byte[1024];

            int nread = 0;

            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
            ;

            byte[] mdbytes = md.digest();

            //convert the byte to hex format
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

	
	
	public static String encryptThisString(String input)// from geeksforgeeks
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
