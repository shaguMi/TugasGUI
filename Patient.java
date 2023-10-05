package KlinikApps;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Patient { 
    private static int nextRegisterNumber = 1;
    private int registerNumber;
    private String name;
    private Date birthDate;
    private String address;
    private String noTelp;

    public Patient(String name, Date birthDate, String address, String noTelp) {
        this.registerNumber = nextRegisterNumber++;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.noTelp = noTelp;
    }

    public int getRegisterNumber() {
        return registerNumber;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getFormattedBirthDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
        return dateFormat.format(birthDate);
    }

    public String getAddress() {
        return address;
    }

    public String getNoTelp() {
        return noTelp;
    }

    // Add a method to update patient information
    public void update(String name, Date birthDate, String address, String noTelp) {
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.noTelp = noTelp;
    }

}
