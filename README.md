# User Experiments of Pattern Lock

## Introduction

Screen locks often bring about problems about decreasing efficiency, since we might need to deal with complicated passwords as accessing our cellphones. According to a survey conducted by Apple in 2016, users open their mobile phone about 80 times per day on average. The overhead caused by unlocking is undesirable, and as a result, this project aims to minimize the unlocking time and design a faster approach to activate applications on smart phones. We conducted three analyses of user's habits and came upon an one-step-way method that people could unlock and activate apps on the main page without extra accessing and loading time. Also, this concept is applicable. Cellphones with a pattern lock, a fingerprint lock or a face recognition lock can be applied to this setting. We conducted the following experiments on android smart phones. Detailed discussion about the experimental results and subject's behavior is shown below.

## Method

We have designed a set of experiments, including three sub-parts, which are aimed at landing habits, error rates, time did the corresponding test. The implementation details are shown below:

#### Recruitment of subjects

A total of 11 subjects were recruited, 6 women and 5 men. All 11 subjects have used smart phones for more than 5 years. They have no discomforts in the hands, and are all right-handed.

#### Experiment 1

This experiment will evaluate the landing points of the users. The experimental variables are the position of target apps and the layout of the bounce window after long pressuring. The control amount is the pattern, the firing time, the app graphic size and the distance, and the dependent variable is the landing position.

#### Process and details

The subjects will be inform to use their dominant thumb to slide over the screen and can use the left hand as support to hold the cellphone. The followings will be informed before the experiment begins:

```
1. The pattern is 36987452.
2. When the fixed point (2) is reached, you must hold it for 0.5s, and a box will pop out.
3. After the icon frame pops out, move your finger onto the target-app icon and release it.
4. Please note that the target pattern will change its position every five times. It will not be timed. Please give priority to accuracy.
```

We will record the landing positions of different configurations (configuration 1 and 2 for 4 types, configuration 3 and 4 for 6 types), position of fingers' release (5 times) according to different targeted apps (4 types in total), so the subject will undergo 4\*4\*5\*2+4\*6\*5\*2=400(times) tests at this stage. The experiment takes about 7 minutes.

<img src="image/1.png" width="550">

#### Experiment 2

This experiment will evaluate the subject's intention to unlock by checking the pressing time to activate the bounce window. Obviously, the experimental variable is the holden time. The controlled variable is the path of the pattern lock while the dependent variable is the error rate.

#### Process and details

The subjects will be inform to use their dominant thumb to slide over the screen and can use the left hand as support to hold the cellphone. The followings will be informed before the experiment begins:

```
1. The lock must be opened with one hand, case 36987452.
2. When you reach the specified point, you must hold on for a certain time.
3. It will automatically restart.
4. It will not be timed. Please give priority to accuracy.
```

It will record the time required to open the pattern lock. 10-time tests with different pause time (a total of 6 types) will be conducted. The subject will go through 6\*10=60 tests. It takes about 4 minutes.

<img src="image/2.png" width="550">

#### Experiment 3

We aim to compare the time difference to open a certain app between using our method and the usual way. The experimental variables are the mode after unlocking, the location of the target app, and the layout of the bounce window after long press. The controlled variables are the layout of the bounce window, the activating time, the size and distance of the the app icon. The dependent variable is the operation time.

#### Process and details

Similarly, the subjects will be inform to use their dominant thumb to slide over the screen and can use the left hand as support to hold the cellphone. The followings will be informed before the experiment begins:

```
1. The pattern lock must be opened with one hand. (36987452).
2. The experiment is divided into two stages, each of which will have a 3 second preparation time.
3. After starting, you must open the pattern lock at the fastest speed.
4. The first stage is an analog phone stage. Please click it in the customary way.
5. The second stage is the experiment stage. The subject must press and hold to activate the app.
6. The target position is randomly generated, please pay attention and not to hurry. Correctness is the first priority.
```

It will record the time required to open the pattern lock (5 times) under different configurations (two general configurations, four experiments), different app positions (random), so the subject will experience 1\*4\*5+1\*6\*5+2\*4\*5+2\*6\*5=150 tests. The experiment takes about 8 minutes.

<img src="image/3.png" width="550">

## Results

In this section we will analyze the results of the three experiments and discuss their significance. First, we will infer the user's  habit by collecting coordinate points. The following figure shows the points (after removing the abnormal points) in the first experimental configuration (90 degrees, 0 degrees, -90 degrees, 180 degrees), and the red line in the distribution chart represents the Gaussian distribution and the purple is the actual distribution.

<img src="image/4.png" width="550">

<img src="image/5.png" width="550">

From the data above, it is obvious that even if the graphics is the same in aspect of ratio, size, and distance, there is still an inaccurate click, and this distribution is closely related to the direction of finger movement. For the target position 1, the center point is located at (0, 200), but the average value falls at (-0.4906, 222.0566). Specifically, users tend to release their fingers north when they move the fingers upward.

The difference in standard deviation also deserves to be analyzed. In the vertical axis of Table 1, the "x-coordinate standard deviation" can obviously be divided into two blocks. The first block is the part of the target position 1 and 3, Their standard deviations are 22.0391 and 18.8988 respectively; while the second block is the part of the target position 2 and 4, their standard deviations are 31.5921 and 28.0826 respectively. The experimental configurations 2 and 4 relying on the longitudinal movement of the finger have a large standard deviation in the x coordinate, and in a similar way, we can find that the experimental configurations 1 and 3 relying on the lateral movement of the finger appear in the y coordinate. This shows that when the user's dragging position is closely related to the movement direction, more precisely, the drop point will be greatly different in the x-axis direction when the finger is dragged horizontally, and the drop point will be in the y-axis when dragged vertically. The direction is quite different.

Summarizing the above two points, there is inaccuracy even in the drag task, and such a point of inaccuracy is regular. The wrong position will deviate from the correct position in the direction of finger movement, and there will be a large standard deviation distribution in the direction of movement. This can be understood by the narrow distribution of the midpoints.

<img src="image/6.png" width="550">

<img src="image/7.png" width="550">

Similar phenomonon occured in the following experiments (3,4).

<img src="image/8.png" width="550">

<img src="image/9.png" width="550">

<img src="image/10.png" width="550">

<img src="image/11.png" width="550">

Due to the limitation of hands' movement, the points are more concentrated at about -30 degree in the 6-way configurations. In addition, we compare the Eulidean distance between ground truth position and the drop point. In configureation 3, the distances are: *27.3790*、44.3147、46.1790、51.2296、*53.8679*、43.0804. In configuration 4, the distances are: 46.5546、41.0050、51.0400、*69.6366*、44.2386、*35.4826*. Astonishingly, some deviations are twice of the others, which reflects the habits of the users.

<img src="image/12.png" width="550">

On the second experiment, we are going to determin the intention of the users to long-press. This idea comes from the trade-off between efficiency and correctness. Too much the time to recognized as long-press makes it inefficient, while too less makes error rate arises. Hoping to find the balance point of the trade-off, we recorded the error rate while the subjects were trying to long-press. We had tried 0.100, 0.135, 0.170, 0.205, 0.240, 0.275 second respectively and the results are shown below:

<img src="image/13.png" width="350">

Obviously, 0.24 second is the knee point and we picked it as the time required for users' intention to long-press.

On the thrid experiment, we are going to find out the required time for our method and usual method to unlock and activate apps. The graph below shows the difference:

<img src="image/14.png" width="550">

<img src="image/15.png" width="550">

