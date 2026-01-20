package service;

import java.util.Scanner;

public interface IGymService {
    void registerMember(Scanner scan);
    void showActiveMembers(Scanner scan);
    void showInactiveMembers(Scanner scan);
    void renewMembership(Scanner scan);
    void updateMemberInformation(Scanner scan);
    void deleteMember(Scanner scan);
    void memberLookupHandle(Scanner scan);
}
