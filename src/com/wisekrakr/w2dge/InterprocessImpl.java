package com.wisekrakr.w2dge;

public interface InterprocessImpl<T extends InterprocessImpl<T>> {

    T copy();
}
