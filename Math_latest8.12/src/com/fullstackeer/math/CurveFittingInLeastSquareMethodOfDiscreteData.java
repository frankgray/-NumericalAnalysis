package com.fullstackeer.math;

/**
 *离散数据的最小二乘曲线拟合
 */
public class CurveFittingInLeastSquareMethodOfDiscreteData {
	/**
	 * 离散点的坐标集合，其中originalPoints[0]记录x坐标
	 * ，originalPoints[1]记录y坐标
	 */
	private double[][] originalPoints = null;
	/**
	 * 拟合曲线阶数
	 */
	private int orderValue = 0;
	/**
	 * 拟合曲线上的等间距点数值集合
	 */
	private double[][] doubleResultPoints = null;
	/**
	 * 拟合曲线上的等间距点字符串集合
	 */
	private String[][] stringResultPoints = null;
	/**
	 * 拟合曲线的点个数
	 */
	private int resultPointsNumber = 0;
	/**
	 * 拟合曲线取点的最小值
	 */
	private double xMin = 0;
	/**
	 * 拟合曲线取点的最大值
	 */
	private double xMax = 0;
	/**
	 * 设置变成字符串时，小数点后的位数
	 */
	private int decimalNumber = 0;
	/**
	 * 设置离散点
	 * @param originalPoints 离散点数组
	 */
	public void setOriginalPoints(double[][] originalPoints){
		this.originalPoints = originalPoints;
	}
	/**
	 * 设置拟合曲线阶数
	 * @param orderValue 拟合曲线阶数
	 */
	public void setOrderValue(int orderValue){
		this.orderValue = orderValue;
	}
	/**
	 * 设置拟合曲线取点的个数
	 * @param resultPointsNumber 拟合曲线取点个数
	 */
	public void setResultPointsNumber(int resultPointsNumber){
		this.resultPointsNumber = resultPointsNumber;
	}
	/**
	 * 设置拟合曲线取点区间的最小值
	 * @param xMax 拟合曲线取点区间的最小值
	 */
	public void setXMax(double xMax){
		this.xMax = xMax;
	}
	/**
	 * 设置拟合曲线取点区间的最大值
	 * @param xMax 拟合曲线取点区间的最大值
	 */	
	public void setXMin(double xMin){
		this.xMin = xMin;
	}
	/**
	 * 设置数字字符串的小数点位数
	 * @param decimalNumber 数字字符串的小数点位数
	 */
	public void setDecemialNumber(int decimalNumber){
		this.decimalNumber = decimalNumber;
	}
	/**
	 * 得到拟合曲线点的数值集合
	 * @return 拟合曲线点集合
	 */
	public double[][] getDoubleResultPointsNumber(){
		return doubleResultPoints;
	}
	/**
	 * 得到拟合曲线点的字符串集合
	 * @return 拟合曲线点集合
	 */
	public String[][] getStringResultPointsNumber(){
		return stringResultPoints;
	}
	/**
	 * 进行最小二乘曲线拟合
	 */
	public void curveFittingInLeastSquareMethodOfDiscreteData(){
		int originalPointsNumber = originalPoints.length;
		double[][] G = new double[orderValue+1][orderValue+1];//对称矩阵，需优化
		double[][] GCholesky = new double[orderValue+1][orderValue+1];
		double[] F = new double[orderValue+1];
		double[] C = new double[orderValue+1];
		
		for(int i=0;i<orderValue+1;i++){
			for(int j=0;j<orderValue+1;j++){
				G[i][j] = 0;
				
				for(int k=0;k<originalPointsNumber;k++){
					G[i][j] += Math.pow(originalPoints[k][0], i+j);
				}
			}
		}
		
		for(int i=0;i<orderValue+1;i++){
			F[i]=0;
			for(int j=0;j<originalPointsNumber;j++){
				F[i]+=Math.pow(originalPoints[j][0], i)*originalPoints[j][1];
			}
		}
		//得到Cholesky分解后的下三角形矩阵
		DecomposeMatrixbyCholesky slec = new DecomposeMatrixbyCholesky();
		slec.setMatrixOrder(orderValue+1);
		slec.setOriginalMatrix(G);
		slec.solveLinearEquationsbyCholesky();
		GCholesky = slec.getDoubleResultMatrix();
		
		if(GCholesky==null){
			return;
		}
		
		for(int i=0;i<orderValue+1;i++){
			for(int j=0;j<i;j++){
				F[i] -= GCholesky[i][j]*C[j];
			}
			
			C[i] = F[i]/GCholesky[i][i];
		}
		
		for(int i=orderValue;i>=0;i--){
			for(int j=i+1;j<orderValue+1;j++){
				C[i]= C[i] - GCholesky[j][i]*C[j];
			}
			C[i] /= GCholesky[i][i];
		}

		double xStepLength = (xMax-xMin)/(resultPointsNumber-1);
		doubleResultPoints = new double[resultPointsNumber][2];
		
		for(int i=0;i<resultPointsNumber;i++){
			doubleResultPoints[i][0]=xMin+xStepLength*i;
			doubleResultPoints[i][1] = 0;
			for(int j=0;j<orderValue+1;j++){
				doubleResultPoints[i][1] += C[j]*Math.pow(doubleResultPoints[i][0], j);
			}
			
			if(Double.isNaN(doubleResultPoints[i][1])){
				doubleResultPoints = null;
				stringResultPoints = null;
				return;
			}
		}
		
		stringResultPoints = decimalChanger(doubleResultPoints);
	}
	/**
	 * 保留小数点后位数
	 * @param matrix 数值矩阵
	 * @return 字符串矩阵
	 */
	private String[][] decimalChanger(double[][] matrix){
		String[][] stringMatrix = new String[resultPointsNumber][2];
		
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
		
		for(int i=0;i<resultPointsNumber;i++){
			for(int j=0;j<2;j++){
				if(String.valueOf(matrix[i][j]).equals("0.0")){
					stringMatrix[i][j] = "0";
				}else{
					stringMatrix[i][j] = df.format(matrix[i][j]);
				}
			}
		}
		return stringMatrix;
	}
	
	/**
	 * 离散点的最小二乘曲线拟合的测试方法
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		double[][] originalPoints =  
				{
				{0,0.25,0.5,0.75,1},
				{1,1.284,1.6487,2.117,2.7183}
				};
		
		double[][] originalPoints = 
			{
				{97.397,73.738,97.194,98.948,43.738},
				{921.175,871.231,549.777,667.979,627.808}
			};
		*/
		double[][] originalPoints = 
			{
				{97.397,921.175},
				{73.738,871.231},
				{97.194,549.777},
				{98.948,667.979},
				{43.738,627.808}
			};		
		
		int decimalNumber = 3;
		double xMin = 0;
		double xMax = 1;
		int orderValue = 3;
		int resultPointsNumber = 101;
		
		CurveFittingInLeastSquareMethodOfDiscreteData cfilsmod = new CurveFittingInLeastSquareMethodOfDiscreteData();
		cfilsmod.setDecemialNumber(decimalNumber);
		cfilsmod.setOrderValue(orderValue);
		cfilsmod.setOriginalPoints(originalPoints);
		cfilsmod.setResultPointsNumber(resultPointsNumber);
		cfilsmod.setXMax(xMax);
		cfilsmod.setXMin(xMin);
		
		cfilsmod.curveFittingInLeastSquareMethodOfDiscreteData();
		
		String[][] stringResultPoints = cfilsmod.getStringResultPointsNumber();
		double[][] doubleResultPoints = cfilsmod.getDoubleResultPointsNumber();
		
		if(stringResultPoints==null){
			System.out.println("Error:由于设置的阶数过高或其他原因，无法的得到拟合后的曲线");
		}else{
			for(int i=0;i<stringResultPoints.length;i++){
				for(int j=0;j<stringResultPoints[i].length;j++){
					System.out.print(stringResultPoints[i][j]+",");
				}
				System.out.println();
			}
		}
		
		if(doubleResultPoints==null){
			System.out.println("Error:由于设置的阶数过高或其他原因，无法的得到拟合后的曲线");
		}else{
			for(int i=0;i<doubleResultPoints.length;i++){
				for(int j=0;j<doubleResultPoints[i].length;j++){
					System.out.print(doubleResultPoints[i][j]+",");
				}
				System.out.println();
			}
		}
	}

}
