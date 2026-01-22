package service.impl;

import model.Member;
import storage.FileHandler;
import util.DisplayUtil;
import util.InputUtil;

import java.util.List;

public class MemberSearchServiceImpl implements service.MemberSearchService {
    @Override
    public void memberLookup() {

        while (true) {
            DisplayUtil.displaySearchMenu();

            int choice = InputUtil.getInt("Enter choice (1-4): ",1, 4);

            switch (choice) {
                case 1 -> DisplayUtil.displayAllMembers(FileHandler.getAllMembers());
                case 2 -> handleNameSearch();
                case 3 -> handleIdSearch();
                case 4 -> {
                    System.out.println("Returning to main menu...\n");
                    return;
                }
                default -> System.out.println("❌ Invalid choice. Please try again.");
            }

            if(!(InputUtil.getYesNo("\nSearch again?"))){
                return;
            }

        }
    }

    private void handleNameSearch() {
        String searchTerm = InputUtil.getString("\nEnter name to search: ");

        List<Member> results = FileHandler.searchByName(searchTerm);
        DisplayUtil.displaySearchResults(results, "Name Search: '" + searchTerm + "'");
    }

    private void handleIdSearch() {

        int memberId = InputUtil.getInt("\nEnter Member ID to search: ", 1, Integer.MAX_VALUE);
        Member member = FileHandler.getMemberById(memberId);
        if (member != null) {
            System.out.println("\n\033[1;96m✓ MEMBER FOUND:\033[0m");
            DisplayUtil.displayFullMemberInformation(member);
        } else {
            System.out.println("❌ \033[1;91mMember with ID " + memberId + " not found.\033[0m");
        }

    }
}
