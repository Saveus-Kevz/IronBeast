package service;

import storage.FileHandler;
import util.DisplayUtil;
import util.InputValidator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public abstract class BaseGymService implements IGymService{

    @Override
    public void showActiveMembers(Scanner scan) {

        boolean stayInView;

        DisplayUtil.displayActiveMembersHeader("ACTIVE MEMBERS");

        FileHandler.getActiveMembers().forEach(m -> System.out.printf(
                        "\033[1;96m%-5d %-25s %-10s %-15s %-10s\033[0m%n",
                        m.getMemberId(),
                        (m.getFirstName() + " " + m.getLastName()).toUpperCase(),
                        m.getMembershipType(),
                        m.getMembershipEndDate(), "ACTIVE"
                ));

        System.out.println("\033[1;93m════════════════════════════════════════════════════════════════════\033[0m");
        stayInView = InputValidator.getYesNo(scan, "Return to main menu?");
        if (!stayInView){
            System.out.println("Thank you for using this application!\n");
            System.exit(0);
        }
    }

    @Override
    public void showInactiveMembers(Scanner scan) {

        boolean stayInView;

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

        stayInView = InputValidator.getYesNo(scan, "Return to main menu?");
        if (!stayInView){
            System.out.println("Thank you for using this application!\n");
            System.exit(0);
        }
    }
}
