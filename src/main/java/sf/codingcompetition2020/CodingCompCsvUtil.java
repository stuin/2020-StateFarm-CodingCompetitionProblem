package sf.codingcompetition2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Predicate;

import sf.codingcompetition2020.structures.Agent;
import sf.codingcompetition2020.structures.Claim;
import sf.codingcompetition2020.structures.Customer;
import sf.codingcompetition2020.structures.Vendor;

public class CodingCompCsvUtil {
	
	/* #1 
	 * readCsvFile() -- Read in a CSV File and return a list of entries in that file.
	 * @param filePath -- Path to file being read in.
	 * @param classType -- Class of entries being read in.
	 * @return -- List of entries being returned.
	 */
	public <T> List<T> readCsvFile(String filePath, Class<T> classType) {
		return readCsvFile(filePath, classType, null);
	}
	
	private <T> List<T> readCsvFile(String filePath, Class<T> classType, Predicate<T> filter) {
		//Open file
		File file = new File(filePath);
		ArrayList<T> list = new ArrayList<>();
		try {
			//Skip first line
			Scanner scan = new Scanner(file);
			scan.nextLine();

			while(scan.hasNextLine()) {
				
				//Build object
				String[] values = scan.nextLine().split(",");
				T ob = readLine(values, classType);
				if(filter == null || filter.test(ob))
					list.add(ob);
			}
		} catch(FileNotFoundException e) {
			System.out.println("File " + filePath + " not found");
		}
		return list;
	}
	
	private <T> T readLine(String[] input, Class<T> classType) {
		if(classType == Agent.class)
			return (T) new Agent(input);
		if(classType == Claim.class)
			return (T) new Claim(input);
		if(classType == Customer.class)
			return (T) new Customer(input);
		if(classType == Vendor.class)
			return (T) new Vendor(input);
		throw new IllegalArgumentException("Invalid class type");
	}

	
	/* #2
	 * getAgentCountInArea() -- Return the number of agents in a given area.
	 * @param filePath -- Path to file being read in.
	 * @param area -- The area from which the agents should be counted.
	 * @return -- The number of agents in a given area
	 */
	public int getAgentCountInArea(String filePath, String area) {
		return readCsvFile(filePath, Agent.class, agent -> agent.getArea().equals(area)).size();
	}

	
	/* #3
	 * getAgentsInAreaThatSpeakLanguage() -- Return a list of agents from a given area, that speak a certain language.
	 * @param filePath -- Path to file being read in.
	 * @param area -- The area from which the agents should be counted.
	 * @param language -- The language spoken by the agent(s).
	 * @return -- The number of agents in a given area
	 */
	public List<Agent> getAgentsInAreaThatSpeakLanguage(String filePath, String area, String language) {
		return readCsvFile(filePath, Agent.class, agent -> agent.getArea().equals(area) && agent.getLanguage().equals(language));
	}
	
	
	/* #4
	 * countCustomersFromAreaThatUseAgent() -- Return the number of individuals from an area that use a certain agent.
	 * @param filePath -- Path to file being read in.
	 * @param customerArea -- The area from which the customers should be counted.
	 * @param agentFirstName -- First name of agent.
	 * @param agentLastName -- Last name of agent.
	 * @return -- The number of customers that use a certain agent in a given area.
	 */
	public short countCustomersFromAreaThatUseAgent(Map<String,String> csvFilePaths, String customerArea, String agentFirstName, String agentLastName) {
		List<Agent> agents = readCsvFile(csvFilePaths.get("agentList"), Agent.class,
				agent -> agent.getFirstName().equals(agentFirstName) && agent.getLastName().equals(agentLastName));
		if(agents.size() != 1)
			return 0;

		return (short)readCsvFile(csvFilePaths.get("customerList"), Customer.class,
				customer -> customer.getArea().equals(customerArea) && customer.getAgentId() == agents.get(0).getAgentId()).size();
	}

	
	/* #5
	 * getCustomersRetainedForYearsByPlcyCostAsc() -- Return a list of customers retained for a given number of years, in ascending order of their policy cost.
	 * @param filePath -- Path to file being read in.
	 * @param yearsOfServeice -- Number of years the person has been a customer.
	 * @return -- List of customers retained for a given number of years, in ascending order of policy cost.
	 */
	public List<Customer> getCustomersRetainedForYearsByPlcyCostAsc(String customerFilePath, short yearsOfService) {
		List<Customer> customers = readCsvFile(customerFilePath, Customer.class, customer -> customer.getYearsOfService() == yearsOfService);
		customers.sort(Comparator.comparingInt(customer -> Integer.parseInt(customer.getTotalMonthlyPremium().substring(1))));
		return customers;
	}

	
	/* #6
	 * getLeadsForInsurance() -- Return a list of individuals who’ve made an inquiry for a policy but have not signed up.
	 * *HINT* -- Look for customers that currently have no policies with the insurance company.
	 * @param filePath -- Path to file being read in.
	 * @return -- List of customers who’ve made an inquiry for a policy but have not signed up.
	 */
	public List<Customer> getLeadsForInsurance(String filePath) {
		return readCsvFile(filePath, Customer.class, customer -> 
				!(customer.hasAutoPolicy() || customer.hasHomePolicy() || customer.hasRentersPolicy()));
	}


	/* #7
	 * getVendorsWithGivenRatingThatAreInScope() -- Return a list of vendors within an area and include options to narrow it down by: 
			a.	Vendor rating
			b.	Whether that vendor is in scope of the insurance (if inScope == false, return all vendors in OR out of scope, if inScope == true, return ONLY vendors in scope)
	 * @param filePath -- Path to file being read in.
	 * @param area -- Area of the vendor.
	 * @param inScope -- Whether or not the vendor is in scope of the insurance.
	 * @param vendorRating -- The rating of the vendor.
	 * @return -- List of vendors within a given area, filtered by scope and vendor rating.
	 */
	public List<Vendor> getVendorsWithGivenRatingThatAreInScope(String filePath, String area, boolean inScope, int vendorRating) {
		return readCsvFile(filePath, Vendor.class,
				vendor -> vendor.getArea().equals(area) && vendor.getVendorRating() >= vendorRating && (!inScope || vendor.isInScope()));
	}


	/* #8
	 * getUndisclosedDrivers() -- Return a list of customers between the age of 40 and 50 years (inclusive), who have:
			a.	More than X cars
			b.	less than or equal to X number of dependents.
	 * @param filePath -- Path to file being read in.
	 * @param vehiclesInsured -- The number of vehicles insured.
	 * @param dependents -- The number of dependents on the insurance policy.
	 * @return -- List of customers filtered by age, number of vehicles insured and the number of dependents.
	 */
	public List<Customer> getUndisclosedDrivers(String filePath, int vehiclesInsured, int dependents) {
		return readCsvFile(filePath, Customer.class,
				customer -> customer.getVehiclesInsured() > vehiclesInsured && customer.getDependents().size() <= dependents &&
				customer.getAge() >= 40 && customer.getAge() <= 50);
	}	


	/* #9
	 * getAgentIdGivenRank() -- Return the agent with the given rank based on average customer satisfaction rating. 
	 * *HINT* -- Rating is calculated by taking all the agent rating by customers (1-5 scale) and dividing by the total number 
	 * of reviews for the agent.
	 * @param filePath -- Path to file being read in.
	 * @param agentRank -- The rank of the agent being requested.
	 * @return -- Agent ID of agent with the given rank.
	 */
	public int getAgentIdGivenRank(String filePath, int agentRank) {
		List<Customer> list = readCsvFile(filePath, Customer.class);
		HashMap<Integer, Pair> agents = new HashMap<>();

		//Create map of agent ratings
		for(Customer c : list) {
			int agentId = c.getAgentId();
			if(agents.containsKey(agentId))
				agents.replace(agentId, agents.get(agentId).add(new Pair(c.getAgentRating(), 1)));
			else
				agents.put(agentId, new Pair(c.getAgentRating(), 1));
		}

		//Create new list
		List<Pair> agentsList = new ArrayList<>();
		for(int agentId : agents.keySet()) {
			//Calculate avg rating
			Pair p = agents.get(agentId);
			agentsList.add(new Pair(agentId, 100 * p.first / p.second));
		}

		//Sort agents
		agentsList.sort(Comparator.comparingInt(pair -> pair.second));
		return agentsList.get(agentsList.size() - agentRank).first;
	}

	
	/* #10
	 * getCustomersWithClaims() -- Return a list of customers who’ve filed a claim within the last <numberOfMonths> (inclusive). 
	 * @param filePath -- Path to file being read in.
	 * @param monthsOpen -- Number of months a policy has been open.
	 * @return -- List of customers who’ve filed a claim within the last <numberOfMonths>.
	 */
	public List<Customer> getCustomersWithClaims(Map<String,String> csvFilePaths, short monthsOpen) {
		List<Claim> claims = readCsvFile(csvFilePaths.get("claimList"), Claim.class, claim -> claim.getMonthsOpen() <= monthsOpen);
		List<Customer> customers = readCsvFile(csvFilePaths.get("customerList"), Customer.class);
		Set<Customer> filteredCustomers = new HashSet<>();
		
		for(Claim c : claims)
			filteredCustomers.add(customers.get(c.getCustomerId() - 1));
		
		return new ArrayList<>(filteredCustomers);
	}	

}
