package com._22evil.effective_example.e003;

/**
 * builder模式
 */
public class Stu {
    private long id;
    private int age;
    private String name;
    private String addr;

    private Stu(Build build) {
        id = build.id;
        age = build.age;
        name = build.name;
        addr = build.addr; 
    }

    public static void main(String[] args) {
        Stu stu = new Stu.Build().age(12).name("aaa").build();
    }

    public static class Build {
        private long id;
        private int age;
        private String name;
        private String addr;

        public Build id(long val) {
            this.id =val;
            return this;
        }

        public Build age(int val) {
            this.age =val;
            return this;
        }

        public Build name(String val) {
            this.name = val;
            return this;
        }

        public Build addr(String val) {
            this.addr = addr;
            return this;
        }

        public Stu build() {
            return new Stu(this);
        }
    }
}