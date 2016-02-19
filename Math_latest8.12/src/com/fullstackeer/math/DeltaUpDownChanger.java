package com.fullstackeer.math;
//AA-上下三角形变换
public class DeltaUpDownChanger {
	
	private int matrixOrder = 0;
	private int decimalNumber = 0;
	private double[][] originalMatrix = null;
	private double[][] tempMatrix = null;
	private String[][] upMatrix = null;
	private String[][] downMatrix = null;
	
	public void setMatrixOrder(int matrixOrder){
		this.matrixOrder = matrixOrder;
	}

	public void setDecimalNumber(int decimalNumber){
		this.decimalNumber = decimalNumber;
	}
	
	public void setOriginalMatrix(double[][] matrix){
		this.originalMatrix = matrix;
	}
	
	private void setTempMatrix(){
		this.tempMatrix = copyArray(this.originalMatrix);
	}
	
	public void setUpMatrix(String[][] upMatrix){
		this.upMatrix = upMatrix;

	}
	
	public void setDownMatrix(String[][] downMatrix){
		this.downMatrix = downMatrix;
	}
	
	//得到变换后的上三角矩阵
	public String[][] getUpDelta(){
		setTempMatrix();
		double muni = 0;
		for(int i=0;i<matrixOrder;i++){
			for(int j=i+1;j<matrixOrder;j++){
				if(tempMatrix[i][i]!=0){
					muni = tempMatrix[j][i] / tempMatrix[i][i];
				}else{
					return null;
				}
				for(int k=i;k<matrixOrder;k++){
					tempMatrix[j][k] = tempMatrix[j][k] - muni*tempMatrix[i][k];
				}
			}
		}
		upMatrix = DecimalChanger(tempMatrix);
		return upMatrix;
	}
	//得到变换后的下三角矩阵
	public String[][] getDownDelta(){
		setTempMatrix();
		double muni = 0;
		for(int i=matrixOrder-1;i>0;i--){
			for(int j=i-1;j>=0;j--){
				if(tempMatrix[i][i]!=0){
				muni = tempMatrix[j][i] / tempMatrix[i][i];
				}else{
					return null;
				}
				for(int k=i;k>=0;k--){
					tempMatrix[j][k] = tempMatrix[j][k] - muni*tempMatrix[i][k];
				}
			}
		}
		downMatrix = DecimalChanger(tempMatrix);
		return downMatrix;
	}
	//保留小数点后位数
	public String[][] DecimalChanger(double[][] matrix){
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
	
	public double[][] copyArray(double[][]matrix){
		/*复制的普遍例子
		double[][] copyMatrix = new double[matrix.length][];
		for(int i=0;i<matrix.length;i++){
			copyMatrix[i]=new double[matrix[i].length];
			System.arraycopy(matrix[i], 0, copyMatrix[i], 0, matrix[i].length);
		}
		*/
		double[][] copyMatrix = new double[matrixOrder][matrixOrder];
		for(int i=0;i<matrixOrder;i++){
			System.arraycopy(matrix[i], 0, copyMatrix[i], 0, matrix[i].length);
		}
		return copyMatrix;
	}
	
	public static void main(String[] args){
		DeltaUpDownChanger dc = new DeltaUpDownChanger();
		dc.setMatrixOrder(3);
		dc.setDecimalNumber(2);
		
		double[][] originalMatrix = {
			{1.91,2.07,3.008},
			{4,5,6},
			{7,8,9}
		};
		
		String[][] changedMatrix = new String[3][3];
		
		dc.setOriginalMatrix(originalMatrix);
		
		changedMatrix = dc.getDownDelta();
		if(changedMatrix==null){
			System.out.println("矩阵无法进行下三角形变换");
		}else{
			System.out.println("矩阵的下三角形变换");
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					System.out.print(changedMatrix[i][j]+", ");
				}
				System.out.println();
			}
		}
		
		System.out.println();
		
		changedMatrix = dc.getUpDelta();
		if(changedMatrix==null){
			System.out.println("矩阵无法进行上三角形变换");
		}else{
			System.out.println("矩阵的上三角形变换");
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					System.out.print(changedMatrix[i][j]+", ");
				}
				System.out.println();
			}
		}
	}
}
