package com.example.maintmanagerultimate.presentation.interfaces.feigns;

import com.example.maintmanagerultimate.service.dto.RequestsDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "requests")
public interface MaintRequestsFeign {

    @CircuitBreaker(name = "maint-manager", fallbackMethod = "defaultRequests")
    @GetMapping("/requests")
    RequestsDto getMaintRequests(@RequestParam Long requestsId);

    default RequestsDto defaultRequests (Long requestsId, Throwable throwable){
        return new RequestsDto(requestsId, "DEFAULT", "DEFAULT");
    }
}
