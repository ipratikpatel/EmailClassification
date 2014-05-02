
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
		for (int i = 0; i <= 500; i++) {
			filename = "TRAINING//TRAIN_";
			filename += String.format("%05d", i);
			filename += ".eml";
			//System.out.println("Email # " + i + " Filename: " + filename + " Prediction: " + ec.predictEmail(filename));
			int ans;
			if(ec.predictEmail(filename).equals("Spam"))
				ans = 0;
			else
				ans = 1;
			System.out.println(ans + " " + filename.replace("TRAINING//", ""));
		}
		
		System.out.println("Done!");
	}

}
