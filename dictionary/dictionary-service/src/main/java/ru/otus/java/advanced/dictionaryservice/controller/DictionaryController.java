package ru.otus.java.advanced.dictionaryservice.controller;

import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.otus.advanced.proto.*;
import ru.otus.java.advanced.dictionaryservice.entity.Dictionary;
import ru.otus.java.advanced.dictionaryservice.repository.DictionaryRepository;

import java.util.UUID;
import java.util.stream.Collectors;

@GrpcService
@RequiredArgsConstructor
public class DictionaryController extends DictionaryServiceGrpc.DictionaryServiceImplBase {

    private final DictionaryRepository dictionaryRepository;

    @Override
    public void getDictionaryById(DictionaryByIdRequest request, StreamObserver<DictionaryResponse> responseObserver) {
        dictionaryRepository.findById(UUID.fromString(request.getId()))
        .ifPresent(dictionary -> {
            responseObserver.onNext(toDictionaryResponse(dictionary));
            responseObserver.onCompleted();
        });
        responseObserver.onError(new StatusException(Status.NOT_FOUND));
    }

    @Override
    public void getDictionaryByCode(DictionaryByCodeRequest request, StreamObserver<DictionaryResponse> responseObserver) {
        dictionaryRepository.findByCode(request.getCode())
                .ifPresent(dictionary -> {
                    responseObserver.onNext(toDictionaryResponse(dictionary));
                    responseObserver.onCompleted();
        });
        responseObserver.onError(new StatusException(Status.NOT_FOUND));
    }

    private DictionaryResponse toDictionaryResponse(Dictionary dictionary) {
        return DictionaryResponse
                .newBuilder()
                .setId(String.valueOf(dictionary.getId()))
                .setCategoryId(String.valueOf(dictionary.getCategoryId()))
                .setCode(dictionary.getCode())
                .setBCode(dictionary.getBCode())
                .setName(dictionary.getName())
                .setDeleted(dictionary.getDeleted())
                .build();
    }

    @Override
    public void getDictionaryByCategoryBCode(DictionaryByCategoryBCodeRequest request, StreamObserver<DictionaryByCategoryBCodeResponse> responseObserver) {
        responseObserver.onNext(
            DictionaryByCategoryBCodeResponse
                    .newBuilder()
                    .addAllDictionaryResponse(
                            dictionaryRepository.findAllByCategoryBCode(request.getBCode())
                                    .stream()
                                    .map(this::toDictionaryResponse)
                                    .collect(Collectors.toList()))
                    .build());
                    responseObserver.onCompleted();
    }
}
