package com.kridan.split_net.domain.device.usecases;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface GenerateConfigUseCase {
    List<Map<String, String>> generate(String user_id, String device_id);
}
