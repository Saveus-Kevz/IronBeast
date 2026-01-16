package Controller;

import Service.GymService;
import java.util.Scanner;

public class MenuController {
    private static final GymService gymService = new GymService();
    private static final Scanner scan = new Scanner(System.in);

    public void start() {

        boolean appRunning = true;
        do{
            try{
                menu();
                int choice = Integer.parseInt(scan.nextLine());

                switch (choice){
                    case 1 -> gymService.registerMember(scan);
                    case 2 -> gymService.showActiveMembers();
                    case 3 -> gymService.showInactiveMembers();
                    case 4 -> gymService.renewMembership();
                    case 5 -> gymService.updateMemberInformation();
                    case 6 -> gymService.deleteMember();
                    case 7 -> gymService.memberLookup();
                    case 99 -> {
                        System.out.println("Thank you for using this application!\n");
                        appRunning = false;
                    }
                    default -> System.out.println("Invalid input.\n");
                }
            }catch (NumberFormatException e){
                System.out.println("Invalid input.\n");
            }
        }while (appRunning);
    }

    public void menu(){
        System.out.print(""" 
                =========================================================
                =========== IRON BEAST Membership Management ============
                =========================================================
                |     Choose option:                                    |
                |     (1) Register a new member                         |
                |     (2) Show active members                           |
                |     (3) Show inactive members                         |
                |     (4) Renew membership                              |
                |     (5) Update member information                     |
                |     (6) Delete member                                 |
                |     (7) Member lookup                                 |
                |     (99) Exit Program                                 |
                =========================================================
                Choice:\s""");
    }
}
