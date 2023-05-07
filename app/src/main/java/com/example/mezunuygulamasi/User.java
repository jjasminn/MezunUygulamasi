package com.example.mezunuygulamasi;

import java.io.Serializable;

public class User implements Serializable {
    private String photoUrl;
    private String uid;
    private String name;
    private String surname;
    private String linkedn;
    private String email;
    private String phone;
    private String licenceCollage;
    private String licenceDepartment;
    private String dateLicenceEnter;
    private String dateLicenceFinish;
    private String company;
    private String companyCountry;
    private String companyCity;
    private String degreeCollage;
    private String degreeDepartment;
    private String degreeEnter;
    private String degreeFinish;
    private String doctorateCollage;
    private String doctorateDepartment;
    private String doctorateEnter;
    private String doctorateFinish;


    public User(String uid,String name, String surname, String email, String dateLicenceEnter, String dateLicenceFinish, String phone) {
        this.uid=uid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dateLicenceEnter = dateLicenceEnter;
        this.dateLicenceFinish = dateLicenceFinish;
        this.phone = phone;

    }
    public User( String name, String surname, String email, String phone,String linkedn, String licenceCollage, String licenceDepartment, String enterLicence, String finishLicence, String degreeCollage, String degreeDepartment, String enterDegree, String finishDegree, String doctorateCollage, String doctorateDepartment, String enterDoctorate, String finishDoctorate, String company, String companyCountry, String companyCity, String photoUrl) {

        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.linkedn=linkedn;
        this.licenceCollage = licenceCollage;
        this.licenceDepartment = licenceDepartment;
        this.dateLicenceEnter = enterLicence;
        this.dateLicenceFinish = finishLicence;
        this.degreeCollage = degreeCollage;
        this.degreeDepartment = degreeDepartment;
        this.degreeEnter = enterDegree;
        this.degreeFinish = finishDegree;
        this.doctorateCollage = doctorateCollage;
        this.doctorateDepartment = doctorateDepartment;
        this.doctorateEnter = enterDoctorate;
        this.doctorateFinish = finishDoctorate;
        this.company = company;
        this.companyCountry = companyCountry;
        this.companyCity = companyCity;
        this.photoUrl = photoUrl;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLicenceCollage() {
        return licenceCollage;
    }

    public void setLicenceCollage(String licenceCollage) {
        this.licenceCollage = licenceCollage;
    }

    public String getLicenceDepartment() {
        return licenceDepartment;
    }

    public void setLicenceDepartment(String licenceDepartment) {
        this.licenceDepartment = licenceDepartment;
    }

    public String getDateLicenceEnter() {
        return dateLicenceEnter;
    }

    public void setDateLicenceEnter(String dateLicenceEnter) {
        this.dateLicenceEnter = dateLicenceEnter;
    }

    public String getDateLicenceFinish() {
        return dateLicenceFinish;
    }

    public void setDateLicenceFinish(String dateLicenceFinish) {
        this.dateLicenceFinish = dateLicenceFinish;
    }

    public String getDegreeCollage() {
        return degreeCollage;
    }

    public void setDegreeCollage(String degreeCollage) {
        this.degreeCollage = degreeCollage;
    }

    public String getDegreeDepartment() {
        return degreeDepartment;
    }

    public void setDegreeDepartment(String degreeDepartment) {
        this.degreeDepartment = degreeDepartment;
    }

    public String getDegreeEnter() {
        return degreeEnter;
    }

    public void setDegreeEnter(String degreeEnter) {
        this.degreeEnter = degreeEnter;
    }

    public String getDegreeFinish() {
        return degreeFinish;
    }

    public void setDegreeFinish(String degreeFinish) {
        this.degreeFinish = degreeFinish;
    }

    public String getDoctorateCollage() {
        return doctorateCollage;
    }

    public void setDoctorateCollage(String doctorateCollage) {
        this.doctorateCollage = doctorateCollage;
    }

    public String getDoctorateDepartment() {
        return doctorateDepartment;
    }

    public void setDoctorateDepartment(String doctorateDepartment) {
        this.doctorateDepartment = doctorateDepartment;
    }

    public String getDoctorateEnter() {
        return doctorateEnter;
    }

    public void setDoctorateEnter(String doctorateEnter) {
        this.doctorateEnter = doctorateEnter;
    }

    public String getDoctorateFinish() {
        return doctorateFinish;
    }

    public void setDoctorateFinish(String doctorateFinish) {
        this.doctorateFinish = doctorateFinish;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getLinkedn() {
        return linkedn;
    }

    public void setLinkedn(String linkedn) {
        this.linkedn = linkedn;
    }
}


