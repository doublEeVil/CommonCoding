package com._22evil.test.error;
import com._22evil.test.error.thread.CommonThread;
/**
 * 模拟线上出了问题
 * 1. 死锁
 * 2. 死循环
 * 3. 长时间等待
 * 4. 线程挂起/被Kill
 *
 * 运行命令 jstack [pid]
 * --------------111111111111111死锁情况----------------
 * Found one Java-level deadlock:
 =============================
 "Thread-2":
 waiting to lock monitor 0x000000005a52d168 (object 0x00000000d7992d10, a com._22evil.test.error.thread.CommonThread$O
 j),
 which is held by "Thread-1"
 "Thread-1":
 waiting to lock monitor 0x00000000577c2528 (object 0x00000000d7992d20, a com._22evil.test.error.thread.CommonThread$O
 j),
 which is held by "Thread-2"

 Java stack information for the threads listed above:
 ===================================================
 "Thread-2":
 at com._22evil.test.error.thread.CommonThread.lambda$deadLock$1(CommonThread.java:63)
 - waiting to lock <0x00000000d7992d10> (a com._22evil.test.error.thread.CommonThread$Obj)
 - locked <0x00000000d7992d20> (a com._22evil.test.error.thread.CommonThread$Obj)
 at com._22evil.test.error.thread.CommonThread$$Lambda$2/65539004.run(Unknown Source)
 at java.lang.Thread.run(Thread.java:748)
 "Thread-1":
 at com._22evil.test.error.thread.CommonThread.lambda$deadLock$0(CommonThread.java:48)
 - waiting to lock <0x00000000d7992d20> (a com._22evil.test.error.thread.CommonThread$Obj)
 - locked <0x00000000d7992d10> (a com._22evil.test.error.thread.CommonThread$Obj)
 at com._22evil.test.error.thread.CommonThread$$Lambda$1/888257875.run(Unknown Source)
 at java.lang.Thread.run(Thread.java:748)

 Found 1 deadlock.

 ----------------------------2222222222死循环情况----------------------------
 "CommonThread" #11 prio=5 os_prio=0 tid=0x000000005a44d800 nid=0x49cc runnable [0x000000005b38f000]
 java.lang.Thread.State: RUNNABLE
 at com._22evil.test.error.thread.CommonThread.deadLoop(CommonThread.java:71)
 at com._22evil.test.error.thread.CommonThread.run(CommonThread.java:26)
 at java.lang.Thread.run(Thread.java:748)

 相隔一段时间打印堆栈，可以发现这个线程一直是RUNNABLE ，那么有可能是死循环

 ----------------------------3333333333长时间等待情况--------------------------
 "CommonThread" #11 prio=5 os_prio=0 tid=0x000000005a0d3800 nid=0x8558 runnable [0x000000005ae9e000]
 java.lang.Thread.State: RUNNABLE
 at com._22evil.test.error.thread.CommonThread.longWait(CommonThread.java:81)
 at com._22evil.test.error.thread.CommonThread.run(CommonThread.java:31)
 at java.lang.Thread.run(Thread.java:748)

 第一次查是上面的情况
 第二次就发现没有上面的信息了（n秒后查询）

 */
public class ErrorApp {

    public static void main(String[] args) {
        CommonThread thread = new CommonThread(ThreadType.KILLED);
        thread.start();
        while (true) {

        }
    }
}
