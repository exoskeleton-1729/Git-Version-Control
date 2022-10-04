import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class HonorsTester {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// Make test files
		File f1 = new File("f1.txt");
		File f2 = new File("f2.txt");
		File f3 = new File("f3.txt");
		File f4 = new File("f4.txt");
		File f5 = new File("f5.txt");
		File f6 = new File("f6.txt");
		File f7 = new File("f7.txt");
		File f8 = new File("f8.txt");
		File f9 = new File("f9.txt");
		
		
		f1.createNewFile();
		f2.createNewFile();
		f3.createNewFile();	
		f4.createNewFile();
		f5.createNewFile();
		f6.createNewFile();
		f7.createNewFile();
		f8.createNewFile();
		f9.createNewFile();
		
		// Writes to the files
		try
		{
			Files.writeString(Paths.get("f1.txt"), "Jihyo", StandardCharsets.ISO_8859_1);
		}
		catch (IOException io) {
			System.out.println("Tragically, trying to write to the file did not work.");
		}
		try
		{
			Files.writeString(Paths.get("f2.txt"), "Nayeon", StandardCharsets.ISO_8859_1);
		}
		catch (IOException io) {
			System.out.println("Tragically, trying to write to the file did not work.");
		}
		try
		{
			Files.writeString(Paths.get("f3.txt"), "Jeongyeon", StandardCharsets.ISO_8859_1);
		}
		catch (IOException io) {
			System.out.println("Tragically, trying to write to the file did not work.");
		}
		try
		{
			Files.writeString(Paths.get("f4.txt"), "Momo", StandardCharsets.ISO_8859_1);
		}
		catch (IOException io) {
			System.out.println("Tragically, trying to write to the file did not work.");
		}
		try
		{
			Files.writeString(Paths.get("f5.txt"), "Mina", StandardCharsets.ISO_8859_1);
		}
		catch (IOException io) {
			System.out.println("Tragically, trying to write to the file did not work.");
		}
		try
		{
			Files.writeString(Paths.get("f6.txt"), "Sana", StandardCharsets.ISO_8859_1);
		}
		catch (IOException io) {
			System.out.println("Tragically, trying to write to the file did not work.");
		}
		try
		{
			Files.writeString(Paths.get("f7.txt"), "Tzuyu", StandardCharsets.ISO_8859_1);
		}
		catch (IOException io) {
			System.out.println("Tragically, trying to write to the file did not work.");
		}
		try
		{
			Files.writeString(Paths.get("f8.txt"), "Chaeyoung", StandardCharsets.ISO_8859_1);
		}
		catch (IOException io) {
			System.out.println("Tragically, trying to write to the file did not work.");
		}
		try
		{
			Files.writeString(Paths.get("f9.txt"), "Dahyun", StandardCharsets.ISO_8859_1);
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
		File obj6 = new File("f6.txt");
		File obj7 = new File("f7.txt");
		File obj8 = new File("f8.txt");
		File obj9 = new File("f9.txt");
		
				
		// Deleting the files
		obj1.delete();
		obj2.delete();
		obj3.delete();
		obj4.delete();
		obj5.delete();
		obj6.delete();
		obj7.delete();
		obj8.delete();
		obj9.delete();
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
		ind.edit("f2.txt");
		
		
		Commit c1 = new Commit("Creating a new girl group!", "JYP Entertainment", null);
		c1.printCommitInfo();
		File cf1 = new File("./tests/objects/" + c1.sha1PTreeAndSummary());
		assertTrue(cf1.exists());
		
		// Second Commit
		ind.add("f3.txt");
		ind.add("f4.txt");
		ind.delete("f1.txt");
		ind.delete("f2.txt");
		File f1 = new File("f1.txt");
		assertTrue(!f1.exists());
		File f2 = new File("f2.txt");
		assertTrue(!f2.exists());
		
		Commit c2 = new Commit("Adding in some Main Dancers.", "JYP Entertainment", c1);
		c2.printCommitInfo();
		File cf2 = new File("./tests/objects/" + c2.sha1PTreeAndSummary());
		assertTrue(cf2.exists());
		
		// Third Commit
		ind.add("f5.txt");
		ind.delete("f3.txt");
		ind.add("f5.txt");
		ind.add("f6.txt");
		ind.edit("f5.txt");
		ind.edit("f6.txt");
		
		Commit c3 = new Commit("Adding in more international members.", "JYP Entertainment", c2);
		c3.printCommitInfo();
		File cf3 = new File("./tests/objects/" + c3.sha1PTreeAndSummary());
		assertTrue(cf3.exists());
		
		
		
		// Fourth Commit
		ind.add("f6.txt");
		ind.add("f1.txt");
		ind.add("f7.txt");
		ind.delete("f7.txt");
		File f7 = new File("f7.txt");
		assertTrue(!f7.exists());
		ind.add("f8.txt");
		
		Commit c4 = new Commit("Finally adding some rappers to complete Twice!", "JYP Entertainment", c3);
		c4.printCommitInfo();
		File cf4 = new File("./tests/objects/" + c4.sha1PTreeAndSummary());
		assertTrue(cf4.exists());
		
		// Fifth Commit
		ind.add("f9.txt");
		ind.delete("f9.txt");
		File f9 = new File("f9.txt");
		assertTrue(!f9.exists());
		
		Commit c5 = new Commit("Ready to debut Twice!", "JYP Entertainment", c4);
		c5.printCommitInfo();
		File cf5 = new File("./tests/objects/" + c5.sha1PTreeAndSummary());
		assertTrue(cf5.exists());
		
		// For screenshots
		System.out.println(c1.sha1PTreeAndSummary());
		System.out.println(c2.sha1PTreeAndSummary());
		System.out.println(c3.sha1PTreeAndSummary());
		System.out.println(c4.sha1PTreeAndSummary());
		System.out.println(c5.sha1PTreeAndSummary());	
	}

}
