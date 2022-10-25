package org.example;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FormulaTest {

    @org.junit.jupiter.api.Test
    void isCorrect() {
        Formula f1 = new Formula("(())", null);
        Formula f2 = new Formula("([])", null);
        Formula f3 = new Formula("{()]", null);
        assertEquals(true, f1.isCorrect());
        assertEquals(true, f2.isCorrect());
        assertEquals(false, f3.isCorrect());
    }

    @org.junit.jupiter.api.Test
    void calc() {
        Formula f1 = new Formula("1+3", null);
        Formula f2 = new Formula("(10-[1+3]*2)", null);
        Formula f3 = new Formula("cos0*1", null);
        assertEquals(4, f1.calc());
        assertEquals(2, f2.calc());
        assertEquals(1, f3.calc());
    }
}