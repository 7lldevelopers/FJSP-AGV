package vo;

import vo.runAGV.Addtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
//[[8, 7], [16, 18], [1, 19], [0, 2], [17, 20], [16, 0], [14, 4], [7, 14], [36, 14], [0, 5], [16, 17], [36, 7], [7, 23], [7, 11], [4, 22], [19, 20], [0, 0], [0, 22], [0, 11], [0, 3], [24, 12], [32, 15], [16, 3], [0, 7], [24, 2], [38, 12], [8, 9], [33, 19], [12, 6], [8, 10], [8, 8], [26, 4], [34, 15], [8, 12], [1, 20], [14, 0], [0, 17], [16, 15], [24, 0], [0, 15]]


//[[1, 5], [1, 5], [1, 7], [1, 5], [2, 0], [2, 3], [2, 5], [1, 6], [4, 3], [2, 5], [1, 3], [4, 3], [1, 7], [1, 7], [1, 6], [2, 6], [2, 6], [2, 1], [4, 6], [2, 6], [2, 6], [2, 0], [1, 1], [4, 7], [2, 7], [2, 0], [1, 6], [4, 6], [1, 6], [1, 6], [1, 6], [2, 6], [2, 0], [2, 7], [2, 7], [2, 0], [2, 7], [2, 7], [2, 7], [2, 7]]
//[16, 3, 19, 9, 23, 18, 39, 36, 17, 2, 34, 14, 13, 7, 12, 0, 30, 26, 29, 33, 28, 35, 6, 5, 22, 37, 10, 1, 4, 15, 38, 24, 20, 31, 21, 27, 32, 11, 8, 25]
public class text {
    public static void main(String[] args) {
        int[][] tempxy;
        int[][] time=new int[40][24];
        tempxy=new int[][]
                {
                        {1, 5}, {1, 5}, {1, 7}, {1, 5}, {2, 0}, {2, 3}, {2, 5}, {1, 6}, {4, 3}, {2, 5}, {1, 3}, {4, 3}, {1, 7}, {1, 7}, {1, 6}, {2, 6}, {2, 6}, {2, 1}, {4, 6}, {2, 6}, {2, 6}, {2, 0}, {1, 1}, {4, 7}, {2, 7}, {2, 0}, {1, 6}, {4, 6}, {1, 6}, {1, 6}, {1, 6}, {2, 6}, {2, 0}, {2, 7}, {2, 7}, {2, 0}, {2, 7}, {2, 7}, {2, 7}, {2, 7}
                };
        int[][] temp=new int[][]{
                {8, 7}, {16, 18}, {1, 19}, {0, 2}, {17, 20}, {16, 0}, {14, 4}, {7, 14}, {36, 14}, {0, 5}, {16, 17}, {36, 7}, {7, 23}, {7, 11}, {4, 22}, {19, 20}, {0, 0}, {0, 22}, {0, 11}, {0, 3}, {24, 12}, {32, 15}, {16, 3}, {0, 7}, {24, 2}, {38, 12}, {8, 9}, {33, 19}, {12, 6}, {8, 10}, {8, 8}, {26, 4}, {34, 15}, {8, 12}, {1, 20}, {14, 0}, {0, 17}, {16, 15}, {24, 0}, {0, 15}
        };
        int[] a1={16, 3, 19, 9, 23, 18, 39, 36, 17, 2, 34, 14, 13, 7, 12, 0, 30, 26, 29, 33, 28, 35, 6, 5, 22, 37, 10, 1, 4, 15, 38, 24, 20, 31, 21, 27, 32, 11, 8, 25};
        List<Addtime> aaw=new ArrayList<>();
        for (int i = 0; i < 40; i++) {
                     aaw.add(new Addtime(tempxy[i][0],tempxy[i][1],temp[i][0],temp[i][1]));
        }
        //标志位
//        Addtime biaozhi=new Addtime(0,0,0,0);
        List<Integer> biao1=new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            //
            int asd=aaw.get(a1[i]).getTi1()*( aaw.get(a1[i]).getCri1()+1);
            for (int j = 0; j < aaw.get(a1[i]).getTi1()*( aaw.get(a1[i]).getCri1()+1); j++) {
                for (int k = 0; k < aaw.get(a1[i]).getFr1(); k++) {
                    switch (aaw.get(a1[i]).getFr1()){
                        case 1:{
                            if (asd%4==0){
                                time[aaw.get(a1[i]).getX()+j][aaw.get(a1[i]).getY()+k]=asd/4;
                            }else {
                                time[aaw.get(a1[i]).getX()+j][aaw.get(a1[i]).getY()+k]=asd/4+1;
                            }

                            break;
                        }
                        case 2:{
                            if (asd%2==0){
                                time[aaw.get(a1[i]).getX()+j][aaw.get(a1[i]).getY()+k]= asd/2;
                            }else {
                                time[aaw.get(a1[i]).getX()+j][aaw.get(a1[i]).getY()+k]= (asd+1)/2;
                            }

                            break;
                        }
                        case 4:{
                                time[aaw.get(a1[i]).getX()+j][aaw.get(a1[i]).getY()+k]=asd;
                            break;
                        }
                    }
                }
                asd--;
            }
        }
        //放的位置
        int[][] time1=new int[40][24];
        for (int i = 0; i < 40; i++) {

            for (int j = 0; j < aaw.get(a1[i]).getTi1()*( aaw.get(a1[i]).getCri1()+1); j++) {
                for (int k = 0; k < aaw.get(a1[i]).getFr1(); k++) {
                    time1[aaw.get(a1[i]).getX()+j][aaw.get(a1[i]).getY()+k]= 1;
                }}}

        //加入空白快
        List<Addtime> kongbai=new ArrayList<>();
        //遍历顺序
        int[][] tempy=new int[40][24];;//遍历Y轴 X

        int k=0,j=0,v=0;


            while (k<40){
                while (j<24) {

                    if (tempy[k][j]==0){
                        //预留空白快
                        if (v<=39){
                            if (aaw.get(a1[v]).getX()==k&&aaw.get(a1[v]).getY()==j){
                                for (int m = 0; m < aaw.get(a1[v]).getTi1()*( aaw.get(a1[v]).getCri1()+1); m++) {
                                    for (int n = 0; n < aaw.get(a1[v]).getFr1(); n++) {
                                        tempy[k+m][j+n]= 1;
                                    }
                                }
                                v=v+1;

                            }else {
                                kongbai.add(new Addtime(k,j));
                            }
                        //覆盖空白快
                        if (k>=1){
                            //一次判断前面能否放置
                            for (int i = v-2; i < v-1; i++) {
                                //高度小于
                                if (time[aaw.get(a1[i]).getX()][aaw.get(a1[i]).getY()]>time[aaw.get(a1[v-1]).getX()][aaw.get(a1[v-1]).getY()]){
                                    //Y小于
                                    if ( aaw.get(a1[i]).getFr1()>= aaw.get(a1[v-1]).getFr1()){
                                        //表示能放置
                                        for (int l = aaw.get(a1[i]).getX(); l <  aaw.get(a1[i]).getX()+aaw.get(a1[i]).getTi1()*( aaw.get(a1[i]).getCri1()+1); l++) {
                                            //y轴
                                            for (int m =  aaw.get(a1[i]).getY(); m <aaw.get(a1[i]).getY()+aaw.get(a1[i]).getFr1() ; m++) {
                                                //右下角的长度小于覆盖物体的最大长度  不要滑出右边边界线
                                                if (l+aaw.get(a1[v-1]).getTi1()*( aaw.get(a1[v-1]).getCri1()+1)<=aaw.get(a1[i]).getX()+aaw.get(a1[i]).getTi1()*( aaw.get(a1[i]).getCri1()+1)){
                                                    //不要滑出上面边界线
                                                    if (m+aaw.get(a1[v-1]).getFr1()<=aaw.get(a1[i]).getY()+aaw.get(a1[i]).getFr1()){

                                                        if (time[l][m]+time[aaw.get(a1[v-1]).getX()][aaw.get(a1[v-1]).getY()]<=time[aaw.get(a1[i]).getX()][aaw.get(a1[i]).getY()]&&time[l+aaw.get(a1[v-1]).getTi1()*( aaw.get(a1[v-1]).getCri1()+1)-1][m]+time[aaw.get(a1[v-1]).getX()+aaw.get(a1[v-1]).getTi1()*( aaw.get(a1[v-1]).getCri1()+1)-1][aaw.get(a1[v-1]).getY()]<=time[aaw.get(a1[i]).getX()][aaw.get(a1[i]).getY()]){

                                                            kongbai.add(new Addtime(l,m));

                                                        }


                                                    }

                                                }

                                            }
                                        }


                                    }

                                }
                                //X轴

                            }
                        }



                        }
                    }
                    j=j+1;

                }
                k=k+1;
                j=0;
            }



//        for (int i = 23; i >= 0; i--) {
//            for (int ju = 0; ju < 40; j++) {
//                System.out.print(tempy[ju][i]+"-");
//            }
//            System.out.println(" ");
//        }
        for (int i = 0; i <  kongbai.size(); i++) {

            System.out.println(kongbai.get(i).getX()+","+kongbai.get(i).getY());

        }





    }
}
