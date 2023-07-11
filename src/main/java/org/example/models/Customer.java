package org.example.models;


import java.util.Date;

public class Customer{
    private Integer id;
    private String companyname;
    private String fullname;
    private Boolean status;
    private Date registrationDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer(Integer id, String companyname, String fullname, Boolean status, Date registrationDate) {
        this.id = id;
        this.companyname = companyname;
        this.fullname = fullname;
        this.status = status;
        this.registrationDate = registrationDate;
    }

    public Customer() {}

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getCompanyname() {
        return companyname;
    }

    public String getFullname() {
        return fullname;
    }

    public Boolean getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "companyname='" + companyname + '\'' +
                ", fullname='" + fullname + '\'' +
                ", status='" + status + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                '}';
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }
}
