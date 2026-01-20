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
                    case 1 -> gymService.registerMember(scan);
                    case 2 -> gymService.showActiveMembers(scan);
                    case 3 -> gymService.showInactiveMembers(scan);
                    case 4 -> gymService.renewMembership(scan);
                    case 5 -> gymService.updateMemberInformation(scan);
                    case 6 -> gymService.deleteMember(scan);
                    case 7 -> gymService.memberLookupHandle(scan);
                    case 99 -> {
                        System.out.println("\nTHANK YOU FOR USING THIS APPLICATION!!\n");
                        appRunning = false;
                    }
                    default -> System.out.println("Invalid input.\n");
                }
            }catch (NumberFormatException e){
                System.out.println("Invalid input.\n");
            }
        }while (appRunning);

        scan.close();
    }

    private int getMenuChoice() {
        while (true) {
            try {
                int choice = Integer.parseInt(scan.nextLine());
                if (choice >= 1 && choice <= 7 || choice == 99) {
                    return choice;
                }
                System.out.print("Invalid option. Please enter (1-7 or 99): ");
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number (1-7 or 99): ");
            }
        }
    }
}
