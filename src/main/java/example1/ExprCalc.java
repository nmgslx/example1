package example1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class ExprCalc {
	private String expr = null;
	private List<Object> list;
	private Stack<Object> stack;
	private enum CharType {Space, Digit, Operator, Other};
	private int opeatorPriority(String opr) {
		switch (opr) {
		case ")":
			return 1;
		case "(":
			return 91;
		case "OR":
			return 11;
		case "AND":
			return 12;
		case ">":
		case ">=":
		case "<":
		case "<=":
		case "<>":
		case "!=":
		case "==":
			return 21;
		case "+":
		case "-":
			return 31;
		case "*":
		case "/":
		case "%":
			return 32;
		case "^":
			return 41;
		}
		return 0;
	}

	private boolean isOperator(Object obj) {
		return obj instanceof String && ((String)obj).matches("\\+|-|\\*|/|\\*|>|>=|<|<=|<>|!=|==|AND|OR|\\^|\\(|\\)");
	}

	private boolean isNumber(Object obj) {
		return (obj instanceof Integer) || (obj instanceof Double);
	}

	private Object toObject(String str) {
		if (str.matches("-?[\\d]+")) return Integer.parseInt(str);
		if (str.matches("-?[\\d\\.]+")) return Double.parseDouble(str);
		if (str.toLowerCase().equals("true")) return true;
		if (str.toLowerCase().equals("false")) return false;
		return str;
	}

	private void calculate(int begin, int end) {
		String op = expr.substring(begin, end);

		if (!isOperator(op)) {
			list.add(toObject(op));
			return;
		}
		while (!stack.empty()) {
			String operator = (String)stack.pop();
			if (operator.equals("(")) {
				stack.push(operator);
				break;
			}
			if (operator.equals(")")) {
				operator = (String)stack.pop();
				continue;
			}
			if (opeatorPriority(operator) >= opeatorPriority(op)) list.add(operator);
			else {
				stack.push(operator);
				break;
			}
		}
		stack.push(op);
	}


	ExprCalc(String expr) {
		this.expr = expr;
	}

	ExprCalc() {
	}

	Object result(String expr) {
		this.expr = expr;
		return result();
	}
	
	Object result() {
		try {
			if (expr==null) return null;
			if (expr.matches("\\\"[^\\\"]*\\\"")) 
				return expr.substring(1, expr.length() - 1);
			
			stack = new Stack<Object>();
			list = new ArrayList<Object>();

			CharType type = CharType.Space; // 0:space, '1':number, 'a':not number
			int d1 = 0;
			int i;
			boolean inQuotation = false;
			for (i = 0; i < expr.length(); i++) {
				char ch = expr.charAt(i);
				if (ch == '"') {
					if (type != CharType.Space) calculate(d1, i);
					inQuotation = !inQuotation;
					type = inQuotation ? CharType.Other : CharType.Space;
					d1 = i + 1;
					continue;
				}
				if (inQuotation) continue;
				if (ch == ' ') {
					if (type == CharType.Space) continue;
					calculate(d1, i);
					type = CharType.Space;
					d1 = i;
				}
				else if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch == '_') {
					if (type == CharType.Operator && !expr.substring(d1, i).equals("!")) {
						calculate(d1, i);
						type = CharType.Space;
					}
					if (type == CharType.Space) d1 = i;
					type =  CharType.Other;
				}
				else if (ch >= '0' && ch <= '9' || ch == '.') {
					if (type ==  CharType.Digit || type ==  CharType.Other) continue;
					if (type ==  CharType.Operator ) calculate(d1, i);
					type = CharType.Digit; // number
					d1 = i;
				}
				else if (ch == '(' || ch == ')') {
					if (type != CharType.Space) calculate(d1, i);
					calculate(i, i + 1);
					type = CharType.Space;
				}
				else {
					if (type == CharType.Other) {
						if (expr.substring(i - 1, i + 1).matches("<>|!=|==")) {
							if (d1 < i - 1) calculate(d1, i - 1);
							calculate(i - 1, i + 1);
							type = CharType.Space;
						}
						continue;
					}
					if (type == CharType.Digit) calculate(d1, i);
					if (type != CharType.Operator) {
						type = CharType.Operator;
						d1 = i;
					}
				}
			}
			if (type != CharType.Space) calculate(d1, i);

			if (stack.empty()) {
				if (list.size() == 1) return list.get(0);
				return expr;
			}
			
			while (!stack.empty()) {
				String tmp = (String)stack.pop();
				if (!tmp.equals("(") && !tmp.equals(")")) list.add(tmp);
			}

			/////////////////////////////////////////////////////////////////////////////
			//System.out.println(list);

			for (i = 0; i < list.size(); i++) {
				Object item = list.get(i);

				if (!isOperator(item)) {
					stack.push(item);
					continue;
				}

				String op = (String) item;
				//System.out.println(op + ":" + stack);

				Object op2 = stack.pop();
				Object op1 = stack.pop();
				if (op1 instanceof String && ((String)op1).matches("\"[^\"]*\"")) op1 = ((String)op1).substring(1, ((String)op1).length() - 1);
				if (op2 instanceof String && ((String)op2).matches("\"[^\"]*\"")) op2 = ((String)op1).substring(1, ((String)op1).length() - 1);

				Object r = null;
				if (!isNumber(op1) || !isNumber(op2) || op.matches("==|<>|!=|AND|OR")) {
					switch (op) {
					case ">":
						r = op1.toString().compareTo(op2.toString()) > 0;
						break;
					case ">=":
						r = op1.toString().compareTo(op2.toString()) >= 0;
						break;
					case "<":
						r = op1.toString().compareTo(op2.toString()) < 0;
						break;
					case "<=":
						r = op1.toString().compareTo(op2.toString()) <= 0;
						break;
					case "==":
						r = op1.toString().equals(op2.toString());
						break;
					case "<>":
					case "!=":
						r = !op1.toString().equals(op2.toString());
						break;
					case "AND":
						r = op1.toString().toLowerCase().equals("true") && op2.toString().toLowerCase().equals("true");
						break;
					case "OR":
						r = op1.toString().toLowerCase().equals("true") || op2.toString().toLowerCase().equals("true");
						break;
					}
				}
				else {
					if (op1 instanceof Integer && op2 instanceof Integer && op.matches("[+\\-\\*/]") || op.equals("%")) {
						int i1 = (int) op1;
						int i2 = (int) op2;
						switch (op) {
						case "+":
							r = i1 + i2;
							break;
						case "-":
							r = i1 - i2;
							break;
						case "*":
							r = i1 * i2;
							break;
						case "/":
							if (i2 == 0) r = Double.POSITIVE_INFINITY;
							else r = i1 / i2;
							break;
						case "%":
							if (i2 == 0) r =  Double.POSITIVE_INFINITY;
							r = i1 % i2;
							break;
						}
					}
					else {
						double x1 = op1 instanceof Double?(double)op1: op1 instanceof Integer? (double)(int)op1 : Double.NaN;
						double x2 = op2 instanceof Double?(double)op2: op2 instanceof Integer? (double)(int)op2 : Double.NaN;

						switch (op) {
						case "+":
							r = x1 + x2;
							break;
						case "-":
							r = x1 - x2;
							break;
						case "*":
							r = x1 * x2;
							break;
						case "^":
							r = Math.pow(x1, x2);
							break;
						case "/":
							r = x2 == 0 ? (x1 >= 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY) : x1 / x2;
							break;
						case ">":
							r = x1 > x2;
							break;
						case ">=":
							r = x1 >= x2;
							break;
						case "<":
							r = x1 < x2;
							break;
						case "<=":
							r = x1 <= x2;
							break;
						default:
							r = Double.NaN;
							break;
						}
					}
				}

				stack.push(r);
			}
			return stack.empty() ? null : stack.pop();
		}
		catch (Exception ex) {
			return null;
		}
	}

	public static void main(String[] args) {
		ExprCalc expc = new ExprCalc();
		System.out.println(expc.result("1+2"));
		System.out.println(expc.result("(2 +2*5/11.0-6.0/3 > 1) OR (3>2) AND \"ABC 11\"==\"ABC 11\""));
		System.out.println(expc.result("(1+(3-(5.0+2))*(4-2)/2)"));
		System.out.println(expc.result("A==B OR C==C OR 1==2"));
		System.out.println(expc.result("2 <= 2"));
		System.out.println(expc.result("*"));
		System.out.println(expc.result("true OR !Err==Live OR !Err==Prod"));
	}

}
