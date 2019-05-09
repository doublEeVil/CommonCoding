package com._22evil.stone;

import com._22evil.stone.ast.ASTLeaf;
import com._22evil.stone.ast.ASTList;
import com._22evil.stone.ast.ASTree;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

public class Parser {

    public static abstract class Element {
        protected abstract void parse(Lexer lexer, List<ASTree> res) throws ParseException;
        protected abstract boolean match(Lexer lexer) throws ParseException;
    }

    protected static class Tree extends Element {
        protected Parser parser;
        protected  Tree(Parser parser) {
            this.parser = parser;
        }
        @Override
        protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
            res.add(parser.parse(lexer));
        }

        @Override
        protected boolean match(Lexer lexer) throws ParseException {
            return parser.match(lexer);
        }
    }

    protected static class OrTree extends Element {
        protected Parser[] parsers;

        public OrTree(Parser[] parsers) {
            this.parsers = parsers;
        }

        @Override
        protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
            Parser p = choose(lexer);
            if (p == null) {
                throw new ParseException(lexer.peek(0));
            } else {
                res.add(p.parse(lexer));
            }
        }

        @Override
        protected boolean match(Lexer lexer) throws ParseException {
            return choose(lexer) != null;
        }

        protected Parser choose(Lexer lexer) throws ParseException{
            for (Parser p : parsers) {
                if (p.match(lexer)) {
                    return p;
                }
            }
            return null;
        }

        protected void insert(Parser p) {
            Parser[] newPasers = new Parser[parsers.length + 1];
            newPasers[0] = p;
            System.arraycopy(parsers, 0, newPasers, 1, parsers.length);
            parsers = newPasers;
        }
    }

    protected static class Repeat extends Element {
        protected Parser parser;
        protected boolean onlyOnce;

        public Repeat(Parser parser, boolean onlyOnce) {
            this.parser = parser;
            this.onlyOnce = onlyOnce;
        }

        @Override
        protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
            while (parser.match(lexer)) {
                ASTree t = parser.parse(lexer);
                if (t.getClass() != ASTList.class || t.numChildren() > 0) {
                    res.add(t);
                }
                if (onlyOnce)
                    break;
            }
        }

        @Override
        protected boolean match(Lexer lexer) throws ParseException {
            return parser.match(lexer);
        }
    }

    public static final String factoryName = "create";
    protected static abstract class Factory {
        protected abstract ASTree make0(Object arg) throws Exception;
        protected ASTree make(Object arg) {
            try {
                return make0(arg);
            } catch (IllegalArgumentException e1) {
                throw e1;
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }
        protected static Factory getForASTList(Class<? extends ASTree> clazz) {
            Factory factory = get(clazz, List.class);
            if (factory == null) {
                factory = new Factory() {
                    @Override
                    protected ASTree make0(Object arg) throws Exception {
                        List<ASTree> results = (List<ASTree>) arg;
                        if (results.size() == 1) {
                            return results.get(0);
                        } else {
                            return new ASTList(results);
                        }
                    }
                };
            }
            return factory;
        }

        protected static Factory get(Class<? extends ASTree> clazz, Class<?> argType) {
            if (clazz == null) {
                return null;
            }
            try {
                final Method m = clazz.getMethod(factoryName, new Class<?>[]{ argType});
                return new Factory() {
                    @Override
                    protected ASTree make0(Object arg) throws Exception {
                        return (ASTree)m.invoke(null, arg);
                    }
                };
            } catch (NoSuchMethodException e) {
            }
            try {
                final Constructor<? extends ASTree> c = clazz.getConstructor(argType);
                return new Factory() {
                    @Override
                    protected ASTree make0(Object arg) throws Exception {
                        return c.newInstance(arg);
                    }
                };
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected static abstract class AToken extends Element {
        protected Factory factory;
        protected AToken(Class<? extends ASTLeaf> type) {
            if (type == null) {
                type = ASTLeaf.class;
            }
            factory = Factory.get(type, Token.class);
        }

        protected void parse(Lexer lexer, List<ASTree> res) throws ParseException{
            Token token = lexer.read();
            if (test(token)) {
                ASTree leaf = factory.make(token);
                res.add(leaf);
            }
        }

        @Override
        protected boolean match(Lexer lexer) throws ParseException {
            return test(lexer.peek(0));
        }

        protected abstract boolean test(Token t);
    }

    protected static class IdToken extends AToken {

        Set<String> reserved;

        public IdToken(Class<? extends ASTLeaf> type, Set<String> r) {
            super(type);
            reserved = r != null ? r : new HashSet<>();
        }

        @Override
        protected boolean test(Token t) {
            return false;
        }
    }

    protected static class NumToken extends AToken {
        public NumToken(Class<? extends ASTLeaf> type) {
            super(type);
        }

        @Override
        protected boolean test(Token t) {
            return t.isNumber();
        }
    }

    protected static class StrToken extends AToken {
        public StrToken(Class<? extends ASTLeaf> type) {
            super(type);
        }

        @Override
        protected boolean test(Token t) {
            return t.isString();
        }
    }

    protected static class Leaf extends Element {
        protected String[] tokens;

        public Leaf(String[] tokens) {
            this.tokens = tokens;
        }

        @Override
        protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
            Token t = lexer.read();
            if (t.isIdentifier()) {
                for (String token : tokens) {
                    if (token.equals(t.getText())) {
                        find(res, t);
                        return;
                    }
                }
            }
            if (tokens.length > 0) {
                throw new ParseException(tokens[0] + " expected.", t);
            } else {
                throw new ParseException(t);
            }
        }

        @Override
        protected boolean match(Lexer lexer) throws ParseException {
            Token t = lexer.peek(0);
            if (t.isIdentifier()) {
                for (String token : tokens) {
                    if (token.equals(t.getText())) {
                        return true;
                    }
                }
            }
            return false;
        }

        protected void find(List<ASTree> res, Token t) {
            res.add(new ASTLeaf(t));
        }
    }

    protected static class Skip extends Leaf {
        public Skip(String[] tokens) {
            super(tokens);
        }

        protected void find(List<ASTree> res, Token t) {

        }
    }

    protected static class Expr extends Element {
        protected Factory factory;
        protected Operators ops;
        protected Parser factor;

        protected Expr(Class<? extends ASTree> clazz, Parser exp, Operators map) {
            factory = Factory.getForASTList(clazz);
            ops = map;
            factor = exp;
        }

        @Override
        protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
            ASTree right = factor.parse(lexer);
            Precedence prec;
            while ((prec = nextOperator(lexer)) != null)
                right = doShift(lexer, right, prec.value);
            res.add(right);
        }

        private ASTree doShift(Lexer lexer, ASTree left, int prec) throws ParseException {
            List<ASTree> list = new ArrayList<>();
            list.add(left);
            list.add(new ASTLeaf(lexer.read()));
            ASTree right = factor.parse(lexer);
            Precedence next;
            while ((next = nextOperator(lexer)) != null && rightIsExpr(prec, next))
                right = doShift(lexer, right, next.value);
            list.add(right);
            return factory.make(list);
        }

        private Precedence nextOperator(Lexer lexer) throws ParseException {
            Token t =lexer.peek(0);
            if (t.isIdentifier())
                return ops.get(t.getText());
            else
                return null;
        }

        private static boolean rightIsExpr(int prec, Precedence nextPrec) {
            if (nextPrec.leftAssoc)
                return prec < nextPrec.value;
            else
                return prec <= nextPrec.value;
        }

        @Override
        protected boolean match(Lexer lexer) throws ParseException {
            return factor.match(lexer);
        }
    }

    protected List<Element> elements;
    protected Factory factory;

    public Parser(Class<? extends ASTree> clazz) {
        reset(clazz);
    }

    protected Parser(Parser p) {
        elements = p.elements;
        factory = p.factory;
    }

    public ASTree parse(Lexer lexer) throws ParseException {
        List<ASTree> results = new ArrayList<>();
        for (Element e : elements) {
            e.parse(lexer, results);
        }
        return factory.make(results);
    }

    public boolean match(Lexer lexer) throws ParseException{
        if (elements.size() == 0)
            return true;
        else {
            Element e = elements.get(0);
            return e.match(lexer);
        }
    }

    public static Parser rule() {
        return rule(null);
    }

    public static Parser rule(Class<? extends ASTree> c) {
        return new Parser(c);
    }



    public Parser number() {
        return number(null);
    }

    public Parser number(Class<? extends ASTLeaf> c) {
        elements.add(new NumToken(c));
        return this;
    }

    public Parser identifier(Set<String> reserved) {
        return identifier(null, reserved);
    }

    public Parser identifier(Class c, Set<String> set) {
        elements.add(new IdToken(c, set));
        return this;
    }

    public Parser string() {
        return string(null);
    }

    public Parser string(Class c) {
        elements.add(new StrToken(c));
        return this;
    }

    public Parser token(String ... pat) {
        elements.add(new Leaf(pat));
        return this;
    }

    public Parser sep(String ... pat) {
        elements.add(new Skip(pat));
        return this;
    }

    public Parser ast(Parser parser) {
        elements.add(new Tree(parser));
        return this;
    }

    public Parser option(Parser p) {
        elements.add(new Repeat(p, true));
        return this;
    }

    public Parser maybe(Parser p) {
        Parser p2 = new Parser(p);
        p2.reset();
        elements.add(new OrTree(new Parser[]{p, p2}));
        return this;
    }

    public Parser or(Parser ... p) {
        elements.add(new OrTree(p));
        return this;
    }

    public Parser repeat(Parser p) {
        elements.add(new Repeat(p, false));
        return this;
    }

    public Parser expression(Parser p, Operators op) {
        elements.add(new Expr(null, p, op));
        return this;
    }

    public Parser expression(Class c, Parser p, Operators op) {
        elements.add(new Expr(c, p, op));
        return this;
    }

    public Parser reset() {
        elements = new ArrayList<>();
        return this;
    }

    public Parser reset(Class c) {
        elements = new ArrayList<>();
        factory = Factory.getForASTList(c);
        return this;
    }

    public Parser insertChoice(Parser p) {
        Element e = elements.get(0);
        if (e instanceof OrTree)
            ((OrTree)e).insert(p);
        else {
            Parser otherwise = new Parser(p);
            reset(null);
            or(p, otherwise);
        }
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
