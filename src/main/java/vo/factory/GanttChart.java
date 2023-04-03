package vo.factory;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.TimePeriod;

/**
 * @author Danny
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GanttChart{

    public static void main(String[] args) {
        IntervalCategoryDataset dataset = createSampleDataset();
        JFreeChart chart = ChartFactory.createGanttChart("柔性车间流程图",
                "AGV各阶段详细实施计划",
                "任务周期",
                dataset,
                false,
                false,
                false);

        CategoryPlot plot=chart.getCategoryPlot();

        chart.getTitle().setFont(new Font("新宋体",Font.BOLD,20));
        CategoryAxis domainAxis=plot.getDomainAxis();
        //水平底部列表
        domainAxis.setLabelFont(new Font("新宋体",Font.BOLD,14));
        //水平底部标题
        domainAxis.setTickLabelFont(new Font("新宋体",Font.BOLD,12));
        //垂直标题
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("新宋体",Font.BOLD,16));
        //用来控制时间轴的显示,防止乱码
//        DateAxis da = (DateAxis)plot.getRangeAxis(0);
//        da.setDateFormatOverride(new SimpleDateFormat("yyyy"));

        FileOutputStream fop = null;
        try{
            System.out.println("Danny>> begin.");
            fop = new FileOutputStream("/home/qill/桌面/gantt12.jpg");
            ChartUtilities.writeChartAsJPEG(fop,1f, chart, 1200, 1000,null);
            System.out.println("Danny>> end..");
            System.out.println("Danny>> successful...");
        } catch (IOException e){
            e.printStackTrace();
        } finally{
            try{
                fop.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * M1 0 24 24 46 46 67 67 79 79 103 103 125 125 149 189 211 211 232 291 303 303 327
     * M2 0 13 71 95 232 255 277 291
     * M3 24 46 95 107 149 161 161 173 255 277 352 364
     * M4 46 57 57 71 125 136 173 189 327 341 341 352
     * AGV1 0 24 24 46 46 67
     * AGV2 24 46 46 57 67 79 79 103 103 125 125 136
     * AGV3 0 13 57 71 71 95 95 107 125 149 149 161 161 173 173 189 189 211 211 232 232 255 255 277 277 291 291 303 303 327 327 341 341 352 352 364
     * @return AGV
     * M1 0 24 24 46 110 134
     * M2 22 36 36 59 59 72 72 85 85 99 99 123 134 148
     * M3 0 22 57 69 85 97 99 111 123 135 136 148 148 160
     * M4 0 16 16 30 46 57 57 68 69 85 85 99 99 110 111 122 122 136 148 159
     * AGV1 0 24 24 46 46 57 57 69 69 85 85 97 99 110 110 134 134 148 148 160
     * AGV2 0 16 16 30 36 59 59 72 85 99 99 111 111 122 122 136 136 148 148 159
     * AGV3 0 22 22 36 57 68 72 85 85 99 99 123 123 135
     * times： =160
     */
    private static IntervalCategoryDataset createSampleDataset3() {

        final TaskSeriesS s1 = new TaskSeriesS("SCHEDULE");

        //final Task t1 = new Task("任务1", date(1, Calendar.JANUARY, 2001), date(5, Calendar.APRIL, 2001));
        int u=160;
        TaskS t1 = new TaskS("AGV1", new Timesad(0,u));
        t1.setPercentComplete(1);
        TaskS t2=new TaskS("AGV2", new Timesad(0,u));
        t2.setPercentComplete(1);
        TaskS t3=new TaskS("AGV3", new Timesad(0,u));
        t3.setPercentComplete(0);
        TaskS t4=new TaskS("机器M1", new Timesad(0,u));
        TaskS t5=new TaskS("机器M2", new Timesad(0,u));
        TaskS t6=new TaskS("机器M3", new Timesad(0,u));
        TaskS t7=new TaskS("机器M4", new Timesad(0,u));

        int[] a1;
        a1= new int[]{0, 24, 24, 46, 46, 57, 57, 69, 69 ,85 ,85, 97, 99, 110, 110, 134, 134, 148, 148 ,160};
        int[] a2 = new int[]{0 ,16 ,16 ,30 ,36 ,59 ,59 ,72 ,85 ,99 ,99 ,111 ,111 ,122 ,122 ,136 ,136, 148, 148, 159};
        int[] a3 = new int[]{0 ,22 ,22 ,36 ,57 ,68 ,72 ,85 ,85 ,99 ,99 ,123 ,123, 135 };
        int[] a4 = new int[]{0 ,24 ,24, 46, 110 ,134};
        int[] a5 = new int[]{22 ,36, 36, 59, 59, 72, 72, 85, 85, 99, 99, 123 ,134 ,148 };
        int[] a6 = new int[]{0 ,22, 57, 69, 85, 97, 99, 111, 123 ,135 ,136 ,148 ,148, 160 };
        int[] a7 = new int[]{0 ,16 ,16, 30 ,46 ,57 ,57 ,68 ,69 ,85, 85, 99, 99, 110, 111, 122, 122, 136, 148, 159  };
        int e=0;
        for (int i = 0; i < a1.length; i=i+2) {
            t1.addSubtask(new TaskS("AGV1",new Timesad(a1[i],a1[i+1])));
            t1.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a2.length; i=i+2) {
            t2.addSubtask(new TaskS("AGV2",new Timesad(a2[i],a2[i+1])));
            t2.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a3.length; i=i+2) {
            t3.addSubtask(new TaskS("AGV3",new Timesad(a3[i],a3[i+1])));
            t3.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a4.length; i=i+2) {
            t4.addSubtask(new TaskS("机器M1",new Timesad(a4[i],a4[i+1])));
            t4.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a5.length; i=i+2) {
            t5.addSubtask(new TaskS("机器M1",new Timesad(a5[i],a5[i+1])));
            t5.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a6.length; i=i+2) {
            t6.addSubtask(new TaskS("机器M1",new Timesad(a6[i],a6[i+1])));
            t6.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a7.length; i=i+2) {
            t7.addSubtask(new TaskS("机器M1",new Timesad(a7[i],a7[i+1])));
            t7.getSubtask(e).setPercentComplete(e++%2);
        }
        s1.add(t1);
       s1.add(t2);
        s1.add(t3);
        s1.add(t4);
        s1.add(t5);
        s1.add(t6);
        s1.add(t7);
        for (int i = 0; i < 37; i++) {
            s1.add(new TaskS("路径"+i,new Timesad(0,u)));
        }


        final TaskSeriesCollections collection = new TaskSeriesCollections();
        collection.add(s1);

        return collection;
    }

    /**
     *
     * @return 机器
     */
    private static IntervalCategoryDataset createSampleDataset1() {

        final TaskSeriesS s1 = new TaskSeriesS("SCHEDULE");

        //final Task t1 = new Task("任务1", date(1, Calendar.JANUARY, 2001), date(5, Calendar.APRIL, 2001));
        TaskS t1 = new TaskS("机器M1", new Timesad(0,310));
        TaskS t2=new TaskS("机器M2", new Timesad(0,310));
        TaskS t3=new TaskS("机器M3", new Timesad(0,310));
        TaskS t4=new TaskS("机器M4", new Timesad(0,310));
/*
最好
M1 13 35 35 47 48 60 60 84 84 105 111 123
M2 0 13 22 36 36 60 60 73 87 111 123 137 139 153
M3 0 22 36 48 105 117 117 139 148 160
M4 0 16 16 30 47 63 73 87 87 101 101 112 112 123 137 148 153 164
AGV1 0 22 22 36 36 48 48 60 60 84 84 105 105 117 117 139 139 153 153 164
AGV2 0 16 16 30 36 60 60 73 73 87 87 111 111 123 123 137 137 148 148 160
AGV3 0 13 13 35 35 47 47 63 87 101 101 112 112 123

最坏


* */

        List<Integer> a=new ArrayList<>();
        a.add(0);a.add(24);a.add(24);a.add(38);a.add(38);a.add(50);a.add(50);a.add(62);a.add(62);a.add(87);a.add(87);a.add(101);a.add(101);a.add(112);a.add(112);a.add(128);a.add(138);a.add(154);a.add(154);a.add(166);
        List<Integer> b=new ArrayList<>();
        b.add(0);b.add(22);b.add(22);b.add(34);b.add(38);b.add(49);b.add(49);b.add(63);b.add(63);b.add(87);b.add(87);b.add(98);b.add(98);b.add(120);b.add(120);b.add(142);b.add(142);b.add(153);b.add(154);b.add(166);
        List<Integer> c=new ArrayList<>();
        c.add(0);c.add(16);c.add(16);c.add(30);c.add(50);c.add(62);c.add(62);c.add(74);c.add(86);c.add(110);c.add(110);c.add(132);c.add(132);c.add(156);
        List<Integer> d=new ArrayList<>();
        d.add(1);
        int e=0;
        for (int i = 0; i < a.size(); i=i+2) {
            t1.addSubtask(new TaskS("机器1",new Timesad(a.get(i),a.get(i+1))));
            t1.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < b.size(); i=i+2) {
            t2.addSubtask(new TaskS("机器2",new Timesad(b.get(i),b.get(i+1))));
            t2.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < c.size(); i=i+2) {
            t3.addSubtask(new TaskS("机器3",new Timesad(c.get(i),c.get(i+1))));
            t3.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < c.size(); i=i+2) {
            t4.addSubtask(new TaskS("机器4",new Timesad(d.get(i),d.get(i+1))));
            t4.getSubtask(e).setPercentComplete(e++%2);
        }
        s1.add(t1);
        s1.add(t2);
        s1.add(t3);
        s1.add(t4);

        final TaskSeriesCollections collection = new TaskSeriesCollections();
        collection.add(s1);

        return collection;
    }
    /*
    *
    *road1 - 0,  2,  2  ， 4 ， 4  ， 6 ， 48,  50,  50,  72,  72,  74,  93,  95,  95,  117,  117,  119,  218,  220,  220,  242,  242,  244,  284,  286,  286,  307,
road2 - 216,  218,
road3 - 214,  216,
road4 - 212,  214,
road5 - 210,  212,
road6 -
road7 -
road8 - 2,  4,  4,  6,  6,  8,  46,  48,  74,  76,  91,  93,  119,  121,  244,  246,  282,  284,
road9 -
road10 -
road11 -
road12 - 208,  210,
road13 -
road14 -
road15 - 4,  6,  6,  8,  8,  10,  44,  46,  76,  78,  89,  91,  121,  123,  246,  248,  280,  282,
road16 - 6,  8,  8,  10,  10,  12,  42,  44,  78,  80,  123,  125,  248,  250,  278,  280,
road17 - 8,  10,  10,  12,  12,  14,  40,  42,  83,  85,  85  ， 87 ， 152,  154,  154  ， 156 ， 208,  210,  250,  252,  276,  278,
road18 - 12,  14,  14,  16,  85,  87,  87,  89,  154,  156,  210,  212,  252,  254,
road19 - 14,  16,  16,  18,  18  ， 31 ， 31,  45,  45,  47,  87,  89,  89,  102,  102  ， 104 ， 104  ， 106 ， 106  ， 129 ， 129,  142,  142,  166,  166,  168,  168  ， 170 ， 170,  193,  193,  206,  206,  208,  212,  214,  214,  228,  228,  230,  254,  256,  291,  293,  293,  316,
road20 - 18,  20,  256,  258,
road21 - 20,  22,  258,  260,
road22 - 87,  89,
road23 -
road24 - 10,  12,  38,  40,  81,  83,  150,  152,  156,  158,  206,  208,  274,  276,
road25 -
road26 - 47,  49,  106,  108,  168,  170,  230,  232,  289,  291,
road27 -
road28 - 22,  24,  260,  262,
road29 - 85,  87,
road30 - 83,  85,
road31 - 12,  14,  14,  36,  36,  38,  53,  55,  55,  67,  67,  79,  79,  81,  81  ， 83 ， 112,  114,  114,  136,  136,  148,  148,  150,  174,  176,  176,  188,  188,  190,  190  ， 192 ， 192,  204,  204,  206,  230,  232,  236,  238,  238  ， 260 ， 260  ， 272 ， 272  ， 274 ， 274  ， 286 ，
road32 - 51,  53,  56,  58,  110,  112,  172,  174,  228,  230,  234,  236,
road33 - 49,  51,  54,  56,  108,  110,  170,  172,  226,  228,  232,  234,  287,  289,
road34 - 52,  54,  224,  226,  285,  287,
road35 - 24,  26,  50,  52,  222,  224,  262,  264,  283,  285,
road36 -
road37 -
road38 - 190,  192,
road39 -
road40 -
road41 -
road42 - 26,  28,  48,  50,  220,  222,  264,  266,  281,  283,
road43 -
road44 -
road45 - 192,  194,
road46 - 194,  196,
road47 - 196,  198,
road48 - 198,  200,
road49 - 28,  30,  30,  46,  46,  48,  200,  202,  202,  218,  218,  220,  266,  268,  268,  279,  279,  281,

times： =316
    *
    *
    * */
    /*road1 - 0,  2,  2  ， 4 ， 4  ， 6 ， 70,  72,  72,  94,  94,  96,  108,  110,  110,  131,  131,  133,  179,  181,  181,  203,  203,  205,  205  ， 207 ， 207,  228,  228,  240,  240  ， 242 ， 242,  263,  263,  275,  275,  277,  379,  381,  381,  393,  393,  395,  469,  471,  471,  483,
road2 - 189,  191,  212,  214,  377,  379,
road3 - 187,  189,  210,  212,  375,  377,
road4 - 185,  187,  208,  210,  373,  375,
road5 - 183,  185,  206,  208,  371,  373,
road6 -
road7 -
road8 - 2,  4,  4,  6,  6,  8,  68,  70,  106,  108,  108  ， 110 ， 133,  135,  177,  179,  205,  207,  277,  279,  395,  397,  467,  469,
road9 -
road10 -
road11 -
road12 - 181,  183,  204,  206,  369,  371,
road13 -
road14 -
road15 - 4,  6,  6,  8,  8,  10,  66,  68,  104,  106,  110,  112,  135,  137,  175,  177,  207,  209,  279,  281,  397,  399,  465,  467,
road16 - 6,  8,  8,  10,  10,  12,  112,  114,  137,  139,  173,  175,  209,  211,  281,  283,  399,  401,
road17 - 8,  10,  10,  12,  12,  14,  114,  116,  139,  141,  171,  173,  211,  213,  283,  285,  337,  339,  401,  403,
road18 - 10,  12,  12,  14,  116,  118,  213,  215,  339,  341,  403,  405,
road19 - 12,  14,  14,  16,  16  ， 29 ， 29,  31,  118,  120,  163,  165,  165,  179,  179,  181,  186,  188,  188,  202,  202,  204,  215,  217,  217,  240,  341,  343,  343,  367,  367,  369,  405,  407,  407,  420,  420,  422,
road20 - 16,  18,  120,  122,
road21 - 18,  20,  122,  124,
road22 - 64,  66,  102,  104,  463,  465,
road23 -
road24 - 14,  16,  141,  143,  169,  171,  285,  287,  335,  337,
road25 -
road26 - 31,  33,  161,  163,  184,  186,  422,  424,
road27 -
road28 - 20,  22,  124,  126,
road29 - 62,  64,  100,  102,  461,  463,
road30 - 60,  62,  98,  100,  459,  461,
road31 - 16,  18,  18,  40,  40,  42,  58,  60,  94,  96,  96,  98,  98  ， 110 ， 110,  112,  143,  145,  145,  167,  167,  169,  287,  289,  289,  311,  311,  333,  333,  335,  457,  459,
road32 - 56,  58,  92,  94,  94,  96,  455,  457,
road33 - 33,  35,  54,  56,  90,  92,  92,  94,  159,  161,  182,  184,  424,  426,  453,  455,
road34 - 52,  54,  88,  90,  90,  92,  157,  159,  180,  182,  451,  453,
road35 - 22,  24,  50,  52,  86,  88,  88,  90,  155,  157,  157  ， 159 ， 178,  180,  449,  451,
road36 -
road37 -
road38 - 42,  44,  112,  114,
road39 -
road40 - 35,  37,  426,  428,
road41 -
road42 - 24,  26,  48,  50,  84,  86,  86,  88,  153,  155,  159,  161,  176,  178,  447,  449,
road43 -
road44 -
road45 - 44,  46,  114,  116,
road46 - 46,  48,  116,  118,
road47 - 37,  39,  48,  50,  118,  120,  428,  430,
road48 - 39,  41,  50,  52,  120,  122,  430,  432,
road49 - 26,  28,  28,  44,  44  ， 46 ， 46  ， 48 ， 52,  54,  54  ， 68 ， 68  ， 82 ， 82  ， 84 ， 84  ， 86 ， 122,  124,  124,  135,  135,  151,  151,  153,  161,  163,  163,  174,  174,  176,  432,  434,  434,  445,  445,  447,
    * */
    private static IntervalCategoryDataset createSampleDataset() {

        final TaskSeriesS s1 = new TaskSeriesS("SCHEDULE");

        //final Task t1 = new Task("任务1", date(1, Calendar.JANUARY, 2001), date(5, Calendar.APRIL, 2001));
        int u=490;
        TaskS t1 = new TaskS("机器1-节点1", new Timesad(0,u));
        t1.setPercentComplete(1);
        TaskS t2=new TaskS("路径2", new Timesad(0,u));
        t2.setPercentComplete(1);
        TaskS t3=new TaskS("节点3", new Timesad(0,u));
        t3.setPercentComplete(0);
        TaskS t4=new TaskS("路径4", new Timesad(0,u));
        TaskS t5=new TaskS("节点5", new Timesad(0,u));
        TaskS t6=new TaskS("路径8", new Timesad(0,u));
        TaskS t7=new TaskS("路径12", new Timesad(0,u));
//        TaskS t8=new TaskS("路径12", new Timesad(0,u));
        TaskS t9=new TaskS("节点15", new Timesad(0,u));
        TaskS t10=new TaskS("路径16", new Timesad(0,u));
        TaskS t11=new TaskS("节点17", new Timesad(0,u));
        TaskS t12=new TaskS("路径18", new Timesad(0,u));
        TaskS t13=new TaskS("机器2-节点19", new Timesad(0,u));
        TaskS t14=new TaskS("路径20", new Timesad(0,u));
        TaskS t15=new TaskS("节点21", new Timesad(0,u));
        TaskS t16=new TaskS("路径22", new Timesad(0,u));

        TaskS t17=new TaskS("路径24", new Timesad(0,u));
        TaskS t8=new TaskS("路径26", new Timesad(0,u));
        TaskS t18=new TaskS("路径28", new Timesad(0,u));
        TaskS t19=new TaskS("节点29", new Timesad(0,u));
        TaskS t20=new TaskS("路径30", new Timesad(0,u));

        TaskS t21=new TaskS("机器3-节点31", new Timesad(0,u));
        TaskS t22=new TaskS("路径32", new Timesad(0,u));
        TaskS t23=new TaskS("节点33", new Timesad(0,u));
        TaskS t24=new TaskS("路径34", new Timesad(0,u));
        TaskS t25=new TaskS("节点35", new Timesad(0,u));

        TaskS t26=new TaskS("路径38", new Timesad(0,u));
        TaskS t33=new TaskS("路径40", new Timesad(0,u));
        TaskS t27=new TaskS("路径42", new Timesad(0,u));
        TaskS t28=new TaskS("节点45", new Timesad(0,u));
        TaskS t29=new TaskS("路径46", new Timesad(0,u));
        TaskS t30=new TaskS("节点47", new Timesad(0,u));

        TaskS t31=new TaskS("路径48", new Timesad(0,u));
        TaskS t32=new TaskS("机器4-节点49", new Timesad(0,u));
        int[] a1=new int[]{0,  2,  2  , 4 , 4  , 6 , 70,  72,  72,  94,  94,  96,  108,  110,  110,  131,  131,  133,  179,  181,  181,  203,  203,  205,  205  , 207 , 207,  228,  228,  240,  240  , 242 , 242,  263,  263,  275,  275,  277,  379,  381,  381,  393,  393,  395,  469,  471,  471,  483 };
        int[] a2=new int[]{189,  191,  212,  214,  377,  379};
        int[] a3=new int[]{187,  189,  210,  212,  375,  377};
        int[] a4=new int[]{ 185,  187,  208,  210,  373,  375 };
        int[] a5=new int[]{183,  185,  206,  208,  371,  373};
        int[] a6=new int[]{2,  4,  4,  6,  6,  8,  68,  70,  106,  108,  108  , 110 , 133,  135,  177,  179,  205,  207,  277,  279,  395,  397,  467,  469};
        int[] a7=new int[]{ 181,  183,  204,  206,  369,  371};
        int e=0;
        for (int i = 0; i < a1.length; i=i+2) {
            t1.addSubtask(new TaskS("AGV1",new Timesad(a1[i],a1[i+1])));
            t1.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a2.length; i=i+2) {
            t2.addSubtask(new TaskS("AGV2",new Timesad(a2[i],a2[i+1])));
            t2.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a3.length; i=i+2) {
            t3.addSubtask(new TaskS("AGV3",new Timesad(a3[i],a3[i+1])));
            t3.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a4.length; i=i+2) {
            t4.addSubtask(new TaskS("机器M1",new Timesad(a4[i],a4[i+1])));
            t4.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a5.length; i=i+2) {
            t5.addSubtask(new TaskS("机器M1",new Timesad(a5[i],a5[i+1])));
            t5.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a6.length; i=i+2) {
            t6.addSubtask(new TaskS("机器M1",new Timesad(a6[i],a6[i+1])));
            t6.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a7.length; i=i+2) {
            t7.addSubtask(new TaskS("机器M1",new Timesad(a7[i],a7[i+1])));
            t7.getSubtask(e).setPercentComplete(e++%2);
        }
        int[] a8=new int[]{4,  6,  6,  8,  8,  10,  66,  68,  104,  106,  110,  112,  135,  137,  175,  177,  207,  209,  279,  281,  397,  399,  465,  467};
        int[] a9=new int[]{6,  8,  8,  10,  10,  12,  112,  114,  137,  139,  173,  175,  209,  211,  281,  283,  399,  401};
        int[] a10=new int[]{8,  10,  10,  12,  12,  14,  114,  116,  139,  141,  171,  173,  211,  213,  283,  285,  337,  339,  401,  403};
        int[] a11=new int[]{10,  12,  12,  14,  116,  118,  213,  215,  339,  341,  403,  405};
        int[] a12=new int[]{12,  14,  14,  16,  16  , 29 , 29,  31,  118,  120,  163,  165,  165,  179,  179,  181,  186,  188,  188,  202,  202,  204,  215,  217,  217,  240,  341,  343,  343,  367,  367,  369,  405,  407,  407,  420,  420,  422};
        int[] a13=new int[]{16,  18,  120,  122};
        int[] a14=new int[]{ 18,  20,  122,  124};
        int[] a15=new int[]{ 64,  66,  102,  104,  463,  465};
        e=0;
        for (int i = 0; i < a8.length; i=i+2) {
            t9.addSubtask(new TaskS("8",new Timesad(a8[i],a8[i+1])));
            t9.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a9.length; i=i+2) {
            t10.addSubtask(new TaskS("9",new Timesad(a9[i],a9[i+1])));
            t10.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a10.length; i=i+2) {
            t11.addSubtask(new TaskS("10",new Timesad(a10[i],a10[i+1])));
            t11.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a11.length; i=i+2) {
            t12.addSubtask(new TaskS("11",new Timesad(a11[i],a11[i+1])));
            t12.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a12.length; i=i+2) {
            t13.addSubtask(new TaskS("12",new Timesad(a12[i],a12[i+1])));
            t13.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a13.length; i=i+2) {
            t14.addSubtask(new TaskS("13",new Timesad(a13[i],a13[i+1])));
            t14.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a14.length; i=i+2) {
            t15.addSubtask(new TaskS("14",new Timesad(a14[i],a14[i+1])));
            t15.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a15.length; i=i+2) {
            t16.addSubtask(new TaskS("15",new Timesad(a15[i],a15[i+1])));
            t16.getSubtask(e).setPercentComplete(e++%2);
        }
        int[] a16=new int[]{14,  16,  141,  143,  169,  171,  285,  287,  335,  337};
        int[] a17=new int[]{31,  33,  161,  163,  184,  186,  422,  424};
        int[] a18=new int[]{20,  22,  124,  126};
        int[] a19=new int[]{  62,  64,  100,  102,  461,  463};
        int[] a20=new int[]{60,  62,  98,  100,  459,  461};
        e=0;
        for (int i = 0; i < a16.length; i=i+2) {
            t17.addSubtask(new TaskS("16",new Timesad(a16[i],a16[i+1])));
            t17.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a17.length; i=i+2) {
            t8.addSubtask(new TaskS("17",new Timesad(a17[i],a17[i+1])));
            t8.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a18.length; i=i+2) {
            t18.addSubtask(new TaskS("18",new Timesad(a18[i],a18[i+1])));
            t18.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a19.length; i=i+2) {
            t19.addSubtask(new TaskS("19",new Timesad(a19[i],a19[i+1])));
            t19.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a20.length; i=i+2) {
            t20.addSubtask(new TaskS("20",new Timesad(a20[i],a20[i+1])));
            t20.getSubtask(e).setPercentComplete(e++%2);
        }

        int[] a21=new int[]{ 16,  18,  18,  40,  40,  42,  58,  60,  94,  96,  96,  98,  98  , 110 , 110,  112,  143,  145,  145,  167,  167,  169,  287,  289,  289,  311,  311,  333,  333,  335,  457,  459};
        int[] a22=new int[]{56,  58,  92,  94,  94,  96,  455,  457};
        int[] a23=new int[]{33,  35,  54,  56,  90,  92,  92,  94,  159,  161,  182,  184,  424,  426,  453,  455};
        int[] a24=new int[]{52,  54,  88,  90,  90,  92,  157,  159,  180,  182,  451,  453};
        int[] a25=new int[]{22,  24,  50,  52,  86,  88,  88,  90,  155,  157,  157  , 159 , 178,  180,  449,  451};
        e=0;
        for (int i = 0; i < a21.length; i=i+2) {
            t21.addSubtask(new TaskS("21",new Timesad(a21[i],a21[i+1])));
            t21.getSubtask(e).setPercentComplete(e++%2);
        }e=0;
        for (int i = 0; i < a22.length; i=i+2) {
            t22.addSubtask(new TaskS("22",new Timesad(a22[i],a22[i+1])));
            t22.getSubtask(e).setPercentComplete(e++%2);
        }e=0;
        for (int i = 0; i < a23.length; i=i+2) {
            t23.addSubtask(new TaskS("23",new Timesad(a23[i],a23[i+1])));
            t23.getSubtask(e).setPercentComplete(e++%2);
        }e=0;
        for (int i = 0; i < a24.length; i=i+2) {
            t24.addSubtask(new TaskS("24",new Timesad(a24[i],a24[i+1])));
            t24.getSubtask(e).setPercentComplete(e++%2);
        }e=0;
        for (int i = 0; i < a25.length; i=i+2) {
            t25.addSubtask(new TaskS("25",new Timesad(a25[i],a25[i+1])));
            t25.getSubtask(e).setPercentComplete(e++%2);
        }e=0;
        int[] a26=new int[]{42,  44,  112,  114};
        int[] a33=new int[]{35,  37,  426,  428};
        int[] a27=new int[]{24,  26,  48,  50,  84,  86,  86,  88,  153,  155,  159,  161,  176,  178,  447,  449};
        int[] a28=new int[]{44,  46,  114,  116};
        int[] a29=new int[]{46,  48,  116,  118};
        int[] a30=new int[]{37,  39,  48,  50,  118,  120,  428,  430};
        int[] a31=new int[]{39,  41,  50,  52,  120,  122,  430,  432};
        int[] a32=new int[]{ 26,  28,  28,  44,  44  , 46 , 46  , 48 , 52,  54,  54 , 68 , 68  , 82 , 82  , 84 , 84 , 86 , 122,  124,  124,  135,  135,  151,  151,  153,  161,  163,  163,  174,  174,  176,  432,  434,  434,  445,  445,  447};
        e=0;
        for (int i = 0; i < a26.length; i=i+2) {
            t26.addSubtask(new TaskS("26",new Timesad(a26[i],a26[i+1])));
            t26.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a33.length; i=i+2) {
            t33.addSubtask(new TaskS("33",new Timesad(a33[i],a33[i+1])));
            t33.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a27.length; i=i+2) {
            t27.addSubtask(new TaskS("27",new Timesad(a27[i],a27[i+1])));
            t27.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a28.length; i=i+2) {
            t28.addSubtask(new TaskS("28",new Timesad(a28[i],a28[i+1])));
            t28.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a29.length; i=i+2) {
            t29.addSubtask(new TaskS("29",new Timesad(a29[i],a29[i+1])));
            t29.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a30.length; i=i+2) {
            t30.addSubtask(new TaskS("30",new Timesad(a30[i],a30[i+1])));
            t30.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a31.length; i=i+2) {
            t31.addSubtask(new TaskS("31",new Timesad(a31[i],a31[i+1])));
            t31.getSubtask(e).setPercentComplete(e++%2);
        }
        e=0;
        for (int i = 0; i < a32.length; i=i+2) {
            t32.addSubtask(new TaskS("32",new Timesad(a32[i],a32[i+1])));
            t32.getSubtask(e).setPercentComplete(e++%2);
        }




        s1.add(t1);
        s1.add(t2);
        s1.add(t3);
        s1.add(t4);
        s1.add(t5);
        s1.add(t6);
        s1.add(t7);

        s1.add(t9);
        s1.add(t10);
        s1.add(t11);
        s1.add(t12);
        s1.add(t13);
        s1.add(t14);
        s1.add(t15);
        s1.add(t16);

        s1.add(t17);
        s1.add(t8);
        s1.add(t18);
        s1.add(t19);
        s1.add(t20);
        s1.add(t21);
        s1.add(t22);
        s1.add(t23);
        s1.add(t24);
        s1.add(t25);
        s1.add(t26);
        s1.add(t33);
        s1.add(t27);
        s1.add(t28);
        s1.add(t29);
        s1.add(t30);
        s1.add(t31);
        s1.add(t32);
        final TaskSeriesCollections collection = new TaskSeriesCollections();
        collection.add(s1);

        return collection;
    }
}
