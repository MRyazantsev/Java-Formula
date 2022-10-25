package org.example;

import java.util.Map;
import java.util.Stack;

/**
 * The formula class allows you to calculate rather complex mathematical expressions,
 * while checking the correctness of the entered brackets.
 * @author Mikhail Ryazantsev
 */
public class Formula {
    String formula;
    Map<String, Double> variables;

    /**
     * Constructor of class
     * @param str formula string
     * @param map a map containing user-entered variables
     */
    public Formula(String str, Map<String, Double> map){
        formula = str;
        variables = map;
    }

    /**
     * A method used before an expression is evaluated as a validation check.
     * @return True if the expression is correct.
     * Or false, if incorrect.
     */
    public boolean isCorrect(){
        boolean result = true;
        Stack<Character> stack = new Stack<Character>();
        for(int i=0;i<this.formula.length();i++){
            char current = this.formula.charAt(i);
            if(current=='(' || current=='{' || current=='[')
                stack.push(this.formula.charAt(i));
            else {
                if(current==')' && ((stack.isEmpty() || stack.pop()!='('))){
                    return false;
                }
                if(current==']' && ((stack.isEmpty() || stack.pop()!='['))){
                    return false;
                }
                if(current=='}' && ((stack.isEmpty() || stack.pop()!='{'))){
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    /**
     *  A helper method that allows you to remove the last character of a string
     *  (used when writing an expression from infix form to postfix form).
     * @param str It takes a string as a parameter.
     * @return Returns a string without the last character.
     */
    private static String removeLastChar(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, str.length() - 1);
    }

    /**
     * Method that determines the priority of the current operation (+, -, *, /)
     * (used when writing an expression from infix form to postfix form).
     * @param ch - operation.
     * @return its priority.
     */
    private int Priority(char ch){
        int result = 0;
        if(ch=='+' || ch=='-'){
            result = 1;
        }
        else if(ch=='*' || ch=='/'){
            result = 2;
        }
        return result;
    }

    /**
     * An important method that writes the original expression in postfix notation.
     * @return Postfix notation of an expression
     */
    private String InToPos(){
        int stillNumber = 0;
        String result = "";
        Stack<Character> stack = new Stack<Character>();
        for(int i=0;i<this.formula.length();i++){
            char current = this.formula.charAt(i);
            if(current=='+' || current=='-' || current=='*' || current=='/'){
                while(!stack.isEmpty() && Priority(current)<=Priority(stack.peek())){
                    result+=stack.pop();
                }
                stack.push(current);
                stillNumber = 0;
            }
            else if(current=='(' || current=='[' || current=='{'){
                stack.push(current);
                stillNumber = 0;
            }
            else if(current==')' || current==']' || current == '}'){
                if(current==')'){
                    while(!stack.isEmpty() && stack.peek()!='('){
                        result+=stack.peek();
                        stack.pop();
                    }
                    stack.pop();
                }
                if(current==']'){
                    while(!stack.isEmpty() && stack.peek()!='['){
                        result+=stack.peek();
                        stack.pop();
                    }
                    stack.pop();
                }
                if(current=='}'){
                    while(!stack.isEmpty() && stack.peek()!='{'){
                        result+=stack.peek();
                        stack.pop();
                    }
                    stack.pop();
                }
                stillNumber = 0;
            }
            else{
                if(stillNumber==0){
                    result+='(';
                    result+=current;
                    result+=')';
                }
                else{
                    result = removeLastChar(result);
                    result+=current;
                    result+=')';
                }
                stillNumber+=1;
            }
        }
        while(!stack.isEmpty()){
            result+=stack.pop();
        }
        return result;
    }

    /**
     * A method that converts string data into numbers, function values, variable values.
     * @param number A string that represents some numeric value.(As a number, function, or variable)
     * @return  double type number
     */
    private double ToValue(String number) {
        if(variables!=null && variables.containsKey(number)){
            return variables.get(number);
        }
        if(number.length()>4){
            if(number.substring(0, 3).equals("pow")){
                double arg1 = Double.parseDouble(number.substring(3, number.split(":")[0].length()));
                double arg2 = Double.parseDouble(number.substring(number.split(":")[0].length()+1, number.length()));
                return Math.pow(arg1, arg2);
            }
        }
        if(number.length()>3){
            double arg3 = Double.parseDouble(number.substring(3, number.length()));
            if(number.substring(0, 3).equals("sin")){
                return Math.sin(arg3);
            }
            if(number.substring(0, 3).equals("cos")){
                return Math.cos(arg3);
            }
            if(number.substring(0, 3).equals("ctg")){
                return 1/Math.tan(arg3);
            }
            if(number.substring(0, 3).equals("exp")){
                return Math.exp(arg3);
            }
            if(number.substring(0, 3).equals("sqr")){
                return Math.sqrt(arg3);
            }
        }
        if(number.length()>2){
            double arg2 = Double.parseDouble(number.substring(2, number.length()));
            if(number.substring(0, 2).equals("tg")){
                return Math.tan(arg2);
            }
            if(number.substring(0, 2).equals("ln")){
                return Math.log(arg2);
            }
        }
        return Double.parseDouble(number);
    }

    /**
     * A function that calculates the value of an expression entered by the user, or writes an error message.
     * @return The number is the result of an expression or an error.
     */
    public double calc(){
        if(this.isCorrect()){
            Stack<Double> stack = new Stack<Double>();
            String postfix = this.InToPos();
            String number = "";
            double value;
            for(int i=0;i<postfix.length();i++){
                char current = postfix.charAt(i);
                if(current=='+'){
                    double value2 = stack.pop();
                    double value1 = stack.pop();
                    stack.push(value1+value2);
                }
                else if(current=='-'){
                    double value2 = stack.pop();
                    double value1 = stack.pop();
                    stack.push(value1-value2);
                }
                else if(current=='*'){
                    double value2 = stack.pop();
                    double value1 = stack.pop();
                    stack.push(value1*value2);
                }
                else if(current=='/'){
                    double value2 = stack.pop();
                    double value1 = stack.pop();
                    stack.push(value1/value2);
                }
                else{
                    i+=1;
                    current = postfix.charAt(i);
                    while(current!=')'){
                        number+=current;
                        i+=1;
                        current = postfix.charAt(i);
                    }
                    value = ToValue(number);
                    stack.push(value);
                    number = "";
                }
            }
            return stack.pop();
        }
        else{
            System.out.println("Формула введена неверно.");
            return 0.0;
        }
    }
}
