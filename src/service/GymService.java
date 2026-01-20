package service;

import enumeration.Gender;
import enumeration.MembershipType;
import model.Member;
import storage.FileHandler;
import util.DisplayUtil;
import util.InputValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class GymService extends BaseGymService{

    @Override
    public void registerMember(Scanner scan){

        boolean registerAnother;

        do{
            DisplayUtil.displayRegisterNewMemberHeader("NEW MEMBER ENROLLMENT");

            // Membership type input
            int memberType = InputValidator.validateIntInRange(scan, "\nEnter membership type (0 - Basic, 1 - Premium, 2 - VIP): ", 0, 2);
            MembershipType membershipType = MembershipType.values()[memberType];

            // First name input
            String firstName = InputValidator.validateNonEmptyString(scan, "Enter First Name: ");

            // Last name input
            String lastName = InputValidator.validateNonEmptyString(scan, "Enter Last Name: ");

            // Email address input
            String emailAddress = InputValidator.validateNonEmptyString(scan, "Enter Email Address: ");

            // Birthdate input, below 14 years old are not allowed in the gym.
            LocalDate birthDate = InputValidator.getDate(scan, "Enter Birthdate (YYYY-MM-DD): ", 14);

            // Gender input
            int gender = InputValidator.validateIntInRange(scan, "Enter Gender\n(0 - MALE, 1 - FEMALE, 2 - PREFER NOT TO SAY): ", 0, 2);
            Gender genderEnum = Gender.values()[gender];

            // Contact number input, PH format
            String contactNumber = InputValidator.validateContactNumber(scan, "Enter Contact Number\n");

            // Address input
            String address = InputValidator.validateAddress(scan, "Enter Address: ");

            Member newMember = new Member(
                    firstName, lastName, emailAddress,
                    membershipType, birthDate, genderEnum,
                    contactNumber, address, LocalDate.now()
            );

            DisplayUtil.displayRegistrationSummaryHeader("REGISTRATION SUMMARY", newMember);

            boolean confirmRegistration = InputValidator.getYesNo(scan, "Confirm?");

            if (confirmRegistration) {

                // Find potential duplicates using lambda
                List<Member> potentialDuplicates = FileHandler.getAllMembers().stream()
                        .filter(existing -> isPotentialDuplicate(existing, newMember))
                        .toList();

                if (!potentialDuplicates.isEmpty()) {

                    DisplayUtil.displayPotentialDuplicates("⚠️ POTENTIAL DUPLICATES FOUND", potentialDuplicates);

                    boolean furtherConfirmationRegistration = InputValidator.getYesNo(scan, "\nRegister anyway?");
                    if (!furtherConfirmationRegistration) {
                        System.out.println("❌ \033[1;91mRegistration cancelled.\033[0m\n");
                        return;
                    }
                }

                int memberId = FileHandler.addMember(newMember);
                System.out.println("\n✅ REGISTRATION SUCCESSFUL! MEMBER ID: " + memberId + "\n");
            } else {
                System.out.println("❌ \033[1;91mRegistration cancelled.\033[0m\n");
            }
            registerAnother = InputValidator.getYesNo(scan, "\033[1;96mRegister another member?\033[0m");
            if (!registerAnother){
                return;
            }
        }while (true);
    }
    private String normalizePhone(String phone) {
        return phone != null ? phone.replaceAll("[^0-9]", "") : "";
    }
    private boolean isPotentialDuplicate(Member existing, Member newMember) {
        // Use lambda-friendly single expression
        return normalizePhone(existing.getContactNumber())
                .equals(normalizePhone(newMember.getContactNumber())) ||
                (existing.getFirstName().equalsIgnoreCase(newMember.getFirstName()) &&
                        existing.getLastName().equalsIgnoreCase(newMember.getLastName()) &&
                        existing.getBirthdate().equals(newMember.getBirthdate()));
    }

    @Override
    public void renewMembership(Scanner scan) {

        if (FileHandler.getAllMembers().isEmpty()) {
            DisplayUtil.handleEmptyList(scan, "NOTHING TO RENEW.");
            return;
        }

        DisplayUtil.displayRenewMembershipHeader("MEMBERSHIP RENEWAL/CHANGE");

        // Find member by ID to renew
        int memberId = InputValidator.validateIntInRange(scan,
                "\nEnter Member ID to renew: ", 1, Integer.MAX_VALUE);

        Member memberToRenew = FileHandler.getMemberById(memberId);

        if (memberToRenew == null) {
            System.out.println("❌ Member not found. Use MEMBER LOOKUP to search for member IDs.");
            return;
        }

        // Display current membership status
        DisplayUtil.displayCurrentMembershipAndMenu("CURRENT MEMBERSHIP", memberToRenew);

        // Ask if they want to change membership type
        boolean wantsToChangeType = InputValidator.getYesNo(scan, "\n\033[1;96mChange membership type?");

        MembershipType newMembershipType = memberToRenew.getMembershipType(); // Default to current
        boolean typeChanged = false;

        if (wantsToChangeType) {
            DisplayUtil.displayMembershipTypeOptions();
            int typeChoice = InputValidator.validateIntInRange(scan,
                    "\nEnter choice (1-3): ", 1, 3);

            typeChanged = true;
            switch (typeChoice) {
                case 1 -> newMembershipType = MembershipType.BASIC;
                case 2 -> newMembershipType = MembershipType.PREMIUM;
                case 3 -> newMembershipType = MembershipType.VIP;
            }
            DisplayUtil.displayTypeChangeConfirmation(memberToRenew.getMembershipType(), newMembershipType);
        }

        DisplayUtil.displayRenewalDurationMenu();

        int durationChoice = InputValidator.validateIntInRange(scan,"\033[1;96mEnter (1-5): \033[0m", 1, 5);

        int[] monthsArray = {1, 3, 6, 12, 0};
        int months = monthsArray[durationChoice - 1];

        // Calculate new end date
        LocalDate newEndDate;
        if (memberToRenew.isActive()) {
            newEndDate = memberToRenew.getMembershipEndDate().plusMonths(months);
        } else {
            newEndDate = LocalDate.now().plusMonths(months);
        }

        // Display summary using new display method
        DisplayUtil.displayRenewalSummary(memberToRenew, newMembershipType, months, newEndDate, typeChanged);

        boolean confirm = InputValidator.getYesNo(scan, "Confirm renewal?");

        if (confirm) {
            // Apply membership type change if requested
            if (typeChanged) {
                memberToRenew.setMembershipType(newMembershipType);
            }

            // Update end date
            memberToRenew.setMembershipEndDate(newEndDate);

            // Save changes to FileHandler
            FileHandler.updateMember(memberToRenew);

            System.out.println("\n✅ Membership updated successfully!");

            if (typeChanged) {
                System.out.printf("New membership type: \033[1;92m%s\033[0m%n", newMembershipType);
            }
            System.out.printf("New expiration date: \033[1;92m%s\033[0m%n%n", newEndDate);
        } else {
            System.out.println("❌ \033[1;91mChanges cancelled.\033[0m");
        }
    }

    @Override
    public void updateMemberInformation(Scanner scan){

        boolean updateAnother;

        if (!FileHandler.hasMembers()) {
            DisplayUtil.handleEmptyList(scan, "NOTHING TO UPDATE.");
            return;
        }

        do {
            DisplayUtil.displayUpdateMemberInformationHeader("UPDATE MEMBER INFORMATION");

            int memberId = InputValidator.validateIntInRange(scan,
                    "\nEnter Member ID to update information: ", 1, Integer.MAX_VALUE);

            Member memberToUpdateInformation = FileHandler.getMemberById(memberId);

            if (memberToUpdateInformation == null) {
                System.out.println("❌ \033[1;91mMEMBER NOT FOUND.\033[0m Use MEMBER LOOKUP to search for member IDs.");
                return;
            }

            DisplayUtil.displayFullMemberInformation(memberToUpdateInformation);
            DisplayUtil.displayUpdateMemberInformationMenu();

            int choice = InputValidator.validateIntInRange(scan, "Enter your choice (1-9): ", 1, 9);

            updateAnother = handleUpdateChoice(choice, memberToUpdateInformation, scan);
            if (!updateAnother){
                return;
            }
        } while (true);
    }

    private boolean handleUpdateChoice(int choice, Member member, Scanner scan) {
        switch (choice) {
            case 1 -> updateFirstName(member, scan);
            case 2 -> updateLastName(member, scan);
            case 3 -> updateBirthdate(member, scan);
            case 4 -> updateGender(member, scan);
            case 5 -> updateContactNumber(member, scan);
            case 6 -> updateEmailAddress(member, scan);
            case 7 -> updateAddress(member, scan);
            case 8 -> updateAllInformation(member, scan);
            case 9 -> {
                return false;
            }
        }

        return InputValidator.getYesNo(scan, "\033[1;96mUpdate another information?\033[0m");
    }
    private void updateFirstName(Member member, Scanner scan) {
        String firstName = InputValidator.validateNonEmptyString(scan,
                "Enter NEW First Name: ");
        member.setFirstName(firstName);
        FileHandler.updateMember(member);
        System.out.println("\n✅\033[1;92m UPDATE FIRST NAME SUCCESSFUL\033[0m\n");
        System.out.printf("\033[1;96mNew First Name:\033[0m \033[1;92m%s\033[0m\n\n", member.getFirstName());
    }
    private void updateLastName(Member member, Scanner scan) {
        String lastName = InputValidator.validateNonEmptyString(scan,
                "Enter NEW Last Name: ");
        member.setLastName(lastName);
        FileHandler.updateMember(member);
        System.out.println("\n✅\033[1;92m UPDATE LAST NAME SUCCESSFUL\033[0m\n");
        System.out.printf("\033[1;96mNew Last Name:\033[0m \033[1;92m%s\033[0m\n\n", member.getLastName());
    }
    private void updateBirthdate(Member member, Scanner scan) {
        LocalDate birthDate = InputValidator.getDate(scan,
                "Enter NEW Birthdate (YYYY-MM-DD): ", 14);
        member.setBirthdate(birthDate);
        FileHandler.updateMember(member);
        System.out.println("\n✅\033[1;92m UPDATE BIRTHDATE SUCCESSFUL\033[0m\n");
        System.out.printf("\033[1;96mNew Birthdate:\033[0m \033[1;92m%s\033[0m\n\n", member.getBirthdate());
    }
    private void updateGender(Member member, Scanner scan) {
        int gender = InputValidator.validateIntInRange(scan,
                "Enter NEW Gender\n(0 - MALE, 1 - FEMALE, 2 - PREFER NOT TO SAY): ", 0, 2);
        Gender genderEnum = Gender.values()[gender];
        member.setGender(genderEnum);
        FileHandler.updateMember(member);
        System.out.println("\n✅\033[1;92m UPDATE GENDER SUCCESSFUL\033[0m\n");
        System.out.printf("\033[1;96mNew Gender:\033[0m \033[1;92m%s\033[0m\n\n", member.getGender());
    }
    private void updateContactNumber(Member member, Scanner scan) {
        String contactNumber = InputValidator.validateContactNumber(scan,
                "Enter NEW Contact Number\n");
        member.setContactNumber(contactNumber);
        FileHandler.updateMember(member);
        System.out.println("\n✅\033[1;92m UPDATE CONTACT NUMBER SUCCESSFUL\033[0m\n");
        System.out.printf("\033[1;96mNew Contact Number:\033[0m \033[1;92m%s\033[0m\n\n", member.getContactNumber());
    }
    private void updateEmailAddress(Member member, Scanner scan) {
        String emailAddress = InputValidator.validateNonEmptyString(scan,
                "Enter NEW Email Address: ");
        member.setEmailAddress(emailAddress);
        FileHandler.updateMember(member);
        System.out.println("\n✅\033[1;92m UPDATE EMAIL ADDRESS SUCCESSFUL\033[0m\n");
        System.out.printf("\033[1;96mNew Email Address:\033[0m \033[1;92m%s\033[0m\n\n", member.getEmailAddress());
    }
    private void updateAddress(Member member, Scanner scan) {
        String address = InputValidator.validateAddress(scan, "Enter NEW Address: ");
        member.setAddress(address);
        FileHandler.updateMember(member);
        System.out.println("\n✅\033[1;92m UPDATE ADDRESS SUCCESSFUL\033[0m\n");  // FIXED: Was showing "EMAIL ADDRESS"
        System.out.printf("\033[1;96mNew Address:\033[0m \033[1;92m%s\033[0m\n\n", member.getAddress());
    }
    private void updateAllInformation(Member member, Scanner scan) {

        Member tempMember = new Member();

        String firstName = InputValidator
                .validateNonEmptyString(scan, "Enter NEW First Name: ");
        tempMember.setFirstName(firstName);

        String lastName = InputValidator
                .validateNonEmptyString(scan, "Enter NEW Last Name: ");
        tempMember.setLastName(lastName);

        LocalDate birthDate = InputValidator
                .getDate(scan, "Enter NEW Birthdate (YYYY-MM-DD): ", 14);
        tempMember.setBirthdate(birthDate);

        int gender = InputValidator
                .validateIntInRange(scan, "Enter NEW Gender\n(0 - MALE, 1 - FEMALE, 2 - PREFER NOT TO SAY): ", 0, 2);
        Gender genderEnum = Gender.values()[gender];
        tempMember.setGender(genderEnum);

        String contactNumber = InputValidator
                .validateContactNumber(scan, "Enter NEW Contact Number\n");
        tempMember.setContactNumber(contactNumber);

        String emailAddress = InputValidator
                .validateNonEmptyString(scan, "Enter NEW Email Address: ");
        tempMember.setEmailAddress(emailAddress);

        String address = InputValidator.validateAddress(scan, "Enter NEW Address: ");
        tempMember.setAddress(address);

        DisplayUtil.displayRegistrationSummaryHeader("UPDATE SUMMARY", tempMember);

        boolean confirmUpdate = InputValidator.getYesNo(scan, "Confirm?");

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

    @Override
    public void deleteMember(Scanner scan){

        if (!FileHandler.hasMembers()) {
            DisplayUtil.handleEmptyList(scan, "NOTHING TO RENEW.");
            return;
        }

        boolean deleteAnother;
        boolean confirmDelete;
        do{
            DisplayUtil.displayDeleteMemberHeader("DELETE MEMBER");
            int memberId = InputValidator.validateIntInRange(scan,
                    "\nEnter Member ID to delete: ", 1, Integer.MAX_VALUE);

            Member memberToBeDeleted = FileHandler.getMemberById(memberId);

            if (memberToBeDeleted == null) {
                System.out.println("❌ \033[1;91mMEMBER NOT FOUND.\033[0m Use MEMBER LOOKUP to search for member IDs.");
                return;
            }

            DisplayUtil.displayFullMemberInformation(memberToBeDeleted);

            confirmDelete = InputValidator.getYesNo(scan, "\nConfirm deletion?");

            if(confirmDelete){
                FileHandler.deleteMember(memberId);
                System.out.println("\n✅ MEMBER DELETION SUCCESSFUL! \n");
            }else System.out.println("❌ \033[1;91mDeletion cancelled.\033[0m\n");

            deleteAnother = InputValidator.getYesNo(scan, "\033[1;96mDelete another member?\033[0m");
            if (!deleteAnother){
                return;
            }
        }while (true);

    }

    @Override
    public void memberLookupHandle(Scanner scan) {

        while (true) {
            DisplayUtil.displaySearchMenu();

            int choice = InputValidator.validateIntInRange(scan,"Enter choice (1-4): ",1, 4);

            switch (choice) {
                case 1 -> DisplayUtil.displayAllMembers(FileHandler.getAllMembers());
                case 2 -> handleNameSearch(scan);
                case 3 -> handleIdSearch(scan);
                case 4 -> {
                    System.out.println("Returning to main menu...\n");
                    return;
                }
                default -> System.out.println("❌ Invalid choice. Please try again.");
            }

            if(!(InputValidator.getYesNo(scan, "\nSearch again?"))){
                return;
            }

        }
    }
    private void handleNameSearch(Scanner scan) {
        String searchTerm = InputValidator.validateNonEmptyString(scan, "\nEnter name to search: ");

        List<Member> results = FileHandler.searchByName(searchTerm);
        DisplayUtil.displaySearchResults(results, "Name Search: '" + searchTerm + "'");
    }
    private void handleIdSearch(Scanner scan) {

        int memberId = InputValidator.validateIntInRange(scan, "\nEnter Member ID to search: ", 1, Integer.MAX_VALUE);
        Member member = FileHandler.getMemberById(memberId);
        if (member != null) {
            System.out.println("\n\033[1;96m✓ MEMBER FOUND:\033[0m");
            DisplayUtil.displayFullMemberInformation(member);
        } else {
            System.out.println("❌ \033[1;91mMember with ID " + memberId + " not found.\033[0m");
        }

    }
}
