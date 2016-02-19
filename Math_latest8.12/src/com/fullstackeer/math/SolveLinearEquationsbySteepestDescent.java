package com.fullstackeer.math;
//AM-最速下降法迭代求解线性方程组
public class SolveLinearEquationsbySteepestDescent {
	
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
	
	public void solveLinearEquationsbySteepestDescent(){
		//设置初值
		double[] xDone = new double[matrixOrder];
		double xLength = 1; //初值
		double xDoneLength = 0; //迭代后的值
		int doneTimes = 0;
		
		tempResultArr = new double[matrixOrder];
		double[] r = new double[matrixOrder];
		
		double sum = 0;
		double r_r = 0;
		double Ar_r = 0;
		double α = 0;
		//迭代过程
		while(Math.abs(xLength - xDoneLength)>Math.pow(10, -accuracyNumber)&&doneTimes<iterations){	
			xLength = 0;
			xDoneLength = 0;
			
			for(int i=0;i<matrixOrder;i++){
				tempResultArr[i] = xDone[i];
			}
			
			//先求取r
			for(int i=0;i<matrixOrder;i++){
				sum = 0;
				for(int j=0;j<matrixOrder;j++){
					sum += this.originalMatrix[i][j]*this.tempResultArr[j];
				}
				
				r[i] = this.constantArr[i] - sum;
			}
			
			//求取α
			//1、先求取（r,r）
			r_r = 0;
			for(int i=0;i<matrixOrder;i++){
				r_r += r[i]*r[i];
			}
			//判断r是否已经符合要求
			if(r_r==0) break;
			//2、求取（Ar,r）
			Ar_r = 0;
			for(int i=0;i<this.matrixOrder;i++){
				for(int j=0;j<this.matrixOrder;j++){
					Ar_r += r[j]*this.originalMatrix[j][i]*r[i];
				}
			}
			
			//3、得到α
			if(Ar_r==0){
				resultArr = null;
				return;
			}
			α= r_r/Ar_r;
			
			//求取结果
			for(int i=0;i<this.matrixOrder;i++){
				xDone[i] = tempResultArr[i] + α*r[i];
			}
			
			for(int i=0;i<matrixOrder;i++){
				xLength += xLength + Math.pow(tempResultArr[i], 2);
				xDoneLength += xDoneLength + Math.pow(xDone[i], 2);
			}
			
			xLength = Math.pow(xLength, 0.5);
			xDoneLength = Math.pow(xDoneLength, 0.5);
			
			doneTimes++;
		}
		
		if(Double.isNaN(xLength - xDoneLength)||(Math.abs(xLength - xDoneLength)>Math.pow(10, -accuracyNumber)&&doneTimes==iterations)){
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
		SolveLinearEquationsbySteepestDescent slesd = new SolveLinearEquationsbySteepestDescent();
		slesd.setMatrixOrder(3);
		slesd.setDecimalNumber(5);
		slesd.setAccuracyNumber(10000);
		slesd.setIterations(1000);
		
		double[][] originalMatrix = {
			{1.00, 0.00, 999999999999999999999.00},      
			{0.00, 1.00, 0.00},    
			{0.00, 0.00, 1.00} 
		};
		
		double[] constantArr = {-12.00,20.00,3.00};
		
		slesd.setOriginalMatrix(originalMatrix);
		slesd.setConstantArr(constantArr);
		
		slesd.solveLinearEquationsbySteepestDescent();
		
		String[] resultArr = slesd.getResultArr();
		
		if(resultArr==null){
			System.out.print("该线性方程组无法进行最速下降法迭代求解");
		}else{
			for(int i=0;i<3;i++){
				System.out.print(resultArr[i] + ", ");
			}
			
			System.out.println();
			
			if(!slesd.getCompletedFlag()){
				System.out.print("Warning:该线性方程组迭代求解时未达到精度要求");
			}
		}
	}
}
