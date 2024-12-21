package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.Accounts;
import com.xdf.xd_f371.repo.AccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepo accountRepo;

    public Accounts findAccountByUsername(String username) {
        return accountRepo.findByUsername(username);
    }
}
