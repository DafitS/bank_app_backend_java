package com.example.demo.service.impl;

import com.example.demo.dto.TransactionDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.OperationHistory;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.User;
import com.example.demo.exceptions.AccountNotExistException;
import com.example.demo.exceptions.AccountNumberNotExistException;
import com.example.demo.exceptions.InvalidArgumentException;
import com.example.demo.exceptions.TransactionNotExistException;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.option.OperationType;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.OperationHistoryRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.TransactionService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final OperationHistoryRepository operationHistoryRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, OperationHistoryRepository operationHistoryRepository)
    {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.operationHistoryRepository = operationHistoryRepository;
    }


    @Override
    public TransactionDto getTransactionById(Long id) {
        Transaction transaction= transactionRepository
                .findById(id)
                .orElseThrow(() ->  new TransactionNotExistException("Transaction not found", id));
        return TransactionMapper.mapToTransactionDto(transaction);
    }

    @Override
    @Transactional
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        if(transactionDto.getAmount() == null || transactionDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidArgumentException("Amount must be positive");
        }

        if(transactionDto.getAccountFrom().equals(transactionDto.getAccountTo())) {
            throw new InvalidArgumentException("Sender and receiver accounts must be different");
        }

        Account sender = accountRepository.findByAccountNumber(transactionDto.getAccountFrom())
                .orElseThrow(() -> new AccountNumberNotExistException("Sender account not found", transactionDto.getAccountFrom()));

        Account receiver = accountRepository.findByAccountNumber(transactionDto.getAccountTo())
                .orElseThrow(() -> new AccountNumberNotExistException("Receiver account not found", transactionDto.getAccountTo()));

        if(sender.getAmount().compareTo(transactionDto.getAmount()) < 0) {
            throw new InvalidArgumentException("Insufficient funds in sender account");
        }

        sender.setAmount(sender.getAmount().subtract(transactionDto.getAmount()));
        receiver.setAmount(receiver.getAmount().add(transactionDto.getAmount()));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        OperationHistory senderHistory = new OperationHistory();

        senderHistory.setAccount(sender);
        senderHistory.setOperationType(OperationType.TRANSFER_OUT);
        senderHistory.setAmount(transactionDto.getAmount());
        senderHistory.setBalanceAfter(sender.getAmount());
        senderHistory.setCreatedAt(LocalDateTime.now());
        senderHistory.setRelatedAccountNumber(receiver.getAccountNumber());
        operationHistoryRepository.save(senderHistory);

        OperationHistory recieverHistory = new OperationHistory();

        recieverHistory.setAccount(receiver);
        recieverHistory.setOperationType(OperationType.TRANSFER_IN);
        recieverHistory.setAmount(transactionDto.getAmount());
        recieverHistory.setBalanceAfter(receiver.getAmount());
        recieverHistory.setCreatedAt(LocalDateTime.now());
        recieverHistory.setRelatedAccountNumber(sender.getAccountNumber());

        operationHistoryRepository.save(recieverHistory);



        Transaction transaction = TransactionMapper.mapToTransaction(transactionDto);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.mapToTransactionDto(savedTransaction);
    }

}
