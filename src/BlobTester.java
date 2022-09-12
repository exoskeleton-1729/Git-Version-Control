import java.io.FileNotFoundException;
import java.io.IOException;

public class BlobTester {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
			Blob blobby=new Blob("something");
			blobby.decompress(blobby.compress("jeff"));
	}

}
