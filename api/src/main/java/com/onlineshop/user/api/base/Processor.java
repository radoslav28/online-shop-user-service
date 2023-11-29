package com.onlineshop.user.api.base;

public interface Processor<I extends ProcessorInput, R extends ProcessorResult>{
    R process(I input);
}
