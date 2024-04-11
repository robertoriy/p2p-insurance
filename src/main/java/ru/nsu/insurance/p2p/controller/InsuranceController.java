package ru.nsu.insurance.p2p.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.insurance.p2p.dto.request.ContractDeployRequest;
import ru.nsu.insurance.p2p.dto.request.EthereumRequest;
import ru.nsu.insurance.p2p.dto.response.ContractResponse;
import ru.nsu.insurance.p2p.service.exception.InsuranceAccessDeniedException;
import ru.nsu.insurance.p2p.service.exception.InsuranceServiceException;
import ru.nsu.insurance.p2p.service.exception.existence.NotFoundException;
import ru.nsu.insurance.p2p.service.insurance.InsuranceService;

@Tag(name = "Insurance Controller", description = "Контроллер для управления смарт-контрактом в страховой группе")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class InsuranceController {
    private final InsuranceService insuranceService;

    @Operation(summary = "Инициировать создание смарт-контракта")
    @PostMapping(value = "/{user_id}/groups/{group_id}/contract")
    public ResponseEntity<ContractResponse> deployContract(
        @PathVariable("user_id") long userId,
        @PathVariable("group_id") long groupId,
        @RequestBody ContractDeployRequest contractDeployRequest
    ) {
        try {
            insuranceService.deploy(userId, groupId, contractDeployRequest);
            return ResponseEntity.ok().build();
        } catch (NotFoundException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.notFound().build();
        } catch (InsuranceAccessDeniedException exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (InsuranceServiceException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Внести взнос")
    @PostMapping(value = "/{user_id}/groups/{group_id}/contract/contribute")
    public ResponseEntity<ContractResponse> contribute(
        @PathVariable("user_id") long userId,
        @PathVariable("group_id") long groupId,
        @RequestBody EthereumRequest ethereumRequest
    ) {
        try {
            insuranceService.contribute(userId, groupId, ethereumRequest);
            return ResponseEntity.ok().build();
        } catch (NotFoundException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.notFound().build();
        } catch (InsuranceAccessDeniedException exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (InsuranceServiceException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Запросить возврат средств всем участникам")
    @PostMapping(value = "/{user_id}/groups/{group_id}/contract/refund")
    public ResponseEntity<ContractResponse> refund(
        @PathVariable("user_id") long userId,
        @PathVariable("group_id") long groupId,
        @RequestBody EthereumRequest ethereumRequest
    ) {
        try {
            insuranceService.refund(userId, groupId, ethereumRequest);
            return ResponseEntity.ok().build();
        } catch (NotFoundException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.notFound().build();
        } catch (InsuranceAccessDeniedException exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (InsuranceServiceException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Заявить о наступлении страхового случая")
    @PostMapping(value = "/{user_id}/groups/{group_id}/contract/insurance_event")
    public ResponseEntity<ContractResponse> reportInsuranceEvent(
        @PathVariable("user_id") long userId,
        @PathVariable("group_id") long groupId,
        @RequestBody EthereumRequest ethereumRequest
    ) {
        try {
            insuranceService.reportInsuranceEvent(userId, groupId, ethereumRequest);
            return ResponseEntity.ok().build();
        } catch (NotFoundException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.notFound().build();
        } catch (InsuranceAccessDeniedException exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (InsuranceServiceException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
