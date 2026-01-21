package service.impl;

import storage.FileHandler;
import util.DisplayUtil;
import util.InputUtil;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class InactiveMemberServiceImpl implements service.InactiveMemberService {
    @Override
    public void showInactiveMembers() {
        DisplayUtil.displayInactiveMembersHeader("INACTIVE MEMBERS");

        FileHandler.getInactiveMembers().forEach(m -> {
            long daysExpired = ChronoUnit.DAYS.between(
                    m.getMembershipEndDate(), LocalDate.now());

            System.out.printf(
                    "\033[1;96m%-5d %-25s %-10s %-12s %-15d\033[0m%n",
                    m.getMemberId(),
                    (m.getFirstName() + " " + m.getLastName()).toUpperCase(),
                    m.getMembershipType(),
                    m.getMembershipEndDate(),
                    daysExpired
            );
        });

        System.out.println("\033[1;93m════════════════════════════════════════════════════════════════════\033[0m");

        if (!InputUtil.getYesNo("Return to main menu?")){
            System.out.println("Thank you for using this application!\n");
            System.exit(0);
        }
    }
}
