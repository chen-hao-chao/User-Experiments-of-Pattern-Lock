# User Experiments of Pattern Lock

### 实验流程
整体将分为三个子实验。
```
实验一：测试使用者对图形的反应与落点。
实验二：测试使用者对不同停顿时间的错误率。
实验三：测试使用者对图形解锁的反应时间。
```

#### 实验一
控量：图形锁的样式(路径)、激发时间、app图形大小、距离。 <br>
变量：target 位置、排版。 <br>
依变量：使用者对该图形的落点。 <br>

>>依据不同的 target 位置我们可以拟合出最佳区域划分。 <br>

:arrow_right: 执行流程：
首先，进入到手机主页面，实验指导人请输入`test_1_($name)`，当中 name 可以是任何字串，用于命名实验。用户实验必须固定使用者**使用姿势**，实验指导人必须请受试者以**单手持手机**，并使用**惯用手拇指**滑动图形锁。在实验开始之前须告知受试者以下几点： <br>
```
1. 在此实验中必须以单手滑开图形锁，图案为36987452(以下图示说明)。
2. 在此实验中当到达定点(2)时，必须手持不放维持 0.5s，会有图示框跳出。
3. 图示框跳出后，将手指移至目标图案上放开。
4. 请留意目标图案会每隔五次更换位置，不会计时，请以准确性为优先。
```
---

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
