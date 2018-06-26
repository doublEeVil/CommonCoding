// package com._22evil.singleton;

// import java.util.function.Supplier;

// /**
//  * 工厂模式实现 
//  * 这种实现方式有问题
//  */

// public class Singleton6 {
//     private static Supplier<Singleton6> instance = Singleton6::createInstance;

//     private Singleton6() {

//     }

//     public static Singleton6 getInstance() {
//         return instance.get();
//     }

//     private static Singleton6 createInstance() {
//         class ClassFactory implements Supplier<Singleton6> {
//             private final Singleton6 singleton6 = new Singleton6();
//             public Singleton6 get() {
//                 return singleton6;
//             }
//         }
//         if (!ClassFactory.class.isInstance(instance)) {
//             instance = new ClassFactory();
//         }
//         return instance.get();
//     }
// }