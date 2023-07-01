package com.debuggeando_ideas.best_travel.infraestructure.helper;

import com.debuggeando_ideas.best_travel.util.exceptions.ForbiddenCustomerException;
import org.springframework.stereotype.Component;

@Component
public class BlackListHelper {
    public void isInBlackList(String customerId) {
        if (customerId.equals("WALA771012HCRGR054")) {
            throw new ForbiddenCustomerException();
        }
    }
}
