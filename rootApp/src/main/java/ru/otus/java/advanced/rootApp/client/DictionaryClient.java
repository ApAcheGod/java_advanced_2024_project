package ru.otus.java.advanced.rootApp.client;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.otus.advanced.proto.*;

import java.util.UUID;

@Component
@Slf4j
public class DictionaryClient {

    @GrpcClient(value = "dictionary-service-grpc")
    private DictionaryServiceGrpc.DictionaryServiceBlockingStub dictionaryService;

    public DictionaryResponse getDictionaryById(UUID id) {
        log.info("get dictionary by id: {}", id);
        return dictionaryService.getDictionaryById(
                DictionaryByIdRequest
                        .newBuilder()
                        .setId(id.toString())
                        .build());
    }

    public DictionaryResponse getDictionaryByCode(String code) {
        log.info("get dictionary by code: {}", code);
        return dictionaryService.getDictionaryByCode(
                DictionaryByCodeRequest
                        .newBuilder()
                        .setCode(code)
                        .build());
    }

    public DictionaryByCategoryBCodeResponse getDictionaryByCategoryBCode(String categoryBCode) {
        log.info("get dictionary by category code: {}", categoryBCode);
        return dictionaryService.getDictionaryByCategoryBCode(
                DictionaryByCategoryBCodeRequest
                        .newBuilder()
                        .setBCode(categoryBCode)
                        .build());
    }

}
