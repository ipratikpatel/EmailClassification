
public class TestEmailClassification {

	public static void main(String[] args) {
//		EmailReceiver.getEmailFromGmail();
//		System.out.println("Get messages from TRAINING Folder");
//		EmailReceiver.allMailsFromFolder("TRAINING");
		EmailClassification ec = new EmailClassification();
		String types[] = {"Spam", "Ham"};
		ec.trainEmails(types, "TRAINING" , "SPAMTrain.label");
		
		System.out.println("Done!");
	}

}
