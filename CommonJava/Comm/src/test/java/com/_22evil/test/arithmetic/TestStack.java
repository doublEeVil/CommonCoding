package com._22evil.test.arithmetic;

import com._22evil.arithmetic.TStack;
import org.junit.Test;

/**
 * 栈测试
 * 计算器的实现
 * 1. 中缀表达式转后缀表达式
 * 2. 后缀表达式转计算
 */
public class TestStack {

    /**
     * 只计算10数字下，不含小数，负数的简单表达式
     */
    @Test
    public void testCalculate() {
        String input = "(1 + 4 * 7) - 8 /  2 + 4";
        String postfix = postfix(input);
        System.out.println(postfix);
        int result = calculate(postfix);
        System.out.println(input + " = " + result);
    }

    /**
     * 中缀转后缀表达式
     * 规则，如果遇到优先级比栈顶优先级小的或者相同，则出栈
     * 为空，进栈
     * @param input
     * @return
     */
    private String postfix(String input) {
        char[] chars = input.replace(" ", "").toCharArray();
        TStack<Character> stack = new TStack<>(20);
        StringBuilder sb = new StringBuilder();
        char tmp;
        for (char c : chars) {
            switch (c) {
                case '(':
                    // 直接进栈
                    stack.push(c);
                    break;
                case ')':
                    // 出栈，直到遇到 ')'
                    while (true) {
                        tmp = stack.pop();
                        if (tmp == '(') {
                            break;
                        }
                        sb.append(tmp);
                    }
                    break;
                case '+':
                case '-':
                    while (true) {
                        // 如果空直接进栈
                        if (stack.isEmpty()) {
                            stack.push(c);
                            break;
                        }
                        tmp = stack.peek();
                        if (tmp == '+' || tmp == '-' || tmp == '*' || tmp == '/') {
                            sb.append(stack.pop());
                        } else {
                            stack.push(c);
                            break;
                        }
                    }
                    break;
                case '*':
                case '/':
                    while (true) {
                        // 如果空直接进栈
                        if (stack.isEmpty()) {
                            stack.push(c);
                            break;
                        }
                        tmp = stack.peek();
                        if (tmp == '*' || tmp == '/') {
                            sb.append(stack.pop());
                        } else {
                            stack.push(c);
                            break;
                        }
                    }
                    break;
                default: sb.append(c);
            }
        }
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }

    /**
     * 后缀表达式计算结果
     * @param postfix
     * @return
     */
    private int calculate(String postfix) {
        TStack<Integer> stack = new TStack<>(20);
        char[] chars = postfix.toCharArray();
        int a,b;
        for (char c : chars) {
            switch (c) {
                case '+':
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(a + b);
                    break;
                case '-':
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(b - a);
                    break;
                case '*':
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(a * b);
                    break;
                case '/':
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(b / a);
                    break;
                default:
                    stack.push(c - 48); // ASCII表0-9的范围是[48,57]
            }
        }
        return stack.pop();
    }
}
