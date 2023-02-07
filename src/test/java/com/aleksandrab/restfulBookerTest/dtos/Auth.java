package com.aleksandrab.restfulBookerTest.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object to store auth details
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Auth {
    private String username;
    private String password;
}
