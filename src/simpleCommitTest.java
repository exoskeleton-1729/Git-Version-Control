import java.io.IOException;

public class simpleCommitTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
			Commit commie = new Commit("tis the og commit","joe",null);
			System.out.println(commie.sha1TreeContent());
			System.out.println(commie.sha1PTreeAndSummary());
			commie.printCommitInfo();
			Commit commiesKid=new Commit("this is child","beanLord",commie);
			System.out.println(commiesKid.sha1TreeContent());
			System.out.println(commiesKid.sha1PTreeAndSummary());
			commiesKid.printCommitInfo();
			commie.printCommitInfo();
			
	}

}
