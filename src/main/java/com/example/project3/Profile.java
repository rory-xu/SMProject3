package com.example.project3;

/**
 * A class describing a person, with first name and last name as well as date of birth
 * @author Rory Xu, Hassan Alfareed
 */
public class Profile {
    private final String fname;
    private final String lname;
    private final Date dob;

    /**
     * Constructs a profile for a person
     * @param fname The first name of the person
     * @param lname The last name of the person
     * @param dob The date of birth of the person
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Overrides the default equals of the java library in order to see if two Profiles are the same
     * @param obj The profile to check
     * @return True if the two profiles are the same, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Profile other))
            return false;
        return this.fname.equalsIgnoreCase(other.fname)
                && this.lname.equalsIgnoreCase(other.lname)
                && this.dob.compareTo(other.dob) == 0;
    }

    /**
     * Overrides the default toString of the java library in order to display profile information
     * @return The first and last name, as well as date of birth of the person
     */
    @Override
    public String toString() {
        return this.fname + " " + this.lname + " " + this.dob;
    }
}
