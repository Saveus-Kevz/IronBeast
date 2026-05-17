package util;

import enumeration.MembershipType;
import model.Member;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DisplayUtil {

    public static void mainMenuDisplay() {
        System.out.print("""
            \033[1;96m
            ╔══════════════════════════════════════════════════════════════════╗
            ║                  IRON BEAST GYM MANAGEMENT SYSTEM                ║
            ╚══════════════════════════════════════════════════════════════════╝\033[0m
            
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            \033[1;93m                   MAIN MENU - Choose an option:\033[0m
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
            """);
    }
    public static void handleEmptyList(String context) {
        System.out.println("\n❌ \033[1;91mEMPTY LIST. " + context + "\n\033[0m");

        boolean stayInView = InputUtil.getYesNo("Return to main menu?");
        if (!stayInView) {
            System.out.println("Thank you for using this application!\n");
            System.exit(0);
        }

    }

    // display util for registerMemberHandle
    public static void displayRegisterNewMemberHeader(String title){
        System.out.printf("""
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            \033[1;93m                        %s\033[0m
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            
            \033[1;96m       ───────────────── Membership Options ─────────────────\033[0m
            
            \033[1;93m          ● Basic:   \033[0m\033[1;97mAccess to basic facilities\033[0m
                                           ₱500/month
            
            \033[1;93m          ● Premium: \033[0m\033[1;97mIncludes Classes + Pool Access\033[0m
                                           ₱1,000/month
            
            \033[1;93m          ● VIP:     \033[0m\033[1;97mAll Facilities + Personal Trainer\033[0m
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

    // display util for activeMemberHandle
    public static void displayActiveMembersHeader(String title) {
        System.out.printf("""
            
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            \033[1;93m                         %s\033[0m
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            %-5s %-25s %-10s %-15s %-10s
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            """, title, "ID", "NAME", "TYPE", "EXPIRATION", "STATUS");
    }

    // display util for InactiveMemberHandle
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
            \033[1;93m                      %s\033[0m
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m""", title);
    }
    public static void displayCurrentMembershipAndMenu(String title, Member member) {
        System.out.printf("""
            
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            \033[1;93m                         %s\033[0m
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            """, title);

        System.out.printf("\033[1;96mName:          %s %s%n\033[0m", member.getFirstName().toUpperCase(), member.getLastName().toUpperCase());
        System.out.printf("\033[1;96mPlan:          %s%n\033[0m", member.getMembershipType());
        System.out.printf("\033[1;96mEnd Date:      %s%n\033[0m", member.getMembershipEndDate());

        if (member.isActive()) {
            long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), member.getMembershipEndDate());
            System.out.printf("\033[1;96mStatus:        \033[0m\033[1;92mACTIVE (%d days remaining)%n\033[0m", daysLeft);
        } else {
            long daysExpired = ChronoUnit.DAYS.between(member.getMembershipEndDate(), LocalDate.now());
            System.out.printf("\033[1;96mStatus:        \033[0m\033[1;91mEXPIRED (%d days ago)%n\033[0m", daysExpired);
        }

    }
    public static void displayRenewalDurationMenu() {
        System.out.println("""
        
        \033[1;96mRenew for how many months?\033[0m
        
        \033[1;92m   [1] 1 Month\033[0m
        \033[1;92m   [2] 3 Months\033[0m
        \033[1;92m   [3] 6 Months\033[0m
        \033[1;92m   [4] 12 Months\033[0m
        \033[1;92m   [5] Don't renew\033[0m
        """);
    }
    public static void displayMembershipTypeOptions() {
        System.out.println("""
        
        \033[1;96mSelect new membership type:\033[0m
        
            \033[1;92m1. BASIC\033[0m
            \033[1;92m2. PREMIUM\033[0m
            \033[1;92m3. VIP\033[0m""");
    }
    public static void displayTypeChangeConfirmation(MembershipType currentType, MembershipType newType) {
        System.out.println("\n\033[1;96mMembership type change:\033[0m");
        System.out.println("\n  From: \033[1;91m" + currentType + "\033[0m");
        System.out.println("  To:   \033[1;92m" + newType + "\033[0m");
    }
    public static void displayRenewalSummary(Member member, MembershipType newType, int months, LocalDate newEndDate, boolean typeChanged) {
        System.out.println("\n\033[1;96m════════════════════════════════════════════════════════════════════\033[0m");
        System.out.println("\033[1;96m                         SUMMARY OF CHANGES\033[0m");
        System.out.println("\033[1;96m════════════════════════════════════════════════════════════════════\033[0m");

        System.out.printf("\n\033[1;96mMember:           %s %s\033[0m%n",
                member.getFirstName(), member.getLastName());

        if (typeChanged) {
            System.out.printf("\033[1;96mMembership Type:  \033[1;91m%s\033[0m → \033[1;92m%s\033[0m%n",
                    member.getMembershipType(), newType);
        } else {
            System.out.printf("\033[1;96mMembership Type:  %s (no change)\033[0m%n",
                    member.getMembershipType());
        }

        System.out.printf("\033[1;96mAdded month(s):   %d month(s)\033[0m%n", months);
        System.out.printf("\033[1;96mNew End Date:     %s\033[0m%n", newEndDate);

        // Show old end date for comparison if active
        if (member.isActive()) {
            System.out.printf("\033[1;96mPrevious End:     %s\033[0m%n",
                    member.getMembershipEndDate());
        }

        System.out.println("\n\033[1;96m════════════════════════════════════════════════════════════════════\033[0m");
    }

    // display util for updateMemberInformation
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
            \033[1;93m      \t\t[9]\033[0m  \033[1;97m\tReturn to main menu\033[0m
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

    // display util for deleteMember
    public static void displayDeleteMemberHeader(String title){
        System.out.printf("""
            
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
            \033[1;93m                          %s\033[0m
            \033[1;93m════════════════════════════════════════════════════════════════════\033[0m""", title);
    }

    // display util for memberLookupHandle
    public static void displaySearchMenu() {
        System.out.println("\n\033[1;93m════════════════════════════════════════════════════════════════════\033[0m");
        System.out.println("\033[1;93m                           MEMBER LOOKUP\033[0m");
        System.out.println("\033[1;93m════════════════════════════════════════════════════════════════════\033[0m");

        System.out.print("""
                \033[1;96m       ───────────────── Search Options ─────────────────\033[0m
                
                \033[1;93m      \t\t[1]\033[0m  \033[1;97m\tView All Members\033[0m
                \033[1;93m      \t\t[2]\033[0m  \033[1;97m\tSearch by Name\033[0m
                \033[1;93m      \t\t[3]\033[0m  \033[1;97m\tSearch by ID\033[0m
                \033[1;93m      \t\t[4]\033[0m  \033[1;97m\tReturn to Main Menu\033[0m
                
                \033[1;93m════════════════════════════════════════════════════════════════════\033[0m
                """);

    }
    public static void displaySearchResults(List<Member> results, String searchTitle) {
        if (results.isEmpty()) {
            System.out.println("\n❌ \033[1;91mNo members found matching your search.\033[0m");
            return;
        }

        System.out.println("\n\033[1;93m════════════════════════════════════════════════════════════════════\033[0m");
        System.out.printf("\033[1;93m                 SEARCH RESULTS: %s\033[0m%n", searchTitle);
        System.out.println("\033[1;93m════════════════════════════════════════════════════════════════════\033[0m");

        System.out.printf("\033[1;96m%-5s %-25s %-10s %-15s %-10s\033[0m%n",
                "ID", "NAME", "TYPE", "CONTACT", "STATUS");
        System.out.println("\033[1;93m════════════════════════════════════════════════════════════════════\033[0m");

        results.forEach(DisplayUtil::displayMemberRow);

        System.out.println("\033[1;93m════════════════════════════════════════════════════════════════════\033[0m");
        System.out.printf("\nFound: \033[1;96m%d member(s)\033[0m%n", results.size());
    }
    public static void displayMemberRow(Member member) {
        String status = member.isActive() ?
                "\033[1;92mACTIVE\033[0m" : "\033[1;91mEXPIRED\033[0m";

        System.out.printf("%-5d %-25s %-10s %-15s %-10s%n",
                member.getMemberId(),
                member.getFullName().toUpperCase(),
                member.getMembershipType(),
                member.getContactNumber(),
                status
        );
    }
    public static void displayAllMembers(List<Member> members) {

        long activeCount = members.stream().filter(Member::isActive).count();
        long inactiveCount = members.size() - activeCount;

        System.out.println("\n\033[1;93m════════════════════════════════════════════════════════════════════\033[0m");
        System.out.println("\033[1;93m                        ALL MEMBERS\033[0m");
        System.out.println("\033[1;93m════════════════════════════════════════════════════════════════════\033[0m");

        if (members.isEmpty()) {
            System.out.println("\n❌ \033[1;91mNo members registered yet.\033[0m\n");
            return;
        }

        System.out.printf("\033[1;96m%-5s %-25s %-10s %-10s %-10s\033[0m%n",
                "ID", "NAME", "TYPE", "STATUS", "REMAINING DAYS");
        System.out.println("\033[1;93m════════════════════════════════════════════════════════════════════\033[0m");

        members.forEach(member -> {

            long statusDays;

            String status = member.isActive() ?
                    "\033[1;92mACTIVE\033[0m" : "\033[1;91mEXPIRED\033[0m";

            if (member.isActive()) {
                statusDays = ChronoUnit.DAYS.between(LocalDate.now(), member.getMembershipEndDate());
            } else {
                statusDays = 0;
            }

            System.out.printf("\033[1;96m%-5d %-25s %-10s\033[0m %-25s \033[1;96m%-10s\033[0m %n",
                    member.getMemberId(),
                    member.getFullName().toUpperCase(),
                    member.getMembershipType(), status, statusDays
            );
        });
        System.out.println("\033[1;93m════════════════════════════════════════════════════════════════════\033[0m");

        System.out.printf("\033[1;96mTotal Members: %d | Active: %d | Inactive: %d%n\033[0m", members.size(), activeCount, inactiveCount);
    }
}
