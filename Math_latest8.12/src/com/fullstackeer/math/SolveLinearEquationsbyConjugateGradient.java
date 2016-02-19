package com.fullstackeer.math;
//AL-共轭斜量法迭代求解线性方程组
public class SolveLinearEquationsbyConjugateGradient {
	
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
	
	public void solveLinearEquationsbyConjugateGradient(){
		//设置初值
		double[] xDone = new double[matrixOrder];
		double xLength = 1; //初值
		double xDoneLength = 0; //迭代后的值
		int doneTimes = 0;
		
		tempResultArr = new double[matrixOrder];
		double[] p = new double[matrixOrder];
		double[] pDone = new double[matrixOrder];
		
		double[] r = new double[matrixOrder];
		
		double sum = 0;
		double α = 0;
		double r_Ap = 0;
		double p_Ap = 0;
		double β = 0;
		double r_pDone = 0;
		double ApDone_pDone = 0;
		
		//迭代过程
		while(Math.abs(xLength - xDoneLength)>Math.pow(10, -accuracyNumber)&&doneTimes<iterations){
			//设定初值
			xLength = 0;
			xDoneLength = 0;
			
			for(int i=0;i<matrixOrder;i++){
				tempResultArr[i] = xDone[i];
				p[i] = pDone[i];
			}
			
			//先求取r
			for(int i=0;i<this.matrixOrder;i++){
				sum = 0;
				for(int j=0;j<this.matrixOrder;j++){
					sum += this.originalMatrix[i][j]*this.tempResultArr[j];
				}
				
				r[i] = this.constantArr[i] - sum;
			}
			
			//得到p的初值
			if(doneTimes==0){
				for(int i=0;i<this.matrixOrder;i++){
					p[i] = r[i];
				}
			}
			
			//求取β
			//1、求取（r,Ap）
			r_Ap = 0;
			for(int i=0;i<this.matrixOrder;i++){
				for(int j=0;j<this.matrixOrder;j++){
					r_Ap += p[j]*this.originalMatrix[j][i]*r[i];
				}
			}
			
			//2、求取（p,Ap）
			p_Ap = 0;
			for(int i=0;i<this.matrixOrder;i++){
				for(int j=0;j<this.matrixOrder;j++){
					p_Ap += p[j]*this.originalMatrix[j][i]*p[i];
				}
			}
			
			//3、求取β
			if(p_Ap==0){
				resultArr = null;
				return;
			}
			β = -r_Ap/p_Ap;
			if(doneTimes == 0) β = 0;
			
			//求取pDone
			for(int i=0;i<this.matrixOrder;i++){
				pDone[i] = r[i] + β*p[i];
			}
			
			//求取α
			//1、求取（r,pDone）
			r_pDone = 0;
			for(int i=0;i<this.matrixOrder;i++){
				r_pDone += pDone[i]*r[i];
			}
			
			//2、判断r是否已经符合要求
			if(r_pDone==0) break;

			//3、求取（ApDone,pDone）
			ApDone_pDone = 0;
			for(int i=0;i<this.matrixOrder;i++){
				for(int j=0;j<this.matrixOrder;j++){
					ApDone_pDone += pDone[j]*this.originalMatrix[j][i]*pDone[i];
				}
			}
			
			//4、得到α
			if(ApDone_pDone==0){
				resultArr = null;
				return;				
			}
			α = r_pDone/ApDone_pDone;
			
			//求取结果
			for(int i=0;i<this.matrixOrder;i++){
				xDone[i] = this.tempResultArr[i] + α*r[i];
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
		
		SolveLinearEquationsbyConjugateGradient slecg = new SolveLinearEquationsbyConjugateGradient();
		slecg.setMatrixOrder(3);
		slecg.setDecimalNumber(5);
		slecg.setAccuracyNumber(10);
		slecg.setIterations(200);
		
		double[][] originalMatrix = {
			{1.00, 0.00, 99999.00},      
			{-0.00, 4.00, 0.00},    
			{0.00, -0.00, 10.00}
		};
		
		double[] constantArr = {-12.00,20.00,3.00};
		
		slecg.setOriginalMatrix(originalMatrix);
		slecg.setConstantArr(constantArr);
		
		slecg.solveLinearEquationsbyConjugateGradient();
		
		String[] resultArr = slecg.getResultArr();
		
		if(resultArr==null){
			System.out.println("该矩阵无法进行共轭斜量法迭代求解线性方程组");
		}else{
			for(int i=0;i<3;i++){
				System.out.print(resultArr[i] + ", ");
			}
			if(!slecg.getCompletedFlag()){
				System.out.print("Warning:该线性方程组迭代求解时未达到精度要求");
			}
		}
	}	
}
