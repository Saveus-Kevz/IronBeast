package service.impl;

import enumeration.Gender;
import enumeration.MembershipType;
import model.Member;
import storage.FileHandler;
import util.DisplayUtil;
import util.InputUtil;

import java.time.LocalDate;
import java.util.List;

public class RegistrationServiceImpl implements service.RegistrationService {

    @Override
    public void registerMember() {
        do {
            Member newMember = collectMemberInformation();
            confirmAndRegister(newMember)       ;

        } while (InputUtil.getYesNo("\033[1;96mRegister another member?\033[0m"));
    }

    private Member collectMemberInformation() {
        DisplayUtil.displayRegisterNewMemberHeader("NEW MEMBER ENROLLMENT");

        int memberType = InputUtil.getInt("\nEnter membership type (0 - Basic, 1 - Premium, 2 - VIP): ", 0, 2);
        MembershipType membershipType = MembershipType.values()[memberType];

        String firstName = InputUtil.getString("Enter First Name: ");
        String lastName = InputUtil.getString("Enter Last Name: ");
        String emailAddress = InputUtil.getString("Enter Email Address: ");
        LocalDate birthDate = InputUtil.getDate("Enter Birthdate (YYYY-MM-DD): ", 14);

        int gender = InputUtil.getInt("Enter Gender\n(0 - MALE, 1 - FEMALE, 2 - PREFER NOT TO SAY): ", 0, 2);
        Gender genderEnum = Gender.values()[gender];

        String contactNumber = InputUtil.getPhoneNumber("Enter Contact Number\n");
        String address = InputUtil.getInputAddress("Enter Address: ");

        return new Member(
                firstName, lastName, emailAddress,
                membershipType, birthDate, genderEnum,
                contactNumber, address, LocalDate.now()
        );
    }

    private void confirmAndRegister(Member newMember) {
        DisplayUtil.displayRegistrationSummaryHeader("REGISTRATION SUMMARY", newMember);

        if (!InputUtil.getYesNo("Confirm?")) {
            System.out.println("❌ \033[1;91mRegistration cancelled.\033[0m\n");
            return;
        }

        List<Member> potentialDuplicates = findPotentialDuplicates(newMember);

        if (!potentialDuplicates.isEmpty()) {
            DisplayUtil.displayPotentialDuplicates("⚠️ POTENTIAL DUPLICATES FOUND", potentialDuplicates);
            if (!InputUtil.getYesNo("\nRegister anyway?")) {
                System.out.println("❌ \033[1;91mRegistration cancelled.\033[0m\n");
                return;
            }
        }

        int memberId = FileHandler.addMember(newMember);
        System.out.println("\n✅ REGISTRATION SUCCESSFUL! MEMBER ID: " + memberId + "\n");
    }

    private List<Member> findPotentialDuplicates(Member newMember) {
        return FileHandler.getAllMembers().stream()
                .filter(existing ->
                        normalizePhone(existing.getContactNumber())
                                .equals(normalizePhone(newMember.getContactNumber())) ||
                                (existing.getFirstName().equalsIgnoreCase(newMember.getFirstName()) &&
                                        existing.getLastName().equalsIgnoreCase(newMember.getLastName()) &&
                                        existing.getBirthdate().equals(newMember.getBirthdate())))
                .toList();
    }

    private String normalizePhone(String phone) {
        return phone != null ? phone.replaceAll("[^0-9]", "") : "";
    }

}