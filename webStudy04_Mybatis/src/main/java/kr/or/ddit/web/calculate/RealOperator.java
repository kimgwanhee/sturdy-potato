package kr.or.ddit.web.calculate;


@FunctionalInterface
interface RealOperator{//public없음 다른곳에서 사용불가
	public int operate(int leftOp, int rightOp);
}

