package service;

import enumeration.Gender;
import model.Member;
import storage.FileHandler;
import util.DisplayUtil;
import util.InputUtil;

import java.time.LocalDate;

public interface MemberUpdateService {
    void updateMemberInformation();

    default boolean handleUpdateChoice(int choice, Member member) {
        switch (choice) {
            case 1 -> updateFirstName(member);
            case 2 -> updateLastName(member);
            case 3 -> updateBirthdate(member);
            case 4 -> updateGender(member);
            case 5 -> updateContactNumber(member);
            case 6 -> updateEmailAddress(member);
            case 7 -> updateAddress(member);
            case 8 -> updateAllInformation(member);
            case 9 -> {
                return false;
            }
        }

        return InputUtil.getYesNo("\033[1;96mUpdate another information?\033[0m");
    }

    default void updateFirstName(Member member) {
        String firstName = InputUtil.getString("Enter NEW First Name: ");
        member.setFirstName(firstName);
        FileHandler.updateMember(member);
        System.out.println("\n✅\033[1;92m UPDATE FIRST NAME SUCCESSFUL\033[0m\n");
        System.out.printf("\033[1;96mNew First Name:\033[0m \033[1;92m%s\033[0m\n\n", member.getFirstName());
    }

    default void updateLastName(Member member) {
        String lastName = InputUtil.getString("Enter NEW Last Name: ");
        member.setLastName(lastName);
        FileHandler.updateMember(member);
        System.out.println("\n✅\033[1;92m UPDATE LAST NAME SUCCESSFUL\033[0m\n");
        System.out.printf("\033[1;96mNew Last Name:\033[0m \033[1;92m%s\033[0m\n\n", member.getLastName());
    }

    default void updateBirthdate(Member member) {
        LocalDate birthDate = InputUtil.getDate("Enter NEW Birthdate (YYYY-MM-DD): ", 14);
        member.setBirthdate(birthDate);
        FileHandler.updateMember(member);
        System.out.println("\n✅\033[1;92m UPDATE BIRTHDATE SUCCESSFUL\033[0m\n");
        System.out.printf("\033[1;96mNew Birthdate:\033[0m \033[1;92m%s\033[0m\n\n", member.getBirthdate());
    }

    default void updateGender(Member member) {
        int gender = InputUtil.getInt("Enter NEW Gender\n(0 - MALE, 1 - FEMALE, 2 - PREFER NOT TO SAY): ", 0, 2);
        Gender genderEnum = Gender.values()[gender];
        member.setGender(genderEnum);
        FileHandler.updateMember(member);
        System.out.println("\n✅\033[1;92m UPDATE GENDER SUCCESSFUL\033[0m\n");
        System.out.printf("\033[1;96mNew Gender:\033[0m \033[1;92m%s\033[0m\n\n", member.getGender());
    }

    default void updateContactNumber(Member member) {
        String contactNumber = InputUtil.getPhoneNumber("Enter NEW Contact Number\n");
        member.setContactNumber(contactNumber);
        FileHandler.updateMember(member);
        System.out.println("\n✅\033[1;92m UPDATE CONTACT NUMBER SUCCESSFUL\033[0m\n");
        System.out.printf("\033[1;96mNew Contact Number:\033[0m \033[1;92m%s\033[0m\n\n", member.getContactNumber());
    }

    default void updateEmailAddress(Member member) {
        String emailAddress = InputUtil.getString("Enter NEW Email Address: ");
        member.setEmailAddress(emailAddress);
        FileHandler.updateMember(member);
        System.out.println("\n✅\033[1;92m UPDATE EMAIL ADDRESS SUCCESSFUL\033[0m\n");
        System.out.printf("\033[1;96mNew Email Address:\033[0m \033[1;92m%s\033[0m\n\n", member.getEmailAddress());
    }

    default void updateAddress(Member member) {
        String address = InputUtil.getInputAddress("Enter NEW Address: ");
        member.setAddress(address);
        FileHandler.updateMember(member);
        System.out.println("\n✅\033[1;92m UPDATE ADDRESS SUCCESSFUL\033[0m\n");  // FIXED: Was showing "EMAIL ADDRESS"
        System.out.printf("\033[1;96mNew Address:\033[0m \033[1;92m%s\033[0m\n\n", member.getAddress());
    }

    default void updateAllInformation(Member member) {

        Member tempMember = new Member();

        String firstName = InputUtil.getString("Enter NEW First Name: ");
        tempMember.setFirstName(firstName);

        String lastName = InputUtil.getString("Enter NEW Last Name: ");
        tempMember.setLastName(lastName);

        LocalDate birthDate = InputUtil.getDate("Enter NEW Birthdate (YYYY-MM-DD): ", 14);
        tempMember.setBirthdate(birthDate);

        int gender = InputUtil.getInt("Enter NEW Gender\n(0 - MALE, 1 - FEMALE, 2 - PREFER NOT TO SAY): ", 0, 2);
        Gender genderEnum = Gender.values()[gender];
        tempMember.setGender(genderEnum);

        String contactNumber = InputUtil.getPhoneNumber("Enter NEW Contact Number\n");
        tempMember.setContactNumber(contactNumber);

        String emailAddress = InputUtil.getString("Enter NEW Email Address: ");
        tempMember.setEmailAddress(emailAddress);

        String address = InputUtil.getInputAddress("Enter NEW Address: ");
        tempMember.setAddress(address);

        DisplayUtil.displayRegistrationSummaryHeader("UPDATE SUMMARY", tempMember);

        boolean confirmUpdate = InputUtil.getYesNo("Confirm?");

        if (confirmUpdate) {
            member.setFirstName(tempMember.getFirstName());
            member.setLastName(tempMember.getLastName());
            member.setBirthdate(tempMember.getBirthdate());
            member.setGender(tempMember.getGender());
            member.setContactNumber(tempMember.getContactNumber());
            member.setEmailAddress(tempMember.getEmailAddress());
            member.setAddress(tempMember.getAddress());

            FileHandler.updateMember(member);
            System.out.println("\n✅\033[1;92m  ALL INFORMATION UPDATED SUCCESSFULLY\033[0m\n");
        } else {
            System.out.println("❌ \033[1;91mUpdate information cancelled.\033[0m\n");
        }

    }
}
