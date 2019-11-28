package com.lance.lockpattern;
import android.app.Application;
import android.util.Log;

public class GlobalVariable extends Application {
    // For all
    private String test_name = "test";
    private int page = 0; // 0, 1, 2, 3
    private int error_counter = 0;
    private int right_counter = 0;
    private int start_time = 0;
    // For page 2
    private int test_time = 0; // 0, 1, 2
    private int test_layout = 0; // 0, 1, 2, 3
    private int test_target = 0; // 0, 1, 2, 3
    private int test_mode = 0;

    // For the test result;
    private final int[][][][]  point_list = new int[10][10][10][2];
    private final int[][] time_list = new int[10][2];
    private final int[][][] time_layout_list = new int[10][10][10];// layout, target, time
    private final int[][] target_list = {{4,1,1,3,2,2,3,1,4,3,2,1,4,1,4,4,3,2,2,3},
                                         {5,6,1,5,1,4,4,3,2,2,3,1,6,4,3,2,5,1,6,1,6,5,6,3,4,2,2,4,3,5},
                                         {4,1,1,3,2,2,3,1,4,3,2,1,4,1,4,4,3,2,2,3},
                                         {4,1,1,3,2,2,3,1,4,3,2,1,4,1,4,4,3,2,2,3},
                                         {5,6,1,5,1,4,4,3,2,2,3,1,6,4,3,2,5,1,6,1,6,5,6,3,4,2,2,4,3,5},
                                         {5,6,1,5,1,4,4,3,2,2,3,1,6,4,3,2,5,1,6,1,6,5,6,3,4,2,2,4,3,5}};

    /**  START TIME **/
    public void setStartTime(int st_time){
        this.start_time = st_time;
    }
    public int getStartTime() {
        return start_time;
    }
    /** SET NAME **/
    public void setName(String s){test_name = s;}
    /**  MODE **/
    public void setMode(int mode_num){
        this.test_mode = mode_num;
    }
    public int getMode() {
        return test_mode;
    }
    /**  PAGE **/
    public void setPage(int page_num){
        this.page = page_num;
    }
    public int getPage() {
        return page;
    }
    /**  COUNTER **/
    public void initCounter() { this.error_counter = 0; this. right_counter = 0;}
    public int getCounter(boolean err) {
        return (err)? error_counter : right_counter;
    }
    public void addCounter(boolean err) {
        if(err) error_counter += 1;
        else right_counter += 1;
    }
    /** TEST TIME**/
    public void setTestTime(int time){
        test_time = time;
    }
    public int getTestTime(){
        return test_time;
    }

    /** TEST LAYOUT*/
    public void setTestLayout(int layout){
        test_layout = layout;
    }
    public int getTestLayout(){
        return test_layout;
    }

    /** TEST TARGET **/
    // for mode 1 only!
    public void setTestTarget(int target){
        test_target = target;
    }
    public int getTestTarget(){ return test_target; }
    public int getTestTargetFix(int layout, int count){ return target_list[layout][count]; }

    /** POINT LIST **/
    public void storePoint(int layout, int target, int count, int x, int y){
        // index: 0,1,2,...n-1
        point_list[layout][target][count][0] = x;
        point_list[layout][target][count][1] = y;
    }
    public void storeTime(int time_idx, int count_right, int count_wrong){
        time_list[time_idx][0] = count_right;
        time_list[time_idx][1] = count_wrong;
    }
    public void storeTimeLayout(int layout_idx, int target, int time){
        int count = 0;
        for(int i=0;i<5;i++){
            count = i;
            if(time_layout_list[layout_idx][target][i] == -1) break;
        }
        Log.i("IDX1", String.valueOf(layout_idx));
        Log.i("IDX2", String.valueOf(target));
        Log.i("IDX3", String.valueOf(count));
        Log.i("VALUE", String.valueOf(time));
        time_layout_list[layout_idx][target][count] = time;
    }
    public void initFileString(){
        if(test_mode == 1){
            for(int i=0;i<4;i++){
                int end = (i == 0 || i == 1)? 4 : 6;
                for(int j=0;j<end;j++){
                    for(int k=0;k<5;k++){
                        point_list[i][j][k][0] = -1;
                        point_list[i][j][k][1] = -1;
                    }
                }
            }
        }
        else if(test_mode == 2){
            for(int i=0;i<3;i++){
                for(int j=0;j<2;j++){
                    time_list[i][j] = -1;
                }
            }
        }
        else{
            for(int i=0;i<6;i++){
                int end = (i==0 || i == 2 || i == 3)? 4 : 6;
                for(int j=0;j<end;j++){
                    for(int k=0;k<5;k++){
                        time_layout_list[i][j][k] = -1;
                    }
                }
            }
        }
    }
    public String getFileString(){
        String out = "";
        if(test_mode == 1){
            for(int i=0;i<4;i++){
                int end = (i == 0 || i == 1)? 4 : 6;
                for(int j=0;j<end;j++){
                    for(int k=0;k<5;k++){
                        int point[] = point_list[i][j][k];
                        out += (point[0]+","+point[1]+" ");
                    }
                }
                //out += "\n";
            }
        }
        else if(test_mode == 2){
            for(int i=0;i<3;i++){
                int right = time_list[i][0];
                int wrong = time_list[i][1];
                out += (right+" "+wrong+"\n");
            }
        }
        else{
            for(int i=0;i<6;i++){
                int end = (i == 0 || i == 2 || i == 3)? 4 : 6;
                for(int j=0;j<end;j++){
                    for(int k=0;k<5;k++){
                        int time = time_layout_list[i][j][k];
                        out += (time+" ");
                    }
                }
            }
        }
        return out;
    }
}