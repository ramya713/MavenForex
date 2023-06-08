package com.sprint.exchange.util;

import com.sprint.exchange.model.Admin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(value = "admin", url = "http://localhost:8081")
public interface AdminFeignClient {
    @GetMapping("/admin/findById/{adminId}")
    public Optional<Admin> findById(@PathVariable("adminId") Integer adminId);
}
