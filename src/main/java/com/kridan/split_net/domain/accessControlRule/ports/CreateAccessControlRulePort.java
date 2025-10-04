package com.kridan.split_net.domain.accessControlRule.ports;

import com.kridan.split_net.domain.accessControlRule.AccessControlRule;

public interface CreateAccessControlRulePort {
    boolean create(AccessControlRule accessControlRule);
}
