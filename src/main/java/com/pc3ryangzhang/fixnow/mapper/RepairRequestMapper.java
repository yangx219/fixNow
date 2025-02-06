package com.pc3ryangzhang.fixnow.mapper;

import com.pc3ryangzhang.fixnow.entity.RepairRequest;
import java.util.List;

public interface RepairRequestMapper {
    RepairRequest getRepairRequestById(Integer requestId);
    List<RepairRequest> getRepairRequestsByUserId(Integer userId);
    int insertRepairRequest(RepairRequest repairRequest);
    int updateRepairRequest(RepairRequest repairRequest);
    int deleteRepairRequest(Integer requestId);
    List<RepairRequest> getAllRepairRequests();

}
