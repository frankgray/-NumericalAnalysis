package com.fullstackeer.math;

/**
 *二分法求取非线性方程，需要设定隔根区间的起始点和终点。
 */
public class SolveNonLinearEquationbyDichotomie {
	private double[] pointMin = null;
	private double[] pointMax = null;
	private double[] pointMid = null;
	private double accuracyNumber = 0;
	private boolean isRoot = false; 
	private double root = 0;
	private double startX = 0;
	private double endX = 0;
	
	public void setPointMin(double[] pointMin){
		this.pointMin = pointMin;
	}
	public void setPointMax(double[] pointMax){
		this.pointMax = pointMax;
	}
	public void setPointMid(double[] pointMid){
		this.pointMid = pointMid;
	}
	public void setAccuracyNumber(int accuracyNumber){
		this.accuracyNumber = accuracyNumber;
	}
	public boolean getIsRoot(){
		return this.isRoot;
	}
	public double getRoot(){
		return root;
	}
	public double getStartX(){
		return startX;
	}
	public double getEndX(){
		return endX;
	}	
	public void solveNonLinearEquationbyDichotomie(){
		startX = pointMin[0];
		endX = pointMax[0];
		
		if(Math.abs(startX-endX)<Math.pow(10, -this.accuracyNumber)){
			isRoot = true;
			root = pointMid[0];
			return;
		}
		if(pointMin[1]*pointMid[1]<0){
			startX = pointMin[0];
			endX = pointMid[0];
		}else if(pointMid[1]*pointMax[1]<0){
			startX = pointMid[0];
			endX = pointMax[0];			
		}else if(pointMid[1]==0){
			isRoot = true;
			root = pointMid[0];
		}
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] startPoint = new double[2];
		double[] endPoint = new double[2];
		double[] midPoint = new double[2];
		int times = 0;
		
		SolveNonLinearEquationbyDichotomie snlebd = new SolveNonLinearEquationbyDichotomie();
		snlebd.setAccuracyNumber(5);
		
		startPoint[0] = 1;
		endPoint[0] = 2;
		midPoint[0] = (startPoint[0]+endPoint[0])/2;
		
		startPoint[1] = Math.pow(startPoint[0], 3)+4*Math.pow(startPoint[0], 2)-10;
		endPoint[1] = Math.pow(endPoint[0], 3)+4*Math.pow(endPoint[0], 2)-10;
		midPoint[1] = Math.pow(midPoint[0], 3)+4*Math.pow(midPoint[0], 2)-10;
		
		while(!snlebd.getIsRoot()){
			snlebd.setPointMax(endPoint);
			snlebd.setPointMid(midPoint);
			snlebd.setPointMin(startPoint);
			snlebd.solveNonLinearEquationbyDichotomie();
			
			startPoint[0] = snlebd.getStartX();
			endPoint[0] = snlebd.getEndX();
			midPoint[0] = (startPoint[0]+endPoint[0])/2;

			startPoint[1] = Math.pow(startPoint[0], 3)+4*Math.pow(startPoint[0], 2)-10;
			endPoint[1] = Math.pow(endPoint[0], 3)+4*Math.pow(endPoint[0], 2)-10;
			midPoint[1] = Math.pow(midPoint[0], 3)+4*Math.pow(midPoint[0], 2)-10;
			
			times++;
			if(times==100) break;
		}
		
		if(times==100){
			System.out.println("阿童木告诉你哟：Error:设置的隔根区间过大或者不是隔根区间");
		}else{
			System.out.println("阿童木告诉你哟：求解得到的根是"+snlebd.getRoot());
			System.out.println("阿童木告诉你哟：二分的次数为"+times);
		}
	}
}
