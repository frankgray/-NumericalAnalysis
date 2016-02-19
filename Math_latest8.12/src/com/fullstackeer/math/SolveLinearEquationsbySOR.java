package com.fullstackeer.math;
//AJ-超松弛迭代求解线性方程组
public class SolveLinearEquationsbySOR {
	
	private int matrixOrder = 0;
	private int decimalNumber = 0;
	private int accuracyNumber = 0;
	private int iterations = 0;
	private double factorω = 0;
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
	
	public void setFactorω(double factorω){
		this.factorω = factorω;
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
	
	public void solveLinearEquationsbySOR(){
		//设置初值
		double maxDistance = 1;
		double temp = 0;
		int doneTimes = 0;
		
		tempResultArr = new double[matrixOrder];
		
		double sum1 = 0;
		double sum2 = 0;
		
		//迭代过程
		while(maxDistance>Math.pow(10, -accuracyNumber)&&doneTimes<iterations){
			maxDistance = 0;
			
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
				
				temp = tempResultArr[i];
				tempResultArr[i] = factorω*(this.constantArr[i] - sum1 - sum2) / originalMatrix[i][i]
						+(1-factorω)*tempResultArr[i];
				temp = Math.abs(temp - tempResultArr[i]);
				
				maxDistance = temp>maxDistance?temp:maxDistance;
			}
				
			doneTimes++;
		}
		
		if(Double.isNaN(maxDistance)||(maxDistance>Math.pow(10, -accuracyNumber)&&doneTimes==iterations)){
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
		SolveLinearEquationsbySOR sles = new SolveLinearEquationsbySOR();
		sles.setMatrixOrder(3);
		sles.setDecimalNumber(5);
		sles.setAccuracyNumber(7);
		sles.setIterations(200);
		sles.setFactorω(0);
		
		double[][] originalMatrix = {
			{3.00, 0.00, 0.00},      
			{0.00, 4.00, 0.00},    
			{0.00, 0.00, 4.00} 
		};
		
		double[] constantArr = {24.00,30.00,-24.00};
		
		sles.setOriginalMatrix(originalMatrix);
		sles.setConstantArr(constantArr);
		
		sles.solveLinearEquationsbySOR();
		
		String[] resultArr = sles.getResultArr();
		
		if(resultArr==null){
			System.out.print("该线性方程组无法进行SOR迭代求解");
		}else{
			for(int i=0;i<3;i++){
				System.out.print(resultArr[i] + ", ");
			}
			System.out.println();
			
			if(!sles.getCompletedFlag()){
				System.out.print("Warning:该线性方程组迭代求解时未达到精度要求");
			}
		}
	}
}
