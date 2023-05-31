package com.emsi.scientist_manage.entities;

import java.io.Serializable;
import java.util.Objects;

public class Scientist implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
    private String fieldOfStudy;
    private int yearsOfExperience;
    private String researchInterests;
    private int publications;
    private int awardsAndHonors;
    
    //Constructor without parameters
    public Scientist() {
		id=0;
        name = "";
        fieldOfStudy = "";
        yearsOfExperience = 0;
        researchInterests = "";
        publications = 0;
        awardsAndHonors = 0;
    }
    
    //Constructor with parameters
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
    public Scientist( Integer id,String name, String fieldOfStudy, int yearsOfExperience, String researchInterests,
                     int publications, int awardsAndHonors) {
        this.id=id;
        this.name = name;
        this.fieldOfStudy = fieldOfStudy;
        this.yearsOfExperience = yearsOfExperience;
        this.researchInterests = researchInterests;
        this.publications = publications;
        this.awardsAndHonors = awardsAndHonors;
    }

	public String getName() {
		return name;
	}

	public String getFieldOfStudy() {
		return fieldOfStudy;
	}

	public int getYearsOfExperience() {
		return yearsOfExperience;
	}

	public String getResearchInterests() {
		return researchInterests;
	}

	public int getPublications() {
		return publications;
	}

	public int getAwardsAndHonors() {
		return awardsAndHonors;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}

	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public void setResearchInterests(String researchInterests) {
		this.researchInterests = researchInterests;
	}

	public void setPublications(int publications) {
		this.publications = publications;
	}

	public void setAwardsAndHonors(int awardsAndHonors) {
		this.awardsAndHonors = awardsAndHonors;
	}

	@Override
	public String toString() {
		return  this.name + " | " + this.fieldOfStudy + " | " + this.yearsOfExperience + " | " + this.researchInterests +" | " + this.publications + " | " + this.awardsAndHonors ;	
		}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Scientist other = (Scientist) obj;
		return Objects.equals(awardsAndHonors, other.awardsAndHonors)
				&& Objects.equals(fieldOfStudy, other.fieldOfStudy) && Objects.equals(name, other.name)
				&& Objects.equals(publications, other.publications)
				&& Objects.equals(researchInterests, other.researchInterests)
				&& yearsOfExperience == other.yearsOfExperience;
	}
	
}
