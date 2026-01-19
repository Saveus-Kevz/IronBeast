package util;

import model.Member;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class DisplayUtil {

    public static void mainMenuDisplay() {
        System.out.print("""
            \033[1;96m
            ╔══════════════════════════════════════════════════════════════════╗
            ║                  IRON BEAST GYM MANAGEMENT SYSTEM                ║
            ╚══════════════════════════════════════════════════════════════════╝\033[0m
            
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            \033[1;93m                 MAIN MENU - Choose an option:\033[0m
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            
            \033[1;93m      \t\t[1]\033[0m  \033[1;97m\tRegister New Member\033[0m
            \033[1;93m      \t\t[2]\033[0m  \033[1;97m\tShow Active Members\033[0m
            \033[1;93m      \t\t[3]\033[0m  \033[1;97m\tShow Inactive Members\033[0m
            \033[1;93m      \t\t[4]\033[0m  \033[1;97m\tRenew Membership\033[0m
            \033[1;93m      \t\t[5]\033[0m  \033[1;97m\tUpdate Member Information\033[0m
            \033[1;93m      \t\t[6]\033[0m  \033[1;97m\tDelete Member\033[0m
            \033[1;93m      \t\t[7]\033[0m  \033[1;97m\tMember Lookup\033[0m
            \033[1;31m      \t\t[99]\033[0m  \033[1;97m\tExit Program\033[0m
            
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            Enter your choice (1-7, 99):\s""");
    }
    public static void handleEmptyList(Scanner scan, String context) {
        System.out.println("\n❌ \033[1;91mEMPTY LIST. " + context + "\n\033[0m");

        boolean stayInView = InputValidator.getYesNo(scan, "Return to main menu?");
        if (!stayInView) {
            System.out.println("Thank you for using this application!\n");
            System.exit(0);
        }

    }

    // display util for registerMemberHandle
    public static void displayRegisterNewMemberHeader(String title){
        System.out.printf("""
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            \033[1;93m                         %s\033[0m
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            
            \033[1;96m───────────────── Membership Options ─────────────────\033[0m
            
            \033[1;93m● Basic:   \033[0m\033[1;97mAccess to basic facilities\033[0m
                                     ₱500/month
            
            \033[1;93m● Premium: \033[0m\033[1;97mIncludes classes and pool access\033[0m
                                     ₱1,000/month
            
            \033[1;93m● VIP:     \033[0m\033[1;97mAll facilities + personal trainer\033[0m
                                     ₱2,000/month
            """, title);
    }
    public static void displayRegistrationSummaryHeader(String title, Member newMember){
        System.out.printf("""
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            \033[1;93m                         %s\033[0m
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            """, title);

        System.out.printf("""
                        \033[1;96mName:           %s %s\033[0m
                        \033[1;96mBirthdate:      %s (Age: %d)\033[0m
                        \033[1;96mMembership:     %s\033[0m
                        \033[1;96mGender:         %s\033[0m
                        \033[1;96mContact:        %s\033[0m
                        \033[1;96mEmail:          %s\033[0m
                        \033[1;96mAddress:        %s\033[0m
                """,
                newMember.getFirstName(), newMember.getLastName(), newMember.getBirthdate(),
                Period.between(newMember.getBirthdate(), LocalDate.now()).getYears(), newMember.getMembershipType(),
                newMember.getGender(), newMember.getContactNumber(), newMember.getEmailAddress(), newMember.getAddress()
        );
        System.out.println("\033[1;93m════════════════════════════════════════════════════════════════════\033[0m");

    }
    public static void displayPotentialDuplicates(String title, List<Member> potentialDuplicates){
        System.out.printf("""
            
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            \033[1;93m                   %s\033[0m
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            """, title);

        potentialDuplicates.forEach(dup ->
                System.out.printf("\033[1;96m\t\t• %-23s (ID: %d | Phone: %s)\033[0m%n",
                        dup.getFullName(), dup.getMemberId(), dup.getContactNumber())
        );
    }

    // display header util for active & inactive member
    public static void displayActiveMembersHeader(String title) {
        System.out.printf("""
            
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            \033[1;93m                         %s\033[0m
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            "%-5s %-25s %-10s %-15s %-10s
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            """, title, "ID", "NAME", "TYPE", "EXPIRATION", "STATUS");
    }
    public static void displayInactiveMembersHeader(String title) {
        System.out.printf("""
            
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            \033[1;93m                         %s\033[0m
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            %-5s %-25s %-10s %-12s %-15s
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            """, title, "ID", "NAME", "TYPE", "EXPIRED", "DAYS EXPIRED");
    }

    // display util for renewMembershipHandle
    public static void displayRenewMembershipHeader(String title){
        System.out.printf("""
            
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            \033[1;93m                          %s\033[0m
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m""", title);
    }
    public static void displayCurrentMembershipAndMenu(String title, Member member){
        System.out.printf("""
                
                \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
                \033[1;93m                         %s\033[0m
                \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
                """, title);

        System.out.printf("\033[1;93mName:     \033[0m%s %s%n", member.getFirstName().toUpperCase(), member.getLastName().toUpperCase());
        System.out.printf("\033[1;93mPlan:     \033[0m%s%n", member.getMembershipType());
        System.out.printf("\033[1;93mEnd Date: \033[0m%s%n", member.getMembershipEndDate());

        if (member.isActive()) {
            long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), member.getMembershipEndDate());
            System.out.printf("\033[1;93mStatus:   \033[0mACTIVE (%d days remaining)%n", daysLeft);
        } else {
            long daysExpired = ChronoUnit.DAYS.between(member.getMembershipEndDate(), LocalDate.now());
            System.out.printf("\033[1;91mStatus:   \033[0mEXPIRED (%d days ago)%n", daysExpired);
        }

        System.out.println("""
        
        \033[1;96mRenew for how many months?\033[0m
        
        \033[1;92m   1. 1 Month\033[0m
        \033[1;92m   2. 3 Months\033[0m
        \033[1;92m   3. 6 Months\033[0m
        \033[1;92m   4. 12 Months\033[0m
        """);
    }

    public static void displayUpdateMemberInformationHeader(String title){
        System.out.printf("""
            
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            \033[1;93m                    %s\033[0m
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m""", title);
    }
    public static void displayUpdateMemberInformationMenu(){
        System.out.println("""
            
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            \033[1;93m                  CHOOSE INFORMATION TO UPDATE:\033[0m
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            
            \033[1;93m      \t\t[1]\033[0m  \033[1;97m\tFirst Name\033[0m
            \033[1;93m      \t\t[2]\033[0m  \033[1;97m\tLast Name\033[0m
            \033[1;93m      \t\t[3]\033[0m  \033[1;97m\tBirthdate\033[0m
            \033[1;93m      \t\t[4]\033[0m  \033[1;97m\tGender\033[0m
            \033[1;93m      \t\t[5]\033[0m  \033[1;97m\tContact Number\033[0m
            \033[1;93m      \t\t[6]\033[0m  \033[1;97m\tEmail Address\033[0m
            \033[1;93m      \t\t[7]\033[0m  \033[1;97m\tAddress\033[0m
            \033[1;93m      \t\t[8]\033[0m  \033[1;97m\tAll\033[0m
            """);
    }

    public static void displayFullMemberInformation(Member member){

        System.out.printf("""
        \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
        \033[1;93m                      MEMBER FULL DETAILS\033[0m
        \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
        
                         \033[1;96mName:           %s %s\033[0m
                         \033[1;96mBirthdate:      %s (Age: %d)\033[0m
                         \033[1;96mMembership:     %s\033[0m
                         \033[1;96mGender:         %s\033[0m
                         \033[1;96mContact:        %s\033[0m
                         \033[1;96mEmail:          %s\033[0m
                         \033[1;96mAddress:        %s\033[0m
                         \033[1;96mPlan:\033[0m           \033[1;93m%s\033[0m
                         \033[1;96mEnd Date:\033[0m       \033[1;93m%s\033[0m
        """,
                member.getFirstName().toUpperCase(), member.getLastName().toUpperCase(), member.getBirthdate(),
                Period.between(member.getBirthdate(), LocalDate.now()).getYears(), member.getMembershipType(),
                member.getGender(), member.getContactNumber(), member.getEmailAddress(), member.getAddress(),

                member.getMembershipType(),
                member.getMembershipEndDate()
        );

        if (member.isActive()) {
            long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), member.getMembershipEndDate());
            System.out.printf("\n\033[1;96m                 STATUS:   \033[0m\033[1;92mACTIVE (%d days remaining)%n\033[0m", daysLeft);
        } else {
            long daysExpired = ChronoUnit.DAYS.between(member.getMembershipEndDate(), LocalDate.now());
            System.out.printf("\n\033[1;96m                 STATUS:   \033[0m\033[1;91mEXPIRED (%d days ago)%n\033[0m", daysExpired);
        }

    }

}
