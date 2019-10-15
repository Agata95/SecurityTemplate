package com.javagda25.securitytemp.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRoleChangeRequest {
    private Long accountId;
    private Map<String, String> roles;
}
