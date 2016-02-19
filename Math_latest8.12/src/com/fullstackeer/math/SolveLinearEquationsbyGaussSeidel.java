package com.fullstackeer.math;
//AI-Gauss-Seidel迭代求解线性方程组
public class SolveLinearEquationsbyGaussSeidel {
	
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
	
	public void solveLinearEquationsbyGaussSeidel(){
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
				
				temp = tempResultArr[i];
				if(originalMatrix[i][i]==0){
					resultArr= null;
					return;
				}
				tempResultArr[i] = (this.constantArr[i] - sum1 - sum2) / originalMatrix[i][i];
				temp = Math.abs(temp - tempResultArr[i]);
				
				maxDistance = temp>maxDistance?temp:maxDistance;
			}
				
			doneTimes++;
		}
		
		if(Double.isNaN(Math.abs(maxDistance))||(maxDistance>Math.pow(10, -accuracyNumber)&&doneTimes==iterations)){
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

			stringMatrix[i] = df.format(matrix[i]);
			if(stringMatrix[i].equals(sbString.toString())||stringMatrix[i].equals("-"+sbString.toString())) stringMatrix[i]="0";
			
		}
		return stringMatrix;
	}
	
	public static void main(String[] args){
		SolveLinearEquationsbyGaussSeidel slegs = new SolveLinearEquationsbyGaussSeidel();
		slegs.setMatrixOrder(2);
		slegs.setDecimalNumber(5);
		slegs.setAccuracyNumber(10);
		slegs.setIterations(40);
		
		double[][] originalMatrix = {
			{850.943, 1999999999999993.925},      
			{13.925, 696.480} 
		};
		
		double[] constantArr = {-12.00,20.00,3.00};
		
		slegs.setOriginalMatrix(originalMatrix);
		slegs.setConstantArr(constantArr);
		
		slegs.solveLinearEquationsbyGaussSeidel();
		
		String[] resultArr = slegs.getResultArr();
		if(resultArr==null){
			System.out.print("该线性方程组无法进行Gauss-Seidel迭代求解");
		}else{
			for(int i=0;i<3;i++){
				System.out.print(resultArr[i] + ", ");
			}
			
			System.out.println();
			
			if(!slegs.getCompletedFlag()){
				System.out.print("Warning:该线性方程组迭代求解时未达到精度要求");
			}
		}
	}
}
