package com.aldidb.backenddb.pbo;

public class Manusia {
	private String Name;
	private String Gender;
	private int Age;
	
	public Manusia(String Name, String Gender, int Age) {
		this.Name = Name;
		this.Gender = Gender;
		this.Age = Age;
	}
	
	public void info() {
		System.out.println(this.Name);
		System.out.println(this.Gender);
		System.out.println(this.Age);
	}

}
