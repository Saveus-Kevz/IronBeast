package service;

import model.Member;
import storage.FileHandler;
import util.DisplayUtil;
import util.InputUtil;

import java.util.List;

public interface MemberSearchService {
    void memberLookup();

    default void handleNameSearch() {
        String searchTerm = InputUtil.getString("\nEnter name to search: ");

        List<Member> results = FileHandler.searchByName(searchTerm);
        DisplayUtil.displaySearchResults(results, "Name Search: '" + searchTerm + "'");
    }

    default void handleIdSearch() {

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
