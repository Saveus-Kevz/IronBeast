package Service;

import Model.Member;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseGymService implements IGymService{
    List<Member> memberList = new LinkedList<>();

    @Override
    public void showActiveMembers() {
        System.out.println("\n======================  ACTIVE MEMBERS  =========================");
        System.out.println("=================================================================");
        System.out.printf("%-5s %-25s %-10s %-15s %-10s %n",
                "ID", "NAME", "TYPE", "EXPIRATION", "STATUS");
        System.out.println("=================================================================");

        memberList.stream()
                .filter(Member::isActive)
                .forEach(m -> System.out.printf(
                        "%-5d %-25s %-10s %-15s %-10s %n",
                        m.getMemberId(),
                        (m.getFirstName() + " " + m.getLastName()).toUpperCase(),
                        m.getMembershipType(),
                        m.getMembershipEndDate(), "ACTIVE"
                ));

        System.out.println("=================================================================\n");
    }

    @Override
    public void showInactiveMembers() {
        System.out.println("\n=======================  INACTIVE MEMBERS  ==========================");
        System.out.println("=====================================================================");
        System.out.printf("%-5s %-25s %-10s %-12s %-15s%n",
                "ID", "NAME", "TYPE", "EXPIRED", "DAYS EXPIRED");
        System.out.println("=====================================================================");

        memberList.stream()
                .filter(m -> !m.isActive())
                .forEach(m -> {
                    long daysExpired = ChronoUnit.DAYS.between(
                            m.getMembershipEndDate(), LocalDate.now());

                    System.out.printf(
                            "%-5d %-25s %-10s %-12s %-15d%n",
                            m.getMemberId(),
                            (m.getFirstName() + " " + m.getLastName()).toUpperCase(),
                            m.getMembershipType(),
                            m.getMembershipEndDate(),
                            daysExpired
                    );
                });

        System.out.println("=====================================================================\n");
    }
}
