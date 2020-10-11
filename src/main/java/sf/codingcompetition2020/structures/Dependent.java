package sf.codingcompetition2020.structures;

public class Dependent {
	private String firstName;
	private String lastName;

	public Dependent(String first, String last) {
		//Clean json data
		firstName = first.split(":")[1].replaceAll("[\"}]","");
		lastName = last.split(":")[1].replaceAll("[\"}]","");
	}
}
