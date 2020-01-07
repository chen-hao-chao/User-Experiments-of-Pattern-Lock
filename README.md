# User Experiments of Pattern Lock

## Introduction

As people attach great importance to the safety of mobile devices, screen locks have become the first line of defense, and a variety of applications have emerged today. However, its use often results in a decrease in use efficiency. According to a survey conducted by Apple in 2016, users open their mobile phone about 80 times per day on average. The overhead caused by unlocking is very pretty heavy. Therefore, this project aims to minimize the time of unlocking and activate applications on smart phones. Three analyses of usage habits was performed. We came up with a one-step way to "unlock + activate the app" to reduce the operation time for users to select applications on the main page. Also, this concept is applicable. Whether it is pattern lock, as well as fingerprint and face recognition locks, can be applied to this setting. The following experiments will be conducted on android smart phones. We will discuss the experimental results and subject's behavior in detail in the following sections.

## Method

We have designed a set of experiments, including three sub-parts, which are aimed at landing habits, error rates, time did the corresponding test. The implementation details are shown below:

- Recruitment of subjects: A total of 11 subjects were recruited in this experiment, 6 women and 5 men. All 11 subjects have used smart phones for more than 5 years. There are no discomforts in the hands, and the dominant hand is the right hand.
- Experiment 1: The overall workflow is shown below. This experiment will evaluate the landing points of users. The experimental variables are the position of the target app and the layout of the bounce window after long pressure. The control amount is the pattern, the firing time, the app graphic size and the distance, and the dependent variable is its click position.
- Implementation details: The input device is an Android phone, and mode 1 test of the experimental app is used[1]. The subject shall slide the thumb with his dominant thumb ock, and can be supported by another hand-held mobile phone on the back of the dominant hand. The followings will be informed before the experiment begins:
```
1. In this experiment, you must slide the graphic lock with one hand, the pattern is 36987452 (Figure 4).
2. In this experiment, when the fixed point (2) is reached, you must hold it for 0.5s, and a box will pop out.
3. After the icon frame pops out, move your finger to the target pattern and release it.
4. Please note that the target pattern will change its position every five times. It will not be timed. Please give priority to accuracy.
```
We will record the position of different apps (4 types in total), including different app positions (configuration 1 and 2 for 4 types, configuration 3 and 4 for 6 types), position of fingers' release(5 times), so the subject will undergo 4\*4\*5\*2+4\*6\*5\*2=400(times) at this stage. The experiment takes about 7 minutes.

#### 实验二
控量：图形锁的样式(路径)。 <br>
变量：激发时间。 <br>
依变量：错误率。 <br>

>>依据错误率-时间 trade-off 排序，我们可以得到最佳激发时间。

:arrow_right: 执行流程：
首先，进入到手机主页面，实验指导人请输入`test_2_($name)`，当中 name 可以是任何字串，用于命名实验。用户实验必须固定使用者**使用姿势**，实验指导人必须请受试者以**单手持手机**，并使用**惯用手拇指**滑动图形锁。在实验开始之前须告知受试者以下几点： <br>
```
1. 在此实验中必须以单手滑开图形锁，图案为36987452(以下图示说明)。
2. 在此实验中当到达定点(2)时，必须手持不放维持。
3. 停止特定时间后会自动重新开始，为正常现象。
4. 不会计时，请以准确性为优先。
```

会记录不同停顿时间配置(0,1,2,3,4,5)下，滑开图形锁(20次)所需的时间，因此受试者会经历 6\*20=120 次测试。
测试后数据会复制到剪贴簿，切记马上存下来。测试数据将如下展示：

```
20 0
8 12
7 13
1 19
0 20
0 20
```

实验所需时间约 4 分钟，请受试者耐心做完实验！ <br>
完成实验后，请将不同受试者的资料分别存储，建议格式： <br>
($name)/test_1.txt <br>
       /test_2.txt <br>
       /test_3.txt <br>
       
---

#### 实验三
控量：图形锁的样式(路径)、激发时间、app图形大小、距离、激发角度。 <br>
变量：target 位置、排版。 <br>
依变量：时间。 <br>

>>依据时间排序，我们可以得到最佳排版与位置。 <br>
NOTE: 需改动code当中注释`// $$`的地方。

:arrow_right: 执行流程：
首先，进入到手机主页面，实验指导人请输入`test_3_($name)`，当中 name 可以是任何字串，用于命名实验。用户实验必须固定使用者**使用姿势**，实验指导人必须请受试者以**单手持手机**，并使用**惯用手拇指**滑动图形锁。在实验开始之前须告知受试者以下几点： <br>
```
1. 在此实验中必须以单手滑开图形锁，图案为36987452(以下图示说明)。
2. 实验分为两阶段，每阶段前会有 3 秒预备时间，请受试者专注。
3. 开始后必须以最快的速度去滑开图形锁，并点中目标。
4. 第一阶段为模拟一般手机页面，请以一般习惯的方式点击即可。
5. 第二阶段为实验页面，受试者者必须以长压来激活 app。
6. 目标位置为随机产生，请随时留意，不要抢快，必须以正确率为前题。
```

会记录不同配置(0,1与0,1,2,3)下，不同app 位置(0,1,2,3或0,1,2,3,4,5)(随机)，滑开图形锁(5次)所需的时间，因此受试者会经历1\*4\*5+1\*6\*5+2\*4\*5+2\*6\*5=150 次测试。
测试后数据会复制到剪贴簿，切记马上存下来。测试数据将如下展示：

```
4175 2700 2761 2304 2220 2522 7292 4971 3687 2258 2609 2559 3665 2154 2283 3459 2364 2275 2332 5212 3512 2801 2838 2934 2842 2923 2587 2771 3201 2836 2994 2913 2749 2787 2563 2749 3064 2624 3216 3189 3494 7868 3006 2512 2629 2877 2556 2653 8446 -444 2376 2434 2278 2380 3057 2295 2319 2401 2379 3055 2343 2355 3254 2384 2450 2892 2462 2448 2458 2304 2454 2348 2560 2455 2392 8085 4077 2572 2457 3762 2410 3201 2540 2386 2556 2951 4137 2731 2516 2571 3802 2947 2497 2538 2364 2874 2663 2576 2672 2784 2668 2563 2426 2588 2635 2576 2594 2552 2553 2565 4006 2679 2863 2585 2649 2531 2643 2585 2447 2563 4476 2633 2647 2548 4250 4000 2868 2384 5635 4035 2829 2641 2471 2529 2375 2593 2305 2400 4052 2949 2503 3670 2500 2498 2565 3501 2810 7044 2668 2529 
```

实验所需时间约 7 分钟，请受试者耐心做完实验！ <br>
完成实验后，请将不同受试者的资料分别存储，建议格式： <br>
($name)/test_1.txt <br>
       /test_2.txt <br>
       /test_3.txt <br>
       
---

| 初始画面  | 输入错误图形 | 长压弹跳图案 | 正确图形锁 |
| -------- | -------- | --------- | --------|
|![](https://i.imgur.com/n4je25a.png)| ![](https://i.imgur.com/hjeOV5g.png) | ![](https://i.imgur.com/xLR1J55.jpg) |![](https://i.imgur.com/8ePq6FS.png)|


| target app |
| ---------  |
|![](https://i.imgur.com/jYS1kwY.png)|
