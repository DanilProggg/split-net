package com.kridan.split_net.domain.device.usecases;

import java.util.List;
import java.util.Map;

public interface GenerateConfigUseCase {
    List<Map<String, String>> generate(String device_id);
}
