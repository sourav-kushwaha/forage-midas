package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransactionProcessor {

    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;

    public TransactionProcessor(UserRepository userRepository,
                                TransactionRecordRepository transactionRecordRepository) {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
    }

    @Transactional
    public void process(Transaction tx) {
        Optional<UserRecord> senderOpt = userRepository.findById(tx.getSenderId());
        if (senderOpt.isEmpty()) return;

        Optional<UserRecord> recipientOpt = userRepository.findById(tx.getRecipientId());
        if (recipientOpt.isEmpty()) return;

        UserRecord sender = senderOpt.get();
        UserRecord recipient = recipientOpt.get();

        float amount = tx.getAmount();
        if (sender.getBalance() < amount) return;

        // Save transaction record
        transactionRecordRepository.save(new TransactionRecord(sender, recipient, amount));

        // Update balances
        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        // Save updated users (save does update too)
        userRepository.save(sender);
        userRepository.save(recipient);
    }
}
