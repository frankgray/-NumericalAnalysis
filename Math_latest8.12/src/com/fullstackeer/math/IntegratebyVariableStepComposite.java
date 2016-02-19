package com.fullstackeer.math;
/**
 * 变步长复化求积，包括变步长梯形公式、变步长辛普森公式
 * @version 1.0
 */
public class IntegratebyVariableStepComposite {
	/**
	 * 平分后的相邻两点的间距
	 */
	private double xStepLength;
	/**
	 * 函数点集的y值集合，为一维数组，记录y值
	 */
	private double[] yValue = null;
	/**
	 * 迭代结果的小数点精确位数
	 */
	private int accuracyNumber = 0;
	/**
	 * 最多的迭代次数
	 */
	private int maxIterNum = 0;
	/**
	 * 迭代的次数
	 */
	private int iterNum = 0;
	/**
	 * 分段的个数
	 */
	private int segmentNum = 0;
	/**
	 * 是否迭代到需求的精度
	 */
	private boolean isCompleted = true;
	/**
	 * 函数积分结果
	 */
	private double intergrationValue = 0;
	/**
	 * 设定平分后的点间距
	 * @param xStepLength 等分后相邻两节点的距离
	 */
	public void setXStepLength(double xStepLength){
		this.xStepLength = xStepLength;
	}
	/**
	 * 设定用于积分的点集
	 * @param yValue<br>
	 * 复化梯形公式需要2^n+1个节点；<br>
	 * 复化Simpson需要2^n+1个节点；<br>
	 */
	public void setYValue(double[] yValue){
		this.yValue = yValue;
	}
	/**
	 * 设置精度
	 * @param deviationValue 积分结果精度
	 */
	public void setAccuracyNumber(int accuracyNumber){
		this.accuracyNumber = accuracyNumber;
	}
	/**
	 * 获得最大迭代次数
	 * @return 最大迭代次数
	 */
	public int getMaxIterNum(){
		return this.maxIterNum;
	}
	/**
	 * 获得迭代次数
	 * @return 迭代次数
	 */
	public int getIterNum(){
		return this.iterNum;
	}
	/**
	 * 获得分段个数
	 * @return 分段个数
	 */
	public int getSegmentNum(){
		return this.segmentNum;
	}
	/**
	 * 得到积分结果
	 * @return 积分结果
	 */
	public double getIntergrationValue(){
		return this.intergrationValue;
	}
	/**
	 * 得到是否完成
	 * @return 是否完成
	 */
	public boolean getIsCompleted(){
		return isCompleted;
	}
	/**
	 * 进行变步长复变梯形积分求解
	 */
	public void variableStepCompositeOfTrapezoid(){
		int length = this.yValue.length;
		maxIterNum = (int) (Math.log(length-1)/Math.log(2));
		
		//设置初值
		double ω = 1;//误差值
		double temp = 0;//迭代后的值
		iterNum = 0;//记录迭代次数
		isCompleted = true;
		
		segmentNum = 1;//分段数
		double stepLength = (length-1)*xStepLength;//段长
		int stepPointNum = length-1;//段间的点个数（包括段首点，不包括段末点）
		temp = (yValue[0]+yValue[length-1])/2*stepLength;
		intergrationValue = 0;
		//迭代过程
		while(ω>Math.pow(10, -accuracyNumber)&&iterNum<maxIterNum){
			intergrationValue = temp;
			
			temp = intergrationValue/2;
			stepPointNum /= 2;
			
			for(int i=0;i<segmentNum;i++){
				temp += yValue[(int) (stepPointNum*(2*i+1))]*(stepLength/2);
			}
			
			stepLength /= 2;
			segmentNum *= 2;
			ω = Math.abs(temp-intergrationValue);
			
			iterNum++;
		}
		
		if(Double.isNaN(ω)||(ω>Math.pow(10, -accuracyNumber)&&iterNum==maxIterNum)){
			isCompleted = false;
		}
	}
	
	public void variableStepCompositeOfSimpson(){
		int length = this.yValue.length;
		maxIterNum = (int) (Math.log(length-1)/Math.log(2));
		
		//设置初值
		double ω = 1;//误差值
		double temp = 0;
		double tempDown = 0;
		iterNum = 0;//记录迭代次数
		isCompleted = true;
		
		segmentNum = 1;//分段数
		double stepLength = (length-1)*xStepLength;//段长
		int stepPointNum = length-1;//段间的点个数（包括段首点，不包括段末点）
		
		tempDown = (yValue[0]+yValue[length-1])/2*stepLength;
		
		intergrationValue = 0;
		double tempIntergrationValue = 0;//迭代后的值
		
		//迭代过程
		while(ω>Math.pow(10, -accuracyNumber)&&iterNum<maxIterNum){
			intergrationValue = tempIntergrationValue;
			temp = tempDown;
			tempDown = temp/2;

			stepPointNum /= 2;
			
			for(int i=0;i<segmentNum;i++){
				tempDown += yValue[(int) (stepPointNum*(2*i+1))]*(stepLength/2);
			}
			
			tempIntergrationValue = (4*tempDown-temp)/3;
			
			stepLength /= 2;
			segmentNum *= 2;
			ω = Math.abs(tempIntergrationValue-intergrationValue);
			
			iterNum++;
		}
		
		if(Double.isNaN(ω)||(ω>Math.pow(10, -accuracyNumber)&&iterNum==maxIterNum)){
			isCompleted = false;
		}
	}
	
	/**
	 * 该类的测试方法
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IntegratebyVariableStepComposite ivsc = new IntegratebyVariableStepComposite();
		ivsc.setAccuracyNumber(100);
		ivsc.setXStepLength(0.001);
		
		double[] yValue = new double[1025];
		for(int i=0;i<1025;i++){
			//yValue[i] = Math.pow(Math.E, i*0.001);
			yValue[i] = Math.pow(i, 4);
		}
		
		ivsc.setYValue(yValue);
		ivsc.variableStepCompositeOfTrapezoid();
		System.out.println("阿童木告诉你哟：变步长复化梯形积分法求解得到的结果是"+ivsc.getIntergrationValue());
		System.out.println(ivsc.getIterNum()+""+ivsc.getMaxIterNum()+""+ivsc.getIsCompleted());
		
		ivsc.variableStepCompositeOfSimpson();
		System.out.println("阿童木告诉你哟：变步长复化Simpson积分法求解得到的结果是"+ivsc.getIntergrationValue());
		System.out.println(ivsc.getIterNum()+""+ivsc.getMaxIterNum()+""+ivsc.getIsCompleted());
	}

}
