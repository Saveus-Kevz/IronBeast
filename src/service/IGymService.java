package service;

import model.Member;
import java.util.Scanner;

public interface IGymService {
    void registerMemberHandle(Scanner scan);
    void showActiveMembersHandle(Scanner scan);
    void showInactiveMembersHandle(Scanner scan);
    void renewMembershipHandle(Scanner scan);
    void updateMemberInformation(Scanner scan);
    void deleteMember();
    void memberLookupHandle();
    Member memberLookupById(int id);
}
