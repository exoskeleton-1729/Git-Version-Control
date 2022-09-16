import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Tree {
	public Tree(ArrayList<String> input) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		for (String s : input) {
			sb.append(s + "\n");
		}
		
		File tree = new File("./tests/objects/" + hash(sb.toString()));
		tree.createNewFile();
		PrintWriter pw = new PrintWriter(tree);
		pw.write(sb.toString());
		pw.close();
	}
	
	public String hash (String input) { // copy pasted from blob
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
