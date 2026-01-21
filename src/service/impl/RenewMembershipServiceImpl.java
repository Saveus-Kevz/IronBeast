package service.impl;

import enumeration.MembershipType;
import model.Member;
import storage.FileHandler;
import util.DisplayUtil;
import util.InputUtil;

import java.time.LocalDate;

public class RenewMembershipServiceImpl implements service.RenewMembershipService {
    @Override
    public void renewMembership() {
        if (FileHandler.getAllMembers().isEmpty()) {
            DisplayUtil.handleEmptyList("NOTHING TO RENEW.");
            return;
        }

        DisplayUtil.displayRenewMembershipHeader("MEMBERSHIP RENEWAL/CHANGE");

        // Find member by ID to renew
        int memberId = InputUtil.getInt("\nEnter Member ID to renew: ", 1, Integer.MAX_VALUE);

        Member memberToRenew = FileHandler.getMemberById(memberId);

        if (memberToRenew == null) {
            System.out.println("❌ Member not found. Use MEMBER LOOKUP to search for member IDs.");
            return;
        }

        // Display current membership status
        DisplayUtil.displayCurrentMembershipAndMenu("CURRENT MEMBERSHIP", memberToRenew);

        // Ask if they want to change membership type
        boolean wantsToChangeType = InputUtil.getYesNo("\n\033[1;96mChange membership type?");

        MembershipType newMembershipType = memberToRenew.getMembershipType(); // Default to current
        boolean typeChanged = false;

        if (wantsToChangeType) {
            DisplayUtil.displayMembershipTypeOptions();
            int typeChoice = InputUtil.getInt("\nEnter choice (1-3): ", 1, 3);

            typeChanged = true;
            switch (typeChoice) {
                case 1 -> newMembershipType = MembershipType.BASIC;
                case 2 -> newMembershipType = MembershipType.PREMIUM;
                case 3 -> newMembershipType = MembershipType.VIP;
            }
            DisplayUtil.displayTypeChangeConfirmation(memberToRenew.getMembershipType(), newMembershipType);
        }

        DisplayUtil.displayRenewalDurationMenu();

        int durationChoice = InputUtil.getInt("\033[1;96mEnter (1-5): \033[0m", 1, 5);

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

        boolean confirm = InputUtil.getYesNo("Confirm renewal?");

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
}
