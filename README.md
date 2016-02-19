# -NumericalAnalysis
本部分包括一些数值分析的计算源码，语言为java。codes of Numerical Analysis

类包简介：
-----------------------------------------------------------------------------------
A类:线性方程求解
DeltaUpDownChanger  //AA-上下三角形变换
SolveLinearEquationsbyPivot  //AB-列主元及全主元求解线性方程组
DecomposeMatrixbyLU  //AC-矩阵LU分解
SolveLinearEquationsbyLUPrivot  //AD-LU列主元变换求解线性方程组
HouseholderTransform  //AE-Householder变换
DecomposeMatrixbyCholesky  //AF-使用Choesky方法分解对称正定矩阵
DecomposeMatrixbyChasing  //AG-使用追赶方法分解对称正定矩阵
SolveLinearEquationsbyJacobi  //AH-Jacobi迭代求解线性方程组
SolveLinearEquationsbyGaussSeidel  //AI-Gauss-Seidel迭代求解线性方程组
SolveLinearEquationsbySOR  //AJ-超松弛迭代求解线性方程组
SolveLinearEquationsbySSOR  //AK-对称超松弛迭代求解线性方程组
SolveLinearEquationsbyConjugateGradient  //AL-共轭斜量法迭代求解线性方程组
SolveLinearEquationsbySteepestDescent  //AM-最速下降法迭代求解线性方程组
-----------------------------------------------------------------------------------
B类:函数插值
LagrangeInterpolation  //BA-拉格朗日插值
NewtonInterpolation  //BB-牛顿插值
缺少拉格朗日Hermite插值  //BC-拉格朗日Hermite插值
缺少牛顿牛顿Hermite插值  //BD-牛顿Hermite插值
缺少分段三次Hermite插值  //BE-分段三次Hermite插值
缺少样条插值  //BF-样条插值
-----------------------------------------------------------------------------------
C类：数值积分
IntegratebyComposite  //CA-复化求积公式（需写入方程）
IntegratebyVariableStepComposite  //CB-变步长复化求积公式（需写入方程）
IntegratebyRomberg  //CC-龙贝格外推（需写入方程）
缺少数值微分 //CD-数值微分（需写入方程）
-----------------------------------------------------------------------------------
D类：函数逼近
CurveFittingInLeastSquareMethodOfDiscreteData  //DA-离散数据的最小二乘曲线拟合
缺少Levenberg-Marquardt //DB-非线性的最小二乘曲线拟合（需写入方程?）
-----------------------------------------------------------------------------------
E类：非线性求解
SolveNonLinearEquationbyDichotomie  //EA-二分法求解方程（需写入方程）
缺少牛顿迭代法（需要数值微分）  //EB-牛顿迭代法（简化牛顿法，割线法，牛顿下山法）（需写入方程）
缺少高次代数方程求根的嵌套算法  //EC-高次代数方程求根的嵌套算法（需写入方程）
缺少非线性方程组的简单迭代法  //ED-非线性方程组的简单迭代法（需写入方程）
缺少非线性方程组的Newton-Raphson方法  //EE-非线性方程组的Newton-Raphson方法（需写入方程）
缺少非线性方程组的同伦算法  //EF-非线性方程组的同伦算法（需写入方程）
-----------------------------------------------------------------------------------
F类：常微分方程初边值问题的数值解法
未完成Euler算法  //FA-Euler算法（需写入方程）
未完成梯形公式算法  //FB-梯形公式算法（需写入方程）
未完成改进的Euler公式算法  //FC-改进的Euler公式算法（需写入方程）
未完成二阶Rungue-Kutta公式算法  //FD-二阶Rungue-Kutta公式算法（需写入方程）
未完成Adams方法算法  //FE-Adams方法算法（需写入方程）
-----------------------------------------------------------------------------------
G类：代数特征值求取
缺少Givens变换  //GA-Givens变换
缺少QR分解  //GB-QR分解
