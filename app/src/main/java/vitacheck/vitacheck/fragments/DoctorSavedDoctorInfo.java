package vitacheck.vitacheck.fragments;

import java.util.Date;

/**
 * Created by ERIC on 11/17/2015.
 * This class will hold all data on a single doctor that was saved
 * by a user
 */
public class DoctorSavedDoctorInfo {
    private String name;
    private String doctorType;
    private String insuranceType;
    private int phoneNumber;
    private String email;
    private String address;
    private String websiteLink;
    private Date appointmentDate;
    private String parseID;

    public String getParseID() {return parseID;}
    public void setParseID(String id) {this.parseID=id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDoctorType() {return doctorType;}
    public void setDoctorType(String doctorType) {this.doctorType = doctorType;}

    public String getInsuranceType() {return insuranceType;}
    public void setInsuranceType(String insuranceType) {this.insuranceType = insuranceType;}

    public int getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(int phoneNumber) {this.phoneNumber = phoneNumber;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    public String getWebsiteLink() {return websiteLink;}
    public void setWebsiteLink(String websiteLink) {this.websiteLink = websiteLink;}

    public Date getAppointmentDate() {return appointmentDate;}
    public void setAppointmentDate(Date appointmentDate) {this.appointmentDate = appointmentDate;}


}

