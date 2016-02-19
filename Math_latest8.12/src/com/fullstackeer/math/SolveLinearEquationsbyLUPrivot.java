package com.fullstackeer.math;
//AD-LU列主元变换求解线性方程组
public class SolveLinearEquationsbyLUPrivot {
	
	private int matrixOrder = 0;
	private int decimalNumber = 0;
	
	private double[][] originalMatrix = null;
	
	private double[] constantArr = null;
	
	private String[][] matrixofL = null;
	private String[][] matrixofU = null;
	
	private double[][] tempMatrixofU = null;
	private double[][] tempMatrixofL = null;
	
	private String[] pArr = null;
	private int[] tempPArr = null;
	
	private String[] resultArr = null;
	private double[] tempResultArr = null;
	
	private double[][][] inversedLMatrix= null;
	private double[][][] branchPMatrix= null;
	
	public void setMatrixOrder(int matrixOrder){
		this.matrixOrder = matrixOrder;
	}
	
	public void setDecimalNumber(int decimalNumber){
		this.decimalNumber = decimalNumber;
	}
	
	public void setOriginalMatrix(double[][] matrix){
		this.originalMatrix = matrix;
	}
	
	public void setConstantArr(double[] matrix){
		this.constantArr = matrix;
	}
	
	private void setMatrixofL(String[][] matrixofL){
		this.matrixofL = matrixofL;
	}
	
	private void setMatrixofU(String[][] matrixofU){
		this.matrixofU = matrixofU;
	}
	
	private void setPArr(String[] pArr){
		this.pArr = pArr;
	}
	
	private void setResultArr(String[] resultArr){
		this.resultArr = resultArr;
	}
	
	public String[][] getMatrixofL(){
		return matrixofL;
	}
	
	public String[][] getMatrixofU(){
		return matrixofU;
	}
	
	public String[] getPArr(){
		return pArr;
	}
	
	public String[] getResultArr(){
		return resultArr;
	}
	
	public void solveLinearEquationsbyLUPrivot(){
		//得到变换后的U double矩阵
		tempMatrixofU = getTempMatrixofU();
		
		if(tempMatrixofU==null){
			tempMatrixofL = null;
			tempResultArr = null;
			tempPArr = null;
			return;
		}
		//设定变换后的U String矩阵
		this.setMatrixofU(this.decimalChanger(tempMatrixofU));
		
		//得到变换后的L
		tempMatrixofL = getTempMatrixofL();
		//设定变换后的L String矩阵
		this.setMatrixofL(this.decimalChanger(tempMatrixofL));
		
		//得到求解得到的tempResultArr
		tempResultArr = getTempResultArr();
		
		if(tempResultArr==null){
			tempPArr = null;
			return;
		}
		
		//设定得到的resultArr
		this.setResultArr(this.decimalChanger(tempResultArr));		
		
		//设定变换后的P String矩阵
		for(int i=0;i<matrixOrder;i++){
			tempPArr[i] += 1;
		}
		this.setPArr(this.decimalChanger(tempPArr));
	}
	
	private double[][] getTempMatrixofU(){
		int tempInt = 0;
		double tempDouble = 0;
		tempPArr = new int[matrixOrder];
		branchPMatrix = new double[matrixOrder-1][matrixOrder][matrixOrder];
		inversedLMatrix = new double[matrixOrder-1][matrixOrder][matrixOrder];
		
		//给tempPArr与branchPMatrix赋予初值
		//将inversedLMatrix的主对角线元素变成1；
		for(int i=0;i<matrixOrder;i++){
			tempPArr[i] = i;
			if(i!=matrixOrder-1){
				for(int k=0;k<matrixOrder;k++){
					branchPMatrix[i][k][k] = 1;
					inversedLMatrix[i][k][k] = 1;
				}
			}
		}
		
		//列主元变换求取U
		for(int i=0;i<matrixOrder-1;i++){
			int rowMax = i;
			double rowMaxValue = Math.abs(originalMatrix[i][i]);
			
			//寻找最大值
			for(int j=i;j<matrixOrder;j++){
				if(Math.abs(originalMatrix[j][i])>rowMaxValue){
					rowMax = j;
					rowMaxValue = Math.abs(originalMatrix[j][i]);
				}
			}
			
			tempInt = tempPArr[i];
			tempPArr[i] = tempPArr[rowMax];
			tempPArr[rowMax] = tempInt;
			
			branchPMatrix[i][i][i] = 0;
			branchPMatrix[i][rowMax][rowMax] = 0;
			branchPMatrix[i][rowMax][i]=1;
			branchPMatrix[i][i][rowMax]=1;
			
			//交换行
			for(int k=i;k<matrixOrder;k++){
				tempDouble = originalMatrix[i][k];
				originalMatrix[i][k] = originalMatrix[rowMax][k];
				originalMatrix[rowMax][k] = tempDouble;
			}
			
			//消元
			for(int j=i+1;j<matrixOrder;j++){
				if(originalMatrix[i][i]==0){
					originalMatrix = null;
					return originalMatrix;
				}
				tempDouble = originalMatrix[j][i]/originalMatrix[i][i];
				inversedLMatrix[i][j][i] = tempDouble; 
				for(int k=i;k<matrixOrder;k++){
					originalMatrix[j][k] = originalMatrix[j][k] - tempDouble*originalMatrix[i][k];
				}
			}
		}
		
		return originalMatrix;
	}
	
	
	private double[][] getTempMatrixofL(){
		double[][][] everyPLmultiplier = new double[matrixOrder-1][matrixOrder][matrixOrder];
		//将每个P和L相乘
		for(int i=0;i<matrixOrder-1;i++){
			for(int j=0;j<matrixOrder;j++){
				for(int k=0;k<matrixOrder;k++){
					everyPLmultiplier[i][j][k]=0;
					for(int h=0;h<matrixOrder;h++){
						everyPLmultiplier[i][j][k] = branchPMatrix[i][j][h] * inversedLMatrix[i][h][k] + everyPLmultiplier[i][j][k];
					}
				}
			}
		}
		
		//计算最终的PL乘积
		double[][][] allPLmultiplier = new double[matrixOrder-1][matrixOrder][matrixOrder];
	    //先将everyPLmultiplier[0][j][k]赋予allPLmultiplier[0][j][k]
		for(int j=0;j<matrixOrder;j++){
			for(int k=0;k<matrixOrder;k++){
				allPLmultiplier[0][j][k] = everyPLmultiplier[0][j][k];
			}
		}
		//再计算所有的everyPLmultiplier乘积
		for(int i=0;i<matrixOrder-2;i++){
			for(int j=0;j<matrixOrder;j++){
				for(int k=0;k<matrixOrder;k++){
					allPLmultiplier[i+1][j][k] = 0;
					for(int h=0;h<matrixOrder;h++){
						allPLmultiplier[i+1][j][k] = allPLmultiplier[i][j][h]*everyPLmultiplier[i+1][h][k] + allPLmultiplier[i+1][j][k];
					}
				}
			}
		}
		
		//得到最终的L
		double[][] finalL = new double[matrixOrder][matrixOrder];
		
		for(int i=0;i<matrixOrder;i++){
			for(int j=0;j<matrixOrder;j++){
				finalL[i][j] = allPLmultiplier[matrixOrder-2][tempPArr[i]][j];
			}
		}
		return finalL;
	}
	
	private double[] getTempResultArr(){
		//PAx = Pb ===> LUx = Pb
		//Ly = Pb 先求取y
		double[] y = new double[matrixOrder];
		y = solveYArr();
		//Ux = y 在求取x
		double[] x = new double[matrixOrder];
		x = solveXArr(y);
		return x;
	}
	
	private double[] solveYArr(){
		double[] tempArr = new double[matrixOrder];
		double temp = 0;
		double tempY[] = new double[matrixOrder];
		
		for(int i=0;i<matrixOrder;i++){
			tempArr[i] = constantArr[tempPArr[i]];
		}
		
		for(int i=0;i<matrixOrder;i++){
			temp = tempArr[i];
			for(int j=0;j<matrixOrder;j++){
				temp = temp - tempMatrixofL[i][j]*tempY[j];
			}
			if(tempMatrixofL[i][i]==0){
				tempY = null;
				return tempY;
			}
			if(tempMatrixofL[i][i]==0){
				tempY = null;
				return tempY;
			}
			tempY[i]=temp/tempMatrixofL[i][i];
		}
		
		return tempY;
	}
	
	private double[] solveXArr(double[] y){
		double temp = 0;
		double tempX[] = new double[matrixOrder];

		if(y==null){
			tempX = null;
			return tempX;
		}
		
		for(int i=matrixOrder-1;i>=0;i--){
			temp = y[i];
			for(int j=matrixOrder-1;j>i;j--){
				temp = temp - tempMatrixofU[i][j]*tempX[j];
			}
			
			if(tempMatrixofU[i][i]==0){
				tempX = null;
				return tempX;
			}
			tempX[i]=temp/tempMatrixofU[i][i];
		}
		
		return tempX;
	}
	//保留小数点后位数
	private String[][] decimalChanger(double[][] matrix){
		String[][] stringMatrix = new String[matrixOrder][matrixOrder];
		
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
		
		for(int i=0;i<matrixOrder;i++){
			for(int j=0;j<matrixOrder;j++){
				if(String.valueOf(matrix[i][j]).equals("0.0")){
					stringMatrix[i][j] = "0";
				}else{
					stringMatrix[i][j] = df.format(matrix[i][j]);
				}
			}
		}
		return stringMatrix;
	}
	
	private String[] decimalChanger(int[] matrix){
		String[] stringMatrix = new String[matrixOrder]; 
		
		for(int i=0;i<matrixOrder;i++){
			stringMatrix[i] = ""+matrix[i];
		}
		return stringMatrix;
	}
	
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
		SolveLinearEquationsbyLUPrivot sleLUp = new SolveLinearEquationsbyLUPrivot();
		sleLUp.setMatrixOrder(8);
		sleLUp.setDecimalNumber(2);
		
		double[][] originalMatrix = {
			{15.00, 35.00, 47.00, 17.00, 3.00,  95.00, 2.00,  9.00},
			{60.00, 47.00, 94.00, 38.00, 0.00,  20.00, 22.00, 26.00},
			{48.00, 81.00, 40.00, 42.00, 67.00, 31.00, 55.00, 21.00},
			{98.00, 79.00, 15.00, 65.00, 78.00, 68.00, 11.00, 15.00},
			{87.00, 72.00, 41.00, 31.00, 95.00, 26.00, 30.00, 81.00},
			{6.00,  79.00, 10.00, 17.00, 65.00, 81.00, 30.00, 40.00},
			{30.00, 78.00, 70.00, 90.00, 74.00, 92.00, 22.00, 99.00},
			{5.00,  84.00, 97.00, 93.00, 62.00, 83.00, 0.00,  47.00}
		};
		
		double[] constantArr = {98,16,34,58,7,56,30,39};
		
		String[][] changedMatrix = new String[8][8];
		String[] resultArr = new String[8];
		
		sleLUp.setOriginalMatrix(originalMatrix);
		sleLUp.setConstantArr(constantArr);
		
		sleLUp.solveLinearEquationsbyLUPrivot();
		
		resultArr = sleLUp.getResultArr();
		if(resultArr==null){
			System.out.println("矩阵无法进行列主元LU变换求解");
		}else{
			for(int i=0;i<8;i++){
				System.out.print(resultArr[i]+" ,");
			}
			System.out.println();
		}
		
		System.out.println();
		
		resultArr = sleLUp.getPArr();
		if(resultArr==null){
			System.out.println("矩阵无法进行列主元LU变换求解");
		}else{
			for(int i=0;i<8;i++){
				System.out.print(resultArr[i]+" ,");
			}
			System.out.println();
		}
		
		System.out.println();
		
		changedMatrix = sleLUp.getMatrixofL();
		
		if(changedMatrix==null){
			System.out.println("矩阵无法进行列主元LU变换求解");
		}else{
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++){
					System.out.print(changedMatrix[i][j]+", ");
				}
				System.out.println();
			}
		}
		
		System.out.println();
		
		changedMatrix = sleLUp.getMatrixofU();
		
		if(changedMatrix==null){
			System.out.println("矩阵无法进行列主元LU变换求解");
		}else{
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++){
					System.out.print(changedMatrix[i][j]+", ");
				}
				System.out.println();
			}
		}
		
		System.out.println();
		
	}
}
