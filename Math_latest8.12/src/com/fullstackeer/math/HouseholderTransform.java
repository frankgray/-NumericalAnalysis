package com.fullstackeer.math;
//AE-Householder变换
public class HouseholderTransform {
  
  private int arrOrder = 0;
  private int decimalNumber = 0;
  private double[] originalArr = null;
    private int zeroNumber = 0;
    
    private String[][] householder = null;
    private String[] transformedArr = null;
    
    //μk[]为构造向量
    //ρk为μk长度的平方
    //σk为原始arr的平方和
    //maxOriginalMatrix用于标准化
    private double[] μk = null;
    private double σk = 0;
    private double ρk = 0;
    private double maxValueofArr = 0;
    
    private double[] tempArr = null;
    private double[][] tempHouseholder = null;
    
    public void setArrOrder(int arrOrder){
      this.arrOrder = arrOrder;
    }
    
    public void setDecimalNumber(int decimalNumber){
      this.decimalNumber = decimalNumber;
    }
    
    public void setOriginalArr(double[] originalArr){
      this.originalArr = originalArr;
    }
    
    public void setZeroNumber(int zeroNumber){
      this.zeroNumber = zeroNumber;
    }
    
    public String[][] getHouseholder(){
      return householder;
    }
    
    public String[] getTransformedArr(){
      return this.transformedArr;
    }
    
    public void householderTransform(){
      //先复制矩阵
      tempArr =  new double[arrOrder];
      copyArray(tempArr,originalArr);
      
      //对原始矩阵进行标准化
      normalizeOriginalMatrix();
      
      if(tempArr==null){
        householder = null;
        transformedArr = null;
        return;
      }
      
      //进行householder变换
      for(int i=arrOrder-1-zeroNumber;i<arrOrder;i++){
        σk += Math.pow(this.tempArr[i], 2);
      }
      
      double signal = tempArr[arrOrder-1-zeroNumber];
      
      if(signal>0){
        σk = Math.pow(σk, 0.5);
      }else if(signal<0){
        σk = -Math.pow(σk, 0.5);
      }
      
      μk = new double[arrOrder];
      for(int i=arrOrder-zeroNumber;i<arrOrder;i++){
        μk[i] = tempArr[i];
      }
      
      μk[arrOrder-1-zeroNumber] = tempArr[arrOrder-1-zeroNumber] + σk;
      ρk = σk*μk[arrOrder-1-zeroNumber];
      
      tempHouseholder = new double[arrOrder][arrOrder];
      for(int i=0;i<arrOrder;i++){
        for(int j=0;j<arrOrder;j++){
          if(i==j) {
            if(ρk==0) {
                householder = null;
                transformedArr = null;
                return;
              }
            tempHouseholder[i][j] = 1 - μk[i] * μk[j] / ρk;
          }else{
            tempHouseholder[i][j] = - μk[i] * μk[j] / ρk;
          }
        }
      }
      
      householder = decimalChanger(tempHouseholder);
      
      //得到变换后的矩阵
      for(int i=0;i<arrOrder;i++){
        int number = arrOrder-zeroNumber-1;
        tempArr[i] = tempArr[i] * maxValueofArr;
        if(i>number){
          tempArr[i] = 0;
        }else if(i==number){
          tempArr[i] = -σk * maxValueofArr;
        }
      }
      
      transformedArr = decimalChanger(tempArr);
    }
    
    private void normalizeOriginalMatrix(){
      for(int i=0;i<arrOrder;i++){
        if(Math.abs(tempArr[i])>maxValueofArr){
          maxValueofArr = Math.abs(tempArr[i]);
        }
      }
      
      if(maxValueofArr==0){
        tempArr = null;
        return;
      }
      
      for(int i=0;i<arrOrder;i++){
        tempArr[i] = tempArr[i] / maxValueofArr;
      }
    }
    
    //保留小数点后位数
    private String[][] decimalChanger(double[][] matrix){
      String[][] stringMatrix = new String[arrOrder][arrOrder];
      
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
      
      for(int i=0;i<arrOrder;i++){
        for(int j=0;j<arrOrder;j++){
          if(String.valueOf(matrix[i][j]).equals("0.0")){
          stringMatrix[i][j] = "0";
        }else{
          stringMatrix[i][j] = df.format(matrix[i][j]);
        }
      }
      }
      return stringMatrix;
    }
    
    private String[] decimalChanger(double[] matrix){
      String[] stringMatrix = new String[arrOrder];
      
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
      
      for(int i=0;i<arrOrder;i++){
        if(String.valueOf(matrix[i]).equals("0.0")){
        stringMatrix[i] = "0";
      }else{
        stringMatrix[i] = df.format(matrix[i]);
      }
    }
      return stringMatrix;
    }
    
    //复制的例子
  private void copyArray(double[] copyArr,double[] arr){
    System.arraycopy(arr, 0, copyArr, 0, arr.length); 
  }
  
  //测试方法
  public static void main(String[] args){
    HouseholderTransform ht = new HouseholderTransform();
    ht.setDecimalNumber(2);
    ht.setArrOrder(3);
    
    double[] originalArr = {1.00,0.00,0.00};
    
    ht.setOriginalArr(originalArr);
    ht.setZeroNumber(7);//注意，要保证zeronumber<arrorder
    
    ht.householderTransform();
    
    String[][] householder = ht.getHouseholder();
    String[] transformedArr = ht.getTransformedArr();
    
    if(householder == null){
      System.out.println("该矩阵无法进行Householder变换");
    }else {
      for(int i=0;i<3;i++){
        for(int j=0;j<3;j++){
          System.out.print(householder[i][j]+", ");
        }
        System.out.println();
      }
    }
    
    System.out.println();
    
    if(transformedArr == null){
      System.out.println("该矩阵无法进行Householder变换");
    }else {
      for(int i=0;i<3;i++){
        System.out.print(transformedArr[i]+", ");
      }
    }   
  }
}
