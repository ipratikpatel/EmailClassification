import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/*
 *  This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
* Got some help from DatumBox blog on Text classification com.datumbox.opensource.features;
 */

/**
 * EmailClassification class can be used to train and predict emails.
 * Trains the DataModel using Multinomial naive bayes.
 * Need to train first and then use it to predict
 * <Some help from DatumBox Programs>
 * @author Pratik
 *
 */
public class EmailClassification {
	
	//Number of emails in taining set
	private int nEmails;
	
	//Number of types of emails
	private int nTypes;
	
	// log priors for log( P(t) )
	private Map<String, Double> prior;
	
	private Map<String, Map<String, Double> > condProb;
	
	/**
	 * EmailClassification constructor
	 */
	public EmailClassification() {
		nEmails = 0;
		nTypes = 0;
		prior = new HashMap<>();
		condProb = new HashMap<>();
	}

	/**
	 * Trains a Naive Bayes email-classifier by using Multinomial model
	 * By passing the trainingDataset, Classes names and ResultFile that
	 * contains information about classifying the trainingDataset.
	 * @param types, Types of emails such as spam, Non-Spam(HAM)
	 * @param folderName, Where the training emails exists
	 * @param ansFile, Which specifies each email from the folder to specific type
	 */
	public void trainEmails(String[] types, String folderName, String ansFile ){
		
		//Preprocess given email dataset using their results
		List<Email> trainSet = preprocessTrainEmails(types, folderName, ansFile);
		
	}
	
	/**
	 * Preprocess the training emails and returns a List of Email objects
	 * @param types
	 * @param folderName
	 * @param ansFile
	 * @return List of email objects which represents email as a bag of words and also specifies it's type
	 */
	public List<Email> preprocessTrainEmails(String[] types, String folderName, String ansFile){
		List<Email> trainset = new ArrayList<>();
		
		//Get a Map<String(email Filename), Integer(type of email)> from ansFile
		Map<String, Integer> answers = null;
		try {
			answers = extractAnsFile(ansFile);
		} catch (IOException e) {
			System.out.println("extractAnsFile threw an exception!");
			e.printStackTrace();
		}
		
		Email eml;
		
		for (String key: answers.keySet()) {
			//System.out.println(key + " = " + answers.get(key));
			//Process the email given its type
			//String key = "TRAIN_00000.eml";
			String fName = folderName +"//" + key;
			eml = processEmail(fName, types[answers.get(key)]);
			trainset.add(eml);
		}
		
		return trainset;
	}
	
	public Email processEmail(String fileName, String type){
		Email ans = new Email();
		ans.kind = type;
		
		//Read Email file and convert it to List<String>
		PrintWriter output = null;
    	try {
			output = new PrintWriter("output.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found exception!");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println("UnsupportedEncodingException");
			e.printStackTrace();
		}
		
		String content = EmailReceiver.mailFromFile(output, fileName);
		//Tokenize the content and have it return List<String>
		
		//System.out.println("Kind = " + ans.kind + " content = " + content);
		tokenizer(content);
		
		output.close();
		return ans;
	}
	
	public Map<String, Integer> tokenizer(String content){
		Map<String, Integer> tokens = new HashMap<>();
		
		//Only Keep alphabets
		String processContent = content.replaceAll("\\P{L}+", " ");
		
		//Preprocess by removing punctuation, duplicate space and lower it all
		processContent = processContent.replaceAll("\\p{P}", " ").replaceAll("\\s+", " ").toLowerCase(Locale.getDefault()); 
		String contentArray[] = processContent.split(" ");
		
		Integer temp;
		for (int i = 0; i < contentArray.length; i++) {
			temp = tokens.get(contentArray[i]);
			if(temp == null)
				temp = 0;
			
			tokens.put(contentArray[i], ++temp); //Increase the number of token
		}
		
//		System.out.println("Preprocesses String");
//		System.out.println(processContent);
//		
//		for(String key : tokens.keySet())
//			System.out.println("Key = " + key + " counter = " + tokens.get(key));
		
		return tokens;
	}
	
	public Map<String, Integer> extractAnsFile(String fileName) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("SPAMTrain.label"));
		
		Map<String, Integer> answers = new HashMap<String, Integer>();
		String line;
		while((line = br.readLine()) != null){
			
			String[] tokens = line.split(" ");
			
			if(tokens.length == 2) {
				String fName = tokens[1];
				Integer type = new Integer(tokens[0]);
				answers.put(fName, type);
			}
		}
		
		return answers;
	}

}
