package service.impl;

import storage.FileHandler;
import util.DisplayUtil;
import util.InputUtil;

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
}
