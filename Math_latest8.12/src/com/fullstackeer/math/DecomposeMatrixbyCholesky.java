package com.fullstackeer.math;
//AF-使用Choesky方法分解对称正定矩阵
public class DecomposeMatrixbyCholesky {

		private int matrixOrder = 0;
		private int decimalNumber = 0;
		
		private double[][] originalMatrix = null;
		
		private double[][] doubleResultMatrix = null;
		
		private String[][] resultMatrix = null;
		
		public void setMatrixOrder(int matrixOrder){
			this.matrixOrder = matrixOrder;
		}
		
		public void setDecimalNumber(int decimalNumber){
			this.decimalNumber = decimalNumber;
		}
		
		public void setOriginalMatrix(double[][] originalMatrix){
			this.originalMatrix = originalMatrix;
		}
		
		private void setResultMatrix(String[][] resultMatrix){
			this.resultMatrix = resultMatrix;
		}
		
		public String[][] getStringResultMatrix(){
			return resultMatrix;
		}
		
		public double[][] getDoubleResultMatrix(){
			return this.doubleResultMatrix;
		}
		//通过Cholesky求解线形方程组
		public void solveLinearEquationsbyCholesky(){
			doubleResultMatrix = new double[matrixOrder][matrixOrder];
			
			for(int j=0;j<matrixOrder;j++){
				for(int i=j;i<matrixOrder;i++){
					if(j==0){
						if(i==0){
							doubleResultMatrix[i][j] = Math.pow(originalMatrix[i][j], 0.5);
						}else{
							if(doubleResultMatrix[0][0]==0){
								resultMatrix = null;
								return;
							}else{
								doubleResultMatrix[i][j] = originalMatrix[i][j]/doubleResultMatrix[0][0];
							}
						}
					}else{
						double sum = 0;
						if(i==j){
							for(int k=0;k<j;k++){
								sum += Math.pow(doubleResultMatrix[j][k], 2);
							}
							if((originalMatrix[j][j]-sum)<0){
								resultMatrix = null;
								return;
							}else{
								doubleResultMatrix[j][j] = Math.pow(originalMatrix[j][j]-sum, 0.5);
							}
						}else{
							for(int k=0;k<j;k++){
								sum += doubleResultMatrix[i][k]*doubleResultMatrix[j][k];
							}
							if(doubleResultMatrix[j][j]==0){
								resultMatrix = null;
								return;
							}else{
								doubleResultMatrix[i][j] = (originalMatrix[i][j]-sum)/doubleResultMatrix[j][j];
							}
						}
					}
				}
			}
			
			setResultMatrix(this.DecimalChanger(doubleResultMatrix));
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
					}}
			}
			return stringMatrix;
		}

		//测试方法
		public static void main(String[] args){
			DecomposeMatrixbyCholesky slec = new DecomposeMatrixbyCholesky();
			slec.setMatrixOrder(3);
			slec.setDecimalNumber(2);
			
			double[][] originalMatrix = {
				{5.00, -2.00, 1.00},
				{-2.00, 4.25, 2.75},
				{1.00,  2.75, 3.50}
			};
			
			slec.setOriginalMatrix(originalMatrix);
			slec.solveLinearEquationsbyCholesky();
			
			String[][] resultMatrix = slec.getStringResultMatrix();
			
			if(resultMatrix==null){
				System.out.println("该矩阵不是对称正定矩阵或者无法用Choesky方法分解");
			}else{
				System.out.println("这是下三角形L");
				for(int i=0;i<3;i++){
					for(int j=0;j<3;j++){
						System.out.print(resultMatrix[i][j]+", ");
					}
					System.out.println();
				}
			}
			System.out.println();
			
			if(resultMatrix==null){
				System.out.println("该矩阵不是对称正定矩阵或者无法用Choesky方法分解");
			}else{
				System.out.println("这是上三角形U");
				for(int i=0;i<3;i++){
					for(int j=0;j<3;j++){
						System.out.print(resultMatrix[j][i]+", ");
					}
					System.out.println();
				}
			}
			System.out.println();
		}
}
