package mz.co.commandline.grocery.user.dto;

import java.math.BigDecimal;

public class UnitDetail {

    private String uuid;

    private String name;

    private Long users;

    private String managerName;

    private String subscriptionEndDate;

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Long getUsers() {
        return users;
    }

    public String getSubscriptionEndDate() {
        if(subscriptionEndDate == null){
            return "";
        }
        return subscriptionEndDate;
    }
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerName() {
        return managerName;
    }
}
