package kr.or.ddit.web.calculate;

public enum Operator {
	
	ADD("+", new RealOperator() {//익명객체?
		@Override
		public int operate(int leftOp, int rightOp) {
			return leftOp+rightOp;
		}
	}), 
	MINUS("-", (leftOp, rightOp)->{return leftOp-rightOp;}), 
	MULTIPLY("*", (a, b)->{return a*b;}),	// 이 상수들은 자기들만의 VALUE를 가지고있음 (상수명과동일한 value)
 //파라미터명은 중요한게 아님
	DIVIDE("/", (a,b)->{return a/b;});
	private String sign;	//property
	private RealOperator realOperator;
	Operator(String sign, RealOperator realOperator) {  //property를 캡슐화해주고 
		this.sign = sign;
		this.realOperator = realOperator;
	}
	
	public String getSign(){ //가져올수있도록 만듬
		return this.sign;
	}
	public int operate(int leftOp, int rightOp) {
		return realOperator.operate(leftOp, rightOp);
	}
}
