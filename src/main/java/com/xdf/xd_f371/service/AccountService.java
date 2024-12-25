package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.Accounts;
import com.xdf.xd_f371.repo.AccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepo accountRepo;
    @Autowired
    private HashService hashService;
    public Accounts findAccountByUsername(String username) {
        return accountRepo.findByUsername(username);
    }
    public Optional<Accounts> findAccById(int id) {
        return accountRepo.findById(id);
    }
    public Optional<Accounts> login(String user, String pass){
        return accountRepo.login(user,hashService.generateSHA1Hash(pass));
    }
    public Accounts save(Accounts a) {
        return accountRepo.save(a);
    }
}
