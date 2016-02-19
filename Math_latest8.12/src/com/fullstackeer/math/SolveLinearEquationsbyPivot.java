package com.fullstackeer.math;
//AB-列主元及全主元求解线性方程组
public class SolveLinearEquationsbyPivot {
	
	private int matrixOrder = 0;
	private int decimalNumber = 0;
	
	private double[][] originalMatrix = null;
	private double[][] tempMatrix = null;
	
	private double[] constantArr = null;
	private double[] tempArr = null;
	
	private String[][] upMatrixbyCol = null;
	private String[][] upMatrixbyAll = null;
	
	private String[] resultArrbyCol = null;
	private String[] resultArrbyAll = null;
	
	private int[] exchange = null;

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
	
	private void setTempMatrix(){
		this.tempMatrix = copyArray(this.originalMatrix);
	}
	
	private void setTempArr(){
		tempArr = new double[matrixOrder];
		System.arraycopy(constantArr, 0, tempArr, 0, constantArr.length);
	}
	
	//通过列主元得到变换后的上三角矩阵
	private void setUpDeltabyCol(){
		setTempMatrix();
		setTempArr();
		//其中i用于开始计算的行数,j为矩阵行数,k为矩阵列数
	    int rowMax = 0;//记录占据最大主元的行数
	    double rowMaxValue = 0;//记录最大的值并比较
	    double temp;
	    for(int i=0;i<matrixOrder-1;i++){
	    	//寻找最大值
	    	rowMax = i;
	    	rowMaxValue = tempMatrix[i][i];
	    	
	    	for(int j=i;j<matrixOrder;j++){
	    		if(tempMatrix[j][i]>rowMaxValue){
	    			rowMax = j;
	    			rowMaxValue = tempMatrix[j][i];
	    		}
	    	}
	    	//交换最大值所在行与正在处理的首行
	    	if(rowMax!=i){
	    		//换行
	    		for(int k=i;k<matrixOrder;k++){
		            temp = tempMatrix[i][k];
		            tempMatrix[i][k] = tempMatrix[rowMax][k];
		            tempMatrix[rowMax][k] = temp;
	    		}
	    		//换常数项
	    		temp = tempArr[i];
	    		tempArr[i] = tempArr[rowMax];
	    		tempArr[rowMax] = temp; 
	    	}
	    	//三角形变换
	    	for(int j=i+1;j<matrixOrder;j++){
	            if(tempMatrix[i][i]==0){
	            	tempMatrix =  null;
	            	return;
	            }
	            temp = tempMatrix[j][i] / tempMatrix[i][i];
	            tempArr[j] = tempArr[j] - temp*tempArr[i];
	            for(int k=i;k<matrixOrder;k++){
	            	tempMatrix[j][k] = tempMatrix[j][k] -  temp*tempMatrix[i][k];
	            }
	    	}
	    }
	    upMatrixbyCol =  DecimalChanger(tempMatrix);
	}
	
	//通过全主元得到变换后的上三角矩阵
	private void setUpDeltabyAll(){
		setTempMatrix();
		setTempArr();
		//其中i用于开始计算的行数,j为矩阵行数,k为矩阵列数
	    int rowMax = 0;//记录占据最大主元的行数
	    int colMax = 0;//记录占据最大主元的列数
	    double allMaxValue = 0;//记录最大的值并比较
	    double temp;
	    exchange = new int[matrixOrder-1];
	    for(int i=0;i<matrixOrder-1;i++){
	    	//寻找最大值
	    	rowMax = i;
	    	colMax = i;
	    	allMaxValue = Math.abs(tempMatrix[i][i]);
	    	
	    	for(int j=i;j<matrixOrder;j++){
	    		for(int k=i;k<matrixOrder;k++){
		    		if(Math.abs(tempMatrix[j][k])>allMaxValue){
		    			rowMax = j;
		    			colMax = k;
		    			allMaxValue = Math.abs(tempMatrix[j][k]);
		    		}
	    		}
	    	}

			//记录交换的列
			exchange[i] = colMax;
            if(rowMax!=i&&colMax!=i){
		    	//交换最大值所在行、列与正在处理的首个所占首行首列
		        //换行
		    	for(int k=0;k<matrixOrder;k++){
		    		temp = tempMatrix[i][k];
		    		tempMatrix[i][k] = tempMatrix[rowMax][k];
		    		tempMatrix[rowMax][k] = temp;
		    	}
		        
		        //换列
		    	for(int j=0;j<matrixOrder;j++){
		    		temp = tempMatrix[j][i];
		    		tempMatrix[j][i] = tempMatrix[j][colMax];
		    		tempMatrix[j][colMax] = temp;
		    	}
		    	//换常数项
	    		temp = tempArr[i];
	    		tempArr[i] = tempArr[rowMax];
	    		tempArr[rowMax] = temp; 
            }
            
	    	//三角形变换
	    	for(int j=i+1;j<matrixOrder;j++){
	            if(tempMatrix[i][i]==0){
	            	tempMatrix =  null;
	            	return;
	            }
	            temp = tempMatrix[j][i] / tempMatrix[i][i];
	            tempArr[j] = tempArr[j] - temp*tempArr[i];
	            for(int k=i;k<matrixOrder;k++){
	            	tempMatrix[j][k] = tempMatrix[j][k] -  temp*tempMatrix[i][k];
	            }
	    	}
	    }
	    upMatrixbyAll =  DecimalChanger(tempMatrix);
	}
	
	//通过列主元求得线性方程组的解
	private void setResultArrbyCol(){
		setUpDeltabyCol();
		
		if(tempMatrix==null){
			resultArrbyCol = null;
			return;
		}
		
		double left = 0;
		double[] tempArrbyCol = new double[matrixOrder];
		
		for(int i=0;i<matrixOrder;i++){
			left = tempArr[matrixOrder-1-i];
			for(int j=matrixOrder-i;j<matrixOrder;j++){
				left  = left - tempMatrix[matrixOrder-1-i][j]*tempArrbyCol[j];
			}
			if(tempMatrix[matrixOrder-1-i][matrixOrder-1-i]==0){
				resultArrbyCol = null;
				return;
			}
			tempArrbyCol[matrixOrder-1-i] = left/tempMatrix[matrixOrder-1-i][matrixOrder-1-i];
		}
		resultArrbyCol = DecimalChanger(tempArrbyCol);
	}
	
	//通过全主元求得线性方程组的解
	private void setResultArrbyAll(){
		setUpDeltabyAll();
		
		if(tempMatrix==null){
			resultArrbyAll = null;
			return;
		}
		
		double left = 0;
		double temp = 0;
		double[] tempArrbyAll = new double[matrixOrder];
		
		for(int i=0;i<matrixOrder;i++){
			left = tempArr[matrixOrder-1-i];
			for(int j=matrixOrder-i;j<matrixOrder;j++){
				left  = left - tempMatrix[matrixOrder-1-i][j]*tempArrbyAll[j];
			}
			if(tempMatrix[matrixOrder-1-i][matrixOrder-1-i]==0){
				resultArrbyCol = null;
				return;
			}
			tempArrbyAll[matrixOrder-1-i] = left/tempMatrix[matrixOrder-1-i][matrixOrder-1-i];
		}
		//从后往前进行交换，还原列
		for(int i=matrixOrder-2;i>=0;i--){
			temp = tempArrbyAll[i];
			tempArrbyAll[i] = tempArrbyAll[exchange[i]];
			tempArrbyAll[exchange[i]] = temp;
		}
		resultArrbyAll = DecimalChanger(tempArrbyAll);
	}
	
	public String[][] getUpDeltabyCol(){
		setUpDeltabyCol();
		return upMatrixbyCol;
	}
	
	public String[][] getUpDeltabyAll(){
		setUpDeltabyAll();
		return upMatrixbyAll;
	}

	public String[] getResultArrbyCol(){
		setResultArrbyCol();
		return resultArrbyCol;
	}

	public String[] getResultArrbyAll(){
		setResultArrbyAll();
		return resultArrbyAll;
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
	
	private String[] DecimalChanger(double[] matrix){
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
	
	private double[][] copyArray(double[][]matrix){
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

	//测试方法
	public static void main(String[] args){
		SolveLinearEquationsbyPivot slep = new SolveLinearEquationsbyPivot();
		slep.setMatrixOrder(8);
		slep.setDecimalNumber(2);
		
		double[][] originalMatrix = {
			{1, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 1, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0},
			 {0, 0, 0, 0, 0, 0, 0,1}
		};
		
		double[] constantArr = {1,16,34,58,7,56,30,39};
		
		String[][] changedMatrix = new String[8][8];
		String[] resultArr = new String[8];
		
		slep.setOriginalMatrix(originalMatrix);
		slep.setConstantArr(constantArr);
		
		changedMatrix = slep.getUpDeltabyCol();
		resultArr = slep.getResultArrbyCol();
		
		if(resultArr==null){
			System.out.println("矩阵无法进行列主元三角形变换求解");
		}else{
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++){
					System.out.print(changedMatrix[i][j]+", ");
				}
				System.out.println();
			}
			
			for(int i=0;i<8;i++){
				System.out.print(resultArr[i]+", ");
			}
			System.out.println();
		}
		
		System.out.println();
		

		changedMatrix = slep.getUpDeltabyAll();
		resultArr = slep.getResultArrbyAll();
		
		if(resultArr==null){
			System.out.println("矩阵无法进行全主元三角形变换求解");
		}else{
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++){
					System.out.print(changedMatrix[i][j]+", ");
				}
				System.out.println();
			}
			
			for(int i=0;i<8;i++){
				System.out.print(resultArr[i]+", ");
			}
			System.out.println();
		}
		
		System.out.println();
		
	}
}
