package enums;

public enum Year {
	
	FRESHMAN("Freshman"),
	SOPHOMORE("Sophomore"),
	JUNIOR("Junior"),
	SENIOR("Senior");
	
	private String name;
	
	private Year(String name) {
		this.name= name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public static Year getYear(String name) {
		if (name == null) {
			return null;
		}
		for (Year year : Year.values()) {
			if (year.toString().equals(name)) {
				return year;
			}
		}
		return null;
	}
}
