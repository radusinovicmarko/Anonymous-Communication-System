package org.unibl.etf.sni.acs.security.models;

import lombok.Data;

import java.util.List;

@Data
public class AuthorizationRules {
    private List<Rule> rules;
}
