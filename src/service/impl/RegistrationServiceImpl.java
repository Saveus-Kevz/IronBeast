package service.impl;

import model.Member;
import util.InputUtil;

public class RegistrationServiceImpl implements service.RegistrationService {

    @Override
    public void registerMember() {
        do {
            Member newMember = collectMemberInformation();
            confirmAndRegister(newMember);

        } while (InputUtil.getYesNo("\033[1;96mRegister another member?\033[0m"));
    }

}