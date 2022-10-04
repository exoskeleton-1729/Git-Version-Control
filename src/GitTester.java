import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GitTester {
	private Index idx = new Index();
	
	File makeFile(String name, String content) throws Exception {
		File newFile = new File("./tests/" + name);
		newFile.createNewFile();
		PrintWriter pw = new PrintWriter(newFile);
		pw.write(content);
		pw.close();
		return newFile;
	}

	@Test
	void testBlob() throws Exception {
		makeFile("test.txt", "Test Content.");
		
		Blob b = new Blob("test.txt");
		File f = new File("./tests/objects/" + b.getSha1Name());
		assertTrue(f.exists());
		
		File testerFile = new File("./tests/test.txt");
		testerFile.delete();
	}
	
	@Test
	void testInit() throws Exception {
		File existingIndex = new File("./tests/index");
		existingIndex.delete();
		File objDir = new File("./tests/objects");
		objDir.delete();
		
		idx.init();
		
		File objectsFolder = new File("./tests/objects");
		assertTrue(objectsFolder.exists());
		File indexFile = new File("./tests/index");
		assertTrue(indexFile.exists());
	}
	
	@Test
	void testAdd() throws Exception {
		File file1 = makeFile("first.txt", "First file test content");
		File file2 = makeFile("second.txt", "Second file test content");
		File file3 = makeFile("third.txt", "This confers essentially no additional benefit from just having two test files but idk man i like having stuff in groups of three");
		
		Path indexFile = Path.of("./tests/index");
				
		idx.add("first.txt");
	//	String firstSHA = idx.indeces.get("first.txt");
	//	File firstBlob = new File("./tests/objects/" + firstSHA);
	//	assertTrue(firstBlob.exists());
	//	assertTrue(Files.readString(indexFile).contains("first.txt : "+idx.indeces.get("first.txt")));
		
		idx.add("second.txt");
	//	String secondSHA = idx.indeces.get("second.txt");
	//	File secondBlob = new File("./tests/objects/" + secondSHA);
	//	assertTrue(secondBlob.exists());
	//	assertTrue(Files.readString(indexFile).contains("first.txt : "+idx.indeces.get("first.txt")) && Files.readString(indexFile).contains("second.txt : "+idx.indeces.get("second.txt")));
		
		idx.add("third.txt");
	//	String thirdSHA = idx.indeces.get("third.txt");
	//	File thirdBlob = new File("./tests/objects/" + thirdSHA);
	//	assertTrue(thirdBlob.exists());
	//	assertTrue(Files.readString(indexFile).contains("first.txt : "+idx.indeces.get("first.txt")) && Files.readString(indexFile).contains("second.txt : "+idx.indeces.get("second.txt")) && Files.readString(indexFile).contains("third.txt : "+idx.indeces.get("third.txt")));
	}
	
	@Test
	void testRemove() throws Exception {
		//String firstSHA = idx.indeces.get("first.txt");
		//String secondSHA = idx.indeces.get("second.txt");
		//String thirdSHA = idx.indeces.get("third.txt");
		
		Path indexFile = Path.of("./tests/index");
		
		idx.remove("first.txt");
		//File firstBlob = new File("./tests/objects/" + firstSHA);
		//assertFalse(firstBlob.exists());
		//assertFalse(Files.readString(indexFile).contains("first.txt : "+idx.indeces.get("first.txt")));
	}
	
	@Test
	void treeTest() throws Exception {
		ArrayList<String> info=new ArrayList<String>();
		info.add("shrek : alkdfjadasd");
		info.add("beans : adlfjasdkf");
		info.add("kuro : ;lj;lkj;kj");
		
		Tree tree=new Tree(info);
		
		File treeFile = new File("./tests/objects/900f7e1e74899e06767f554eff1c99471b9e2ae6");
		assertTrue(treeFile.exists());
		
		Path treePath= Paths.get("./tests/objects/900f7e1e74899e06767f554eff1c99471b9e2ae6");
		String indexContent=Files.readString(treePath);
		assertTrue(indexContent.equals("shrek : alkdfjadasd"+"\n"+"beans : adlfjasdkf"+"\n"+"kuro : ;lj;lkj;kj"+"\n"));
		
	}
	
	
}
