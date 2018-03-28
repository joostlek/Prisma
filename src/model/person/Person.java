package model.person;

public abstract class Person {
	private String firstName;
	private String middleName;
	private String lastName;
	private String password;
	private String username;


	public Person(String firstName, String middleName, String lastName, String password, String username) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.password = password;
		this.username = username;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Person) {
			Person p = (Person) obj;
			return (this.firstName.equals(p.firstName) &&
					this.middleName.equals(p.middleName) &&
					this.lastName.equals(p.middleName) &&
					this.password.equals(p.password) &&
					this.username.equals(p.username));
		}
		return false;
	}

	public String getFirstName() {
		return this.firstName;
	}

	private String getLastName() {
		return this.lastName;
	}

	protected String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	public String getFullLastName() {
		String res = "";
		if (this.middleName != null && !this.middleName.equals("") && this.middleName.length() > 0) {
			res += this.middleName + " ";
		}
		return res + this.getLastName();
	}

	public boolean samePassword(String password) {
		return this.getPassword().equals(password);
	}
}
