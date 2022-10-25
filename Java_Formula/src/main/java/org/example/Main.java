package org.example;

import java.util.*;

/**
 * The main class through which you can enter the expression needed for the count.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Введите названия переменных (если они нужны) и их значения, \n" +
                "(Названия переменных должны отличаться от названий встроенных функций)\n" +
                "(Не обязательно использовать введенные переменные в формуле)\n" +
                "либо введите два нуля, если больше не хотите добавлять переменные.");
        String name = "1";
        double value;
        Map<String, Double> variables = new HashMap<>();
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);

        while(!Objects.equals(name, "0")){
            System.out.println("Введите название переменной: ");
            name = sc.next();
            System.out.println("Введите значение переменной: ");
            value = sc.nextDouble();
            if(!Objects.equals(name, "0")){
                variables.put(name, value);
            }
        }

        System.out.println("Переменные:\n" + Arrays.asList(variables));
        System.out.println("Введите выражение: ");
        String formula = sc.next();
        Formula f1 = new Formula(formula, variables);
        System.out.print("Результат: " + f1.calc() + "\n");
    }
}