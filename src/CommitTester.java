import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CommitTester {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// Make test files
		File f1 = new File("f1.txt");
		File f2 = new File("f2.txt");
		File f3 = new File("f3.txt");
		File f4 = new File("f4.txt");
		File f5 = new File("f5.txt");
		
		f1.createNewFile();
		f2.createNewFile();
		f3.createNewFile();	
		f4.createNewFile();
		f5.createNewFile();
		
		// Writes to the files
		try
		{
			Files.writeString(Paths.get("f1.txt"), "Yeji", StandardCharsets.ISO_8859_1);
		}
		catch (IOException io) {
			System.out.println("Tragically, trying to write to the file did not work.");
		}
		try
		{
			Files.writeString(Paths.get("f2.txt"), "Lia", StandardCharsets.ISO_8859_1);
		}
		catch (IOException io) {
			System.out.println("Tragically, trying to write to the file did not work.");
		}
		try
		{
			Files.writeString(Paths.get("f3.txt"), "Chaeryeong", StandardCharsets.ISO_8859_1);
		}
		catch (IOException io) {
			System.out.println("Tragically, trying to write to the file did not work.");
		}
		try
		{
			Files.writeString(Paths.get("f4.txt"), "Ryujin", StandardCharsets.ISO_8859_1);
		}
		catch (IOException io) {
			System.out.println("Tragically, trying to write to the file did not work.");
		}
		try
		{
			Files.writeString(Paths.get("f4.txt"), "Yuna", StandardCharsets.ISO_8859_1);
		}
		catch (IOException io) {
			System.out.println("Tragically, trying to write to the file did not work.");
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		File obj1 = new File("f1.txt");
		File obj2 = new File("f2.txt");
		File obj3 = new File("f3.txt");
		File obj4 = new File("f4.txt");
		File obj5 = new File("f5.txt");
		
		// Deleting the files
		obj1.delete();
		obj2.delete();
		obj3.delete();
		obj4.delete();
		obj5.delete();
	}

	@Test
	void test() throws Exception {
		// Creates index, checks if it exists
		Index ind = new Index();
		ind.init();
		
		File index = new File("index");
		assertTrue(index.exists());

		// First Commit
		ind.add("f1.txt");
		ind.add("f2.txt");
		
		Commit c1 = new Commit("Creating a new girl group!", "JYP Entertainment", null);
		System.out.println(c1.sha1PTreeAndSummary());
		System.out.println(c1.sha1PTreeAndSummary());
		c1.printCommitInfo();
		File cf1 = new File("./tests/objects/" + c1.sha1PTreeAndSummary());
		assertTrue(cf1.exists());
		
	}

}
