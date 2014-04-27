import java.util.HashMap;
import java.util.Map;

/**
 * The Email object represents the emails that we use for
 * training or predicting
 * @author Pratik
 *
 */
public class Email {
	
	//Tokens extracted from email and how many times they are repeated in
	//The email
	public Map<String, Integer> tokens;
	
	//The kind or class of the Email
	public String kind;
	
	//Email constructor
	public Email(){
		tokens = new HashMap<>();
	}
	
}
