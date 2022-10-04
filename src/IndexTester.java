import java.io.IOException;

public class IndexTester {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Index indy = new Index();
		indy.init();
		indy.add("first.txt");
		indy.add("second.txt");
		indy.edit("second.txt");
		indy.delete("first.txt");
		
	}
}