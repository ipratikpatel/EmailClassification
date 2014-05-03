Name:	Pratik Patel
UT EID:	pmp642

EmailClassification
===================
1) EmailClassification Class
	This class is the implementation of multinomial naive bayes framework. It implements methods such as trainEmails() and predictEmail() which are responsible for training a classifier and using it to predicting emails. It calls other methods to read and process emails as well.

2) EmailReceiver class
	This class is responsible from retrieving emails. It has methods such as getEmailFromGmail() and mailFromFile(PrintWriter output, String filename). getEmailFromGmail() method is used to get email from gmail account. To use the function mailFromGmail(), you would have to change the values user name and password. mailFromFile() method is used to get email from a given file path and it returns a string containing content of the email.

3) Email class
	The email Object holds information about the email such as the Email's type and it's Map<String, Integer> containing all of the tokens from email and how many times each token is repeated in the email.

Using the EmailClassification class
Requirements:
1) Have to have a TRAINING folder containing all of the Emails, which will be used for training the classifier
2) Have to have a file containing email name and it's type index

//To create an EmailClassification object
		EmailClassification ec = new EmailClassification();

//Define a types array, here Spam's index is 0 and Ham's index is 1, This indexes are used to label email in your trainlanbel.txt file
//So to label email00001 in your label file you say <type> <email filename> something like 0 email00001, this will label email00001 as spam
		String types[] = {"Spam", "Ham"};

		//../SPAMTrain.label contains a list of Email file name which exist in TRAINING folder and it's label number
		// label numbers are the index in your types array
		// So for example, in SPAMTrain.label we have 
		// 0 TRAIN_00501.eml, which means the type of TRAIN_00501 email that exist in TRAINING folder's type is "Spam"
		ec.trainEmails(types, "../TRAINING" , "../SPAMTrain.label");

		//If you want to predict some of the emails already exist in TRAINING folder then run this block of code
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
//Above code will print out the result on the console and the original email for first 500 emails are in TestingEmail.txt file.