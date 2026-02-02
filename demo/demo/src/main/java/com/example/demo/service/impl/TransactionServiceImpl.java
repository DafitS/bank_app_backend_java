package com.example.demo.service.impl;

import com.example.demo.dto.TransactionDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.User;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.TransactionService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository)
    {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDto);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.mapToTransactionDto(savedTransaction);
    }

    @Override
    public TransactionDto getTransactionById(Long id) {
        Transaction transaction= transactionRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return TransactionMapper.mapToTransactionDto(transaction);
    }

//    @Transactional
//    public Transaction transfer(TransactionDto transactionDto) {
//        if(transactionDto.getBalance() <= 0)
//        {
//            throw new RuntimeException("Kwota musi byc wieksza od 0");
//        }
//
//        if(transactionDto.getAccountFrom().equals(transactionDto.getAccountTo())){
//            throw new RuntimeException("Konta muszą być różne!");
//        }
//
//        Account from = accountRepository.f(transactionDto.getAccountFrom()).orElseThrow(()-> new RuntimeException("Konto nie znalezione"))
//        Transaction tx = new Transaction();
//    }

}
