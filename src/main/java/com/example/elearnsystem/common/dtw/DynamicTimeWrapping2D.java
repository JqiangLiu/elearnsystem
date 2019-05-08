package com.example.elearnsystem.common.dtw;

import java.util.HashMap;
import java.util.Map;

public class DynamicTimeWrapping2D extends DynamicTimeWrapping{
    private double[][] test;
    private double[][] reference;
    // variance for each feature dimension
    private double[] variance;
    private Map<String,Double> DTW; // 稀疏矩阵进行压缩

    public DynamicTimeWrapping2D(double[][] reference, double[][] test)
    {
        this.test = test;
        this.reference = reference;
        // by default, we initialize variance as 1 for each dimension.
        variance = new double[test[0].length];
        for (int i = 0; i < variance.length; i++)
            variance[i] = 1;
    }

    public DynamicTimeWrapping2D(double[][] reference, double[][] test, double[] variance)
    {
        this.test = test;
        this.reference = reference;
        this.variance = variance;
    }

    @Override
    public double calDistance() {

        int n = test.length;
        int m = reference.length;

        // DP for calculating the minimum distance between two vector.
        // DTW[i,j] = minimum distance between vector test[0..i] and reference[0..j]
//        double[][] DTW = new double[n][m];
        DTW = new HashMap<>();

        // initialization
//        for(int i = 0; i < n; i++)
//            for(int j = 0; j < m; j++)
//                DTW[i][j] = Double.MAX_VALUE;

        // initialize base case
//        DTW[0][0] =  getDistance(test[0],reference[0]);
        putDTW(0,0,getDistance(test[0],reference[0]));


        // initialize boundary condition.
        for (int i = 1; i < n; i++){
            //   DTW[i][0] = DTW[i-1][0] + getDistance(test[i],reference[0]);
                double lastRes = getDTW(i-1,0);
            putDTW(i,0,lastRes + getDistance(test[i],reference[0]));
        }


        for(int i = 1; i < m; i++){
//            DTW[0][i] = DTW[0][i-1] + getDistance(test[0],reference[i]);
            double lastRes = getDTW(0,i-1);
            putDTW(0,i,lastRes + getDistance(test[0],reference[i]));
        }


        // DP comes here...
        for (int i = 1; i < n; i++)
        {
            int z = Math.min(m-globalPathConstraint,i*2-headConstraint);
            for (int j = i-headConstraint<=0?1:z; j < Math.min(m, z+globalPathConstraint); j++)
            {   // consider five different moves.
                double cost = getDistance(test[i],reference[j]);
//                double d1 = cost + DTW[i-1][j];
                double d1 = cost + getDTW(i-1,j);
//                double d2 = cost + DTW[i][j-1];
                double d2 = cost + getDTW(i,j-1);
//                double d3 = 2 * cost + DTW[i-1][j-1];
                double d3 = 2  * cost + getDTW(i-1,j-1);
                double d4 = Double.MAX_VALUE;
                if (j > 1)
//                    d4 = 3 * cost + DTW[i-1][j-2];
                    d4 = 3 * cost + getDTW(i-1,j-2);
                double d5 = Double.MAX_VALUE;
                if (i > 1)
//                    d5 = 3 * cost + DTW[i-2][j-1];
                    d5 = 3 * cost + getDTW(i-2,j-1);
//
//                DTW[i][j] = getMin(d1,d2,d3,d4,d5);
                putDTW(i,j,getMin(d1,d2,d3,d4,d5));
            }
        }

//        return DTW[n-1][m-1] /(m+n);
        return Math.round(100-(getDTW(n-1,m-1)*40 /(m+n)));
    }

    /**
     * Calculate the distance between two feature vectors. The two feature
     * vector have the same dimension. When calculating such Euclidean distance,
     * we need to take variance into account.
     *
     * @param vec1        测试数据
     * @param vec2        模板数据
     * @param variance    variance for each dimension.
     * @return            Normalized Euclidean distance (or in manhatan distance,
     *                    the covariance matrix is diagonal matrix, where each diagonal
     *                    entry is the variance for that dimension)
     */
    private double getDistance(double[] vec1, double[] vec2)
    {
        int valid = 0;
        double distance = 0.0;
        for(int i = 0; i < vec1.length; i++) {
            if (Math.max(Math.abs(vec1[i]),Math.abs(vec2[i])) == 0){
                valid++;
                continue;
            }
            distance += (Math.abs(Math.abs(vec1[i])-Math.abs(vec2[i])) / variance[i])/Math.max(Math.abs(vec1[i]),Math.abs(vec2[i]));
        }
        return distance*100/12;
    }

    // 添加矩阵元素
    public void putDTW(int x, int y, double v){
            DTW.put(SparseIndex(x,y),v);
    }

    // 获取矩阵元素
    public Double getDTW(int x, int y){
        Double v = DTW.get(SparseIndex(x,y));
        if(v == null) {
            v = Double.MAX_VALUE;
        }
        return v;
    }

    // 把矩阵索引转换成字符串，用于哈希索引
    static public String SparseIndex(int x, int y){
        return String.valueOf(x)+","+String.valueOf(y);
    }

}
