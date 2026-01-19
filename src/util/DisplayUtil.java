package util;

import model.Member;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class DisplayUtil {

    // Moved this constant to the top for reuse
    private static final String HEADER_LINE = "\033[1;93m════════════════════════════════════════════════════════════════════\033[0m";

    public static void mainMenuDisplay() {
        System.out.printf("""
        \033[1;96m
        ╔══════════════════════════════════════════════════════════════════╗
        ║                  IRON BEAST GYM MANAGEMENT SYSTEM                ║
        ╚══════════════════════════════════════════════════════════════════╝\033[0m
        
        %s
        \033[1;93m                 MAIN MENU - Choose an option:\033[0m
        %s
        
        \033[1;93m      \t\t[1]\033[0m  \033[1;97m\tRegister New Member\033[0m
        \033[1;93m      \t\t[2]\033[0m  \033[1;97m\tShow Active Members\033[0m
        \033[1;93m      \t\t[3]\033[0m  \033[1;97m\tShow Inactive Members\033[0m
        \033[1;93m      \t\t[4]\033[0m  \033[1;97m\tRenew Membership\033[0m
        \033[1;93m      \t\t[5]\033[0m  \033[1;97m\tUpdate Member Information\033[0m
        \033[1;93m      \t\t[6]\033[0m  \033[1;97m\tDelete Member\033[0m
        \033[1;93m      \t\t[7]\033[0m  \033[1;97m\tMember Lookup\033[0m
        \033[1;31m      \t\t[99]\033[0m  \033[1;97m\tExit Program\033[0m
        
        %s
        Enter your choice (1-7, 99):\s""", HEADER_LINE, HEADER_LINE, HEADER_LINE);
    }

    public static void handleEmptyList(Scanner scan, String context) {
        System.out.println("\n❌ \033[1;91mEMPTY LIST. " + context + "\n\033[0m");

        boolean stayInView = InputValidator.getYesNo(scan, "Return to main menu?");
        if (!stayInView) {
            System.out.println("Thank you for using this application!\n");
            System.exit(0);
        }
    }

    // Generic header display method to avoid duplication
    public static void displayHeader(String title) {
        System.out.printf("""
            %s
            \033[1;93m                         %s\033[0m
            %s
            """, HEADER_LINE, title, HEADER_LINE);
    }

    // Consolidated member details display
    private static void displayMemberDetails(Member member) {
        System.out.printf("""
                        \033[1;96mName:           %s %s\033[0m
                        \033[1;96mBirthdate:      %s (Age: %d)\033[0m
                        \033[1;96mMembership:     %s\033[0m
                        \033[1;96mGender:         %s\033[0m
                        \033[1;96mContact:        %s\033[0m
                        \033[1;96mEmail:          %s\033[0m
                        \033[1;96mAddress:        %s\033[0m
                """,
                member.getFirstName(), member.getLastName(),
                member.getBirthdate(),
                Period.between(member.getBirthdate(), LocalDate.now()).getYears(),
                member.getMembershipType(),
                member.getGender(), member.getContactNumber(),
                member.getEmailAddress(), member.getAddress()
        );
        System.out.println(HEADER_LINE);
    }
    public static void displayPotentialDuplicates(String title, List<Member> potentialDuplicates) {
        displayHeader(title);

        potentialDuplicates.forEach(dup ->
                System.out.printf("\033[1;96m\t\t• %-23s (ID: %d | Phone: %s)\033[0m%n",
                        dup.getFullName(), dup.getMemberId(), dup.getContactNumber())
        );
    }

    // Consolidated table header display
    public static void displayTableHeader(String title, String... headers) {
        displayHeader(title);
        System.out.printf("%-5s %-25s %-10s %-15s %-10s%n", (Object[]) headers);
        System.out.println(HEADER_LINE);
    }

    // display util for registerMemberHandle
    public static void displayRegisterNewMemberHeader(String title) {
        displayHeader(title);

        System.out.println("""
            
            \033[1;96m───────────────── Membership Options ─────────────────\033[0m
            
            \033[1;93m● Basic:   \033[0m\033[1;97mAccess to basic facilities\033[0m
                                     ₱500/month
            
            \033[1;93m● Premium: \033[0m\033[1;97mIncludes classes and pool access\033[0m
                                     ₱1,000/month
            
            \033[1;93m● VIP:     \033[0m\033[1;97mAll facilities + personal trainer\033[0m
                                     ₱2,000/month
            """);
    }
    public static void displayRegistrationSummaryHeader(String title, Member newMember) {
        displayHeader(title);
        displayMemberDetails(newMember);
    }

    // display util for activeMemberHandle
    public static void displayActiveMembersHeader(String title) {
        displayTableHeader(title, "ID", "NAME", "TYPE", "EXPIRATION", "STATUS");
    }

    // display util for InactiveMemberHandle
    public static void displayInactiveMembersHeader(String title) {
        displayTableHeader(title, "ID", "NAME", "TYPE", "EXPIRED", "DAYS EXPIRED");
    }

    // display util for renewMembershipHandle
    public static void displayRenewMembershipHeader(String title) {
        displayHeader(title);
    }
    public static void displayCurrentMembershipAndMenu(String title, Member member) {
        displayHeader(title);
        displayMemberSummary(member);
        displayRenewalOptions();
    }
    private static void displayMemberSummary(Member member) {
        System.out.printf("\033[1;93mName:     \033[0m%s %s%n",
                member.getFirstName().toUpperCase(), member.getLastName().toUpperCase());
        System.out.printf("\033[1;93mPlan:     \033[0m%s%n", member.getMembershipType());
        System.out.printf("\033[1;93mEnd Date: \033[0m%s%n", member.getMembershipEndDate());

        if (member.isActive()) {
            long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), member.getMembershipEndDate());
            System.out.printf("\033[1;93mStatus:   \033[0mACTIVE (%d days remaining)%n", daysLeft);
        } else {
            long daysExpired = ChronoUnit.DAYS.between(member.getMembershipEndDate(), LocalDate.now());
            System.out.printf("\033[1;91mStatus:   \033[0mEXPIRED (%d days ago)%n", daysExpired);
        }
    }
    private static void displayRenewalOptions() {
        System.out.println("""
        
        \033[1;96mRenew for how many months?\033[0m
        
        \033[1;92m   1. 1 Month\033[0m
        \033[1;92m   2. 3 Months\033[0m
        \033[1;92m   3. 6 Months\033[0m
        \033[1;92m   4. 12 Months\033[0m
        """);
    }

    // display util for updateMemberInformation
    public static void displayUpdateMemberInformationHeader(String title) {
        displayHeader(title);
    }
    public static void displayUpdateMemberInformationMenu() {
        System.out.printf("""
            %s
            \033[1;93m                  CHOOSE INFORMATION TO UPDATE:\033[0m
            %s
            
            \033[1;93m      \t\t[1]\033[0m  \033[1;97m\tFirst Name\033[0m
            \033[1;93m      \t\t[2]\033[0m  \033[1;97m\tLast Name\033[0m
            \033[1;93m      \t\t[3]\033[0m  \033[1;97m\tBirthdate\033[0m
            \033[1;93m      \t\t[4]\033[0m  \033[1;97m\tGender\033[0m
            \033[1;93m      \t\t[5]\033[0m  \033[1;97m\tContact Number\033[0m
            \033[1;93m      \t\t[6]\033[0m  \033[1;97m\tEmail Address\033[0m
            \033[1;93m      \t\t[7]\033[0m  \033[1;97m\tAddress\033[0m
            \033[1;93m      \t\t[8]\033[0m  \033[1;97m\tAll\033[0m
            \033[1;93m      \t\t[9]\033[0m  \033[1;97m\tReturn to main menu\033[0m
            """, HEADER_LINE, HEADER_LINE);
    }
    public static void displayFullMemberInformation(Member member) {
        System.out.printf("""
        %s
        \033[1;93m                      MEMBER FULL DETAILS\033[0m
        %s
        
                         \033[1;96mName:           %s %s\033[0m
                         \033[1;96mBirthdate:      %s (Age: %d)\033[0m
                         \033[1;96mMembership:     %s\033[0m
                         \033[1;96mGender:         %s\033[0m
                         \033[1;96mContact:        %s\033[0m
                         \033[1;96mEmail:          %s\033[0m
                         \033[1;96mAddress:        %s\033[0m
                         \033[1;96mPlan:\033[0m           \033[1;93m%s\033[0m
                         \033[1;96mEnd Date:\033[0m       \033[1;93m%s\033[0m
        """, HEADER_LINE, HEADER_LINE,
                member.getFirstName().toUpperCase(), member.getLastName().toUpperCase(),
                member.getBirthdate(),
                Period.between(member.getBirthdate(), LocalDate.now()).getYears(),
                member.getMembershipType(),
                member.getGender(), member.getContactNumber(),
                member.getEmailAddress(), member.getAddress(),
                member.getMembershipType(), member.getMembershipEndDate()
        );

        displayMemberStatus(member);
    }
    private static void displayMemberStatus(Member member) {
        if (member.isActive()) {
            long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), member.getMembershipEndDate());
            System.out.printf("\n\033[1;96m                 STATUS:   \033[0m\033[1;92mACTIVE (%d days remaining)%n\033[0m", daysLeft);
        } else {
            long daysExpired = ChronoUnit.DAYS.between(member.getMembershipEndDate(), LocalDate.now());
            System.out.printf("\n\033[1;96m                 STATUS:   \033[0m\033[1;91mEXPIRED (%d days ago)%n\033[0m", daysExpired);
        }
    }


}