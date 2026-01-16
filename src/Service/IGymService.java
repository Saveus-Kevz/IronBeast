package Service;

import Model.Member;
import java.util.Scanner;

public interface IGymService {
    void registerMember(Scanner scan);
    void showActiveMembers();
    void showInactiveMembers();
    void renewMembership();
    void updateMemberInformation();
    void deleteMember();
    void memberLookup();
    Member memberLookupById(int id);
}
