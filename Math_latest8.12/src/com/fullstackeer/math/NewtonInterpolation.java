package com.fullstackeer.math;
//BB-牛顿插值
public class NewtonInterpolation {
	
	private int decimalNumber = 0;
	private int nodesNumber = 0;
	private double[][] originalNodesPoints = null;
	
	private int resultPointsNumber = 0;
	private double minX = 0;
	private double maxX = 0;
	
	private double[][] tempResultPoints = null;
	private String[][] resultPoints = null;
	
	public void setDecimalNumber(int decimalNumber){
		this.decimalNumber = decimalNumber;
	}
	
	public void setNodesNumber(int nodesNumber){
		this.nodesNumber = nodesNumber;
	}
	
	public void setOriginalNodesPoints(double[][] originalNodesPoints){
		this.originalNodesPoints = originalNodesPoints;
	}
	
	public void setResultPointsNumber(int resultPointsNumber){
		this.resultPointsNumber = resultPointsNumber;
	}
	
	public void setMinX(double minX){
		this.minX = minX;
	}
	
	public void setMaxX(double maxX){
		this.maxX = maxX;
	}
	
	private void setResultPoints(String[][] resultPoints){
		this.resultPoints = resultPoints;
	}
	
	public String[][] getResultPoints(){
		return this.resultPoints;
	}
	
	public void newtonInterpolation(){
		double newtonFactor = 1;
		double[] dNewtonFactor = new double[nodesNumber];
		double c = 0;
		tempResultPoints = new double[resultPointsNumber][2];
		
		for(int count=0;count<nodesNumber;count++){
			if(count == 0){
				for(int i=0;i<resultPointsNumber;i++){
					this.tempResultPoints[i][0] = minX + i*(maxX - minX)/(resultPointsNumber-1);
					this.tempResultPoints[i][1] = originalNodesPoints[0][1];
				}
				continue;
			}
			
			for(int i=0;i<resultPointsNumber;i++){
				newtonFactor = 1;
				for(int j=0;j<count;j++){
					newtonFactor = newtonFactor*(tempResultPoints[i][0]-originalNodesPoints[j][0]);
				}
				
				for(int j=0;j<count+1;j++){
					dNewtonFactor[j] = 1;
					for(int k=0;k<count+1;k++){
						if(j!=k) 
							dNewtonFactor[j]=dNewtonFactor[j]*(originalNodesPoints[j][0]-originalNodesPoints[k][0]);
					}
				}
				
				c = 0;
				for(int j=0;j<count+1;j++){
					c += originalNodesPoints[j][1]/dNewtonFactor[j];
				}
				
				tempResultPoints[i][1] += c*newtonFactor;
			}
		}
		
		this.setResultPoints(this.decimalChanger(tempResultPoints));
	}
	
	//保留小数点后位数
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
		//若要不四舍五，加上此句即可
		df.setRoundingMode(java.math.RoundingMode.FLOOR);
		
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
	
	public static void main(String[] args){
		int decimalNumber = 3;
		double maxX = 1;
		double minX = 0;
		double[][] originalNodesPoints = {
				{0.01,	-4.605170186},
				{0.45,	-0.798507696},
				{0.55,	-0.597837001},
				{0.65,	-0.430782916},
				{0.75,	-0.287682072},
				{0.85,	-0.162518929}
		};
		int nodesNumber = 6;
		int resultPointsNumber = 101;
		
		NewtonInterpolation ni = new NewtonInterpolation();
		ni.setDecimalNumber(decimalNumber);
		ni.setMaxX(maxX);
		ni.setMinX(minX);
		ni.setOriginalNodesPoints(originalNodesPoints);
		ni.setNodesNumber(nodesNumber);
		ni.setResultPointsNumber(resultPointsNumber);
		
		ni.newtonInterpolation();
		
		String[][] resultPoints = ni.getResultPoints();
		
		for(int i=0;i<resultPointsNumber;i++){
			System.out.println((i+1)+":"+resultPoints[i][0]+" "+resultPoints[i][1]);
		}
	}
}
