package com.alibou.security.dao;

import lombok.Builder;
import lombok.Data;

/**
 * @author mqz
 */
@Data
@Builder
public class User {


    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private String role;

}
