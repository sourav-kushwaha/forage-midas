// package com.jpmc.midascore.component;

// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.stereotype.Component;

// import com.jpmc.midascore.foundation.Transaction;

// @Component
// public class TransactionKafkaListener {

//     @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core")
//     public void onMessage(Transaction tx) {
//         float amount = tx.getAmount();
//         // No-op for now
//         // Put breakpoint here later for TaskTwoTests to record tx.getAmount()
//     }
// }


package com.jpmc.midascore.component;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.jpmc.midascore.foundation.Transaction;

@Component
public class TransactionKafkaListener {

    private final TransactionProcessor processor;

    public TransactionKafkaListener(TransactionProcessor processor) {
        this.processor = processor;
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core")
    public void onMessage(Transaction tx) {
        processor.process(tx);
    }
}
