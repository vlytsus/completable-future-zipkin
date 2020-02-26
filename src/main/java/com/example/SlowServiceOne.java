package com.example;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Slf4j
@Service
public class SlowServiceOne {
    @SneakyThrows
    @Async
    CompletableFuture<Integer> operation(Integer delay){
        log.info("operation <<< " + delay);
        try {
            Thread.sleep(delay);
            return CompletableFuture.completedFuture(delay);
        }finally {
            log.info("operation >>> " + delay);
        }
    }

    @SneakyThrows
    @Async
    CompletableFuture<Integer> operationOne(){
        Integer delay = 1000;
        log.info("operation <<< " + delay);
        try {
            Thread.sleep(delay);
            return CompletableFuture.completedFuture(delay);
        }finally {
            log.info("operation >>> " + delay);
        }
    }

    @SneakyThrows
    @Async
    CompletableFuture<Integer> operationTwo(){
        Integer delay = 2000;
        log.info("operation <<< " + delay);
        try {
            Thread.sleep(delay);
            return CompletableFuture.completedFuture(delay);
        }finally {
            log.info("operation >>> " + delay);
        }
    }

    @SneakyThrows
    @Async
    CompletableFuture<Integer> operationThree(){
        Integer delay = 3000;
        log.info("operation <<< " + delay);
        try {
            Thread.sleep(delay);
            return CompletableFuture.completedFuture(delay);
        }finally {
            log.info("operation >>> " + delay);
        }
    }
}
