import java.util.*;

class Expression {

	static double Eval(Vector<String> infix) throws Exception {
		// postfix 계산을 위한 stack 
		class solution {
			private int Size;
			public double[] solve;
			public int index;

			public solution(int max) {
				solve = new double[max];
				index = -1;
			}

			public void push(double j) {
				solve[++index] = j;
			}

			public double pop2() {
				int index2 = index - 1;
				return solve[index2];
			}

			public double pop1() {
				int index1 = index;
				return solve[index1];
			}
		}

		infix.expressInfix();
		// 마지막에 모여 있는 연산자를 제외한 것을 postfix로 넣는다
		for (int i = 0; i < infix.theInfix.infixIndex; i++) {
			infix.TokenStack(infix.theInfix.infixdata[i]);
		}
		// 마지막에 모여 있는 연산자를 연산규칙에 따라 postfix로 넣는다
		for (int i = 0 ; i < infix.theStack.top+1; i++) {
			int index = infix.theStack.top-i;
			infix.thePostfix.push(infix.theStack.stackArray[index]);
		}

		solution thesol = new solution(100);
		for (int i1 = 0; i1 < infix.thePostfix.postfixIndex + 1; i1++) {
			char ele = infix.thePostfix.postfixdata[i1];
			if (isNum(ele)) {
				int eleInt = Character.getNumericValue(ele);
				thesol.push(eleInt);
			} else {
				double num1 = thesol.pop1();
				double num2 = thesol.pop2();
				if (infix.thePostfix.postfixdata[i1] == '+') {
					thesol.index = thesol.index - 2;
					thesol.push(num2 + num1);
				} else if (infix.thePostfix.postfixdata[i1] == '-') {
					thesol.index = thesol.index - 2;
					thesol.push(num2 - num1);
				} else if (infix.thePostfix.postfixdata[i1] == '*') {
					thesol.index = thesol.index - 2;
					thesol.push(num2 * num1);
				} else if (infix.thePostfix.postfixdata[i1] == '/') {
					thesol.index = thesol.index - 2;
					thesol.push(num2 / num1);
				}
			}
		}
		infix.expressPostfix();
		return thesol.solve[0];
	}

	public static boolean isNum(char t) {
		String s = Character.toString(t);
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

};

class Vector<String> {

	public Stack theStack; // stack
	public postfix thePostfix; // postfix
	public infix theInfix; //  infix

	// vector를 생성하면 stack postfix infix를 관리하는 stack 생성
	public Vector() {
		theStack = new Stack();
		thePostfix = new postfix();
		theInfix = new infix();
	}
	
	// 여기는 infix express를 출력하기 위한 class 생성
	class infix{
		public char[] infixdata;
		public int infixIndex;
		
		public infix() {
			infixIndex = 0;
			infixdata = new char[100];
		}
		
		public void push(char j) {
			infixdata[infixIndex++] = j;
		}
	}

	// 여기는 postfix express를 출력하기 위한 class 생성
	class postfix {
		public char[] postfixdata;
		public int postfixIndex;

		public postfix() {
			postfixdata = new char[100];
			postfixIndex = -1;
		}

		public void push(char j) {
			postfixdata[++postfixIndex] = j;
		}
	}

	// 여기는 stack 를 관리 하기위한 class
	class Stack {
		public char[] stackArray;
		public int top;

		public Stack() {
			stackArray = new char[100];
			top = -1;
		}

		public void push(char j) {
			stackArray[++top] = j;
		}

		public char pop() {
			return stackArray[top--];
		}

		public boolean isEmpty() {
			return (top == -1);
		}
	}

	// vector에 넣어 infix 형태로 집어 넣는다.
	public void add(String e) {
		char ch = ((java.lang.String) e).charAt(0);
		theInfix.push(ch);
		
	}
	
	// infix -> postfix로 바꾸는 함수
	// 들어온 값은 token으로 처리
	// 들어와서 쌓이는 값(연산자)는 stack로 처리
	public void TokenStack(char ch) {
		
		switch (ch) {
		case '+':
		case '-':
			System.out.println("Token : " + ch);
			gotOper(ch, 1);
			printStack();
			break;

		case '*':
		case '/':
			System.out.println("Token : " + ch);
			gotOper(ch, 2);
			printStack();
			break;

		case '(':
			System.out.println("Token : " + ch);
			theStack.push(ch);
			printStack();
			break;

		case ')':
			System.out.println("Token : " + ch);
			gotParen(ch);
			printStack();
			break;

		default:
			System.out.println("Token : " + ch);

			thePostfix.push(ch);
			printStack();
			break;
		}
	}
	
	// 들어오는 인자가 연산자( + - * / )인 경우 
	public void gotOper(char opThis, int prec1) {
		while (!theStack.isEmpty()) {
			char opTop = theStack.pop();
			if (opTop == '(') {
				theStack.push(opTop);
				break;
			} else {
				int prec2;
				if (opTop == '+' || opTop == '-')
					prec2 = 1;
				else
					prec2 = 2;
				if (prec2 < prec1) {
					theStack.push(opTop);
					break;
				} else {
					thePostfix.push(opTop);
				}
			}
		}
		// 연산자를 postfix에 집어 넣는 역할
		theStack.push(opThis);
	}

	// 괄호가 닫힐 때 축적 되었던 연산자 집어 넣기
	public void gotParen(char ch) {
		while (!theStack.isEmpty()) {
			char chx = theStack.pop();
			if (chx == '(')
				break;
			else {
				thePostfix.push(chx);
			}
		}
	}
	
	// stack상태를 print시켜주는 함수
	public void printStack() {
		System.out.print("Stack : ");
		for (int i = 0; i < theStack.top + 1; i++) {
			System.out.print(theStack.stackArray[i] + " ");
		}
		System.out.println();
	}

	// postfix를 출력 시켜주는 함
	public void expressPostfix() {
		System.out.print("Postfix expression : [");
		for (int i = 0; i < thePostfix.postfixIndex + 1; i++) {
			if (i != thePostfix.postfixIndex) {
			System.out.print(thePostfix.postfixdata[i] + "  ");
			}
			else {
				System.out.print(thePostfix.postfixdata[i]);
			}
		}
		System.out.println("]");
	}
	
	// infix에 있는 것을 postfix로 바꾼다
	public void expressInfix() {
		System.out.print("infix expression : [");
		for (int i = 0; i < theInfix.infixIndex; i++) {
			if (i != theInfix.infixIndex-1) {
			System.out.print(theInfix.infixdata[i]+"  ");
			}
			else {
				System.out.print(theInfix.infixdata[i]);
			}
		}
		System.out.println("]");
	}

}


