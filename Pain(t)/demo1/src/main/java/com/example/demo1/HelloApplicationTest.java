package com.example.demo1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloApplicationTest {
     @Test
     void test() {
          HelloApplication hi = new HelloApplication();
          assertEquals(true, hi.methodLine());
          assertEquals(true, hi.methodClearButton());
          assertEquals(true, hi.methodDrawButton());

     }
}