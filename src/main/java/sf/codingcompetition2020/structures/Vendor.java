package sf.codingcompetition2020.structures;

public class Vendor {
	private int vendorId;
	private String area;
	private int vendorRating;
	private boolean inScope;

	public Vendor(String[] s) {
		if(s.length != 4)
			return;

		vendorId = Integer.parseInt(s[0]);
		area = s[1];
		vendorRating = Integer.parseInt(s[2]);
		inScope = s[3].equals("true");
	}

	public int getVendorId() {
		return vendorId;
	}

	public String getArea() {
		return area;
	}

	public int getVendorRating() {
		return vendorRating;
	}

	public boolean isInScope() {
		return inScope;
	}
}
