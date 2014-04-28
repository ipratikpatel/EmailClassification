
public class TestEmailClassification {

	public static void main(String[] args) {
//		EmailReceiver.getEmailFromGmail();
//		System.out.println("Get messages from TRAINING Folder");
//		EmailReceiver.allMailsFromFolder("TRAINING");
		EmailClassification ec = new EmailClassification();
		String types[] = {"Spam", "Ham"};
		ec.trainEmails(types, "TRAINING" , "SPAMTrain.label");
		
		System.out.println("Predicting Emails!");
		//System.out.println(ec.predictEmail("TRAINING//TRAIN_00002.eml"));
		
		String filename;
		for (int i = 10; i <= 20; i++) {
			filename = "TRAINING//TRAIN_";
			filename += String.format("%05d", i);
			filename += ".eml";
			System.out.println("Email # " + i + " Filename: " + filename + " Prediction: " + ec.predictEmail(filename));
			
		}
		
		System.out.println("Done!");
	}

}
