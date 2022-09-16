import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GitTester {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		File testerFile = new File("test.txt");
		testerFile.createNewFile();
		PrintWriter pw = new PrintWriter(testerFile);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		File testerFile = new File("test.txt");
		testerFile.delete();
	}

	@Test
	void testBlob() throws Exception {
		Blob b = new Blob("test.txt");
		File f = new File("./tests/objects/" + b.getSha1Name());
		assertTrue(f.exists());
	}

}
