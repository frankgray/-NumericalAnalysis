package com.fullstackeer.math;
//AK-对称超松弛迭代求解线性方程组
public class SolveLinearEquationsbySSOR {
	
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
	
	public void solveLinearEquationsbySSOR(){ 
		//设置初值
		double maxDistance = 1;
		double temp = 0;
		int doneTimes = 0;
		
		double[] originalResultArr = new double[matrixOrder];
		tempResultArr = new double[matrixOrder];
		
		double sum = 0;
		
		//迭代过程
		while(maxDistance>Math.pow(10, -accuracyNumber)&&doneTimes<iterations){
			
			maxDistance = 0;
			
			for(int i=0;i<matrixOrder;i++){
				originalResultArr[i] = tempResultArr[i];
			}
			
			//先得到中间值
			for(int i=0;i<matrixOrder;i++){
				sum = 0;
				
				for(int j=0;j<matrixOrder;j++){
					sum = sum + originalMatrix[i][j] *tempResultArr[j];
				}
				if(originalMatrix[i][i]==0){
					resultArr = null;
					return;
				}
				tempResultArr[i] = factorω*(this.constantArr[i] - sum) / originalMatrix[i][i]
						+ tempResultArr[i];
			}
			
			//再得到最终值	
			for(int i=matrixOrder-1;i>0;i--){
				sum = 0;
				
				for(int j=0;j<matrixOrder;j++){
					sum = sum + originalMatrix[i][j] *tempResultArr[j];
				}
				if(originalMatrix[i][i]==0){
					resultArr = null;
					return;
				}
				tempResultArr[i] = factorω*(this.constantArr[i] - sum) / originalMatrix[i][i]
						+ tempResultArr[i];
			}
			
			//得到最大差值
			for(int i=0;i<matrixOrder;i++){
				
				temp = Math.abs(originalResultArr[i] - tempResultArr[i]);
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
		SolveLinearEquationsbySSOR sles = new SolveLinearEquationsbySSOR();
		sles.setMatrixOrder(3);
		sles.setDecimalNumber(5);
		sles.setAccuracyNumber(7);
		sles.setIterations(2);
		sles.setFactorω(1.25);
		
		double[][] originalMatrix = {
			{1.00, 0.00, 0.00},      
			{3.00, 4.00, -1.00},    
			{0.00, -1.00, 4.00} 
		};
		
		double[] constantArr = {24.00,30.00,-24.00};
		
		sles.setOriginalMatrix(originalMatrix);
		sles.setConstantArr(constantArr);
		
		sles.solveLinearEquationsbySSOR();
		
		String[] resultArr = sles.getResultArr();
		
		if(resultArr==null){
			System.out.print("该线性方程组无法进行SSOR迭代求解");
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
