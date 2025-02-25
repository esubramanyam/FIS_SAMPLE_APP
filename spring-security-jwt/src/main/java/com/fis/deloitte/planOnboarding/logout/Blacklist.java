package com.fis.deloitte.planOnboarding.logout;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Blacklist {

    private Set<String> blacklistTokenSet = new HashSet<>();

    public void blacklistToken(String token){
        blacklistTokenSet.add(token);
    }

    public boolean isBlackListed(String token) {
        return blacklistTokenSet.contains(token);
    }
}
