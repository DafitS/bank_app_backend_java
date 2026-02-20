package com.example.demo.service.impl;

import com.example.demo.dto.transaction.TransactionCreateDto;
import com.example.demo.dto.transaction.TransactionResponseDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.OperationHistory;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.User;
import com.example.demo.exceptions.custom.AccountNotActiveException;
import com.example.demo.exceptions.custom.AccountNumberNotExistException;
import com.example.demo.exceptions.custom.InvalidArgumentException;
import com.example.demo.exceptions.custom.TransactionNotExistException;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.option.OperationType;
import com.example.demo.option.RoleType;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.OperationHistoryRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public TransactionResponseDto getTransactionById(Long id) {
        Transaction transaction= transactionRepository
                .findById(id)
                .orElseThrow(() ->  new TransactionNotExistException("Transaction not found", id));
        return TransactionMapper.mapToTransactionResponseDto(transaction);
    }

    @Override
    @Transactional
    public TransactionResponseDto createTransaction(TransactionCreateDto transactionCreateDto) {
        if(transactionCreateDto.getAmount() == null || transactionCreateDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidArgumentException("Amount must be positive");
        }

        if(transactionCreateDto.getAccountFrom().equals(transactionCreateDto.getAccountTo())) {
            throw new InvalidArgumentException("Sender and receiver accounts must be different");
        }

        Account sender;
        User currentUser = getCurrentUser();

        if(currentUser.getRoleType() == RoleType.ADMINISTRATOR)
        {
            sender = accountRepository
                    .findByAccountNumber(transactionCreateDto.getAccountFrom())
                        .orElseThrow(() -> new AccountNumberNotExistException(
                                "Sender account not found",
                                transactionCreateDto.getAccountFrom()));
        } else {
            sender = accountRepository
                    .findByAccountNumberAndUserId(transactionCreateDto.getAccountFrom(), currentUser.getId())
                        .orElseThrow(() -> new AccountNumberNotExistException(
                                "Sender account not found",
                                transactionCreateDto.getAccountFrom()));
        }

        if(!sender.isActive())
        {
            throw new AccountNotActiveException("Account is disabled!");
        }

        Account receiver = accountRepository.findByAccountNumber(transactionCreateDto.getAccountTo())
                .orElseThrow(() -> new AccountNumberNotExistException("Receiver account not found", transactionCreateDto.getAccountTo()));

        if(!receiver.isActive())
        {
            throw new AccountNotActiveException("Something wrong error!");
        }

        if(sender.getAmount().compareTo(transactionCreateDto.getAmount()) < 0) {
            throw new InvalidArgumentException("Insufficient funds in sender account");
        }

        sender.setAmount(sender.getAmount().subtract(transactionCreateDto.getAmount()));
        receiver.setAmount(receiver.getAmount().add(transactionCreateDto.getAmount()));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        OperationHistory senderHistory = new OperationHistory();

        senderHistory.setAccount(sender);
        senderHistory.setOperationType(OperationType.TRANSFER_OUT);
        senderHistory.setAmount(transactionCreateDto.getAmount());
        senderHistory.setBalanceAfter(sender.getAmount());
        senderHistory.setCreatedAt(LocalDateTime.now());
        senderHistory.setRelatedAccountNumber(receiver.getAccountNumber());
        operationHistoryRepository.save(senderHistory);

        OperationHistory receiverHistory = new OperationHistory();

        receiverHistory.setAccount(receiver);
        receiverHistory.setOperationType(OperationType.TRANSFER_IN);
        receiverHistory.setAmount(transactionCreateDto.getAmount());
        receiverHistory.setBalanceAfter(receiver.getAmount());
        receiverHistory.setCreatedAt(LocalDateTime.now());
        receiverHistory.setRelatedAccountNumber(sender.getAccountNumber());

        operationHistoryRepository.save(receiverHistory);



        Transaction transaction = TransactionMapper.mapToTransaction(transactionCreateDto, sender);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.mapToTransactionResponseDto(savedTransaction);
    }

    private User getCurrentUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

}
