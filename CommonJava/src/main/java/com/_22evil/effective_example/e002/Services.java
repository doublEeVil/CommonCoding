package com._22evil.effective_example.e002;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Services {
    private static Map<String, Provider> providers = new ConcurrentHashMap<>();
    private static String DEDAULT_PROVIDER = "<>";

    static {
        registerProvider(DEDAULT_PROVIDER, new Provider(){
        
            @Override
            public Service newService() {
                return new Service() {
                };
            }
        });
    }

    public static void registerProvider(String name, Provider p) {
        providers.put(name, p);
    }

    public static Service newInstance() {
        return newInService(DEDAULT_PROVIDER);
    }

    public static Service newInService(String name) {
        Provider p = providers.get(name);
        if (p == null) {
            throw new RuntimeException("");
        }
        return p.newService();
    }
}