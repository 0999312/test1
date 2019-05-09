package upn;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class upnrechner {

	private Stack<String> operator_stack = new Stack<>();
	private ArrayList<String> befor_list = new ArrayList<>();
	private ArrayList<String> after_list = new ArrayList<>();
	private static final int One = 1;
	private static final int Two = 3;

	private static Stack<String> rechnen_stack = new Stack<>();

	public upnrechner(String bdString) {
		StringTokenizer stringTokenizer = new StringTokenizer(bdString, "+-*/", true);
		while (stringTokenizer.hasMoreTokens()) {
			befor_list.add(stringTokenizer.nextToken());

		}
	}

	public boolean isCounter(String str) {
		if (str.matches("[0-9]")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isOperator(String str) {
		if (str.matches("[\\+\\-\\*\\/]")) {
			return true;
		} else {

			return false;
		}
	}

	public int rank(String str) {

		switch (str) {
		case "*":
		case "/":
			return Two;
		case "+":
		case "-":
			return One;

		default:
			return -1;

		}

	}

	public boolean rankg(String str1, String str2) {
		return rank(str1) > rank(str2);
	}

	public void stack_op(String op) {

		if (operator_stack.isEmpty()) {
			operator_stack.push(op);
		}

		if (rankg(op, operator_stack.peek())) {
			operator_stack.push(op);
		}

		if (!rankg(op, operator_stack.peek())) {
			after_list.add(operator_stack.pop());
			stack_op(op);
		}

	}

	public void befor_after() {

		for (String str : befor_list) {

			if (isCounter(str)) {
				after_list.add(str);
			} else if (isOperator(str)) {
				stack_op(str);
			} else {
				System.out.println("Falsch eingeben!Nur Zahl und Operator!");
			}

		}

		while (!operator_stack.isEmpty()) {
			after_list.add(operator_stack.pop());
		}

	}

	public String nbls_bc() {
		String nbls_cc = new String();
		for (String string : after_list) {

			nbls_cc += string;
		}
		return nbls_cc;
	}

	public int rechnen(String s1, String s2, String s3) {
		int a = Integer.parseInt(s2);
		int b = Integer.parseInt(s1);
		switch (s3) {
		case "+":
			return a + b;
		case "-":
			return a - b;
		case "*":
			return a * b;
		case "/":// b²»µÃÎªÁã
			return a / b;
		default:
			return 0;
		}
	}

	public int rechner_upn() {
		for (String str : after_list) {
			if (isCounter(str)) {
				rechnen_stack.push(str);
			} else {
				rechnen_stack.push(String.valueOf(rechnen(rechnen_stack.pop(), rechnen_stack.pop(), str)));
			}
		}
		return Integer.parseInt(rechnen_stack.pop());
	}

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Bitte geben Sie die Formel ein :");
		String input = keyboard.nextLine();
		upnrechner upn = new upnrechner(input);
		String upnform = new String();
		upn.befor_after();
		upn.nbls_bc();
		System.out.println("UPNFORM £º" + upnform);
		System.out.println("Ergebniss£º" + upn.rechner_upn());

	}

}