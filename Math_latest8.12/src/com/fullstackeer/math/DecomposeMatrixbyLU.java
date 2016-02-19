package com.fullstackeer.math;
//AC-矩阵LU分解
public class DecomposeMatrixbyLU {
	
	private int matrixOrder = 0;
	private int decimalNumber = 0;
	private double[][] originalMatrix = null;
	
	private String[][] matrixofL = null;
	private String[][] matrixofU = null;
	
	private double[][] tempMatrixofU = null;
	private double[][] tempMatrixofL = null;
	
	public void setMatrixOrder(int matrixOrder){
		this.matrixOrder = matrixOrder;
	}
	
	public void setDecimalNumber(int decimalNumber){
		this.decimalNumber = decimalNumber;
	}
	
	public void setOriginalMatrix(double[][] matrix){
		this.originalMatrix = matrix;
	}
	
	private void setMatrixofL(String[][] matrixofL){
		this.matrixofL = matrixofL;
	}
	
	private void setMatrixofU(String[][] matrixofU){
		this.matrixofU = matrixofU;
	}
	
	public void decomposeMatrix(){
		double temp;
		tempMatrixofU = new double[matrixOrder][matrixOrder];
		tempMatrixofL = new double[matrixOrder][matrixOrder];
		
	    for(int i=0;i<matrixOrder;i++){
	    	tempMatrixofU[i][i]=1;
	    }
	    
	    for(int i=0;i<matrixOrder-1;i++){
	    	for(int j=i+1;j<matrixOrder;j++){
	    		if(originalMatrix[i][i]==0){
	    			matrixofL = null;
	    			matrixofU = null;
	    			return;
	    		}
	    		
	    		temp = originalMatrix[j][i]/originalMatrix[i][i];
	    		tempMatrixofU[j][i] = temp;
	    		for(int k=i;k<matrixOrder;k++){
	    			originalMatrix[j][k] = originalMatrix[j][k] - temp*originalMatrix[i][k];
	    		}
	    	}
	    }
	    
	    tempMatrixofL = originalMatrix;
	    setMatrixofL(DecimalChanger(tempMatrixofL));
	    setMatrixofU(DecimalChanger(tempMatrixofU));
	}
	
	public String[][] getMatrixofL(){
		return matrixofL;
	}
	
	public String[][] getMatrixofU(){
		return matrixofU;		
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
		DecomposeMatrixbyLU dmlu = new DecomposeMatrixbyLU();
		dmlu.setMatrixOrder(7);
		dmlu.setDecimalNumber(2);
		
		double[][] originalMatrix = {
			{0.98, 34.82, 29.48, 63.34, 49.36, 82.88, 67.91  }, 
			{78.75, 71.34, 94.85, 63.49, 12.70, 56.18, 85.33  },
			{35.12, 2.76,  52.45, 34.44, 99.46, 57.16, 0.30   },
			{38.29, 51.05, 34.12, 83.06, 55.51, 28.52, 74.35  },
			{35.87, 57.01, 93.75, 11.56, 15.91, 37.85, 65.95  },
			{79.09, 18.77, 69.17, 2.18,  80.04, 6.72,  13.69  },
			{54.26, 14.32, 37.19, 74.79, 14.23, 29.16, 69.85  },
		};
		
		String[][] L = new String[7][7];
		String[][] U = new String[7][7];
		
		dmlu.setOriginalMatrix(originalMatrix);
		
		dmlu.decomposeMatrix();
		
		L = dmlu.getMatrixofL();
		U = dmlu.getMatrixofU();
		
		if(L==null){
			System.out.println("矩阵无法进行LU分解");
		}else{
			System.out.println("这是LU分解后的U");
			for(int i=0;i<7;i++){
				for(int j=0;j<7;j++){
					System.out.print(L[i][j]+", ");
				}
				System.out.println();
			}
		}
		
		System.out.println("");
		
		if(U==null){
			System.out.println("矩阵无法进行LU分解");
		}else{
			System.out.println("这是LU分解后的L");
			for(int i=0;i<7;i++){
				for(int j=0;j<7;j++){
					System.out.print(U[i][j]+", ");
				}
				System.out.println();
			}
		}
	}
}
