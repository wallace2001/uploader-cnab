package com.wallace.uploadcnab.fixture;

import com.wallace.uploadcnab.domain.TypeOperation;
import com.wallace.uploadcnab.util.Utils;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TypeOperationFixture {

    private final Random random = new Random();

    public TypeOperation create() {
        TypeOperation typeOperation = new TypeOperation();
        typeOperation.setId(random.nextLong(2));
        typeOperation.setDescription(Utils.randomStringSimple(10));
        typeOperation.setNature(Utils.randomStringSimple(4));
        typeOperation.setCod(random.nextLong(1));
        typeOperation.setSignal(random.nextInt(2) == 0 ? "+" : "-");

        return typeOperation;
    }
}
