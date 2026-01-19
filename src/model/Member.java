package model;

import enumeration.Gender;
import enumeration.MembershipType;

import java.time.LocalDate;

public class Member {

    private int memberId;

    private static int idCtr = 1;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private MembershipType membershipType;
    private LocalDate birthdate;
    private Gender gender;
    private String contactNumber;
    private String address;
    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;

    public Member(String firstName, String lastName,
                  String emailAddress,MembershipType membershipType,
                  LocalDate birthdate, Gender gender,
                  String contactNumber, String address) {

        this.memberId = idCtr++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.membershipType = membershipType;
        this.birthdate = birthdate;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.address = address;

        this.membershipStartDate = LocalDate.now();
        this.membershipEndDate = LocalDate.now().plusMonths(membershipType.getDurationInMonths());
    }

    // Test Members with expired membership date
    public Member(String firstName, String lastName,
                  String emailAddress, MembershipType membershipType,
                  LocalDate birthdate, Gender gender,
                  String contactNumber, String address,
                  LocalDate membershipStartDate) {

        this.memberId = idCtr++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.membershipType = membershipType;
        this.birthdate = birthdate;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.address = address;

        this.membershipStartDate = membershipStartDate;
        this.membershipEndDate =
                membershipStartDate.plusMonths(membershipType.getDurationInMonths());
    }

    public Member(){}

    public boolean isActive() {
        return !membershipEndDate.isBefore(LocalDate.now());
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getMembershipStartDate() {
        return membershipStartDate;
    }

    public void setMembershipStartDate(LocalDate membershipStartDate) {
        this.membershipStartDate = membershipStartDate;
    }

    public LocalDate getMembershipEndDate() {
        return membershipEndDate;
    }

    public void setMembershipEndDate(LocalDate membershipEndDate) {
        this.membershipEndDate = membershipEndDate;
    }


}
