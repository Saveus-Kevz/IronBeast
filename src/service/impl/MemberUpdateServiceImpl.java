package service.impl;

import model.Member;
import storage.FileHandler;
import util.DisplayUtil;
import util.InputUtil;

public class MemberUpdateServiceImpl implements service.MemberUpdateService {

    @Override
    public void updateMemberInformation(){
        boolean updateAnother;

        if (!FileHandler.hasMembers()) {
            DisplayUtil.handleEmptyList("NOTHING TO UPDATE.");
            return;
        }

        do {
            DisplayUtil.displayUpdateMemberInformationHeader("UPDATE MEMBER INFORMATION");

            int memberId = InputUtil.getInt("\nEnter Member ID to update information: ", 1, Integer.MAX_VALUE);

            Member memberToUpdateInformation = FileHandler.getMemberById(memberId);

            if (memberToUpdateInformation == null) {
                System.out.println("❌ \033[1;91mMEMBER NOT FOUND.\033[0m Use MEMBER LOOKUP to search for member IDs.");
                return;
            }

            DisplayUtil.displayFullMemberInformation(memberToUpdateInformation);
            DisplayUtil.displayUpdateMemberInformationMenu();

            int choice = InputUtil.getInt("Enter your choice (1-9): ", 1, 9);

            updateAnother = handleUpdateChoice(choice, memberToUpdateInformation);
            if (!updateAnother){
                return;
            }
        } while (true);
    }


}
