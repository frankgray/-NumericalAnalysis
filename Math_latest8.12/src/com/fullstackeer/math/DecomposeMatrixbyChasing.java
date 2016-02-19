package com.fullstackeer.math;
//AG-使用追赶方法分解对称正定矩阵
public class DecomposeMatrixbyChasing {

	private int matrixOrder = 0;
	private int decimalNumber = 0;
	
	private double[][] originalMatrix = null;
	
	private double[][] tempResultMatrixofL = null;
	private double[][] tempResultMatrixofU = null;
	
	private String[][] resultMatrixofL = null;
	private String[][] resultMatrixofU = null;
	
	public void setMatrixOrder(int matrixOrder){
		this.matrixOrder = matrixOrder;
	}
	
	public void setDecimalNumber(int decimalNumber){
		this.decimalNumber = decimalNumber;
	}
	
	public void setOriginalMatrix(double[][] originalMatrix){
		this.originalMatrix = originalMatrix;
	}
	
	private void setResultMatrixofL(String[][] resultMatrixofL){
		this.resultMatrixofL = resultMatrixofL;
	}

	private void setResultMatrixofU(String[][] resultMatrixofU){
		this.resultMatrixofU = resultMatrixofU;
	}
	
	public String[][] getResultMatrixofL(){
		return resultMatrixofL;
	}

	public String[][] getResultMatrixofU(){
		return resultMatrixofU;
	}
	
	//通过Cholesky求解线形方程组
	public void solveLinearEquationsbyChasing(){
		tempResultMatrixofL = new double[matrixOrder][matrixOrder];
		tempResultMatrixofU = new double[matrixOrder][matrixOrder];
		
		double[] l = new double[matrixOrder+1];
		double[] u = new double[matrixOrder+1];
		double[] r = new double[matrixOrder];
		
		for(int i=1;i<matrixOrder;i++){
			r[i] = this.originalMatrix[i-1][i];
		}
		
		u[1] = this.originalMatrix[0][0];
		
		for(int i=2;i<matrixOrder+1;i++){
			if(u[i-1] == 0){
				resultMatrixofL = null;
				resultMatrixofU = null;
				return;
			}
			
			l[i] = this.originalMatrix[i-1][i-2]/u[i-1];
			u[i] = this.originalMatrix[i-1][i-1] - l[i]*originalMatrix[i-2][i-1];
		}
		
		for(int i=0;i<matrixOrder;i++){
			this.tempResultMatrixofL[i][i] = 1;
			this.tempResultMatrixofU[i][i] = u[i+1];
		}
		
		for(int i=1;i<matrixOrder;i++) {
			this.tempResultMatrixofL[i][i-1] = l[i+1];
			this.tempResultMatrixofU[i-1][i] = r[i];			
		}
		
		this.setResultMatrixofL(this.DecimalChanger(this.tempResultMatrixofL));
		this.setResultMatrixofU(this.DecimalChanger(this.tempResultMatrixofU));
	}

	//保留小数点后位数
	private String[][] DecimalChanger(double[][] matrix){
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

	//测试方法
	public static void main(String[] args){
		DecomposeMatrixbyChasing slec = new DecomposeMatrixbyChasing();
		slec.setMatrixOrder(4);
		slec.setDecimalNumber(2);
		
		double[][] originalMatrix = {
			{2.00, -1.00,0.00, 0.00},
			{-1.00,2.00, -1.00, 0.00}, 
			{0.00, -1.00,2.00, -1.00},    
			{0.00, 0.00, -1.00,2.00 }
		};
		
		slec.setOriginalMatrix(originalMatrix);
		slec.solveLinearEquationsbyChasing();
	
		String[][] resultL = slec.getResultMatrixofL();
		String[][] resultU = slec.getResultMatrixofU();
		
		if(resultL==null){
			System.out.println("该矩阵不可进行LU变换,无法得到L");
		}else{
			System.out.println("这是下三角形L");
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					System.out.print(resultL[i][j]+", ");
				}
				System.out.println();
			}
		}
		
		System.out.println();
		if(resultU==null){
			System.out.println("该矩阵不可进行LU变换,无法得到U");
		}else{
			System.out.println("这是上三角形U");
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					System.out.print(resultU[i][j]+", ");
				}
				System.out.println();
			}
		}
		System.out.println();
	}
}
