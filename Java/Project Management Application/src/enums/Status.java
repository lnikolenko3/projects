package enums;

public enum Status {
	
	ACCEPTED("Accepted"),
	PENDING("Pending"),
	REJECTED("Rejected");
	
	private String name;
	
	private Status(String name) {
		this.name= name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public static Status getStatus(String name) {
		for (Status status : Status.values()) {
			if (status.toString().equals(name)) {
				return status;
			}
		}
		return null;
	}
}
