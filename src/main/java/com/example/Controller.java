package com.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@RestController
@RequestMapping("calc")
@RequiredArgsConstructor
public class Controller {
    private final CalcService calcService;
    private final SlowServiceOne slowServiceOne;

    @GetMapping("/all")
    public String calcualte() {
        log.info("*** controller: " + Thread.currentThread().getId());
        return Thread.currentThread().getId() + " " + calcService.calcualte();
    }

    @GetMapping("/chain")
    public String chain() throws ExecutionException, InterruptedException {
        log.info("*** chain: " + Thread.currentThread().getId());

        CompletableFuture<Integer> result1 = slowServiceOne.operation(1000);
        CompletableFuture<Integer> result2 = slowServiceOne.operation(1000 + result1.get());
        CompletableFuture<Integer> result3 = slowServiceOne.operation(1000 + result2.get());

        log.info("*** join1: ");
        CompletableFuture.allOf(result1, result2, result3).join();
        //log.info("*** join2: ");
        //Long total = result1.get() + result2.get() + result3.get();
        log.info("*** completed: ");
        return Thread.currentThread().getId() + " " + calcService.calcualte();
    }

    @GetMapping("/chain0")
    public String chain0() throws ExecutionException, InterruptedException {
        log.info("*** chain: =========================================================");
        CompletableFuture<Integer> futureOne = slowServiceOne.operationOne();
        log.info("*** join1: ");
        futureOne.thenApply(aLong -> slowServiceOne.operation(1000 + aLong).thenApply(aLong1 -> slowServiceOne.operation(1000 + aLong1))).join();
        Thread.yield();
        log.info("*** end nu get: ");
        return Thread.currentThread().getId() + "";
    }

    @GetMapping("/chain1")
    public String chain1() throws ExecutionException, InterruptedException {
        log.info("*** chain: =========================================================");
        CompletableFuture<Integer> futureOne = slowServiceOne.operationOne();
        log.info("*** join1: ");
        futureOne.thenApply(aLong -> slowServiceOne.operationTwo().thenApply(aLong1 -> slowServiceOne.operationThree())).join();
        Thread.yield();
        log.info("*** end nu get: ");
        return Thread.currentThread().getId() + "";
    }

    @GetMapping("/chain2")
    public String chain2() throws ExecutionException, InterruptedException {
        log.info("*** chain: =========================================================");
        CompletableFuture<Integer> futureOne = slowServiceOne.operationOne();
        log.info("*** join1: ");
        futureOne.thenApply(aLong -> slowServiceOne.operationTwo().thenApply(aLong1 -> slowServiceOne.operationThree())).join();
        Thread.yield();
        log.info("*** futureOne: get: " + futureOne.get());
        return Thread.currentThread().getId() + "";
    }

    @GetMapping("/chain3")
    public String chain3() throws ExecutionException, InterruptedException {
        log.info("*** chain: =========================================================");
        CompletableFuture<Integer> futureOne = slowServiceOne.operationOne();
        CompletableFuture<Integer> futureTwo = slowServiceOne.operationTwo();
        CompletableFuture<Integer> futureThree = slowServiceOne.operationThree();
        log.info("*** join1: ");
        futureOne.thenApply(aLong -> futureTwo.thenApply(aLong1 -> futureThree)).join();
        Thread.yield();
        log.info("*** futures: get: " + futureOne.get());
        return Thread.currentThread().getId() + "";
    }

    @GetMapping("/chain4")
    public String chain4() throws ExecutionException, InterruptedException {
        log.info("*** chain: =========================================================");
        CompletableFuture<Integer> futureOne = slowServiceOne.operationOne();
        CompletableFuture<Integer> futureTwo = slowServiceOne.operationTwo();
        CompletableFuture<Integer> futureThree = slowServiceOne.operationThree();
        log.info("*** join1: ");
        futureOne.thenApply(aLong -> futureTwo.thenApply(aLong1 -> futureThree)).join();
        Thread.yield();
        log.info("*** futures: get: " + futureOne.get() + futureTwo.get() + futureThree.get());
        return Thread.currentThread().getId() + "";
    }

    @GetMapping("/chain5")
    public String chain5() throws ExecutionException, InterruptedException {
        log.info("*** chain: =========================================================");
        CompletableFuture<Integer> futureOne = slowServiceOne.operationOne();
        CompletableFuture<Integer> futureTwo = slowServiceOne.operationTwo();
        CompletableFuture<Integer> futureThree = slowServiceOne.operationThree();
        log.info("*** join1: ");
        CompletableFuture.allOf(futureOne, futureTwo, futureThree);
        Thread.yield();
        log.info("*** futures: join: ");
        return Thread.currentThread().getId() + "";
    }

    @GetMapping("/sleep")
    public String sleep() {
        log.info("*** controller sleep: " + Thread.currentThread().getId());
        calcService.sleep();
        return "sleep: " + Thread.currentThread().getId() + " | " + System.currentTimeMillis();
    }
}
