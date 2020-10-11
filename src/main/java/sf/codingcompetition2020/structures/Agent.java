package sf.codingcompetition2020.structures;

public class Agent {
	
	private int agentId;
	private String area;
	private String language;
	private String firstName;
	private String lastName;
	
	public Agent(String[] s) {
		if(s.length != 5)
			return;
		
		//Set values
		agentId = Integer.parseInt(s[0]);
		area = s[1];
		language = s[2];
		firstName = s[3];
		lastName = s[4];
	}

	public int getAgentId() {
		return agentId;
	}

	public String getArea() {
		return area;
	}

	public String getLanguage() {
		return language;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}
