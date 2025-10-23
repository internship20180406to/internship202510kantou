package com.example.internship.service;

import com.example.internship.entity.BankTransferForm;
import com.example.internship.repository.BankTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApplyBankTransferService {
    @Autowired
    private BankTransferRepository bankTransferRepository;

    public void applyBankTransfer(BankTransferForm bankTransferForm) {
        // ユーザーの入力内容をDBに入れる（repository/BankTransferRepository.javaのcreateメソッドへ）
        bankTransferRepository.create(bankTransferForm);
        bankTransferRepository.update(bankTransferForm);
    }
}
