package com._22evil.test.error;
public enum ThreadType {
    DEAD_LOCK,  //死锁
    DEAD_LOOP,  //死循环
    LONG_WAIT,  //长时间等待
    KILLED      //被中断
}
