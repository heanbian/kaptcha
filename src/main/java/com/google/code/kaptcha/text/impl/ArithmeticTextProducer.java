package com.google.code.kaptcha.text.impl;

import java.security.SecureRandom;
import java.util.function.BiFunction;

import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.util.AbstractKaptchaConfig;

public class ArithmeticTextProducer extends AbstractKaptchaConfig implements TextProducer {

	private static final SecureRandom RAND = new SecureRandom();
	private static final Operator[] OPERATORS = Operator.values();

	private int num1;
	private int num2;
	private Operator currentOperator;

	public String getText() {
		currentOperator = OPERATORS[RAND.nextInt(OPERATORS.length)];
		generateNumbers();
		return String.format("%d %s %d = ?", num1, currentOperator.symbol, num2);
	}

	public int getAnswer() {
		return currentOperator.calculate(num1, num2);
	}

	private void generateNumbers() {
		switch (currentOperator) {
		case ADDITION:
			num1 = rand(10, 99);
			num2 = rand(10, 99);
			break;
		case SUBTRACTION:
			num1 = rand(20, 99);
			num2 = rand(10, num1 - 10);
			break;
		case MULTIPLICATION:
			num1 = rand(10, 30);
			num2 = rand(10, 30);
			break;
		case DIVISION:
			generateDivisionNumbers();
			break;
		}
	}

	private void generateDivisionNumbers() {
		num2 = rand(2, 15);
		int maxFactor = 50 / num2; // 控制结果不超过50
		int factor = rand(2, maxFactor);
		num1 = num2 * factor;

		// 确保数值在合理范围
		if (num1 > 100) {
			num1 = num2 * (100 / num2);
		}
	}

	private int rand(int min, int max) {
		return RAND.nextInt(max - min + 1) + min;
	}

	private enum Operator {
		ADDITION("+", (a, b) -> a + b), //
		SUBTRACTION("-", (a, b) -> a - b), //
		MULTIPLICATION("×", (a, b) -> a * b), //
		DIVISION("÷", (a, b) -> a / b);

		final String symbol;
		final BiFunction<Integer, Integer, Integer> operation;

		Operator(String symbol, BiFunction<Integer, Integer, Integer> operation) {
			this.symbol = symbol;
			this.operation = operation;
		}

		int calculate(int a, int b) {
			return operation.apply(a, b);
		}
	}

}