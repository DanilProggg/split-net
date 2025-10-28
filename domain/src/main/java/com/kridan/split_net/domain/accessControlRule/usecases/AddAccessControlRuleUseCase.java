package com.kridan.split_net.domain.accessControlRule.usecases;

public interface AddAccessControlRuleUseCase {
    boolean create(String destination, String type);
}
