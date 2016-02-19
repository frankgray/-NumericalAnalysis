package com.fullstackeer.math;
/**
 *龙贝格外推法求取积分。n次外推需要2^n+1个点
 * @version 1.0
 */
public class IntegratebyRomberg {
	/**
	 * 平分后的相邻两点的间距
	 */
	private double xStepLength;
	/**
	 * 函数点集的y值集合，为一维数组，记录y值
	 */
	private double[] yValue = null;
	/**
	 * 外推次数
	 */
	private int extrapolationTimes = 0;
	/**
	 * 积分结果
	 */
	private double intergrationValue = 0;
	/**
	 * 龙贝格积分表
	 */
	private double[][] T;
	/**
	 * 设定平分后的点间距
	 * @param xStepLength
	 */
	public void setXStepLength(double xStepLength){
		this.xStepLength = xStepLength;
	}
	/**
	 * 设定y值集合
	 * @param yValue 函数点集的y值集合
	 */
	public void setYValue(double[] yValue){
		this.yValue = yValue;
	}
	/**
	 * 设定外推次数
	 * @param extrapolationTimes 外推次数
	 */
	public void setExtrapolationTimes(int extrapolationTimes){
		this.extrapolationTimes = extrapolationTimes;
	}
	/**
	 * 得到积分结果
	 */
	public double getIntergrationValue(){
		return intergrationValue;
	}
	/**
	 * 得到龙贝格积分表
	 */
	public double[][] getT(){
		return this.T;
	}
	/**
	 * 进行龙贝格外推
	 */
	public void integratebyRomberg(){
		T = new double[extrapolationTimes+1][];
		int length = yValue.length;
		int stepPointNum = length-1;//段间的点个数（包括段首点，不包括段末点）
		int startPointIndex = length-1;
		double stepLength = xStepLength*stepPointNum;////段长
		
		//计算T[][0]
		for(int i=0;i<extrapolationTimes+1;i++){
			T[i] = new double[extrapolationTimes+1-i];
			
			if(i==0){
				T[0][0] = (yValue[0]+yValue[length-1])/2*stepLength;
			}else{
				T[i][0] = T[i-1][0]/2;
				
				startPointIndex /= 2;
				
				for(int j=0;j<Math.pow(2, i-1);j++){
					T[i][0] += yValue[startPointIndex+j*stepPointNum]/2*stepLength;
				}
				
				stepPointNum /= 2;
				stepLength /=2;
			}	
		}
		
		//计算T[][]
		for(int i=1;i<extrapolationTimes+1;i++){
			for(int j=0;j<extrapolationTimes+1-i;j++){
				T[j][i] = (Math.pow(4, i)*T[j+1][i-1]-T[j][i-1])/(Math.pow(4, i)-1);
			}
		}
		
		intergrationValue = T[0][extrapolationTimes];
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] yValue = new double[17];
		for(int i=0;i<17;i++){
			double x = Math.pow(Math.PI, 0.5)/16*i;
			yValue[i] = 2*Math.pow(x, 2)*Math.cos(Math.pow(x, 2));
		}
		
		IntegratebyRomberg ir = new IntegratebyRomberg();
		ir.setExtrapolationTimes(4);
		ir.setXStepLength(Math.pow(Math.PI, 0.5)/16);
		ir.setYValue(yValue);
		
		ir.integratebyRomberg();
		
		System.out.println(ir.getIntergrationValue());
		
		double[][] T = ir.getT();
		
		for(int i=0;i<T.length;i++){
			for(int j=0;j<T[i].length;j++){
				System.out.print(T[i][j]+",");
			}
			System.out.println();
		}
	}

}
