package vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class text {
    public static void main(String[] args) {
        List<Integer> a=new ArrayList<>();
        List<Integer> b=new ArrayList<>();
        int  n,m;
        Scanner scanner=new Scanner(System.in);
        String [] splits = scanner.nextLine().split(" ");
        n = Integer.parseInt(splits[0]);
        m = Integer.parseInt(splits[1]);
        boolean flag = false;
        for(int j = 0 ; j < 2 ; j++){
            String [] split = scanner.nextLine().split(" ");
            int [] nums = new int[split.length];
            for(int i = 0 ; i < nums.length ; i++){
                nums[i] = Integer.parseInt(split[i]);
            }
            if(! flag){
                for(int num : nums){
                    a.add(0,num);
                }
            }else{
                for(int num : nums){
                    b.add(0,num);
                }
            }
            flag = !flag;
        }
        if (n>=m){
            for (int i = 0; i < n-m; i++) {
                b.add(0);
            }
            int r=0;
            for (int i = 0; i < n; i++) {
                int e=i+2;
                if (r==0) {
                    r = (a.get(i) + b.get(i)) /e;
                    a.set(i, (a.get(i) + b.get(i)) % e);
                }else {
                    r=(a.get(i) + b.get(i)+1) / e;
                    int f=(a.get(i) + b.get(i) + 1) % e;
                    a.set(i, f);
                }
            }
        }
        if (n<m){
            for (int i = 0; i < m-n; i++) {
                a.add(0);
            }
            int r=0;
            for (int i = 0; i < n; i++) {
                int e=i+2;
                if (r==0) {
                    r = (a.get(i) + b.get(i)) /e;
                    a.set(i, (a.get(i) + b.get(i)) % e);
                }else {
                    r=(a.get(i) + b.get(i)+1) / e;
                    int f=(a.get(i) + b.get(i) + 1) % e;
                    a.set(i, f);
                }
            }
        }
        for (int i = n-1; i >= 0; i--) {
            System.out.print(a.get(i)+" ");
        }
    }
}
