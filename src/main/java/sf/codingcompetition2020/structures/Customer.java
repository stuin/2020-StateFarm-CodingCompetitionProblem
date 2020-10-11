package sf.codingcompetition2020.structures;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private int customerId;
	private String firstName;
	private String lastName;
	private int age;
	private String area;
	private int agentId;
	private short agentRating;
	private String primaryLanguage;
	private List<Dependent> dependents;
	private boolean homePolicy;
	private boolean autoPolicy;
	private boolean rentersPolicy;
	private String totalMonthlyPremium;
	private short yearsOfService;
	private Integer vehiclesInsured;
	
	public Customer(String[] s) {
		if(s.length < 15)
			return;

		customerId = Integer.parseInt(s[0]);
		firstName = s[1];
		lastName = s[2];
		age = Integer.parseInt(s[3]);
		area = s[4];
		agentId = Integer.parseInt(s[5]);
		agentRating = Short.parseShort(s[6]);
		primaryLanguage = s[7];

		int i = 0;
		dependents = new ArrayList<>();
		if(s[8].length() > 0) {
			do {
				dependents.add(new Dependent(s[8 + i], s[9 + i]));
				i += 2;
			} while(s[8 + i].charAt(0) == '{');
			i--;
		}

		homePolicy = s[9 + i].equals("true");
		autoPolicy = s[10 + i].equals("true");
		rentersPolicy = s[11 + i].equals("true");
		totalMonthlyPremium = s[12 + i];
		yearsOfService = Short.parseShort(s[13 + i]);
		vehiclesInsured = Integer.parseInt(s[14 + i]);
	}

	public int getCustomerId() {
		return customerId;
	}

    public String getFirstName() {
    	return firstName;
    }

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public String getArea() {
		return area;
	}

	public int getAgentId() {
		return agentId;
	}

	public short getAgentRating() {
		return agentRating;
	}

	public List<Dependent> getDependents() {
		return dependents;
	}

	public boolean hasHomePolicy() {
		return homePolicy;
	}

	public boolean hasAutoPolicy() {
		return autoPolicy;
	}

	public boolean hasRentersPolicy() {
		return rentersPolicy;
	}

	public String getTotalMonthlyPremium() {
		return totalMonthlyPremium;
	}

	public int getYearsOfService() {
		return yearsOfService;
	}

	public Integer getVehiclesInsured() {
		return vehiclesInsured;
	}
}
