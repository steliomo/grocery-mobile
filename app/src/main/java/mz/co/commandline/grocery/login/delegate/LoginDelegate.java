package mz.co.commandline.grocery.login.delegate;

import java.util.List;

import mz.co.commandline.grocery.generics.dto.EnumDTO;
import mz.co.commandline.grocery.grocery.dto.UnitDTO;

public interface LoginDelegate extends SignUpDelegate {

    void login(String username, String password);

    void resetPasswordPage();

    void resetPassword(String number);

    void signUp();

    void signUp(UnitDTO grocery);

    void cancel();

    List<EnumDTO> getUnitTypes();

    EnumDTO getUnitType();

    void setUnitType(EnumDTO enumDTO);
}
