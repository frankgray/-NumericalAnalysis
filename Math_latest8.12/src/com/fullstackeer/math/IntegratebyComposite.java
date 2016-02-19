package com.fullstackeer.math;
/**
 *复化求积公式，包括复化梯形公式、复化Simpson、复化3/8
 * Simpson、复化Cotes<br>
 * 缺点：暂时没有数字溢出判断
 * @version 1.0
 */
public class IntegratebyComposite {
	/**
	 * 平分后的相邻两点的间距
	 */
	private double xStepLength;
	/**
	 * 函数点集的y值集合，为一维数组，记录y值
	 */
	private double[] yValue = null;
	/**
	 * 精确的小数点位数
	 */
	private int decimalNumber = 0;
	/**
	 * 函数积分的数值结果
	 */
	private double doubleIntergrationValue = 0;
	/**
	 * 函数积分的字符串结果
	 */
	private String stringIntergrationValue = "";
	/**
	 * 设定平分后的点间距
	 * @param xStepLength
	 * @return void
	 */
	public void setXStepLength(double xStepLength){
		this.xStepLength = xStepLength;
	}
	/**
	 * 设定精确的小数点位数
	 * @param decimalNumber 小数点位数
	 */
	public void setDecimalNumber(int decimalNumber){
		this.decimalNumber = decimalNumber;
	}
	/**
	 * 设定用于积分的点集
	 * @param yValue<br>
	 * 复化梯形公式需要n+1个节点；<br>
	 * 复化Simpson需要2n+1个节点；<br>
	 * 复化3/8Simpson需要3n+1个节点；<br>
	 * 复化Cotes需要4n+1个节点；
	 */
	public void setyValue(double[] yValue){
		this.yValue = yValue;
	}
	/**
	 * 得到积分的数值结果
	 */
	public double getDoubleIntergrationValue(){
		return doubleIntergrationValue;
	}
	/**
	 * 得到积分的字符串结果
	 */
	public String getStringIntergrationValue(){
		return stringIntergrationValue;
	}
	
	/**
	 * 复化梯形求积分
	 */
	public void complexTrapezoid(){
		int step = yValue.length-1;
		doubleIntergrationValue = 0;
		for(int i=0;i<step;i++){
			doubleIntergrationValue +=1.0/2*(yValue[i]+yValue[i+1])*xStepLength;
		}
		
		stringIntergrationValue = decimalChanger(doubleIntergrationValue);
	}
	
	/**
	 * 复化Simpson求积分
	 */	
	public void complexSimpson(){
		int step = (yValue.length-1)/2;
		doubleIntergrationValue = 0;
		for(int i=0;i<step;i++){
			doubleIntergrationValue +=1.0/6*(yValue[2*i]+4*yValue[2*i+1]+
					yValue[2*i+2])*xStepLength*2;
		}
		
		stringIntergrationValue = decimalChanger(doubleIntergrationValue);
	}
	
	/**
	 * 复化3/8Simpson求积分
	 */
	public void complex38Simpson(){
		int step = (yValue.length-1)/3;
		doubleIntergrationValue = 0;
		for(int i=0;i<step;i++){
			doubleIntergrationValue +=1.0/8*(yValue[3*i]+3*yValue[3*i+1]+
					3*yValue[3*i+2]+yValue[3*i+3])*xStepLength*3;
		}
		
		stringIntergrationValue = decimalChanger(doubleIntergrationValue);
	}
	
	/**
	 * 复化Cotes求积分
	 */	
	public void complexCotes(){
		int step = (yValue.length-1)/4;
		doubleIntergrationValue = 0;
		for(int i=0;i<step;i++){
			doubleIntergrationValue +=1.0/90*(7*yValue[4*i]+32*yValue[4*i+1]+
					12*yValue[4*i+2]+32*yValue[4*i+3]+7*yValue[4*i+4])*xStepLength*4;
		}
		
		stringIntergrationValue = decimalChanger(doubleIntergrationValue);
	}
	/**
	 * 保留小数点后位数
	 * @param matrix 数值矩阵
	 * @return 字符串矩阵
	 */
	private String decimalChanger(double matrix){
		String stringValue = new String();
		
		StringBuffer sbFormat = new StringBuffer();
		sbFormat.append("##0.");
		for(int i=0;i<decimalNumber;i++){
			sbFormat.append("0");
		}
		
		StringBuffer sbString = new StringBuffer();
		sbString.append("0.");
		for(int i=0;i<decimalNumber;i++){
			sbString.append("0");
		}
		
		System.out.println(sbFormat.toString()+" "+sbString.toString());
		java.text.DecimalFormat df = new java.text.DecimalFormat(sbFormat.toString());  

		if(String.valueOf(matrix).equals("0.0")){
			stringValue = "0";
		}else{
			stringValue = df.format(matrix);
		}
		
		return stringValue;
	}
	/**
	 * 该类的测试方法，测试函数为y=e^x(0<=x<=1)
	 */
	public static void main(String[] args){
		int num = 25;
		double xStepLength = 0.004/(num-1);
		double[] y = new double[num];
		
		for(int i=0;i<num;i++){
			y[i]=Math.pow(Math.E, i*xStepLength);
		}
		
		IntegratebyComposite ic = new IntegratebyComposite();
		ic.setXStepLength(xStepLength);
		ic.setyValue(y);
		ic.setDecimalNumber(3);
		
		ic.complexTrapezoid();
		System.out.println("阿童木告诉你哟：复化梯形求积法得到的是"+ic.getDoubleIntergrationValue());
		System.out.println("阿童木告诉你哟：复化梯形求积法得到的是"+ic.getStringIntergrationValue());
		
		ic.complexSimpson();
		System.out.println("阿童木告诉你哟：复化Simpson求积法得到的是"+ic.getDoubleIntergrationValue());
		System.out.println("阿童木告诉你哟：复化Simpson求积法得到的是"+ic.getStringIntergrationValue());
		
		ic.complex38Simpson();
		System.out.println("阿童木告诉你哟：复化3/8Simpson求积法得到的是"+ic.getDoubleIntergrationValue());
		System.out.println("阿童木告诉你哟：复化3/8Simpson求积法得到的是"+ic.getStringIntergrationValue());
		
		ic.complexCotes();
		System.out.println("阿童木告诉你哟：复化Cotes求积法得到的是"+ic.getDoubleIntergrationValue());
		System.out.println("阿童木告诉你哟：复化Cotes求积法得到的是"+ic.getStringIntergrationValue());
	}	
}
