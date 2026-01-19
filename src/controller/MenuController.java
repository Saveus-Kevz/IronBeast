package controller;

import service.GymService;
import util.DisplayUtil;

import java.util.Scanner;

public class MenuController {
    private static final GymService gymService = new GymService();
    private static final Scanner scan = new Scanner(System.in);

    public void start() {

        boolean appRunning = true;
        do{
            try{
                DisplayUtil.mainMenuDisplay();
                int choice = getMenuChoice();

                switch (choice){
                    case 1 -> gymService.registerMemberHandle(scan);
                    case 2 -> gymService.showActiveMembersHandle(scan);
                    case 3 -> gymService.showInactiveMembersHandle(scan);
                    case 4 -> gymService.renewMembershipHandle(scan);
                    case 5 -> gymService.updateMemberInformation(scan);
                    case 6 -> gymService.deleteMember();
                    case 7 -> gymService.memberLookupHandle();
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

    private int getMenuChoice() {
        while (true) {
            try {
                int choice = Integer.parseInt(scan.nextLine());
                if (choice >= 1 && choice <= 7 || choice == 99) {
                    return choice;
                }
                System.out.println("Invalid option. Please enter 1-7 or 99.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

}
