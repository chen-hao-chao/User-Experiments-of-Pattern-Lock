# User Experiments of Pattern Lock

## Introduction

As people attach great importance to the safety of mobile devices, screen locks have become the first line of defense, and a variety of applications have emerged today. However, its use often results in a decrease in use efficiency. According to a survey conducted by Apple in 2016, users open their mobile phone about 80 times per day on average. The overhead caused by unlocking is very pretty heavy. Therefore, this project aims to minimize the time of unlocking and activate applications on smart phones. Three analyses of usage habits was performed. We came up with a one-step way to "unlock + activate the app" to reduce the operation time for users to select applications on the main page. Also, this concept is applicable. Whether it is pattern lock, as well as fingerprint and face recognition locks, can be applied to this setting. The following experiments will be conducted on android smart phones. We will discuss the experimental results and subject's behavior in detail in the following sections.

## Method

We have designed a set of experiments, including three sub-parts, which are aimed at landing habits, error rates, time did the corresponding test. The implementation details are shown below:

#### Recruitment of subjects

A total of 11 subjects were recruited in this experiment, 6 women and 5 men. All 11 subjects have used smart phones for more than 5 years. There are no discomforts in the hands, and the dominant hand is the right hand.

#### Experiment 1

The overall workflow is shown below. This experiment will evaluate the landing points of users. The experimental variables are the position of the target app and the layout of the bounce window after long pressure. The control amount is the pattern, the firing time, the app graphic size and the distance, and the dependent variable is its click position.

#### Process and details

The input device is an Android phone, and mode 1 test of the experimental app is used[1]. The subject shall slide the thumb with his dominant thumb ock, and can be supported by another hand-held mobile phone on the back of the dominant hand. The followings will be informed before the experiment begins:
```
1. In this experiment, you must slide the graphic lock with one hand, the pattern is 36987452 (Figure 4).
2. In this experiment, when the fixed point (2) is reached, you must hold it for 0.5s, and a box will pop out.
3. After the icon frame pops out, move your finger to the target pattern and release it.
4. Please note that the target pattern will change its position every five times. It will not be timed. Please give priority to accuracy.
```
We will record the position of different apps (4 types in total), including different app positions (configuration 1 and 2 for 4 types, configuration 3 and 4 for 6 types), position of fingers' release(5 times), so the subject will undergo 4\*4\*5\*2+4\*6\*5\*2=400(times) at this stage. The experiment takes about 7 minutes.

<img src="images/1.png" width="450">

#### Experiment 2

The overall workflow is shown below. This experiment will evaluate the subject's intention to unlock by checking the pressing time to activate the bounce window. Obviously, the experimental variable is the pressing time. The controlled variable is the path of the pattern lock while the dependent variable is the error rate.

#### Process and details

The input device is an Android phone. Mode 2 of an experimental app had been used [1]. The subject shall slide over the screen with the thumb of his dominant thumb. It is allowed to use another hand to hold back. The following rules will be informed before the experiment begins:

```
1. In this experiment, the lock must be opened with one hand, case 36987452.
2. In this experiment, when you reach the specified point, you must hold on for a while.
3. It will restart automatically after a short period of time.
4. No time, please give priority to accuracy.
```

It will record the time required to open the pattern lock. A total of 10 times test under different pause time (a total of 6 types) will be conducted. The subject will go through 6\*10=60 tests and it takes about 4 minutes.

<img src="images/2.png" width="450">

#### Experiment 3

The overall workflow is shown below. This experiment will compare the time required to open a certain app between using our method and the usual way. The experimental variables are the mode after unlocking, the location of the target app, and the layout of the bounce window after long-pressing. The controlled variables are the layout of the bounce window, the activating time, the size and distance of the the app icon. The dependent variable is its operation time.

#### Process and details

The input device is an Android phone. Mode 2 of an experimental app had been used [1]. The subject shall open the pattern lock with the thumb of the dominant hand. It is allowed to use another hand to hold back. The following rules will be informed before the experiment begins:

```
1. In this experiment, the pattern lock must be opened with one hand. (36987452).
2. The experiment is divided into two stages, each of which will have a 3 second preparation time.
3. After starting, you must open the pattern lock at the fastest speed.
4. The first stage is an analog phone stage. Please click it in the customary way.
5. The second stage is the experiment stage. The subject must press and hold to activate the app.
6. The target position is randomly generated, please pay attention and not to hurry. Correctness is the first priority.
```

It will record the time required to open the pattern lock (5 times) under different configurations (two general configurations, four experiments), different app positions (random), so the subject will experience 1\*4\*5+1\*6\*5+2\*4\*5+2\*6\*5=150 tests. The experiment takes about 8 minutes.

<img src="images/3.png" width="450">
