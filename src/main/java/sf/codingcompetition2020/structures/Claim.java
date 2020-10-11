package sf.codingcompetition2020.structures;

public class Claim {
	private int claimId;
	private int customerId;
	private boolean closed;
	private int monthsOpen;
	
	public Claim(String[] s) {
		if(s.length != 4)
			return;

		//Set values
		claimId = Integer.parseInt(s[0]);
		customerId = Integer.parseInt(s[1]);
		closed = s[2].equals("true");
		monthsOpen = Integer.parseInt(s[3]);
	}

    public int getClaimId() {
    	return claimId;
    }

	public int getCustomerId() {
		return customerId;
	}

	public boolean isClosed() {
		return closed;
	}

	public int getMonthsOpen() {
		return monthsOpen;
	}
}
