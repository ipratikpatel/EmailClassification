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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//EmailReceiver.getEmailFromGmail();
		System.out.println("Get messages from TRAINING Folder");
		EmailReceiver.allMailsFromFolder("TRAINING");
	}
	
	
	/**
	 * Trains a Naive Bayes email-classifier by using Multinomial model
	 * By passing the trainingDataset, Classes names and ResultFile that
	 * contains information about classifying the trainingDataset.
	 */
	public void trainEmails(){
		
		//Preprocess given email dataset using their results
		
	}
	
	/**
	 * 
	 */
	public void preprocessEmails(){
		
	}

}
