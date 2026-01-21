package service.impl;

import model.Member;
import storage.FileHandler;
import util.DisplayUtil;
import util.InputUtil;

public class MemberDeleteServiceImpl implements service.MemberDeleteService {
    @Override
    public void deleteMember(){

        if (!FileHandler.hasMembers()) {
            DisplayUtil.handleEmptyList("NOTHING TO DELETE.");
            return;
        }

        boolean deleteAnother;
        boolean confirmDelete;
        do{
            DisplayUtil.displayDeleteMemberHeader("DELETE MEMBER");
            int memberId = InputUtil.getInt("\nEnter Member ID to delete: ", 1, Integer.MAX_VALUE);

            Member memberToBeDeleted = FileHandler.getMemberById(memberId);

            if (memberToBeDeleted == null) {
                System.out.println("❌ \033[1;91mMEMBER NOT FOUND.\033[0m Use MEMBER LOOKUP to search for member IDs.");
                return;
            }

            DisplayUtil.displayFullMemberInformation(memberToBeDeleted);

            confirmDelete = InputUtil.getYesNo("\nConfirm deletion?");

            if(confirmDelete){
                FileHandler.deleteMember(memberId);
                System.out.println("\n✅ MEMBER DELETION SUCCESSFUL! \n");
            }else System.out.println("❌ \033[1;91mDeletion cancelled.\033[0m\n");

            deleteAnother = InputUtil.getYesNo("\033[1;96mDelete another member?\033[0m");
            if (!deleteAnother){
                return;
            }
        }while (true);

    }
}
