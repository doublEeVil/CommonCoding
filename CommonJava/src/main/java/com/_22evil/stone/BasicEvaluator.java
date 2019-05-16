package com._22evil.stone;

import com._22evil.stone.ast.*;
import javassist.gluonj.Reviser;

import java.util.List;

@Reviser
public class BasicEvaluator {
    public static final int TRUE = 1;
    public static final int FALSE = 0;

    @Reviser
    public static abstract class ASTreeEx extends ASTree {
        public abstract Object eval(Environment env);
    }

    @Reviser
    public static class ASTListEx extends ASTList {
        public ASTListEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment env) {
            throw new StoneException("cannot eval: " + toString(), this);
        }
    }

    @Reviser
    public static class ASTLeafEx extends ASTLeaf {
        public ASTLeafEx(Token token) {
            super(token);
        }

        public Object eval(Environment env) {
            throw new StoneException("cannot eval: " + toString(), this);
        }
    }

    @Reviser
    public static class NumberEx extends NumberLiteral {
        public NumberEx(Token token) {
            super(token);
        }

        public Object eval(Environment env) {
            return value();
        }
    }

    @Reviser
    public static class StringEx extends StringLiteral {
        public StringEx(Token token) {
            super(token);
        }

        public Object eval(Environment env) {
            return value();
        }
    }

    @Reviser
    public static class NameEx extends Name {
        public NameEx(Token token) {
            super(token);
        }

        public Object eval(Environment env) {
            Object val = env.get(name());
            if (val == null)
                throw new StoneException("undefined name: " + name(), this);
            else
                return val;
        }
    }

    @Reviser
    public static class NegativeEx extends NegativeExpr {
        public NegativeEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment env) {
            Object val = ((ASTreeEx)operand()).eval(env);
            if (val instanceof Integer)
                return new Integer(-((Integer)(val)).intValue());
            else
                throw new StoneException("bad type for -", this);
        }
    }

    @Reviser
    public static class BinaryEx extends BinaryExpr {
        public BinaryEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment env) {
            String op = operator();
            if ("=".equals(op)) {
                Object right = ((ASTreeEx)right()).eval(env);
                return computeAssign(env, right);
            } else {
                Object left = ((ASTreeEx)left()).eval(env);
                Object right = ((ASTreeEx)right()).eval(env);
                return computeOp(left, op, right);
            }
        }

        protected Object computeAssign(Environment env, Object rvalue) {
            ASTree l = left();
            if (l instanceof Name) {
                env.put(((Name)l).name(), rvalue);
                return rvalue;
            } else
                throw new StoneException("bad assignment");
        }

        protected Object computeOp(Object left, String op, Object right) {
            if (left instanceof Integer && right instanceof Integer) {
                return computeNumber((Integer) left, op, (Integer) right);
            } else if (op.equals("+"))
                return String.valueOf(left) + String.valueOf(right);
            else if (op.equals("==")) {
                if (left == null)
                    return right == null ? TRUE : FALSE;
                else
                    return left.equals(right) ? TRUE : FALSE;
            } else
                throw new StoneException("bad type ", this);
        }

        protected Object computeNumber(Integer left, String op, Integer right) {
            int a = left.intValue();
            int b = right.intValue();
            if (op.equals("+"))
                return a + b;
            else if (op.equals("-"))
                return a - b;
            else if (op.equals("*"))
                return a * b;
            else if (op.equals("/"))
                return a / b;
            else if (op.equals("%"))
                return a % b;
            else if (op.equals("=="))
                return a == b ? TRUE : FALSE;
            else if (op.equals(">"))
                return a > b ? TRUE : FALSE;
            else if (op.equals("<"))
                return a < b ? TRUE : FALSE;
            else
                throw new StoneException("bad operator", this);
        }
    }

    @Reviser
    public static class BlockEx extends BlockStmnt {
        public BlockEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment env) {
            Object val = 0;
            for (ASTree t : this) {
                if (t instanceof NullStmnt)
                    continue;
                val = ((ASTreeEx)t).eval(env);
            }
            return val;
        }
    }

    @Reviser
    public static class IfEx extends IfStmnt {
        public IfEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment env) {
            Object c = ((ASTreeEx)condition()).eval(env);
            if (c instanceof Integer && ((Integer) c).intValue() != FALSE)
                return ((ASTreeEx)thenBlock()).eval(env);
            else {
                ASTree b = elseBlock();
                if (b == null)
                    return 0;
                else
                    return ((ASTreeEx)b).eval(env);
            }
        }
    }

    @Reviser
    public static class WhileEx extends WhileStmnt {
        public WhileEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment env) {
            Object result = 0;
            for (;;) {
                Object c = ((ASTreeEx)condition()).eval(env);
                if (c instanceof Integer && (((Integer) c).intValue()) == FALSE)
                    return result;
                else
                    result = ((ASTreeEx)body()).eval(env);
            }
        }
    }
}
