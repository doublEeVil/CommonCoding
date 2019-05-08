package com._22evil.stone;

import com._22evil.stone.ast.ASTree;

import java.util.HashMap;
import java.util.Set;

public class Parser {

    public static Parser rule() {
        return null;
    }

    public static Parser rule(Class c) {
        return null;
    }

    public ASTree parse(Lexer lexer) {
        return null;
    }

    public Parser number() {
        return number();
    }

    public Parser number(Class c) {
        return null;
    }

    public Parser identifier(Set<String> set) {
        return null;
    }

    public Parser identifier(Class c, Set<String> set) {
        return null;
    }

    public Parser string() {
        return null;
    }

    public Parser string(Class c) {
        return null;
    }

    public Parser token() {
        return null;
    }

    public Parser sep(String ... pat) {
        return null;
    }

    public Parser ast(Parser t) {
        return null;
    }

    public Parser option(Parser p) {
        return null;
    }

    public Parser maybe(Parser p) {
        return null;
    }

    public Parser or(Parser ... p) {
        return null;
    }

    public Parser repeat(Parser p) {
        return null;
    }

    public Parser expression(Parser p, Operators op) {
        return null;
    }

    public Parser expression(Class c, Parser p, Operators op) {
        return null;
    }

    public Parser reset() {
        return this;
    }

    public Parser reset(Class c) {
        return this;
    }

    public Parser insertChoice(Parser p) {
        return this;
    }


    public static class Precedence {
        int value;
        boolean leftAssoc;

        public Precedence(int value, boolean leftAssoc) {
            this.value = value;
            this.leftAssoc = leftAssoc;
        }
    }

    public static class Operators extends HashMap<String, Precedence> {
        public static boolean LEFT = true;
        public static boolean RIGHT = false;

        public void add(String name, int prec, boolean leftAssoc) {
            put(name, new Precedence(prec, leftAssoc));
        }
    }

}
