package com.fullstackeer.math;
//AH-Jacobi迭代求解线性方程组
public class SolveLinearEquationsbyJacobi {
	
	private int matrixOrder = 0;
	private int decimalNumber = 0;
	private int accuracyNumber = 0;
	private int iterations = 0;
	
	private double[][] originalMatrix = null;
	
	private double[] constantArr = null;

	private String[] resultArr = null;
	private double[] tempResultArr = null;
	
	private boolean competedFlag = true;
	
	public void setMatrixOrder(int matrixOrder){
		this.matrixOrder = matrixOrder;
	}
	
	public void setDecimalNumber(int decimalNumber){
		this.decimalNumber = decimalNumber;
	}
	
	public void setAccuracyNumber(int accuracyNumber){
		this.accuracyNumber = accuracyNumber;
	}
	
	public void setIterations(int iterations){
		this.iterations = iterations;
	}
	
	public void setOriginalMatrix(double[][] originalMatrix){
		this.originalMatrix = originalMatrix;
	}
	
	public void setConstantArr(double[] constantArr){
		this.constantArr = constantArr;
	}
	
	private void setResultArr(String[] resultArr){
		this.resultArr = resultArr;
	}
	
	public String[] getResultArr(){
		return resultArr;
	}
	
	public boolean getCompletedFlag(){
		return this.competedFlag;
	}
	
	public void solveLinearEquationsbyJacobi(){
		//设置初值 
		double[] xDone = new double[matrixOrder];
		double xLength = 1; //初值长度
		double xDoneLength = 0; //迭代后的长度
		int doneTimes = 0;
		
		tempResultArr = new double[matrixOrder];
		
		double sum1 = 0;
		double sum2 = 0;
		//迭代过程
		while(Math.abs(xLength - xDoneLength)>Math.pow(10, -accuracyNumber)&&doneTimes<iterations){
			xLength = 0;
			xDoneLength = 0;
			
			for(int i=0;i<matrixOrder;i++){
				tempResultArr[i] = xDone[i];
			}
			
			for(int i=0;i<matrixOrder;i++){
				sum1 = 0;
				sum2 = 0;
				
				for(int j=0;j<i;j++){
					sum1 = sum1 + this.originalMatrix[i][j] * tempResultArr[j];
				}
				
				for(int j=i+1;j<matrixOrder;j++){
					sum2 = sum2 + originalMatrix[i][j] *tempResultArr[j];
				}
				if(originalMatrix[i][i]==0){
					resultArr = null;
					return;
				}
				xDone[i] = (this.constantArr[i] - sum1 - sum2) / originalMatrix[i][i];
			}
			
			for(int i=0;i<matrixOrder;i++){
				xLength += xLength + Math.pow(tempResultArr[i], 2);
				xDoneLength += xDoneLength + Math.pow(xDone[i], 2);
			}
			
			xLength = Math.pow(xLength, 0.5);
			xDoneLength = Math.pow(xDoneLength, 0.5);
			
			doneTimes++;
		}
		
		if(Double.isNaN(Math.abs(xLength - xDoneLength))||((Math.abs(xLength - xDoneLength)>Math.pow(10, -accuracyNumber)&&doneTimes==iterations))){
			competedFlag = false;
		}
		setResultArr(decimalChanger(tempResultArr));
	}
	
	//保留小数点后位数
	private String[] decimalChanger(double[] matrix){
		String[] stringMatrix = new String[matrixOrder];
		
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
		//若要不四舍五，加上此句即可
		df.setRoundingMode(java.math.RoundingMode.FLOOR);
		
		for(int i=0;i<matrixOrder;i++){
			if(String.valueOf(matrix[i]).equals("0.0")){
				stringMatrix[i] = "0";
			}else{
				stringMatrix[i] = df.format(matrix[i]);
			}		
		}
		return stringMatrix;
	}
	
	public static void main(String[] args){
		SolveLinearEquationsbyJacobi slej = new SolveLinearEquationsbyJacobi();
		slej.setMatrixOrder(3);
		slej.setDecimalNumber(11);
		slej.setAccuracyNumber(10);
		slej.setIterations(5);
		
		double[][] originalMatrix = {
			{1.00, 0.00, 0.00},      
			{0.00, 4.00, 0.00},    
			{0.00, 0.00, 10.00}
		};
		
		double[] constantArr = {0.00,0.00,0.00};
		
		slej.setOriginalMatrix(originalMatrix);
		slej.setConstantArr(constantArr);
		
		slej.solveLinearEquationsbyJacobi();
		
		String[] resultArr = slej.getResultArr();
		if(resultArr==null){
			System.out.print("该线性方程组无法进行Jacobi迭代求解");
		}else{
			for(int i=0;i<3;i++){
				System.out.print(resultArr[i] + ", ");
			}
			System.out.println();
			
			if(!slej.getCompletedFlag()){
				System.out.print("Warning:该线性方程组迭代求解时未达到精度要求");
			}	
		}
	}
}
