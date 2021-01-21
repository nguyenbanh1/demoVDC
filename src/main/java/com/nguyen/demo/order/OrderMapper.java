package com.nguyen.demo.order;

import com.nguyen.demo.core.entity.Order;
import com.nguyen.demo.core.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "address", source = "orderReq.address")
    @Mapping(target = "fullName", source = "orderReq.fullName")
    @Mapping(target = "phoneNumber", source = "orderReq.phoneNumber")
    @Mapping(target = "email", source = "orderReq.email")
    @Mapping(target = "user", source = "user")
    Order of(OrderReq orderReq, User user);
}
