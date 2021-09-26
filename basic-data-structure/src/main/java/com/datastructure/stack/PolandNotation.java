package com.datastructure.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PolandNotation {
    public static void main(String[] args) {
        //将中缀表达式转换为后缀表达式
        //1. 1+((2-3)*4）-5 =》 1 2 3 + 4 x + 5
        String expression = "1+((2-3)*4)-5";

        List<String> arrayExpression2 = toArrayExpressionList(expression);

        System.out.println("-----=======" + arrayExpression2);

        List<String> suffixEpression2 = parseSuffixExpression(arrayExpression2);

        System.out.println("suffix-----" + suffixEpression2);


//        String suffixExpression = "3 4 + 5 * 6 -";
        String suffixExpression = "30 4 + 5 * 6 -";

        //1. 先将 expression 放到arraylist
        //2. 将arraylist 传递给一个方法，遍历arraylist 配合栈完成计算

        List<String> list = getListString(suffixExpression);
        System.out.println("rpnlist=" + list);

        int res = calculate(list);
        System.out.println("list res = " + res);


    }

    public static List<String> parseSuffixExpression(List<String> ls) {
        Stack<String> s1 = new Stack<String>();
        List<String> s2 = new ArrayList<String>();

        for (String item : ls) {
            if (item.matches("\\d+")) {
                s2.add(item);
            } else if (item.equals("(")) {
                s1.push(item);
            } else if (item.equals(")")) {
                //如果遇到）， 则依次弹出s1栈顶的运算符，并压入s2, 直到遇到左括号，此时将这对括号丢弃
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                s1.pop(); //将（ 弹出 s1 栈
            } else {
                //当item的优先级小于等于栈顶运算符，将s1栈顶的运算符弹出并加入到s2中，再转到4.1与s1中新的栈顶运算符相比较
                while (s1.size() != 0 && Operation.getValue(s1.peek()) >= Operation.getValue(item)) {
                    s2.add(s1.pop());
                }
                //将item压入栈中
                s1.push(item);
            }
        }

        while (s1.size() != 0) {
            s2.add(s1.pop());
        }

        return s2;
    }


    //将中缀转后缀
    public static List<String> toArrayExpressionList(String s) {
        List<String> list = new ArrayList<String>();
        int i = 0;  //这是一个指针，用于遍历中缀表达式字符串
        String str;  //对多位数拼接
        char c;  //每遍历到一个字符就放入c中

        do {
            //如果是非数字，就需要加到ls
            if ((c = s.charAt(i)) < 48 || (c = s.charAt(i)) > 57) {
                list.add("" + c);
                i++;
            } else {
                str = ""; //先将str， 置成“”
                while (i < s.length() && (c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57) {
                    str += c;
                    i++;
                }
                list.add(str);
            }
        } while (i < s.length());

        return list;
    }


    //将一个逆波兰表达式，依次将数据和运算符 放入到ArrayList
    public static List<String> getListString(String suffixExpression) {
        String[] split = suffixExpression.split(" ");
        List<String> list = new ArrayList<String>();
        for (String el : split) {
            list.add(el);
        }
        return list;
    }

    //完成对逆波兰表达式的运算

    public static int calculate(List<String> ls) {
        //创建一个栈
        Stack<String> stack = new Stack<String>();
        for (String item : ls) {
            //这里使用正则取出数字
            if (item.matches("\\d+")) {
                //入栈
                stack.push(item);
            } else {
                //pop出两个数，并运算，再入栈
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());

                int res = 0;
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num1 - num2;
                } else if (item.equals("*")) {
                    res = num1 * num2;
                } else if (item.equals("/")) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("operator is wrong");
                }

                stack.push("" + res);

            }
        }

        return Integer.parseInt(stack.pop());
    }

}

// create class

class Operation {
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUB = 2;
    private static int DIV = 2;

    public static int getValue(String oper) {
        int res = 0;
        switch (oper) {
            case "+":
                res = ADD;
                break;
            case "-":
                res = SUB;
                break;
            case "*":
                res = MUB;
                break;
            case "/":
                res = DIV;
                break;
            default:
                break;
        }
        return res;
    }


}