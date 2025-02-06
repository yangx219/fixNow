package com.pc3ryangzhang.fixnow.mapper;

import com.pc3ryangzhang.fixnow.entity.Location;

public interface LocationMapper {
    Location getLocationByUserId(Integer userId);
    int insertLocation(Location location);
    int updateLocation(Location location);
}
