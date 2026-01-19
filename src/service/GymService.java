package service;

import enumeration.Gender;
import enumeration.MembershipType;
import model.Member;
import util.DisplayUtil;
import util.InputValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class GymService extends BaseGymService{

    // Test members
    public GymService(){
        memberList.add(new Member("Kevin", "Flores", "Sample1@gmail.com", MembershipType.BASIC,
                LocalDate.of(1995, 6, 26), Gender.MALE, "09157684654",
                "258 Talumpong Street"));
        memberList.add(new Member("Gi-ann Camille", "Huit", "Sample2@gmail.com", MembershipType.VIP,
                LocalDate.of(1998, 1, 7), Gender.FEMALE, "09157684654",
                "258 Talumpong Street", LocalDate.now().minusMonths(14)));
        memberList.add(new Member("Kianna-louise", "Flores", "Sample3@gmail.com", MembershipType.PREMIUM,
                LocalDate.of(2024, 2, 8), Gender.FEMALE, "09157684654",
                "258 Talumpong Street"));
        memberList.add(new Member("John", "Doe", "Sample4@gmail.com", MembershipType.PREMIUM,
                LocalDate.of(1985, 5, 10),
                Gender.MALE, "09159159152", "123 Test Street",
                LocalDate.now().minusMonths(14)
        ));
    }

    @Override
    public void registerMemberHandle(Scanner scan){

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
            int gender = InputValidator.validateIntInRange(scan, "Enter Gender (0 - MALE, 1 - FEMALE, 2 - PREFER NOT TO SAY): ", 0, 2);
            Gender genderEnum = Gender.values()[gender];

            // Contact number input, PH format
            String contactNumber = InputValidator.validateContactNumber(scan, "Enter Contact Number");

            // Address input
            String address = InputValidator.validateAddress(scan, "Enter Address: ");

            Member newMember = new Member(firstName, lastName, emailAddress, membershipType,
                    birthDate, genderEnum, contactNumber, address,
                    LocalDate.now());

            DisplayUtil.displayRegistrationSummaryHeader("REGISTRATION SUMMARY", newMember);

            boolean confirmRegistration = InputValidator.getYesNo(scan, "Confirm?");

            if (confirmRegistration) {

                // Find potential duplicates using lambda
                List<Member> potentialDuplicates = memberList.stream()
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

                memberList.add(newMember);
                System.out.println("\n✅ REGISTRATION SUCCESSFUL! MEMBER ID: " + newMember.getMemberId() + "\n");
            } else {
                System.out.println("❌ \033[1;91mRegistration cancelled.\033[0m\n");
            }
            registerAnother = InputValidator.getYesNo(scan, "\033[1;96mRegister another member?\033[0m");
            if (!registerAnother){
                return;
            }
        }while (true);
    }

    @Override
    public void renewMembershipHandle(Scanner scan) {

        if (memberList.isEmpty()) {
            DisplayUtil.handleEmptyList(scan, "NOTHING TO RENEW.");
            return;
        }

        DisplayUtil.displayRenewMembershipHeader("MEMBERSHIP RENEWAL");

        // Find member by ID to renew
        int memberId = InputValidator.validateIntInRange(scan,
                "\nEnter Member ID to renew: ", 1, Integer.MAX_VALUE);

        Member memberToRenew = memberLookupById(memberId);

        if (memberToRenew == null) {
            System.out.println("❌ Member not found. Use MEMBER LOOKUP to search for member IDs.");
            return;
        }

        // Display current membership status
        DisplayUtil.displayCurrentMembershipAndMenu("CURRENT MEMBERSHIP", memberToRenew);

        int durationChoice = InputValidator.validateIntInRange(scan,"\033[1;96mEnter (1-4): \033[0m", 1, 4);

        int[] monthsArray = {1, 3, 6, 12};
        int months = monthsArray[durationChoice - 1];

        // Calculate new end date
        LocalDate newEndDate;
        if (memberToRenew.isActive()) {
            newEndDate = memberToRenew.getMembershipEndDate().plusMonths(months);
        } else {
            newEndDate = LocalDate.now().plusMonths(months);
        }

        // Confirm renewal
        System.out.printf("\n\033[1;96mRenew %s's membership for\033[0m %d months?%n",
                memberToRenew.getFirstName(), months);
        System.out.printf("\033[1;96mNew end date will be:\033[0m %s%n%n", newEndDate);

        boolean confirm = InputValidator.getYesNo(scan, "Confirm renewal?");

        if (confirm) {
            memberToRenew.setMembershipEndDate(newEndDate);
            System.out.println("\n✅ Membership renewed successfully!");
            System.out.printf("New expiration date: %s%n%n", newEndDate);
        } else {
            System.out.println("❌ \033[1;91mRenewal cancelled.\033[0m");
        }
    }

    @Override
    public void updateMemberInformation(Scanner scan){

        boolean updateAnother;

        if (memberList.isEmpty()) {
            DisplayUtil.handleEmptyList(scan, "NOTHING TO UPDATE.");
            return;
        }

        do {
            DisplayUtil.displayUpdateMemberInformationHeader("UPDATE MEMBER INFORMATION");

            int memberId = InputValidator.validateIntInRange(scan,
                    "\nEnter Member ID to update information: ", 1, Integer.MAX_VALUE);

            Member memberToUpdateInformation = memberLookupById(memberId);

            if (memberToUpdateInformation == null) {
                System.out.println("❌ \033[1;91mMEMBER NOT FOUND.\033[0m Use MEMBER LOOKUP to search for member IDs.");
                return;
            }

            DisplayUtil.displayFullMemberInformation(memberToUpdateInformation);

            DisplayUtil.displayUpdateMemberInformationMenu();

            int choice = InputValidator.validateIntInRange(scan,
                    "Enter your choice (1-8): ", 1, 8);

            switch (choice){
                case 1 -> {
                    String firstName = InputValidator.validateNonEmptyString(scan, "Enter NEW First Name (" + memberToUpdateInformation.getFirstName() + "): ");
                    memberToUpdateInformation.setFirstName(firstName);
                    System.out.println("✅ UPDATE FIRST NAME SUCCESSFUL\n");
                    updateAnother = InputValidator.getYesNo(scan, "\033[1;96mUpdate another information?\033[0m");
                    if (!updateAnother){
                        return;
                    }
                }
                case 2 -> {
                    String lastName = InputValidator.validateNonEmptyString(scan, "Enter NEW Last Name (" + memberToUpdateInformation.getLastName() + "): ");
                    memberToUpdateInformation.setLastName(lastName);
                    System.out.println("✅ UPDATE LAST NAME SUCCESSFUL\n");
                    updateAnother = InputValidator.getYesNo(scan, "\033[1;96mUpdate another information?\033[0m");
                    if (!updateAnother){
                        return;
                    }
                }
                case 3 -> {
                    LocalDate birthDate = InputValidator.getDate(scan, "Enter NEW Birthdate (" + memberToUpdateInformation.getBirthdate() + "): ", 14);
                    memberToUpdateInformation.setBirthdate(birthDate);
                    System.out.println("✅ UPDATE BIRTHDATE SUCCESSFUL\n");
                    updateAnother = InputValidator.getYesNo(scan, "\033[1;96mUpdate another information?\033[0m");
                    if (!updateAnother){
                        return;
                    }
                }
                case 4 -> {
                    int gender = InputValidator.validateIntInRange(scan, "Enter NEW Gender (" + memberToUpdateInformation.getGender() +
                            ")\n(0 - MALE, 1 - FEMALE, 2 - PREFER NOT TO SAY): ", 0, 2);
                    Gender genderEnum = Gender.values()[gender];
                    memberToUpdateInformation.setGender(genderEnum);
                    System.out.println("✅ UPDATE GENDER SUCCESSFUL\n");
                    updateAnother = InputValidator.getYesNo(scan, "\033[1;96mUpdate another information?\033[0m");
                    if (!updateAnother){
                        return;
                    }
                }
                case 5 -> {
                    String contactNumber = InputValidator.validateContactNumber(scan, "Enter NEW (" + memberToUpdateInformation.getContactNumber() + ") Contact Number: ");
                    memberToUpdateInformation.setContactNumber(contactNumber);
                    System.out.println("✅ UPDATE CONTACT NUMBER SUCCESSFUL\n");
                    updateAnother = InputValidator.getYesNo(scan, "\033[1;96mUpdate another information?\033[0m");
                    if (!updateAnother){
                        return;
                    }
                }
                case 6 -> {
                    String emailAddress = InputValidator.validateNonEmptyString(scan, "Enter NEW (" + memberToUpdateInformation.getEmailAddress() + ") Email Address: ");
                    memberToUpdateInformation.setEmailAddress(emailAddress);
                    System.out.println("✅ UPDATE EMAIL ADDRESS SUCCESSFUL\n");
                    updateAnother = InputValidator.getYesNo(scan, "\033[1;96mUpdate another information?\033[0m");
                    if (!updateAnother){
                        return;
                    }
                }

                case 7 -> {
                    String address = InputValidator.validateAddress(scan, "Enter NEW Address: ");
                    memberToUpdateInformation.setAddress(address);
                    System.out.println("✅ UPDATE EMAIL ADDRESS SUCCESSFUL\n");
                    System.out.println("New Address: " + memberToUpdateInformation);
                }
            }
        } while (true);
    }

    @Override
    public void deleteMember(){
    }

    @Override
    public void memberLookupHandle(){
        System.out.println("Showing active member list…");
        List<String> personNames = memberList.stream().map(x -> x.getFirstName().toUpperCase() + " " +
                x.getLastName().toUpperCase() + " - " + x.getGender() +
                " - " + x.getBirthdate() + " - " + x.getAddress() +
                " - " + x.getContactNumber()).toList();

        personNames.forEach(System.out::println);
        System.out.println("End of list.\n");
    }

    @Override
    public Member memberLookupById(int id){
        return memberList.stream().filter(x -> x.getMemberId() == id).findFirst().orElse(null);
    }

    // registerMemberHandle supporting method
    private boolean isPotentialDuplicate(Member existing, Member newMember) {
        // Use lambda-friendly single expression
        return normalizePhone(existing.getContactNumber())
                .equals(normalizePhone(newMember.getContactNumber())) ||
                (existing.getFirstName().equalsIgnoreCase(newMember.getFirstName()) &&
                        existing.getLastName().equalsIgnoreCase(newMember.getLastName()) &&
                        existing.getBirthdate().equals(newMember.getBirthdate()));
    }
    // registerMemberHandle supporting method
    private String normalizePhone(String phone) {
        return phone != null ? phone.replaceAll("[^0-9]", "") : "";
    }

}
