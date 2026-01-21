package service.impl;

import storage.FileHandler;
import util.DisplayUtil;
import util.InputUtil;

public class ActiveMemberServiceImpl implements service.ActiveMemberService {
    @Override
    public void showActiveMembers() {
        DisplayUtil.displayActiveMembersHeader("ACTIVE MEMBERS");

        FileHandler.getActiveMembers().forEach(m -> System.out.printf(
                "\033[1;96m%-5d %-25s %-10s %-15s %-10s\033[0m%n",
                m.getMemberId(),
                (m.getFirstName() + " " + m.getLastName()).toUpperCase(),
                m.getMembershipType(),
                m.getMembershipEndDate(), "ACTIVE"
        ));

        System.out.println("\033[1;93m════════════════════════════════════════════════════════════════════\033[0m");

        if (!InputUtil.getYesNo("Return to main menu?")){
            System.out.println("Thank you for using this application!\n");
            System.exit(0);
        }
    }
}
