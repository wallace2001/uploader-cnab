package com.wallace.uploadcnab.fixture;

import com.wallace.uploadcnab.domain.Operation;
import com.wallace.uploadcnab.domain.TypeOperation;
import com.wallace.uploadcnab.repository.OperationRepository;
import com.wallace.uploadcnab.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Component
public class OperationFixture {

    private final Random random = new Random();

    public Operation create() {
        Operation operation = new Operation();
        operation.setId(UUID.randomUUID());
        operation.setDocument(Utils.randomStringSimple(11));
        operation.setCardNumber(Utils.randomStringSimple(12));
        operation.setValue("1234567890");
        operation.setDate(Timestamp.valueOf(LocalDateTime.now()));
        operation.setNameStore(Utils.randomStringSimple(19));
        operation.setStoreOwner(Utils.randomStringSimple(14));
        operation.setType(createTypeOperation());

        return operation;
    }

    private TypeOperation createTypeOperation() {
        TypeOperation typeOperation = new TypeOperation();
        typeOperation.setId(random.nextLong(2));
        typeOperation.setDescription(Utils.randomStringSimple(10));
        typeOperation.setNature(Utils.randomStringSimple(4));
        typeOperation.setCod(random.nextLong(1));
        typeOperation.setSignal(random.nextInt(2) == 0 ? "+" : "-");

        return typeOperation;
    }
}
