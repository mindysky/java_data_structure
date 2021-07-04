package com.hackrank.stack;

public class CalculatorStack {
    public static void main(String[] args) {

        String expression = "7+2*6-4";
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);

        int index = 0;
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int res = 0;
        char ch = ' ';
        String keepNum = ""; //拼接多位数

        while (true) {
            ch = expression.substring(index, index + 1).charAt(0);
            //判断ch是什么
            if (operStack.isOper(ch)) {
                if (!operStack.isEmpty()) {
                    //不为空
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.calc(num1, num2, oper);
                        numStack.push(res);
                        operStack.push(ch);
                    } else {
                        operStack.push(ch);
                    }
                } else {
                    //为空
                    operStack.push(ch);
                }
            } else {
                //如果是数字，则直接入数栈
//                numStack.push(ch - 48);
                //1. 当处理多位数时，不能发现是一个数就立即入数栈，因为可能是多位数
                //2.在处理数时，需要向expression的表达式 index 后再看一位，如果是数就进行扫描，如果是符号才入栈
                //3. 因此我们需要定义一个变量字符串，用于拼接

                //处理多位数
                keepNum += ch;

                //如果ch已经是expression 的最后一位， 就直接入栈
                if (index == expression.length() - 1) {
                    numStack.push(Integer.parseInt(keepNum));
                } else {
                    //判断下一份字符是不是数组，如果是数字就继续扫描，如果是符号，就入栈
                    if (operStack.isOper(expression.substring(index + 1, index + 2).charAt(0))) {
                        numStack.push(Integer.parseInt(keepNum));
                        //情况keepNum
                        keepNum = "";
                    }
                }

            }
            index++;
            if (index >= expression.length()) {
                break;
            }
        }
        //当表达式扫描完毕，
        while (true) {
            if (operStack.isEmpty()) {
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.calc(num1, num2, oper);
            numStack.push(res);
        }

        int res2 = numStack.pop();
        System.out.printf("expression %s = %d", expression, res2);

    }
}


class ArrayStack2 {
    private int maxSize; //stack size
    private int[] stack; //Array simulate stack
    private int top = -1;  // stack top , init -1

    public ArrayStack2(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    //返回栈顶的值
    public int peek() {
        return stack[top];
    }

    public boolean isFull() {
        return top == maxSize - 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void push(int value) {
        if (isFull()) {
            System.out.println("stack is full");
            return;
        }
        top++;
        stack[top] = value;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("stack is empty.....");
        }
        int value = stack[top];
        top--;
        return value;
    }

    public void list() {
        if (isEmpty()) {
            System.out.println("stack is empty...2");
            return;
        }
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d \n", i, stack[i]);
        }
    }

    //返回运算符的优先级，优先级使用数字表示
    public int priority(int oper) {
        if (oper == '*' || oper == '/') {
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        } else {
            return -1;
        }
    }

    //
    public boolean isOper(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    // calculate method
    public int calc(int num1, int num2, int oper) {
        int res = 0;
        switch (oper) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1;
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num2 / num1;
                break;
            default:
                break;
        }
        return res;
    }

}