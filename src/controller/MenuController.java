package controller;

import service.impl.*;
import util.DisplayUtil;
import util.InputUtil;


public class MenuController {
    private static final RegistrationServiceImpl registrationServiceImpl = new RegistrationServiceImpl();
    private static final MemberUpdateServiceImpl memberUpdateServiceImpl = new MemberUpdateServiceImpl();
    private static final RenewMembershipServiceImpl renewMembershipImpl = new RenewMembershipServiceImpl();
    private static final MemberSearchServiceImpl memberSearchServiceImpl = new MemberSearchServiceImpl();
    private static final MemberDeleteServiceImpl memberDeleteServiceImpl = new MemberDeleteServiceImpl();
    private static final ActiveMemberServiceImpl activeMemberServiceImpl = new ActiveMemberServiceImpl();
    private static final InactiveMemberServiceImpl inactiveMemberServiceImpl = new InactiveMemberServiceImpl();

    public void start() {

        boolean appRunning = true;
        do{
            try{
                DisplayUtil.mainMenuDisplay();
                int choice = InputUtil.getInt(" Enter your choice (1-7, 99): ", 1, 99);

                switch (choice){
                    case 1 -> registrationServiceImpl.registerMember();
                    case 2 -> activeMemberServiceImpl.showActiveMembers();
                    case 3 -> inactiveMemberServiceImpl.showInactiveMembers();
                    case 4 -> renewMembershipImpl.renewMembership();
                    case 5 -> memberUpdateServiceImpl.updateMemberInformation();
                    case 6 -> memberDeleteServiceImpl.deleteMember();
                    case 7 -> memberSearchServiceImpl.memberLookup();
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

        InputUtil.closeScanner();
    }

}
