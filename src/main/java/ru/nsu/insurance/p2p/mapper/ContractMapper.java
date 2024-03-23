package ru.nsu.insurance.p2p.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.nsu.insurance.p2p.dao.entity.InsuranceContract;
import ru.nsu.insurance.p2p.dto.response.ContractResponse;

@Mapper
public interface ContractMapper {
    ContractMapper INSTANCE = Mappers.getMapper(ContractMapper.class);

    @Mapping(target = "contractId", source = "id")
    @Mapping(target = "ethereumAddress", source = "address")
    ContractResponse contractToContractResponse(InsuranceContract contract);
}
