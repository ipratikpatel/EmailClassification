
public class TestEmailClassification {

	public static void main(String[] args) {
//		EmailReceiver.getEmailFromGmail();
//		System.out.println("Get messages from TRAINING Folder");
//		EmailReceiver.allMailsFromFolder("TRAINING");
		EmailClassification ec = new EmailClassification();
		String types[] = {"Spam", "Ham"};

		//../SPAMTrain.label contains a list of Email file name which exist in TRAINING folder and it's label number
		// label numbers are the index in your types array
		// So for example, in SPAMTrain.label we have 
		// 0 TRAIN_00501.eml, which means the type of TRAIN_00501 email that exist in TRAINING folder's type is "Spam"
		ec.trainEmails(types, "../TRAINING" , "../SPAMTrain.label");
		
		//System.out.println("Predicting Email!");
		//System.out.println(ec.predictEmail("TRAINING//TRAIN_00002.eml"));
		
		String filename;

		//If you want to predict some of the emails already exixst in TRAINING folder then run this block of code
		//In this case it predicts first 500 emails in the ../TRAINING folder
		for (int i = 0; i <= 500; i++) {
			filename = "../TRAINING//TRAIN_";
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
