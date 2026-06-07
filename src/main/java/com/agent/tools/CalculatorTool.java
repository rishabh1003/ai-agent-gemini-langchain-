package com.agent.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class CalculatorTool {

    @Tool("Calculates the sum of two numbers")
    public double add(double a, double b) {
        System.out.println("Invoked Tool: add(" + a + ", " + b + ")");
        return a + b;
    }

    @Tool("Calculates the product of two numbers")
    public double multiply(double a, double b) {
        System.out.println("Invoked Tool: multiply(" + a + ", " + b + ")");
        return a * b;
    }
}
